package co.ksga.model.service;


import co.ksga.exceptions.NotFoundException;
import co.ksga.model.entity.Product;
import co.ksga.utils.DBConnection;

import java.util.Scanner;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    static Scanner sc = new Scanner(System.in);

    @Override
    public int writeProducts(Product product) {
        return 0;
    }

    @Override
    public List<Product> readAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = """
                SELECT * FROM products
                """;
        try(
                Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setUnitPrice(resultSet.getDouble("unit_price"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setImportedDate(resultSet.getDate("imported_date").toLocalDate());
                products.add(product);
            }

        }catch (SQLException sqlException){
            System.out.println("cannot get data " + sqlException.getSQLState());
        }
        return products;
    }

    @Override
    public Product readProductById(Integer id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        Product product = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setUnitPrice(resultSet.getDouble("unit_price"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setImportedDate(resultSet.getDate("imported_date").toLocalDate());
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle properly in production code
        }
        return product;
    }

    @Override
    public ArrayList<Product> updateProduct(Product product, Integer id) {
        ArrayList<Product> tepProducts = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE id = " + id;
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println("Name : " + resultSet.getString("name"));
                System.out.println("Unit Price : " + resultSet.getDouble("unit_price"));
                System.out.println("Quantity : " + resultSet.getInt("quantity"));
                System.out.println("Imported Date : " + resultSet.getDate("imported_date").toLocalDate());
                product.setName(resultSet.getString("Name"));
                product.setUnitPrice(resultSet.getDouble("unit_price"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setImportedDate(resultSet.getDate("imported_date").toLocalDate());
            }
            int option;
            while (true) {

                System.out.println("1. Name     2. Unit Price      3. Quantity   4. All field");
                System.out.print("Choose an option: ");
                option = sc.nextInt();
                switch (option) {
                    case 1:
                        System.out.print("Enter Name: ");
                        product.setName(sc.next());
                        break;
                    case 2:
                        System.out.print("Enter Unit Price: ");
                        product.setUnitPrice(sc.nextDouble());
                        break;
                    case 3:
                        System.out.print("Enter Quantity: ");
                        product.setQuantity(sc.nextInt());
                        break;
                    case 4:
                        System.out.print("Enter Name: ");
                        product.setName(sc.next());
                        System.out.print("Enter Unit Price: ");
                        product.setUnitPrice(sc.nextDouble());
                        System.out.print("Enter Quantity: ");
                        product.setQuantity(sc.nextInt());
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                        continue;
                }
                System.out.print("Do you want to continue? (Y/N): ");
                String response = sc.next().toUpperCase();
                if (response.equals("N")) {
                    System.out.println("Product details: ");
                    System.out.println("Name: " + product.getName());
                    System.out.println("Unit Price: " + product.getUnitPrice());
                    System.out.println("Quantity: " + product.getQuantity());
                    System.out.println("ImportDate : " + product.getImportedDate());
                    break;
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("cannot get data " + sqlException.getSQLState());
        }
        tepProducts.add(product);
        return tepProducts;
    }

    @Override
    public int deleteProduct(Integer id) {
        return 0;
    }

    public List<Product> searchProductsByName(String name) {
        List<Product> productList = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "select * from products where name like '%"+name+"%'";
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id")); // Use column names for clarity
                product.setName(rs.getString("name"));
                product.setUnitPrice(rs.getDouble("unit_price"));
                product.setQuantity(rs.getInt("quantity"));

                // Parse the imported_date as LocalDate
                Date sqlDate = rs.getDate("imported_date");
                if (sqlDate != null) {
                    product.setImportedDate(((java.sql.Date) sqlDate).toLocalDate());
                } else {
                    product.setImportedDate(null); // Handle null dates if necessary
                }productList.add(product);
            }

            rs.close();
            ps.close();
            con.close();

            if (productList.isEmpty()) {
                throw new NotFoundException("No products found with name: " + name);
            }

        } catch (SQLException | RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return productList;

    }

    @Override
    public void setDisplayRow(int rows) {
        String sql = "UPDATE setting SET display_row = ? WHERE id = 1"; // Example query
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, rows);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Row setting updated successfully.");
            } else {
                System.out.println("Failed to update the row setting.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred while updating row setting.");
        }
    }

    @Override
    public int getDisplayRow() {
        String sql = "SELECT display_row FROM setting WHERE id = 1";
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getInt("display_row");
            } else {
                throw new RuntimeException("No row found with id 1.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product saveProduct(Product product) {
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