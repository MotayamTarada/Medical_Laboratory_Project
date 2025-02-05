import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class Privacy extends Stage {

    public Privacy() {
        setTitle("Privacy and Policy - PAFM Test");

        // Privacy and policy content
        String policyText = """
                Privacy Policy for PAFM Test Desktop Program
                
                1. Introduction:
                   This Privacy Policy outlines how we collect, use, and protect your personal data while using the PAFM Test desktop application.
                
                2. Data Collection:
                   - The application may collect patient IDs, names, test results, and emergency contact information.
                   - This data is securely stored and used strictly for diagnostic and record-keeping purposes.
                
                3. Data Usage:
                   - Data collected will not be shared with third parties unless required by law.
                   - Data will only be accessed by authorized personnel.

                4. Data Protection:
                   - All data is stored in secure databases with encryption and restricted access.
                   - Regular audits are conducted to ensure data integrity and security.

                5. User Rights:
                   - Users have the right to access their data and request corrections.
                   - Users may contact support for data-related inquiries.

                6. Changes to Policy:
                   - This Privacy Policy may be updated periodically.
                   - Users will be notified of significant changes.

                By using the PAFM Test desktop program, you agree to this Privacy Policy.
                """;

        // Create a TextFlow for displaying the privacy policy
        TextFlow policyTextFlow = new TextFlow(new Text(policyText));
        policyTextFlow.setTextAlignment(TextAlignment.JUSTIFY);
        policyTextFlow.setPadding(new Insets(15));
        policyTextFlow.setStyle("-fx-font-size: 14px; -fx-line-spacing: 1.5;");

        // Add scroll functionality for long text
        ScrollPane scrollPane = new ScrollPane(policyTextFlow);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(15));

        // Create a close button
        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px;");
        closeButton.setOnAction(e -> close());

        // Create a button container
        HBox buttonContainer = new HBox(closeButton);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(15));

        // Create a layout for the stage
        BorderPane layout = new BorderPane();
        layout.setCenter(scrollPane);
        layout.setBottom(buttonContainer);

        // Create and set the scene
        Scene scene = new Scene(layout, 600, 400);
        setScene(scene);

        // Show the stage
        show();
    }
}
