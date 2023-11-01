package ro.tuc.tp.logic;

import ro.tuc.tp.dataAccess.ClientDAO;
import ro.tuc.tp.logic.validators.ClientAgeValidator;
import ro.tuc.tp.logic.validators.EmailValidator;
import ro.tuc.tp.logic.validators.Validator;
import ro.tuc.tp.model.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Aceasta clasa implementeaza logica pentru clasa Client.
 */

public class ClientBLL {
    private final List<Validator<Client>> validatorList;
    private final ClientDAO clientDAO;
    public ClientBLL() {
        validatorList = new ArrayList<>();
        validatorList.add(new EmailValidator());
        validatorList.add(new ClientAgeValidator());
        clientDAO = new ClientDAO();
    }
    public Client findClientById(Integer id) {
        return clientDAO.findClientById(id);
    }
    public void insertClient(Client client) {
        clientDAO.insertClient(client);
    }
    public void deleteClient(Client client) {
        clientDAO.deleteClient(client);
    }
    public void updateClient(Client client, String field, Object valueChanged) {
        clientDAO.updateClient(client, field, valueChanged);
    }
    public ArrayList<Client> getAllClients()
    {
        return (ArrayList<Client>) clientDAO.findAll();
    }
    public List<Validator<Client>> getValidatorList() {
        return validatorList;
    }
}