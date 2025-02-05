import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewMyReports extends Stage {

    private int id;

    public ViewMyReports(int id) {
        this.id = id;
        setTitle("View My Reports");
        searchPatientReport();
    }

    private TableView<PatientReport> createTableView() {
        TableView<PatientReport> table = new TableView<>();

        TableColumn<PatientReport, Integer> patientIdColumn = new TableColumn<>("Patient ID");
        patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));

        TableColumn<PatientReport, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<PatientReport, String> genderColumn = new TableColumn<>("Gender");
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<PatientReport, String> dateOfBirthColumn = new TableColumn<>("Date of Birth");
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));


        TableColumn<PatientReport, Integer> urineTest1Column = new TableColumn<>("Address");
        urineTest1Column.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<PatientReport, String> testColumn = new TableColumn<>("Test Type");
        testColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<PatientReport, Integer> test1Column = new TableColumn<>("Test 1");
        test1Column.setCellValueFactory(new PropertyValueFactory<>("first"));

        TableColumn<PatientReport, Integer> test2Column = new TableColumn<>("Test 2");
        test2Column.setCellValueFactory(new PropertyValueFactory<>("second"));

        TableColumn<PatientReport, Integer> test3Column = new TableColumn<>("Test 3");
        test3Column.setCellValueFactory(new PropertyValueFactory<>("third"));

        TableColumn<PatientReport, Double> averageColumn = new TableColumn<>("Average");
        averageColumn.setCellValueFactory(new PropertyValueFactory<>("average"));

        TableColumn<PatientReport, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(patientIdColumn, nameColumn, genderColumn, dateOfBirthColumn, urineTest1Column, testColumn, test1Column, test2Column, test3Column, averageColumn, statusColumn);

        return table;
    }

    private void downloadReport(TableView<PatientReport> table) {
        // Access the application's main stage
        Stage stage = new Stage(); // Replace with your application's main Stage if already available.

        // Create a FileChooser to allow the user to choose the location to save the file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Table Data");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        // Set desktop as the initial directory
        String userDesktop = System.getProperty("user.home") + "/Desktop";
        fileChooser.setInitialDirectory(new File(userDesktop));

        // Show save dialog and get the file selected by the user
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Write the table headers
                ObservableList<TableColumn<PatientReport, ?>> columns = table.getColumns();
                for (TableColumn<PatientReport, ?> column : columns) {
                    writer.write(column.getText() + ",");
                }
                writer.write("\n");

                // Write the table data
                ObservableList<PatientReport> items = table.getItems();
                for (PatientReport item : items) {
                    writer.write(item.getPatientId() + ",");
                    writer.write(item.getName() + ",");
                    writer.write(item.getGender() + ",");
                    writer.write(item.getDateOfBirth() + ",");
                    writer.write(item.getAddress() + ",");

                    if (item instanceof BloodPressureReport) {
                        writer.write(((BloodPressureReport) item).getType() + ",");
                        writer.write(((BloodPressureReport) item).getFirst() + ",");
                        writer.write(((BloodPressureReport) item).getSecond() + ",");
                        writer.write(((BloodPressureReport) item).getThird() + ",");
                        writer.write(((BloodPressureReport) item).getAverage() + ",");
                        writer.write(((BloodPressureReport) item).getStatus() + "\n");
                    } else if (item instanceof BloodSugarReport) {
                        writer.write(((BloodSugarReport) item).getType() + ",");
                        writer.write(((BloodSugarReport) item).getFirst() + ",");
                        writer.write(((BloodSugarReport) item).getSecond() + ",");
                        writer.write(((BloodSugarReport) item).getThird() + ",");
                        writer.write(((BloodSugarReport) item).getAverage() + ",");
                        writer.write(((BloodSugarReport) item).getStatus() + "\n");
                    } else if (item instanceof UrineTestReport) {
                        writer.write(((UrineTestReport) item).getType() + ",");
                        writer.write(((UrineTestReport) item).getFirst() + ",");
                        writer.write(((UrineTestReport) item).getSecond() + ",");
                        writer.write(((UrineTestReport) item).getThird() + ",");
                        writer.write(((UrineTestReport) item).getAverage() + ",");
                        writer.write(((UrineTestReport) item).getStatus() + "\n");
                    }

                }

                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Download Successful");
                alert.setHeaderText(null);
                alert.setContentText("The report has been successfully saved to: " + file.getAbsolutePath());
                alert.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void searchPatientReport() {
        // Create an instance of DatabaseHandler
        DatabaseHandler dbHandler = new DatabaseHandler();

        int patientId = this.id;

        // Get the patient report result set from DatabaseHandler


        // Create a table to display results
        TableView<PatientReport> table = createTableView();

        try {
            ResultSet patient = dbHandler.getPatientDetails(patientId);

            if (patient.next()) { // Move the cursor to the first row
                String name = patient.getString("name");
                String gender = patient.getString("gender");
                String dateOfBirth = patient.getString("dateOfBirth");
                String address = patient.getString("address");
                String emergencyContactName = patient.getString("emergencyContactName");
                String emergencyContactNumber = patient.getString("emergencyContactNum");

                // Process Sugar Reports
                try (ResultSet rsSugar = dbHandler.getSugerReports(patientId)) {
                    while (rsSugar.next()) {
                        int sugarTest1 = rsSugar.getInt("BloodSugarTest1");
                        int sugarTest2 = rsSugar.getInt("BloodSugarTest2");
                        int sugarTest3 = rsSugar.getInt("BloodSugarTest3");
                        double averageSugar = rsSugar.getDouble("average");
                        String sugarStatus = rsSugar.getString("status");

                        PatientReport sugarReport = new BloodSugarReport(
                                name, patientId, gender, dateOfBirth, address, emergencyContactName,
                                emergencyContactNumber, averageSugar, sugarTest1, sugarTest2, sugarTest3, sugarStatus);
                        table.getItems().add(sugarReport);
                    }
                }

                // Process Urine Reports
                try (ResultSet rsUrine = dbHandler.getUrineReports(patientId)) {
                    while (rsUrine.next()) {
                        int urineTest1 = rsUrine.getInt("urineTest1");
                        int urineTest2 = rsUrine.getInt("urineTest2");
                        int urineTest3 = rsUrine.getInt("urineTest3");
                        double averageUrine = rsUrine.getDouble("averageUrine");
                        String urineStatus = rsUrine.getString("urineStatus");

                        PatientReport urineReport = new UrineTestReport(
                                name, patientId, gender, dateOfBirth, address, emergencyContactName,
                                emergencyContactNumber, averageUrine, urineTest1, urineTest2, urineTest3, urineStatus);
                        table.getItems().add(urineReport);
                    }
                }

                // Process Blood Pressure Reports
                try (ResultSet rsBloodPressure = dbHandler.getBloodPressureReports(patientId)) {
                    while (rsBloodPressure.next()) {
                        int bloodPressureTest1 = rsBloodPressure.getInt("bloodPressureTest1");
                        int bloodPressureTest2 = rsBloodPressure.getInt("bloodPressureTest2");
                        int bloodPressureTest3 = rsBloodPressure.getInt("bloodPressureTest3");
                        double averageBloodPressure = rsBloodPressure.getDouble("averageBloodPressure");
                        String bloodPressureStatus = rsBloodPressure.getString("bloodPressureStatus");

                        PatientReport bloodPressureReport = new BloodPressureReport(
                                name, patientId, gender, dateOfBirth, address, emergencyContactName,
                                emergencyContactNumber, averageBloodPressure, bloodPressureTest1, bloodPressureTest2,
                                bloodPressureTest3, bloodPressureStatus);
                        table.getItems().add(bloodPressureReport);
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Data Found");
                alert.setHeaderText(null);
                alert.setContentText("No patient found with the given ID.");
                alert.showAndWait();
            }

            // Display the table
            Stage resultStage = new Stage();
            VBox vbox = new VBox(10);
            Button downloadButton = new Button("Download Report");
            downloadButton.setOnAction(event -> downloadReport(table));
            vbox.getChildren().addAll(table, downloadButton);
            Scene resultScene = new Scene(vbox);
            resultStage.setScene(resultScene);
            resultStage.show();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHandler.closeConnection();
        }

    }
}
