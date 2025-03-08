package co.ksga.view;

import co.ksga.controller.ProductController;
import co.ksga.model.entity.Product;
import co.ksga.utils.ProductValidation;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class UI {
    private final static ProductController productController = new ProductController();
    private static List<Product> products = new ArrayList<>();
    private static int pageSize = 3; // Default value
    private static int currentPage = 0;

    // listAllProduct
    public static void listAllProduct() throws SQLException {
        pageSize = productController.getDisplayRow(); // Get display row from the service

        // Get all products
        List<Product> products = productController.getAllProducts();

        // Calculate total pages based on pageSize
        int totalPages = (int) Math.ceil(products.size() / (double) pageSize);
        Scanner sc = new Scanner(System.in);

        do {
            int start = currentPage * pageSize;
            int end = Math.min(start + pageSize, products.size());

            // Create the table to display product info
            Table table = new Table(5, BorderStyle.UNICODE_BOX_HEAVY_BORDER, ShownBorders.ALL);
            table.addCell("ALL PRODUCTS INFO", new CellStyle(CellStyle.HorizontalAlign.CENTER), 5);
            table.addCell("ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("NAME", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("UNIT PRICE", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("QUANTITY", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("IMPORTED_DATE", new CellStyle(CellStyle.HorizontalAlign.CENTER));

            // Set column widths
            for (int i = 0; i < 5; i++) {
                table.setColumnWidth(i, 25, 25);
            }

            // Add product rows to the table
            for (int i = start; i < end; i++) {
                Product product = products.get(i);
                table.addCell(product.getId().toString(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(product.getName(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(String.valueOf(product.getUnitPrice()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(String.valueOf(product.getQuantity()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(product.getImportedDate().toString(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            }

            // Pagination and total records info
            table.addCell("PAGE NUMBER " + (currentPage + 1) + " / " + totalPages, new CellStyle(CellStyle.HorizontalAlign.CENTER), 2);
            table.addCell("TOTAL RECORD " + products.size(), new CellStyle(CellStyle.HorizontalAlign.CENTER), 3);

            // Render table
            System.out.println(table.render());

            // Create the menu table
            Table table2 = new Table(5, BorderStyle.UNICODE_BOX_HEAVY_BORDER, ShownBorders.ALL);
            table2.addCell("MENU", new CellStyle(CellStyle.HorizontalAlign.CENTER), 5);
            table2.addCell("[F] First Page", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[N] Next Page", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[P] Previous Page", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[L] Last Page", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[G] GOTO", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[E] Exit", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[W] Write", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[R] Read (id)", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[U] Update", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[D] Delete", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[S] Search (name)", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[Se] Set rows", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[Sa] Save", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[Un] Unsaved", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[Ba] Backup", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[Re] Restore", new CellStyle(CellStyle.HorizontalAlign.CENTER));

            // Set column widths for the menu table
            for (int i = 0; i < 5; i++) {
                table2.setColumnWidth(i, 30, 50);
            }

            // Render menu table
            System.out.println(table2.render());

            // Get user input for option selection
            System.out.print("Choose Option: ");
            String option = sc.nextLine().trim().toLowerCase();

            switch (option) {
                // Pagination Controls
                case "f":
                    currentPage = 0;
                    break;
                case "n":
                    if (currentPage < totalPages - 1) {
                        currentPage++;
                    }
                    break;
                case "p":
                    if (currentPage > 0) {
                        currentPage--;
                    }
                    break;
                case "l":
                    currentPage = totalPages - 1;
                    break;
                case "g":
                    System.out.print("Enter the page number you want to go to: ");
                    String inputPage = sc.nextLine().trim();
                    try {
                        int pageNumber = Integer.parseInt(inputPage);
                        if (pageNumber >= 1 && pageNumber <= totalPages) {
                            currentPage = pageNumber - 1;
                        } else {
                            System.out.println(BoxBorder.red + "Invalid page number. Please enter a number between 1 and " + totalPages + "." + BoxBorder.reset);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(BoxBorder.red + "Invalid input. Please enter a valid page number." + BoxBorder.reset);
                    }
                    break;
                case "e":
                    System.out.print("\n\uD83E\uDD14 Are you sure you want to exit? (Y/N): ");
                    String confirmExit = sc.nextLine();
                    if (confirmExit.equalsIgnoreCase("Y")) {
                        System.out.println("\uD83D\uDD1A Exiting the system. Thank You \uD83D\uDE0A❣\uFE0F");
                        System.exit(0);
                    }
                    break;

                // Product Operations
                case "w":
                    // Write Operation (Jakriya)
                    break;
                case "r":
                    // Read Operation (Makara)
                    // read product by id
                    searchById();
                    break;
                case "u":
                    // Update Operation (Try)

                    break;
                case "d":
                    // Delete Operation (Seyha)
                    DeleteProductByID();
                    listAllProduct();

                    break;
                case "s":
                    // Search Operation (Seyha)
                    SearchProductByName();

                    break;
                case "se":
                    // Set Rows
                    setRow();
                    break;
                case "sa":
                    // Save Operation (Tra)

                    break;
                case "un":
                    // Unsaved Operation (Tra)

                    break;
                case "ba":

                    String backupDirectory = "D:\\KSHRD-CENTER\\Java Assignment & Homwork\\Group01_KPS_Mini_Project\\src\\main\\java\\co\\ksga\\Backup";
                    try {
                        BackupDate(backupDirectory);
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                        System.err.println("Error: " + e.getMessage());
                    }


                    break;
                case "re":

                    RestoreDate();

                    break;

                default:
                    System.out.println(BoxBorder.red+" Invalid choice, please choose a valid option."+BoxBorder.reset);
            }
        } while (true);
    }

    // Set row method for user input
    public static void setRow() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of rows to display per page: ");
        int rows = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        // Update the row setting in the database via the controller
        productController.setRow(rows);

        // Update pageSize for pagination
        pageSize = rows;

        // After setting the row successfully, list all products
        listAllProduct();
    }
    // search for products by id
    public static void searchById() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the product ID to search: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        Product product = productController.getProductById(id);
        if (product!= null) {
            Table table = new Table(5, BorderStyle.UNICODE_BOX_HEAVY_BORDER, ShownBorders.ALL);
            table.addCell("GET PRODUCTS BY ID", new CellStyle(CellStyle.HorizontalAlign.CENTER), 5);
            table.addCell("ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("NAME", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("UNIT PRICE", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("QUANTITY", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("IMPORTED_DATE", new CellStyle(CellStyle.HorizontalAlign.CENTER));

            // Set column widths
            for (int i = 0; i < 5; i++) {
                table.setColumnWidth(i, 25, 25);
            }

            // Add product rows to the table
            table.addCell(product.getId().toString(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(product.getName(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(String.valueOf(product.getUnitPrice()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(String.valueOf(product.getQuantity()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(product.getImportedDate().toString(), new CellStyle(CellStyle.HorizontalAlign.CENTER));

            // Render table
            System.out.println(table.render());
            // press enter to continue
            System.out.print("\nPress Enter to continue...");
            sc.nextLine();
        } else {
            System.out.println("Product not found with ID " + id);
        }
    }

    public static void BackupDate(String backupDirectory) throws SQLException, IOException {
        // Prompt the user for confirmation
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you sure you want to back up the current date? (y/n): ");
        String userInput = scanner.nextLine().trim().toLowerCase();

        // Validate user input
        if (!userInput.equals("y") && !userInput.equals("n")) {
            System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
            return; // Exit the method if input is invalid
        }

        if (userInput.equals("n")) {
            System.out.println("Backup canceled.");
            return; // Exit the method if the user cancels
        }

        // Perform the backup
        try {
            boolean success = productController.backupProduct(backupDirectory);
            System.out.println(success ? "Backup completed successfully." : "Backup failed.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error during backup: " + e.getMessage());
        }
    }

//    handle of restore data
    public List<String> listBackupFiles(String backupDirectory) throws IOException {
        Path dirPath = Paths.get(backupDirectory);
        if (!Files.exists(dirPath)) {
            throw new IOException("Backup directory does not exist: " + backupDirectory);
        }

        List<String> backupFiles = new ArrayList<>();
        try (Stream<Path> files = Files.list(dirPath)) {
            files
                    .map(Path::getFileName)
                    .map(Object::toString)
                    .filter(name -> name.matches("^Version\\d+-Product-Backup-\\d{4}-\\d{2}-\\d{2}\\.sql$"))
                    .forEach(backupFiles::add);
        }
        return backupFiles;
    }



    public static void RestoreDate() {
        // Restore Operation (Sreyphea)
        Scanner scanner = new Scanner(System.in);
        String restoreDirectory = "D:\\KSHRD-CENTER\\Java Assignment & Homwork\\Group01_KPS_Mini_Project\\src\\main\\java\\co\\ksga\\Backup";
        UI backup = new UI();

        try {
            // List all available backup files
            List<String> backupFiles = backup.listBackupFiles(restoreDirectory);
            if (backupFiles.isEmpty()) {
                System.out.println(BoxBorder.red + "No backup files found in: " + BoxBorder.reset + restoreDirectory);
                return;
            }

            // Create a styled table for backup files
            Table table = new Table(2, BorderStyle.UNICODE_BOX_HEAVY_BORDER, ShownBorders.ALL);
            table.addCell("No.", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Backup File Name", new CellStyle(CellStyle.HorizontalAlign.CENTER));

            for (int i = 0; i < backupFiles.size(); i++) {
                table.addCell(BoxBorder.yellow+String.valueOf(i + 1)+BoxBorder.reset, new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(BoxBorder.pink+backupFiles.get(i)+BoxBorder.reset, new CellStyle(CellStyle.HorizontalAlign.LEFT));
            }

            // Display the table
            System.out.println("Available backup files:");
            System.out.println(table.render());

            int maxAttempts = 3;
            int attemptCount = 0;
            boolean isValidChoice = false;

            while (attemptCount < maxAttempts) {
                // Prompt user to select a backup file
                System.out.print(BoxBorder.yellow + "Select a backup file to restore (enter the number): " + BoxBorder.reset);
                String userInput = scanner.nextLine();

                try {
                    int fileChoice = Integer.parseInt(userInput);

                    if (fileChoice >= 1 && fileChoice <= backupFiles.size()) {
                        // Valid choice
                        isValidChoice = true;

                        String selectedBackupFile = backupFiles.get(fileChoice - 1);
                        String backupFilePath = restoreDirectory + File.separator + selectedBackupFile;

                        // Restore the database
                        boolean restoreSuccess = productController.restoreProduct(backupFilePath);
                        if (restoreSuccess) {
                            System.out.println(BoxBorder.green + "Database restored successfully from: " + BoxBorder.reset + selectedBackupFile);
                            listAllProduct();
                        } else {
                            System.out.println(BoxBorder.red + "Database restore failed." + BoxBorder.reset);
                        }
                        break;
                    } else {
                        attemptCount++;
                        System.out.println(BoxBorder.red + "Invalid choice. : "+ BoxBorder.reset);
                    }
                } catch (NumberFormatException e) {
                    attemptCount++;
                    System.out.println(BoxBorder.red + "Invalid input. Please enter a number. "+ BoxBorder.reset);
                }


                if (attemptCount == maxAttempts) {
                    System.out.print(BoxBorder.yellow + "You have reached the maximum number of attempts. Do you want to try again? (yes/no): " + BoxBorder.reset);
                    String retryInput = scanner.nextLine().trim().toLowerCase();

                    if (retryInput.equals("yes") || retryInput.equals("y")) {
                        attemptCount = 0;
                    } else {
                        System.out.println(BoxBorder.red + "Exiting the restore process." + BoxBorder.reset);
                        return;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error during restore: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // search for products by name
    public static void SearchProductByName() throws SQLException {
        String name = ProductValidation.productNameValidation("Enter the product name to search: ");
        productController.getProductByName(name);
    }
//    delete for product
    public static void DeleteProductByID(){
        int id = ProductValidation.idValidation("ID");
        productController.deleteProduct(id);
    }

    public static void home() throws SQLException {
        listAllProduct();
    }
}
