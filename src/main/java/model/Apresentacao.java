package model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Apresentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    private Date data;

    private double precoIngresso;
    private int duracao;
    private int numeroDeIngressosVendidos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artista_id")
    private Artista artista;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;

    // Construtores
    public Apresentacao(Date data, Artista artista, Cidade cidade, double precoIngresso, int duracao, int numeroDeIngressosVendidos) {
        this.data = data;
        this.artista = artista;
        this.cidade = cidade;
        this.precoIngresso = precoIngresso;
        this.duracao = duracao;
        this.numeroDeIngressosVendidos = numeroDeIngressosVendidos;
    }

    public Apresentacao() {
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getPrecoIngresso() {
        return precoIngresso;
    }

    public void setPrecoIngresso(double precoIngresso) {
        this.precoIngresso = precoIngresso;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public int getNumeroDeIngressosVendidos() {
        return numeroDeIngressosVendidos;
    }

    public void setNumeroDeIngressosVendidos(int numeroDeIngressosVendidos) {
        this.numeroDeIngressosVendidos = numeroDeIngressosVendidos;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
}
