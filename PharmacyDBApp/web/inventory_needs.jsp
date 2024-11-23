<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Pharmacy Inventory and Patient Needs Report</title>
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
        <h1>Pharmacy Inventory and Patient Needs Report</h1>
    </header>

    <div class="form-container">
        <jsp:useBean id="A" class="reports.inventoryNeeds" scope="page" />
        <form action="inventory_needs.jsp" method="GET">
            <label for="alert_week">Select Week:</label>
            <select id="alert_week" name="alert_week">
                <option value="1">Week 1</option>
                <option value="2">Week 2</option>
                <option value="3">Week 3</option>
                <option value="4">Week 4</option>
            </select>

            <label for="alert_month">Select Month:</label>
            <select id="alert_month" name="alert_month">
                <option value="January">January</option>
                <option value="February">February</option>
                <option value="March">March</option>
                <option value="April">April</option>
                <option value="May">May</option>
                <option value="June">June</option>
                <option value="July">July</option>
                <option value="August">August</option>
                <option value="September">September</option>
                <option value="October">October</option>
                <option value="November">November</option>
                <option value="December">December</option>
            </select>

            <label for="alert_year">Enter Year:</label>
            <input type="text" id="alert_year" name="alert_year" required>
            <input type="submit" value="Check">
        </form>
    </div>

    <div class="form-container">
        <% 
            String v_alert_week = request.getParameter("alert_week");
            String v_alert_month = request.getParameter("alert_month");
            String v_alert_year = request.getParameter("alert_year");

            A.pharmacy_inventories(v_alert_week, v_alert_month, v_alert_year);

            if (A.alert_idList.size() > 0) { 
        %>
        <h2>Low Stock Products in Week <%= v_alert_week %> of <%= v_alert_month %>, <%= v_alert_year %></h2>
        <table>
            <tr>
                <th>Pharmacy Name</th>
                <th>Pharmacy Floor</th>
                <th>Product Name</th>
                <th>Strength</th>
                <th>Dosage Form</th>
                <th>Date and Time Alerted</th>
            </tr>
            <% 
                for (int i = 0; i < A.alert_idList.size(); i++) { 
            %>
            <tr>
                <td><%= A.pharmacy.pharmacy_nameList.get(i) %></td>
                <td><%= A.pharmacy.floorList.get(i) %></td>
                <td><%= A.product.product_nameList.get(i) %></td>
                <td><%= A.product.strengthList.get(i) %></td>
                <td><%= A.product.dosage_formList.get(i) %></td>
                <td><%= A.alertList.get(i) %></td>
            </tr>
            <% } %>
        </table>
        <% 
            } else { 
        %>
        <h3>No Low Stock Alerted at Selected Date</h3>
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
