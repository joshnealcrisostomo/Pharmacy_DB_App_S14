<%-- 
    Document   : inventory_needs
    Created on : Nov 21, 2024, 8:12:35 PM
    Author     : dulat
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, reports.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Physician Prescription and Medication Report</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
            color: #333;
        }
        header {
            background-color: #3c8dbc;
            color: white;
            text-align: center;
            padding: 1rem 0;
        }
        h2 {
            text-align: center;
            color: #000000;
            margin-bottom: 1rem;
        }
        .form-container {
            display: grid;
            margin: 2rem auto;
            width: 90%;
            max-width: 800px;
            background-color: white;
            padding: 1rem;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .form-container input, .form-container select {
            width: 100%;
            padding: 0.8rem;
            margin: 0.5rem 0;
            border-radius: 8px;
            border: 1px solid #ddd;
            box-sizing: border-box;
        }
        .form-container input[type="submit"] {
            background-color: #3c8dbc;
            color: white;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s ease, box-shadow 0.3s ease;
        }
        .form-container input[type="submit"]:hover {
            background-color: #2a6a8d;
            box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.2);
        }
        table {
            width: 90%;
            margin: 2rem auto;
            border-collapse: collapse;
            border: 1px solid #ddd;
            background-color: white;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 0.8rem;
            text-align: left;
            border: 1px solid #ddd;
        }
        th {
            background-color: #3c8dbc;
            color: white;
        }
        footer {
            text-align: center;
            font-style: italic;
            font-size: 12px;
            padding: 1rem;
            color: black;
            margin-top: 2rem;
        }
        .return-btn {
            display: inline-block;
            text-decoration: none;
            color: #fff;
            background-color: #800000;
            padding: 1rem 2rem;
            border-radius: 8px;
            text-align: center;
            font-weight: bold;
            margin-top: 2rem;
            transition: background-color 0.3s ease, box-shadow 0.3s ease;
        }
        .return-btn:hover {
            background-color: #FF7F7F;
            box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.2);
        }
    </style>
</head>
<body>
    <header>
        <h1>Physician Prescription and Medication Report</h1>
    </header>

    <div class="form-container">
        <jsp:useBean id="A" class="reports.prescriptionMedication" scope="page" />
        
        <h2>Check Medications in Prescriptions</h2>
        <form action="prescription_medication.jsp" method="GET">
            <label for="prescription_month">Enter Date:</label>
            <select id="prescription_month" name="prescription_month">
                <option value="January">January</option>
                <option value="February">February</option>
                <option value="March">March</option>
                <option value="April">April</option>
                <option value="May">May</option>
                <option value="June">June</option>
                <option value="July">July</option>
                <option value="August">August</option>
                <option value="September">September</option>
                <option value="October">October</option>
                <option value="November">November</option>
                <option value="December">December</option>
            </select>
            <input type="text" id="prescription_year" name="prescription_year" required>
            <input type="submit" value="Check">
        </form>
    </div>
    
    <%
        String v_prescription_month = request.getParameter("prescription_month");
        String v_prescription_year = request.getParameter("prescription_year");

        A.get_medications(v_prescription_month, v_prescription_year);
                
        if (A.prescription_idList.size() > 0) {
    %>
    <div class="form-container">
        <h2>Prescription Medications in <%= v_prescription_month %>, <%= v_prescription_year %></h2>
        <table>
            <tr>
                <th>Product Name</th>
                <th>Strength</th>
                <th>Dosage Form</th>
                <th>Prescribed By</th>
                <th>Prescription Date</th>
            </tr>
            <%
                for (int i = 0; i < A.prescription_idList.size(); i++) {
            %>
            <tr>
                <td><%= A.product.product_nameList.get(i) %></td>
                <td><%= A.product.strengthList.get(i) %></td>
                <td><%= A.product.dosage_formList.get(i) %></td>
                <td><%= A.physician.first_nameList.get(i) %></td>
                <td><%= A.prescription_dateList.get(i) %></td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
    <%
        } else {
    %>
    <div class="form-container">
        <h2>No Patient Prescriptions at Selected Date</h2>
    </div>
    <%
        }
    %>

    <div class="form-container">
        <a href="index.html" class="return-btn">Return to Main Menu</a>
    </div>

    <footer>
        &copy; 2024. Crisostomo, Lim, Dularte, Sadey.
    </footer>
</body>
</html>
