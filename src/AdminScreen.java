import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdminScreen {
    final InactivityMonitor inactivityMonitor = new InactivityMonitor(300 * 1000); // 5 minute timeout


    public AdminScreen() {
        show();
    }

    // Admin Dashboard Screen
    public void show() {
        Stage stage = new Stage();
        Label lblTitle = new Label("Admin Dashboard");
        lblTitle.setStyle("-fx-font-size: 35; -fx-font-weight: bold;");

        Button btnManageUsers = new Button("Manage Users");
        Button btnAddPatientRecord = new Button("Add Patient Record");
        Button btnViewReports = new Button("View Patient Reports");
        Button btnAppointments = new Button("Appointments");
        Button btnShowAllAppointments = new Button("Show Appointments");
        Button btnFeedback = new Button("Rating & Feedback");
        Button btnLogOut = new Button("Log out");

        btnManageUsers.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btnManageUsers.setOnMouseEntered(e -> btnManageUsers.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btnManageUsers.setOnMouseExited(e -> btnManageUsers.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));

        btnAddPatientRecord.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btnAddPatientRecord.setOnMouseEntered(e -> btnAddPatientRecord.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btnAddPatientRecord.setOnMouseExited(e -> btnAddPatientRecord.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));

        btnViewReports.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btnViewReports.setOnMouseEntered(e -> btnViewReports.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btnViewReports.setOnMouseExited(e -> btnViewReports.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));

        btnAppointments.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btnAppointments.setOnMouseEntered(e -> btnAppointments.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btnAppointments.setOnMouseExited(e -> btnAppointments.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));

        btnShowAllAppointments.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btnShowAllAppointments.setOnMouseEntered(e -> btnShowAllAppointments.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btnShowAllAppointments.setOnMouseExited(e -> btnShowAllAppointments.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));

        btnFeedback.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btnFeedback.setOnMouseEntered(e -> btnFeedback.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btnFeedback.setOnMouseExited(e -> btnFeedback.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));

        btnLogOut.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;");
        btnLogOut.setOnMouseEntered(e -> btnLogOut.setStyle("-fx-background-color: lightcoral; -fx-text-fill: black; -fx-font-size: 16;"));
        btnLogOut.setOnMouseExited(e -> btnLogOut.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;"));

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(btnManageUsers, btnAddPatientRecord, btnViewReports, btnAppointments, btnShowAllAppointments, btnFeedback, btnLogOut);
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

        btnLogOut.setOnAction(e -> {
            LogIn logIn = new LogIn(stage);
        });

        btnManageUsers.setOnAction(e -> {
            stage.close();
            ManageUsers manageUsers = new ManageUsers();
        });

        btnFeedback.setOnAction(e -> {
            DatabaseHandler dbHandler = new DatabaseHandler();
            RatingData data = dbHandler.fetchRatingData();

            RatingFeedBackResults rate = new RatingFeedBackResults(data);
            rate.show();
        });

        btnViewReports.setOnAction(e -> {
            stage.close();
            ViewPatientReports viewPatientReports = new ViewPatientReports();
        });

        btnAddPatientRecord.setOnAction(e -> {
            AddPatientRecord addPatientRecord = new AddPatientRecord();
        });

        btnAppointments.setOnAction(e -> {
            AddAppointments addAppointments = new AddAppointments();
        });

        btnShowAllAppointments.setOnAction(e -> {
            ShowAppointments showAppointments = new ShowAppointments();
        });
    }

}
