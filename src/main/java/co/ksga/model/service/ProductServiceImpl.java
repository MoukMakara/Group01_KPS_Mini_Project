package co.ksga.model.service;


import co.ksga.model.entity.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ProductServiceImpl implements ProductService{

    @Override
    public int writeProducts(Product product) {
        return 0;
    }

    @Override
    public List<Product> readAllProducts() {
        return List.of();
    }

    @Override
    public Product readProductById(Integer id) {
        return null;
    }

    @Override
    public int updateProduct(Product product, Integer id) {
        return 0;
    }

    @Override
    public int deleteProduct(Integer id) {
        return 0;
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        return List.of();
    }

    @Override
    public void setDisplayRow(int rows) {

    }

    @Override
    public Product saveProduct(Product product) {
        Scanner sc = new Scanner(System.in);
        String option;

        return null;
    }

    @Override
    public void unsavedProduct(Product product) {

    }

    @Override
    public boolean backupProducts(String fileName) throws IOException, SQLException {
        return false;
    }

    @Override
    public boolean restoreProducts(String fileName) throws IOException, SQLException {
        return false;
    }
}