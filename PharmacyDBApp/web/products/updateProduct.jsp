<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, recordManagement.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Update a Product Record</title>
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
        <h1>UPDATE PRODUCT RECORD</h1>
    </header>
    <div class="form-container">
        <jsp:useBean id="B" class="recordManagement.products" scope="session" />
        <h3>Product List</h3>
        <div class="table-container">
            <table>
                <tr>
                    <th>Product ID</th>
                    <th>Product Name</th>
                    <th>Strength</th>
                    <th>Dosage Form</th>
                    <th>Supplier Name</th>
                </tr>  
                <%  B.product_list();
                    for(int i = 0; i < B.product_idList.size(); i++){
                %>
                    <tr>
                        <td><%=B.product_idList.get(i)%></td>
                        <td><%=B.product_nameList.get(i)%></td>
                        <td><%=B.strengthList.get(i)%></td>
                        <td><%=B.dosage_formList.get(i)%></td>
                        <td><%=B.supplier_nameList.get(i)%></td>
                    </tr>
                <%  }
                %>
            </table>
        </div>
    </div>

    <div class="form-container">
        <form action="updating_product.jsp">
            <label for="product_id">Enter Product ID to Update:</label><br>
            <input type="text" id="product_id" name="product_id" required><br>
            <label for="field">Select Field to Update:</label><br>
            <select id="field" name="field">
                <option value="PRODUCT_NAME">Product Name</option>
                <option value="STRENGTH">Strength</option>
                <option value="DOSAGE_FORM">Dosage Form</option>
                <option value="SUPPLIER_NAME">Supplier Name</option>
            </select><br><br>
            <label for="new_value">Enter New Value for the Selected Field:</label><br>
            <input type="text" id="new_value" name="new_value" required><br><br>
            <input type="submit" value="Submit">
        </form>
    </div>

    <div class="form-container">
        <a href="manageProduct.html" class="return-btn">Return to Manage Product Records Menu</a>
    </div>

    <footer>
        &copy; 2024. Crisostomo, Lim, Dularte, Sadey.
    </footer>
</body>
</html>
