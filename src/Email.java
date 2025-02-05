import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;

public class Email {
    private final String myAccountMail = "communicationsecure99@gmail.com";
    private final String password = "gnbp hsfa zgdy mfra";

    /*
    * This method sends an email to the recipient with the specified subject and message body.
    * It throws an exception if the email could not be sent.
    * */
    public void sendMail(String recipient, String subject, String messageBody) throws Exception {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountMail, password);
            }
        });

        Message message = prepareMessage(session, recipient, subject, messageBody);
        Transport.send(message);
    }

    /*
    * This method prepares the email message with the specified parameters.
    * It throws an exception if the message could not be prepared.
    * */
    private Message prepareMessage(Session session, String recipient, String subject,
                                   String messageBody) throws MessagingException {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("communicationsecure99@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(messageBody);

            return message;
        } catch (Exception e) {
            throw new MessagingException("Failed to prepare email message.", e);
        }
    }

    /*
    * This method generates a random 4-digit code and sends it to the recipient's email.
    * It returns the code that was sent.
    * */
    public String sendAuthCode(String recipient) {
        String subject = "Authentication Code";
        String code = generateRandomCode();
        String messageBody = "Your authentication code is: " + code;

        try {
            sendMail(recipient, subject, messageBody);
            return code;
        } catch (Exception e) {
            return "";
        }
    }

    /*
    * This method generates a random 4-digit code.
    * */
    private String generateRandomCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000);
        return String.valueOf(code);
    }

    /*
    * This method creates an email authentication scene with a text field for the user to enter the code.
    * It returns true if the user entered the correct code, and false otherwise.
    * */
    public static boolean emailAuthenticationScene(String receivedEmail) {
        Email email = new Email();
        String code = email.sendAuthCode(receivedEmail);
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Make it modal
        popupStage.setTitle("Authentication Request");

        // Create UI components
        Label instructionLabel = new Label("Provide the authentication code sent to your email:");
        TextField codeField = new TextField();
        codeField.setPromptText("Authentication code");
        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: #bcdfe5; -fx-text-fill: #454648; -fx-font-size: 16;");
        submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-background-color: #a3c7d3; -fx-text-fill: #454648; -fx-font-size: 16;"));
        submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-background-color: #bcdfe5; -fx-text-fill: #454648; -fx-font-size: 16;"));

        final boolean[] isAuthenticated = {false};

        // Handle button click
        submitButton.setOnAction(event -> {
            String enteredCode = codeField.getText();
            if (enteredCode.equals(code)) {
                isAuthenticated[0] = true;
                popupStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Authentication failed");
                alert.setContentText("The code you entered is incorrect.");
                alert.showAndWait();
            }
        });

        // Layout for the popup
        VBox popupLayout = new VBox(10, instructionLabel, codeField, submitButton);
        popupLayout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Scene and Stage setup
        Scene popupScene = new Scene(popupLayout, 300, 150);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();

        return isAuthenticated[0];
    }
}