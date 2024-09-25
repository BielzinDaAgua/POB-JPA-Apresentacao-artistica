package tela;

import fachada.Fachada;
import model.Cidade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CidadeFrame extends JFrame {
    private JTextField nomeField;
    private JTextField capacidadeField;
    private JTextArea listaCidadesArea;
    private JButton cadastrarButton, excluirButton, listarButton;

    public CidadeFrame() {

        setTitle("Gerenciamento de Cidades");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Use DISPOSE_ON_CLOSE
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        panel.add(nomeField);

        panel.add(new JLabel("Capacidade:"));
        capacidadeField = new JTextField();
        panel.add(capacidadeField);

        cadastrarButton = new JButton("Cadastrar");
        panel.add(cadastrarButton);

        excluirButton = new JButton("Excluir");
        panel.add(excluirButton);

        listarButton = new JButton("Listar");
        panel.add(listarButton);

        listaCidadesArea = new JTextArea();
        listaCidadesArea.setEditable(false);
        panel.add(new JScrollPane(listaCidadesArea));

        add(panel);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCidade();
            }
        });

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirCidade();
            }
        });

        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarCidades();
            }
        });
    }

    private void cadastrarCidade() {
        try {
            String nome = nomeField.getText();
            int capacidade = Integer.parseInt(capacidadeField.getText());

            Fachada.criarCidade(nome, capacidade);
            JOptionPane.showMessageDialog(this, "Cidade cadastrada com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar cidade: " + e.getMessage());
        }
    }

    private void excluirCidade() {
        try {
            String nome = nomeField.getText();

            // Exibe um diálogo de confirmação antes de excluir
            int confirmacao = JOptionPane.showConfirmDialog(
                    this,
                    "Tem certeza que deseja excluir a cidade com nome: " + nome + "?",
                    "Confirmação de exclusão",
                    JOptionPane.YES_NO_OPTION
            );

            // Se o usuário clicar em "Sim", a exclusão será realizada
            if (confirmacao == JOptionPane.YES_OPTION) {
                Fachada.deletarCidade(nome);
                JOptionPane.showMessageDialog(this, "Cidade excluída com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Exclusão cancelada.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir cidade: " + ex.getMessage());
        }
    }

    private void listarCidades() {
        try {
            List<Cidade> cidades = Fachada.listarCidades();
            listaCidadesArea.setText("");
            for (Cidade cidade : cidades) {
                listaCidadesArea.append("Nome: " + cidade.getNome() + ", Capacidade: " + cidade.getCapacidadePublico() + "\n");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar cidades: " + e.getMessage());
        }
    }

}
