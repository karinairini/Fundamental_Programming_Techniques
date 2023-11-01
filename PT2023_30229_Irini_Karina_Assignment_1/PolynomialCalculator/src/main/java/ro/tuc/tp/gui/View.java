package ro.tuc.tp.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class View extends JFrame {
    private JPanel contentPane ;
    private JPanel polynomialsPanel;
    private JPanel resultPanel;
    private JPanel operationsPanel;
    private JTextField firstPolynomialTextField;
    private JTextField secondPolynomialTextField;
    private JLabel resultPolynomialLabel;
    private ButtonGroup buttonGroup;
    private final Color backgroundColorContentPane = new Color(228, 209, 185);
    private final Color backgroundColorPolynomialsPanel = new Color(190, 140, 99);
    private final Color backgroundColorResultPanel = new Color(143, 189, 211);
    private final Border border = BorderFactory.createLineBorder(Color.WHITE);
    Controller controller = new Controller(this);
    public View(String title)
    {
        super(title);
        this.prepareGui();
    }
    public void prepareGui()
    {
        this.setSize(900,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
        this.contentPane = new JPanel();
        this.prepareContentPane();
        this.preparePolynomialsPanel();
        this.prepareResultPanel();
        this.prepareOperationsPanel();
        this.setContentPane(contentPane);
    }
    public void prepareContentPane()
    {
        this.contentPane.setLayout(null);
        this.contentPane.setBackground(backgroundColorContentPane);
        JLabel contentPaneTitle = new JLabel("POLYNOMIAL CALCULATOR");
        contentPaneTitle.setFont(new Font("Mono-type Corsica", Font.BOLD, 25));
        contentPaneTitle.setForeground(Color.BLACK);
        contentPaneTitle.setBounds(250,10,500,50);
        this.contentPane.add(contentPaneTitle);
        JButton computeButton = new JButton("COMPUTE");
        computeButton.setBounds(280,495,300,50);
        computeButton.setFont(new Font("Mono-type Corsica", Font.BOLD, 25));
        computeButton.setForeground(Color.BLACK);
        computeButton.setBackground(backgroundColorResultPanel);
        computeButton.setBorder(BorderFactory.createLineBorder(backgroundColorResultPanel));
        computeButton.setFocusable(false);
        computeButton.setActionCommand("COMPUTE");
        computeButton.addActionListener(this.controller);
        this.contentPane.add(computeButton);
    }
    public void prepareFirstPolynomial()
    {
        JLabel firstPolynomialLabel = new JLabel("FIRST POLYNOMIAL:", JLabel.CENTER);
        firstPolynomialLabel.setFont(new Font("Mono-type Corsica", Font.BOLD, 20));
        firstPolynomialLabel.setForeground(Color.BLACK);
        firstPolynomialLabel.setBounds(50,20,250,50);
        this.polynomialsPanel.add(firstPolynomialLabel);
        this.firstPolynomialTextField = new JTextField();
        this.firstPolynomialTextField.setBounds(325,25,440,40);
        this.firstPolynomialTextField.setFont(new Font("Mono-type Corsica", Font.BOLD, 18));
        this.firstPolynomialTextField.setForeground(Color.BLACK);
        this.firstPolynomialTextField.setBorder(border);
        this.firstPolynomialTextField.setHorizontalAlignment(JTextField.CENTER);
        this.polynomialsPanel.add(this.firstPolynomialTextField);
    }
    public void prepareSecondPolynomial()
    {
        JLabel secondPolynomialLabel = new JLabel("SECOND POLYNOMIAL:", JLabel.CENTER);
        secondPolynomialLabel.setFont(new Font("Mono-type Corsica", Font.BOLD, 20));
        secondPolynomialLabel.setForeground(Color.BLACK);
        secondPolynomialLabel.setBounds(30,110,290,50);
        this.polynomialsPanel.add(secondPolynomialLabel);
        this.secondPolynomialTextField = new JTextField();
        this.secondPolynomialTextField.setBounds(325,115,440,40);
        this.secondPolynomialTextField.setFont(new Font("Mono-type Corsica", Font.BOLD, 18));
        this.secondPolynomialTextField.setForeground(Color.BLACK);
        this.secondPolynomialTextField.setBorder(border);
        this.secondPolynomialTextField.setHorizontalAlignment(JTextField.CENTER);
        this.polynomialsPanel.add(this.secondPolynomialTextField);
    }
    public void preparePolynomialsPanel()
    {
        this.polynomialsPanel = new JPanel();
        this.polynomialsPanel.setBounds(40, 70, 800,190);
        this.polynomialsPanel.setBackground(backgroundColorPolynomialsPanel);
        this.polynomialsPanel.setLayout(null);
        this.prepareFirstPolynomial();
        this.prepareSecondPolynomial();
        this.contentPane.add(this.polynomialsPanel);
    }
    public void prepareResult()
    {
        JLabel resultLabel = new JLabel("RESULT:", JLabel.CENTER);
        resultLabel.setFont(new Font("Mono-type Corsica", Font.BOLD, 20));
        resultLabel.setForeground(Color.BLACK);
        resultLabel.setSize(290,60);
        this.resultPanel.add(resultLabel);
        this.resultPolynomialLabel = new JLabel("", JLabel.CENTER);
        this.resultPolynomialLabel.setFont(new Font("Mono-type Corsica", Font.BOLD, 18));
        this.resultPolynomialLabel.setForeground(Color.BLACK);
        this.resultPolynomialLabel.setBackground(Color.WHITE);
        this.resultPolynomialLabel.setOpaque(true);
        this.resultPolynomialLabel.setBounds(215,15,570,40);
        this.resultPanel.add(this.resultPolynomialLabel);
    }
    public void prepareResultPanel()
    {
        this.resultPanel = new JPanel();
        this.resultPanel.setBounds(40,260,800,70);
        this.resultPanel.setBackground(backgroundColorResultPanel);
        this.prepareResult();
        this.resultPanel.setLayout(null);
        this.contentPane.add(this.resultPanel);
    }
    public void initializeRadioButton(String nameRadioButton)
    {
        JRadioButton radioButton = new JRadioButton(nameRadioButton, false);
        radioButton.setFocusable(false);
        radioButton.setFont(new Font("Mono-type Corsica", Font.BOLD, 20));
        radioButton.setForeground(Color.BLACK);
        radioButton.setBackground(backgroundColorContentPane);
        radioButton.setActionCommand(nameRadioButton);
        this.buttonGroup.add(radioButton);
        this.operationsPanel.add(radioButton);
    }
    public void prepareOperations()
    {
        this.buttonGroup = new ButtonGroup();
        this.initializeRadioButton("ADD");
        this.initializeRadioButton("SUBTRACT");
        this.initializeRadioButton("MULTIPLY");
        this.initializeRadioButton("DIVIDE");
        this.initializeRadioButton("DERIVE");
        this.initializeRadioButton("INTEGRATE");
    }
    public void prepareOperationsPanel()
    {
        this.operationsPanel = new JPanel(new GridLayout(2,3));
        this.operationsPanel.setBounds(130,340,700,150);
        this.operationsPanel.setBackground(backgroundColorContentPane);
        this.prepareOperations();
        this.contentPane.add(this.operationsPanel);
    }
    public JTextField getFirstPolynomialTextField()
    {
        if(this.firstPolynomialTextField.getText().equals(""))
            JOptionPane.showMessageDialog(this.contentPane, "Please enter the first polynomial!", "ERROR", JOptionPane.ERROR_MESSAGE);
        else
            return firstPolynomialTextField;
        return null;
    }
    public JTextField getSecondPolynomialTextField()
    {
        if(this.secondPolynomialTextField.getText().equals(""))
            JOptionPane.showMessageDialog(this.contentPane, "Please enter the second polynomial!", "ERROR", JOptionPane.ERROR_MESSAGE);
        else
            return secondPolynomialTextField;
        return null;
    }
    public String getOperationRadioButton()
    {
        if(buttonGroup.getSelection() == null)
          JOptionPane.showMessageDialog(this.contentPane, "Please select the operation!", "WARNING", JOptionPane.WARNING_MESSAGE);
        else
            return buttonGroup.getSelection().getActionCommand();
        return null;
    }
    public JLabel getResultPolynomialLabel() {
        return resultPolynomialLabel;
    }
}