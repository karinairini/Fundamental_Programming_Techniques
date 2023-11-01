package ro.tuc.tp.model;

import ro.tuc.tp.logic.ProductBLL;

/**
 * Aceasta clasa defineste tipul de obiect Command cu atributele id, idClient, idProduct, quantity, total.
 */

public class Command {
    private int id;
    private int idClient;
    private int idProduct;
    private int quantity;
    private double total;
    public Command() {
        super();
    }
    public Command(int idClient, int idProduct, int quantity) {
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.quantity = quantity;
        ProductBLL productBLL = new ProductBLL();
        Product product = productBLL.findProductById(this.idProduct);
        this.total = quantity * product.getPrice();
    }
    public Command(int id, int idClient, int idProduct, int quantity) {
        this.id = id;
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.quantity = quantity;
        ProductBLL productBLL = new ProductBLL();
        Product product = productBLL.findProductById(this.idProduct);
        this.total = quantity * product.getPrice();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getIdClient() {return idClient;}
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
    public int getIdProduct() {
        return idProduct;
    }
    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getTotal() {return total;}
    public void setTotal(double total) {this.total = total;}
    @Override
    public String toString() {
        return "Command{" + "id=" + id + ", idClient=" + idClient + ", idProduct=" + idProduct + ", quantity=" + quantity + ", total=" + total + '}';
    }
    public void updateTotal() {
        ProductBLL productBLL = new ProductBLL();
        Product product = productBLL.findProductById(this.idProduct);
        this.total = product.getPrice() * this.quantity;
    }
}