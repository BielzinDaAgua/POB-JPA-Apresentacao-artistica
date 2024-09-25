package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public abstract class DAO<T> implements DAOInterface<T> {
    protected EntityManager manager;

    public DAO() {}

    public void open() {
        if (manager == null || !manager.isOpen()) {
            manager = Util.getEntityManager();
        }
    }


    public void close() {
        if (manager != null && manager.isOpen()) {
            manager.close();
        }
    }

    public void create(T obj) throws Exception {
        open();  // Sempre garantir que o EntityManager esteja aberto
        try {
            manager.getTransaction().begin();  // Iniciar transação
            manager.persist(obj);  // Persistir o objeto
            manager.getTransaction().commit();  // Commitar transação
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();  // Rollback em caso de erro
            }
            throw e;  // Relançar a exceção
        } finally {
            close();  // Fechar o EntityManager
        }
    }



    public abstract T read(Object chave);

    public T update(T obj) {
        open(); // Certifique-se de abrir o EntityManager
        try {
            manager.getTransaction().begin();  // Iniciar a transação
            T updatedObj = manager.merge(obj);  // Atualizar o objeto
            manager.getTransaction().commit();  // Commitar a transação
            return updatedObj;  // Retorna o objeto atualizado
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();  // Rollback em caso de erro
            }
            throw e;  // Relançar a exceção
        } finally {
            close();  // Fechar o EntityManager
        }
    }

    public void delete(T obj) throws Exception {
        open();  // Abrir o EntityManager, se necessário
        try {
            manager.getTransaction().begin();  // Iniciar a transação

            // Para garantir que o objeto esteja anexado ao contexto de persistência
            if (!manager.contains(obj)) {
                obj = manager.merge(obj);  // Reanexar o objeto
            }

            manager.remove(obj);  // Remover o objeto
            manager.getTransaction().commit();  // Commitar a transação
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();  // Rollback em caso de erro
            }
            throw e;  // Relançar a exceção
        } finally {
            close();  // Fechar o EntityManager
        }
    }


    @SuppressWarnings("unchecked")
    public List<T> readAll() throws Exception {
        open();  // Abrir o EntityManager
        try {
            Class<T> type = (Class<T>) ((ParameterizedType) this.getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
            TypedQuery<T> query = manager.createQuery("select x from " + type.getSimpleName() + " x", type);
            return query.getResultList();
        } catch (Exception e) {
            throw e;  // Relançar exceção em caso de erro
        } finally {
            close();  // Fechar o EntityManager
        }
    }


    public static void beginTransaction(EntityManager manager) {
        if (!manager.getTransaction().isActive())
            manager.getTransaction().begin();
    }

    public static void commitTransaction(EntityManager manager) {
        if (manager.getTransaction().isActive()) {
            manager.getTransaction().commit();
            manager.clear();
        }
    }

    public static void rollbackTransaction(EntityManager manager) {
        if (manager.getTransaction().isActive())
            manager.getTransaction().rollback();
    }

    public static Connection getConnectionJdbc() {
        try {
            EntityManagerFactory factory = Util.getEntityManager().getEntityManagerFactory();
            String driver = (String) factory.getProperties().get("jakarta.persistence.jdbc.driver");
            String url = (String) factory.getProperties().get("jakarta.persistence.jdbc.url");
            String user = (String) factory.getProperties().get("jakarta.persistence.jdbc.user");
            String pass = (String) factory.getProperties().get("jakarta.persistence.jdbc.password");
            Class.forName(driver);
            return DriverManager.getConnection(url, user, pass);
        } catch (Exception ex) {
            return null;
        }
    }
}
