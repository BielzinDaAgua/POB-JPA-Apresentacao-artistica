package dao;

import java.util.List;

public interface DAOInterface<T> {
    public void create(T obj) throws Exception;
    public T read(Object chave);
    public T update(T obj);
    public void delete(T obj) throws Exception;
    public List<T> readAll() throws Exception;
    public void deleteAll();
}