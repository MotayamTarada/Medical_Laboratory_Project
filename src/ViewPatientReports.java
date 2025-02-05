import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

import javafx.stage.FileChooser;

public class ViewPatientReports extends Stage {

    private Connection connection; // Connection to the database

    public ViewPatientReports() {
        setTitle("View Patient Reports");

        try {

            // Image for background
            Image background = new Image("background.jpg");
            // BackgroundImage to set the background image on grid pane
            BackgroundImage backgroundImage = new BackgroundImage(background,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    null,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));

            GridPane root = new GridPane();
            root.setBackground(new Background(backgroundImage));
            root.setAlignment(Pos.CENTER);
            root.setHgap(10);
            root.setVgap(10);

            Label lblTitle = new Label("View Patient Reports");
            lblTitle.setStyle("-fx-font-size: 35; -fx-font-weight: bold;");
            root.add(lblTitle, 0, 0, 2, 1);

            Label lblPatientId = new Label("Patient ID:");
            root.add(lblPatientId, 0, 1);

            TextField tfPatientId = new TextField();
            root.add(tfPatientId, 1, 1);

            Button btSearch = new Button("Search");
            btSearch.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
            btSearch.setOnMouseEntered(e -> btSearch.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
            btSearch.setOnMouseExited(e -> btSearch.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));
            root.add(btSearch, 2, 1);

            btSearch.setOnAction(e -> {
                searchPatientReport(tfPatientId.getText());
            });


            Scene scene = new Scene(root, 1150, 630);
            setScene(scene);
            show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchPatientReport(String patientIdStr) {
        // Create an instance of DatabaseHandler
        DatabaseHandler dbHandler = new DatabaseHandler();

        int patientId = Integer.parseInt(patientIdStr);


        // Create a table to display results
        TableView<PatientReport> table = createTableView();
        try {

            try (ResultSet patient = dbHandler.getPatientDetails(patientId);) {

                if (patient.next()) {
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
                                    name, patientId, gender, dateOfBirth, address,
                                    emergencyContactName, emergencyContactNumber,
                                    averageSugar, sugarTest1, sugarTest2, sugarTest3, sugarStatus);

                            table.getItems().add(sugarReport);
                        }
                    }

                    try (ResultSet rsUrine = dbHandler.getUrineReports(patientId)) {
                        while (rsUrine.next()) {
                            int urineTest1 = rsUrine.getInt("urineTest1");
                            int urineTest2 = rsUrine.getInt("urineTest2");
                            int urineTest3 = rsUrine.getInt("urineTest3");
                            double averageUrineSugar = rsUrine.getDouble("average");
                            String urineTestStatus = rsUrine.getString("status");

                            PatientReport urineReport = new UrineTestReport(
                                    name, patientId, gender, dateOfBirth, address,
                                    emergencyContactName, emergencyContactNumber, averageUrineSugar, urineTest1, urineTest2, urineTest3, urineTestStatus);

                            table.getItems().add(urineReport);
                        }
                    }

                    try (ResultSet rsBloodPressure = dbHandler.getBloodPressureReports(patientId)) {
                        while (rsBloodPressure.next()) {
                            int bloodPressureTest1 = rsBloodPressure.getInt("bloodPressureTest1");
                            int bloodPressureTest2 = rsBloodPressure.getInt("bloodPressureTest2");
                            int bloodPressureTest3 = rsBloodPressure.getInt("bloodPressureTest3");
                            double averageBloodPressure = rsBloodPressure.getDouble("average");
                            String bloodPressureStatus = rsBloodPressure.getString("status");

                            PatientReport bloodPressureReport = new BloodPressureReport(
                                    name, patientId, gender, dateOfBirth, address,
                                    emergencyContactName, emergencyContactNumber, averageBloodPressure, bloodPressureTest1, bloodPressureTest2, bloodPressureTest3, bloodPressureStatus);

                            table.getItems().add(bloodPressureReport);
                        }
                    }
                } else {
                    Alert alert = new Alert(AlertType.ERROR, "No patient found with the given ID", ButtonType.OK);
                    alert.showAndWait();
                }
            }

            // Display the table in a new stage
            Stage resultStage = new Stage();
            Button btn = new Button("Download On Desktop");
            btn.setOnAction(e -> {
                downloadReport(table);
            });
            VBox vbox = new VBox(10);
            vbox.getChildren().addAll(table, btn);
            btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"));

            Scene resultScene = new Scene(vbox);
            resultStage.setScene(resultScene);
            resultStage.show();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
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


    public void downloadReport(TableView<PatientReport> table) {
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


    private void applyInitialTransparency(Node... nodes) {
        for (Node node : nodes) {
            node.setOpacity(0);
        }
    }

    private void applyFadeInAnimation(Node... nodes) {
        for (Node node : nodes) {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), node);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.play();
        }
    }

    // Helper method to create styled buttons
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font(18));
        button.setMinWidth(120);
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"));
        button.setOnMouseExited(e -> button.setStyle(null));
        return button;
    }
}
