import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

// Add patient ID to the database function call.

public class Patient {
    final InactivityMonitor inactivityMonitor = new InactivityMonitor(300 * 1000); // 5 minute timeout

    private String email;
    private int id;

    public Patient(String email, int id) {
        this.email = email;
        this.id = id;
        show();
    }

    public void show() {
        Stage stage = new Stage();
        Label lblTitle = new Label("Patient Dashboard");
        lblTitle.setStyle("-fx-font-size: 35; -fx-font-weight: bold;");

        Button btnEditDetails = new Button("Edit Personal Details");
        Button btnAddAppointment = new Button("Add an Appointment");
        Button btnRating = new Button("Rating & Feedback");
        Button btnViewReports = new Button("View Reports");
        Button btnLogOut = new Button("Log out");

        btnEditDetails.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btnEditDetails.setOnMouseEntered(e -> btnEditDetails.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btnEditDetails.setOnMouseExited(e -> btnEditDetails.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));

        btnAddAppointment.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btnAddAppointment.setOnMouseEntered(e -> btnAddAppointment.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btnAddAppointment.setOnMouseExited(e -> btnAddAppointment.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));

        btnViewReports.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btnViewReports.setOnMouseEntered(e -> btnViewReports.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btnViewReports.setOnMouseExited(e -> btnViewReports.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));

        btnRating.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btnRating.setOnMouseEntered(e -> btnRating.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btnRating.setOnMouseExited(e -> btnRating.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));

        btnLogOut.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;");
        btnLogOut.setOnMouseEntered(e -> btnLogOut.setStyle("-fx-background-color: lightcoral; -fx-text-fill: black; -fx-font-size: 16;"));
        btnLogOut.setOnMouseExited(e -> btnLogOut.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;"));
        btnLogOut.setOnAction(e -> {
            stage.close();
            LogIn loginScreen = new LogIn();
        });

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(btnEditDetails, btnAddAppointment, btnViewReports, btnRating, btnLogOut);
        hBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(50);
        vBox.getChildren().addAll(lblTitle, hBox);
        vBox.setAlignment(Pos.CENTER);

        Image background = new Image("background.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(background,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                null,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));

        vBox.setBackground(new Background(backgroundImage));


        Scene scene = new Scene(vBox, 800, 600);
        stage.setTitle("Admin Dashboard");
        stage.setScene(scene);
        inactivityMonitor.monitorScene(scene);
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.show();

        btnAddAppointment.setOnAction(e -> {
            AddAppointments addAppointments = new AddAppointments(id);
        });

        btnEditDetails.setOnAction(e -> {
            EditPersonalDetailsForUser editPersonalDetails = new EditPersonalDetailsForUser();
        });

        btnRating.setOnAction(e -> {
            RatingAndFeedBack ratingAndFeedback = new RatingAndFeedBack();
        });

        btnViewReports.setOnAction(e -> {
            ViewMyReports viewReports = new ViewMyReports(id);
        });
    }

}
