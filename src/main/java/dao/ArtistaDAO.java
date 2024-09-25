package dao;

import jakarta.persistence.LockModeType;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import model.Artista;

import java.util.Date;
import java.util.List;

public class ArtistaDAO extends DAO<Artista> {

    @Override
    public Artista read(Object chave) {
        open(); // Certifique-se de abrir o EntityManager
        try {
            String nome = (String) chave;
            TypedQuery<Artista> query = manager.createQuery("SELECT a FROM Artista a WHERE a.nome = :x", Artista.class);
            query.setParameter("x", nome);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            close(); // Feche o EntityManager para evitar vazamentos de conexão
        }
    }


    public Artista readLock(Object chave) {
        try {
            String nome = (String) chave;
            TypedQuery<Artista> query = manager.createQuery("SELECT a FROM Artista a WHERE a.nome = :x", Artista.class);
            query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
            query.setParameter("x", nome);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Artista> readAll() {
        open();  // Abrir o EntityManager
        try {
            TypedQuery<Artista> query = manager.createQuery(
                    "SELECT a FROM Artista a LEFT JOIN FETCH a.listaApresentacao ORDER BY a.id",
                    Artista.class
            );
            return query.getResultList();
        } finally {
            close();  // Fechar o EntityManager
        }
    }


    public List<Artista> listarArtistasComMaisDeNApresentacoes(int n) {
        open();  // Abrir o EntityManager
        try {
            String jpql = "SELECT a FROM Artista a WHERE SIZE(a.listaApresentacao) > :n";
            return manager.createQuery(jpql, Artista.class)
                    .setParameter("n", n)
                    .getResultList();
        } finally {
            close();  // Fechar o EntityManager
        }
    }


    public List<Artista> listarArtistasPorDataECidade(Date data, String cidadeNome) {
        open();  // Abrir o EntityManager
        try {
            String jpql = "SELECT DISTINCT a FROM Artista a " +
                    "JOIN a.listaApresentacao ap " +
                    "JOIN ap.cidade c " +
                    "WHERE ap.data = :data AND c.nome = :cidadeNome";
            TypedQuery<Artista> query = manager.createQuery(jpql, Artista.class);
            query.setParameter("data", data);
            query.setParameter("cidadeNome", cidadeNome);
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
            manager.createQuery("DELETE FROM Artista").executeUpdate();
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback(); // Em caso de erro, faz rollback da transação
            throw e; // Repassa a exceção
        } finally {
            close(); // Feche o EntityManager para evitar vazamentos de conexão
        }
    }



}
