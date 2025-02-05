import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        // Image for background
        Image background = new Image("background.jpg");
        // BackgroundImage to set the background image on grid pane
        BackgroundImage backgroundImage = new BackgroundImage(background,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                null,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));

        VBox vBox = new VBox(50);
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(new Background(backgroundImage));

        Label label = new Label("Welcome to BZU Labs");
        label.setStyle("-fx-font-size: 36; -fx-text-fill: black; -fx-font-weight: bold;");

        Button btExit = new Button("Exit");
        btExit.setOnAction(e -> System.exit(0));
        btExit.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;");
        btExit.setOnMouseEntered(e -> btExit.setStyle("-fx-background-color: lightcoral; -fx-text-fill: black; -fx-font-size: 16;"));
        btExit.setOnMouseExited(e -> btExit.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-font-size: 16;"));

        Button btLogin = new Button("Log In");
        btLogin.setOnAction(e -> {
            LogIn logIn = new LogIn(stage);
        });
        btLogin.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;");
        btLogin.setOnMouseEntered(e -> btLogin.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-font-size: 16;"));
        btLogin.setOnMouseExited(e -> btLogin.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-font-size: 16;"));

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(btLogin, btExit);

        btExit.setPrefWidth(100);
        btExit.setPrefHeight(50);

        btLogin.setPrefWidth(100);
        btLogin.setPrefHeight(50);

        vBox.getChildren().addAll(label, hBox);

        Scene scene = new Scene(vBox, 800, 600);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("BZU Labs");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}