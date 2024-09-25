package tela;

import fachada.Fachada;
import model.Apresentacao;
import model.Artista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ConsultasFrame extends JFrame {
    private JTextField dataField;
    private JTextField cidadeField;
    private JTextField numApresentacoesField;
    private JTextArea resultadoArea;

    public ConsultasFrame() {

        setTitle("Consultas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Data (dd/MM/yyyy):"), gbc);
        dataField = new JTextField(15);
        gbc.gridx = 1;
        inputPanel.add(dataField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Cidade:"), gbc);
        cidadeField = new JTextField(15);
        gbc.gridx = 1;
        inputPanel.add(cidadeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Artistas com mais de N Apresentações:"), gbc);
        numApresentacoesField = new JTextField(15);
        gbc.gridx = 1;
        inputPanel.add(numApresentacoesField, gbc);

        JButton consultarButton = new JButton("Consultar");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(consultarButton, gbc);

        resultadoArea = new JTextArea(10, 40);
        resultadoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultadoArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarConsultas();
            }
        });

        setVisible(true);
    }

    private void realizarConsultas() {
        try {
            String dataTexto = dataField.getText();
            String cidadeNome = cidadeField.getText();
            int numApresentacoes = Integer.parseInt(numApresentacoesField.getText());

            Date data = new SimpleDateFormat("dd/MM/yyyy").parse(dataTexto);

            List<Apresentacao> apresentacoesNaData = Fachada.listarApresentacoesPorData(data);
            List<Artista> artistasNaDataCidade = Fachada.listarArtistasPorDataECidade(data, cidadeNome);
            List<Artista> artistasComMaisApresentacoes = Fachada.listarArtistasComMaisDeNApresentacoes(numApresentacoes);

            StringBuilder resultado = new StringBuilder();

            resultado.append("Apresentações na Data:\n");
            for (Apresentacao a : apresentacoesNaData) {
                resultado.append(a.toString()).append("\n");
            }

            resultado.append("\nArtistas na Data e Cidade:\n");
            for (Artista a : artistasNaDataCidade) {
                resultado.append(a.toString()).append("\n");
            }

            resultado.append("\nArtistas com mais de ").append(numApresentacoes).append(" Apresentações:\n");
            for (Artista a : artistasComMaisApresentacoes) {
                resultado.append(a.toString()).append("\n");
            }

            resultadoArea.setText(resultado.toString());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Erro ao formatar data: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número de apresentações deve ser um número inteiro.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar consultas: " + e.getMessage());
        }
    }

}
