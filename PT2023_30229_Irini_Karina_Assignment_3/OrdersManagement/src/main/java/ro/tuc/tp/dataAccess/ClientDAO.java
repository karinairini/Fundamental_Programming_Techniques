package ro.tuc.tp.dataAccess;

import ro.tuc.tp.model.Client;
import java.util.List;

/**
 * Aceasta clasa extinde clasa AbstractDAO pe tipul de obiect Client si implementeaza actiunile
 * corespunzatoare acestuia.
 */

public class ClientDAO extends AbstractDAO<Client> {
    public Client findClientById(Integer clientId) {
        return super.findById(clientId);
    }
    public void insertClient(Client client) {
        super.insert(client);
    }
    public void deleteClient(Client client) {
        super.delete(client.getId());
    }
    public void updateClient(Client client, String field, Object valueChanged) {
        super.update(client, client.getId(), field, valueChanged);
    }
    public List<Client> findAll() {
        return super.findAll();
    }
}