package console;

import excecoes.ExcecaoNegocio;
import fachada.Fachada;
import model.Artista;
import model.Apresentacao;
import model.Cidade;

import java.util.List;

public class Listar {
    public static void main(String[] args) {
        Fachada fachada = new Fachada();

        try {
            // Listando Artistas
            List<Artista> artistas = fachada.listarArtistas();
            System.out.println("Artistas:");
            for (Artista artista : artistas) {
                System.out.println(artista.getNome() + ", " + artista.getGeneroMusical() + ", " + artista.getIdade() + " anos");
            }

            // Listando Cidades
            List<Cidade> cidades = fachada.listarCidades();
            System.out.println("\nCidades:");
            for (Cidade cidade : cidades) {
                System.out.println(cidade.getNome() + ", capacidade: " + cidade.getCapacidadePublico());
            }

            // Listando Apresentações
            List<Apresentacao> apresentacoes = fachada.listarApresentacoes();
            System.out.println("\nApresentações:");
            for (Apresentacao apresentacao : apresentacoes) {
                System.out.println(apresentacao.getId() + ": " + apresentacao.getArtista().getNome() + " em " + apresentacao.getCidade().getNome() + " - " + apresentacao.getPrecoIngresso() + " reais");
            }
        } catch (ExcecaoNegocio e) {
            System.err.println(e.getMessage());
        }
    }
}
