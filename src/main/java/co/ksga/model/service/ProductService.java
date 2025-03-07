package co.ksga.model.service;

import co.ksga.model.entity.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProductService {
    //1. Write Product
    int writeProducts(Product product);

    //2. Display Product List
    List<Product> readAllProducts();

    //3. Read Product by id
    Product readProductById(Integer id);

    //4. Update
    ArrayList<Product> updateProduct(Product product, Integer id);

    //5. Delete Product
    int deleteProduct(Integer id);

    //6. Search by product name
    List<Product> searchProductsByName(String name);

    //7. Set Number of Display Row
    void setDisplayRow(int rows);

    //8. Save (Save insert product and update product to database)
    Product saveProduct(Product product);

    //9. Unsaved (View insert product and update product)
    void unsavedProduct(Product product);

    //10. Backup
    boolean backupProducts(String fileName) throws IOException, SQLException;

    //11. Restore
    boolean restoreProducts(String fileName) throws IOException, SQLException;
}
