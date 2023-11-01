package ro.tuc.tp.model;

/**
 * Aceasta este o clasa imutabila Bill, definita cu ajutorul Java records.
 * @param idOrder
 * @param idClient
 * @param totalAmount
 */
public record Bill(int idOrder, int idClient, double totalAmount) {
}