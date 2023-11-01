package imsapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static imsapp.Application.mainFormScene;
import static imsapp.Application.mainFormStage;

/**
 *Class for JavaFX ModifyProductForm controller.
 * FUTURE ENHANCEMENT Create separate controller classes for each form.
 * Modify auto generated IDs to allow reuse of deleted IDs.
 * Move exception warnings to point of entry for each text field.
 * @author Robin Jiang
 */
public class ModifyProductController implements Initializable {

    @FXML
    private TextField modifyProductIdTextField;
    @FXML
    private TextField modifyProductNameTextField;
    @FXML
    private TextField modifyProductInventoryTextField;
    @FXML
    private TextField modifyProductMaxTextField;
    @FXML
    private TextField modifyProductMinTextField;
    @FXML
    private TextField modifyProductPriceTextField;
    @FXML
    private TextField modifyProductFormTextField;
    @FXML
    private TableView<Part> modifyProductFormPoolTableView;
    @FXML
    private TableColumn<Product, Integer> modifyProductPoolTVColumnId;
    @FXML
    private TableColumn<Product, String> modifyProductPoolTVColumnName;
    @FXML
    private TableColumn<Product, Integer> modifyProductPoolTVColumnInv;
    @FXML
    private TableColumn<Product, Double> modifyProductPoolTVColumnPrice;
    @FXML
    private TableView<Part> modifyProductFormSelectTableView;
    @FXML
    private TableColumn<Product, Integer> modifyProductSelectTVColumnId;
    @FXML
    private TableColumn<Product, String> modifyProductSelectTVColumnName;
    @FXML
    private TableColumn<Product, Integer> modifyProductSelectTVColumnInv;
    @FXML
    private TableColumn<Product, Double> modifyProductSelectTVColumnPrice;
    private ObservableList<Part> selectedParts = MainController.selectedProduct.getAllAssociatedParts();

    /**
     * @param url Initialize url path.
     * @param resourceBundle Initialize resource bundle path.
     * RUNTIME ERROR Did not create override method. Implemented proper initialize override.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyProductPoolTVColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPoolTVColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductPoolTVColumnInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPoolTVColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyProductPoolTVColumnPrice.setCellFactory(column -> new TableCell<Product, Double>() {
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
        modifyProductFormPoolTableView.setItems(Inventory.getAllParts());

        modifyProductSelectTVColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductSelectTVColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductSelectTVColumnInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductSelectTVColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyProductSelectTVColumnPrice.setCellFactory(column -> new TableCell<Product, Double>() {
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
        modifyProductFormSelectTableView.setItems(selectedParts);

        Product Product = MainController.selectedProduct;
        modifyProductIdTextField.setText(Integer.toString(Product.getId()));
        modifyProductNameTextField.setText(Product.getName());
        modifyProductInventoryTextField.setText(Integer.toString(Product.getStock()));
        modifyProductMaxTextField.setText(Integer.toString(Product.getMax()));
        modifyProductMinTextField.setText(Integer.toString(Product.getMin()));
        modifyProductPriceTextField.setText(Double.toString(Product.getPrice()));
    }

    /**
     * @param keyEvent Filters Products table view based on text field input.
     * RUNTIME ERROR Method did not work with on input method text changed filter. Changed to on key typed method.
     */
    public void modifyProductFormFilter(KeyEvent keyEvent) throws Exception {
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        try {
            String input = modifyProductFormTextField.getText().trim();
            ObservableList<Part> PartNameLookup = Inventory.lookupPart(input);
            if (input.isEmpty()) {
                modifyProductFormPoolTableView.setItems(Inventory.getAllParts());
            } else {
                if (PartNameLookup != null) {
                    modifyProductFormPoolTableView.setItems(PartNameLookup);
                } else if (input.matches("\\d+")) {
                    Part PartIdLookup = Inventory.lookupPart(Integer.parseInt(input));
                    if (PartIdLookup != null) {
                        filteredParts.add(PartIdLookup);
                        modifyProductFormPoolTableView.setItems(filteredParts);
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Part Not Found");
                        alert.setContentText("No Parts matching the input were found.");
                        alert.showAndWait();
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Part Not Found");
                    alert.setContentText("No Parts matching the input were found.");
                    alert.showAndWait();
                }
            }
        } catch (Exception exception){
            System.out.println("Unknown exception.");
        }
    }

    /**
     * @param actionEvent Add selected pool Part to selected Part tableview.
     * RUNTIME ERROR selectedParts not properly initialized, initialized with FXCollections.observableArrayList().
     */
    public void modifyProductFormAddPart(ActionEvent actionEvent) {
        Part Part = modifyProductFormPoolTableView.getSelectionModel().getSelectedItem();
        if (selectedParts.contains(Part)) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Duplicate");
            errorAlert.setContentText("Part has already been added.");
            errorAlert.showAndWait();
        } else {
            selectedParts.add(Part);
        }
    }

    /**
     * @param actionEvent Remove selected Part from selectedParts list.
     * RUNTIME ERROR selectedParts not properly initialized, initialized with FXCollections.observableArrayList().
     */
    public void modifyProductFormRemovePart(ActionEvent actionEvent) {
        Part part = modifyProductFormSelectTableView.getSelectionModel().getSelectedItem();
        selectedParts.remove(part);
    }

    /**
     * @param actionEvent Saves new product.
     * RUNTIME ERROR Input exceptions not shown to end users. Added warning prompts for each exception.
     */
    public void modifyProductSave(ActionEvent actionEvent) {
        String inv = modifyProductInventoryTextField.getText();
        String price = modifyProductPriceTextField.getText();
        String max = modifyProductMaxTextField.getText();
        String min = modifyProductMinTextField.getText();
        String name = modifyProductNameTextField.getText();
        String id = modifyProductIdTextField.getText();
        int productIndex = Inventory.allProducts.indexOf(MainController.selectedProduct);
        ObservableList <Part> selectedParts = modifyProductFormSelectTableView.getItems();
        try {
            if (!(inv.matches("\\d+"))) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Enter a non negative integer value for the Inventory.");
                alert.showAndWait();
            } else if (!(price.matches("\\d+(\\.\\d{2})?"))) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Enter a non negative two decimal number for Price/Cost.");
                alert.showAndWait();
            } else if (!max.matches("\\d+")) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Enter a non negative integer value for the Inventory max.");
                alert.showAndWait();
            } else if (!min.matches("\\d+")) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Enter a non negative integer value for the Inventory min.");
                alert.showAndWait();
            } else if (Integer.parseInt(max) < Integer.parseInt(min)) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Inventory max can not be lower than min.");
                alert.showAndWait();
            } else if (Integer.parseInt(inv) < Integer.parseInt(min) || Integer.parseInt(inv) > Integer.parseInt(max)) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Enter a value for Inventory within the range of Min and Max.");
                alert.showAndWait();
            } else if (inv.isEmpty() || price.isEmpty() || max.isEmpty() || min.isEmpty() || name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Enter a value for all fields.");
                alert.showAndWait();
            } else {
                Product product = new Product(Integer.parseInt(id), name, Double.parseDouble(price), Integer.parseInt(inv),
                        Integer.parseInt(min), Integer.parseInt(max));
                for (Part part: selectedParts) {
                    product.addAssociatedPart(part);
                }
                Inventory.updateProduct(productIndex, product);
                enterMainForm(actionEvent);
            }
        } catch (Exception exception) {
            System.out.println("Unknown Exception.");
        }
    }

    /**
     * @param actionEvent sends back to mainForm.
     * RUNTIME ERROR Null reference to stage and scene objects. Created a static reference to main stage and scene.
     */
    public void enterMainForm(ActionEvent actionEvent) throws Exception {
        Stage stage = mainFormStage;
        stage.setScene(mainFormScene);
        stage.show();
    }

    /**
     * @param actionEvent Sends back to main form without modifying product.
     * RUNTIME ERROR Exit method didn't work. actionEvent was missing so entered as arg.
     */
    public void modifyProductCancel(ActionEvent actionEvent) throws Exception { enterMainForm(actionEvent); }

}


