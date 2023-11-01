package ro.tuc.tp.gui;

import ro.tuc.tp.logic.ClientBLL;
import ro.tuc.tp.logic.CommandBLL;
import ro.tuc.tp.logic.ProductBLL;
import ro.tuc.tp.model.Client;
import ro.tuc.tp.model.Command;
import ro.tuc.tp.model.Product;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

/**
 * Aceasta clasa implementeaza fereasta corespunzatoare actiunilor posibile pe baza de date a comenzilor.
 */

public class CommandsView extends JFrame {
    private JPanel contentPane;
    private JPanel variablesPanel;
    private final JTextField idTextField = new JTextField();
    private final JTextField idClientTextField = new JTextField();
    private final JTextField idProductTextField = new JTextField();
    private final JTextField quantityTextField = new JTextField();
    private final Color backgroundColor =  new Color(173,216,230);
    private final Color buttonColor =  new Color(255,165,0);
    private final Border border = BorderFactory.createLineBorder(Color.WHITE);
    private final ClientBLL clientBLL = new ClientBLL();
    private final ProductBLL productBLL = new ProductBLL();
    private final CommandBLL commandBLL = new CommandBLL();
    private final ArrayList<Client> clients = clientBLL.getAllClients();
    private final ArrayList<Product> products = productBLL.getAllProducts();
    private final ArrayList<Command> commands = commandBLL.getAllCommands();
    ControllerCommands controllerCommands = new ControllerCommands(this);
    public CommandsView(String title) {
        super(title);
        this.prepareGUI();
    }
    public void prepareGUI() {
        this.setSize(1200, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
        this.prepareContentPane();
        this.setContentPane(contentPane);
    }
    public void prepareContentPane() {
        this.contentPane = new JPanel();
        this.contentPane.setLayout(null);
        this.contentPane.setBackground(backgroundColor);
        this.prepareLabel("COMMANDS", 25, 530, 0, 200, 50);
        this.prepareLabel("Select a Client", 22, 150, 45, 200, 50);
        this.prepareLabel("Select a Product", 22, 150, 295, 200, 50);
        this.prepareButtons();
        this.prepareVariablePanel();
        this.prepareClientsTable(clients);
        this.prepareProductsTable(products);
        this.prepareCommandsTable(commands);
    }
    public void prepareLabel(String labelString, int fontSize, int x, int y, int width, int height) {
        JLabel label = new JLabel(labelString, JLabel.CENTER);
        label.setFont(new Font("Mono-type Corsica", Font.BOLD, fontSize));
        label.setBounds(x, y, width, height);
        label.setBackground(backgroundColor);
        label.setForeground(Color.BLACK);
        this.contentPane.add(label);
    }
    public void prepareButtons() {
        this.prepareButton("Order",890, 340, 250, 40);
        this.prepareButton("Find Command By Id", 890, 490, 250, 40);
        this.prepareButton("Edit Command", 890, 440, 250, 40);
        this.prepareButton("Delete Command",890, 390, 250, 40);
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
        button.addActionListener(this.controllerCommands);
        this.contentPane.add(button);
    }
    public void prepareVariablePanel() {
        this.variablesPanel = new JPanel();
        this.variablesPanel.setLayout(null);
        this.variablesPanel.setBounds(510, 335, 350, 200);
        this.variablesPanel.setBackground(backgroundColor);
        this.idClientTextField.setEditable(false);
        this.idProductTextField.setEditable(false);
        this.prepareSetOfVariables("Id:", idTextField, 0);
        this.prepareSetOfVariables("IdClient:", idClientTextField, 50);
        this.prepareSetOfVariables("IdProduct:", idProductTextField, 100);
        this.prepareSetOfVariables("Quantity:", quantityTextField, 150);
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
        textField.setBounds(180, 10 + height, 150, 30);
        textField.setHorizontalAlignment(JTextField.CENTER);
        this.variablesPanel.add(textField);
    }
    public void prepareClientsTable(ArrayList<Client> clients) {
        ArrayList<Object> arrayList = new TableGenerator<Client>().generateTable(clients, new Color(216,191,216), new Color(229, 214, 229), "panelClients");
        JPanel panelClients = (JPanel) arrayList.get(0);
        panelClients.setBounds(10, 90, 500, 200);
        this.contentPane.add(panelClients);
        JTable tableClients = (JTable) arrayList.get(1);
        tableClients.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableClients.getSelectedRow();
                if (selectedRow != -1) {
                    Object selectedValue = tableClients.getValueAt(selectedRow, 0);
                    idClientTextField.setText(String.valueOf(selectedValue));
                }
            }
        });
    }
    public void prepareProductsTable(ArrayList<Product> products) {
        ArrayList<Object> arrayList = new TableGenerator<Product>().generateTable(products, new Color(216,191,216), new Color(229, 214, 229), "panelProducts");
        JPanel panelProducts= (JPanel) arrayList.get(0);
        panelProducts.setBounds(10, 340, 500, 200);
        Component[] components = contentPane.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel && component.getName() != null && component.getName().equals("panelProducts")) {
                contentPane.remove(component);
                break;
            }
        }
        panelProducts.setName("panelProducts");
        this.contentPane.add(panelProducts);
        JTable tableProducts = (JTable) arrayList.get(1);
        tableProducts.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableProducts.getSelectedRow();
                if (selectedRow != -1) {
                    Object selectedValue = tableProducts.getValueAt(selectedRow, 0);
                    idProductTextField.setText(String.valueOf(selectedValue));
                }
            }
        });
        this.contentPane.revalidate();
        this.contentPane.repaint();
    }
    public void prepareCommandsTable(ArrayList<Command> commands) {
        ArrayList<Object> arrayList = new TableGenerator<Command>().generateTable(commands, new Color(60,179,113), new Color(106, 206, 148), "panelCommands");
        JPanel panelCommands= (JPanel) arrayList.get(0);
        panelCommands.setBounds(550, 70, 600, 240);
        Component[] components = contentPane.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel && component.getName() != null && component.getName().equals("panelCommands")) {
                contentPane.remove(component);
                break;
            }
        }
        panelCommands.setName("panelCommands");
        this.contentPane.add(panelCommands);
        JTable tableCommands = (JTable) arrayList.get(1);
        tableCommands.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableCommands.getSelectedRow();
                if (selectedRow != -1) {
                    Object selectedValue = tableCommands.getValueAt(selectedRow, 0);
                    idTextField.setText(String.valueOf(selectedValue));
                    selectedValue = tableCommands.getValueAt(selectedRow, 1);
                    idClientTextField.setText(String.valueOf(selectedValue));
                    selectedValue = tableCommands.getValueAt(selectedRow, 2);
                    idProductTextField.setText(String.valueOf(selectedValue));
                    selectedValue = tableCommands.getValueAt(selectedRow, 3);
                    quantityTextField.setText(String.valueOf(selectedValue));
                }
            }
        });
        this.contentPane.revalidate();
        this.contentPane.repaint();
    }
    public Integer getIdTextField() {return Integer.parseInt(idTextField.getText());}
    public Integer getIdClientTextField() {return Integer.parseInt(idClientTextField.getText());}
    public Integer getIdProductTextField() {return Integer.parseInt(idProductTextField.getText());}
    public Integer getQuantityTextField() {return Integer.parseInt(quantityTextField.getText());}
    public void setIdClientTextField(String idClientTextField) {this.idClientTextField.setText(idClientTextField);}
    public void setIdProductTextField(String idProductTextField) {this.idProductTextField.setText(idProductTextField);}
    public void setQuantityTextField(String quantityTextField) {this.quantityTextField.setText(quantityTextField);}
    public String getStringQuantityTextField() {return quantityTextField.getText();}
    public String getStringIdTextField() {return idTextField.getText();}
    public String getStringIdClientTextField() {return idClientTextField.getText();}
    public String getStringIdProductTextField() {return idProductTextField.getText();}
}