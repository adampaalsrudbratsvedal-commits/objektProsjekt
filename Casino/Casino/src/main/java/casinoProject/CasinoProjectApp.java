package casinoProject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class CasinoProjectApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Starter app...");

        var url = getClass().getResource("/exampleproject/casinoProject/Main-menu.fxml");
        System.out.println("FXML path: " + url);

        if (url == null) {
            System.out.println("FANT IKKE FXML-FILEN! Sjekk at 'Main-menu.fxml' ligger i 'src/main/resources/casinoProject/'");
            return;
        }

        System.out.println("FXML path: " + url);
        System.out.println("Loader prøver å lese FXML...");

        Parent root = FXMLLoader.load(url);
        stage.setTitle("Casino");
        stage.setScene(new Scene(root));
        stage.getScene().getStylesheets().add(getClass().getResource("/exampleproject/casinoProject/styles/Main-menu.css").toExternalForm());

        Image icon = new Image(getClass().getResourceAsStream("/exampleproject/casinoProject/photos/icon.png"));
        stage.getIcons().add(icon);
        stage.setFullScreen(true);

        stage.show();
    }
}