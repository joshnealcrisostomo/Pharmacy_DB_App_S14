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

public class manageInventory {
    
    public int stock_id;
    public int product_id;
    public String product_name;
    public int quantity;
    public int pharmacy_id;
    public int batch_number;
    public String expiration_date;
    
    public ArrayList<Integer> stock_idList = new ArrayList<>();
    public ArrayList<Integer> product_idList = new ArrayList<>();
    public ArrayList<String> product_nameList = new ArrayList<>();
    public ArrayList<Integer> quantityList = new ArrayList<>();
    public ArrayList<Integer> pharmacy_idList = new ArrayList<>();
    public ArrayList<Integer> batch_numberList = new ArrayList<>();
    public ArrayList<String> expiration_dateList = new ArrayList<>();
    
    public pharmacies pharmacy = new pharmacies();
    
    public manageInventory(){}
    
    public int current_stocks(String pharmacyID){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM stocks WHERE PHARMACY_ID=?");
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
                stock_id = rst.getInt("STOCK_ID");
                product_id = rst.getInt("PRODUCT_ID");
                product_name = rst.getString("PRODUCT_NAME");
                quantity = rst.getInt("QUANTITY");
                pharmacy_id = rst.getInt("PHARMACY_ID");
                batch_number = rst.getInt("BATCH_NUMBER");
                expiration_date = rst.getString("EXPIRATION_DATE");
                
                stock_idList.add(stock_id);
                product_idList.add(product_id);
                product_nameList.add(product_name);
                quantityList.add(quantity);
                pharmacy_idList.add(pharmacy_id);
                batch_numberList.add(batch_number);
                expiration_dateList.add(expiration_date);
            }
            System.out.println("Debugging Pharmacy Data:");

            
            pstmt.close();
            conn.close();
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
    
    public int low_stocks(String pharmacyID){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM stocks WHERE PHARMACY_ID=? AND QUANTITY <= 10");
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
                stock_id = rst.getInt("STOCK_ID");
                product_id = rst.getInt("PRODUCT_ID");
                product_name = rst.getString("PRODUCT_NAME");
                quantity = rst.getInt("QUANTITY");
                pharmacy_id = rst.getInt("PHARMACY_ID");
                batch_number = rst.getInt("BATCH_NUMBER");
                expiration_date = rst.getString("EXPIRATION_DATE");

                stock_idList.add(stock_id);
                product_idList.add(product_id);
                product_nameList.add(product_name);
                quantityList.add(quantity);
                pharmacy_idList.add(pharmacy_id);
                batch_numberList.add(batch_number);
                expiration_dateList.add(expiration_date);
            }
            
            
            pstmt.close();
            conn.close();
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
    
    public int remove_expired(String pharmacyID){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM stocks WHERE PHARMACY_ID=? AND EXPIRATION_DATE <= CURRENT_DATE + INTERVAL 7 DAY");
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
                stock_id = rst.getInt("STOCK_ID");
                product_id = rst.getInt("PRODUCT_ID");
                product_name = rst.getString("PRODUCT_NAME");
                quantity = rst.getInt("QUANTITY");
                pharmacy_id = rst.getInt("PHARMACY_ID");
                batch_number = rst.getInt("BATCH_NUMBER");
                expiration_date = rst.getString("EXPIRATION_DATE");
                
                stock_idList.add(stock_id);
                product_idList.add(product_id);
                product_nameList.add(product_name);
                quantityList.add(quantity);
                pharmacy_idList.add(pharmacy_id);
                batch_numberList.add(batch_number);
                expiration_dateList.add(expiration_date);
            }
            
            /*pstmt = conn.prepareStatement("DELETE FROM stocks WHERE PHARMACY_ID=? AND EXPIRATION_DATE <= CURRENT_DATE + INTERVAL 7 DAY");
            pstmt.setString(1, pharmacyID);
            pstmt.executeUpdate();*/
            
            pstmt.close();
            conn.close();
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
    
    public int update_stocks(String pharmacyID) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");

            // find stocks low on quantity
            PreparedStatement pstmtLowStock = conn.prepareStatement("SELECT * FROM stocks WHERE PHARMACY_ID = ? AND QUANTITY <= 10");
            pstmtLowStock.setString(1, pharmacyID);
            ResultSet rstLowStock = pstmtLowStock.executeQuery();

            while (rstLowStock.next()) {
                int lowStockId = rstLowStock.getInt("STOCK_ID");
                int lowQuantity = rstLowStock.getInt("QUANTITY");
                String productId = rstLowStock.getString("PRODUCT_ID");
                String productName = rstLowStock.getString("PRODUCT_NAME");
                String batchNum = rstLowStock.getString("BATCH_NUMBER");
                String expiryDate = rstLowStock.getString("EXPIRATION_DATE");

                // find identical product with higher stock
                PreparedStatement pstmtHighStock = conn.prepareStatement("SELECT * FROM stocks WHERE PRODUCT_ID = ? AND PRODUCT_NAME = ? AND BATCH_NUMBER = ? AND EXPIRATION_DATE = ? AND QUANTITY > 10");
                pstmtHighStock.setString(1, productId);
                pstmtHighStock.setString(2, productName);
                pstmtHighStock.setString(3, batchNum);
                pstmtHighStock.setString(4, expiryDate);

                ResultSet rstHighStock = pstmtHighStock.executeQuery();

                if (rstHighStock.next()) {
                    int highStockId = rstHighStock.getInt("STOCK_ID");
                    int highQuantity = rstHighStock.getInt("QUANTITY");

                    // get average quantity and update both records
                    int totalQuantity = lowQuantity + highQuantity;
                    int newQuantity = totalQuantity / 2;
                    int leftoverQuantity = totalQuantity - newQuantity; // Handle odd quantities

                    PreparedStatement pstmtUpdateLowStock = conn.prepareStatement("UPDATE stocks SET QUANTITY = ? WHERE STOCK_ID = ?");
                    pstmtUpdateLowStock.setInt(1, newQuantity);
                    pstmtUpdateLowStock.setInt(2, lowStockId);
                    pstmtUpdateLowStock.executeUpdate();

                    PreparedStatement pstmtUpdateHighStock = conn.prepareStatement("UPDATE stocks SET QUANTITY = ? WHERE STOCK_ID = ?");
                    pstmtUpdateHighStock.setInt(1, leftoverQuantity);
                    pstmtUpdateHighStock.setInt(2, highStockId);
                    pstmtUpdateHighStock.executeUpdate();

                    pstmtUpdateLowStock.close();
                    pstmtUpdateHighStock.close();
                }

                pstmtHighStock.close();
                rstHighStock.close();
            }

            pstmtLowStock.close();
            rstLowStock.close();
            conn.close();

            return 1;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
 
    public int record_lowStocks(String pharmacyID){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=1q2w3e4r5t");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM stocks WHERE PHARMACY_ID=? AND QUANTITY <= 10");
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
                stock_id = rst.getInt("STOCK_ID");
                product_id = rst.getInt("PRODUCT_ID");
                product_name = rst.getString("PRODUCT_NAME");
                quantity = rst.getInt("QUANTITY");
                pharmacy_id = rst.getInt("PHARMACY_ID");
                batch_number = rst.getInt("BATCH_NUMBER");
                expiration_date = rst.getString("EXPIRATION_DATE");

                stock_idList.add(stock_id);
                product_idList.add(product_id);
                product_nameList.add(product_name);
                quantityList.add(quantity);
                pharmacy_idList.add(pharmacy_id);
                batch_numberList.add(batch_number);
                expiration_dateList.add(expiration_date);
                
                // record in low_stocks_alert table
                PreparedStatement pstmtLowStock = conn.prepareStatement("SELECT MAX(ALERT_ID) + 1 AS new_id FROM low_stocks_alerts");
                ResultSet rstLowStock = pstmtLowStock.executeQuery();
                rstLowStock.next();
                int alert_id = rstLowStock.getInt("new_id");
                if(alert_id == 0){
                    alert_id = 1;
                }
                
                pstmtLowStock = conn.prepareStatement("INSERT INTO low_stocks_alerts (ALERT_ID, PHARMACY_ID, PRODUCT_ID, QUANTITY, ALERT_DATETIME) VALUE (?, ?, ?, ?, NOW())");
                pstmtLowStock.setInt(1, alert_id);
                pstmtLowStock.setInt(2, pharmacy_id);
                pstmtLowStock.setInt(3, product_id);
                pstmtLowStock.setInt(4, quantity);
                pstmtLowStock.executeUpdate();
                
                pstmtLowStock.close();
                rstLowStock.close();
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
       manageInventory A = new manageInventory();
       /*A.remove_expired("2");
       for(int i = 0; i < A.stock_idList.size(); i++){
           System.out.println(A.expiration_dateList.get(i));
       }*/
       int status = A.current_stocks("3");
       System.out.println(status);
       /*for(int i = 0; i < A.quantityList.size(); i++){
           System.out.println(A.quantityList.get(i));
       }*/
       for (int i=0; i < A.stock_idList.size();i++){
        System.out.println("id");
        System.out.println(A.stock_idList.get(i));   
       }
       
    }
}