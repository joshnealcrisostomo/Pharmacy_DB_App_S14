<%-- 
    Document   : restocking_product
    Created on : Nov 23, 2024, 10:19:21 AM
    Author     : dulat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, transactions.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Stock to a Pharmacy</title>
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
        .form-container input[type="submit"] {
            background-color: #3c8dbc;
            color: white;
            font-weight: bold;
            cursor: pointer;
            padding: 1rem 2rem;
            border-radius: 8px;
            border: none;
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
        <h1>Add Stock to a Pharmacy</h1>
    </header>

    <div class="form-container">
        <jsp:useBean id="A" class="transactions.restockProducts" scope="page" />
        <%
            String v_pharmacy_id = request.getParameter("pharmacy_id2");
            String v_product_id = request.getParameter("product_id");
            String v_quantity = request.getParameter("quantity");

            if (v_product_id != null && !v_product_id.trim().isEmpty()) {
                if (A.product.check_IDNum(v_product_id) == 2) {
                    if (Integer.valueOf(v_quantity) > 0) {
                        int status = A.restock_pharmacy(v_pharmacy_id, v_product_id, v_quantity);
                        if (status == 1) {
        %>
        <h2>Successfully Stocked the Product</h2>
        <% 
                        } else {
        %>
        <h2>Failed to Stock Product. An Error Occurred</h2>
        <% 
                        }
                    } else {
        %>
        <h2>Failed to Stock Product. Quantity Cannot Be Negative</h2>
        <% 
                    }
                } else {
        %>
        <h2>Failed to Stock Product. Product ID Not Found</h2>
        <% 
                }
            } else {
        %>
        <h2>Failed to Stock Product. Product ID Cannot Be Empty/Null</h2>
        <% 
            } 
        %>
    </div>

    <div class="form-container">
        <a href="restockProducts.jsp" class="return-btn">Return to Restocking and Product Allocation Menu</a>
        <a href="index.html" class="return-btn">Return to Main Menu</a>
    </div>

    <footer>
        &copy; 2024. Crisostomo, Lim, Dularte, Sadey.
    </footer>
</body>
</html>
