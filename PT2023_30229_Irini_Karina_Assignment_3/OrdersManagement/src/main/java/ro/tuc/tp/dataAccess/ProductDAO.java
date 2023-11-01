package ro.tuc.tp.dataAccess;

import ro.tuc.tp.model.Product;

import java.util.List;

/**
 * Aceasta clasa extinde clasa AbstractDAO pe tipul de obiect Product si implementeaza actiunile
 * corespunzatoare acestuia.
 */

public class ProductDAO extends AbstractDAO<Product> {
    public Product findProductById(int productId) {
        return super.findById(productId);
    }
    public Product insertProduct(Product product) {
        return super.insert(product);
    }
    public void deleteProduct(Product product) {
        super.delete(product.getId());
    }
    public Product updateProduct(Product product, String field, Object valueChanged) {
        return super.update(product, product.getId(), field, valueChanged);
    }
    public List<Product> findAll() {
        return super.findAll();
    }
}
