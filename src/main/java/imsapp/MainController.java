package imsapp;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class for JavaFX MainController of IMS app.
 * FUTURE ENHANCEMENT Modify auto generated IDs to allow reuse of deleted IDs.
 * Move exception warnings to point of entry for each text field.
 * Use filtered lists and predicate lambda.
 * Set sorting of table view based on columns.
 * @author Robin Jiang
 */
public class MainController implements Initializable {

    @FXML
    private TableView<Part> mainFormPartTableView;
    @FXML
    private TableColumn<Part, Integer> partTVColumnPartId;
    @FXML
    private TableColumn<Part, String> partTVColumnName;
    @FXML
    private TableColumn<Part, Integer> partTVColumnInv;
    @FXML
    private TableColumn<Part, Double> partTVColumnPrice;
    @FXML
    private TextField mainFormPartsTextField;
    @FXML
    private Button mainFormPartsAddButton;
    @FXML
    private Button mainFormPartsModifyButton;
    protected static Part selectedPart;
    @FXML
    private TableView<Product> mainFormProductTableView;
    @FXML
    private TableColumn<Product, Integer> productTVColumnProductId;
    @FXML
    private TableColumn<Product, String> productTVColumnName;
    @FXML
    private TableColumn<Product, Integer> productTVColumnInv;
    @FXML
    private TableColumn<Product, Double> productTVColumnPrice;
    @FXML
    private TextField mainFormProductsTextField;
    @FXML
    private Button mainFormProductsAddButton;
    @FXML
    private Button mainFormProductsModifyButton;
    protected static Product selectedProduct;

    /**
     * @param url Initialize url path.
     * @param resourceBundle Initialize resource bundle path.
     * RUNTIME ERROR Did not create override method. Implemented proper initialize override.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTVColumnPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partTVColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partTVColumnInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partTVColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTVColumnPrice.setCellFactory(column -> new TableCell<Part, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", item));
                }
            }
        });
        mainFormPartTableView.setItems(Inventory.getAllParts());

        productTVColumnProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productTVColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productTVColumnInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productTVColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTVColumnPrice.setCellFactory(column -> new TableCell<Product, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", item));
                }
            }
        });
        mainFormProductTableView.setItems(Inventory.getAllProducts());
    }

    /**
     * @param keyEvent Filters parts table view based on text field input.
     * RUNTIME ERROR Method did not work with on input method text changed filter. Changed to on key typed method.
     */
    public void mainFormPartsFilter(KeyEvent keyEvent) throws Exception {
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        try {
            String input = mainFormPartsTextField.getText().trim();
            ObservableList<Part> partNameLookup = Inventory.lookupPart(input);

            if (input.isEmpty()) {
                mainFormPartTableView.setItems(Inventory.getAllParts());
            } else {
                 if (partNameLookup != null) {
                    mainFormPartTableView.setItems(partNameLookup);
                } else if (input.matches("\\d+")) {
                    Part partIdLookup = Inventory.lookupPart(Integer.parseInt(input));
                    if (partIdLookup != null) {
                        filteredParts.add(partIdLookup);
                        mainFormPartTableView.setItems(filteredParts);
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Part Not Found");
                        alert.setContentText("No parts matching the input were found.");
                        alert.showAndWait();
                    }
                }
                 else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Part Not Found");
                    alert.setContentText("No parts matching the input were found.");
                    alert.showAndWait();
                }
            }
        } catch (Exception exception){
            System.out.println("Unknown exception.");
        }
    }

    /**
     * @param actionEvent Change to add part form.
     * RUNTIME ERROR addpartview.fxml not loading, changed to full path in method.
     */
    public void enterAddPartForm (ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imsapp/addpartview.fxml"));
        Scene addPartForm = new Scene(fxmlLoader.load());
        Stage stage = (Stage) mainFormPartsAddButton.getScene().getWindow();
        stage.setScene(addPartForm);
        stage.show();
    }

    /**
     * @param actionEvent Change to modify part form.
     * RUNTIME ERROR addpartview.fxml not loading, changed to full path in method.
     */
    public void enterModifyPartForm(ActionEvent actionEvent) throws Exception{
        try {
            selectedPart = mainFormPartTableView.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imsapp/modifypartview.fxml"));
            Scene modifyPartForm = new Scene(fxmlLoader.load());
            Stage stage = (Stage) mainFormPartsModifyButton.getScene().getWindow();
            stage.setScene(modifyPartForm);
            stage.show();
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No Part selected.");
            alert.showAndWait();
        }
    }

    /**
     * @param actionEvent Delete selected part.
     * RUNTIME ERROR No confirmation prompt to prevent accidental deletion. Added prompt for confirmation.
     */
    public void mainFormPartDelete(ActionEvent actionEvent) {
        Part selectedPart = mainFormPartTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Part");
            alert.setContentText("Are you sure you want to delete the selected part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean isDeleted = Inventory.deletePart(selectedPart);
                if (isDeleted) {
                    mainFormPartTableView.getItems().remove(selectedPart);
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Deletion Failed");
                    errorAlert.setContentText("Cannot delete a part associated with a product.");
                    errorAlert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Part Selected");
            alert.setContentText("Please select a part to delete.");
            alert.showAndWait();
        }
    }

    /**
     * @param keyEvent Filters product table view based on text field input.
     * RUNTIME ERROR Method did not work with on input method text changed filter. Changed to on key typed method.
     */
    public void mainFormProductsFilter(KeyEvent keyEvent) throws Exception {
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        try {
            String input = mainFormProductsTextField.getText().trim();
            ObservableList<Product> productNameLookup = Inventory.lookupProduct(input);

            if (input.isEmpty()) {
                mainFormProductTableView.setItems(Inventory.getAllProducts());
            } else {
                if (productNameLookup != null) {
                    mainFormProductTableView.setItems(productNameLookup);
                } else if (input.matches("\\d+")) {
                    Product productIdLookup = Inventory.lookupProduct(Integer.parseInt(input));
                    if (productIdLookup != null) {
                        filteredProducts.add(productIdLookup);
                        mainFormProductTableView.setItems(filteredProducts);
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Product Not Found");
                        alert.setContentText("No products matching the input were found.");
                        alert.showAndWait();
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Product Not Found");
                    alert.setContentText("No products matching the input were found.");
                    alert.showAndWait();
                }
            }
        } catch (Exception exception){
            System.out.println("Unknown exception.");
        }
    }

    /**
     * @param actionEvent Change to add product form.
     * RUNTIME ERROR addproductview.fxml not loading, changed to full path in method.
     */
    public void enterAddProductForm(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imsapp/addproductview.fxml"));
        Scene addProductForm = new Scene(fxmlLoader.load());
        Stage stage = (Stage) mainFormProductsAddButton.getScene().getWindow();
        stage.setScene(addProductForm);
        stage.show();
    }

    /**
     * @param actionEvent Change to modify product form.
     * RUNTIME ERROR modifyproductview.fxml not loading, changed to full path in method.
     */
    public void enterModifyProductForm(ActionEvent actionEvent) throws Exception{
        try {
            selectedProduct = mainFormProductTableView.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imsapp/modifyproductview.fxml"));
            Scene modifyProductForm = new Scene(fxmlLoader.load());
            Stage stage = (Stage) mainFormProductsModifyButton.getScene().getWindow();
            stage.setScene(modifyProductForm);
            stage.show();
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No Product selected.");
            alert.showAndWait();
        }
    }

    /**
     * @param actionEvent Delete selected part.
     * RUNTIME ERROR No confirmation prompt to prevent accidental deletion. Added prompt for confirmation.
     */
    public void mainFormProductDelete(ActionEvent actionEvent) {
        Product selectedProduct = mainFormProductTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Product");
            alert.setContentText("Are you sure you want to delete the selected product?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean isDeleted = Inventory.deleteProduct(selectedProduct);
                if (isDeleted) {
                    mainFormProductTableView.getItems().remove(selectedProduct);
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Deletion Failed");
                    errorAlert.setContentText("Cannot delete a product with associated parts.");
                    errorAlert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Product Selected");
            alert.setContentText("Please select a product to delete.");
            alert.showAndWait();
        }
    }

    /**
     * @param actionEvent Close JavaFx application.
     * RUNTIME ERROR Application did not close all threads properly. Changed from System.exit() to Platform.exit() as recommended in Javadocs.
     */
    public void onExit(ActionEvent actionEvent) { Platform.exit(); }

}