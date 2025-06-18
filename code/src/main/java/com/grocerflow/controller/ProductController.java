package com.grocerflow.controller;

import com.grocerflow.model.Product;
import com.grocerflow.model.InventoryLog;
import com.grocerflow.model.dao.ProductDAO;
import com.grocerflow.model.dao.InventoryLogDAO;

import java.util.List;

public class ProductController {

    private final ProductDAO productDAO;
    private final InventoryLogDAO logDAO;

    public ProductController() {
        this.productDAO = new ProductDAO();
        this.logDAO = new InventoryLogDAO();
    }

    public boolean addProduct(Product product, int performedByUserId) {
        boolean success = productDAO.addProduct(product);
        if (success) {
            logDAO.addLog(new InventoryLog(0, product.getProductId(), "added", performedByUserId, null));
        }
        return success;
    }

    public boolean updateProduct(Product product, int performedByUserId) {
        boolean success = productDAO.updateProduct(product);
        if (success) {
            logDAO.addLog(new InventoryLog(0, product.getProductId(), "updated", performedByUserId, null));
        }
        return success;
    }

    public boolean deleteProduct(int productId, int performedByUserId) {
        boolean success = productDAO.deleteProduct(productId);
        if (success) {
            logDAO.addLog(new InventoryLog(0, productId, "deleted", performedByUserId, null));
        }
        return success;
    }
    
    public boolean removeProductById(int productId) {
    return productDAO.deleteProduct(productId);
}

public boolean removeProductByName(String name) {
    Product product = productDAO.getProductByName(name);
    if (product != null) {
        return productDAO.deleteProduct(product.getProductId());
    }
    return false;
}

    public Product getProductById(int productId) {
        return productDAO.getProductById(productId);
    }
    

    
    public List<Product> searchProducts(String keyword) {
        return productDAO.searchProducts(keyword);
    }

    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }
}
