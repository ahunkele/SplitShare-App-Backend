package splitshare.app.backend.dataaccess_jdbc;

import java.util.List;

import splitshare.app.backend.dataaccess.UserDO;
import splitshare.app.backend.dataaccess_jdbc_utils.DAOException;

final public class UserDAOImpl implements UserDAO
{

    private DAOFactory daoFactory;

    public UserDAOImpl(final DAOFactory daoFactory)
    {
        this.daoFactory = daoFactory;
    }

    @Override
    public UserDO findByUID(final Long id) throws DAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserDO find(final String email, final String password) throws DAOException
    {
        // TODO Auto-generated method stub
        return null;
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

}
