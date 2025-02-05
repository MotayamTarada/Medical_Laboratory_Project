import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UrineTestStage extends Stage {
    public UrineTestStage() {
        setTitle("Add New Test Stage");

        try {
            // Background Image

            Image image = new Image("background.jpg");
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(1150);
            imageView.setFitHeight(630);

            // Background overlay
            javafx.scene.shape.Rectangle backgroundRect = new javafx.scene.shape.Rectangle(1150, 630);
            backgroundRect.setFill(Color.BLACK);
            backgroundRect.setOpacity(0.5);

            // Welcome text
            Text welcomeText = new Text("Add Urine Test");
            welcomeText.setFont(Font.font("Arial", 35));
            welcomeText.setFill(Color.WHITE);

            VBox welcomeBox = new VBox(welcomeText);
            welcomeBox.setAlignment(Pos.TOP_CENTER);
            welcomeBox.setPadding(new Insets(30, 0, 50, 0));

            // Form fields
            String labelStyle = "-fx-text-fill: white; -fx-font-size: 16px;";
            Label l11 = new Label("Patient ID:");
            l11.setStyle(labelStyle);
            TextField tf11 = new TextField();

            Label l12 = new Label("Urine ID:");
            l12.setStyle(labelStyle);
            TextField tf12 = new TextField();

            Label l1 = new Label("Urine Test 1:");
            l1.setStyle(labelStyle);
            TextField tf1 = new TextField();

            Label l2 = new Label("Urine Test 2:");
            l2.setStyle(labelStyle);
            TextField tf2 = new TextField();

            Label l3 = new Label("Urine Test 3:");
            l3.setStyle(labelStyle);
            TextField tf3 = new TextField();

            // Layout HBoxes for each field
            HBox h1 = createFormRow(l11, tf11);
            HBox h2 = createFormRow(l12, tf12);
            HBox h3 = createFormRow(l1, tf1);
            HBox h4 = createFormRow(l2, tf2);
            HBox h5 = createFormRow(l3, tf3);

            // Buttons
            Button editButton = createStyledButton("Add");
            Button exitButton = createStyledButton("Exit");

            HBox buttonBox = new HBox(20, editButton, exitButton);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.setPadding(new Insets(20));

            // VBox for form layout
            VBox vBox = new VBox(20, h1, h2, h3, h4, h5, buttonBox);
            vBox.setAlignment(Pos.CENTER);
            vBox.setPadding(new Insets(20));

            // StackPane layout
            StackPane layout = new StackPane(imageView, backgroundRect, welcomeBox, vBox);

            // Scene setup
            Scene scene = new Scene(layout, 1150, 630);
            setScene(scene);
            show();

            // Button actions
            editButton.setOnAction(e -> {
                try {

                    int test1 = Integer.parseInt(tf1.getText());
                    int test2 = Integer.parseInt(tf2.getText());
                    int test3 = Integer.parseInt(tf3.getText());

                    double average = (test1 + test2 + test3) / 3.0;

                    DatabaseHandler dbHandler = new DatabaseHandler(
                    );
                    dbHandler.insertUrineTest(
                            Integer.parseInt(tf12.getText()),
                            Integer.parseInt(tf1.getText()),
                            Integer.parseInt(tf2.getText()),
                            Integer.parseInt(tf3.getText()),
                            Integer.parseInt(tf11.getText()),
                            average,
                            average > 8 ? "High" : average < 0 ? "Low" : "Normal"
                    );
                    showAlert("Success", "Urine Test details added successfully.");
                } catch (Exception ex) {
                    showAlert("Error", "Failed to add Urine Test details.");
                    ex.printStackTrace();
                }
            });

            exitButton.setOnAction(e -> close());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HBox createFormRow(Label label, TextField textField) {
        HBox hbox = new HBox(10, label, textField);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font(16));
        button.setMinWidth(100);
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px;");

        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #45A049; -fx-text-fill: white;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"));

        return button;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}


