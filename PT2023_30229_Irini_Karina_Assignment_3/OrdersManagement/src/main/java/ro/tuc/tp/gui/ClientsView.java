package ro.tuc.tp.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Aceasta clasa implementeaza fereasta corespunzatoare actiunilor posibile pe baza de date a clientilor.
 */

public class ClientsView extends JFrame {
    private JPanel contentPane;
    private JPanel variablesPanel;
    private final JTextField idTextField = new JTextField();
    private final JTextField nameTextField = new JTextField();
    private final JTextField addressTextField = new JTextField();
    private final JTextField emailTextField = new JTextField();
    private final JTextField ageTextField = new JTextField();
    private final Color backgroundColor =  new Color(173,216,230);
    private final Color buttonColor =  new Color(255,165,0);
    private final Border border = BorderFactory.createLineBorder(Color.WHITE);
    ControllerClients controllerClients = new ControllerClients(this);
    public ClientsView(String title) {
        super(title);
        this.prepareGUI();
    }
    public void prepareGUI() {
        this.setSize(800, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
        this.prepareContentPane();
        this.setContentPane(contentPane);
    }
    public void prepareContentPane() {
        this.contentPane = new JPanel();
        this.variablesPanel = new JPanel();
        this.contentPane.setLayout(null);
        this.variablesPanel.setLayout(null);
        this.contentPane.setBackground(backgroundColor);
        this.prepareLabel();
        this.prepareButtons();
        this.prepareVariablePanel();
    }
    public void prepareLabel() {
        JLabel clientsLabel = new JLabel("CLIENTS");
        clientsLabel.setFont(new Font("Mono-type Corsica", Font.BOLD, 30));
        clientsLabel.setBounds(330, 0, 200, 50);
        clientsLabel.setBackground(backgroundColor);
        clientsLabel.setForeground(Color.BLACK);
        this.contentPane.add(clientsLabel);
    }
    public void prepareButtons() {
        this.prepareButton("Insert Client", 20, 70, 200, 70);
        this.prepareButton("Delete Client", 20, 170, 200, 70);
        this.prepareButton("Edit Client", 20, 270, 200, 70);
        this.prepareButton("View All Clients",20, 370, 200, 70);
        this.prepareButton("Find Client By Id",20, 470, 200, 70);
        this.prepareButton("Clear Fields", 560, 490, 200, 50);
    }
    public void prepareButton(String actionCommand, int x, int y, int width, int height) {
        JButton button = new JButton(actionCommand);
        button.setFocusable(false);
        button.setBackground(buttonColor);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Mono-type Corsica", Font.BOLD, 21));
        button.setActionCommand(actionCommand);
        button.setBorder(border);
        button.setBounds(x, y, width, height);
        button.addActionListener(this.controllerClients);
        this.contentPane.add(button);
    }
    public void prepareVariablePanel()
    {
        this.variablesPanel.setBounds(220, 130, 600, 400);
        this.variablesPanel.setBackground(backgroundColor);
        this.prepareSetOfVariables("Id:", idTextField, 0);
        this.prepareSetOfVariables("Name:", nameTextField, 60);
        this.prepareSetOfVariables("Address:", addressTextField, 120);
        this.prepareSetOfVariables("Email:", emailTextField, 180);
        this.prepareSetOfVariables("Age:", ageTextField, 240);
        this.contentPane.add(variablesPanel);
    }
    public void prepareSetOfVariables(String labelString, JTextField textField, int height)
    {
        JLabel label = new JLabel(labelString);
        label.setBounds(50, 10 + height, 100, 30);
        label.setFont(new Font("Mono-type Corsica", Font.BOLD, 20));
        label.setBackground(backgroundColor);
        label.setForeground(Color.BLACK);
        this.variablesPanel.add(label);
        textField.setFont(new Font("Mono-type Corsica", Font.BOLD, 16));
        textField.setForeground(Color.BLACK);
        textField.setBorder(border);
        textField.setBounds(200, 10 + height, 300, 30);
        textField.setHorizontalAlignment(JTextField.CENTER);
        this.variablesPanel.add(textField);
    }
    public Integer getIdTextField() {return Integer.parseInt(idTextField.getText());}
    public String getNameTextField() {
        return nameTextField.getText();
    }
    public String getAddressTextField() {
        return addressTextField.getText();
    }
    public String getEmailTextField() {
        return emailTextField.getText();
    }
    public Integer getAgeTextField() {
        return Integer.parseInt(ageTextField.getText());
    }
    public void setIdTextField(String idTextField) {
        this.idTextField.setText(idTextField);
    }
    public void setNameTextField(String nameTextField) {
        this.nameTextField.setText(nameTextField);
    }
    public void setAddressTextField(String addressTextField) {
        this.addressTextField.setText(addressTextField);
    }
    public void setEmailTextField(String emailTextField) {
        this.emailTextField.setText(emailTextField);
    }
    public void setAgeTextField(String ageTextField) {
        this.ageTextField.setText(ageTextField);
    }
    public String getStringIdTextField() {
        return idTextField.getText();
    }
    public String getStringAgeTextField() {
        return ageTextField.getText();
    }
}