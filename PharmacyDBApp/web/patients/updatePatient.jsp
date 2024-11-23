<!DOCTYPE html>
<html>
<head>
    <title>Update a Patient Record</title>
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
        <h1>UPDATE PATIENT RECORD</h1>
    </header>
    <div class="form-container">
        <jsp:useBean id="A" class="recordManagement.patients" scope="session" />
        <h3>Patient List</h3>
        <div class="table-container">
            <table>
                <tr>
                    <th>Patient ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Phone Number</th>
                    <th>City</th>
                    <th>Sex</th>
                </tr>
                <%  A.patient_list();
                    for(int i = 0; i < A.patient_idList.size(); i++){
                %>
                    <tr>
                        <td><%=A.patient_idList.get(i)%></td>
                        <td><%=A.first_nameList.get(i)%></td>
                        <td><%=A.last_nameList.get(i)%></td>
                        <td><%=A.phone_numberList.get(i)%></td>
                        <td><%=A.cityList.get(i)%></td>
                        <td><%=A.sexList.get(i)%></td>
                    </tr>
                <%  }
                %>
            </table>
        </div>
    </div>

    <div class="form-container">
        <form action="updating_patient.jsp">
            <label for="patient_id">Enter Patient ID to Update:</label><br>
            <input type="text" id="patient_id" name="patient_id" required><br>
            <label for="field">Select Field to Update:</label><br>
            <select id="field" name="field">
                <option value="FIRST_NAME">First Name</option>
                <option value="LAST_NAME">Last Name</option>
                <option value="PHONE_NUMBER">Phone Number</option>
                <option value="CITY">City</option>
                <option value="SEX">Sex</option>
            </select><br><br>
            <label for="new_value">Enter New Value for the Selected Field:</label><br>
            <input type="text" id="new_value" name="new_value" required><br><br>
            <input type="submit" value="Submit">
        </form>
    </div>

    <div class="form-container">
        <a href="managePatient.html" class="return-btn">Return to Manage Patient Records Menu</a>
    </div>

    <footer>
        &copy; 2024. Crisostomo, Lim, Dularte, Sadey.
    </footer>
</body>
</html>
