package ro.tuc.tp.gui;

import ro.tuc.tp.logic.ClientBLL;
import ro.tuc.tp.logic.validators.Validator;
import ro.tuc.tp.model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Aceasta clasa se ocupa de interactiunile din fereastra de gestionare a clientilor.
 * Se poate alege actiunea dorita, din sase optiuni fiecare intuitiva dupa numele butoanelor.
 */

public class ControllerClients implements ActionListener {
    ClientsView clientsView;
    Client client;
    ClientBLL clientBLL;
    Validator<Client> validatorAge;
    Validator<Client> validatorEmail;
    public ControllerClients(ClientsView clientsView) {
        this.clientsView = clientsView;
        clientBLL = new ClientBLL();
        validatorAge = clientBLL.getValidatorList().get(1);
        validatorEmail = clientBLL.getValidatorList().get(0);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        int validInputValues = 1;
        switch (command) {
            case "Insert Client" -> {
                if (clientsView.getNameTextField().equals("") || clientsView.getAddressTextField().equals("") || clientsView.getEmailTextField().equals("") || clientsView.getStringAgeTextField().equals("")) {
                    validInputValues = 0;
                    JOptionPane.showMessageDialog(clientsView.getContentPane(), "Invalid input values!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            case "Edit Client" -> {
                if (clientsView.getStringIdTextField().equals("") || clientsView.getNameTextField().equals("") || clientsView.getAddressTextField().equals("") || clientsView.getEmailTextField().equals("") || clientsView.getStringAgeTextField().equals("")) {
                    validInputValues = 0;
                    JOptionPane.showMessageDialog(clientsView.getContentPane(), "Invalid input values!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            case "Find Client By Id", "Delete Client" -> {
                if (clientsView.getStringIdTextField().equals("")) {
                    validInputValues = 0;
                    JOptionPane.showMessageDialog(clientsView.getContentPane(), "Invalid id!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (validInputValues == 1) {
            switch (command) {
                case "Insert Client" -> {
                    client = new Client(clientsView.getNameTextField(), clientsView.getAddressTextField(), clientsView.getEmailTextField(), clientsView.getAgeTextField());
                    if (!validatorAge.validate(client)) {
                        JOptionPane.showMessageDialog(clientsView.getContentPane(), "The client age needs to be between 18 and 65!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (!validatorEmail.validate(client)) {
                        JOptionPane.showMessageDialog(clientsView.getContentPane(), "Incorrect email format!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        clientBLL.insertClient(client);
                        JOptionPane.showMessageDialog(clientsView.getContentPane(), "The client has been inserted!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                case "Delete Client" -> {
                    client = clientBLL.findClientById(clientsView.getIdTextField());
                    if (client != null) {
                        clientBLL.deleteClient(client);
                        JOptionPane.showMessageDialog(clientsView.getContentPane(), "The client with id " + client.getId() + " has been deleted!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(clientsView.getContentPane(), "The client with id " + clientsView.getIdTextField() + " doesn't exist in the database!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                case "Edit Client" -> {
                    int alrightUpdate = 2;
                    client = clientBLL.findClientById(clientsView.getIdTextField());
                    if (client != null) {
                        if (!client.getName().equals(clientsView.getNameTextField())) {
                            alrightUpdate = 1;
                            clientBLL.updateClient(client, "name", clientsView.getNameTextField());
                        }
                        if (!client.getAddress().equals(clientsView.getAddressTextField())) {
                            alrightUpdate = 1;
                            clientBLL.updateClient(client, "address", clientsView.getAddressTextField());
                        }
                        if (!client.getEmail().equals(clientsView.getEmailTextField())) {
                            if (!validatorEmail.validate(clientsView.getEmailTextField())) {
                                alrightUpdate = 0;
                                JOptionPane.showMessageDialog(clientsView.getContentPane(), "Incorrect email format!", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                alrightUpdate = 1;
                                clientBLL.updateClient(client, "email", clientsView.getEmailTextField());
                            }
                        }
                        if (client.getAge() != clientsView.getAgeTextField()) {
                            if (!validatorAge.validate(Integer.toString(clientsView.getAgeTextField()))) {
                                alrightUpdate = 0;
                                JOptionPane.showMessageDialog(clientsView.getContentPane(), "The client age needs to be between 18 and 65!", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                alrightUpdate = 1;
                                clientBLL.updateClient(client, "age", clientsView.getAgeTextField());
                            }
                        }
                        if (alrightUpdate == 1) {
                            JOptionPane.showMessageDialog(clientsView.getContentPane(), "The client with id " + client.getId() + " has been updated!", "Information", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(clientsView.getContentPane(), "The client with id " + clientsView.getIdTextField() + " doesn't exist in the database!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                case "View All Clients" -> {
                    JFrame frame = new JFrame("CLIENTS");
                    frame.setSize(900, 500);
                    frame.setBackground(new Color(216, 191, 216));
                    frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - frame.getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - frame.getSize().height) / 2);
                    ArrayList<Object> arrayList = new TableGenerator<Client>().generateTable(clientBLL.getAllClients(), new Color(216,191,216), new Color(255,240,245), "panelClients");
                    frame.setContentPane((Container) arrayList.get(0));
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    frame.setVisible(true);
                }
                case "Find Client By Id" -> {
                    client = clientBLL.findClientById(clientsView.getIdTextField());
                    if (client != null) {
                        clientsView.setNameTextField(client.getName());
                        clientsView.setAddressTextField(client.getAddress());
                        clientsView.setEmailTextField(client.getEmail());
                        clientsView.setAgeTextField(Integer.toString(client.getAge()));
                    } else {
                        JOptionPane.showMessageDialog(clientsView.getContentPane(), "The client with id " + clientsView.getIdTextField() + " doesn't exist in the database!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                case "Clear Fields" -> {
                    clientsView.setIdTextField("");
                    clientsView.setNameTextField("");
                    clientsView.setAddressTextField("");
                    clientsView.setEmailTextField("");
                    clientsView.setAgeTextField("");
                }
            }
        }
    }
}