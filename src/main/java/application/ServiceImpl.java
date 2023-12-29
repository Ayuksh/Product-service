package application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceImpl implements Service{

    private static Connection conn ;
    static {
        String url = "jdbc:mysql://localhost:3306/productdb";
        String  username = "root";
        String password = "tiger";
        try {
            conn = DriverManager.getConnection(url , username , password);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }


    @Override
    public List<Product> displayAllProduct() {
        String displayQuery = "SELECT * FROM PRODUCT_INFO";
        List<Product> productList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement() ;
            ResultSet rs = stmt.executeQuery(displayQuery);

            while (rs.next())
            {
                Product product = new Product();
                product.setProductId(  rs.getInt(1)  );
                product.setProductName(  rs.getString(2) );
                product.setProductPrice( rs.getDouble(3) );

                productList.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    @Override
    public boolean placeOrder(Order newOrder) {
        boolean status = false ;
        try {
            CallableStatement cstmt = conn.prepareCall( " {call placeOrder( ? , ? , ? , ?)}" );
            cstmt.setString( 1 , newOrder.getCustomerName());
            cstmt.setInt( 2 , newOrder.getProductId());
            cstmt.setInt(3 , newOrder.getProductQty());

            cstmt.execute() ;

            status = cstmt.getBoolean( 4  );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    @Override
    public boolean cancelOrder(int orderId) {
        String query = "{call cancleOrder(? , ?)}";
        boolean status = false ;
        try {
            CallableStatement cstmt = conn.prepareCall(query);
            cstmt.setInt(1 , orderId);
            cstmt.execute();
            status = cstmt.getBoolean(2);

        } catch (SQLException e) {
            System.out.println(e);
        }
        return status;
    }

    @Override
    public List<OrderInfo> displayAllOrders() {
        List<OrderInfo> orderList = new ArrayList<>();
        String displayQuery = "SELECT \n" +
                "    o.order_id,\n" +
                "    o.customer_name,\n" +
                "    o.product_qty * p.product_price AS Total,\n" +
                "    p.product_name,\n" +
                "    o.product_qty\n" +
                "FROM\n" +
                "    order_info o\n" +
                "        INNER JOIN\n" +
                "    product_info p ON o.product_id = p.product_id;";

        try {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(displayQuery);
            while (rs.next())
            {
                int oId = rs.getInt(1);
                String cName = rs.getString(2);
                double totalAmt = rs.getDouble(3);
                String pName = rs.getString(4);
                int pQty = rs.getInt(5);
                OrderInfo o1 = new OrderInfo(oId , cName , totalAmt , pName , pQty);
                orderList.add(o1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return orderList;
    }
}
