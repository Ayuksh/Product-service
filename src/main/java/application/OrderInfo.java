package application;

public class OrderInfo {
    private int orderId ;
    private String customerName ;
    private double totalAmount ;
    private String productName ;
    private int productQty ;

    public OrderInfo(int orderId, String customerName,
                     double totalAmount, String productName,
                     int productQty) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.productName = productName;
        this.productQty = productQty;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductQty() {
        return productQty;
    }
}
