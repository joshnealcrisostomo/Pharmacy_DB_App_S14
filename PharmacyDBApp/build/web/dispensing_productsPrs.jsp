<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, transactions.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dispense Product/s to a Patient</title>
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
        <h1>Dispense Product/s to a Patient</h1>
    </header>

    <div class="form-container">
        <jsp:useBean id="A" class="transactions.dispenseProducts" scope="page" />
        <% 
            String v_prescription_id = request.getParameter("prescription_id2");
            A.product_prescription(v_prescription_id);
            String v_product_id = String.valueOf(A.product_id);
            String v_quantity = String.valueOf(A.quantity);

            if (v_product_id != null && !v_product_id.trim().isEmpty()) {
                if (A.product.check_IDNum(v_product_id) == 2) {
                    if (Integer.valueOf(v_quantity) > 0) {
                        int status = A.dispense_products(v_product_id, v_quantity);
                        if (status == 1) {
        %>
        <h2>Successfully Dispensed the Product</h2>
        <% 
                        } else if (status == 2) {
        %>
        <h2>Failed to Dispense Product. Insufficient Stock Quantity</h2>
        <% 
                        } else {
        %>
        <h2>Failed to Dispense Product. An Error Occurred</h2>
        <% 
                        }
                    } else {
        %>
        <h2>Failed to Dispense Product. Quantity Cannot be Negative</h2>
        <% 
                    }
                } else {
        %>
        <h2>Failed to Dispense Product. Product ID Not Found</h2>
        <% 
                }
            } else {
        %>
        <h2>Failed to Dispense Product. Product ID Cannot Be Empty/Null</h2>
        <% 
            } 
        %>
    </div>

    <div class="form-container">
        <form action="restockProducts.jsp" method="GET">
            <input type="submit" value="Return to Restocking and Product Allocation Menu">
        </form>
        <a href="index.html" class="return-btn">Return to Main Menu</a>
    </div>

    <footer>
        &copy; 2024. Crisostomo, Lim, Dularte, Sadey.
    </footer>
</body>
</html>
