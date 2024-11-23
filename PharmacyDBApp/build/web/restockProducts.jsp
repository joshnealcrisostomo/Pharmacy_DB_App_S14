<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, transactions.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Restock and Allocate Products</title>
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
        <h1>RESTOCK AND ALLOCATE PRODUCTS</h1>
    </header>

    <div class="form-container">
        <jsp:useBean id="A" class="transactions.restockProducts" scope="page" />
        <form action="restockProducts.jsp" method="GET">
            <label for="pharmacy_id">Select Pharmacy:</label>
            <select id="pharmacy_id" name="pharmacy_id">
                <%  
                    A.pharmacy.pharmacies_list();
                    for (int i = 0; i < A.pharmacy.pharmacy_idList.size(); i++) { 
                %>
                    <option value="<%= A.pharmacy.pharmacy_idList.get(i) %>">
                        <%= A.pharmacy.pharmacy_nameList.get(i) %> (Floor <%= A.pharmacy.floorList.get(i) %>)
                    </option>
                <% } %>
            </select>
            <input type="submit" value="Check and Update Stocks">
        </form>
    </div>

    <div class="form-container">
        <% 
            String v_pharmacy_id = request.getParameter("pharmacy_id");
            if (v_pharmacy_id != null && !v_pharmacy_id.trim().isEmpty()) {
                A.check_stocks(v_pharmacy_id);
        %>
        <h2>Current Stocks</h2>
        <table>
            <tr>
                <th>Product ID</th>
                <th>Product Name</th>
                <th>Quantity</th>
                <th>Batch Number</th>
                <th>Expiration Date</th>
            </tr>
            <% 
                for (int i = 0; i < A.stock_idList.size(); i++) { 
            %>
            <tr>
                <td><%= A.product_idList.get(i) %></td>
                <td><%= A.product_nameList.get(i) %></td>
                <td><%= A.quantityList.get(i) %></td>
                <td><%= A.batch_numberList.get(i) %></td>
                <td><%= A.expiration_dateList.get(i) %></td>
            </tr>
            <% } %>
        </table>

        <h2>Available Products for Stocking</h2>
        <table>
            <tr>
                <th>Product ID</th>
                <th>Product Name</th>
                <th>Strength</th>
                <th>Dosage Form</th>
                <th>Supplier Name</th>
            </tr>
            <% 
                A.product.product_list();
                for (int i = 0; i < A.product.product_idList.size(); i++) { 
            %>
            <tr>
                <td><%= A.product.product_idList.get(i) %></td>
                <td><%= A.product.product_nameList.get(i) %></td>
                <td><%= A.product.strengthList.get(i) %></td>
                <td><%= A.product.dosage_formList.get(i) %></td>
                <td><%= A.product.supplier_nameList.get(i) %></td>
            </tr>
            <% } %>
        </table>

        <form action="restocking_product.jsp" method="GET">
            <label for="product_id">Enter Product ID and Quantity to Stock:</label>
            <input type="text" id="product_id" name="product_id" placeholder="Product ID">
            <input type="text" id="quantity" name="quantity" placeholder="Quantity">
            <input type="hidden" id="pharmacy_id2" name="pharmacy_id2" value="<%= v_pharmacy_id %>">
            <input type="submit" value="Add to Stocks">
        </form>

        <h2>Recommended Products to Restock</h2>
        <% 
            A.recommended_restock(v_pharmacy_id);
            if (A.product_idList.size() > 0) { 
        %>
        <p>
            <% 
                for (int i = 0; i < A.product_idList.size(); i++) { 
                    out.print(A.product_idList.get(i));
                    if (i < A.product_idList.size() - 1) out.print(", ");
                } 
            %>
        </p>
        <% } else { %>
        <h3>No Products Recommended for Restocking</h3>
        <% } %>
        <% } else { %>
        <h3>No Pharmacy Selected</h3>
        <% } %>
    </div>

    <div class="form-container">
        <a href="index.html" class="return-btn">Return to Main Menu</a>
    </div>

    <footer>
        &copy; 2024. Crisostomo, Lim, Dularte, Sadey.
    </footer>
</body>
</html>
