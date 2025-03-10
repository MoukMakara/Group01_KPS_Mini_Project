package co.ksga.controller;


import co.ksga.model.entity.Product;
import co.ksga.model.service.ProductService;
import co.ksga.model.service.ProductServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class
ProductController {
    private final static ProductService productService = new ProductServiceImpl();

    //1. Write Product
    public void addProduct(Product product) {
         productService.writeProducts(product);
    }
    //2. Display Product List
    public List<Product> getAllProducts() {
        return productService.readAllProducts();
    }
    //3. Read Product by id
    public Product getProductById(int id) {
        return productService.readProductById(id);
    }
    //4. Update
    public void updateProduct(Product product) {
         productService.updateProduct(product);
    }
    //5. Delete Product
    public String deleteProduct(Integer id) {
        return productService.deleteProduct(id);
    }

    //6. Search by product name
    public List<Product> getProductByName(String name) throws SQLException {
        return productService.searchProductsByName(name);
    }
    //7. Set Number of Display Row
    public void setRow(int rows){
        productService.setDisplayRow(rows);
    }
    // getDisplayRow
    public int getDisplayRow() {
        return productService.getDisplayRow();
    }
    //8. Save (Save insert product and update product to database)
    public void saveProduct( String operation) {
         productService.saveProduct(operation);
    }
    //9. Unsaved (View insert product and update product)
    public  void unSaveProduct(Product product, String operation) {
         productService.unsavedProduct(product , operation);
    }
    //10. Backup
    public boolean backupProduct(String fileName) throws SQLException, IOException {
        return productService.backupProducts(fileName);
    }
    //11. Restore
    public boolean restoreProduct(String backupFilePath) throws SQLException, IOException {
        return productService.restoreProducts(backupFilePath);
    }

    public void displayProduct(String operation) {
        productService.displayProduct(operation);
    }
}
