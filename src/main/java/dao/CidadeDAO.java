package dao;

import jakarta.persistence.LockModeType;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import model.Cidade;

import java.util.List;

public class CidadeDAO extends DAO<Cidade> {

    @Override
    public Cidade read(Object chave) {
        open(); // Certifique-se de abrir o EntityManager
        try {
            String nome = (String) chave;
            TypedQuery<Cidade> query = manager.createQuery("SELECT c FROM Cidade c WHERE c.nome = :x", Cidade.class);
            query.setParameter("x", nome);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            close(); // Feche o EntityManager para evitar vazamentos de conexão
        }
    }

    public Cidade readLock(Object chave) {
        try {
            String nome = (String) chave;
            TypedQuery<Cidade> query = manager.createQuery("SELECT c FROM Cidade c WHERE c.nome = :x", Cidade.class);
            query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
            query.setParameter("x", nome);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Cidade> readAll() {
        open();  // Abrir o EntityManager
        try {
            TypedQuery<Cidade> query = manager.createQuery("SELECT c FROM Cidade c ORDER BY c.id", Cidade.class);
            return query.getResultList();
        } finally {
            close();  // Fechar o EntityManager
        }
    }



    @Override
    public void deleteAll() {
        open(); // Certifique-se de abrir o EntityManager
        try {
            manager.getTransaction().begin();
            manager.createQuery("DELETE FROM Cidade").executeUpdate();
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback(); // Em caso de erro, faz rollback da transação
            throw e; // Repassa a exceção
        } finally {
            close(); // Feche o EntityManager para evitar vazamentos de conexão
        }
    }


}
