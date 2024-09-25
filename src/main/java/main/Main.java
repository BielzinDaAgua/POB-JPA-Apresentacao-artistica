package main;

import tela.MainFrame;

import javax.swing.*;

public class Main {
    private static fachada.Fachada Fachada;

    public static void main(String[] args) {
        // Inicializa o DAO no início do sistema
        Fachada.inicializar();

        // Cria a interface gráfica e a torna visível
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));

        // Adiciona um shutdown hook para finalizar o DAO ao fechar o programa
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Fachada.finalizar();
        }));
    }
}
