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

    public static void option(){
        Table table = new Table(3, BorderStyle.UNICODE_BOX_HEAVY_BORDER, ShownBorders.ALL);
        String[] optionName = {"W) Write","R) Read (id)","U) Update",
                "D) Delete","S) Search (name)","Se) Set rows",
                "sa) Save","Un) Unsaved","Ba) Backup","Re) Restore", "E) Exit"
        };
        for (String option : optionName){
            table.addCell(option,new CellStyle(CellStyle.HorizontalAlign.CENTER));
        }
        for (int i = 0; i < 3; i++){
            table.setColumnWidth(i, 51,75);
        }
        System.out.println(table.render());
    }




    public static void switchProcess(){

        Scanner scanner = new Scanner(System.in);
        String op;
        while (true){
            option();
            System.out.print("Enter your choice: ");
            op = new Scanner(System.in).nextLine();
            switch((op.trim())){
                case "w": {
                    // write

                    // jakriya
                    break;
                }
                case "r": {
                    // read

                    // makara
                    break;
                }
                case "u":{
                    // update

                    //try

                    break;
                }
                case "d":{
                    // delete
                    // seyha
                    break;
                }
                case "s":{
                    // search
                    // seyha
                    break;
                }
                case "se":{
                    // set row
                    break;
                }
                case "sa":{
                    // save

                    // tra
                    break;
                }
                case "un":{
                    // un save
                    // tra
                    break;
                }
                case "ba":{
                    //  backup
                    // kim long
                    break;
                }
                case "re":{
                    // sreyphea
                    // restore
                    break;
                }
                case "e": {
                    // exit
                    System.out.print("\n\uD83E\uDD14 Are you sure you want to exit? (Y/N): ");
                    String confirmExit = scanner.nextLine();
                    if (confirmExit.equalsIgnoreCase("Y")) {
                        System.out.println("\uD83D\uDD1A Exiting the system. Thank You \uD83D\uDE0A❣\uFE0F");
                        System.exit(0);
                    }
                    break;
                }
                default:
                    System.out.println("❌ Invalid choice, please choose a valid option.");
                    System.exit(0);
                    break;
            }
        }
    }
    public static void home(){
        switchProcess();
    }
}
