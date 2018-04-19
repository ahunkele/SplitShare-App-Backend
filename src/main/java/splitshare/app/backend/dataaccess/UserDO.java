package splitshare.app.backend.dataaccess;

import java.io.Serializable;

public class UserDO implements Serializable
{

    private static final long serialVersionUID = -57758741951258636L;

    private Long uid;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;

    public Long getUID()
    {
        return this.uid;
    }

    public void setUID(final Long uid)
    {
        this.uid = uid;
    }

    public String getFirstName()
    {
        return this.firstName;
    }

    public void setFirstName(final String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public void setLastName(final String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(final String email)
    {
        this.email = email;
    }

    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber)
    {
        this.phoneNumber = phoneNumber;

    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(final String password)
    {
        this.password = password;
    }

    @Override
    public boolean equals(final Object other)
    {
        return (other instanceof UserDO) && (uid != null) ? uid.equals(((UserDO) other).getUID()) : (other == this);
    }

}
