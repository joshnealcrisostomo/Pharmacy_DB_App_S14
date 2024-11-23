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
public class patients {
    
    public int patient_id;
    public String first_name;
    public String last_name;
    public String phone_number;
    public String city;
    public String sex;
    
    public ArrayList<Integer> patient_idList = new ArrayList<>();
    public ArrayList<String> first_nameList = new ArrayList<>();
    public ArrayList<String> last_nameList = new ArrayList<>();
    public ArrayList<String> phone_numberList = new ArrayList<>();
    public ArrayList<String> cityList = new ArrayList<>();
    public ArrayList<String> sexList = new ArrayList<>();
    
    public patients(){}
    
    public int patient_list(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM patients");
            ResultSet rst = pstmt.executeQuery();
            
            patient_idList.clear();
            first_nameList.clear();
            last_nameList.clear();
            phone_numberList.clear();
            cityList.clear();
            sexList.clear();
            
            while(rst.next()){
                patient_id = rst.getInt("PATIENT_ID");
                first_name = rst.getString("FIRST_NAME");
                last_name = rst.getString("LAST_NAME");
                phone_number = rst.getString("PHONE_NUMBER");
                city = rst.getString("CITY");
                sex = rst.getString("SEX");
               
                patient_idList.add(patient_id);
                first_nameList.add(first_name);
                last_nameList.add(last_name);
                phone_numberList.add(phone_number);
                cityList.add(city);
                sexList.add(sex);
            }
            pstmt.close();
            conn.close();
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int add_patient(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(PATIENT_ID) + 1 AS new_id FROM patients");
            ResultSet rst = pstmt.executeQuery();
            while(rst.next()){
                patient_id = rst.getInt("new_id");
            }
            
            pstmt = conn.prepareStatement("INSERT INTO patients (patient_id, first_name, last_name, phone_number, city, sex) VALUE (?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, patient_id);
            pstmt.setString(2, first_name);
            pstmt.setString(3, last_name);
            pstmt.setString(4, phone_number);
            pstmt.setString(5, city);
            pstmt.setString(6, sex);
            
            pstmt.executeUpdate();
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
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT PATIENT_ID FROM patients");
            ResultSet rst = pstmt.executeQuery();
            
            ArrayList<Integer> existingNums = new ArrayList<>();
            
            while(rst.next()){
                patient_id = rst.getInt("PATIENT_ID");
                existingNums.add(patient_id);
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
    
    public int remove_patient(int ID){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM patients WHERE PATIENT_ID=?");
            pstmt.setInt(1, ID);
            pstmt.executeUpdate();
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
    
    public int update_patient(int ID, String field, String value){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("UPDATE patients SET " + field + "=? WHERE PATIENT_ID=?");
            pstmt.setString(1, value);
            pstmt.setInt(2, ID);
            pstmt.executeUpdate();
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
    
    public int filter_patient(String fields){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            String[] allowedFields = {"PATIENT_ID", "FIRST_NAME", "LAST_NAME", "PHONE_NUMBER", "CITY", "SEX"};
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

            PreparedStatement pstmt = conn.prepareStatement("SELECT " + validFields + " FROM patients");
            ResultSet rst = pstmt.executeQuery();
            
            patient_idList.clear();
            first_nameList.clear();
            last_nameList.clear();
            phone_numberList.clear();
            cityList.clear();
            sexList.clear();
            
            while (rst.next()) {
                if (fields.contains("PATIENT_ID")) {
                    patient_idList.add(rst.getInt("PATIENT_ID"));
                }
                if (fields.contains("FIRST_NAME")) {
                    first_nameList.add(rst.getString("FIRST_NAME"));
                }
                if (fields.contains("LAST_NAME")) {
                    last_nameList.add(rst.getString("LAST_NAME"));
                }
                if (fields.contains("PHONE_NUMBER")) {
                    phone_numberList.add(rst.getString("PHONE_NUMBER"));
                }
                if (fields.contains("CITY")) {
                    cityList.add(rst.getString("CITY"));
                }
                if (fields.contains("SEX")) {
                    cityList.add(rst.getString("SEX"));
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
