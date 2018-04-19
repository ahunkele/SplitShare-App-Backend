package splitshare.app.backend.dataaccess_jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import splitshare.app.backend.dataaccess.UserDO;
import splitshare.app.backend.dataaccess_jdbc_utils.DAOException;
import splitshare.app.backend.dataaccess_jdbc_utils.DAOUtils;

final public class UserDAOImpl implements UserDAO
{
    // SQL Query Constants.
    private final String SQL_FIND_BY_ID = "SELECT id, email, firstname, lastname FROM User WHERE id = ?";
    private final String SQL_FIND_BY_EMAIL_AND_PASSWORD = "SELECT id, email, firstname, lastname FROM User WHERE email = ? AND password = MD5(?)";
    private final String SQL_LIST_ORDER_BY_ID = "SELECT id, email, firstname, lastname, FROM User ORDER BY id";
    private final String SQL_INSERT = "INSERT INTO User (email, password, firstname, lastname) VALUES (?, MD5(?), ?, ?, )";
    private final String SQL_UPDATE = "UPDATE User SET email = ?, firstname = ?, lastname = ?, WHERE id = ?";
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void create(final UserDO user) throws DAOException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(final UserDO user) throws DAOException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(final UserDO user) throws DAOException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean emailExisting(final String email) throws DAOException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void changePassword(final UserDO user) throws DAOException
    {
        // TODO Auto-generated method stub

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
        user.setUID(resultSet.getLong("id"));
        user.setEmail(resultSet.getString("email"));
        user.setFirstName(resultSet.getString("firstname"));
        user.setLastName(resultSet.getString("lastname"));

        return user;
    }

}
