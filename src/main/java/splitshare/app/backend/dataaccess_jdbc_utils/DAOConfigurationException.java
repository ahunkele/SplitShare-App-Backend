package splitshare.app.backend.dataaccess_jdbc_utils;

public class DAOConfigurationException extends RuntimeException
{

    private static final long serialVersionUID = -2029240292712596959L;

    public DAOConfigurationException(final String message)
    {
        super(message);
    }

    public DAOConfigurationException(final Throwable cause)
    {
        super(cause);
    }

    public DAOConfigurationException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
