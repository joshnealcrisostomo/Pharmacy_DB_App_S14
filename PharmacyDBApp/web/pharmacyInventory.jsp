<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, transactions.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Pharmacy Stocks</title>
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
        <h1>VIEW PHARMACY STOCKS</h1>
    </header>

    <div class="form-container">
        <jsp:useBean id="A" class="transactions.manageInventory" scope="page" />
        <form action="pharmacyInventory.jsp" method="GET">
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
                A.current_stocks(v_pharmacy_id);
                A.remove_expired(v_pharmacy_id);

                if (A.stock_idList.size() > 0) {
        %>
        <h2>Expired/Nearly Expired Stocks Removed</h2>
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
        <% 
                } else {
        %>
        <h3>No Expired Products Found</h3>
        <% 
                }

                A.low_stocks(v_pharmacy_id);
        %>
        <h2>Products Low on Stock (Threshold: Quantity ≤ 10)</h2>
        <% 
                if (A.stock_idList.size() > 0) { 
        %>
        <table>
            <tr>
                <th>Product ID</th>
                <th>Product Name</th>
                <th>Quantity</th>
                <th>Batch Number</th>
            </tr>
            <% 
                for (int i = 0; i < A.stock_idList.size(); i++) { 
            %>
            <tr>
                <td><%= A.product_idList.get(i) %></td>
                <td><%= A.product_nameList.get(i) %></td>
                <td><%= A.quantityList.get(i) %></td>
                <td><%= A.batch_numberList.get(i) %></td>
            </tr>
            <% } %>
        </table>
        <% 
                } else { 
        %>
        <h3>No Low Stocks</h3>
        <% 
                }

                A.current_stocks(v_pharmacy_id);
        %>
        <h2>Current Stocks</h2>
        <table>
            <tr>
                <th>Stock ID</th>
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
                <td><%= A.stock_idList.get(i) %></td>
                <td><%= A.product_idList.get(i) %></td>
                <td><%= A.product_nameList.get(i) %></td>
                <td><%= A.quantityList.get(i) %></td>
                <td><%= A.batch_numberList.get(i) %></td>
                <td><%= A.expiration_dateList.get(i) %></td>
            </tr>
            <% } %>
        </table>
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
