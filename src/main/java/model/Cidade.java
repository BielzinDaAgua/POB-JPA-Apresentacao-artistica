package model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private int capacidadePublico;

    @OneToMany(mappedBy = "cidade", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Apresentacao> listaApresentacao;

    // Construtores
    public Cidade(String nome, int capacidadePublico) {
        this.nome = nome;
        this.capacidadePublico = capacidadePublico;
    }

    public Cidade() {}

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

    public int getCapacidadePublico() {
        return capacidadePublico;
    }

    public void setCapacidadePublico(int capacidadePublico) {
        this.capacidadePublico = capacidadePublico;
    }

    public List<Apresentacao> getListaApresentacao() {
        return listaApresentacao;
    }

    public void setListaApresentacao(List<Apresentacao> listaApresentacao) {
        this.listaApresentacao = listaApresentacao;
    }
}
