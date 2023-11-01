package ro.tuc.tp.logic.validators;

/**
 * Aceasta interfata furnizeaza metodele de validare folosite pentru tipurile de obiect.
 * Aceste metode vor fi implementate corespunzator fiecarui tip de obiect.
 * @param <T>
 */

public interface Validator<T> {
    public boolean validate(T t);
    public boolean validate(String string);
}
