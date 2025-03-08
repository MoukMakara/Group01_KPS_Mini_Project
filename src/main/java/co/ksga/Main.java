package co.ksga;

import co.ksga.model.entity.Product;
import co.ksga.model.service.ProductServiceImpl;
import co.ksga.view.UI;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        String reset = "\u001B[0m";  // Reset color
        String cyanBold = "\u001B[1;36m";// Cyan color and bold
        System.out.println(cyanBold +"");
        System.out.println(cyanBold +" ".repeat(12) +"███████╗████████╗ ██████╗  ██████╗██╗  ██╗    ███╗   ███╗ █████╗ ███╗   ██╗ █████╗  ██████╗ ███████╗███╗   ███╗███████╗███╗   ██╗████████╗");
        System.out.println(cyanBold +" ".repeat(12) +"██╔════╝╚══██╔══╝██╔═══██╗██╔════╝██║ ██╔╝    ████╗ ████║██╔══██╗████╗  ██║██╔══██╗██╔════╝ ██╔════╝████╗ ████║██╔════╝████╗  ██║╚══██╔══╝");
        System.out.println(cyanBold +" ".repeat(12) +"███████╗   ██║   ██║   ██║██║     █████╔╝     ██╔████╔██║███████║██╔██╗ ██║███████║██║  ███╗█████╗  ██╔████╔██║█████╗  ██╔██╗ ██║   ██║  ");
        System.out.println(cyanBold +" ".repeat(12) +"╚════██║   ██║   ██║   ██║██║     ██╔═██╗     ██║╚██╔╝██║██╔══██║██║╚██╗██║██╔══██║██║   ██║██╔══╝  ██║╚██╔╝██║██╔══╝  ██║╚██╗██║   ██║  ");
        System.out.println(cyanBold +" ".repeat(12) +"███████║   ██║   ╚██████╔╝╚██████╗██║  ██╗    ██║ ╚═╝ ██║██║  ██║██║ ╚████║██║  ██║╚██████╔╝███████╗██║ ╚═╝ ██║███████╗██║ ╚████║   ██║  ");
        System.out.println(cyanBold +" ".repeat(12) +"╚══════╝   ╚═╝    ╚═════╝  ╚═════╝╚═╝  ╚═╝    ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝   ╚═╝  ");
        System.out.println(cyanBold +" ".repeat(57) +"មជ្ឈមណ្ឌលអភិវឌ្ឍន៍វិទ្យាសាស្រ្ត និង បច្ចេកទេសវិទ្យាកម្រិតខ្ពស់");
        System.out.println(cyanBold +" ".repeat(50) +"Center Of Science and Technology Advanced Development-CSTAD"+reset);
        // makara
        UI.home();

    }

}