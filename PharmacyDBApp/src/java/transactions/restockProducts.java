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

public class restockProducts {
    
    public ArrayList<Integer> stock_idList = new ArrayList<>();
    public ArrayList<Integer> product_idList = new ArrayList<>();
    public ArrayList<String> product_nameList = new ArrayList<>();
    public ArrayList<Integer> quantityList = new ArrayList<>();
    public ArrayList<Integer> pharmacy_idList = new ArrayList<>();
    public ArrayList<Integer> batch_numberList = new ArrayList<>();
    public ArrayList<String> expiration_dateList = new ArrayList<>();
    
    public pharmacies pharmacy = new pharmacies();
    public products product = new products();
    
    public restockProducts(){}
    
    public int check_stocks(String pharmacyID){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM stocks WHERE PHARMACY_ID=? ORDER BY QUANTITY");
            pstmt.setString(1, pharmacyID);
            ResultSet rst = pstmt.executeQuery();
            
            stock_idList.clear();
            product_idList.clear();
            product_nameList.clear();
            quantityList.clear();
            pharmacy_idList.clear();
            batch_numberList.clear();
            expiration_dateList.clear();
            
            while(rst.next()){         
                stock_idList.add(rst.getInt("STOCK_ID"));
                product_idList.add(rst.getInt("PRODUCT_ID"));
                product_nameList.add(rst.getString("PRODUCT_NAME"));
                quantityList.add(rst.getInt("QUANTITY"));
                pharmacy_idList.add(rst.getInt("PHARMACY_ID"));
                batch_numberList.add(rst.getInt("BATCH_NUMBER"));
                expiration_dateList.add(rst.getString("EXPIRATION_DATE"));
            }
            
            pstmt.close();
            conn.close();
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
    
    public int recommended_restock(String pharmacyID){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM stocks WHERE PHARMACY_ID=? AND QUANTITY <= 10");
            pstmt.setString(1, pharmacyID);
            ResultSet rst = pstmt.executeQuery();
            
            product_idList.clear();
            
            while(rst.next()){         
                product_idList.add(rst.getInt("PRODUCT_ID"));
            }
            
            pstmt.close();
            conn.close();
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
    
    public int restock_pharmacy(String pharmacyID, String productID, String quantity){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM products WHERE PRODUCT_ID=?");
            pstmt.setString(1, productID);
            ResultSet rst = pstmt.executeQuery();

            while(rst.next()){         
                product.product_id = rst.getInt("PRODUCT_ID");
                product.product_name = rst.getString("PRODUCT_NAME");
                product.strength = rst.getDouble("STRENGTH");
                product.dosage_form = rst.getString("DOSAGE_FORM");
                product.supplier_name = rst.getString("SUPPLIER_NAME");
            }
            
            String selectSQL = "SELECT QUANTITY FROM stocks WHERE PRODUCT_ID = ? AND PRODUCT_NAME = ? AND PHARMACY_ID = ? AND EXPIRATION_DATE = CURRENT_DATE() + INTERVAL 1 YEAR";
            pstmt = conn.prepareStatement(selectSQL);
            pstmt.setString(1, productID);
            pstmt.setString(2, product.product_name);
            pstmt.setString(3, pharmacyID);
            rst = pstmt.executeQuery();

            if (rst.next()) {
                int currentQuantity = rst.getInt("QUANTITY");
                int newQuantity = currentQuantity + Integer.parseInt(quantity);

                pstmt = conn.prepareStatement("UPDATE stocks SET QUANTITY = ? WHERE PRODUCT_ID = ? AND PRODUCT_NAME = ? AND PHARMACY_ID = ? AND EXPIRATION_DATE = CURRENT_DATE() + INTERVAL 1 YEAR");
                pstmt.setInt(1, newQuantity);
                pstmt.setString(2, productID);
                pstmt.setString(3, product.product_name);
                pstmt.setString(4, pharmacyID);
                pstmt.executeUpdate();
            }
            else{
                pstmt = conn.prepareStatement("SELECT MAX(STOCK_ID) + 1 AS new_id FROM stocks");
                rst = pstmt.executeQuery();
                rst.next();
                int stock_id = rst.getInt(1);
                
                int batch_num;
                pstmt = conn.prepareStatement("SELECT BATCH_NUMBER FROM stocks WHERE EXPIRATION_DATE = CURRENT_DATE() + INTERVAL 1 YEAR");
                rst = pstmt.executeQuery();

                if (rst.next()) {
                    batch_num = rst.getInt("BATCH_NUMBER");
                } else {
                    pstmt = conn.prepareStatement("SELECT MAX(BATCH_NUMBER) + 1 AS new_batch FROM stocks");
                    rst = pstmt.executeQuery();
                    rst.next();
                    batch_num = rst.getInt(1);
                }

                pstmt = conn.prepareStatement("INSERT INTO stocks (STOCK_ID, PRODUCT_ID, PRODUCT_NAME, QUANTITY, PHARMACY_ID, BATCH_NUMBER, EXPIRATION_DATE) VALUES (?, ?, ?, ?, ?, ?, CURRENT_DATE() + INTERVAL 1 YEAR)");
                pstmt.setInt(1, stock_id);
                pstmt.setInt(2, product.product_id);
                pstmt.setString(3, product.product_name);
                pstmt.setString(4, quantity);
                pstmt.setString(5, pharmacyID);
                pstmt.setInt(6, batch_num);
                pstmt.executeUpdate();
            }
            pstmt.close();
            conn.close();
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
    
    public static void main(String args[]){
        restockProducts A = new restockProducts();
        /*int status = A.recommended_restock("1");
        for(int i = 0; i < A.product_idList.size(); i++){
            System.out.println(A.product_idList.get(i));
        }*/
        int status = A.restock_pharmacy("1", "1", "100");
        System.out.println(status);
    }
}