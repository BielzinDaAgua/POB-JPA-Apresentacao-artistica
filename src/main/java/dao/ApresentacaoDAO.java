package dao;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import model.Apresentacao;

import java.util.Date;
import java.util.List;

public class ApresentacaoDAO extends DAO<Apresentacao> {

    @Override
    public Apresentacao read(Object chave) {
        open(); // Certifique-se de abrir o EntityManager
        try {
            Integer id = (Integer) chave;
            TypedQuery<Apresentacao> query = manager.createQuery("SELECT a FROM Apresentacao a WHERE a.id = :x", Apresentacao.class);
            query.setParameter("x", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            close(); // Feche o EntityManager para evitar vazamentos de conexão
        }
    }

    public List<Apresentacao> readAll() {
        open();  // Abrir o EntityManager
        try {
            TypedQuery<Apresentacao> query = manager.createQuery("SELECT a FROM Apresentacao a ORDER BY a.id", Apresentacao.class);
            return query.getResultList();
        } finally {
            close();  // Fechar o EntityManager
        }
    }



    public List<Apresentacao> listarApresentacoesPorData(Date data) {
        open();  // Abrir o EntityManager
        try {
            TypedQuery<Apresentacao> query = manager.createQuery(
                    "SELECT a FROM Apresentacao a WHERE a.data = :data", Apresentacao.class);
            query.setParameter("data", data);
            return query.getResultList();
        } finally {
            close();  // Fechar o EntityManager
        }
    }


    public List<Apresentacao> listarApresentacoesPorArtista(String nomeArtista) {
        open();  // Abrir o EntityManager
        try {
            TypedQuery<Apresentacao> query = manager.createQuery(
                    "SELECT a FROM Apresentacao a WHERE a.artista.nome = :nomeArtista", Apresentacao.class);
            query.setParameter("nomeArtista", nomeArtista);
            return query.getResultList();
        } finally {
            close();  // Fechar o EntityManager
        }
    }


    public List<Apresentacao> listarApresentacoesPorCidade(String nomeCidade) {
        open();  // Abrir o EntityManager
        try {
            TypedQuery<Apresentacao> query = manager.createQuery(
                    "SELECT a FROM Apresentacao a WHERE a.cidade.nome = :nomeCidade", Apresentacao.class);
            query.setParameter("nomeCidade", nomeCidade);
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
            manager.createQuery("DELETE FROM Apresentacao").executeUpdate();
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback(); // Em caso de erro, faz rollback da transação
            throw e; // Repassa a exceção
        } finally {
            close(); // Feche o EntityManager para evitar vazamentos de conexão
        }
    }

}
