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
import java.time.*;
import recordManagement.*;

public class inventoryNeeds {
    
    public int alert_id;
    public int pharmacy_id;
    public int product_id;
    public int quantity;
    public String alert_datetime;
    
    public ArrayList<Integer> alert_idList = new ArrayList<>();
    public ArrayList<Integer> pharmacy_idList = new ArrayList<>();
    public ArrayList<Integer> product_idList = new ArrayList<>();
    public ArrayList<Integer> quantityList = new ArrayList<>();
    public ArrayList<String> alertList = new ArrayList<>();
    
    public pharmacies pharmacy = new pharmacies();
    public products product = new products();
    
    public inventoryNeeds(){}
    
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
    
public int pharmacy_inventories(String week, String month, String year) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_app?useTimezone=true&serverTimezone=UTC&user=root&password=Joshneal2245!");
            System.out.println("Connection Successful");

            int monthNum = get_month(month);
            
            String startDate, endDate;
            switch (week) {
                case "1":
                    startDate = year + "-" + String.format("%02d", monthNum) + "-01";
                    endDate = year + "-" + String.format("%02d", monthNum) + "-07";
                    break;
                case "2":
                    startDate = year + "-" + String.format("%02d", monthNum) + "-08";
                    endDate = year + "-" + String.format("%02d", monthNum) + "-14";
                    break;
                case "3":
                    startDate = year + "-" + String.format("%02d", monthNum) + "-15";
                    endDate = year + "-" + String.format("%02d", monthNum) + "-21";
                    break;
                case "4":
                    startDate = year + "-" + String.format("%02d", monthNum) + "-22";

                    YearMonth yearMonth = YearMonth.of(Integer.parseInt(year), monthNum);
                    endDate = year + "-" + String.format("%02d", monthNum) + "-" + yearMonth.lengthOfMonth();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid week: " + week);
            }
            
            String inventoryQuery = "SELECT * FROM low_stocks_alerts lsa "
                    + "JOIN pharmacies p ON lsa.PHARMACY_ID = p.PHARMACY_ID "
                    + "WHERE DATE(ALERT_DATETIME) BETWEEN ? AND ?";
            PreparedStatement pstmt = conn.prepareStatement(inventoryQuery);
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);
            ResultSet rst = pstmt.executeQuery();

            alert_idList.clear();
            pharmacy_idList.clear();
            product_idList.clear();
            quantityList.clear();
            alertList.clear();
            
            pharmacy.pharmacy_nameList.clear();
            pharmacy.floorList.clear();
            pharmacy.contact_firstList.clear();
            pharmacy.contact_lastList.clear();

            while (rst.next()) {
                alert_idList.add(rst.getInt("ALERT_ID"));
                pharmacy_idList.add(rst.getInt("PHARMACY_ID"));
                product_idList.add(rst.getInt("PRODUCT_ID"));
                quantityList.add(rst.getInt("QUANTITY"));
                alertList.add(rst.getString("ALERT_DATETIME"));

                pharmacy.pharmacy_nameList.add(rst.getString("PHARMACY_NAME"));
                pharmacy.floorList.add(rst.getInt("FLOOR"));
                pharmacy.contact_firstList.add(rst.getString("CONTACT_FIRST_NAME"));
                pharmacy.contact_lastList.add(rst.getString("CONTACT_LAST_NAME"));
            }

            rst.close();
            pstmt.close();

            
            PreparedStatement productStmt = conn.prepareStatement("SELECT * FROM products WHERE PRODUCT_ID = ?");

            product.product_idList.clear();
            product.product_nameList.clear();
            product.strengthList.clear();
            product.dosage_formList.clear();
            product.supplier_nameList.clear();

            for (Integer productId : product_idList) {
                productStmt.setInt(1, productId);
                ResultSet productRst = productStmt.executeQuery();

                if (productRst.next()) {
                    product.product_id = productRst.getInt("PRODUCT_ID");
                    product.product_name = productRst.getString("PRODUCT_NAME");
                    product.strength = productRst.getDouble("STRENGTH");
                    product.dosage_form = productRst.getString("DOSAGE_FORM");
                    product.supplier_name = productRst.getString("SUPPLIER_NAME");

                    product.product_idList.add(product.product_id);
                    product.product_nameList.add(product.product_name);
                    product.strengthList.add(product.strength);
                    product.dosage_formList.add(product.dosage_form);
                    product.supplier_nameList.add(product.supplier_name);
                }

                productRst.close();
            }

            productStmt.close();
            conn.close();

            return 1;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public static void main(String args[]){
        inventoryNeeds A = new inventoryNeeds();
        int status = A.pharmacy_inventories("3","November", "2024");
        System.out.println(status);
        for(int i = 0; i < A.alert_idList.size(); i++){
            System.out.println(A.pharmacy.pharmacy_nameList);
        }
    }
}