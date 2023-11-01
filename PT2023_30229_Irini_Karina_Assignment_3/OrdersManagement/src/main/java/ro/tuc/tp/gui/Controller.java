package ro.tuc.tp.gui;

import ro.tuc.tp.logic.BillBLL;
import ro.tuc.tp.model.Bill;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Aceasta clasa se ocupa de interactiunile din fereastra principala a aplicatiei.
 * In aceasta se pot alege trei butoane, pentru fiecare tip de obiect definit in proiect.
 * In functie de cel apasat, se deschide fereasta aferenta.
 */

public class Controller implements ActionListener {
    private final View view;
    public Controller(View view)
    {
        this.view = view;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        JFrame frame;
        switch (command) {
            case "CLIENTS" -> frame = new ClientsView("CLIENTS");
            case "PRODUCTS" -> frame = new ProductsView("PRODUCTS");
            case "COMMANDS" -> frame = new CommandsView("COMMANDS");
            default -> {
                frame = new JFrame("BILLS");
                frame.setSize(900, 500);
                frame.setBackground(new Color(216, 191, 216));
                frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - frame.getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - frame.getSize().height) / 2);
                BillBLL billBLL = new BillBLL();
                ArrayList<Object> arrayList = new TableGenerator<Bill>().generateTable(billBLL.getAllBills(), new Color(216, 191, 216), new Color(255, 240, 245), "panelBills");
                frame.setContentPane((Container) arrayList.get(0));
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        }
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}