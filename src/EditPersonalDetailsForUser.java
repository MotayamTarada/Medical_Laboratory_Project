import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import javafx.util.Duration;

public class EditPersonalDetailsForUser extends Stage{
    final InactivityMonitor inactivityMonitor = new InactivityMonitor(60 * 1000); // 1 minute timeout
    DatabaseHandler dh = new DatabaseHandler();
	public EditPersonalDetailsForUser() {
		  setTitle("Edit Personal Details");
	        try {
	            Image image = new Image("background.jpg");
	            ImageView imageView = new ImageView(image);
	            imageView.setFitWidth(1150);
	            imageView.setFitHeight(630);
	            javafx.scene.shape.Rectangle backgroundRect = new javafx.scene.shape.Rectangle(530, 90);
	            StackPane backgroundPane = new StackPane();
	            backgroundRect.setFill(Color.BLACK);
	            backgroundRect.setOpacity(0.6);

	            Text welcomeText = new Text("Edit Personal Details");
	            backgroundPane.getChildren().addAll(backgroundRect, welcomeText);

	            welcomeText.setFont(Font.font(35));
	            welcomeText.setStyle("-fx-fill: white;");

//	            Button startButton = createStyledButton("Get Started");
	            Label l1 = new Label("Your Current Email:");
	            TextField tf1 = new TextField();
	            Label l2 = new Label("New User Name: ");
	            TextField tf2 = new TextField();
	            Label l3 = new Label("New Phone Number: ");
	            TextField tf3 = new TextField();
	            Label l4 = new Label("New Password: ");
	            TextField tf4 = new TextField();
	            Button AddStudent = createStyledButton("Update");

	            Button exithomescreen = createStyledButton("Exit");
	            String labelStyle = "-fx-text-fill: black; -fx-stroke: white; -fx-stroke-width: 10px;";
	            String labelStyle1 = "-fx-text-fill: white;";

	            l1.setStyle(labelStyle);
	            l2.setStyle(labelStyle);
	            l3.setStyle(labelStyle);
	            l4.setStyle(labelStyle);
	            


	            applyFadeInAnimation(l1, tf1);
	            applyFadeInAnimation(l2, tf2);
	            applyFadeInAnimation(l3, tf3);
	            applyFadeInAnimation(l4, tf4);
	           
	            applyInitialTransparency(l1, tf1);
	            applyInitialTransparency(l2, tf2);
	            applyInitialTransparency(l3, tf3);
	            applyInitialTransparency(l4, tf4);
	            
	            AddStudent.setOnAction(e -> {

	 			    try {
	 			       
	 			        // Use the parsed date in your addStudent method
	 			        dh.updateUserDetails(tf2.getText(), tf3.getText(), tf4.getText(), tf1.getText());			        
	 			        tf1.clear();
	 			        tf2.clear();
	 			        tf3.clear();
	 			        tf4.clear();
	 			       

	 			        Alert alert = new Alert(Alert.AlertType.INFORMATION, "adding was successfull.", ButtonType.OK);
	 			        alert.setTitle("success in adding course");
	 			        alert.setHeaderText(null);
	 			        alert.showAndWait();
	 			        
	 			    } catch (Exception e1) {
	 			    	Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred while adding the student.", ButtonType.OK);
	 			        alert.setTitle("Error adding course");
	 			        alert.setHeaderText(null);
	 			        alert.showAndWait();
	 			        e1.printStackTrace();
	 			        // Handle the parsing error gracefully, e.g., show an error message to the user
	 			    }
	            });
	           
	 

	            HBox h1 = new HBox(30);
	            h1.getChildren().addAll(l1,tf1);
	            HBox h2 = new HBox(30);
	            h2.getChildren().addAll(l2,tf2);
	            HBox h3 = new HBox(30);
	            h3.getChildren().addAll(l3,tf3);
	            HBox h4 = new HBox(30);
	            h4.getChildren().addAll(l4,tf4);
	            HBox h5 = new HBox(30);
	            HBox h8 = new HBox(30);
	            h8.getChildren().addAll(AddStudent,exithomescreen);
	            VBox v1 = new VBox(30);
	            v1.getChildren().addAll(h1,h2,h3,h4,h5,h8);
	            StackPane layout = new StackPane();
	            javafx.scene.control.Label ll1 = new javafx.scene.control.Label(" ");
	            layout.getChildren().addAll(ll1, imageView, backgroundPane, v1);

	            // Center the HBox in the middle of the screen

	            StackPane.setMargin(backgroundRect, new Insets(30, 0, 0, 0));
	            StackPane.setAlignment(backgroundRect, javafx.geometry.Pos.TOP_CENTER);
	            StackPane.setAlignment(welcomeText, javafx.geometry.Pos.TOP_CENTER);
	            StackPane.setAlignment(v1, javafx.geometry.Pos.CENTER);
	            v1.setAlignment(Pos.CENTER);
	            StackPane.setAlignment(v1, Pos.CENTER);
	            StackPane.setAlignment(imageView, javafx.geometry.Pos.CENTER);

	            // Set margin to move HBox down
	            StackPane.setMargin(welcomeText, new Insets(50, 0, 0, 0));
	            StackPane.setMargin(v1, new Insets(100, 0, 0, 440));

	            Scene scene = new Scene(layout, 1150, 630); // Set your desired dimensions

	            // Add fade-in and scale animations to the main screen
	            Transition fadeTransition = createFadeTransition(layout);
	            Transition scaleTransition = createScaleTransition(layout);

	            ParallelTransition parallelTransition = new ParallelTransition(fadeTransition, scaleTransition);
	            parallelTransition.play();

	            setScene(scene);
	            
	            show();
	            inactivityMonitor.monitorScene(scene);


	            exithomescreen.setOnAction(e -> close());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
			
		}
		 private Transition createFadeTransition(StackPane node) {
		        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), node);
		        fadeTransition.setFromValue(0);
		        fadeTransition.setToValue(1);
		        fadeTransition.setInterpolator(javafx.animation.Interpolator.EASE_OUT);
		        return fadeTransition;
		    }

		    private Transition createScaleTransition(StackPane node) {
		        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), node);
		        scaleTransition.setFromX(0.5);
		        scaleTransition.setFromY(0.5);
		        scaleTransition.setToX(1);
		        scaleTransition.setToY(1);
		        scaleTransition.setInterpolator(javafx.animation.Interpolator.EASE_OUT);
		        return scaleTransition;
		    }

		    private Button createStyledButton(String text) {
		        Button button = new Button(text);
		        button.setFont(Font.font(18));
		        button.setMinWidth(120);

		        // Hover effect
		        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"));
		        button.setOnMouseExited(e -> button.setStyle(null));

		        return button;
		    }
		    private void applyInitialTransparency(Node... nodes) {
		        for (Node node : nodes) {
		            node.setOpacity(0);
		        }
		    }

		    private void applyFadeInAnimation(Node... nodes) {
		        for (Node node : nodes) {
		            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), node);
		            fadeTransition.setFromValue(0);
		            fadeTransition.setToValue(1);
		            fadeTransition.play();
		        }
		    }
		    public void close() {
		        inactivityMonitor.stopMonitoring();
		        super.close();
		    }
		    
		    
		    
}
