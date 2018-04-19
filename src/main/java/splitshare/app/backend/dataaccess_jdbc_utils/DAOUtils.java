package splitshare.app.backend.dataaccess_jdbc_utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public final class DAOUtils
{

    private DAOUtils()
    {
        // Utility class, no need for a constructor.
    }

    /**
     *
     * @param connection
     *            the given connection.
     * @param sql
     *            the String query.
     * @param returnKey
     *            if the statement should return generated key or not.
     * @param values
     *            the values to set.
     * @return a {@link PreparedStatement} with its values set.
     * @throws SQLException
     */
    public static PreparedStatement prepareStatement(final Connection connection, final String sql,
            final boolean returnKey, final Object... values) throws SQLException
    {
        final PreparedStatement pStatment = connection.prepareStatement(sql,
                returnKey ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);

        setStatmentValues(pStatment, values);

        return pStatment;
    }

    /**
     * Sets the given objects to the given PreparedStatement.
     *
     * @param statement
     *            the statement the objects will be set to.
     * @param values
     *            the input objects to set to the statement.
     * @throws SQLException
     */
    public static void setStatmentValues(final PreparedStatement statement, final Object... values) throws SQLException
    {
        for (int i = 0; i < values.length; i++)
        {
            statement.setObject(i + 1, values[i]);
        }
    }

}
