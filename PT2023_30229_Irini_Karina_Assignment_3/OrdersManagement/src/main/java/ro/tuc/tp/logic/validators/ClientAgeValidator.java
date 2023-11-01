package ro.tuc.tp.logic.validators;

import ro.tuc.tp.model.Client;

/**
 * Aceasta clasa implementeaza interfata Validator cu tipul de obiect Client.
 * Se verifica daca varsta clientului care urmeaza sa fie inregistrat este intre limitele stabilite.
 * Se returneaza true sau false pentru a putea gestiona mai departe situatia in fereasta grafica
 * asociata clientilor.
 */

public class ClientAgeValidator implements Validator<Client> {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 65;
    public boolean validate(Client client) {
        return client.getAge() >= MIN_AGE && client.getAge() <= MAX_AGE;
    }
    @Override
    public boolean validate(String string) {
        return Integer.parseInt(string) >= MIN_AGE && Integer.parseInt(string) <= MAX_AGE;
    }
}