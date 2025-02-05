import java.sql.*;

public class DatabaseHandler {
    private static final String URL = "jdbc:mysql://localhost:3306/lab";
    private static final String USER = "root"; // Replace with your DB username
    private static final String PASSWORD = "kareem2004"; // Replace with your DB password

    public static Connection connect() {
        try {
            // Establish and return the connection
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void closeConnection() {
        try {
            getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean patientExists(int id) {
        String query = "SELECT COUNT(*) FROM Patients WHERE PatientID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateUserDetails(String newUserName, String newPhoneNumber, String newPassword, String email) {
        String query = "SELECT * FROM users WHERE Email = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt1 = conn.prepareStatement(query)) { // Correctly use PreparedStatement
            pstmt1.setString(1, email);
            try (ResultSet rs = pstmt1.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("No user found with the provided email.");
                    return;
                } else {
                    String username = rs.getString("Username");
                    String phoneNumber = rs.getString("PhoneNumber");
                    String password = rs.getString("Password");

                    if (newUserName == null || newUserName.isEmpty()) {
                        newUserName = username;
                    }
                    if (newPhoneNumber == null || newPhoneNumber.isEmpty()) {
                        newPhoneNumber = phoneNumber;
                    }
                    if (newPassword == null || newPassword.isEmpty()) {
                        newPassword = password;
                    }

                    String query1 = "UPDATE users SET Username = ?, PhoneNumber = ?, Password = ? WHERE Email = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(query1)) {
                        pstmt.setString(1, newUserName);
                        pstmt.setString(2, newPhoneNumber);
                        pstmt.setString(3, newPassword);
                        pstmt.setString(4, email);
                        pstmt.executeUpdate();
                        System.out.println("User details updated successfully.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getUserID(String email) {
        String query = "SELECT userId FROM users WHERE Email = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("userID");
                } else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean changeStatus(int appointmentID, String newStatus) {
        String query = "UPDATE Appointments SET Status = ? WHERE AppointmentID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, appointmentID);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addAppointment(String appointmentDate, String appointmentTime, String status, int patientID) {
        // Check if the patient exists
        String checkPatientQuery = "SELECT COUNT(*) FROM Patients WHERE PatientID = ?";
        String insertAppointmentQuery = "INSERT INTO Appointments (AppointmentDate, `Appointment Time`, Status, Patients_PatientID) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement checkStmt = conn.prepareStatement(checkPatientQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertAppointmentQuery)) {

            // Check if patient exists
            checkStmt.setInt(1, patientID);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("Error: Patient with ID " + patientID + " does not exist.");
                return false;
            }

            // Insert appointment
            insertStmt.setString(1, appointmentDate);
            insertStmt.setString(2, appointmentTime);
            insertStmt.setString(3, status);
            insertStmt.setInt(4, patientID);
            insertStmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean addPatient(int patientID, String dateOfBirth, String gender, String address,
                              String emergencyContactNum, String emergencyContactName,
                              String knownAllergies) {
        String query = "INSERT INTO Patients (PatientID, DateOfBirth, Gender, Address, EmergencyContactNum, EmergencyContactName, KnownAllergies) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patientID);
            pstmt.setString(2, dateOfBirth);
            pstmt.setString(3, gender);
            pstmt.setString(4, address);
            pstmt.setString(5, emergencyContactNum);
            pstmt.setString(6, emergencyContactName);
            pstmt.setString(7, knownAllergies);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Get all rows from a table
    public ResultSet getAllFromTable(String tableName) {
        String query = "SELECT * FROM " + tableName;
        try {
            Connection conn = connect(); Statement stmt = conn.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get the user type from Users table
    public String getUserType(String email) {
        String query = "SELECT Role FROM Users WHERE Email = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Role");
                } else {
                    return "User not found.";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error retrieving role.";
        }
    }

    // Get password of a user
    public String getPassword(String email) {
        String query = "SELECT Password FROM Users WHERE email = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Password");
                } else {
                    return "User not found.";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error retrieving password.";
        }
    }

    // Get phone number of a user
    public String getPhone(String email) {
        String query = "SELECT PhoneNumber FROM Users WHERE email = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("PhoneNumber");
                } else {
                    return "User not found.";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error retrieving phone number.";
        }
    }

    // Add a new user
    public boolean addUser(int userId, String username, String password, String fullName, String email, String phoneNumber, String role) {
        String query = "INSERT INTO Users (UserID, Username, Password, FullName, Email, PhoneNumber, Role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, fullName);
            pstmt.setString(5, email);
            pstmt.setString(6, phoneNumber);
            pstmt.setString(7, role);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a user
    public boolean deleteUser(int userId) {
        String query = "DELETE FROM Users WHERE UserID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Modify a user's name
    public boolean modifyUserName(int userId, String username) {
        String query = "UPDATE Users SET Username = ? WHERE UserID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Modify a user's email
    public boolean modifyUserEmail(int userId, String email) {
        String query = "UPDATE Users SET Email = ? WHERE UserID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Modify a user's phone number
    public boolean modifyUserPhone(int userId, String phone) {
        String query = "UPDATE Users SET PhoneNumber = ? WHERE UserID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, phone);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Modify a user's role
    public boolean modifyUserRole(int userId, String role) {
        String query = "UPDATE Users SET Role = ? WHERE UserID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, role);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Connection getConnection() {
        return connect();
    }

    public ResultSet getSugerReports(int patientId) {
        String query = "SELECT * FROM BloodSugarTests WHERE Patients_PatientID = ?";
        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, patientId);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getBloodPressureReports(int patientId) {
        String query = "SELECT * FROM BloodPresureTests WHERE Patients_PatientID = ?";
        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, patientId);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getUrineReports(int patientId) {
        String query = "SELECT * FROM UrineTest WHERE Patients_PatientID = ?";
        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, patientId);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getPatientDetails(int patientId) {
        String query = "SELECT * FROM Patients WHERE PatientID = ?";
        try {
            Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, patientId);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    /*                      Motayam                    */

    public ResultSet searchDependentPatient(int patientId) {
        // SQL query to fetch patient report
        String sql = "SELECT p.PatientID, p.DateOfBirth, p.Gender, p.Address, " +
                "p.EmergencyContactName, p.EmergencyContactNum, " +
                "ut.UrineTest1, ut.UrineTest2, ut.UrineTest3, " +
                "bt.BloodSugarTest1, bt.BloodSugarTest2, bt.BloodSugarTest3, " +
                "bpt.BloodPresureTest1, bpt.BloodPresureTest2, bpt.BloodPresureTest3, " +
                "pu.PatientUserscol, bt.average, bpt.average " +
                "FROM Lab.Patients p " +
                "LEFT JOIN Lab.UrineTest ut ON p.PatientID = ut.Patients_PatientID " +
                "LEFT JOIN Lab.BloodSugarTests bt ON p.PatientID = bt.Patients_PatientID " +
                "LEFT JOIN Lab.BloodPresureTests bpt ON p.PatientID = bpt.Patients_PatientID " +
                "LEFT JOIN Lab.PatientUsers pu ON p.PatientID = pu.Patients_PatientID " +
                "WHERE p.PatientID = ?";

        try {
            // Prepare the statement
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, patientId);


            // Execute the query
            return preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Done By Motayam Tayseer Tarada
    // for lab worker
    public boolean updatePersonalDetails(int UserID, String userName, String password, String fullName, String email, String phoneNumber, java.sql.Date datecreated) {
        String updateQuery = "UPDATE users " +
                "SET userName = ? ,Password = ?, FullName = ?, Email = ?, PhoneNumber = ?,DateCreated = ?, LastModified = NOW() " +
                "WHERE UserID = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, fullName);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, phoneNumber);
            preparedStatement.setDate(6, new java.sql.Date(datecreated.getTime()));
            preparedStatement.setInt(7, UserID);


            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    //Add Test Done By motayam Tarada

    public void insertUrineTest(int id, int test1, int test2, int test3, int patientID, double average, String status) {
        String query = "INSERT INTO urinetest (UrineTestID,UrineTest1, UrineTest2, UrineTest3,Patients_PatientID, average, status) VALUES (?,?, ?, ?, ?,?,?)";

        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setInt(2, test1);
            statement.setInt(3, test2);
            statement.setInt(4, test3);

            statement.setInt(5, patientID);
            statement.setDouble(6, average);
            statement.setString(7, status);

            statement.executeUpdate();
            System.out.println("Data inserted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRating(Double ReceptionTreating, Double LabTreating, Double InterfaceUsability, Double PrivacyandSecurity, String Notes) {
        String sql = "INSERT INTO rating (ReceptionTreating, LabTreating, InterfaceUsability, PrivacyandSecurity, Notes) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setDouble(1, ReceptionTreating);
            preparedStatement.setDouble(2, LabTreating);
            preparedStatement.setDouble(3, InterfaceUsability);
            preparedStatement.setDouble(4, PrivacyandSecurity);
            preparedStatement.setString(5, Notes);


            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //    //Add Test Done By motayam Tarada

    public void insertBloodPressureTest(int id, int test1, int test2, int test3, int patientID, double average, String status) {
        String query = "INSERT INTO BLOODPRESURETESTS (BloodPresureTestID,BloodPresureTest1, BloodPresureTest2, BloodPresureTest3,Patients_PatientID, average, status) VALUES (?, ?, ?, ?,?,?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setInt(2, test1);
            statement.setInt(3, test2);
            statement.setInt(4, test3);

            statement.setInt(5, patientID);
            statement.setDouble(6, average);
            statement.setString(7, status);

            statement.executeUpdate();
            System.out.println("Data inserted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Add Test Done By motayam Tarada

    public void insertSugarTest(int id, int test1, int test2, int test3, int patientID, double average, String status) {
        String query = "INSERT INTO BLOODSUGARTESTS (BloodSugarTestID,BloodSugarTest1, BloodSugarTest2, BloodSugarTest3,Patients_PatientID, average, status) VALUES (?,?, ?, ?, ?,?,?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setInt(2, test1);
            statement.setInt(3, test2);
            statement.setInt(4, test3);

            statement.setInt(5, patientID);
            statement.setDouble(6, average);
            statement.setString(7, status);

            statement.executeUpdate();
            System.out.println("Data inserted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Add Test Done By motayam Tarada

    // Fetch average ratings and notes
    public RatingData fetchRatingData() {
        String query = "SELECT AVG(ReceptionTreating) AS ReceptionAvg, " +
                "AVG(LabTreating) AS LabAvg, " +
                "AVG(InterfaceUsability) AS InterfaceAvg, " +
                "AVG(PrivacyandSecurity) AS PrivacyAvg, " +
                "GROUP_CONCAT(Notes SEPARATOR '\n') AS AllNotes " +
                "FROM rating";

        try (PreparedStatement statement = getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return new RatingData(
                        resultSet.getDouble("ReceptionAvg"),
                        resultSet.getDouble("LabAvg"),
                        resultSet.getDouble("InterfaceAvg"),
                        resultSet.getDouble("PrivacyAvg"),
                        resultSet.getString("AllNotes")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
