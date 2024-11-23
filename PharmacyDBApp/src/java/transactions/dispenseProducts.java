/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transactions;

/**
 *
 * @author dulat
 */
import java.util.*;
import java.sql.*;
import recordManagement.*;

public class dispenseProducts {
    
    public int stock_id;
    public int product_id;
    public int quantity;
    
    public pharmacies pharmacy = new pharmacies();
    public products product = new products();
    public patients patient = new patients();
    
    public int product_prescription(String prescriptionID){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM prescriptions p "
                    + "JOIN prescriptions_has_products php "
                    + "ON p.PRESCRIPTION_ID = php.prescriptions_PRESCRIPTION_ID "
                    + "WHERE php.prescriptions_PRESCRIPTION_ID = ?");
            pstmt.setString(1, prescriptionID);
            ResultSet rst = pstmt.executeQuery();
            
            if(rst.next()){
                product_id = rst.getInt("products_PRODUCT_ID");
                quantity = rst.getInt("QUANTITY_REQUIRED");
                
                if(quantity == 0){
                    return 1;
                }
                
                pstmt = conn.prepareStatement("SELECT * FROM products WHERE PRODUCT_ID = ?");
                pstmt.setInt(1, product_id);
                rst = pstmt.executeQuery();
                
                while(rst.next()){
                    product.product_name = rst.getString("PRODUCT_NAME");
                    product.strength = rst.getDouble("STRENGTH");
                    product.dosage_form = rst.getString("DOSAGE_FORM");
                    product.supplier_name = rst.getString("SUPPLIER_NAME");
                }
                pstmt.close();
                conn.close();
                
                return 2;
            }
            
            pstmt.close();
            conn.close();
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
    
    public int available_products() {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");

            PreparedStatement pstmt = conn.prepareStatement("SELECT DISTINCT PRODUCT_ID FROM stocks");
            ResultSet rst = pstmt.executeQuery();

            product.product_idList.clear();
            product.product_nameList.clear();
            product.strengthList.clear();
            product.dosage_formList.clear();
            product.supplier_nameList.clear();

            while (rst.next()) {
                int productId = rst.getInt("PRODUCT_ID");
                product.product_idList.add(productId);

                PreparedStatement productQuery = conn.prepareStatement("SELECT * FROM products WHERE PRODUCT_ID = ?");
                productQuery.setInt(1, productId);
                ResultSet productRst = productQuery.executeQuery();

                if (productRst.next()) {
                    product.product_nameList.add(productRst.getString("PRODUCT_NAME"));
                    product.strengthList.add(productRst.getDouble("STRENGTH"));
                    product.dosage_formList.add(productRst.getString("DOSAGE_FORM"));
                    product.supplier_nameList.add(productRst.getString("SUPPLIER_NAME"));
                }

                productRst.close();
                productQuery.close();
            }

            pstmt.close();
            conn.close();

            return 1;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
    
    public int dispense_products(String productID, String quantity) {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");

            PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(QUANTITY) AS max_quantity FROM stocks WHERE PRODUCT_ID = ?");
            pstmt.setString(1, productID);
            ResultSet rst = pstmt.executeQuery();

            if (rst.next()) {
                int maxQuantity = rst.getInt("max_quantity");
                int requiredQuantity = Integer.parseInt(quantity);
                
                if (requiredQuantity > maxQuantity) {
                    System.out.println("Insufficient stock: Required quantity exceeds available stock.");
                    pstmt.close();
                    conn.close();
                    return 2;
                }
                
                pstmt = conn.prepareStatement("SELECT * FROM stocks WHERE PRODUCT_ID = ? ORDER BY QUANTITY DESC LIMIT 1");
                pstmt.setString(1, productID);
                rst = pstmt.executeQuery();
                while(rst.next()){
                    stock_id = rst.getInt("STOCK_ID");
                }

                pstmt = conn.prepareStatement("UPDATE stocks SET QUANTITY = QUANTITY - ? WHERE STOCK_ID = ?");
                pstmt.setInt(1, requiredQuantity);
                pstmt.setInt(2, stock_id);
                pstmt.executeUpdate();

            }

            pstmt.close();
            conn.close();
            
            return 1;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
    
    public dispenseProducts(){}
    
    public static void main(String args[]){
        dispenseProducts A = new dispenseProducts();
        int status = A.available_products();
        System.out.println(status);
        System.out.println(A.product.product_idList.size());
    }
}