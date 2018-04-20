package splitshare.app.backend.dataaccess_jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Verify;

import splitshare.app.backend.dataaccess.UserDO;
import splitshare.app.backend.dataaccess_jdbc_utils.DAOException;
import splitshare.app.backend.dataaccess_jdbc_utils.DAOUtils;

final public class UserDAOImpl implements UserDAO
{
    // SQL Query Constants.
    private final String SQL_FIND_BY_ID = "SELECT id, email, firstname, lastname FROM User WHERE id = ?";
    private final String SQL_FIND_BY_EMAIL_AND_PASSWORD = "SELECT id, email, firstname, lastname FROM User WHERE email = ? AND password = MD5(?)";
    private final String SQL_LIST_ORDER_BY_ID = "SELECT id, email, firstname, lastname, phonenumber FROM User ORDER BY id";
    private final String SQL_INSERT = "INSERT INTO User (uid, email, password, firstname, lastname, phonenumber) VALUES (?, ?, MD5(?), ?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE User SET email = ?, firstname = ?, lastname = ?, phonenumber = ? WHERE id = ?";
    private final String SQL_DELETE = "DELETE FROM User WHERE id = ?";
    private final String SQL_EXIST_EMAIL = "SELECT id FROM User WHERE email = ?";
    private final String SQL_CHANGE_PASSWORD = "UPDATE User SET password = MD5(?) WHERE id = ?";

    // Passed in DAO factory.
    private DAOFactory daoFactory;

    public UserDAOImpl(final DAOFactory daoFactory)
    {
        this.daoFactory = daoFactory;
    }

    @Override
    public UserDO findByUID(final Long id) throws DAOException
    {
        return find(SQL_FIND_BY_ID, id);
    }

    @Override
    public UserDO find(final String email, final String password) throws DAOException
    {
        return find(SQL_FIND_BY_EMAIL_AND_PASSWORD, email, password);
    }

    @Override
    public List<UserDO> list() throws DAOException
    {
        final List<UserDO> users = new ArrayList<>();

        try (Connection connection = daoFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
                ResultSet resultSet = statement.executeQuery();)
        {
            while (resultSet.next())
            {
                users.add(map(resultSet));
            }
        }
        catch (final SQLException e)
        {
            throw new DAOException(e);
        }

        return users;
    }

    @Override
    public String create(final UserDO user) throws DAOException
    {
        verifyIUserIsSet(user);

        final Object[] values = { user.getUID(), user.getEmail(), user.getPassword(), user.getFirstName(),
                user.getLastName(), user.getPhoneNumber() };

        try (Connection connection = daoFactory.getConnection();
                PreparedStatement statement = DAOUtils.prepareStatement(connection, SQL_INSERT, true, values);)
        {
            final int affectedRows = statement.executeUpdate();
            if (affectedRows == 0)
            {
                throw new DAOException("Creating user failed, no rows affected.");
            }
            else
            {
                return user.getUID();
            }
        }
        catch (final SQLException e)
        {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(final UserDO user) throws DAOException
    {
        verifyIUserIsSet(user);
        final Object[] values = { user.getEmail(), user.getFirstName(), user.getLastName(), user.getUID() };

        try (Connection connection = daoFactory.getConnection();
                PreparedStatement statement = DAOUtils.prepareStatement(connection, SQL_UPDATE, false, values);)
        {
            final int affectedRows = statement.executeUpdate();
            if (affectedRows == 0)
            {
                throw new DAOException("Updating user failed, no rows affected.");
            }
        }
        catch (final SQLException e)
        {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(final UserDO user) throws DAOException
    {
        verifyIUserIsSet(user);
        final String uid = user.getUID();

        try (Connection connection = daoFactory.getConnection();
                PreparedStatement statement = DAOUtils.prepareStatement(connection, SQL_DELETE, false, uid))
        {
            final int affectedRows = statement.executeUpdate();
            if (affectedRows == 0)
            {
                throw new DAOException("Delete user failed. No Rows affected.");
            }
        }
        catch (final SQLException e)
        {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean emailExisting(final String email) throws DAOException
    {
        boolean exists = false;

        try (Connection connection = daoFactory.getConnection();
                PreparedStatement statement = DAOUtils.prepareStatement(connection, SQL_EXIST_EMAIL, false, email);
                ResultSet resultSet = statement.executeQuery();)
        {
            exists = resultSet.next();
        }
        catch (final SQLException e)
        {
            throw new DAOException(e);
        }

        return exists;
    }

    @Override
    public void changePassword(final UserDO user) throws DAOException
    {
        verifyIUserIsSet(user);

        final Object[] values = { user.getPassword(), user.getUID() };

        try (Connection connection = daoFactory.getConnection();
                PreparedStatement statement = DAOUtils.prepareStatement(connection, SQL_CHANGE_PASSWORD, false,
                        values);)
        {
            final int affectedRows = statement.executeUpdate();
            if (affectedRows == 0)
            {
                throw new DAOException("Changing password failed, no rows affected.");
            }
        }
        catch (final SQLException e)
        {
            throw new DAOException(e);
        }
    }

    /**
     * Returns the user from the database matching the given SQL query with the
     * given values.
     *
     * @param sql
     *            The SQL query to be executed in the database.
     * @param values
     *            The PreparedStatement values to be set.
     * @return The user from the database matching the given SQL query with the
     *         given values.
     * @throws DAOException
     *             If something fails at database level.
     */
    private UserDO find(final String sql, final Object... values) throws DAOException
    {
        UserDO user = null;

        try (Connection connection = daoFactory.getConnection();
                PreparedStatement statement = DAOUtils.prepareStatement(connection, sql, false, values);
                ResultSet resultSet = statement.executeQuery();)
        {
            if (resultSet.next())
            {
                user = map(resultSet);
            }
        }
        catch (final SQLException e)
        {
            throw new DAOException(e);
        }

        return user;
    }

    private static UserDO map(final ResultSet resultSet) throws SQLException
    {
        final UserDO user = new UserDO();
        user.setEmail(resultSet.getString("email"));
        user.setFirstName(resultSet.getString("firstname"));
        user.setLastName(resultSet.getString("lastname"));

        return user;
    }

    private static void verifyIUserIsSet(final UserDO user)
    {
        Verify.verifyNotNull(user.getUID(), "User UID is null");
        Verify.verifyNotNull(user.getFirstName(), "User firstname is null");
        Verify.verifyNotNull(user.getLastName(), "User lastname is null");
        Verify.verifyNotNull(user.getEmail(), "User email is null");
        Verify.verifyNotNull(user.getPhoneNumber(), "User phone number is null");
    }

}
