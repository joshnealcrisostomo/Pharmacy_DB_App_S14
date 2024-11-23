<%-- 
    Document   : dispensing_products
    Created on : Nov 23, 2024, 4:39:14 PM
    Author     : dulat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, transactions.*" %>
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
        h2 {
            text-align: center;
            color: #333;
        }
        .container {
            width: 90%;
            max-width: 800px;
            margin: 2rem auto;
            padding: 2rem;
            background: white;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        form {
            text-align: center;
            margin-top: 2rem;
        }
        input[type="submit"] {
            padding: 0.8rem 1.5rem;
            background-color: #3c8dbc;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 1rem;
        }
        input[type="submit"]:hover {
            background-color: #2a6a8d;
        }
        .message {
            text-align: center;
            margin-top: 2rem;
            font-size: 1.2rem;
            color: #333;
        }
        .error {
            color: #ff4d4d;
        }
        .success {
            color: #28a745;
        }
        footer {
            text-align: center;
            margin-top: 2rem;
            font-size: 0.9rem;
            color: #666;
        }
    </style>
</head>
<body>
    <header>
        <h1>Dispense Product to Patient</h1>
    </header>
    <div class="container">
        <jsp:useBean id="A" class="transactions.dispenseProducts" scope="page" />
        
        <%
            String v_product_id = request.getParameter("product_id");
            String v_quantity = request.getParameter("quantity");
            String message = "";
            String messageClass = "";

            if (v_product_id != null && !v_product_id.trim().isEmpty()) {
                if (A.product.check_IDNum(v_product_id) == 2) {
                    if (Integer.parseInt(v_quantity) > 0) {
                        int status = A.dispense_products(v_product_id, v_quantity);
                        if (status == 1) {
                            message = "Successfully Dispensed the Product.";
                            messageClass = "success";
                        } else if (status == 2) {
                            message = "Failed to Dispense Product. Insufficient Stock Quantity.";
                            messageClass = "error";
                        } else {
                            message = "Failed to Dispense Product. An Error Occurred.";
                            messageClass = "error";
                        }
                    } else {
                        message = "Failed to Dispense Product. Quantity Cannot Be Negative.";
                        messageClass = "error";
                    }
                } else {
                    message = "Failed to Dispense Product. Product ID Not Found.";
                    messageClass = "error";
                }
            } else {
                message = "Failed to Dispense Product. Product ID Cannot Be Empty or Null.";
                messageClass = "error";
            }
        %>
        
        <p class="message <%= messageClass %>"><%= message %></p>

        <form action="restockProducts.jsp" method="GET">
            <input type="submit" value="Return to Restocking and Product Allocation Menu">
        </form>
        <form action="index.html" method="GET">
            <input type="submit" value="Return to Main Menu">
        </form>
    </div>
    <footer>
        &copy; 2024. Crisostomo, Lim, Dulat, Sadey.
    </footer>
</body>
</html>
