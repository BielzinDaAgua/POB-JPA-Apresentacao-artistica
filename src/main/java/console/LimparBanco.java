package console;

import dao.ApresentacaoDAO;
import dao.ArtistaDAO;
import dao.CidadeDAO;

public class LimparBanco {
    public static void main(String[] args) {

        try {
            // Criando instâncias dos DAOs
            CidadeDAO cidadeDAO = new CidadeDAO();
            ArtistaDAO artistaDAO = new ArtistaDAO();
            ApresentacaoDAO apresentacaoDAO = new ApresentacaoDAO();

            // Iniciando a limpeza do banco de dados
            System.out.println("Limpando tabelas do banco...");

            // Removendo dados de Apresentacoes (que podem depender de Cidades e Artistas)
            apresentacaoDAO.deleteAll();
            System.out.println("Todas as apresentações foram removidas.");

            // Removendo dados de Artistas
            artistaDAO.deleteAll();
            System.out.println("Todos os artistas foram removidos.");

            // Removendo dados de Cidades
            cidadeDAO.deleteAll();
            System.out.println("Todas as cidades foram removidas.");

            System.out.println("Banco de dados limpo com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao limpar o banco de dados: " + e.getMessage());
        }
    }
}
