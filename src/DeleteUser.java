import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DeleteUser {
    private DatabaseHandler db;

    public DeleteUser() {
        this.db = new DatabaseHandler();
        show();
    }

    public void show() {
        List<User> userList = new ArrayList<>();
        try (Connection conn = DatabaseHandler.connect(); Statement stmt = conn.createStatement(); ResultSet users = stmt.executeQuery("SELECT * FROM Users")) {
            while (users.next()) {
                User user = new User(
                        users.getInt("UserID"),
                        users.getString("Username"),
                        users.getString("Email"),
                        users.getString("PhoneNumber"),
                        users.getString("Role")
                );
                userList.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return;
        }

        TableView<User> table = new TableView<>();
        TableColumn<User, Integer> colUserID = new TableColumn<>("User ID");
        TableColumn<User, String> colUsername = new TableColumn<>("Username");
        TableColumn<User, String> colEmail = new TableColumn<>("Email");
        TableColumn<User, String> colPhoneNumber = new TableColumn<>("Phone Number");
        TableColumn<User, String> colUserType = new TableColumn<>("User Type");

        colUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colUserType.setCellValueFactory(new PropertyValueFactory<>("userType"));

        colUserID.setMinWidth(150);
        colUsername.setMinWidth(150);
        colUserType.setMinWidth(150);


        table.getColumns().addAll(colUserID, colUsername, colEmail, colPhoneNumber, colUserType);
        table.getItems().addAll(userList);
        table.setEditable(true);
        table.setPrefSize(800, 600);
        table.setStyle("-fx-font-size: 16;");


        Button btnBack = new Button("Back");
        btnBack.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;");
        btnBack.setOnMouseEntered(e -> btnBack.setStyle("-fx-background-color: lightcoral; -fx-text-fill: black; -fx-font-size: 16;"));
        btnBack.setOnMouseExited(e -> btnBack.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;"));
        btnBack.setOnAction(e -> {
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.close();
            ManageUsers manageUsers = new ManageUsers();
        });

        Button btnDelete = new Button("Delete");
        btnDelete.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;");
        btnDelete.setOnMouseEntered(e -> btnDelete.setStyle("-fx-background-color: lightcoral; -fx-text-fill: black; -fx-font-size: 16;"));
        btnDelete.setOnMouseExited(e -> btnDelete.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;"));
        btnDelete.setOnAction(e -> {
            User selectedUser = table.getSelectionModel().getSelectedItem();
            if (selectedUser == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No user selected");
                alert.setContentText("Please select a user to delete");
                alert.showAndWait();
                return;
            }

            Stage warningStage = new Stage();
            warningStage.setTitle("Warning");
            Label lblWarning = new Label("Are you sure you want to delete this user?");
            Button btnYes = new Button("Yes");
            Button btnNo = new Button("Cancel");
            HBox hBoxWarning = new HBox(10);
            hBoxWarning.getChildren().addAll(btnNo, btnYes);
            hBoxWarning.setAlignment(Pos.CENTER);
            VBox vBoxWarning = new VBox(10);
            vBoxWarning.getChildren().addAll(lblWarning, hBoxWarning);
            vBoxWarning.setAlignment(Pos.CENTER);
            warningStage.setScene(new Scene(vBoxWarning, 300, 200));
            warningStage.show();

            btnNo.setOnAction(e2 -> warningStage.close());
            btnYes.setOnAction(e2 -> {
                warningStage.close();
                if (db.deleteUser(selectedUser.getUserID())) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("User deleted successfully");
                    alert.showAndWait();
                    table.getItems().remove(selectedUser);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error deleting user");
                    alert.showAndWait();
                }
            });
        });

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(btnDelete, btnBack);
        hBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(50, table, hBox);
        vBox.setAlignment(Pos.CENTER);

        Image background = new Image("background.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
                background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)
        );

        vBox.setBackground(new Background(backgroundImage));

        Stage stage = new Stage();
        stage.setTitle("Modify User");
        stage.setScene(new Scene(vBox, 800, 600));
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.show();
    }

}
