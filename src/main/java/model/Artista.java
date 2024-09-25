package model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Artista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private String generoMusical;
    private int idade;

    @OneToMany(mappedBy = "artista", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Apresentacao> listaApresentacao;

    // Construtores
    public Artista(String nome, String generoMusical, int idade) {
        this.nome = nome;
        this.generoMusical = generoMusical;
        this.idade = idade;
    }

    public Artista() {}  // Construtor padrão necessário para o JPA

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGeneroMusical() {
        return generoMusical;
    }

    public void setGeneroMusical(String generoMusical) {
        this.generoMusical = generoMusical;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public List<Apresentacao> getListaApresentacao() {
        return listaApresentacao;
    }

    public void setListaApresentacao(List<Apresentacao> listaApresentacao) {
        this.listaApresentacao = listaApresentacao;
    }
}
