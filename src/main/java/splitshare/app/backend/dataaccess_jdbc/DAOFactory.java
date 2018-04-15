package splitshare.app.backend.dataaccess_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import splitshare.app.backend.dataaccess_jdbc_utils.DAOConfigurationException;
import splitshare.app.backend.dataaccess_jdbc_utils.DAOProperties;

public abstract class DAOFactory
{

    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_DRIVER = "driver";
    private static final String PROPERTY_USERNAME = "username";
    private static final String PROPERTY_PASSWORD = "password";

    public static DAOFactory getInstance(final String name)
    {
        if (name == null)
        {
            throw new DAOConfigurationException("Database name is null.");
        }

        final DAOProperties properties = new DAOProperties(name);
        final String url = properties.getProperty(PROPERTY_URL, true);
        final String driverClassName = properties.getProperty(PROPERTY_DRIVER, false);
        final String username = properties.getProperty(PROPERTY_USERNAME, false);
        final String password = properties.getProperty(PROPERTY_PASSWORD, false);
        final DAOFactory instance;

        if (driverClassName != null)
        {
            try
            {
                Class.forName(driverClassName);
            }
            catch (final ClassNotFoundException e)
            {
                throw new DAOConfigurationException("Driver class '" + driverClassName + "' is missing in classpath.",
                        e);
            }
            instance = new DriverManagerDAOFactory(url, username, password);
        }
        else
        {
            DataSource dataSource;

            try
            {
                dataSource = (DataSource) new InitialContext().lookup(url);
            }
            catch (final NamingException e)
            {
                throw new DAOConfigurationException("DataSource '" + url + "' is missing in JNDI.", e);
            }
            if (username != null)
            {
                instance = new DataSourceWithLoginDAOFactory(dataSource, username, password);
            }
            else
            {
                instance = new DataSourceDAOFactory(dataSource);
            }
        }

        return instance;
    }

    abstract Connection getConnection() throws SQLException;

    public UserDAO getUserDAO()
    {
        return new UserDAOImpl(this);
    }
}

/**
 * The DriverManager based DAOFactory.
 */
class DriverManagerDAOFactory extends DAOFactory
{
    private String url;
    private String username;
    private String password;

    DriverManagerDAOFactory(final String url, final String username, final String password)
    {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(url, username, password);
    }
}

/**
 * The DataSource based DAOFactory.
 */
class DataSourceDAOFactory extends DAOFactory
{
    private DataSource dataSource;

    DataSourceDAOFactory(final DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    @Override
    Connection getConnection() throws SQLException
    {
        return dataSource.getConnection();
    }
}

/**
 * The DataSource-with-Login based DAOFactory.
 */
class DataSourceWithLoginDAOFactory extends DAOFactory
{
    private DataSource dataSource;
    private String username;
    private String password;

    DataSourceWithLoginDAOFactory(final DataSource dataSource, final String username, final String password)
    {
        this.dataSource = dataSource;
        this.username = username;
        this.password = password;
    }

    @Override
    Connection getConnection() throws SQLException
    {
        return dataSource.getConnection(username, password);
    }
}
