<%-- 
    Document   : adding_patient
    Created on : Nov 17, 2024, 12:09:11 PM
    Author     : dulat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, recordManagement.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add a Patient Record</title>
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
        h3 {
            text-align: center;
            color: #000000;
            margin-bottom: 1.0rem;
        }
        .form-container {
            display: grid;
            margin: 2rem auto;
            width: 90%;
            max-width: 600px;
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
        <h1>ADD PATIENT RECORD</h1>
    </header>
    <div class="form-container">
        <jsp:useBean id="A" class="recordManagement.patients" scope="session" />
        
        <%  
            String v_first_name = request.getParameter("first_name");
            String v_last_name = request.getParameter("last_name");
            String v_phone_number = request.getParameter("phone_number");
            String v_city = request.getParameter("city");
            String v_sex = request.getParameter("sex");
            
            A.first_name = v_first_name;
            A.last_name = v_last_name;
            A.phone_number = v_phone_number;
            A.city = v_city;
            A.sex = v_sex;

            int status = A.add_patient();
        %>
        <% if (status == 1) { %>
            <h3>Added Patient Record Successfully</h3>
        <% } else { %>
            <h3>Failed to Add Patient Record</h3>
        <% } %>
    </div>
    <div class="form-container">
        <form action="addPatient.html">
            <input type="submit" value="Add Another Patient Record">
        </form>
    </div>
    <div class="form-container">
        <a href="managePatient.html" class="return-btn">Return to Manage Patient Records Menu</a>
    </div>
    <footer>
        &copy; 2024. Crisostomo, Lim, Dularte, Sadey.
    </footer>
</body>
</html>
