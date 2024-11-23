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

public class pharmacies {
    
    public int pharmacy_id;
    public String pharmacy_name;
    public int floor;
    public String contact_first_name;
    public String contact_last_name;
    
    public ArrayList<Integer> pharmacy_idList = new ArrayList<>();
    public ArrayList<String> pharmacy_nameList = new ArrayList<>();
    public ArrayList<Integer> floorList = new ArrayList<>();
    public ArrayList<String> contact_firstList = new ArrayList<>();
    public ArrayList<String> contact_lastList = new ArrayList<>();
    
    public pharmacies(){}
    
    public int pharmacies_list(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM pharmacies");
            ResultSet rst = pstmt.executeQuery();
            
            pharmacy_idList.clear();
            pharmacy_nameList.clear();
            floorList.clear();
            contact_firstList.clear();
            contact_lastList.clear();
            
            while(rst.next()){
                pharmacy_id = rst.getInt("PHARMACY_ID");
                pharmacy_name = rst.getString("PHARMACY_NAME");
                floor = rst.getInt("FLOOR");
                contact_first_name = rst.getString("CONTACT_FIRST_NAME");
                contact_last_name = rst.getString("CONTACT_LAST_NAME");
               
                pharmacy_idList.add(pharmacy_id);
                pharmacy_nameList.add(pharmacy_name);
                floorList.add(floor);
                contact_firstList.add(contact_first_name);
                contact_lastList.add(contact_last_name);
            }
            pstmt.close();
            conn.close();
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int add_pharmacy(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(PHARMACY_ID) + 1 AS new_id FROM pharmacies");
            ResultSet rst = pstmt.executeQuery();
            while(rst.next()){
                pharmacy_id = rst.getInt("new_id");
            }
            
            pstmt = conn.prepareStatement("INSERT INTO pharmacies (pharmacy_id, pharmacy_name, floor, contact_first_name, contact_last_name) VALUE (?, ?, ?, ?, ?)");
            pstmt.setInt(1, pharmacy_id);
            pstmt.setString(2, pharmacy_name);
            pstmt.setDouble(3, floor);
            pstmt.setString(4, contact_first_name);
            pstmt.setString(5, contact_last_name);
            
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
        //error checking for double digits
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT PHARMACY_ID FROM pharmacies");
            ResultSet rst = pstmt.executeQuery();
            
            ArrayList<Integer> existingNums = new ArrayList<>();
            
            
            while(rst.next()){
                pharmacy_id = rst.getInt("PHARMACY_ID");
                existingNums.add(pharmacy_id);
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
    
    public int remove_pharmacy(int ID){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM pharmacies WHERE PHARMACY_ID=?");
            pstmt.setInt(1, ID);
            pstmt.executeUpdate();
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
    
    public int update_pharmacy(int ID, String field, String value){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("UPDATE pharmacies SET " + field + "=? WHERE pharmacy_id=?");
            pstmt.setString(1, value);
            pstmt.setInt(2, ID);
            pstmt.executeUpdate();
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
    
    public int filter_pharmacy(String fields){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            String[] allowedFields = {"PHARMACY_ID", "PHARMACY_NAME", "FLOOR", "CONTACT_FIRST_NAME", "CONTACT_LAST_NAME"};
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

            PreparedStatement pstmt = conn.prepareStatement("SELECT " + validFields + " FROM pharmacies");
            ResultSet rst = pstmt.executeQuery();
            
            pharmacy_idList.clear();
            pharmacy_nameList.clear();
            floorList.clear();
            contact_firstList.clear();
            contact_lastList.clear();
            
            while (rst.next()) {
                if (fields.contains("PHARMACY_ID")) {
                    pharmacy_idList.add(rst.getInt("PHARMACY_ID"));
                }
                if (fields.contains("PHARMACY_NAME")) {
                    pharmacy_nameList.add(rst.getString("PHARMACY_NAME"));
                }
                if (fields.contains("FLOOR")) {
                    floorList.add(rst.getInt("FLOOR"));
                }
                if (fields.contains("CONTACT_FIRST_NAME")) {
                    contact_firstList.add(rst.getString("CONTACT_FIRST_NAME"));
                }
                if (fields.contains("CONTACT_LAST_NAME")) {
                    contact_lastList.add(rst.getString("CONTACT_LAST_NAME"));
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
        pharmacies D = new pharmacies();
        
        int maxSize = 0;
        if (!D.pharmacy_idList.isEmpty()) maxSize = Math.max(maxSize, D.pharmacy_idList.size());
        if (!D.pharmacy_nameList.isEmpty()) maxSize = Math.max(maxSize, D.pharmacy_nameList.size());
        if (!D.floorList.isEmpty()) maxSize = Math.max(maxSize, D.floorList.size());
        if (!D.contact_firstList.isEmpty()) maxSize = Math.max(maxSize, D.contact_firstList.size());
        if (!D.contact_lastList.isEmpty()) maxSize = Math.max(maxSize, D.contact_lastList.size());
        
        for(int i = 0; i < maxSize; i++){
            if (!D.pharmacy_idList.isEmpty()){
                System.out.println(D.pharmacy_idList.get(i));
            }
            if (!D.pharmacy_nameList.isEmpty()){
                System.out.println(D.pharmacy_nameList.get(i));
            }
            if (!D.floorList.isEmpty()){
                System.out.println(D.floorList.get(i));
            }
            if (!D.contact_firstList.isEmpty()){
                System.out.println(D.contact_firstList.get(i));
            }
            if (!D.contact_lastList.isEmpty()){
                System.out.println(D.contact_lastList.get(i));
            }
        }
    }
}
