package tela;

import fachada.Fachada;
import model.Apresentacao;
import model.Artista;
import model.Cidade;

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
    private JTextField capacidadeMinimaField;
    private JTextArea resultadoArea;

    public ConsultasFrame() {
        setTitle("Consultas Avançadas");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Filtro de data
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Data (dd/MM/yyyy):"), gbc);
        dataField = new JTextField(15);
        gbc.gridx = 1;
        inputPanel.add(dataField, gbc);

        // Filtro de cidade
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Cidade:"), gbc);
        cidadeField = new JTextField(15);
        gbc.gridx = 1;
        inputPanel.add(cidadeField, gbc);

        // Filtro de artistas com mais de N apresentações
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Artistas com mais de N Apresentações:"), gbc);
        numApresentacoesField = new JTextField(15);
        gbc.gridx = 1;
        inputPanel.add(numApresentacoesField, gbc);

        // Filtro de capacidade mínima do local
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Capacidade Mínima do Local:"), gbc);
        capacidadeMinimaField = new JTextField(15);
        gbc.gridx = 1;
        inputPanel.add(capacidadeMinimaField, gbc);

        // Botão de consulta
        JButton consultarButton = new JButton("Consultar");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(consultarButton, gbc);

        // Área de resultado
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
            int numApresentacoes = !numApresentacoesField.getText().isEmpty() ?
                    Integer.parseInt(numApresentacoesField.getText()) : 0;
            int capacidadeMinima = !capacidadeMinimaField.getText().isEmpty() ?
                    Integer.parseInt(capacidadeMinimaField.getText()) : 0;

            Date data = dataTexto.isEmpty() ? null : new SimpleDateFormat("dd/MM/yyyy").parse(dataTexto);

            // Consultas separadas
            StringBuilder resultado = new StringBuilder();

            // Apresentações
            resultado.append("Apresentações:\n");
            List<Apresentacao> apresentacoes = (data != null) ?
                    Fachada.listarApresentacoesPorData(data) : Fachada.listarApresentacoes();
            for (Apresentacao a : apresentacoes) {
                resultado.append(formatarApresentacao(a)).append("\n");
            }

            // Artistas por Data e Cidade
            if (!cidadeNome.isEmpty() && data != null) {
                resultado.append("\nArtistas por Data e Cidade:\n");
                List<Artista> artistasPorDataECidade = Fachada.listarArtistasPorDataECidade(data, cidadeNome);
                for (Artista artista : artistasPorDataECidade) {
                    resultado.append(formatarArtista(artista)).append("\n");
                }
            }

            // Artistas com mais de N apresentações
            resultado.append("\nArtistas:\n");
            List<Artista> artistasComMaisApresentacoes = (numApresentacoes > 0) ?
                    Fachada.listarArtistasComMaisDeNApresentacoes(numApresentacoes) : Fachada.listarArtistas();
            for (Artista a : artistasComMaisApresentacoes) {
                resultado.append(formatarArtista(a)).append("\n");
            }

            // Cidades
            resultado.append("\nCidades:\n");
            List<Cidade> cidades;
            if (cidadeNome.isEmpty()) {
                cidades = Fachada.listarCidades();
            } else {
                Cidade cidade = Fachada.buscarCidadePorNome(cidadeNome);
                cidades = (cidade != null) ? List.of(cidade) : List.of();  // Transformando cidade única em lista
            }
            for (Cidade c : cidades) {
                resultado.append(formatarCidade(c)).append("\n");
            }

            resultadoArea.setText(resultado.toString());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Erro ao formatar data: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro de formato numérico: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar consultas: " + e.getMessage());
        }
    }


    private String formatarApresentacao(Apresentacao apresentacao) {
        return "Data: " + apresentacao.getData() +
                ", Artista: " + apresentacao.getArtista().getNome() +
                ", Cidade: " + apresentacao.getCidade().getNome() +
                ", Preço Ingresso: R$" + apresentacao.getPrecoIngresso() +
                ", Duração: " + apresentacao.getDuracao() + " min" +
                ", Ingressos Vendidos: " + apresentacao.getNumeroDeIngressosVendidos();
    }

    private String formatarArtista(Artista artista) {
        return "Nome: " + artista.getNome() +
                ", Gênero Musical: " + artista.getGeneroMusical() +
                ", Idade: " + artista.getIdade();
    }

    private String formatarCidade(Cidade cidade) {
        return "Cidade: " + cidade.getNome() +
                ", Capacidade do Público: " + cidade.getCapacidadePublico();
    }
}
