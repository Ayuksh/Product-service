package application;

import java.util.List;

public interface Service {
    List<Product> displayAllProduct();
    boolean placeOrder( Order newOrder );
    boolean cancelOrder(int orderId );
}
