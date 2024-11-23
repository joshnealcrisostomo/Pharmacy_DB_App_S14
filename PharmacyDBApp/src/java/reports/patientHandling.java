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

public class patientHandling {
    
    public int prescription_id;
    public int physician_id;
    public int patient_id;
    public String prescription_date;
    
    public ArrayList<Integer> prescription_idList = new ArrayList<>();
    public ArrayList<Integer> physician_idList = new ArrayList<>();
    public ArrayList<Integer> patient_idList = new ArrayList<>();
    public ArrayList<String> prescription_dateList = new ArrayList<>();
    
    public patients patient = new patients();
    public physicians physician = new physicians();
    
    public int num_prescriptions;
    
    public patientHandling(){}
    
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
    
    public int patient_prescriptions(String month, String year){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            int monthNum = get_month(month);
            
            String prescriptionQuery = "SELECT * FROM prescriptions pr JOIN patients p "
                    + "ON pr.PATIENT_ID = p.PATIENT_ID JOIN physicians ph "
                    + "ON pr.PHYSICIAN_ID = ph.PHYSICIAN_ID WHERE "
                    + "MONTH(pr.PRESCRIPTION_DATE) = ? AND YEAR(pr.PRESCRIPTION_DATE) = ? "
                    + "ORDER BY pr.PRESCRIPTION_DATE";
            PreparedStatement pstmt = conn.prepareStatement(prescriptionQuery);
            pstmt.setInt(1, monthNum);
            pstmt.setString(2, year);
            ResultSet rst = pstmt.executeQuery();
            
            prescription_idList.clear();
            physician_idList.clear();
            patient_idList.clear();
            prescription_dateList.clear();
            
            patient.patient_idList.clear();
            patient.first_nameList.clear();
            patient.last_nameList.clear();
            patient.phone_numberList.clear();
            patient.cityList.clear();
            patient.sexList.clear();
            
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
                
                patient.patient_id = rst.getInt("PATIENT_ID");
                patient.first_name = rst.getString("p.FIRST_NAME");
                patient.last_name = rst.getString("LAST_NAME");
                patient.phone_number = rst.getString("PHONE_NUMBER");
                patient.city = rst.getString("CITY");
                patient.sex = rst.getString("SEX");
                
                patient.patient_idList.add(patient.patient_id);
                patient.first_nameList.add(patient.first_name);
                patient.last_nameList.add(patient.last_name);
                patient.phone_numberList.add(patient.phone_number);
                patient.cityList.add(patient.city);
                patient.sexList.add(patient.sex);
                
                physician.physician_id = rst.getInt("PHYSICIAN_ID");
                physician.first_name = rst.getString("ph.FIRST_NAME");
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
    
    public int get_numPrescriptions(String month, String year){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            int monthNum = get_month(month);
            
            String prescriptionQuery = "SELECT COUNT(pr.PRESCRIPTION_ID) as NUM_PRESCRIPTIONS "
                    + "FROM prescriptions pr JOIN patients p ON pr.PATIENT_ID = p.PATIENT_ID "
                    + "JOIN physicians ph ON pr.PHYSICIAN_ID = ph.PHYSICIAN_ID WHERE "
                    + "MONTH(pr.PRESCRIPTION_DATE) = ? AND YEAR(pr.PRESCRIPTION_DATE) = ?";
            PreparedStatement pstmt = conn.prepareStatement(prescriptionQuery);
            pstmt.setInt(1, monthNum);
            pstmt.setString(2, year);
            ResultSet rst = pstmt.executeQuery();
            
            while(rst.next()){
                num_prescriptions = rst.getInt("NUM_PRESCRIPTIONS");
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
        patientHandling A = new patientHandling();
        int status = A.patient_prescriptions("March", "2023");
        System.out.println(status);
    }
}