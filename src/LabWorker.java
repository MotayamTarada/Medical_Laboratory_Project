import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LabWorker {
    final InactivityMonitor inactivityMonitor = new InactivityMonitor(300 * 1000); // 5 minute timeout
    private String email;
    private int id;


    public LabWorker(String email, int id) {
        this.email = email;
        this.id = id;
        show();
    }

    public void show() {
        Stage stage = new Stage();
        Label lblTitle = new Label("Admin Dashboard");
        lblTitle.setStyle("-fx-font-size: 35; -fx-font-weight: bold;");

        Button btnEditDetails = new Button("Edit Personal Details");
        Button btnAddTest = new Button("Add a Test");
        Button btnViewReports = new Button("View Patient Reports");
        Button btShowAllPatients = new Button("Show All Patients");
        Button btnAppointments = new Button("Appointments");
        Button btnLogOut = new Button("Log out");

        btnEditDetails.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btnEditDetails.setOnMouseEntered(e -> btnEditDetails.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btnEditDetails.setOnMouseExited(e -> btnEditDetails.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));

        btnAddTest.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btnAddTest.setOnMouseEntered(e -> btnAddTest.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btnAddTest.setOnMouseExited(e -> btnAddTest.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));

        btnViewReports.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btnViewReports.setOnMouseEntered(e -> btnViewReports.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btnViewReports.setOnMouseExited(e -> btnViewReports.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));

        btShowAllPatients.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btShowAllPatients.setOnMouseEntered(e -> btShowAllPatients.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btShowAllPatients.setOnMouseExited(e -> btShowAllPatients.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));

        btnAppointments.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btnAppointments.setOnMouseEntered(e -> btnAppointments.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btnAppointments.setOnMouseExited(e -> btnAppointments.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));

        btnLogOut.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;");
        btnLogOut.setOnMouseEntered(e -> btnLogOut.setStyle("-fx-background-color: lightcoral; -fx-text-fill: black; -fx-font-size: 16;"));
        btnLogOut.setOnMouseExited(e -> btnLogOut.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;"));

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(btnEditDetails, btnAddTest, btnViewReports, btShowAllPatients, btnAppointments, btnLogOut);
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

        btnEditDetails.setOnAction(e -> {
            EditPersonalDetailsForLab editDetails = new EditPersonalDetailsForLab();
        });

        btnAddTest.setOnAction(e -> {
            LabTest addTest = new LabTest();
        });

        btnViewReports.setOnAction(e -> {
            ViewPatientReports viewReports = new ViewPatientReports();
        });

        btShowAllPatients.setOnAction(e -> {
            ShowAllPatients showAllPatients = new ShowAllPatients();
        });

        btnAppointments.setOnAction(e -> {
            ShowAppointments showAppointments = new ShowAppointments();
        });

        btnLogOut.setOnAction(e -> {
            stage.close();
            LogIn logIn = new LogIn(stage);
        });
    }
}
