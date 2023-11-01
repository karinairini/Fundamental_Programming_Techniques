package ro.tuc.tp.gui;

import ro.tuc.tp.logic.ProductBLL;
import ro.tuc.tp.model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Aceasta clasa se ocupa de interactiunile din fereastra de gestionare a produselor.
 * Se poate alege actiunea dorita, din sase optiuni fiecare intuitiva dupa numele butoanelor.
 */

public class ControllerProducts implements ActionListener {
    ProductsView productsView;
    Product product;
    ProductBLL productBLL;
    public ControllerProducts(ProductsView productsView)
    {
        this.productsView = productsView;
        productBLL = new ProductBLL();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        int validInputValues = 1;
        switch (command) {
            case "Insert Product" -> {
                if (productsView.getNameTextField().equals("") || productsView.getStringQuantityTextField().equals("") || productsView.getStringPriceTextField().equals("")) {
                    validInputValues = 0;
                    JOptionPane.showMessageDialog(productsView.getContentPane(), "Invalid input values!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            case "Edit Product" -> {
                if (productsView.getStringIdTextField().equals("") || productsView.getNameTextField().equals("") || productsView.getStringQuantityTextField().equals("") || productsView.getStringPriceTextField().equals("")) {
                    validInputValues = 0;
                    JOptionPane.showMessageDialog(productsView.getContentPane(), "Invalid input values!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            case "Find Product By Id", "Delete Product" -> {
                if (productsView.getStringIdTextField().equals("")) {
                    validInputValues = 0;
                    JOptionPane.showMessageDialog(productsView.getContentPane(), "Invalid id!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (validInputValues == 1) {
            switch (command) {
                case "Insert Product" -> {
                    product = new Product(productsView.getNameTextField(), productsView.getQuantityTextField(), productsView.getPriceTextField());
                    if (!productBLL.getValidator().validate(product)) {
                        JOptionPane.showMessageDialog(productsView.getContentPane(), "The quantity of the product cannot be under zero!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        productBLL.insertProduct(product);
                        JOptionPane.showMessageDialog(productsView.getContentPane(), "The product has been inserted!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                case "Delete Product" -> {
                    product = productBLL.findProductById(productsView.getIdTextField());
                    if (product != null) {
                        productBLL.deleteProduct(product);
                        JOptionPane.showMessageDialog(productsView.getContentPane(), "The product with id " + product.getId() + " has been deleted!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(productsView.getContentPane(), "The product with id " + productsView.getIdTextField() + " doesn't exist in the database!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                case "Edit Product" -> {
                    int alrightUpdate = 2;
                    product = productBLL.findProductById(productsView.getIdTextField());
                    if (product != null) {
                        if (!product.getName().equals(productsView.getNameTextField())) {
                            alrightUpdate = 1;
                            productBLL.updateProduct(product, "name", productsView.getNameTextField());
                        }
                        if (product.getQuantity() != productsView.getQuantityTextField()) {
                            if (!productBLL.getValidator().validate(Integer.toString(productsView.getQuantityTextField()))) {
                                alrightUpdate = 0;
                                JOptionPane.showMessageDialog(productsView.getContentPane(), "The quantity of the product cannot be under zero!", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                alrightUpdate = 1;
                                productBLL.updateProduct(product, "quantity", productsView.getQuantityTextField());
                            }
                        }
                        if (product.getPrice() != productsView.getPriceTextField()) {
                            alrightUpdate = 1;
                            productBLL.updateProduct(product, "price", productsView.getPriceTextField());
                        }
                        if (alrightUpdate == 1) {
                            JOptionPane.showMessageDialog(productsView.getContentPane(), "The product with id " + product.getId() + " has been updated!", "Information", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(productsView.getContentPane(), "The product with id " + productsView.getIdTextField() + " doesn't exist in the database!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                case "View All Products" -> {
                    JFrame frame = new JFrame("PRODUCTS");
                    frame.setSize(700, 400);
                    frame.setBackground(new Color(216, 191, 216));
                    frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - frame.getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - frame.getSize().height) / 2);
                    ArrayList<Object> arrayList = new TableGenerator<Product>().generateTable(productBLL.getAllProducts(), new Color(216,191,216), new Color(255,240,245), "panelProducts");
                    frame.setContentPane((Container) arrayList.get(0));
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    frame.setVisible(true);
                }
                case "Find Product By Id" -> {
                    product = productBLL.findProductById(productsView.getIdTextField());
                    if (product != null) {
                        productsView.setNameTextField(product.getName());
                        productsView.setQuantityTextField(Integer.toString(product.getQuantity()));
                        productsView.setPriceTextField(Double.toString(product.getPrice()));
                    } else {
                        JOptionPane.showMessageDialog(productsView.getContentPane(), "The product with id " + productsView.getIdTextField() + " doesn't exist in the database!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                case "Clear Fields" -> {
                    productsView.setIdTextField("");
                    productsView.setNameTextField("");
                    productsView.setQuantityTextField("");
                    productsView.setPriceTextField("");
                }
            }
        }
    }
}