package ro.tuc.tp.logic.validators;

import ro.tuc.tp.logic.ProductBLL;
import ro.tuc.tp.model.Command;
import ro.tuc.tp.model.Product;

/**
 * Aceasta clasa implementeaza interfata Validator cu tipul de obiect Command.
 * Se verifica daca cantitatea comenzii care urmeaza sa fie inregistrata este mai mai mare sau mai mica
 * decat cantitatea disponibila a produsului.
 * Se returneaza true sau false pentru a putea gestiona mai departe situatia in fereasta grafica
 * asociata comenzilor.
 */

public class CommandQuantityValidator implements Validator<Command>{
    @Override
    public boolean validate(Command command) {
        ProductBLL productBLL = new ProductBLL();
        Product product = productBLL.findProductById(command.getIdProduct());
        return command.getQuantity() <= product.getQuantity() && command.getQuantity() > 0;
    }
    @Override
    public boolean validate(String string) {
        return false;
    }
}