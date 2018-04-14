package splitshare.app.backend.dataaccess_jdbc;

import java.util.List;

import splitshare.app.backend.dataaccess.UserDO;
import splitshare.app.backend.dataaccess_jdbc_utils.DAOException;

final public class UserDAOImpl extends GenericDAOImpl<UserDO> implements UserDAO
{

    public UserDO find(final String email, final String password) throws DAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public List<UserDO> list() throws DAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public void create(final UserDO user) throws DAOException
    {
        // TODO Auto-generated method stub

    }

    public void update(final UserDO user) throws DAOException
    {
        // TODO Auto-generated method stub

    }

    public void delete(final UserDO user) throws DAOException
    {
        // TODO Auto-generated method stub

    }

    public boolean emailExisting(final String email) throws DAOException
    {
        // TODO Auto-generated method stub
        return false;
    }

    public void changePassword(final UserDO user) throws DAOException
    {
        // TODO Auto-generated method stub

    }

}
