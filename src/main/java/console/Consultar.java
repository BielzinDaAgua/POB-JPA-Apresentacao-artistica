package console;

import excecoes.ExcecaoNegocio;
import fachada.Fachada;
import model.Apresentacao;
import model.Artista;

import java.util.Date;
import java.util.List;

public class Consultar {
    public static void main(String[] args) {
        Fachada fachada = new Fachada();

        try {
            // Consulta 1: Listar apresentações por data
            Date data = new Date();
            List<Apresentacao> apresentacoesPorData = fachada.listarApresentacoesPorData(data);
            System.out.println("Apresentações na data " + data + ":");
            for (Apresentacao apresentacao : apresentacoesPorData) {
                System.out.println(apresentacao.getArtista().getNome() + " em " + apresentacao.getCidade().getNome());
            }

            // Consulta 2: Listar todas as apresentações de um determinado artista
            String nomeArtista = "Artista1";
            List<Apresentacao> apresentacoesDoArtista = fachada.listarApresentacoesPorArtista(nomeArtista);
            System.out.println("\nApresentações do artista " + nomeArtista + ":");
            for (Apresentacao apresentacao : apresentacoesDoArtista) {
                System.out.println("Na cidade " + apresentacao.getCidade().getNome() + " em " + apresentacao.getData());
            }

            // Consulta 3: Listar artistas com mais de N apresentações
            int n = 1;
            List<Artista> artistasComMaisDeNApresentacoes = fachada.listarArtistasComMaisDeNApresentacoes(n);
            System.out.println("\nArtistas com mais de " + n + " apresentações:");
            for (Artista artista : artistasComMaisDeNApresentacoes) {
                System.out.println(artista.getNome() + " (" + artista.getListaApresentacao().size() + " apresentações)");
            }
        } catch (ExcecaoNegocio e) {
            System.err.println(e.getMessage());
        }
    }
}
