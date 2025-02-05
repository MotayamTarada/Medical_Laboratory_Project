import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ManageUsers {

    private DatabaseHandler db;

    public ManageUsers() {
        this.db = new DatabaseHandler();
        show();
    }

    public void show() {
        Stage stage = new Stage();
        Label lblTitle = new Label("Manage Users");
        lblTitle.setStyle("-fx-font-size: 35; -fx-font-weight: bold;");

        Button btnAddUser = new Button("Add User");
        Button btnModifyUser = new Button("Modify User");
        Button btnDeleteUser = new Button("Delete User");
        Button btShowAllUsers = new Button("Show All Users");
        Button btnBack = new Button("Back");

        btnAddUser.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btnAddUser.setOnMouseEntered(e -> btnAddUser.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btnAddUser.setOnMouseExited(e -> btnAddUser.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));

        btnModifyUser.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btnModifyUser.setOnMouseEntered(e -> btnModifyUser.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btnModifyUser.setOnMouseExited(e -> btnModifyUser.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));

        btnDeleteUser.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btnDeleteUser.setOnMouseEntered(e -> btnDeleteUser.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btnDeleteUser.setOnMouseExited(e -> btnDeleteUser.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));

        btShowAllUsers.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btShowAllUsers.setOnMouseEntered(e -> btShowAllUsers.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btShowAllUsers.setOnMouseExited(e -> btShowAllUsers.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));


        btnBack.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;");
        btnBack.setOnMouseEntered(e -> btnBack.setStyle("-fx-background-color: lightcoral; -fx-text-fill: black; -fx-font-size: 16;"));
        btnBack.setOnMouseExited(e -> btnBack.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;"));

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(btShowAllUsers, btnAddUser, btnModifyUser, btnDeleteUser, btnBack);
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


        stage.setTitle("Manage Users");
        stage.setScene(new Scene(vBox, 800, 600));
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.show();

        btnBack.setOnAction(e -> {
            stage.close();
            AdminScreen adminScreen = new AdminScreen();
        });

        btnAddUser.setOnAction(e -> {
            stage.close();
            AddUser addUser = new AddUser();
        });

        btnModifyUser.setOnAction(e -> {
            stage.close();
            ModifyUser modifyUser = new ModifyUser();
        });

        btnDeleteUser.setOnAction(e -> {
            stage.close();
            DeleteUser deleteUser = new DeleteUser();
        });

        btShowAllUsers.setOnAction(e -> {
            ShowAllUsers showAllUsers = new ShowAllUsers();
        });
    }
}
