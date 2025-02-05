import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowAppointments {
    private DatabaseHandler db;

    public ShowAppointments() {
        this.db = new DatabaseHandler();
        showAppointments();
    }

    // Show appointments
    public void showAppointments() {
        Stage stage = new Stage();
        // Create a table view to display appointments data
        TableView<Appointment> tableView = new TableView<>();

        TableColumn<Appointment, Integer> colAppointmentID = new TableColumn<>("Appointment ID");
        colAppointmentID.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getAppointmentID()));

        TableColumn<Appointment, String> colAppointmentDate = new TableColumn<>("Appointment Date");
        colAppointmentDate.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAppointmentDate()));

        TableColumn<Appointment, String> colAppointmentTime = new TableColumn<>("Appointment Time");
        colAppointmentTime.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAppointmentTime()));

        TableColumn<Appointment, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus()));
        // Add a combo box to update the status
        colStatus.setCellFactory(column -> new TableCell<>() {
            private final ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList("Pending", "Confirmed", "Completed", "Cancelled"));

            {
                comboBox.setOnAction(event -> {
                    Appointment appointment = getTableView().getItems().get(getIndex());
                    String newStatus = comboBox.getValue();
                    updateStatusInDatabase(appointment.getAppointmentID(), newStatus);
                    appointment.setStatus(newStatus);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    comboBox.setValue(item);
                    setGraphic(comboBox);
                }
            }
        });

        TableColumn<Appointment, Integer> colPatientID = new TableColumn<>("Patient ID");
        colPatientID.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPatientID()));

        tableView.getColumns().addAll(colAppointmentID, colAppointmentDate, colAppointmentTime, colStatus, colPatientID);

        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        // Fetch data from database
        String query = "SELECT * FROM Appointments";
        try (Connection conn = db.connect(); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            // Loop through the result set
            while (rs.next()) {
                // Get data from the current row
                int appointmentID = rs.getInt("AppointmentID");
                String appointmentDate = rs.getString("AppointmentDate");
                String appointmentTime = rs.getString("Appointment Time");
                String status = rs.getString("Status");
                int patientID = rs.getInt("Patients_PatientID");

                // Create an appointment object
                Appointment appointment = new Appointment(appointmentID, appointmentDate, appointmentTime, status, patientID);
                appointmentList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableView.setItems(appointmentList);

        StackPane layout = new StackPane(tableView);

        Scene scene = new Scene(layout, 800, 600);
        stage.setTitle("Show Appointments");
        stage.setScene(scene);
        stage.show();
    }

    // Update status in database
    private void updateStatusInDatabase(int appointmentID, String newStatus) {
        String updateQuery = "UPDATE Appointments SET Status = ? WHERE AppointmentID = ?";
        try (Connection conn = db.connect(); PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, appointmentID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}