package ro.tuc.tp.gui;

import ro.tuc.tp.logic.BillBLL;
import ro.tuc.tp.logic.CommandBLL;
import ro.tuc.tp.logic.ProductBLL;
import ro.tuc.tp.model.Bill;
import ro.tuc.tp.model.Command;
import ro.tuc.tp.model.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Aceasta clasa se ocupa de interactiunile din fereastra de gestionare a comenzilor.
 * Se poate alege actiunea dorita, din sase optiuni fiecare intuitiva dupa numele butoanelor.
 */

public class ControllerCommands implements ActionListener {
    CommandsView commandsView;
    Command command;
    CommandBLL commandBLL;
    ProductBLL productBLL;
    public ControllerCommands(CommandsView commandsView) {
        this.commandsView = commandsView;
        commandBLL = new CommandBLL();
        productBLL = new ProductBLL();
    }
    public int validInputValues(String command) {
        if (command.equals("Order")) {
            if (commandsView.getStringIdClientTextField().equals("") || commandsView.getStringIdProductTextField().equals("") || commandsView.getStringQuantityTextField().equals("")) {
                return 0;
            }
        } else if (command.equals("Edit Command")) {
            if (commandsView.getStringIdTextField().equals("") || commandsView.getStringIdClientTextField().equals("") || commandsView.getStringIdProductTextField().equals("") || commandsView.getStringQuantityTextField().equals("")) {
                return 0;
            }
        } else {
            if (commandsView.getStringIdTextField().equals("")) {
                return 0;
            }
        }
        return 1;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String commandString = e.getActionCommand();
        int validInputValues;
        switch (commandString) {
            case "Order" -> {
                validInputValues = validInputValues(commandString);
                if(validInputValues == 1) {
                    command = new Command(commandsView.getIdClientTextField(), commandsView.getIdProductTextField(), commandsView.getQuantityTextField());
                    if (commandBLL.getValidatorCommand().validate(command)) {
                        commandBLL.insertCommand(command);
                        ArrayList<Product> products = productBLL.getAllProducts();
                        commandsView.prepareProductsTable(products);
                        ArrayList<Command> commands = commandBLL.getAllCommands();
                        commandsView.prepareCommandsTable(commands);
                        int idOrder = commands.get(commands.size()-1).getId();
                        Bill bill = new Bill(idOrder++, command.getIdClient(), command.getTotal());
                        BillBLL billBLL = new BillBLL();
                        billBLL.insertBill(bill);
                        JOptionPane.showMessageDialog(commandsView.getContentPane(), "The command has been registered!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(commandsView.getContentPane(), "The product is under-stocked! Cannot place command!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(commandsView.getContentPane(), "Invalid input values!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            case "Find Command By Id" -> {
                validInputValues = validInputValues(commandString);
                if (validInputValues == 1) {
                    command = commandBLL.findCommandById(commandsView.getIdTextField());
                    if (command != null) {
                        commandsView.setIdClientTextField(Integer.toString(command.getIdClient()));
                        commandsView.setIdProductTextField(Integer.toString(command.getIdProduct()));
                        commandsView.setQuantityTextField(Integer.toString(command.getQuantity()));
                    } else {
                        JOptionPane.showMessageDialog(commandsView.getContentPane(), "The command with id " + commandsView.getIdTextField() + " doesn't exist in the database!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(commandsView.getContentPane(), "Invalid id!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            case "Edit Command" -> {
                validInputValues = validInputValues("Edit Command");
                if (validInputValues == 1) {
                    command = commandBLL.findCommandById(commandsView.getIdTextField());
                    int alrightUpdate = 2;
                    if (command != null) {
                        if (command.getIdClient() != commandsView.getIdClientTextField()) {
                            alrightUpdate = 1;
                            commandBLL.updateCommand(command, "idClient", commandsView.getIdClientTextField());
                        }
                        if (command.getIdProduct() != commandsView.getIdProductTextField()) {
                            alrightUpdate = 1;
                            commandBLL.updateCommand(command, "idProduct", commandsView.getIdProductTextField());
                        }
                        if (command.getQuantity() != commandsView.getQuantityTextField()) {
                            Command newCommand = new Command(commandsView.getIdTextField(), commandsView.getIdClientTextField(), commandsView.getIdProductTextField(), commandsView.getQuantityTextField());
                            if (commandBLL.getValidatorCommand().validate(newCommand)) {
                                alrightUpdate = 1;
                                commandBLL.updateCommand(command, "quantity", newCommand.getQuantity());
                                ArrayList<Product> products = productBLL.getAllProducts();
                                commandsView.prepareProductsTable(products);
                            } else {
                                alrightUpdate = 0;
                                JOptionPane.showMessageDialog(commandsView.getContentPane(), "The product is under-stocked! Cannot place command!", "Information", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        if (alrightUpdate == 1) {
                            JOptionPane.showMessageDialog(commandsView.getContentPane(), "The command with id " + command.getId() + " has been updated!", "Information", JOptionPane.INFORMATION_MESSAGE);
                        }
                        if (alrightUpdate != 2) {
                            ArrayList<Command> commands = commandBLL.getAllCommands();
                            commandsView.prepareCommandsTable(commands);
                        }
                    } else {
                        JOptionPane.showMessageDialog(commandsView.getContentPane(), "The command with id " + commandsView.getIdTextField() + " doesn't exist in the database!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(commandsView.getContentPane(), "Invalid input values!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            case "Delete Command" -> {
                validInputValues = validInputValues("Delete Command");
                if (validInputValues == 1) {
                    command = commandBLL.findCommandById(commandsView.getIdTextField());
                    if (command != null) {
                        commandBLL.deleteCommand(command);
                        ArrayList<Product> products = productBLL.getAllProducts();
                        commandsView.prepareProductsTable(products);
                        ArrayList<Command> commands = commandBLL.getAllCommands();
                        commandsView.prepareCommandsTable(commands);
                        JOptionPane.showMessageDialog(commandsView.getContentPane(), "The command with id " + commandsView.getIdTextField() + " has been deleted!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(commandsView.getContentPane(), "The command with id " + commandsView.getIdTextField() + " doesn't exist in the database!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(commandsView.getContentPane(), "Invalid id!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}