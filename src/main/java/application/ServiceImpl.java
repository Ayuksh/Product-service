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
        return false;
    }
}
