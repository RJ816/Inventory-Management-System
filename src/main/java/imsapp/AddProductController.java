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
 *Class for JavaFX addPartForm controller.
 * FUTURE ENHANCEMENT Create separate controller classes for each form.
 * Modify auto generated IDs to allow reuse of deleted IDs.
 * Move exception warnings to point of entry for each text field.
 * @author Robin Jiang
 */
public class AddProductController implements Initializable {

    @FXML
    private TextField addProductIdTextField;
    @FXML
    private TextField addProductNameTextField;
    @FXML
    private TextField addProductInventoryTextField;
    @FXML
    private TextField addProductMaxTextField;
    @FXML
    private TextField addProductMinTextField;
    @FXML
    private TextField addProductPriceTextField;
    @FXML
    private TextField addProductFormTextField;
    @FXML
    private TableView<Part> addProductFormPoolTableView;
    @FXML
    private TableColumn<Part, Integer> addProductPoolTVColumnId;
    @FXML
    private TableColumn<Part, String> addProductPoolTVColumnName;
    @FXML
    private TableColumn<Part, Integer> addProductPoolTVColumnInv;
    @FXML
    private TableColumn<Part, Double> addProductPoolTVColumnPrice;
    @FXML
    private TableView<Part> addProductFormSelectTableView;
    @FXML
    private TableColumn<Part, Integer> addProductSelectTVColumnId;
    @FXML
    private TableColumn<Part, String> addProductSelectTVColumnName;
    @FXML
    private TableColumn<Part, Integer> addProductSelectTVColumnInv;
    @FXML
    private TableColumn<Part, Double> addProductSelectTVColumnPrice;
    private ObservableList<Part> selectedParts = FXCollections.observableArrayList();

    /**
     * @param url Initialize url path.
     * @param resourceBundle Initialize resource bundle path.
     * RUNTIME ERROR Did not create override method. Implemented proper initialize override.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductSetIdAuto();

        addProductPoolTVColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductPoolTVColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductPoolTVColumnInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPoolTVColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProductPoolTVColumnPrice.setCellFactory(column -> new TableCell<Part, Double>() {
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
        addProductFormPoolTableView.setItems(Inventory.getAllParts());

        addProductSelectTVColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductSelectTVColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductSelectTVColumnInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductSelectTVColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProductSelectTVColumnPrice.setCellFactory(column -> new TableCell<Part, Double>() {
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
        addProductFormSelectTableView.setItems(selectedParts);
    }

    /**
     * Create a Product ID based on allProducts list
     * RUNTIME ERROR No conditional to check if ID greater than 0. Added counter to find next highest number and set as ID.
     */
    public void addProductSetIdAuto() {
        int productIdMax = 0;
        if (Inventory.allProducts.isEmpty()) {
            addProductIdTextField.setText("1");
        }
        else {
            for (Product product: Inventory.allProducts) {
                if (product.getId() > productIdMax) {
                    productIdMax = product.getId();
                }
            }
            productIdMax++;
            addProductIdTextField.setText(String.valueOf(productIdMax));
        }
    }

    /**
     * @param keyEvent Filters parts table view based on text field input.
     * RUNTIME ERROR Method did not work with on input method text changed filter. Changed to on key typed method.
     */
    public void addProductFormFilter(KeyEvent keyEvent) throws Exception {
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        try {
            String input = addProductFormTextField.getText().trim();
            ObservableList<Part> partNameLookup = Inventory.lookupPart(input);
            if (input.isEmpty()) {
                addProductFormPoolTableView.setItems(Inventory.getAllParts());
            } else {
                if (partNameLookup != null) {
                    addProductFormPoolTableView.setItems(partNameLookup);
                } else if (input.matches("\\d+")) {
                    Part partIdLookup = Inventory.lookupPart(Integer.parseInt(input));
                    if (partIdLookup != null) {
                        filteredParts.add(partIdLookup);
                        addProductFormPoolTableView.setItems(filteredParts);
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
     * @param actionEvent Add selected pool part to selected part tableview.
     * RUNTIME ERROR selectedParts not properly initialized, initialized with FXCollections.observableArrayList().
     */
    public void addProductFormAddPart(ActionEvent actionEvent) {
            Part part = addProductFormPoolTableView.getSelectionModel().getSelectedItem();
            if (selectedParts.contains(part)) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Duplicate");
                errorAlert.setContentText("Part has already been added.");
                errorAlert.showAndWait();
            } else {
                selectedParts.add(part);
            }
    }

    /**
     * @param actionEvent Remove selected part from selectedParts list.
     * RUNTIME ERROR selectedParts not properly initialized, initialized with FXCollections.observableArrayList().
     */
    public void addProductFormRemovePart(ActionEvent actionEvent) {
        Part part = addProductFormSelectTableView.getSelectionModel().getSelectedItem();
        selectedParts.remove(part);
    }

    /**
     * @param actionEvent Saves new product.
     * RUNTIME ERROR Input exceptions not shown to end users. Added warning prompts for each exception.
     */
    public void addProductSave(ActionEvent actionEvent) {
        String inv = addProductInventoryTextField.getText();
        String price = addProductPriceTextField.getText();
        String max = addProductMaxTextField.getText();
        String min = addProductMinTextField.getText();
        String name = addProductNameTextField.getText();
        String id = addProductIdTextField.getText();

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
                Inventory.addProduct(product);
                for (Part part: selectedParts) {
                    product.addAssociatedPart(part);
                }
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
     * @param actionEvent Sends back to main form without adding product.
     * RUNTIME ERROR Exit method didn't work. actionEvent was missing so entered as arg.
     */
    public void addProductCancel(ActionEvent actionEvent) throws Exception { enterMainForm(actionEvent); }

}


