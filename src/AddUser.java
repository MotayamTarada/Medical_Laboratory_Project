import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AddUser {
    private DatabaseHandler db;

    public AddUser() {
        this.db = new DatabaseHandler();
        addUser();
    }

    /*
     * Add user to the database, it sends a verification email to the user's email address.
     * If the email is verified, the user is added to the database.
     * If the email is not verified, the user is not added to the database.
     * */
    public void addUser() {
        Stage stage = new Stage();
        Label lblTitle = new Label("Add User");
        lblTitle.setStyle("-fx-font-size: 35; -fx-font-weight: bold;");

        Button btnBack = new Button("Back");
        btnBack.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;");
        btnBack.setOnMouseEntered(e -> btnBack.setStyle("-fx-background-color: lightcoral; -fx-text-fill: black; -fx-font-size: 16;"));
        btnBack.setOnMouseExited(e -> btnBack.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;"));

        TextField tfUserID = new TextField();
        tfUserID.setPromptText("User ID");

        TextField tfUsername = new TextField();
        tfUsername.setPromptText("Username");

        TextField tfEmail = new TextField();
        tfEmail.setPromptText("Email");

        PasswordField tfPassword = new PasswordField();
        tfPassword.setPromptText("Password");

        TextField tfPhone = new TextField();
        tfPhone.setPromptText("Phone Number");


        ComboBox<String> cbUserType = new ComboBox<>();
        cbUserType.getItems().addAll("Admin", "LabWorker", "Patient");
        cbUserType.setPromptText("User Type");

        Button btnAdd = new Button("Add User");
        btnAdd.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btnAdd.setOnMouseEntered(e -> btnAdd.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btnAdd.setOnMouseExited(e -> btnAdd.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));
        btnAdd.setPrefSize(100, 50);

        Button btnClear = new Button("Clear");
        btnClear.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;");
        btnClear.setOnMouseEntered(e -> btnClear.setStyle("-fx-background-color: lightcoral; -fx-text-fill: black; -fx-font-size: 16;"));
        btnClear.setOnMouseExited(e -> btnClear.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;"));

        HBox hButtons = new HBox(10);
        hButtons.getChildren().addAll(btnAdd, btnClear);

        GridPane gp = new GridPane();
        gp.add(lblTitle, 0, 0, 2, 1);
        gp.add(new Label("User ID:"), 0, 1);
        gp.add(tfUserID, 1, 1);
        gp.add(new Label("Username:"), 0, 2);
        gp.add(tfUsername, 1, 2);
        gp.add(new Label("Email:"), 0, 3);
        gp.add(tfEmail, 1, 3);
        gp.add(new Label("Password:"), 0, 4);
        gp.add(tfPassword, 1, 4);
        gp.add(new Label("Phone Number:"), 0, 5);
        gp.add(tfPhone, 1, 5);
        gp.add(new Label("User Type:"), 0, 6);
        gp.add(cbUserType, 1, 6);
        gp.add(hButtons, 0, 8, 2, 1);
        gp.add(btnBack, 0, 9, 2, 1);
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        GridPane.setHalignment(btnBack, HPos.CENTER);

        Image background = new Image("background.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(background,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                null,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));

        gp.setBackground(new Background(backgroundImage));

        stage.setTitle("Add User");
        stage.setScene(new Scene(gp, 800, 600));
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.show();

        btnBack.setOnAction(e -> {
            stage.close();
            ManageUsers manageUsers = new ManageUsers();
        });

        btnClear.setOnAction(e -> {
            tfUserID.clear();
            tfUsername.clear();
            tfEmail.clear();
            tfPassword.clear();
            tfPhone.clear();
            cbUserType.getSelectionModel().clearSelection();
        });

        btnAdd.setOnAction(e -> {
            try {
                String userID = tfUserID.getText();
                String username = tfUsername.getText();
                String email = tfEmail.getText();
                String password = tfPassword.getText();
                String phone = tfPhone.getText();
                String userType = cbUserType.getValue();

                // Check if all fields are filled
                if (userID.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || userType == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("All fields are required!");
                    alert.showAndWait();
                    return;
                }

                // Check if email is valid
                if (!email.contains("@") || !email.contains(".")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid email address!");
                    alert.showAndWait();
                    return;
                }

                // Check if phone number is valid
//                if (!Mobile.isValidPhone(phone)) {
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Error");
//                    alert.setHeaderText("Phone number must be 10 digits!");
//                    alert.showAndWait();
//                    return;
//                }

                // Check if user already exists
                if (checkUser(email, password)) {
                    tfUserID.clear();
                    tfUsername.clear();
                    tfEmail.clear();
                    tfPassword.clear();
                    tfPhone.clear();
                    cbUserType.getSelectionModel().clearSelection();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("User already exists!");
                    alert.showAndWait();
                    return;
                }

                if (userType.equals("Patient")) {
                    if (!db.patientExists(Integer.parseInt(userID))) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("The patient does not has a record. Please add a record first");
                        alert.showAndWait();
                        return;
                    }
                }

                // Check if email is authenticated
                if (Email.emailAuthenticationScene(email)) {
                    try {
                        boolean isAdded = db.addUser(Integer.parseInt(userID), username, LogIn.hashPassword(password), username, email, phone, userType);
                        Alert alert;
                        if (isAdded) {
                            tfUserID.clear();
                            tfUsername.clear();
                            tfEmail.clear();
                            tfPassword.clear();
                            tfPhone.clear();
                            cbUserType.getSelectionModel().clearSelection();
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.setHeaderText("User added successfully!");
                        } else {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Failed to add user!");
                        }
                        alert.showAndWait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("An error occurred while adding user!");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Authentication failed!");
                    alert.showAndWait();
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("User ID must be a number!");
                alert.showAndWait();
            }

        });

    }


    /*
     * Check if the user already exists in the database
     * */
    public boolean checkUser(String email, String password) {
        String passwordFromDatabase = db.getPassword(email);
        return password.equals(passwordFromDatabase);
    }
}
