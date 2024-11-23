<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, recordManagement.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Physician Record</title>
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
    </style>
</head>
<body>
    <header>
        <h1>ADD PHYSICIAN RECORD</h1>
    </header>
    <div class="form-container">
        <jsp:useBean id="C" class="recordManagement.physicians" scope="session" />
        
        <%  
            // get the input values from addPhysician.html
            String v_first_name = request.getParameter("first_name");
            String v_phone_number = request.getParameter("phone_number");
            String v_license_num = request.getParameter("license_number");
            String v_address = request.getParameter("address_of_practice");
            
            // assign the values to the physician object to be added
            C.first_name = v_first_name;
            C.phone_number = v_phone_number;
            C.license_num = v_license_num;
            C.address = v_address;

            // Call the addPhysician method and get status
            int status = C.add_physician();
                
            if (status == 1) {
                out.println("<h3 style='color: green;'>Added Physician Record Successfully</h3>");
            } else {
                out.println("<h3 style='color: red;'>Failed to Add Physician Record</h3>");
            }
        %>

        <form action="addPhysician.html">
            <input type="submit" value="Add Another Physician Record">
        </form>
    </div>

    <div class="form-container">
        <a href="managePhysician.html" class="return-btn">Return to Manage Physician Records Menu</a>
    </div>
        
    <footer>
        &copy; 2024. Crisostomo, Lim, Dularte, Sadey.
    </footer>
</body>
</html>