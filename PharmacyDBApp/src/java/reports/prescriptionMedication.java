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

public class prescriptionMedication {
    
    public int prescription_id;
    public int physician_id;
    public int patient_id;
    public String prescription_date;
    
    public ArrayList<Integer> prescription_idList = new ArrayList<>();
    public ArrayList<Integer> physician_idList = new ArrayList<>();
    public ArrayList<Integer> patient_idList = new ArrayList<>();
    public ArrayList<String> prescription_dateList = new ArrayList<>();
    
    public products product = new products();
    public physicians physician = new physicians();
    
    public int num_prescriptions;
    
    public prescriptionMedication(){}
    
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
    
    public int get_medications(String month, String year){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            int monthNum = get_month(month);
            
            String medicationQuery = "SELECT * FROM prescriptions pr\n" +
                "JOIN physicians ph ON pr.PHYSICIAN_ID = ph.PHYSICIAN_ID\n" +
                "LEFT JOIN prescriptions_has_products php ON pr.PRESCRIPTION_ID = php.prescriptions_PRESCRIPTION_ID\n" +
                "LEFT JOIN products p ON php.products_PRODUCT_ID = p.PRODUCT_ID\n" +
                "WHERE MONTH(pr.PRESCRIPTION_DATE) = ? AND YEAR(pr.PRESCRIPTION_DATE) = ?";
            PreparedStatement pstmt = conn.prepareStatement(medicationQuery);
            pstmt.setInt(1, monthNum);
            pstmt.setString(2, year);
            ResultSet rst = pstmt.executeQuery();
            
            prescription_idList.clear();
            physician_idList.clear();
            patient_idList.clear();
            prescription_dateList.clear();
            
            product.product_idList.clear();
            product.product_nameList.clear();
            product.strengthList.clear();
            product.dosage_formList.clear();
            product.supplier_nameList.clear();
            
            physician.physician_idList.clear();
            physician.first_nameList.clear();
            physician.license_numList.clear();
            physician.phone_numberList.clear();
            physician.addressList.clear();
            
            while(rst.next()){
                
                prescription_id = rst.getInt("PRESCRIPTION_ID");
                physician_id = rst.getInt("PHYSICIAN_ID");
                patient_id = rst.getInt("PATIENT_ID");
                prescription_date = rst.getString("PRESCRIPTION_DATE");
                
                prescription_idList.add(prescription_id);
                physician_idList.add(physician_id);
                patient_idList.add(patient_id);
                prescription_dateList.add(prescription_date);
                
                product.product_id = rst.getInt("PRODUCT_ID");
                product.product_name = rst.getString("PRODUCT_NAME");
                product.strength = rst.getDouble("STRENGTH");
                product.dosage_form = rst.getString("DOSAGE_FORM");
                product.supplier_name = rst.getString("SUPPLIER_NAME");
               
                product.product_idList.add(product.product_id);
                product.product_nameList.add(product.product_name);
                product.strengthList.add(product.strength);
                product.dosage_formList.add(product.dosage_form);
                product.supplier_nameList.add(product.supplier_name);
                
                physician.physician_id = rst.getInt("PHYSICIAN_ID");
                physician.first_name = rst.getString("FIRST_NAME");
                physician.license_num = rst.getString("LICENSE_NUMBER");
                physician.phone_number = rst.getString("PHONE_NUMBER");
                physician.address = rst.getString("ADDRESS_OF_PRACTICE");
                
                physician.physician_idList.add(physician.physician_id);
                physician.first_nameList.add(physician.first_name);
                physician.license_numList.add(physician.license_num);
                physician.phone_numberList.add(physician.phone_number);
                physician.addressList.add(physician.address);
                
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