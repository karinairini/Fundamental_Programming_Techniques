package ro.tuc.tp.logic;

import ro.tuc.tp.dataAccess.ProductDAO;
import ro.tuc.tp.logic.validators.ProductQuantityValidator;
import ro.tuc.tp.logic.validators.Validator;
import ro.tuc.tp.model.Product;

import java.util.ArrayList;

/**
 * Aceasta clasa implementeaza logica pentru clasa Product.
 */

public class ProductBLL {
    private final Validator<Product> validator;
    private final ProductDAO productDAO;
    public ProductBLL() {
        validator = new ProductQuantityValidator();
        productDAO = new ProductDAO();
    }
    public Product findProductById(Integer id) {
        return productDAO.findProductById(id);
    }
    public void insertProduct(Product product) {
        productDAO.insertProduct(product);
    }
    public void deleteProduct(Product product) {
        productDAO.deleteProduct(product);
    }
    public void updateProduct(Product product, String field, Object valueChanged) {
        if(field.equals("quantity")) {
            validator.validate(String.valueOf(valueChanged));
        }
       productDAO.updateProduct(product, field, valueChanged);
    }
    public ArrayList<Product> getAllProducts() {
        return (ArrayList<Product>) productDAO.findAll();
    }
    public Validator<Product> getValidator() {
        return validator;
    }
}