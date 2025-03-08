package co.ksga.model.service;

import co.ksga.model.entity.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProductService {
    //1. Write Product
    void writeProducts(Product product);

    //2. Display Product List
    List<Product> readAllProducts();

    //3. Read Product by id
    Product readProductById(Integer id);

    //4. Update
    public void updateProduct(Product product);

    //5. Delete Product
    String deleteProduct(Integer id);

    //6. Search by product name
    List<Product> searchProductsByName(String name) throws SQLException;

    //7. Set Number of Display Row
    void setDisplayRow(int rows);
    // get display row
    int getDisplayRow();

    //8. Save (Save insert product and update product to database)
    void saveProduct(String operation);

    //9. Unsaved (View insert product and update product)
    void unsavedProduct(Product products , String operation);

    //10. Backup
    boolean backupProducts(String backupDirectory) throws IOException, SQLException;

    //11. Restore
    boolean restoreProducts(String fileName) throws IOException, SQLException;

    void displayProduct(String operation);
}
