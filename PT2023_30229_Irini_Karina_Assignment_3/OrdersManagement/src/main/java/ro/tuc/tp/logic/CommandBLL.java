package ro.tuc.tp.logic;

import ro.tuc.tp.dataAccess.CommandDAO;
import ro.tuc.tp.logic.validators.CommandQuantityValidator;
import ro.tuc.tp.logic.validators.Validator;
import ro.tuc.tp.model.Command;
import ro.tuc.tp.model.Product;

import java.util.ArrayList;

/**
 * Aceasta clasa implementeaza logica pentru clasa Command.
 */

public class CommandBLL {
    private final Validator<Command> validatorCommand;
    private final CommandDAO commandDAO;
    public CommandBLL() {
        validatorCommand = new CommandQuantityValidator();
        commandDAO = new CommandDAO();
    }
    public Command findCommandById(Integer id) {
        return commandDAO.findCommandById(id);
    }

    /**
     * Atunci cand se inregistreaza o comanda, cantitatea produsului trebuie sa scada cu cantitatea
     * ceruta in comanda. Acest produs trebuie actualizat conform valorii noi de cantitate.
     * @param command
     */
    public void insertCommand(Command command) {
        ProductBLL productBLL = new ProductBLL();
        Product product = productBLL.findProductById(command.getIdProduct());
        int currentQuantity = product.getQuantity();
        product.setQuantity(currentQuantity - command.getQuantity());
        productBLL.updateProduct(product, "quantity", product.getQuantity());
        commandDAO.insertCommand(command);
    }

    /**
     * Atunci cand se sterge o inregistrare de comanda, cantitatea produsului trebuie sa creasca cu cantitatea
     * ceruta in comanda. Acest produs trebuie actualizat conform valorii noi de cantitate.
     * @param command
     */
    public void deleteCommand(Command command) {
        ProductBLL productBLL = new ProductBLL();
        Product product = productBLL.findProductById(command.getIdProduct());
        int currentQuantity = product.getQuantity();
        product.setQuantity(currentQuantity + command.getQuantity());
        productBLL.updateProduct(product, "quantity", product.getQuantity());
        commandDAO.deleteCommand(command);
    }

    /**
     * Aceasta metoda actualizeaza campul field cu valoarea valueChanged din inregistrarea de comanda.
     * In cazul in care se editeaza cantitatea comenzii, se asigura actualizarea aferenta a cantitatii
     * produsului.
     * @param command
     * @param field
     * @param valueChanged
     */
    public void updateCommand(Command command, String field, Object valueChanged) {
        if(field.equals("quantity")) {
            ProductBLL productBLL = new ProductBLL();
            Product product = productBLL.findProductById(command.getIdProduct());
            int currentQuantityProduct = product.getQuantity();
            int currentQuantityCommand = command.getQuantity();
            Integer currentQuantity = (Integer) valueChanged;
            if(currentQuantity < currentQuantityCommand) {
                product.setQuantity(currentQuantityProduct + (currentQuantityCommand - currentQuantity));
            }
            else {
                product.setQuantity(currentQuantityProduct - (currentQuantity - currentQuantityCommand));
            }
            command.setQuantity(currentQuantity);
            command.updateTotal();
            commandDAO.updateCommand(command, "total", command.getTotal());
            productBLL.updateProduct(product, "quantity", product.getQuantity());
        }
        commandDAO.updateCommand(command, field, valueChanged);
    }
    public ArrayList<Command> getAllCommands() {
        return (ArrayList<Command>) commandDAO.findAll();
    }
    public Validator<Command> getValidatorCommand() {
        return validatorCommand;
    }
}