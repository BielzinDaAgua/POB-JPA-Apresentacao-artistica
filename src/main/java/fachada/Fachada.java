package fachada;

import dao.*;
import excecoes.ExcecaoNegocio;
import jakarta.persistence.EntityManager;
import model.Apresentacao;
import model.Artista;
import model.Cidade;

import java.util.Date;
import java.util.List;

public class Fachada {
    private static ArtistaDAO artistaDAO = new ArtistaDAO();
    private static ApresentacaoDAO apresentacaoDAO = new ApresentacaoDAO();
    private static CidadeDAO cidadeDAO = new CidadeDAO();

    public static void inicializar() {
        Util.iniciarFactory();
    }

    public static void finalizar() {
        Util.fecharFactory();
    }

    public static Artista criarArtista(String nome, int idade, String generoMusical) throws Exception {
        try {
            artistaDAO.open();  // Abrir o EntityManager no DAO
            Artista artista = artistaDAO.read(nome);
            if (artista != null) {
                throw new Exception("Artista já existe");
            }
            if (idade < 18) {
                throw new ExcecaoNegocio("Artista deve ter no mínimo 18 anos.");
            }
            artista = new Artista(nome, generoMusical, idade);
            artistaDAO.create(artista);  // Persistir o artista
            return artista;
        } finally {
            artistaDAO.close();  // Fechar o EntityManager no final
        }
    }





    // Método para criar uma nova Apresentação
    public static Apresentacao criarApresentacao(Date data, String nomeArtista, String nomeCidade, double preco, int duracao, int ingressosVendidos) throws Exception {
        EntityManager manager = Util.getEntityManager();
        try {
            DAO.beginTransaction(manager);

            // Busca o Artista e a Cidade
            Artista artista = artistaDAO.read(nomeArtista);
            Cidade cidade = cidadeDAO.read(nomeCidade);

            // Verifica se o Artista e a Cidade existem
            if (artista == null || cidade == null) {
                DAO.rollbackTransaction(manager);
                throw new Exception("Artista ou cidade não encontrados");
            }

            // Valida o preço do ingresso
            if (preco < 10.0) {
                DAO.rollbackTransaction(manager);
                throw new Exception("Preço do ingresso deve ser no mínimo 10");
            }

            // Cria a apresentação com os objetos Artista e Cidade
            Apresentacao apresentacao = new Apresentacao(data, artista, cidade, preco, duracao, ingressosVendidos);
            apresentacaoDAO.create(apresentacao);

            // Confirma a transação
            DAO.commitTransaction(manager);

            return apresentacao;
        } finally {
            manager.close();
        }
    }

    // Método para criar uma nova Cidade
    public static Cidade criarCidade(String nome, int capacidadePublico) throws Exception {
        EntityManager manager = Util.getEntityManager();
        try {
            DAO.beginTransaction(manager);
            Cidade cidade = cidadeDAO.read(nome);
            if (cidade != null) {
                DAO.rollbackTransaction(manager);
                throw new Exception("Cidade já existe");
            }
            cidade = new Cidade(nome, capacidadePublico);
            cidadeDAO.create(cidade);
            DAO.commitTransaction(manager);
            return cidade;
        } finally {
            manager.close();
        }
    }


    public static List<Artista> listarArtistas() {
        EntityManager manager = Util.getEntityManager();
        try {
            return artistaDAO.readAll();  // Sem transação
        } finally {
            manager.close();  // Certifique-se de fechar o EntityManager
        }
    }


    public static List<Apresentacao> listarApresentacoes() {
        EntityManager manager = Util.getEntityManager();
        try {
            DAO.beginTransaction(manager);
            List<Apresentacao> result = apresentacaoDAO.readAll();
            DAO.commitTransaction(manager);
            return result;
        } finally {
            manager.close();
        }
    }

    public static List<Cidade> listarCidades() {
        EntityManager manager = Util.getEntityManager();
        try {
            DAO.beginTransaction(manager);
            List<Cidade> result = cidadeDAO.readAll();
            DAO.commitTransaction(manager);
            return result;
        } finally {
            manager.close();
        }
    }

    public static void deletarArtista(String nome) throws Exception {
        EntityManager manager = Util.getEntityManager();
        try {
            DAO.beginTransaction(manager);

            // Busca o artista pelo nome
            Artista artista = artistaDAO.read(nome);
            if (artista == null) {
                DAO.rollbackTransaction(manager);
                throw new Exception("Artista não encontrado");
            }

            // Excluir o artista (apresentações associadas serão excluídas automaticamente pelo CascadeType.REMOVE)
            artistaDAO.delete(artista);

            DAO.commitTransaction(manager);
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                DAO.rollbackTransaction(manager);
            }
            throw e; // Relança a exceção para ser tratada no nível superior
        } finally {
            if (manager.isOpen()) {
                manager.close();  // Fecha o EntityManager
            }
        }
    }


    public static void deletarCidade(String nome) throws Exception {
        EntityManager manager = Util.getEntityManager();
        try {
            DAO.beginTransaction(manager);
            Cidade cidade = cidadeDAO.read(nome);
            if (cidade == null) {
                DAO.rollbackTransaction(manager);
                throw new Exception("Cidade não encontrada");
            }
            cidadeDAO.delete(cidade);  // Apresentações vinculadas serão excluídas automaticamente
            DAO.commitTransaction(manager);
        } catch (Exception e) {
            DAO.rollbackTransaction(manager);
            throw e;  // Repassa a exceção para exibir a mensagem correta
        } finally {
            if (manager.isOpen()) {
                manager.close();
            }
        }
    }


    public static void deletarApresentacao(int id) throws Exception {
        EntityManager manager = Util.getEntityManager();
        try {
            DAO.beginTransaction(manager);

            Apresentacao apresentacao = apresentacaoDAO.read(id);
            if (apresentacao == null) {
                throw new Exception("Apresentação não encontrada");
            }

            apresentacaoDAO.delete(apresentacao);
            DAO.commitTransaction(manager);
        } catch (Exception e) {
            DAO.rollbackTransaction(manager);
            throw e; // Repassa a exceção para exibir a mensagem correta
        } finally {
            if (manager.isOpen()) {
                manager.close();
            }
        }
    }



    public static void atualizarApresentacao(Apresentacao apresentacao) throws Exception {
        apresentacaoDAO.update(apresentacao);
        System.out.println("Apresentação atualizada com sucesso.");
    }


    public static Artista buscarArtistaPorNome(String nome) {
        EntityManager manager = Util.getEntityManager();
        try {
            return artistaDAO.read(nome);  // Sem transação
        } finally {
            manager.close();  // Certifique-se de fechar o EntityManager
        }
    }


    public static Cidade buscarCidadePorNome(String nome) {
        EntityManager manager = Util.getEntityManager();
        try {
            DAO.beginTransaction(manager);
            Cidade result = cidadeDAO.read(nome);
            DAO.commitTransaction(manager);
            return result;
        } finally {
            manager.close();
        }
    }

    public static List<Apresentacao> listarApresentacoesPorData(Date data) {
        EntityManager manager = Util.getEntityManager();
        try {
            DAO.beginTransaction(manager);
            List<Apresentacao> result = apresentacaoDAO.listarApresentacoesPorData(data);
            DAO.commitTransaction(manager);
            return result;
        } finally {
            manager.close();
        }
    }

    public static List<Apresentacao> listarApresentacoesPorArtista(String nomeArtista) {
        EntityManager manager = Util.getEntityManager();
        try {
            DAO.beginTransaction(manager);
            List<Apresentacao> result = apresentacaoDAO.listarApresentacoesPorArtista(nomeArtista);
            DAO.commitTransaction(manager);
            return result;
        } finally {
            manager.close();
        }
    }

    public static List<Apresentacao> listarApresentacoesPorCidade(String nomeCidade) {
        EntityManager manager = Util.getEntityManager();
        try {
            DAO.beginTransaction(manager);
            List<Apresentacao> result = apresentacaoDAO.listarApresentacoesPorCidade(nomeCidade);
            DAO.commitTransaction(manager);
            return result;
        } finally {
            manager.close();
        }
    }

    public static List<Artista> listarArtistasComMaisDeNApresentacoes(int n) {
        EntityManager manager = Util.getEntityManager();
        try {
            return artistaDAO.listarArtistasComMaisDeNApresentacoes(n);  // Sem transação
        } finally {
            manager.close();  // Certifique-se de fechar o EntityManager
        }
    }


    public static List<Artista> listarArtistasPorDataECidade(Date data, String cidadeNome) {
        EntityManager manager = Util.getEntityManager();
        try {
            return artistaDAO.listarArtistasPorDataECidade(data, cidadeNome);  // Sem transação
        } finally {
            manager.close();  // Certifique-se de fechar o EntityManager
        }
    }

}
