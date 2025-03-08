package co.ksga.controller;


import co.ksga.model.entity.Product;
import co.ksga.model.service.ProductService;
import co.ksga.model.service.ProductServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ProductController {
    private final static ProductService productService = new ProductServiceImpl();

    //1. Write Product
    public List<Product> addProduct(Product product) {
        return productService.writeProducts(product);
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
    public int updateProduct(Product product, Integer id) {
        return productService.updateProduct(product, id);
    }
    //5. Delete Product
    public int deleteProduct(Integer id) {
        return productService.deleteProduct(id);
    }

    //6. Search by product name
    public List<Product> getProductByName(String name) {
        return productService.searchProductsByName(name);
    }
    //7. Set Number of Display Row
    public void setRow(int row){
        productService.setDisplayRow(row);
    }
    //8. Save (Save insert product and update product to database)
    public Product saveProduct(Product product) {
        return productService.saveProduct(product);
    }
    //9. Unsaved (View insert product and update product)
    public void unSaveProduct(Product product) {
        productService.unsavedProduct(product);
    }
    //10. Backup
    public boolean backupProduct(String fileName) throws SQLException, IOException {
        return productService.backupProducts(fileName);
    }
    //11. Restore
    public boolean restoreProduct(String fileName) throws SQLException, IOException {
        return productService.restoreProducts(fileName);
    }
}
