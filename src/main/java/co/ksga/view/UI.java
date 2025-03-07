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

public class UI {
    private final static ProductController productController = new ProductController();
    private static List<Product> products = new ArrayList<>();

    public static void listAllProduct() {
        int pageSize = 3;
        int currentPage = 0;
        List<Product> products = productController.getAllProducts();
        int totalPages = (int) Math.ceil(products.size() / (double) pageSize);
        Scanner sc = new Scanner(System.in);

        do {
            int start = currentPage * pageSize;
            int end = Math.min(start + pageSize, products.size());

            Table table = new Table(5, BorderStyle.UNICODE_BOX_HEAVY_BORDER, ShownBorders.ALL);
            table.addCell("ALL PRODUCTS INFO", new CellStyle(CellStyle.HorizontalAlign.CENTER), 5);
            table.addCell("ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("NAME", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("UNIT PRICE", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("QUANTITY", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("IMPORTED_DATE", new CellStyle(CellStyle.HorizontalAlign.CENTER));


            for (int i = 0; i < 5; i++){
                table.setColumnWidth(i, 25,25);
            }

            for (int i = start; i < end; i++) {
                Product product = products.get(i);
                table.addCell(product.getId().toString(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(product.getName(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(String.valueOf(product.getUnitPrice()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(String.valueOf(product.getQuantity()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(product.getImportedDate().toString(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            }
            table.addCell("PAGE NUMBER "+ (currentPage + 1) + " / " + totalPages , new CellStyle(CellStyle.HorizontalAlign.CENTER), 2);
            // table total record
            table.addCell("TOTAL RECORD " + products.size(), new CellStyle(CellStyle.HorizontalAlign.CENTER), 3);

            System.out.println(table.render());

            Table table2 = new Table(5, BorderStyle.UNICODE_BOX_HEAVY_BORDER, ShownBorders.ALL);

            table2.addCell(" MENU " , new CellStyle(CellStyle.HorizontalAlign.CENTER), 5);
            table2.addCell("[F] First Page" , new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[N] Next Page", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[P] Previous Page", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[L] Last Page" , new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[G] GOTO" , new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[E] Exit" , new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[W] Write" , new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[R] Read (id)" , new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[U] Update" , new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[D] Delete" , new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[S] Search (name)" , new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[Se] Set rows" , new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[Sa] Save" , new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[Un] Unsaved" , new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[Ba] Backup" , new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[Re] Restore" , new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table2.addCell("[E] Exit" , new CellStyle(CellStyle.HorizontalAlign.CENTER));

            for (int i = 0; i < 5; i++){
                table2.setColumnWidth(i, 30,50);
            }

            System.out.println(table2.render());

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

                    break;
                case "r":
                    // Read Operation (Makara)

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

    public static void home() {
        listAllProduct();
    }
}
