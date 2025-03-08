package co.ksga.model.service;


import co.ksga.exceptions.NotFoundException;
import co.ksga.model.entity.Product;
import co.ksga.utils.DBConnection;
import co.ksga.utils.ProductValidation;
import co.ksga.view.BoxBorder;
import co.ksga.view.UI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Scanner;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ProductServiceImpl implements ProductService {
    ArrayList<Product> productUpdate = new ArrayList<>();
    ArrayList<Product> productInsert = new ArrayList<>();


    static Scanner sc = new Scanner(System.in);

    @Override
    public void writeProducts(Product product) {
        String sql = """
                SELECT COUNT(*) AS total FROM products
                """;
        int id = 0;
        try (
                Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {
            if (resultSet.next()) {
                id = resultSet.getInt("total");
            }
            id = id + 1;
            System.out.println("ID: " + id);
            String name = ProductValidation.productNameValidation("ProductName");
            String price = ProductValidation.productPriceValidation("Price");
            double finalprice = Double.parseDouble(price);
            String quantity = ProductValidation.productPriceValidation("Quantity");
            int finalquantity = Integer.parseInt(quantity);
            LocalDate currentDate = LocalDate.now();
            System.out.println("Enter to continue......");
            sc.nextLine();
            Product temp = new Product(id,name,finalprice,finalquantity,currentDate);
            id++;
            unsavedProduct(temp,"add");
        } catch (SQLException sqlException) {
            System.out.println("cannot get data " + sqlException.getSQLState());
        }

    }

    @Override
    public List<Product> readAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = """
                SELECT * FROM products
                """;
        try (
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

        } catch (SQLException sqlException) {
            System.out.println("cannot get data " + sqlException.getSQLState());
        }
        return products;
    }

    @Override
    public Product readProductById(Integer id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        Product product = null;

        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                System.err.println("Database connection is null. Cannot fetch product.");
                return null;
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Product found, populate the object
                    product = new Product();
                    product.setId(resultSet.getInt("id"));
                    product.setName(resultSet.getString("name"));
                    product.setUnitPrice(resultSet.getDouble("unit_price"));
                    product.setQuantity(resultSet.getInt("quantity"));
                    product.setImportedDate(resultSet.getDate("imported_date").toLocalDate());
                } else {
                    // Product not found
                    throw new NotFoundException(BoxBorder.red + "not found product with id " + BoxBorder.reset + id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle properly in production code
            System.err.println("Error while fetching product by ID: " + e.getMessage());
        }

        return product;
    }

    @Override
    public void updateProduct(Product product) {
        ArrayList<Product> tepProducts = new ArrayList<>();
        int id;
        System.out.println("Enter ID : ");
        id = sc.nextInt();
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


    }

    @Override
    public String deleteProduct(Integer id) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            if (con == null) {
                throw new SQLException("Failed to establish database connection");
            }

            String selectSql = "SELECT * FROM products WHERE id = ?";
            pstm = con.prepareStatement(selectSql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            if (!rs.next()) {
                throw new NotFoundException("Product not found with ID: " + id);
            }
            System.out.println("Product Details:");
            System.out.println("ID: " + rs.getInt("id"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Unit Price: " + rs.getDouble("unit_price"));
            System.out.println("Quantity: " + rs.getInt("quantity"));
            System.out.println("Imported Date: " + rs.getDate("imported_date"));

            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.print("Are you sure you want to delete this product? (y/n): ");
                String response = sc.nextLine().trim().toLowerCase();

                if ("y".equals(response)) {
                    // Perform deletion
                    String deleteSql = "DELETE FROM products WHERE id = ?";
                    try (PreparedStatement deleteStmt = con.prepareStatement(deleteSql)) {
                        deleteStmt.setInt(1, id);
                        int rowsAffected = deleteStmt.executeUpdate();
                        return rowsAffected + " product(s) deleted successfully";
                    }
                } else if ("n".equals(response)) {
                    return "Deletion canceled by user";
                } else {
                    System.out.println("Invalid input. Please enter 'y' or 'n'");
                }
            }

        } catch (SQLException e) {
            return "Error occurred: " + e.getMessage();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }


    @Override
    public List<Product> searchProductsByName(String name) throws SQLException {
        List<Product> productList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            if (con == null) {
                throw new SQLException("Failed to establish database connection");
            }
            String sql = "SELECT * FROM products WHERE name LIKE ?";
            ps = con.prepareStatement(sql);

            //  (to prevent SQL injection)
            ps.setString(1, "%" + name + "%");

            rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {
                throw new NotFoundException(BoxBorder.red + "Product not found with name: " + BoxBorder.reset + name);
            }
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setUnitPrice(rs.getDouble("unit_price"));
                product.setQuantity(rs.getInt("quantity"));

                Date sqlDate = rs.getDate("imported_date");
                if (sqlDate != null) {
                    product.setImportedDate(sqlDate.toLocalDate());
                } else {
                    product.setImportedDate(null);
                }
                System.out.println("ID : " + product.getId());
                System.out.println("Product Name: " + product.getName());
                System.out.println("Product Price: " + product.getUnitPrice());
                System.out.println("Product Quantity: " + product.getQuantity());
                System.out.println("Product Date: " + product.getImportedDate());
                productList.add(product);
            }

            if (productList.isEmpty()) {
                throw new NotFoundException("No products found with name: " + name);
            }
        } catch (SQLException | RuntimeException e) {
            System.out.println("Error occurred: " + e.getMessage());
        } finally {

            assert rs != null;
            rs.close();
            ps.close();
            con.close();
        }
        return productList;
    }


    @Override
    public void setDisplayRow(int rows) {
        final int DEFAULT_ROWS = 3;

        if (rows <= 0) {
            System.out.println("Rows cannot be less than or equal to 0. Setting default value: " + DEFAULT_ROWS + " items per page.");
            rows = DEFAULT_ROWS;
        }

        String sql = "UPDATE setting SET display_row = ? WHERE id = 1"; // Example query

        try (Connection connection = DBConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setInt(1, rows);

                int result = preparedStatement.executeUpdate();

                if (result > 0) {
                    System.out.println("Row setting updated successfully. Items per page: " + rows);
                } else {
                    System.out.println("Failed to update the row setting.");
                }
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
    public void saveProduct(String operation) {
        LocalDate currentDate = LocalDate.now();
        if (operation.equals("update")) {
            String sqlUpdate = "UPDATE products SET name = ?, unit_price = ?, quantity = ? WHERE id = ?";
            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)) {
                for (Product productUpdate : productUpdate) {
                    preparedStatement.setString(1, productUpdate.getName());
                    preparedStatement.setDouble(2, productUpdate.getUnitPrice());
                    preparedStatement.setInt(3, productUpdate.getQuantity());
                    preparedStatement.setInt(4, productUpdate.getId());
                    preparedStatement.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle properly in production code
            }
        } else if(operation.equals("add")){
            String sqlInsert = "INSERT INTO products (name, unit_price, quantity, imported_date) VALUES (?, ?, ?,?)";
            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
                for (Product productInsert : productInsert) {
                    preparedStatement.setString(1, productInsert.getName());
                    preparedStatement.setDouble(2, productInsert.getUnitPrice());
                    preparedStatement.setInt(3, productInsert.getQuantity());
                    preparedStatement.setDate(4, Date.valueOf(productInsert.getImportedDate().toString()));
                    preparedStatement.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle properly in production code
            }
        }

    }

    @Override
    public void unsavedProduct(Product products, String operation) {
        if (operation.equals("add")) {
            productInsert.add(products);  // Changed to addAll since we're adding a list
        } else if (operation.equals("update")) {
            productUpdate.add(products);
        }
    }

    @Override
    public boolean backupProducts(String backupDirectory) throws IOException, SQLException {
        String dbUser = "postgres", dbPassword = "seyha", dbName = "stockmanagement";
        String pgDumpPath = "C:\\Program Files\\PostgreSQL\\17\\bin\\pg_dump.exe";

        // Generate a custom file name with versioning
        String fileName = generateBackupFileName(backupDirectory);

        // Build and execute the pg_dump command
        ProcessBuilder processBuilder = new ProcessBuilder(
                pgDumpPath, "--username=" + dbUser, "--dbname=" + dbName, "--file=" + fileName
        );
        processBuilder.environment().put("PGPASSWORD", dbPassword);
        processBuilder.redirectErrorStream(true);

        try {
            // Log the command being executed
            System.out.println("Executing command: " + String.join(" ", processBuilder.command()));
            Process process = processBuilder.start();

            // Capture and log output
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                reader.lines().forEach(line -> output.append(line).append("\n"));
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Backup successful: " + fileName);
                return true;
            } else {
                System.err.println("Backup failed. Exit code: " + exitCode + "\nOutput: " + output);
                return false;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Backup interrupted.");
            return false;
        }
    }


    private String generateBackupFileName(String backupDirectory) throws IOException {
        Path dirPath = Paths.get(backupDirectory);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath); // Create the directory if it doesn't exist
        }

        LocalDate currentDate = LocalDate.now();
        String baseName = "Version%d-Product-Backup-" + currentDate + ".sql";

        // Find the latest version number
        int version = 1;
        try (Stream<Path> files = Files.list(dirPath)) {
            version = files
                    .map(Path::getFileName)
                    .map(Object::toString)
                    .filter(name -> name.matches("^Version(\\d+)-Product-Backup-" + currentDate + "\\.sql$")) // Strict regex
                    .peek(name -> System.out.println("Found backup file: " + name)) // Debugging: Log matching files
                    .map(name -> {
                        // Extract the version number using regex
                        String versionPart = name.replaceAll("^Version(\\d+)-Product-Backup-" + currentDate + "\\.sql$", "$1");
                        return Integer.parseInt(versionPart); // Parse the version number
                    })
                    .max(Integer::compareTo) // Find the highest version number
                    .orElse(0) + 1; // Increment the highest version number
        }

        // Return the full file path
        return dirPath.resolve(String.format(baseName, version)).toString();
    }


    @Override
    public boolean restoreProducts(String fileName) throws IOException, SQLException {
        return false;
    }
}