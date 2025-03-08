package co.ksga.view;

import co.ksga.controller.ProductController;
import co.ksga.model.entity.Product;
import co.ksga.model.service.ProductServiceImpl;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UI {
    private final static ProductController productController = new ProductController();
    private static List<Product> products = new ArrayList<>();
    private static int pageSize = 3; // Default value
    private static int currentPage = 0;

    // listAllProduct
    public static void listAllProduct() {
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
                            System.out.println("❌ Invalid page number. Please enter a number between 1 and " + totalPages + ".");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid input. Please enter a valid page number.");
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
                    productController.addProduct(new Product());
                    break;
                case "r":
                    // Read Operation (Makara)
                    // read product by id
                    searchById();
                    break;
                case "u":
                    // Update Operation (Try)
                    productController.updateProduct(new Product());
                    break;
                case "d":
                    // Delete Operation (Seyha)

                    break;
                case "s":
                    // Search Operation (Seyha)

                    break;
                case "se":
                    // Set Rows
                    setRow();
                    break;
                case "sa":
                    // Save Operation (Tra)
                    String type;
                    System.out.println(" 'si' for saving insert products and 'su' for saving update products or 'b' for back to menu");
                    System.out.print("Enter option : ");
                    type = sc.nextLine().trim().toLowerCase();
                    if (type.equals("si")) {
                        productController.saveProduct("add");
                    } else {
                        productController.saveProduct("update");
                    }
                    break;
                case "un":
                    String types;
                    System.out.println("\n'ui' for saving insert products and 'uu' for saving update products or 'b' for back to menu");
                    System.out.print("Enter option : ");
                    types = sc.nextLine().trim().toLowerCase();
                    if(types.equals("ui")){
                        productController.unSaveProduct(new Product(),"add");
                    }else if(types.equals("uu")){
                        productController.unSaveProduct(new Product(),"update");
                    }
                    // Unsaved Operation (Tra)
                    break;
                case "ba":
                    // Backup Operation (Kim Long)

                    break;
                case "re":
                    // Restore Operation (Sreyphea)

                    break;
                default:
                    System.out.println("❌ Invalid choice, please choose a valid option.");
            }
        } while (true);
    }

    // Set row method for user input
    public static void setRow() {
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

    // updateProduct
    public static void updateProduct() {
        productController.updateProduct(new Product());
    }

    // search for products by id
    public static void searchById() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the product ID to search: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        Product product = productController.getProductById(id);
        if (product != null) {
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

    // search for products by name

    public static void home() {
        listAllProduct();
    }
}
