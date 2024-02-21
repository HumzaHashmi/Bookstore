import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import coe528.project.Customer;
import coe528.project.Books;
import coe528.project.SilverCustomer;
import coe528.project.Owner;

public class FinalFxMain extends Application {
    private Customer customer = null;
    private Owner owner = null;
    private static final int BUTTON_MAX_WIDTH = 100;
    private static final int SCENE_WIDTH = 600;
    private static final int SCENE_HEIGHT = 400;
    private static final String APP_TITLE = "BookStore App";
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "admin";

    @Override
    public void start(Stage primaryStage) {
        owner = Owner.getOwnerInstance();
        owner.addBooks("Book 1", 20.0);
        owner.addBooks("Book 2", 25.0);
        owner.addBooks("Book 3", 30.0);

        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(createLoginScene(primaryStage));
        primaryStage.show();
    }

    private Scene createLoginScene(Stage primaryStage) {
        GridPane grid = createGridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Label welcomeLabel = new Label("Welcome to the BookStore App");
        GridPane.setConstraints(welcomeLabel, 0, 0, 2, 1);

        Label userLabel = new Label("User ID:");
        GridPane.setConstraints(userLabel, 0, 1);

        TextField usernameField = new TextField();
        GridPane.setConstraints(usernameField, 1, 1);

        Label passLabel = new Label("Password:");
        GridPane.setConstraints(passLabel, 0, 2);

        PasswordField passwordField = new PasswordField();
        GridPane.setConstraints(passwordField, 1, 2);

        Button loginButton = new Button("Login");
        loginButton.setMaxWidth(BUTTON_MAX_WIDTH);
        GridPane.setConstraints(loginButton, 1, 3);
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (username.equals(DEFAULT_USERNAME) && password.equals(DEFAULT_PASSWORD)) {
                primaryStage.setScene(createOwnerScene(primaryStage));
            } else {
                customer = owner.getCustomers(username, password);
                if (customer != null) {
                    primaryStage.setScene(createCustomerScene(primaryStage));
                } else {
                    showAlert(Alert.AlertType.ERROR, "Invalid username or password.");
                }
            }
        });

        grid.getChildren().addAll(welcomeLabel, userLabel, usernameField, passLabel, passwordField, loginButton);
        return new Scene(grid, SCENE_WIDTH, SCENE_HEIGHT);
    }

    private Scene createOwnerScene(Stage primaryStage) {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        Button addBookButton = new Button("Add Book");
        addBookButton.setOnAction(e -> addBookDialog(primaryStage));
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> primaryStage.setScene(createLoginScene(primaryStage)));

        TableView<Books> booksTable = new TableView<>();
        TableColumn<Books, String> bookNameColumn = new TableColumn<>("Book Name");
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        TableColumn<Books, Double> bookPriceColumn = new TableColumn<>("Book Price");
        bookPriceColumn.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));

        booksTable.setItems(owner.getBooks());
        booksTable.getColumns().addAll(bookNameColumn, bookPriceColumn);

        vbox.getChildren().addAll(booksTable, addBookButton, logoutButton);
        return new Scene(vbox, SCENE_WIDTH, SCENE_HEIGHT);
    }

    private Scene createCustomerScene(Stage primaryStage) {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        Button buyButton = new Button("Buy");
        buyButton.setOnAction(e -> buyDialog(primaryStage));
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> primaryStage.setScene(createLoginScene(primaryStage)));

        TableView<Books> booksTable = new TableView<>();
        TableColumn<Books, String> bookNameColumn = new TableColumn<>("Book Name");
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        TableColumn<Books, Double> bookPriceColumn = new TableColumn<>("Book Price");
        bookPriceColumn.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
        TableColumn<Books, CheckBox> selectColumn = new TableColumn<>("Select");
        selectColumn.setCellValueFactory(new PropertyValueFactory<>("select"));

        booksTable.setItems(owner.getBooks());
        booksTable.getColumns().addAll(bookNameColumn, bookPriceColumn, selectColumn);

        vbox.getChildren().addAll(booksTable, buyButton, logoutButton);
        return new Scene(vbox, SCENE_WIDTH, SCENE_HEIGHT);
    }

    private void addBookDialog(Stage primaryStage) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        HBox nameBox = new HBox(10, nameLabel, nameField);

        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField();
        HBox priceBox = new HBox(10, priceLabel, priceField);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            owner.addBooks(name, price);
            dialogStage.close();
        });

        vbox.getChildren().addAll(nameBox, priceBox, addButton);
        Scene scene = new Scene(vbox, 300, 200);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    private void buyDialog(Stage primaryStage) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        Label messageLabel = new Label("Are you sure you want to buy?");
        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {
            // Implement buy logic here
            dialogStage.close();
        });

        vbox.getChildren().addAll(messageLabel, confirmButton);
        Scene scene = new Scene(vbox, 300, 200);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    private GridPane createGridPane() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        return grid;
    }

    private void showAlert(Alert.AlertType type, String content) {
        Alert alert = new Alert(type);
        alert.setContentText(content);
        alert.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
