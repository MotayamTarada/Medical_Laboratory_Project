import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class ShowAllUsers {
    private DatabaseHandler db = new DatabaseHandler();

    public ShowAllUsers() {
        show();
    }

    public void show() {
        ResultSet rs = db.getAllFromTable("users");
        Stage stage = new Stage();
        TableView<User> table = new TableView<>();

        TableColumn<User, Integer> userId = new TableColumn<>("User ID");
        userId.setCellValueFactory(new PropertyValueFactory<>("userID"));

        TableColumn<User, String> username = new TableColumn<>("Username");
        username.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> email = new TableColumn<>("Email");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, String> phone = new TableColumn<>("Phone");
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<User, String> userType = new TableColumn<>("User Type");
        userType.setCellValueFactory(new PropertyValueFactory<>("userType"));

        table.getColumns().addAll(userId, username, email, phone, userType);

        try {
            while (rs.next()) {
                table.getItems().add(new User(rs.getInt("userID"), rs.getString("username"), rs.getString("email"), rs.getString("PhoneNumber"), rs.getString("role")));
            }

            Scene scene = new Scene(table, 650, 500);
            stage.setScene(scene);
            stage.setTitle("All Users");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}