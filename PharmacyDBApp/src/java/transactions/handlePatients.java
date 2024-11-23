/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transactions;
import java.util.*;
import java.sql.*;
import recordManagement.*;
/**
 *
 * @author dulat
 */
public class handlePatients {
    public int patient_id;
    public int physician_id;
    public String license_number;
    
    public ArrayList<Integer> patient_idList = new ArrayList<>();
    public ArrayList<Integer> physician_idList = new ArrayList<>();
    
    public patients patient = new patients();
    public physicians physician = new physicians();
    
    public handlePatients(){}
    
    public int assigned_physician(String patientID){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT physicians_PHYSICIAN_ID FROM patients_have_physicians WHERE patients_PATIENT_ID=" + patientID);
            ResultSet rst = pstmt.executeQuery();
            
            physician_idList.clear();
            
            while(rst.next()){
                physician_id = rst.getInt("physicians_PHYSICIAN_ID");
                physician_idList.add(physician_id);
            }
            
            if(!physician_idList.isEmpty()){
                pstmt = conn.prepareStatement("SELECT LICENSE_NUMBER FROM physicians WHERE PHYSICIAN_ID=" + physician_id);
                rst = pstmt.executeQuery();
                while(rst.next()){
                    license_number = rst.getString("LICENSE_NUMBER");
                }
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
    
    public int patient_info(String patientID){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM patients WHERE PATIENT_ID=" + patientID);
            ResultSet rst = pstmt.executeQuery();
            
            while(rst.next()){
                patient.patient_id = rst.getInt("PATIENT_ID");
                patient.first_name = rst.getString("FIRST_NAME");
                patient.last_name = rst.getString("LAST_NAME");
                patient.phone_number = rst.getString("PHONE_NUMBER");
                patient.city = rst.getString("CITY");
                patient.sex = rst.getString("SEX");
            }
            pstmt.close();
            conn.close();
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
    
    public int physician_info(String licenseNum){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM physicians WHERE LICENSE_NUMBER=?");
            pstmt.setString(1, licenseNum);
            ResultSet rst = pstmt.executeQuery();
            
            while(rst.next()){
                physician.physician_id = rst.getInt("PHYSICIAN_ID");
                physician.first_name = rst.getString("FIRST_NAME");
                physician.license_num = rst.getString("LICENSE_NUMBER");
                physician.phone_number = rst.getString("PHONE_NUMBER");
                physician.address = rst.getString("ADDRESS_OF_PRACTICE");
            }
            pstmt.close();
            conn.close();
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
    
    public int add_assignment(String patientID, String physicianID) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");

            PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM patients_have_physicians WHERE patients_PATIENT_id = ?");
            pstmt.setString(1, patientID);

            ResultSet rst = pstmt.executeQuery();
            rst.next();
            int count = rst.getInt(1);

            if (count > 0) {
                String updateQuery = "UPDATE patients_have_physicians SET physicians_PHYSICIAN_id = ? WHERE patients_PATIENT_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setString(1, physicianID);
                updateStmt.setString(2, patientID);

                updateStmt.executeUpdate();
                updateStmt.close();
            } else {
                String insertQuery = "INSERT INTO patients_have_physicians (patients_PATIENT_id, physicians_PHYSICIAN_id) VALUES (?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setString(1, patientID);
                insertStmt.setString(2, physicianID);

                insertStmt.executeUpdate();
                insertStmt.close();
            }

            pstmt.close();
            conn.close();

            return 1;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int get_assignments(){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM patients_have_physicians");
            ResultSet rst = pstmt.executeQuery();
            
            patient_idList.clear();
            physician_idList.clear();
            
            while(rst.next()){
                patient_id = rst.getInt("patients_PATIENT_ID");
                physician_id = rst.getInt("physicians_PHYSICIAN_ID");
                
                patient_idList.add(patient_id);
                physician_idList.add(physician_id);
            }
            pstmt.close();
            conn.close();
            
            return 1;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int get_unassigned() {

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
             PreparedStatement stmtPatients = conn.prepareStatement("SELECT PATIENT_ID FROM patients");
             PreparedStatement stmtAssigned = conn.prepareStatement("SELECT patients_PATIENT_ID FROM patients_have_physicians")) {

            System.out.println("Connection Successful");
            patient_idList.clear();

            try (ResultSet rsPatients = stmtPatients.executeQuery()) {
                while (rsPatients.next()) {
                    patient_idList.add(rsPatients.getInt("PATIENT_ID"));
                }
            }

            Set<Integer> assignedIds = new HashSet<>();
            try (ResultSet rsAssigned = stmtAssigned.executeQuery()) {
                while (rsAssigned.next()) {
                    assignedIds.add(rsAssigned.getInt("patients_PATIENT_ID"));
                }
            }

            patient_idList.removeAll(assignedIds);

            return 1;

        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public static void main(String args[]){
        handlePatients A = new handlePatients();
         
            
    }
}