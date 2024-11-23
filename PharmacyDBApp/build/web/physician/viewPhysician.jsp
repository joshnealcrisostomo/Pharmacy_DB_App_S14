<%-- 
    Document   : viewPhysician
    Created on : Nov 17, 2024, 1:29:00 PM
    Author     : dulat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, recordManagement.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Physician Records</title>
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
        .table-container {
            max-height: 300px;
            overflow-y: auto;
            border: 1px solid #ddd;
            border-radius: 8px;
        }
    </style>
</head>
<body>
    <header>
        <h1>VIEW PHYSICIAN RECORDS</h1>
    </header>

    <div class="form-container">
        <jsp:useBean id="C" class="recordManagement.physicians" scope="session" />
        <h3>Physician List</h3>

        <!-- Filter Form -->
        <form action="viewPhysician.jsp" method="get">
            <label for="field">Filter (By Fields):</label>
            <input type="text" id="field" name="field">
            <input type="submit" value="Update Table">
        </form>

        <%  
            String v_fields = request.getParameter("field");
            if (v_fields != null && !v_fields.trim().isEmpty()) {
                C.filter_physician(v_fields.toUpperCase());
            } else {
                C.physician_list();
            }
        %>
        
        <div class="table-container">
            <table>
                <tr>
                    <%  
                        if (!C.physician_idList.isEmpty()) { %>
                            <th>Physician ID</th>
                    <%  }
                        if (!C.first_nameList.isEmpty()) { %>
                            <th>First Name</th>
                    <%  }
                        if (!C.license_numList.isEmpty()) { %>
                            <th>License Number</th>
                    <%  }
                        if (!C.phone_numberList.isEmpty()) { %>
                            <th>Phone Number</th>
                    <%  }
                        
                        if (!C.addressList.isEmpty()) { %>
                            <th>Address of Practice</th>
                    <%  }
                    %>
                </tr>
                
                <%  
                    int maxSize = 0;
                    if (!C.physician_idList.isEmpty()) maxSize = Math.max(maxSize, C.physician_idList.size());
                    if (!C.first_nameList.isEmpty()) maxSize = Math.max(maxSize, C.first_nameList.size());
                    if (!C.license_numList.isEmpty()) maxSize = Math.max(maxSize, C.license_numList.size());                    
                    if (!C.phone_numberList.isEmpty()) maxSize = Math.max(maxSize, C.phone_numberList.size());
                    if (!C.addressList.isEmpty()) maxSize = Math.max(maxSize, C.addressList.size());

                    for (int i = 0; i < maxSize; i++) { 
                %>
                    <tr>
                        <%  
                            if (i < C.physician_idList.size()) { %>
                                <td><%= C.physician_idList.get(i) %></td>
                        <%  }
                            if (i < C.first_nameList.size()) { %>
                                <td><%= C.first_nameList.get(i) %></td>
                        <%  }
                            if (i < C.license_numList.size()) { %>
                                <td><%= C.license_numList.get(i) %></td>
                        <%  }
                            if (i < C.phone_numberList.size()) { %>
                                <td><%= C.phone_numberList.get(i) %></td>
                        <%  }
                            if (i < C.addressList.size()) { %>
                                <td><%= C.addressList.get(i) %></td>
                        <%  }
                        %>
                    </tr>
                <%  
                    } 
                    if (maxSize == 0) { 
                %>
                    <tr>
                        <td>No table data available</td>
                    </tr>
                <%  
                    } 
                %>
            </table>
        </div>
    </div>

    <div class="form-container">
        <a href="managePhysician.html" class="return-btn">Return to Manage Physician Records Menu</a>
    </div>

    <footer>
        &copy; 2024. Crisostomo, Lim, Dularte, Sadey.
    </footer>
</body>
</html>
