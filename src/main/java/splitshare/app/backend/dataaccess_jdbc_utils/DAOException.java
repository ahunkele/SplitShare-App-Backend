package splitshare.app.backend.dataaccess_jdbc_utils;

public class DAOException extends RuntimeException
{

    private static final long serialVersionUID = -1727663363921672855L;

    public DAOException(final String message)
    {
        super(message);
    }

    public DAOException(final Throwable cause)
    {
        super(cause);
    }

    public DAOException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

}
