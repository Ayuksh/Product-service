package application;

import java.util.List;
import java.util.Scanner;

public class MainApp {

    private static Scanner sc = new Scanner(System.in);
    private static Service service = new ServiceImpl() ;

    public static void main(String[] args) {
        System.out.println("\n\n\n\nSELECT OPERATION ");
        System.out.println("1. DISPLAY ALL PRODUCTS ");
        System.out.println("2. PLACE ORDER ");
        System.out.println("3. CANCEL ORDER ");
        System.out.println("4. DISPLAY ORDERS ");
        System.out.println("5. exit");
        int ch = sc.nextInt();

        switch (ch)
        {
            case 1:
                displayAllProducts();
                break;
            case 2:
                placeOrder();
                break ;
            case 3:
                // cancelOrder();
                break;
            case 4 :
                // displayAllOrders();
                break;
            case 5 :
                System.exit(0 );
                break;
            default:
                System.err.println("INVALID CHOICE ");
        }
        main(args);
    }

    private static void displayAllProducts()
    {
        List<Product> productList  =  service.displayAllProduct();

        for (Product p : productList)
            System.out.println(p.getProductId() +"      "+ p.getProductName() +"       "+p.getProductPrice());
    }

    private static void placeOrder()
    {
        System.out.println("ENTER NAME ");
        String customerName = sc.next() ;
        System.out.println("ENTER PRODUCT ID");
        int productId = sc.nextInt();
        System.out.println("ENTER PRODUCT QUANTITY ");
        int productQty = sc.nextInt() ;

        Order newOrder = new Order(customerName , productId , productQty);
        boolean status = service.placeOrder(newOrder);
        if (status)
            System.out.println("ORDER PLACED !!");
        else
            System.out.println("ORDER NOT PLACED !!");

    }

}
