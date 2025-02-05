import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class ShowAllPatients {
    private DatabaseHandler db = new DatabaseHandler();

    public ShowAllPatients() {
        show();
    }

    public void show() {
        ResultSet rs = db.getAllFromTable("patients");
        Stage stage = new Stage();
        TableView<PatientData> table = new TableView<>();

        TableColumn<PatientData, Integer> patientID = new TableColumn<>("Patient ID");
        patientID.setCellValueFactory(new PropertyValueFactory<>("patientId"));

        TableColumn<PatientData, String> firstName = new TableColumn<>("Name");
        firstName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<PatientData, String> dateOfBirth = new TableColumn<>("Date of Birth");
        dateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        TableColumn<PatientData, String> gender = new TableColumn<>("Gender");
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<PatientData, String> address = new TableColumn<>("Address");
        address.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<PatientData, String> emergencyContactName = new TableColumn<>("Emergency Contact Name");
        emergencyContactName.setCellValueFactory(new PropertyValueFactory<>("emergencyContactName"));

        TableColumn<PatientData, String> emergencyContactNumber = new TableColumn<>("Emergency Contact Number");
        emergencyContactNumber.setCellValueFactory(new PropertyValueFactory<>("emergencyContactNumber"));

        TableColumn<PatientData, String> allergies = new TableColumn<>("Allergies");
        allergies.setCellValueFactory(new PropertyValueFactory<>("allergies"));

        TableColumn<PatientData, String> registerDate = new TableColumn<>("Registration Date");
        registerDate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));

        table.getColumns().addAll(patientID, firstName, dateOfBirth, gender, address, emergencyContactName, emergencyContactNumber, allergies, registerDate);

        try {
            while (rs.next()) {
                table.getItems().add(new PatientData(rs.getString("name"), rs.getInt("patientID"), rs.getString("dateOfBirth"), rs.getString("gender"), rs.getString("address"), rs.getString("emergencyContactName"), rs.getString("emergencyContactNum"), rs.getString("KnownAllergies"), rs.getString("RegistrationDate")));
            }

            Scene scene = new Scene(table, 900, 500);
            stage.setScene(scene);
            stage.setTitle("All Users");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}