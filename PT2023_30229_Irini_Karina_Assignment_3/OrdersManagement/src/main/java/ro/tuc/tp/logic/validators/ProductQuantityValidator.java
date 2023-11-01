package ro.tuc.tp.logic.validators;

import ro.tuc.tp.model.Product;

/**
 * Aceasta clasa implementeaza interfata Validator cu tipul de obiect Product.
 * Se asigura ca produsul care urmeaza sa fie inregistrat sa nu fie descris de o cantitate negativa.
 * Se returneaza true sau false pentru a putea gestiona mai departe situatia in fereasta grafica
 * asociata produselor.
 */

public class ProductQuantityValidator implements Validator<Product> {
    @Override
    public boolean validate(Product product) {
        return product.getQuantity() >= 0;
    }
    @Override
    public boolean validate(String string) {
        return Integer.parseInt(string) >= 0;
    }
}