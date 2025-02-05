import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ModifyUser {
    private DatabaseHandler db;

    public ModifyUser() {
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

        colUsername.setCellFactory(TextFieldTableCell.forTableColumn());
        colUsername.setOnEditCommit(event -> {
            User user = event.getRowValue();
            String newUsername = event.getNewValue();
            user.setUsername(newUsername);
            db.modifyUserName(user.getUserID(), newUsername);
        });

        // Allow editing of Email
        colEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        colEmail.setOnEditCommit(event -> {
            User user = event.getRowValue();
            String newEmail = event.getNewValue();
            user.setEmail(newEmail);
            db.modifyUserEmail(user.getUserID(), newEmail);
        });

        // Allow editing of Phone Number
        colPhoneNumber.setCellFactory(TextFieldTableCell.forTableColumn());
        colPhoneNumber.setOnEditCommit(event -> {
            User user = event.getRowValue();
            String newPhoneNumber = event.getNewValue();
            user.setPhone(newPhoneNumber);
            db.modifyUserPhone(user.getUserID(), newPhoneNumber);
        });

        // Allow editing of Role with a choice box
        colUserType.setCellFactory(column -> new TableCell<>() {
            private final ComboBox<String> comboBox = new ComboBox<>();

            {
                comboBox.getItems().addAll("Admin", "Patient", "LabWorker");
                comboBox.setOnAction(e -> {
                    User user = getTableView().getItems().get(getIndex());
                    String newRole = comboBox.getValue();
                    user.setUserType(newRole);
                    db.modifyUserRole(user.getUserID(), newRole);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    comboBox.setValue(item);
                    setGraphic(comboBox);
                }
            }
        });


        Button btnBack = new Button("Back");
        btnBack.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;");
        btnBack.setOnMouseEntered(e -> btnBack.setStyle("-fx-background-color: lightcoral; -fx-text-fill: black; -fx-font-size: 16;"));
        btnBack.setOnMouseExited(e -> btnBack.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;"));
        btnBack.setOnAction(e -> {
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.close();
            ManageUsers manageUsers = new ManageUsers();
        });

        VBox vBox = new VBox(50, table, btnBack);
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
