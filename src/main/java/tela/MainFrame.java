package tela;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Sistema de Gestão de Eventos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnArtistas = new JButton("Gerenciar Artistas");
        JButton btnApresentacoes = new JButton("Gerenciar Apresentações");
        JButton btnCidades = new JButton("Gerenciar Cidades");
        JButton btnConsultas = new JButton("Consultas");

        btnArtistas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ArtistaFrame().setVisible(true);
            }
        });

        btnApresentacoes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ApresentacaoFrame().setVisible(true);
            }
        });

        btnCidades.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CidadeFrame().setVisible(true);
            }
        });

        btnConsultas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ConsultasFrame().setVisible(true);
            }
        });

        setLayout(new GridLayout(4, 1));
        add(btnArtistas);
        add(btnApresentacoes);
        add(btnCidades);
        add(btnConsultas);
    }
}
