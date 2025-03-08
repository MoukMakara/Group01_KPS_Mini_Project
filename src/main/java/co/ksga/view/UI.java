package co.ksga.view;

import co.ksga.controller.ProductController;
import co.ksga.model.entity.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static co.ksga.view.BoxBorder.*;

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
            table.addCell(magenta +"ALL PRODUCTS INFO" + reset, new CellStyle(CellStyle.HorizontalAlign.CENTER), 5);
            table.addCell(magenta +"ID"+ reset, new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(magenta +"NAME"+ reset, new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(magenta +"UNIT PRICE"+ reset, new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(magenta +"QUANTITY"+ reset, new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(magenta +"IMPORTED_DATE"+ reset, new CellStyle(CellStyle.HorizontalAlign.CENTER));

            // Set column widths
            for (int i = 0; i < 5; i++) {
                table.setColumnWidth(i, 25, 25);
            }

            // Add product rows to the table
            for (int i = start; i < end; i++) {
                Product product = products.get(i);
                table.addCell(blue + product.getId().toString() + reset, new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(blue + product.getName()+ reset, new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(blue + String.valueOf(product.getUnitPrice())+ reset, new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(blue + String.valueOf(product.getQuantity())+ reset, new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(blue + product.getImportedDate().toString()+ reset, new CellStyle(CellStyle.HorizontalAlign.CENTER));
            }

            // Pagination and total records info
            table.addCell(green + "PAGE NUMBER : " + reset + yellow + (currentPage + 1) +reset + green + " of " + totalPages + reset, new CellStyle(CellStyle.HorizontalAlign.CENTER), 2);
            table.addCell(green + "TOTAL RECORD" + reset + " : " + darkRed + products.size() +reset, new CellStyle(CellStyle.HorizontalAlign.CENTER), 3);

            // Render table
            System.out.println(table.render());

            Table table2 = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
            CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);
            table2.setColumnWidth(0, 20, 25);
            table2.setColumnWidth(1, 20, 25);
            table2.setColumnWidth(2, 20, 25);
            table2.setColumnWidth(3, 20, 25);
            table2.setColumnWidth(4, 20, 25);

            table2.addCell(" ");
            table2.addCell(" ", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell(magenta + "APPLICATION MENU"+ reset, new CellStyle(CellStyle.HorizontalAlign.CENTER)); // Set colspan to 5
            table2.addCell(" ", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell(" ");
            table2.addCell(HORIZONTAL_CONNECTOR_BORDER.repeat(20));
            table2.addCell(HORIZONTAL_CONNECTOR_BORDER.repeat(20));
            table2.addCell(HORIZONTAL_CONNECTOR_BORDER.repeat(20));
            table2.addCell(HORIZONTAL_CONNECTOR_BORDER.repeat(20));
            table2.addCell(HORIZONTAL_CONNECTOR_BORDER.repeat(20));

            table2.addCell(cyan + "  (F) First Page");
            table2.addCell(cyan + "  (N) Next Page");
            table2.addCell(cyan + "  (P) Previous Page");
            table2.addCell(cyan + "  (L) Last Page");
            table2.addCell(cyan + "  (G) GOTO" + reset);
            ////////////////////////////////////////////////////////////////
            table2.addCell(HORIZONTAL_CONNECTOR_BORDER.repeat(20));
            table2.addCell(HORIZONTAL_CONNECTOR_BORDER.repeat(20));
            table2.addCell(HORIZONTAL_CONNECTOR_BORDER.repeat(20));
            table2.addCell(HORIZONTAL_CONNECTOR_BORDER.repeat(20));
            table2.addCell(HORIZONTAL_CONNECTOR_BORDER.repeat(20));

            table2.addCell(cyan + "  (W) Write");
            table2.addCell(cyan + "  (R) Read (id)");
            table2.addCell(cyan + "  (U) Update");
            table2.addCell(cyan + "  (D) Delete");
            table2.addCell(cyan + "  (S) Search (name)");
            table2.addCell(cyan + "  (Se) Set rows");
            table2.addCell(cyan + "  (Sa) Save");
            table2.addCell(cyan + "  (Un) Unsaved");
            table2.addCell(cyan + "  (Ba) Backup");
            table2.addCell(cyan + "  (Re) Restore");
            table2.addCell(cyan + "  (E)EXIT" + reset);

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
                            System.out.println("Invalid page number. Please enter a number between 1 and " + totalPages + ".");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid page number.");
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

                    break;
                case "un":
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
        sc.nextLine();

        productController.setRow(rows);

        // Update pageSize for pagination
        pageSize = rows;

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
            table.addCell(magenta + "GET PRODUCTS BY ID" + reset, new CellStyle(CellStyle.HorizontalAlign.CENTER), 5);
            table.addCell(magenta + "ID" + reset, new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(magenta + "NAME" + reset, new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(magenta + "UNIT PRICE" + reset, new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(magenta + "QUANTITY" + reset, new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(magenta + "IMPORTED_DATE" + reset, new CellStyle(CellStyle.HorizontalAlign.CENTER));

            // Set column widths
            for (int i = 0; i < 5; i++) {
                table.setColumnWidth(i, 25, 25);
            }

            // Add product rows to the table
            table.addCell(blue + product.getId().toString() + reset, new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(blue + product.getName()+ reset, new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(blue + String.valueOf(product.getUnitPrice())+ reset, new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(blue + String.valueOf(product.getQuantity())+ reset, new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(blue + product.getImportedDate().toString()+ reset, new CellStyle(CellStyle.HorizontalAlign.CENTER));

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
