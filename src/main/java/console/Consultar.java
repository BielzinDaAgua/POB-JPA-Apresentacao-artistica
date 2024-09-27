package console;

import excecoes.ExcecaoNegocio;
import fachada.Fachada;
import model.Apresentacao;
import model.Artista;
import model.Cidade;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Consultar {
    public static void main(String[] args) {
        Fachada fachada = new Fachada();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            StringBuilder resultado = new StringBuilder();
            Date data = dateFormat.parse("24/09/2024");

            // Listar todas as apresentações
            List<Apresentacao> todasApresentacoes = fachada.listarApresentacoes();
            resultado.append("Todas as Apresentações:\n");
            for (Apresentacao apresentacao : todasApresentacoes) {
                resultado.append("ID: ").append(apresentacao.getId())
                        .append(" / Data: ").append(apresentacao.getData())
                        .append("\nArtista: ").append(apresentacao.getArtista().getNome())
                        .append(" / Cidade: ").append(apresentacao.getCidade().getNome())
                        .append("\nPreço do Ingresso: ").append(apresentacao.getPrecoIngresso())
                        .append(" / Duração: ").append(apresentacao.getDuracao()).append(" minutos")
                        .append("\nIngressos Vendidos: ").append(apresentacao.getNumeroDeIngressosVendidos())
                        .append("\n\n");
            }

            // Listar todos os artistas
            List<Artista> todosArtistas = fachada.listarArtistas();
            resultado.append("\nTodos os Artistas:\n");
            for (Artista artista : todosArtistas) {
                resultado.append("Nome: ").append(artista.getNome())
                        .append(" / Gênero Musical: ").append(artista.getGeneroMusical())
                        .append(" / Idade: ").append(artista.getIdade())
                        .append("\n");
            }

            // Listar todas as cidades
            List<Cidade> todasCidades = fachada.listarCidades();
            resultado.append("\nTodas as Cidades:\n");
            for (Cidade cidade : todasCidades) {
                resultado.append("Nome: ").append(cidade.getNome())
                        .append(" / Capacidade de Público: ").append(cidade.getCapacidadePublico())
                        .append("\n");
            }

            // Consulta 1: Listar apresentações por data
            List<Apresentacao> apresentacoesPorData = fachada.listarApresentacoesPorData(data);
            resultado.append("\nApresentações na data ").append(data).append(":\n");
            for (Apresentacao apresentacao : apresentacoesPorData) {
                resultado.append(apresentacao.getArtista().getNome())
                        .append(" em ").append(apresentacao.getCidade().getNome())
                        .append("\n");
            }

            // Consulta 2: Listar todas as apresentações de um determinado artista
            String nomeArtista = "Artista1";
            List<Apresentacao> apresentacoesDoArtista = fachada.listarApresentacoesPorArtista(nomeArtista);
            resultado.append("\nApresentações do artista ").append(nomeArtista).append(":\n");
            for (Apresentacao apresentacao : apresentacoesDoArtista) {
                resultado.append("Na cidade ").append(apresentacao.getCidade().getNome())
                        .append(" em ").append(apresentacao.getData())
                        .append("\n");
            }

            // Consulta 3: Listar apresentações por cidade
            String nomeCidade = "Cidade1";
            List<Apresentacao> apresentacoesPorCidade = fachada.listarApresentacoesPorCidade(nomeCidade);
            resultado.append("\nApresentações na cidade ").append(nomeCidade).append(":\n");
            for (Apresentacao apresentacao : apresentacoesPorCidade) {
                resultado.append(apresentacao.getArtista().getNome())
                        .append(" em ").append(apresentacao.getData())
                        .append("\n");
            }

            // Consulta 4: Listar artistas com mais de N apresentações
            int n = 1;
            List<Artista> artistasComMaisDeNApresentacoes = fachada.listarArtistasComMaisDeNApresentacoes(n);
            resultado.append("\nArtistas com mais de ").append(n).append(" apresentações:\n");
            for (Artista artista : artistasComMaisDeNApresentacoes) {
                resultado.append(artista.getNome())
                        .append(" (").append(artista.getListaApresentacao().size()).append(" apresentações)")
                        .append("\n");
            }

            // Consulta 5: Listar artistas por data e cidade
            List<Artista> artistasPorDataECidade = fachada.listarArtistasPorDataECidade(data, nomeCidade);
            resultado.append("\nArtistas na data ").append(data).append(" e na cidade ").append(nomeCidade).append(":\n");
            for (Artista artista : artistasPorDataECidade) {
                resultado.append(artista.getNome())
                        .append("\n");
            }

            // Exibir os resultados em uma janela de alerta
            JOptionPane.showMessageDialog(null, resultado.toString(), "Resultados da Consulta", JOptionPane.INFORMATION_MESSAGE);

        } catch (ExcecaoNegocio e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
