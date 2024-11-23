<%-- 
    Document   : dispenseProducts
    Created on : Nov 23, 2024, 2:05:32 PM
    Author     : dulat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, transactions.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>DISPENSE PRODUCT/S TO A PATIENT</title>
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
        h2, h3 {
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
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 2rem 0;
            text-align: left;
        }
        table th, table td {
            padding: 0.8rem;
            border: 1px solid #ddd;
        }
        table th {
            background-color: #3c8dbc;
            color: white;
        }
        input[type="text"], input[type="submit"] {
            padding: 0.8rem;
            margin: 0.5rem 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            width: calc(100% - 1.6rem);
        }
        input[type="submit"] {
            background-color: #3c8dbc;
            color: white;
            font-weight: bold;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #2a6a8d;
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
            margin-top: 2rem;
            color: black;
        }
    </style>
</head>
<body>
    <header>
        <h1>DISPENSE PRODUCT/S TO A PATIENT</h1>
    </header>

    <div class="form-container">
        <jsp:useBean id="A" class="transactions.dispenseProducts" scope="page" />
        <form action="dispenseProducts.jsp">
            <label for="prescription_id">Enter Prescription ID (if none, leave blank or put N/A):</label>
            <input type="text" id="prescription_id" name="prescription_id">
            <input type="submit" value="Enter">
        </form>
    </div>

    <div class="form-container">
        <%
            String v_prescription_id = request.getParameter("prescription_id");

            if (v_prescription_id != null && !v_prescription_id.trim().isEmpty()) {
                if (A.product_prescription(v_prescription_id) == 2) {
        %>
        <h3>Products Required in the Prescription:</h3>
        <table>
            <tr>
                <th>Product Name</th>
                <th>Strength</th>
                <th>Dosage Form</th>
                <th>Quantity Required</th>
            </tr>
            <tr>
                <td><%= A.product.product_name %></td>
                <td><%= A.product.strength %></td>
                <td><%= A.product.dosage_form %></td>
                <td><%= A.quantity %></td>
            </tr>
        </table>
        <form action="dispensing_productsPrs.jsp">
            <input type="hidden" id="prescription_id2" name="prescription_id2" value="<%= v_prescription_id %>">
            <input type="submit" value="Proceed to Dispense">
        </form>
        <% 
                } else { 
        %>
        <p>No Products Required in the Given Prescription. Proceed below for OTC Dispensing</p>
        <% 
                } 
            } 

            if (v_prescription_id == null || v_prescription_id.trim().isEmpty() || A.product_prescription(v_prescription_id) == 1) {
                A.available_products();
        %>
        <h3>Available Products for Dispensing (If No Prescription/Products in Prescription)</h3>
        <table>
            <tr>
                <th>Product Name</th>
                <th>Strength</th>
                <th>Dosage Form</th>
            </tr>
            <%
            for (int i = 0; i < A.product.product_idList.size(); i++) {
            %>
            <tr>
                <td><%= A.product.product_nameList.get(i) %></td>
                <td><%= A.product.strengthList.get(i) %></td>
                <td><%= A.product.dosage_formList.get(i) %></td>
            </tr>
            <%
            }
            %>
        </table>
        <form action="dispensing_productsOTC.jsp">
            <label for="product_id">Enter Product ID and Quantity to Dispense:</label>
            <input type="text" id="product_id" name="product_id" placeholder="Product ID">
            <input type="text" id="quantity" name="quantity" placeholder="Quantity">
            <input type="submit" value="Proceed to Dispense">
        </form>
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
