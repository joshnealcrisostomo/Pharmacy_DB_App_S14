<%-- 
    Document   : assignPatient
    Created on : Nov 18, 2024, 4:52:07 PM
    Author     : dulat
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        .form-container input {
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
        .table-container {
            max-height: 300px;
            overflow-y: auto;
            border: 1px solid #ddd;
            border-radius: 8px;
            margin-top: 1rem;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 1rem;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 0.8rem;
            text-align: left;
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
        .square {
            padding: 1rem;
            border-radius: 8px;
            background-color: #f4f4f9;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
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
        <h1>Handle Patient and Physician Assignment</h1>
    </header>

    <div class="form-container">
        <jsp:useBean id="A" class="transactions.handlePatients" scope="page" />

        <form action="handlePatient.jsp" method="GET">
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
        %>
                    <h3>Patient Information</h3>
                    
                    <%
                        A.patient_info(v_patient_id);
                    %>
                    
                    <div class="table-container">
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
                    </div>

        <%            
                    if (A.assigned_physician(v_patient_id) == 2) {
        %>
                        <h3>The Physician Assigned to this Patient</h3>
                        
                        <%
                            A.physician_info(A.license_number.toString());
                        %>
                        
                        <div class="table-container">
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
                        </div>
        <%          
                    } else {
        %>
                        <h2>This Patient Has No Assigned Physician</h2>
                        <form action="assigning_patient.jsp" method="GET">
                            <label for="license_number">Assign a physician to this patient (Enter Physician License Number):</label>
                            <input type="text" id="license_number" name="license_number" required>
                            <input type="hidden" id="patient_id2" name="patient_id2" value="<%= v_patient_id %>">
                            <input type="submit" value="Assign">
                        </form>
        <%
                    }

                A.get_assignments();
        %>
        
                <h3>Patients With Assigned Physicians</h3>
                    <table border="1">
                        <tr>
                            <th>Patient ID</th>
                            <th>Patient First Name</th>
                            <th>Patient Last Name</th>
                            <th>Physician ID</th>
                            <th>Physician First Name</th>
                            <th>Physician License Number</th>
                        </tr>
                        <%
                            for (int i = 0; i < A.patient_idList.size(); i++) {
                                A.patient_info(A.patient_idList.get(i).toString());
                                A.assigned_physician(A.patient_idList.get(i).toString());
                                A.physician_info(A.license_number);
                        %>
                        <tr>
                            <td><%= A.patient.patient_id %></td>
                            <td><%= A.patient.first_name %></td>
                            <td><%= A.patient.last_name %></td>
                            <td><%= A.physician.physician_id %></td>
                            <td><%= A.physician.first_name %></td>
                            <td><%= A.physician.license_num %></td>
                        </tr>
                        <%
                            }
                        %>
                    </table>

        <%
                    A.get_unassigned();
                    
        %>
                    <h3>Patients With No Assigned Physicians</h3>
                    <table border="1">
                        <tr>
                            <th>Patient ID</th>
                            <th>Patient First Name</th>
                            <th>Patient Last Name</th>
                        </tr>
                        <%
                            for (int i = 0; i < A.patient_idList.size(); i++) {
                                A.patient_info(A.patient_idList.get(i).toString());
                        %>
                        <tr>
                            <td><%= A.patient.patient_id %></td>
                            <td><%= A.patient.first_name %></td>
                            <td><%= A.patient.last_name %></td>
                        </tr>
                        <%
                            }
                        %>
                    </table>
        <%-- Further patient and physician assignments tables here --%>
        <% 
                } else { 
        %>
                <p>Patient Not Found in Records</p>
                <form action="patients/addPatient.html" method="GET">
                    <input type="submit" value="Add Patient to Patient Records">
                </form>
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
