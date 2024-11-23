<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, transactions.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Handle Patient and Physician Assignment</title>
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
        h3, h2 {
            text-align: center;
            color: #000000;
            margin-bottom: 1.0rem;
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
        .form-container input,
        .form-container select {
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
            width: 100%;
            border-collapse: collapse;
            margin: 1rem auto;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            text-align: left;
            padding: 0.5rem;
        }
        th {
            background-color: #3c8dbc;
            color: white;
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
        footer {
            text-align: center;
            font-style: italic;
            font-size: 12px;
            padding: 1rem;
            margin-top: 12rem;
            color: black;
            position: relative;
        }
    </style>
</head>
<body>
    <header>
        <h1>HANDLE PATIENT AND PHYSICIAN ASSIGNMENT</h1>
    </header>

    <div class="form-container">
        <jsp:useBean id="A" class="transactions.handlePatients" scope="page" />
        <form action="handlePatients.jsp" method="GET">
            <label for="patient_id">Enter Patient ID:</label>
            <input type="text" id="patient_id" name="patient_id" required>
            <input type="submit" value="Check">
        </form>
    </div>

    <div class="form-container">
        <% 
            String v_patient_id = request.getParameter("patient_id");
            if (v_patient_id != null && !v_patient_id.trim().isEmpty()) {
                if (A.patient.check_IDNum(v_patient_id) == 2) {
                    A.patient_info(v_patient_id);
        %>
        <h3>Patient Information</h3>
        <table>
            <tr>
                <th>Patient ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Phone Number</th>
                <th>City</th>
                <th>Sex</th>
            </tr>
            <tr>
                <td><%= A.patient.patient_id %></td>
                <td><%= A.patient.first_name %></td>
                <td><%= A.patient.last_name %></td>
                <td><%= A.patient.phone_number %></td>
                <td><%= A.patient.city %></td>
                <td><%= A.patient.sex %></td>
            </tr>
        </table>

        <% 
            if (A.assigned_physician(v_patient_id) == 2) { 
                A.physician_info(A.license_number.toString());
        %>
        <h3>The Physician Assigned to this Patient</h3>
        <table>
            <tr>
                <th>Physician ID</th>
                <th>First Name</th>
                <th>License Number</th>
                <th>Phone Number</th>
                <th>Address of Practice</th>
            </tr>
            <tr>
                <td><%= A.physician.physician_id %></td>
                <td><%= A.physician.first_name %></td>
                <td><%= A.physician.license_num %></td>
                <td><%= A.physician.phone_number %></td>
                <td><%= A.physician.address %></td>
            </tr>
        </table>
        <% 
            } else { 
        %>
        <h2>This Patient Has No Assigned Physician</h2>
        <form action="assigning_patient.jsp" method="GET">
            <label for="license_number">Assign a Physician (Enter License Number):</label>
            <input type="text" id="license_number" name="license_number" required>
            <input type="hidden" name="patient_id2" value="<%= v_patient_id %>">
            <input type="submit" value="Assign">
        </form>
        <% 
            } 
        %>
        <%-- Add logic for tables of assignments and unassigned patients --%>
        <% 
                } else { 
        %>
        <h2>Patient Not Found</h2>
        <a href="patients/addPatient.html" class="return-btn">Add Patient</a>
        <% 
                }
            } else { 
        %>
        <h2>Patient ID Cannot Be Null or Empty</h2>
        <% 
            } 
        %>
    </div>

    <div class="form-container">
        <a href="index.html" class="return-btn">Return to Main Menu</a>
    </div>

    <footer>
        &copy; 2024. Crisostomo, Lim, Dularte, Sadey.
    </footer>
</body>
</html>
