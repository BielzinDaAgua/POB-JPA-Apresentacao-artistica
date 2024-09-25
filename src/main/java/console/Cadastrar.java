package console;

import excecoes.ExcecaoNegocio;
import fachada.Fachada;
import model.Artista;
import model.Cidade;

import java.util.Date;

public class Cadastrar {
    public static void main(String[] args) {
        Fachada fachada = new Fachada();

        try {
            // Cadastrando Artistas
            Artista artista1 = new Artista("Artista1", "Rock", 25);
            Fachada.criarArtista(artista1.getNome(), artista1.getIdade(), artista1.getGeneroMusical());

            Artista artista2 = new Artista("Artista2", "Pop", 30);
            fachada.criarArtista(artista2.getNome(), artista2.getIdade(), artista2.getGeneroMusical());

            Artista artista3 = new Artista("Artista3", "Jazz", 28);
            fachada.criarArtista(artista3.getNome(), artista3.getIdade(), artista3.getGeneroMusical());

            // Cadastrando Cidades
            Cidade cidade1 = new Cidade("Cidade1", 10000);
            fachada.criarCidade(cidade1.getNome(), cidade1.getCapacidadePublico());

            Cidade cidade2 = new Cidade("Cidade2", 15000);
            fachada.criarCidade(cidade2.getNome(), cidade2.getCapacidadePublico());

            Cidade cidade3 = new Cidade("Cidade3", 20000);
            fachada.criarCidade(cidade3.getNome(), cidade3.getCapacidadePublico());

            // Cadastrando Apresentações
            // Criar Apresentacao para artista1 e cidade1
            Fachada.criarApresentacao(
                    new Date(),                      // Data da apresentação
                    artista1.getNome(),               // Nome do artista 1
                    cidade1.getNome(),                // Nome da cidade 1
                    50,                               // Preço do ingresso
                    120,                              // Duração
                    5000                              // Número de ingressos vendidos
            );

// Criar Apresentacao para artista2 e cidade2
            Fachada.criarApresentacao(
                    new Date(),                      // Data da apresentação
                    artista2.getNome(),               // Nome do artista 2
                    cidade2.getNome(),                // Nome da cidade 2
                    75,                               // Preço do ingresso
                    150,                              // Duração
                    7000                              // Número de ingressos vendidos
            );

// Criar Apresentacao para artista3 e cidade3
            Fachada.criarApresentacao(
                    new Date(),                      // Data da apresentação
                    artista3.getNome(),               // Nome do artista 3
                    cidade3.getNome(),                // Nome da cidade 3
                    60,                               // Preço do ingresso
                    100,                              // Duração
                    4000                              // Número de ingressos vendidos
            );

            System.out.println("Objetos cadastrados com sucesso!");
        } catch (ExcecaoNegocio e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
