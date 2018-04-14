package splitshare.app.backend.dataaccess_jdbc;

public interface GenericDAO<T>
{
    public T findByUID(T t);
}
