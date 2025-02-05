import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RatingFeedBackResults extends Stage {

	    public RatingFeedBackResults(RatingData data) {
	        setTitle("Rating & Feedback Results");

	        Label titleLabel = new Label("Rating & Feedback");
	        titleLabel.setFont(Font.font("Arial", 36));
	        titleLabel.setTextFill(Color.BLACK);

	        VBox feedbackVBox = new VBox(15);
	        feedbackVBox.setAlignment(Pos.CENTER_LEFT);

	        feedbackVBox.getChildren().addAll(
	            createFeedbackLabel("Average Reception Treating:", String.format("%.1f/10", data.getReceptionTreating()), "#4CAF50"),
	            createFeedbackLabel("Average Lab Treating:", String.format("%.1f/10", data.getLabTreating()), "#FFCA28"),
	            createFeedbackLabel("Average Interface Usability:", String.format("%.1f/10", data.getInterfaceUsability()), "#F44336"),
	            createFeedbackLabel("Average Privacy & Security:", String.format("%.1f/10", data.getPrivacyAndSecurity()), "#4CAF50")
	        );

	        VBox notesVBox = new VBox(10);
	        notesVBox.setAlignment(Pos.CENTER);
	        notesVBox.setPadding(new Insets(20));

	        Label notesLabel = new Label("Notes:");
	        notesLabel.setFont(Font.font("Arial", 24));
	        notesLabel.setTextFill(Color.BLACK);

	        TextArea notesTextArea = new TextArea(data.getNotes());
	        notesTextArea.setPrefSize(400, 300);

	        notesVBox.getChildren().addAll(notesLabel, notesTextArea);

	        Button exitButton = createStyledButton("Exit", "#B71C1C");
	        exitButton.setOnAction(e -> close());

	        VBox buttonVBox = new VBox(15, exitButton);
	        buttonVBox.setAlignment(Pos.CENTER);

	        HBox mainContent = new HBox(50, feedbackVBox, notesVBox, buttonVBox);
	        mainContent.setAlignment(Pos.CENTER);
	        mainContent.setPadding(new Insets(20));

	        VBox mainLayout = new VBox(20, titleLabel, mainContent);
	        mainLayout.setAlignment(Pos.TOP_CENTER);
	        mainLayout.setPadding(new Insets(20));

	        StackPane root = new StackPane(mainLayout);

	        Scene scene = new Scene(root, 1150, 630);
	        setScene(scene);
			show();
	    }

	    private HBox createFeedbackLabel(String text, String value, String color) {
	        Label label = new Label(text);
	        label.setFont(Font.font("Arial", 18));
	        label.setTextFill(Color.BLACK);

	        Label valueLabel = new Label(value);
	        valueLabel.setFont(Font.font("Arial", 18));
	        valueLabel.setTextFill(Color.web(color));

	        HBox feedbackBox = new HBox(5, label, valueLabel);
	        feedbackBox.setAlignment(Pos.CENTER_LEFT);
	        feedbackBox.setPadding(new Insets(10));

	        return feedbackBox;
	    }

	    private Button createStyledButton(String text, String color) {
	        Button button = new Button(text);
	        button.setFont(Font.font("Arial", 16));
	        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white;");
	        return button;
	    }
	}

   



