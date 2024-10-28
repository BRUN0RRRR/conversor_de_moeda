import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ConversorMoedas extends JFrame {
    private JTextField txtValor;
    private JComboBox<String> comboOrigem, comboDestino;
    private JLabel labelResultado;
    private JButton btnConverter, btnLimpar;

    // Taxas de câmbio fixas entre moedas
    private HashMap<String, Double> taxasCambio;

    public ConversorMoedas() {
        setTitle("Conversor de Moedas");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        inicializarTaxas();

        // Entrada de valor
        JPanel painelValor = new JPanel();
        painelValor.add(new JLabel("Valor a converter:"));
        txtValor = new JTextField(10);
        painelValor.add(txtValor);

        // Seleção de moeda de origem
        JPanel painelOrigem = new JPanel();
        painelOrigem.add(new JLabel("Moeda de Origem:"));
        comboOrigem = new JComboBox<>(new String[]{"USD", "BRL", "EUR", "JPY"});
        painelOrigem.add(comboOrigem);

        // Seleção de moeda de destino
        JPanel painelDestino = new JPanel();
        painelDestino.add(new JLabel("Moeda de Destino:"));
        comboDestino = new JComboBox<>(new String[]{"USD", "BRL", "EUR", "JPY"});
        painelDestino.add(comboDestino);

        // Resultado da conversão
        JPanel painelResultado = new JPanel();
        labelResultado = new JLabel("Valor Convertido: ");
        painelResultado.add(labelResultado);

        // Botões de converter e limpar
        JPanel painelBotoes = new JPanel();
        btnConverter = new JButton("Converter");
        btnLimpar = new JButton("Limpar");
        painelBotoes.add(btnConverter);
        painelBotoes.add(btnLimpar);

        // Adiciona todos os painéis ao JFrame
        add(painelValor);
        add(painelOrigem);
        add(painelDestino);
        add(painelResultado);
        add(painelBotoes);

        // Ações dos botões
        btnConverter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                converterMoeda();
            }
        });

        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
    }

    // Inicializa as taxas de câmbio
    private void inicializarTaxas() {
        taxasCambio = new HashMap<>();
        taxasCambio.put("USD-BRL", 5.25);
        taxasCambio.put("BRL-USD", 0.19);
        taxasCambio.put("USD-EUR", 0.85);
        taxasCambio.put("EUR-USD", 1.18);
        taxasCambio.put("USD-JPY", 110.0);
        taxasCambio.put("JPY-USD", 0.0091);
        taxasCambio.put("BRL-EUR", 0.16);
        taxasCambio.put("EUR-BRL", 6.25);
        taxasCambio.put("BRL-JPY", 21.0);
        taxasCambio.put("JPY-BRL", 0.047);
        taxasCambio.put("EUR-JPY", 130.0);
        taxasCambio.put("JPY-EUR", 0.0077);
        // Para moedas iguais, taxa de 1
        taxasCambio.put("USD-USD", 1.0);
        taxasCambio.put("BRL-BRL", 1.0);
        taxasCambio.put("EUR-EUR", 1.0);
        taxasCambio.put("JPY-JPY", 1.0);
    }

    private void converterMoeda() {
        try {
            double valor = Double.parseDouble(txtValor.getText());
            if (valor <= 0) {
                JOptionPane.showMessageDialog(this, "Digite um valor maior que zero.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String moedaOrigem = (String) comboOrigem.getSelectedItem();
            String moedaDestino = (String) comboDestino.getSelectedItem();
            String chave = moedaOrigem + "-" + moedaDestino;

            if (taxasCambio.containsKey(chave)) {
                double taxa = taxasCambio.get(chave);
                double valorConvertido = valor * taxa;
                labelResultado.setText(String.format("Valor Convertido: %.2f %s", valorConvertido, moedaDestino));
            } else {
                labelResultado.setText("Taxa de conversão não disponível.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite um valor numérico válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtValor.setText("");
        labelResultado.setText("Valor Convertido: ");
        comboOrigem.setSelectedIndex(0);
        comboDestino.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ConversorMoedas().setVisible(true);
            }
        });
    }
}
