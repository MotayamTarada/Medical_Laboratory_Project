import java.text.ParseException;
import java.text.SimpleDateFormat;

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

public class EditPersonalDetailsForLab extends Stage {
	public EditPersonalDetailsForLab() {
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
	            Label l11 = new Label("User ID : ");
	            TextField tf11 = new TextField();
	            Label l1 = new Label("User Name : ");
	            TextField tf1 = new TextField();
	            Label l2 = new Label("Password : ");
	            TextField tf2 = new TextField();
	            Label l3 = new Label("Full Name : ");
	            TextField tf3 = new TextField();
	            Label l4 = new Label("Email : ");
	            TextField tf4 = new TextField();
	            
	            Label l5 = new Label("Phone Number : ");
	            TextField tf5 = new TextField();
	            Label l6 = new Label("Date Created : ");
	            TextField tf6 = new TextField("2024/12/27");
	           
	            Button edit = createStyledButton("Edit");

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
	            
	            edit.setOnAction(e -> {
	                DatabaseHandler dbHandler = new DatabaseHandler();
	                
	                SimpleDateFormat inputFormat = new SimpleDateFormat("M/d/yyyy"); // Input format
	                java.util.Date parsedDateCreated = null;
					try {
						parsedDateCreated = inputFormat.parse(tf6.getText());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

	                try {
	                    boolean isUpdated = dbHandler.updatePersonalDetails(
		                Integer.parseInt(tf11.getText()), // PhoneNumber
	                    	tf1.getText(), // UserName
	                        tf2.getText(), // Password
	                        tf3.getText(), // FullName
	                        tf4.getText(), // Email
	                        tf5.getText(),  // PhoneNumber
	                        new java.sql.Date(parsedDateCreated.getTime()) // Convert java.util.Date to java.sql.Date
	                        
	                    );

	                    if (isUpdated) {
	                        tf1.clear();
	                        tf2.clear();
	                        tf3.clear();
	                        tf4.clear();
	                        tf5.clear();
	                        tf6.clear();
	                        tf11.clear();


	                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Details updated successfully.", ButtonType.OK);
	                        alert.setTitle("Update Success");
	                        alert.setHeaderText(null);
	                        alert.showAndWait();
	                    } else {
	                        Alert alert = new Alert(Alert.AlertType.ERROR, "Update failed. Please check your details.", ButtonType.OK);
	                        alert.setTitle("Update Error");
	                        alert.setHeaderText(null);
	                        alert.showAndWait();
	                    }
	                } catch (Exception ex) {
	                    Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred during the update.", ButtonType.OK);
	                    alert.setTitle("Error");
	                    alert.setHeaderText(null);
	                    alert.showAndWait();
	                    ex.printStackTrace();
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
	            HBox h6 = new HBox(30);
	            h6.getChildren().addAll(l5,tf5);
	            HBox h7 = new HBox(30);
	            h7.getChildren().addAll(l6,tf6);
	            HBox h8 = new HBox(30);
	            HBox h11 = new HBox(30);
	            h11.getChildren().addAll(l11,tf11);
	            
	            HBox h9 = new HBox(30);
	            
	            HBox h10 = new HBox(30);
	            h8.getChildren().addAll(edit,exithomescreen);
	            VBox v1 = new VBox(30);
	            v1.getChildren().addAll(h11,h1,h2,h3,h4,h6,h7,h8,h10);
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

}
