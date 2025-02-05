import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LogIn {
    private DatabaseHandler databaseHandler;

    public LogIn() {
        logInScene();
        databaseHandler = new DatabaseHandler();
    }

    public LogIn(Stage stage) {
        stage.close();
        logInScene();
        databaseHandler = new DatabaseHandler();
    }

    /*
     * This method creates a log in scene with a background image, user image, password image, username text field,
     * password field, log in button, and privacy policy button.
     * */
    public void logInScene() {
        // User image
        Image user = new Image("userimage.png");
        ImageView userView = new ImageView(user);
        userView.setFitHeight(50);
        userView.setFitWidth(50);

        // Username text field
        TextField email = new TextField();
        email.setPromptText("Email");
        email.setPrefWidth(200);

        // HBox to hold user image and username text field
        HBox hUser = new HBox(10);
        hUser.setAlignment(Pos.CENTER);
        hUser.getChildren().addAll(userView, email);

        Image pass = new Image("key.png");
        ImageView passView = new ImageView(pass);
        passView.setFitHeight(50);
        passView.setFitWidth(50);
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setPrefWidth(200);

        // HBox to hold password image and password field
        HBox hPass = new HBox(10);
        hPass.setAlignment(Pos.CENTER);
        hPass.getChildren().addAll(passView, password);

        // Log in button
        Button btLogin = new Button("Log In");
        btLogin.setStyle("-fx-background-color: #bcdfe5; -fx-text-fill: #454648; -fx-font-size: 16;");
        btLogin.setOnMouseEntered(e -> btLogin.setStyle("-fx-background-color: #a3c7d3; -fx-text-fill: #454648; -fx-font-size: 16;"));
        btLogin.setOnMouseExited(e -> btLogin.setStyle("-fx-background-color: #bcdfe5; -fx-text-fill: #454648; -fx-font-size: 16;"));


        // Privacy policy button
        Button btPrivacyPolicy = new Button("Privacy Policy");
        btPrivacyPolicy.setStyle("-fx-background-color: #bcdfe5; -fx-text-fill: #454648; -fx-font-size: 16;");
        btPrivacyPolicy.setOnMouseEntered(e -> btPrivacyPolicy.setStyle("-fx-background-color: #a3c7d3; -fx-text-fill: #454648; -fx-font-size: 16;"));
        btPrivacyPolicy.setOnMouseExited(e -> btPrivacyPolicy.setStyle("-fx-background-color: #bcdfe5; -fx-text-fill: #454648; -fx-font-size: 16;"));

        // Background image
        Image background = new Image("log in screen.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(background,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                null,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));

        GridPane gridPane = new GridPane();
        gridPane.setBackground(new Background(backgroundImage));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.add(hUser, 0, 0);
        gridPane.add(hPass, 0, 1);
        gridPane.add(btLogin, 0, 2);
        gridPane.add(btPrivacyPolicy, 0, 3);

        GridPane.setHalignment(btLogin, HPos.CENTER);
        GridPane.setHalignment(btPrivacyPolicy, HPos.CENTER);

        Scene scene = new Scene(gridPane, 800, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Log In");
        stage.show();

        btLogin.setOnAction(e -> {
            String emailText = email.getText();
            String passwordText = password.getText();
            String hashedPassword = hashPassword(passwordText);

            if (checkUser(emailText, hashedPassword)) {
                email.clear();
                password.clear();
                if (emailAuthenticationScene(emailText)) {
                    int id = databaseHandler.getUserID(emailText);
                    String role = databaseHandler.getUserType(emailText);
                    if (role.equals("Admin")) {
                        stage.close();
                        AdminScreen adminScreen = new AdminScreen();
                    } else if (role.equals("Patient")) {
                        stage.close();
                        Patient patient = new Patient(emailText, id);
                    } else if (role.equals("LabWorker")) {
                        stage.close();
                        LabWorker labWorker = new LabWorker(emailText, id);
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Login failed");
                alert.setContentText("Wrong password or email");
                alert.showAndWait();
            }
        });

        btPrivacyPolicy.setOnAction(e -> {
            Privacy privacyPolicy = new Privacy();
        });
    }

    /*
     * This method creates an authenticated scene with a welcome text and an exit button.
     * */
    public boolean emailAuthenticationScene(String receivedEmail) {
//        boolean isAuthenticated = Email.emailAuthenticationScene(receivedEmail);
//        if (isAuthenticated) {
//            String phone = databaseHandler.getPhone(receivedEmail);
//            phone = "+970" + phone.substring(1);
//            return phoneAuthenticationScene(phone);
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText("Authentication failed");
//            alert.setContentText("Wrong authentication code");
//            alert.showAndWait();
//            return false;
//        }
        return true;
    }

    /*
     * This method creates a phone authentication scene with a success message and an exit button.
     * */
    public boolean phoneAuthenticationScene(String receivedPhone) {
//        boolean isAuthenticated = Mobile.phoneAuthenticationScene(receivedPhone);
//        if (isAuthenticated) {
//            return true;
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText("Authentication failed");
//            alert.setContentText("Wrong authentication code");
//            alert.showAndWait();
//            return false;
//        }
        return true;
    }

    /*
     * This method checks if the user exists in the database.
     * */
    public boolean checkUser(String email, String password) {
        String passwordFromDatabase = databaseHandler.getPassword(email);
        System.out.println(email);
        System.out.println(password);
        System.out.println(passwordFromDatabase);
        return password.equals(passwordFromDatabase);
    }

    /*
     * This method hashes a password using SHA-256.
     * */
    public static String hashPassword(String password) {
        try {
            // Create a MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Convert the password string to bytes and hash it
            byte[] hash = digest.digest(password.getBytes());

            // Convert the hashed bytes to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
