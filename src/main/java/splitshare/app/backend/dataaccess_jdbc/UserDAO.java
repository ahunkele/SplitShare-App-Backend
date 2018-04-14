package splitshare.app.backend.dataaccess_jdbc;

import java.util.List;

import splitshare.app.backend.dataaccess.UserDO;
import splitshare.app.backend.dataaccess_jdbc_utils.DAOException;

public interface UserDAO
{

    public UserDO find(final String email, final String password) throws DAOException;

    public List<UserDO> list() throws DAOException;

    public void create(final UserDO user) throws DAOException;

    public void update(final UserDO user) throws DAOException;

    public void delete(final UserDO user) throws DAOException;

    public boolean emailExisting(final String email) throws DAOException;

    public void changePassword(final UserDO user) throws DAOException;

}
