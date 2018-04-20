package splitshare.app.backend.dataaccess_jdbc_utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import splitshare.app.backend.dataaccess.UserDO;
import splitshare.app.backend.dataaccess_jdbc.DAOFactory;
import splitshare.app.backend.dataaccess_jdbc.UserDAO;

public class DAOProperties
{

    private static final String PROPERTIES_FILE = "dao.properties";
    private static final Properties PROPERTIES = new Properties();

    private String specificKey;

    static
    {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

        if (propertiesFile == null)
        {
            throw new DAOConfigurationException("Properties file '" + PROPERTIES_FILE + "' is missing in classpath.");
        }

        try
        {
            PROPERTIES.load(propertiesFile);
        }
        catch (final IOException e)
        {
            throw new DAOConfigurationException("Cannot load properties file '" + PROPERTIES_FILE + "'.", e);
        }
    }

    public DAOProperties(final String specificKey)
    {
        this.specificKey = specificKey;
    }

    public String getProperty(final String key, final boolean mandatory) throws DAOConfigurationException
    {
        final String fullKey = this.specificKey + "." + key;
        String property = PROPERTIES.getProperty(fullKey);

        if (property == null || property.trim().length() == 0)
        {
            if (mandatory)
            {
                throw new DAOConfigurationException("Required property '" + fullKey + "'"
                        + " is missing in properties file '" + PROPERTIES_FILE + "'.");
            }
            else
            {
                property = null;
            }
        }

        return property;
    }

    public static void main(final String[] args)
    {
        final DAOFactory javabase = DAOFactory.getInstance("splitshare.jdbc");
        System.out.println("DAOFactory successfully obtained: " + javabase);

        // Obtain UserDAO.
        final UserDAO userDAO = javabase.getUserDAO();

        final UserDO user = new UserDO();
        user.setFirstName("andy");
        user.setLastName("hunkele");
        user.setEmail("ahunkele@gmail.com");
        user.setPassword("airking3");
        user.setPhoneNumber("8168780095");

        System.out.println(userDAO.create(user));

    }
}
