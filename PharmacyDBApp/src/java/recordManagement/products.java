/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recordManagement;

/**
 *
 * @author dulat
 */
import java.util.*;
import java.sql.*;

public class products {
    
    public int product_id;
    public String product_name;
    public double strength;
    public String dosage_form;
    public String supplier_name;
    
    public ArrayList<Integer> product_idList = new ArrayList<>();
    public ArrayList<String> product_nameList = new ArrayList<>();
    public ArrayList<Double> strengthList = new ArrayList<>();
    public ArrayList<String> dosage_formList = new ArrayList<>();
    public ArrayList<String> supplier_nameList = new ArrayList<>();
    
    public products(){}
    
    public int product_list(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM products");
            ResultSet rst = pstmt.executeQuery();
            
            product_idList.clear();
            product_nameList.clear();
            strengthList.clear();
            dosage_formList.clear();
            supplier_nameList.clear();
            
            while(rst.next()){
                product_id = rst.getInt("PRODUCT_ID");
                product_name = rst.getString("PRODUCT_NAME");
                strength = rst.getDouble("STRENGTH");
                dosage_form = rst.getString("DOSAGE_FORM");
                supplier_name = rst.getString("SUPPLIER_NAME");
               
                product_idList.add(product_id);
                product_nameList.add(product_name);
                strengthList.add(strength);
                dosage_formList.add(dosage_form);
                supplier_nameList.add(supplier_name);
            }
            pstmt.close();
            conn.close();
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int check_IDNum(String IDNum){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT PRODUCT_ID FROM products");
            ResultSet rst = pstmt.executeQuery();
            
            ArrayList<Integer> existingNums = new ArrayList<>();
            
            
            while(rst.next()){
                product_id = rst.getInt("PRODUCT_ID");
                existingNums.add(product_id);
            }
            pstmt.close();
            conn.close();
            
            if(existingNums.contains(Integer.valueOf(IDNum))){
                return 2;
            }
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        
    }    
    
    public int add_product(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(PRODUCT_ID) + 1 AS new_id FROM products");
            ResultSet rst = pstmt.executeQuery();
            while(rst.next()){
                product_id = rst.getInt("new_id");
            }
            
            pstmt = conn.prepareStatement("INSERT INTO products (product_id, product_name, strength, dosage_form, supplier_name) VALUE (?, ?, ?, ?, ?)");
            pstmt.setInt(1, product_id);
            pstmt.setString(2, product_name);
            pstmt.setDouble(3, strength);
            pstmt.setString(4, dosage_form);
            pstmt.setString(5, supplier_name);
            
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int remove_product(int ID){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
                     
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
    
    public int update_product(int ID, String field, String value){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("UPDATE products SET " + field + "=? WHERE product_id=?");
            pstmt.setString(1, value);
            pstmt.setInt(2, ID);
            pstmt.executeUpdate();
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
    
    public int filter_product(String fields){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            String[] allowedFields = {"PRODUCT_ID", "PRODUCT_NAME", "STRENGTH", "DOSAGE_FORM", "SUPPLIER_NAME"};
            StringBuilder validFields = new StringBuilder();

            for (String field : fields.split(" ")) {
                for (String allowedField : allowedFields) {
                    if (field.equalsIgnoreCase(allowedField)) {
                        if (validFields.length() > 0) validFields.append(", ");
                        validFields.append(field.toUpperCase());
                        break;
                    }
                }
            }

            PreparedStatement pstmt = conn.prepareStatement("SELECT " + validFields + " FROM products");
            ResultSet rst = pstmt.executeQuery();
            
            product_idList.clear();
            product_nameList.clear();
            strengthList.clear();
            dosage_formList.clear();
            supplier_nameList.clear();
            
            while (rst.next()) {
                if (fields.contains("PRODUCT_ID")) {
                    product_idList.add(rst.getInt("PRODUCT_ID"));
                }
                if (fields.contains("PRODUCT_NAME")) {
                    product_nameList.add(rst.getString("PRODUCT_NAME"));
                }
                if (fields.contains("STRENGTH")) {
                    strengthList.add(rst.getDouble("STRENGTH"));
                }
                if (fields.contains("DOSAGE_FORM")) {
                    dosage_formList.add(rst.getString("DOSAGE_FORM"));
                }
                if (fields.contains("SUPPLIER_NAME")) {
                    supplier_nameList.add(rst.getString("SUPPLIER_NAME"));
                }
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

    }
}
