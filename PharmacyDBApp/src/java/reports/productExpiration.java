/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reports;

/**
 *
 * @author dulat
 */

import java.util.*;
import java.sql.*;
import recordManagement.*;

public class productExpiration {
    
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
    
    public productExpiration(){}
    
    public int get_month(String month) {
        switch(month.toLowerCase()) {
            case "january": return 1;
            case "february": return 2;
            case "march": return 3;
            case "april": return 4;
            case "may": return 5;
            case "june": return 6;
            case "july": return 7;
            case "august": return 8;
            case "september": return 9;
            case "october": return 10;
            case "november": return 11;
            case "december": return 12;
            default:
                throw new IllegalArgumentException("Invalid month name: " + month);
        }
    }
    
    public int expired_products(String month, String year){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            int monthNum = get_month(month);
            
            String expirationQuery = "SELECT * FROM stocks s JOIN pharmacies ph ON "
                    + "s.PHARMACY_ID = ph.PHARMACY_ID WHERE MONTH(s.EXPIRATION_DATE) <= ? AND "
                    + "YEAR(s.EXPIRATION_DATE) <= ? ORDER BY s.EXPIRATION_DATE;";
            PreparedStatement pstmt = conn.prepareStatement(expirationQuery);
            pstmt.setInt(1, monthNum);
            pstmt.setString(2, year);
            ResultSet rst = pstmt.executeQuery();
            
            stock_idList.clear();
            product_idList.clear();
            product_nameList.clear();
            quantityList.clear();
            pharmacy_idList.clear();
            batch_numberList.clear();
            expiration_dateList.clear();
            
            pharmacy.pharmacy_nameList.clear();
            
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
                
                pharmacy.pharmacy_name = rst.getString("PHARMACY_NAME");
                
                pharmacy.pharmacy_nameList.add(pharmacy.pharmacy_name);
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
        productExpiration A = new productExpiration();
        int status = A.expired_products("November", "2025");
        System.out.println(status);
    }
}