package co.ksga.model.service;


import co.ksga.exceptions.NotFoundException;
import co.ksga.model.entity.Product;
import co.ksga.utils.DBConnection;

import java.util.Scanner;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private List<Product> saveInsert = new ArrayList<>();
    private List<Product> saveUpdate = new ArrayList<>();
    private List<Product> unsavedProducts = new ArrayList<>();

    static Scanner sc = new Scanner(System.in);

    @Override
    public List<Product> writeProducts(Product product) {
        ArrayList productTemp = new ArrayList();

        return productTemp;
    }

    @Override
    public List<Product> readAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = """
                SELECT * FROM product
                """;
        try(
                Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {
            while (resultSet.next()){
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
        String sql;
        if (product.getId() == null){
            // Insert new product
            sql = "INSERT INTO products (name, unit_price, quantity, imported_date) VALUES (?, ?, ?, ?)";
        }
        else {
            // Update existing product
            sql = "UPDATE products SET name = ?, unit_price = ?, quantity = ?, imported_date = ? WHERE id = ?";
        }
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS)){
            if (conn == null){
                throw new SQLException("Failed to establish database connection");
            }
            ps.setString(1, product.getName());
            ps.setDouble(2,product.getUnitPrice());
            ps.setInt(3,product.getQuantity());
            ps.setDate(4, Date.valueOf(product.getImportedDate()));

            if (product.getId() != null){
                ps.setInt(5, product.getId());
            }
            int rows = ps.executeUpdate();
            if (rows > 0){
                if (product.getId() == null){
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()){
                            product.setId(rs.getInt(1));
                        }
                    }
                }
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving product to database", e);
        }
        return null;
    }

    public Product saveToUnsavedProduct(Product product){
        unsavedProducts.add(product);
        System.out.println("Product added to unsaved: " + product.getName());
        return product;
    }

    @Override
    public void unsavedProduct(Product product) {
        Scanner sc = new Scanner(System.in);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-10s %-20s %-15s %-10s %-15s%n", "ID", "Name", "Unit Price", "Qty", "Import Date");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-10s %-20s %-15.2f %-10d %-15s%n",
                product.getId(),
                product.getName(),
                product.getUnitPrice(),
                product.getQuantity(),
                product.getImportedDate());
        System.out.println("--------------------------------------------------------------------------------");
        System.out.print("Enter to continue...");
        sc.nextLine();
    }

    // Process unsaved products with user input (for "sa" option)
    private void processUnsavedProducts() {
        Scanner sc = new Scanner(System.in);
        List<Product> tempUnsaved = new ArrayList<>(unsavedProducts); // Create a copy to avoid issues with removal
        for (Product product : tempUnsaved) {

            System.out.println("\n'si' for saving insert products and 'su' for saving update products or 'b' for back to menu");
            System.out.print("Enter your option : ");
            String choice = sc.nextLine().trim().toLowerCase();
            if (choice.equals("si")) {
                saveInsert.add(product);
                Product savedProduct = saveProduct(product);
                if (savedProduct != null) {
                    System.out.println("Product " + savedProduct.getId() + " successfully added.");
                    unsavedProducts.remove(product); // Remove from unsavedProducts after successful insert
                    saveInsert.remove(product); // Clean up staging list
                } else {
                    System.out.println("Failed to save product: " + product.getName());
                }
            } else if (choice.equals("su")) {
                if (product.getId() != null) {
                    saveUpdate.add(product);
                    Product updatedProduct = saveProduct(product);
                    if (updatedProduct != null) {
                        System.out.println("Product " + updatedProduct.getId() + " successfully updated.");
                        unsavedProducts.remove(product); // Remove from unsavedProducts after successful update
                        saveUpdate.remove(product); // Clean up staging list
                    } else {
                        System.out.println("Failed to update product: " + product.getName());
                    }
                } else {
                    System.out.println("Cannot update: Product ID is null. Use 'si' to insert first.");
                }
            } else if (choice.equals("b")) {
                System.out.println("Returning to menu...");
                // Do not transfer to unsavedProducts; just leave it as is
            }
            System.out.print("Enter to continue...");
            sc.nextLine();
        }
    }

    // Process unsaved products with user input (for "un" option)
    private void processUnsaved() {
        Scanner scanner = new Scanner(System.in);
        if (unsavedProducts.isEmpty()) {
            System.out.println("No unsaved products to process.");
            return;
        }

        System.out.println("\n'ui' for saving insert products and 'uu' for saving update products or 'b' for back to menu");
        System.out.print("Enter your option : ");
        String choice = scanner.nextLine().trim().toLowerCase();

        if (choice.equals("ui")) {
            // Display only products without IDs (new inserts)
            for (Product product : unsavedProducts) {
                if (product.getId() == null) {
                    unsavedProduct(product);
                }
            }
        } else if (choice.equals("uu")) {
            // Display only products with IDs (updates)
            for (Product product : unsavedProducts) {
                if (product.getId() != null) {
                    unsavedProduct(product);
                }
            }
        } else if (choice.equals("b")) {
            System.out.println("Returning to menu...");
            return;
        } else {
            System.out.println("Invalid option. Please choose 'ui', 'uu', or 'b'.");
            return;
        }
    }

    public static void main(String[] args) {
        ProductServiceImpl productService = new ProductServiceImpl();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print("\n=> Choose an option() : ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "w":
                    System.out.print("Enter product name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter unit price: ");
                    Double unitPrice = scanner.nextDouble();
                    System.out.print("Enter quantity: ");
                    Integer quantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter imported date (YYYY-MM-DD): ");
                    LocalDate importedDate = LocalDate.parse(scanner.nextLine());

                    Product product = new Product();
                    product.setName(name);
                    product.setUnitPrice(unitPrice);
                    product.setQuantity(quantity);
                    product.setImportedDate(importedDate);

                    productService.saveToUnsavedProduct(product); // Directly to unsavedProducts
                    break;

                case "sa":
                    if (productService.unsavedProducts.isEmpty()) {
                        System.out.println("No products in virtual table to save.");
                    } else {
                        productService.processUnsavedProducts();
                    }
                    break;

                case "un":
                    productService.processUnsaved();
                    break;

                case "e":
                    running = false;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
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
