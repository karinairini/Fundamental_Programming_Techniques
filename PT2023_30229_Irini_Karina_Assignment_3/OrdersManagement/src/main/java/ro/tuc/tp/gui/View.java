package ro.tuc.tp.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Aceasta clasa implementeaza fereastra principala a aplicatiei.
 */

public class View extends JFrame {
    private JPanel contentPane;
    private final Color backgroundColor =  new Color(173,216,230);
    private final Color buttonColor = new Color(255,165,0);
    private final Border border = BorderFactory.createLineBorder(Color.WHITE);
    Controller controller = new Controller(this);
    public View(String title) {
        super(title);
        this.prepareGUI();
    }
    public void prepareGUI() {
        this.setSize(600, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
        this.prepareContentPane();
        this.setContentPane(contentPane);
    }
    public void prepareContentPane() {
        this.contentPane = new JPanel();
        this.contentPane.setLayout(null);
        this.contentPane.setBackground(backgroundColor);
        this.prepareLabel("HELLO, EMPLOYEE!", 28, 155, 10, 300, 50);
        this.prepareLabel("SELECT ONE:", 25, 200, 80, 200, 50);
        this.prepareButtons();
    }
    public void prepareLabel(String labelString, int fontSize, int x, int y, int width, int height) {
        JLabel label = new JLabel(labelString, JLabel.CENTER);
        label.setFont(new Font("Mono-type Corsica", Font.BOLD, fontSize));
        label.setBackground(backgroundColor);
        label.setForeground(Color.BLACK);
        label.setBounds(x, y, width, height);
        this.contentPane.add(label);
    }
    public void prepareButtons() {
        this.prepareButton("CLIENTS", 205, 150, 200, 70);
        this.prepareButton("PRODUCTS", 205, 250, 200, 70);
        this.prepareButton("COMMANDS", 205, 350, 200, 70);
        this.prepareButton("VIEW BILLS", 205, 450, 200, 70);
    }
    public void prepareButton(String actionCommand, int x, int y, int width, int height) {
        JButton button = new JButton(actionCommand);
        button.setFocusable(false);
        button.setBackground(buttonColor);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Mono-type Corsica", Font.BOLD, 25));
        button.setActionCommand(actionCommand);
        button.setBorder(border);
        button.setBounds(x, y, width, height);
        button.addActionListener(this.controller);
        this.contentPane.add(button);
    }
}