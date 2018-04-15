package splitshare.app.backend.enum_consts;

public enum UserQueries
{
    SQL_FIND_BY_ID("SELECT id, email, firstname, lastname FROM User WHERE id = ?"),
    SQL_FIND_BY_EMAIL_AND_PASSWORD("SELECT id, email, firstname, lastname FROM User WHERE email = ? AND password = MD5(?)"),
    SQL_LIST_ORDER_BY_ID("SELECT id, email, firstname, lastname, FROM User ORDER BY id"),
    SQL_INSERT("INSERT INTO User (email, password, firstname, lastname) VALUES (?, MD5(?), ?, ?, )"),
    SQL_UPDATE("UPDATE User SET email = ?, firstname = ?, lastname = ?, WHERE id = ?"),
    SQL_DELETE("DELETE FROM User WHERE id = ?"),
    SQL_EXIST_EMAIL("SELECT id FROM User WHERE email = ?"),
    SQL_CHANGE_PASSWORD("UPDATE User SET password = MD5(?) WHERE id = ?");
    
    private final String value;
    
    private UserQueries(final String value)
    {
        this.value = value;
    }
    
    public String getQuery()
    {
        return this.value;
    }
    
}
