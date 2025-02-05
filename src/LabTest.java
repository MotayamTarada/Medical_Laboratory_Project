import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LabTest extends Stage {
	public LabTest() {
        setTitle("Lab Test");

        // Background setup
        StackPane root = new StackPane();
        try {
            Image backgroundImage = new Image("background.jpg");
            ImageView backgroundView = new ImageView(backgroundImage);
            backgroundView.setFitWidth(1350);
            backgroundView.setFitHeight(780);
            root.getChildren().add(backgroundView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Title
        Text title = new Text("Lab Test");
        title.setFont(Font.font(35));
        title.setStyle("-fx-fill: black; -fx-font-weight: bold;");

        // Buttons
        Button bloodSugar = createStyledButton("Blood Sugar");
        Button bloodTest = createStyledButton("Blood Presure");
        Button urineExamination = createStyledButton("Urine Examination");

        Button exitButton = createStyledButton("Exit");
        exitButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");

        Button logoutButton = createStyledButton("Log Out");
        logoutButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");

        // Button actions
        bloodSugar.setOnAction(e ->{
        	BloodSugarStage ss = new BloodSugarStage();
        	ss.show();
        });
        bloodTest.setOnAction(e -> {
        	BloodPressureStage ss = new BloodPressureStage();
        	ss.show();
        });
        urineExamination.setOnAction(e -> {
        	UrineTestStage ss = new UrineTestStage();
        	ss.show();
        });
        exitButton.setOnAction(e -> close());
        logoutButton.setOnAction(e -> System.out.println("Log Out clicked!"));

        // Add animations
        addHoverAnimations(bloodSugar);
        addHoverAnimations(bloodTest);
        addHoverAnimations(urineExamination);
        addHoverAnimations(exitButton);
        addHoverAnimations(logoutButton);

        // Horizontal button layout
        HBox buttonBox = new HBox(20, bloodSugar, bloodTest, urineExamination, exitButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Vertical layout
        VBox centerLayout = new VBox(30, title, buttonBox, logoutButton);
        centerLayout.setAlignment(Pos.CENTER);

        // Add fade-in animation for the whole layout
        Transition fadeTransition = createFadeTransition(centerLayout);
        fadeTransition.play();

        root.getChildren().add(centerLayout);

        // Scene setup
        Scene scene = new Scene(root, 1350, 780);
        setScene(scene);
        show();
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font(18));
        button.setMinWidth(150);
        button.setMinHeight(40);
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #45a049; -fx-text-fill: white;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"));
        return button;
    }

    private Transition createFadeTransition(Region node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        return fadeTransition;
    }

    private void addHoverAnimations(Button button) {
        button.setOnMouseEntered(e -> {
            createScaleTransition(button, 1.2, 1.2).play();
        });
        button.setOnMouseExited(e -> {
            createScaleTransition(button, 1, 1).play();
        });
    }

    private Transition createScaleTransition(Button button, double toX, double toY) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.25), button);
        scaleTransition.setFromX(button.getScaleX());
        scaleTransition.setFromY(button.getScaleY());
        scaleTransition.setToX(toX);
        scaleTransition.setToY(toY);
        return scaleTransition;
    }
}
