package ro.tuc.tp.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private JPanel contentPane;
    private JPanel variablesPanel;
    private JComboBox strategyComboBox;
    private JTextField timeLimitTextField;
    private JTextField maxProcessingTimeTextField;
    private JTextField minProcessingTimeTextField;
    private JTextField maxArrivalTimeTextField;
    private JTextField minArrivalTimeTextField;
    private JTextField numberOfServersTextField;
    private JTextField numberOfClientsTextField;
    private JTextArea result;
    private JButton startButton;
    private final Color backgroundColor = new Color(210, 190, 246);
    private final Color buttonColor = new Color(255,215,0);
    private final Border border = BorderFactory.createLineBorder(Color.BLACK);
    public String[] strategy = {"ConcreteStrategyTime", "ConcreteStrategyQueue"};
    public View() {
        this.setTitle("QUEUES MANAGEMENT");
        this.prepareGUI();
        this.setVisible(true);
    }
    public void prepareGUI() {
        this.setSize(1200, 650);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
        this.contentPane = new JPanel();
        this.prepareContentPane();
        this.prepareVariablePanel();
        this.setContentPane(contentPane);
    }
    public void prepareContentPane() {
        this.contentPane.setLayout(null);
        this.contentPane.setBackground(backgroundColor);
        JLabel contentPaneTitle = new JLabel("QUEUES MANAGEMENT");
        contentPaneTitle.setFont(new Font("Mono-type Corsica", Font.BOLD, 25));
        contentPaneTitle.setBounds(450, 5, 300, 50);
        contentPaneTitle.setForeground(Color.BLACK);
        this.contentPane.add(contentPaneTitle);
        this.prepareStrategy();
        this.prepareResultButton();
        this.prepareResult();
        this.initializeTextFields();
    }
    public void prepareStrategy() {
        JLabel strategyLabel = new JLabel("Strategy:", JLabel.CENTER);
        strategyLabel.setBounds(500, 83, 100, 25);
        strategyLabel.setFont(new Font("Mono-type Corsica", Font.BOLD, 20));
        strategyLabel.setForeground(Color.BLACK);
        strategyLabel.setBackground(backgroundColor);
        this.contentPane.add(strategyLabel);
        this.strategyComboBox = new JComboBox(strategy);
        this.strategyComboBox.setBounds(600, 80, 300, 35);
        this.strategyComboBox.setFont(new Font("Mono-type Corsica", Font.BOLD, 18));
        this.strategyComboBox.setBackground(Color.WHITE);
        this.strategyComboBox.setFocusable(false);
        this.strategyComboBox.setForeground(Color.BLACK);
        this.contentPane.add(strategyComboBox);
    }
    public void prepareResultButton() {
        startButton = new JButton("START");
        startButton.setBackground(buttonColor);
        startButton.setFont(new Font("Mono-type Corsica", Font.BOLD, 25));
        startButton.setForeground(Color.BLACK);
        startButton.setBorder(border);
        startButton.setFocusable(false);
        startButton.setBounds(980, 78, 150, 40);
        startButton.setActionCommand("START");
        this.contentPane.add(startButton);
    }
    public void addActionListener(ActionListener actionListener) {
        this.startButton.addActionListener(actionListener);
    }
    public void prepareResult() {
        result = new JTextArea(100, 50);
        result.setEditable(false);
        result.setForeground(Color.BLACK);
        result.setFont(new Font("Mono-type Corsica", Font.BOLD, 15));
        JScrollPane scrollPane = new JScrollPane(result);
        scrollPane.setBounds(450,180,725,400);
        this.contentPane.add(scrollPane);
    }
    public void initializeTextFields() {
        this.timeLimitTextField = new JTextField();
        this.maxProcessingTimeTextField = new JTextField();
        this.minProcessingTimeTextField = new JTextField();
        this.maxArrivalTimeTextField = new JTextField();
        this.minArrivalTimeTextField = new JTextField();
        this.numberOfClientsTextField = new JTextField();
        this.numberOfServersTextField = new JTextField();
    }
    public void prepareSetOfVariables(String labelName, JTextField textField) {
        JLabel label = new JLabel(labelName, JLabel.CENTER);
        label.setFont(new Font("Mono-type Corsica", Font.BOLD, 18));
        label.setForeground(Color.BLACK);
        label.setBackground(backgroundColor);
        this.variablesPanel.add(label);
        textField.setFont(new Font("Mono-type Corsica", Font.BOLD, 18));
        textField.setForeground(Color.BLACK);
        textField.setBorder(border);
        textField.setHorizontalAlignment(JTextField.CENTER);
        this.variablesPanel.add(textField);
    }
    public void prepareVariablePanel() {
        this.variablesPanel = new JPanel();
        this.variablesPanel = new JPanel(new GridLayout(7, 2, 0, 10));
        this.variablesPanel.setBounds(20,80,400, 500);
        this.variablesPanel.setBackground(backgroundColor);
        this.prepareSetOfVariables("Time Limit: ", timeLimitTextField);
        this.prepareSetOfVariables("Max Processing Time: ", maxProcessingTimeTextField);
        this.prepareSetOfVariables("Min Processing Time: ", minProcessingTimeTextField);
        this.prepareSetOfVariables("Max Arrival Time: ", maxArrivalTimeTextField);
        this.prepareSetOfVariables("Min Arrival Time: ", minArrivalTimeTextField);
        this.prepareSetOfVariables("Number of Servers: ", numberOfServersTextField);
        this.prepareSetOfVariables("Number of Clients: ", numberOfClientsTextField);
        this.contentPane.add(variablesPanel);
    }
    public int getTextField(JTextField textField) {
        return Integer.parseInt(textField.getText());
    }
    public String getComboBox() {
        return (String) strategyComboBox.getSelectedItem();
    }
    public JTextField getTimeLimitTextField() {
        return timeLimitTextField;
    }
    public JTextField getMaxProcessingTimeTextField() {
        return maxProcessingTimeTextField;
    }
    public JTextField getMinProcessingTimeTextField() {
        return minProcessingTimeTextField;
    }
    public JTextField getMaxArrivalTimeTextField() {
        return maxArrivalTimeTextField;
    }
    public JTextField getMinArrivalTimeTextField() {
        return minArrivalTimeTextField;
    }
    public JTextField getNumberOfServersTextField() {
        return numberOfServersTextField;
    }
    public JTextField getNumberOfClientsTextField() {
        return numberOfClientsTextField;
    }
    public void setTextArea(String string) {
        this.result.setText(string);
    }
}