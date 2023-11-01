package imsapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class for JavaFX application of IMS app.
 * FUTURE ENHANCEMENT App loads persistent data. Log exceptions in Log4j or SLF4J when app goes into production.
 * Implement persistent data.
 * @author Robin Jiang
 */
public class Application extends javafx.application.Application {

    protected static Stage mainFormStage;
    protected static Scene mainFormScene;

    /**
     * @param stage First JavaFX window to set.
     * RUNTIME ERROR Did not create override method. Implemented proper start override.
     */
    @Override
    public void start (Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/imsapp/mainview.fxml"));
        Scene mainForm = new Scene(fxmlLoader.load()); //TODO MainController initialized
        stage.setTitle("Inventory Management System");
        stage.setScene(mainForm);
        stage.show();
        mainFormStage = stage;
        mainFormScene = mainForm;
    }

    /**
     * @param args Initialize main method.
     * RUNTIME ERROR Did not call launch method to start JavaFX app.
     * Javadocs in \InventoryManagementSystem\src\main\java
     */
    public static void main(String[] args) {
        launch();
    }

}