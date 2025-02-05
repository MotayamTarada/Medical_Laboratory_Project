import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddAppointments {
    private DatabaseHandler db;
    private int id;

    public AddAppointments() {
        this.db = new DatabaseHandler();
        makeAppointment();
    }

    public AddAppointments(int id) {
        this.db = new DatabaseHandler();
        this.id = id;
        makeAppointment();
    }

    public void makeAppointment() {
        Stage stage = new Stage();
        Label lblTitle = new Label("Make Appointment");
        lblTitle.setStyle("-fx-font-size: 35; -fx-font-weight: bold;");


        DatePicker dpAppointmentDate = new DatePicker();
        dpAppointmentDate.setPromptText("Appointment Date");

        TextField tfAppointmentTime = new TextField();
        tfAppointmentTime.setPromptText("Appointment Time (HH:MM)");


        Button btnAdd = new Button("Add Appointment");
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

        gp.add(new Label("Appointment Date:"), 0, 2);
        gp.add(dpAppointmentDate, 1, 2);
        gp.add(new Label("Appointment Time:"), 0, 3);
        gp.add(tfAppointmentTime, 1, 3);
        gp.add(hButtons, 0, 6, 2, 1);
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);

        stage.setTitle("Make Appointment");
        stage.setScene(new Scene(gp, 800, 600));
        stage.show();

        btnClear.setOnAction(e -> {
            dpAppointmentDate.setValue(null);
            tfAppointmentTime.clear();
        });

        btnAdd.setOnAction(e -> {
            LocalDate appointmentDate = dpAppointmentDate.getValue();
            String appointmentTime = tfAppointmentTime.getText();


            if (appointmentDate == null || appointmentTime.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
                return;
            }

            if (!isValidTime(appointmentTime)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid time format. Use HH:MM.");
                return;
            }

            if (!isDateAfterToday(appointmentDate)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Appointment date must be after today.");
                return;
            }

            try {
                boolean isAdded = db.addAppointment(appointmentDate.toString(), appointmentTime, "Pending", this.id);
                if (isAdded) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Appointment added successfully!");
                    btnClear.fire();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to add appointment!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while adding appointment!");
            }
        });
    }

    private boolean isValidTime(String time) {
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            timeFormatter.parse(time);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isDateAfterToday(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
