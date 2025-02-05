import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AddPatientRecord {
    private DatabaseHandler db;

    public AddPatientRecord() {
        this.db = new DatabaseHandler();
        addPatient();
    }

    public void addPatient() {
        Stage stage = new Stage();
        Label lblTitle = new Label("Add Patient");
        lblTitle.setStyle("-fx-font-size: 35; -fx-font-weight: bold;");


        TextField tfPatientID = new TextField();
        tfPatientID.setPromptText("Patient ID");

        TextField tfPatientName = new TextField();
        tfPatientName.setPromptText("Patient Name");

        DatePicker dpDateOfBirth = new DatePicker();
        dpDateOfBirth.setPromptText("Date of Birth");

        ToggleGroup genderGroup = new ToggleGroup();
        RadioButton rbMale = new RadioButton("Male");
        RadioButton rbFemale = new RadioButton("Female");
        rbMale.setToggleGroup(genderGroup);
        rbFemale.setToggleGroup(genderGroup);

        TextField tfAddress = new TextField();
        tfAddress.setPromptText("Address");

        TextField tfEmergencyContactNum = new TextField();
        tfEmergencyContactNum.setPromptText("Emergency Contact Number");

        TextField tfEmergencyContactName = new TextField();
        tfEmergencyContactName.setPromptText("Emergency Contact Name");

        TextArea taKnownAllergies = new TextArea();
        taKnownAllergies.setPromptText("Known Allergies");
        taKnownAllergies.setPrefRowCount(3);


        Button btnAdd = new Button("Add Patient");
        btnAdd.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btnAdd.setOnMouseEntered(e -> btnAdd.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btnAdd.setOnMouseExited(e -> btnAdd.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));

        Button btnClear = new Button("Clear");
        btnClear.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;");
        btnClear.setOnMouseEntered(e -> btnClear.setStyle("-fx-background-color: lightcoral; -fx-text-fill: black; -fx-font-size: 16;"));
        btnClear.setOnMouseExited(e -> btnClear.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;"));

        HBox hButtons = new HBox(10);
        hButtons.getChildren().addAll(btnAdd, btnClear);

        GridPane gp = new GridPane();

        Image background = new Image("background.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(background,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                null,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));

        gp.setBackground(new Background(backgroundImage));

        gp.add(lblTitle, 0, 0, 2, 1);
        gp.add(new Label("Patient ID:"), 0, 1);
        gp.add(tfPatientID, 1, 1);
        gp.add(new Label("Patient Name:"), 0, 2);
        gp.add(tfPatientName, 1, 2);
        gp.add(new Label("Date of Birth:"), 0, 3);
        gp.add(dpDateOfBirth, 1, 3);
        gp.add(new Label("Gender:"), 0, 4);
        HBox genderBox = new HBox(10, rbMale, rbFemale);
        gp.add(genderBox, 1, 4);
        gp.add(new Label("Address:"), 0, 5);
        gp.add(tfAddress, 1, 5);
        gp.add(new Label("Emergency Contact Number:"), 0, 6);
        gp.add(tfEmergencyContactNum, 1, 6);
        gp.add(new Label("Emergency Contact Name:"), 0, 7);
        gp.add(tfEmergencyContactName, 1, 7);
        gp.add(new Label("Known Allergies:"), 0, 8);
        gp.add(taKnownAllergies, 1, 8);
        gp.add(hButtons, 0, 10, 2, 1);
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);

        stage.setTitle("Add Patient");
        stage.setScene(new Scene(gp, 800, 600));
        stage.show();


        btnClear.setOnAction(e -> {
            tfPatientID.clear();
            dpDateOfBirth.setValue(null);
            genderGroup.selectToggle(null);
            tfAddress.clear();
            tfEmergencyContactNum.clear();
            tfEmergencyContactName.clear();
            taKnownAllergies.clear();
        });

        btnAdd.setOnAction(e -> {
            String patientID = tfPatientID.getText();
            String dateOfBirth = dpDateOfBirth.getValue() != null ? dpDateOfBirth.getValue().toString() : null;
            String gender = rbMale.isSelected() ? "Male" : rbFemale.isSelected() ? "Female" : null;
            String address = tfAddress.getText();
            String emergencyContactNum = tfEmergencyContactNum.getText();
            String emergencyContactName = tfEmergencyContactName.getText();
            String knownAllergies = taKnownAllergies.getText();

            if (patientID.isEmpty() || dateOfBirth == null || gender == null || address.isEmpty() || emergencyContactNum.isEmpty() || emergencyContactName.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
                return;
            }

            try {
                boolean isAdded = db.addPatient(Integer.parseInt(patientID), dateOfBirth, gender, address, emergencyContactNum, emergencyContactName, knownAllergies);
                if (isAdded) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Patient added successfully!");
                    btnClear.fire();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to add patient!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while adding patient!");
            }
        });
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
