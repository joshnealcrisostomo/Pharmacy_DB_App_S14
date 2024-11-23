/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recordManagement;

import java.util.*;
import java.sql.*;

/**
 *
 * @author dulat
 */
public class physicians {
    
    public int physician_id;
    public String first_name;
    public String phone_number;
    public String license_num;
    public String address;
    
    public ArrayList<Integer> physician_idList = new ArrayList<>();
    public ArrayList<String> first_nameList = new ArrayList<>();
    public ArrayList<String> phone_numberList = new ArrayList<>();
    public ArrayList<String> license_numList = new ArrayList<>();
    public ArrayList<String> addressList = new ArrayList<>();
    
    public physicians(){}
    
    public int physician_list(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM physicians");
            ResultSet rst = pstmt.executeQuery();
            
            physician_idList.clear();
            first_nameList.clear();
            license_numList.clear();
            phone_numberList.clear();
            addressList.clear();
            
            while(rst.next()){
                physician_id = rst.getInt("PHYSICIAN_ID");
                first_name = rst.getString("FIRST_NAME");
                license_num = rst.getString("LICENSE_NUMBER");                
                phone_number = rst.getString("PHONE_NUMBER");
                address = rst.getString("ADDRESS_OF_PRACTICE");
               
                physician_idList.add(physician_id);
                first_nameList.add(first_name);
                license_numList.add(license_num);
                phone_numberList.add(phone_number);
                addressList.add(address);
            }
            pstmt.close();
            conn.close();
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int check_licenseNum(String licenseNum){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT LICENSE_NUMBER FROM physicians");
            ResultSet rst = pstmt.executeQuery();
            
            StringBuilder existingNums = new StringBuilder();
            
            while(rst.next()){
                license_num = rst.getString("LICENSE_NUMBER");
                existingNums.append(license_num);
            }
            pstmt.close();
            conn.close();
            
            if(existingNums.toString().contains(licenseNum)){
                return 2;
            }
            
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
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT PHYSICIAN_ID FROM physicians");
            ResultSet rst = pstmt.executeQuery();
            
            ArrayList<Integer> existingNums = new ArrayList<>();
            
            while(rst.next()){
                physician_id = rst.getInt("PHYSICIAN_ID");
                existingNums.add(physician_id);
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
    
    public int add_physician(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(PHYSICIAN_ID) + 1 AS new_id FROM physicians");
            ResultSet rst = pstmt.executeQuery();
            while(rst.next()){
                physician_id = rst.getInt("new_id");
            }
            
            pstmt = conn.prepareStatement("INSERT INTO physicians (physician_id, first_name, license_number, phone_number, address_of_practice) VALUE (?, ?, ?, ?, ?)");
            pstmt.setInt(1, physician_id);
            pstmt.setString(2, first_name);
            pstmt.setString(3, license_num);
            pstmt.setString(4, phone_number);
            pstmt.setString(5, address);
            
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int remove_physician(int ID){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM physicians WHERE PHYSICIAN_ID=?");
            pstmt.setInt(1, ID);
            pstmt.executeUpdate();
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
    
    public int update_physician(int ID, String field, String value){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("UPDATE physicians SET " + field + "=? WHERE PHYSICIAN_ID=?");
            pstmt.setString(1, value);
            pstmt.setInt(2, ID);
            pstmt.executeUpdate();
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
    
    public int filter_physician(String fields){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            String[] allowedFields = {"PHYSICIAN_ID", "FIRST_NAME", "LICENSE_NUMBER", "PHONE_NUMBER", "CITY", "ADDRESS_OF_PRACTICE"};
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

            PreparedStatement pstmt = conn.prepareStatement("SELECT " + validFields + " FROM physicians");
            ResultSet rst = pstmt.executeQuery();
            
            physician_idList.clear();
            first_nameList.clear();
            license_numList.clear();
            phone_numberList.clear();
            addressList.clear();
            
            while (rst.next()) {
                if (fields.contains("PATIENT_ID")) {
                    physician_idList.add(rst.getInt("PATIENT_ID"));
                }
                if (fields.contains("FIRST_NAME")) {
                    first_nameList.add(rst.getString("FIRST_NAME"));
                }
                if (fields.contains("LICENSE_NUMBER")) {
                    license_numList.add(rst.getString("LICENSE_NUMBER"));
                }
                if (fields.contains("PHONE_NUMBER")) {
                    phone_numberList.add(rst.getString("PHONE_NUMBER"));
                }
                if (fields.contains("ADDRESS_OF_PRACTICE")) {
                    addressList.add(rst.getString("ADDRESS_OF_PRACTICE"));
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
        physicians C = new physicians();
    }
    
}
