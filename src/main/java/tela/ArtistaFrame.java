package tela;

import fachada.Fachada;
import model.Artista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ArtistaFrame extends JFrame {
    private JTextField nomeField, generoField, idadeField;
    private JTextArea listaArtistasArea;
    private JButton criarButton, alterarButton, excluirButton, listarButton;

    public ArtistaFrame() {
        setTitle("Gerenciamento de Artistas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 5, 5)); // 6 linhas e 2 colunas com espaçamento de 5 pixels

        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(nomeLabel);
        nomeField = new JTextField();
        nomeField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(nomeField);

        JLabel generoLabel = new JLabel("Gênero Musical:");
        generoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(generoLabel);
        generoField = new JTextField();
        generoField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(generoField);

        JLabel idadeLabel = new JLabel("Idade:");
        idadeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(idadeLabel);
        idadeField = new JTextField();
        idadeField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(idadeField);

        criarButton = new JButton("Criar");
        panel.add(criarButton);

        alterarButton = new JButton("Alterar");
        panel.add(alterarButton);

        excluirButton = new JButton("Excluir");
        panel.add(excluirButton);

        listarButton = new JButton("Listar");
        panel.add(listarButton);

        listaArtistasArea = new JTextArea();
        listaArtistasArea.setEditable(false);
        panel.add(new JScrollPane(listaArtistasArea));

        add(panel);

        criarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarArtista();
            }
        });

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarArtista();
            }
        });

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirArtista();
            }
        });

        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarArtistas();
            }
        });
    }

    private void criarArtista() {
        try {
            String nome = nomeField.getText();
            String generoMusical = generoField.getText();
            int idade = Integer.parseInt(idadeField.getText());

            Fachada.criarArtista(nome, idade, generoMusical);
            JOptionPane.showMessageDialog(this, "Artista criado com sucesso!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao criar artista: " + ex.getMessage());
        }
    }

    private void alterarArtista() {
        try {
            // Como o método de alteração não está implementado na Fachada, ele precisa ser adicionado ou tratado aqui.
            JOptionPane.showMessageDialog(this, "Método de alteração não implementado.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao alterar artista: " + ex.getMessage());
        }
    }

    private void excluirArtista() {
        try {
            String nome = nomeField.getText();

            // Exibe um diálogo de confirmação antes de excluir
            int confirmacao = JOptionPane.showConfirmDialog(
                    this,
                    "Tem certeza que deseja excluir a artista com nome: " + nome + "?",
                    "Confirmação de exclusão",
                    JOptionPane.YES_NO_OPTION
            );

            // Se o usuário clicar em "Sim", a exclusão será realizada
            if (confirmacao == JOptionPane.YES_OPTION) {
                Fachada.deletarArtista(nome);
                JOptionPane.showMessageDialog(this, "Artista excluída com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Exclusão cancelada.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir artista: " + ex.getMessage());
        }
    }


    private void listarArtistas() {
        try {
            List<Artista> artistas = Fachada.listarArtistas();
            listaArtistasArea.setText("");
            for (Artista artista : artistas) {
                listaArtistasArea.append("Nome: " + artista.getNome() + " | Gênero Musical: " + artista.getGeneroMusical() + " | Idade: " + artista.getIdade() + "\n");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar artistas: " + e.getMessage());
        }
    }

}
