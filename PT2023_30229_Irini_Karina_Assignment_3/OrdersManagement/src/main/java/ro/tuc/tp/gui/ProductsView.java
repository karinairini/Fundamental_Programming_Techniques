package ro.tuc.tp.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Aceasta clasa implementeaza fereasta corespunzatoare actiunilor posibile pe baza de date a produselor.
 */

public class ProductsView extends JFrame {
    private JPanel contentPane;
    private JPanel variablesPanel;
    private final JTextField idTextField = new JTextField();
    private final JTextField nameTextField = new JTextField();
    private final JTextField quantityTextField = new JTextField();
    private final JTextField priceTextField = new JTextField();
    private final Color backgroundColor =  new Color(173,216,230);
    private final Color buttonColor =  new Color(255,165,0);
    private final Border border = BorderFactory.createLineBorder(Color.WHITE);
    ControllerProducts controllerProducts = new ControllerProducts(this);
    public ProductsView(String title) {
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
        JLabel productsLabel = new JLabel("PRODUCTS");
        productsLabel.setFont(new Font("Mono-type Corsica", Font.BOLD, 30));
        productsLabel.setBounds(330, 0, 200, 50);
        productsLabel.setBackground(backgroundColor);
        productsLabel.setForeground(Color.BLACK);
        this.contentPane.add(productsLabel);
    }
    public void prepareButtons() {
        this.prepareButton("Insert Product", 20, 70, 200, 70);
        this.prepareButton("Delete Product", 20, 170, 200, 70);
        this.prepareButton("Edit Product", 20, 270, 200, 70);
        this.prepareButton("View All Products",20, 370, 200, 70);
        this.prepareButton("Find Product By Id",20, 470, 200, 70);
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
        button.addActionListener(this.controllerProducts);
        this.contentPane.add(button);
    }
    public void prepareVariablePanel() {
        this.variablesPanel.setBounds(220, 140, 600, 300);
        this.variablesPanel.setBackground(backgroundColor);
        this.prepareSetOfVariables("Id:", idTextField, 0);
        this.prepareSetOfVariables("Name:", nameTextField, 60);
        this.prepareSetOfVariables("Quantity:", quantityTextField, 120);
        this.prepareSetOfVariables("Price:", priceTextField, 180);
        this.contentPane.add(variablesPanel);
    }
    public void prepareSetOfVariables(String labelString, JTextField textField, int height) {
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
    public Integer getQuantityTextField() {
        return Integer.parseInt(quantityTextField.getText());
    }
    public Double getPriceTextField() {
        return Double.parseDouble(priceTextField.getText());
    }
    public void setIdTextField(String idTextField) {this.idTextField.setText(idTextField);}
    public void setNameTextField(String nameTextField) {
        this.nameTextField.setText(nameTextField);
    }
    public void setQuantityTextField(String quantityTextField) {
        this.quantityTextField.setText(quantityTextField);
    }
    public void setPriceTextField(String priceTextField) {
        this.priceTextField.setText(priceTextField);
    }
    public String getStringIdTextField() {
        return idTextField.getText();
    }
    public String getStringQuantityTextField() {return quantityTextField.getText();}
    public String getStringPriceTextField() {
        return priceTextField.getText();
    }
}