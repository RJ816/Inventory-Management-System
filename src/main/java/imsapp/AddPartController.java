package imsapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
public class AddPartController implements Initializable {

    @FXML
    private TextField addPartIdTextField;
    @FXML
    private Label addPartPolyLabel;
    @FXML
    private TextField addPartInventoryTextField;
    @FXML
    private TextField addPartPriceTextField;
    @FXML
    private TextField addPartMaxTextField;
    @FXML
    private TextField addPartMinTextField;
    @FXML
    private TextField addPartNameTextField;
    @FXML
    private TextField addPartPolyTextField;
    @FXML
    private RadioButton addPartInHouseRadial;
    @FXML
    private RadioButton addPartOutsourcedRadial;

    /**
     * @param url Initialize url path.
     * @param resourceBundle Initialize resource bundle path.
     * RUNTIME ERROR Did not create override method. Implemented proper initialize override.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {addPartSetIdAuto();}

    /**
     * @param actionEvent Change in house class specific label and text field.
     * RUNTIME ERROR Radial buttons could be deselected and not mutually exclusive. Added radial buttons in a toggle group.
     */
    public void addPartInHouseSelected(ActionEvent actionEvent) {
        addPartPolyLabel.setText("Machine ID");
    }

    /**
     * @param actionEvent Change outsourced class specific label and text field.
     * RUNTIME ERROR Radial buttons could be deselected and not mutually exclusive. Added radial buttons in a toggle group.
     */
    public void addPartOutsourcedSelected(ActionEvent actionEvent) {
        addPartPolyLabel.setText("Company Name");
    }

    /**
     * Create a Part ID based on allParts list
     * RUNTIME ERROR Radial buttons could be deselected and not mutually exclusive. Added radial buttons in a toggle group.
     */
    public void addPartSetIdAuto() {
        int partIdMax = 0;
        if (Inventory.allParts.isEmpty()) {
            addPartIdTextField.setText("1");
        }
        else {
            for (Part part: Inventory.allParts) {
                if (part.getId() > partIdMax) {
                    partIdMax = part.getId();
                }
            }
            partIdMax++;
            addPartIdTextField.setText(String.valueOf(partIdMax));
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
     * @param actionEvent Saves new part.
     * RUNTIME ERROR Input exceptions not shown to end users. Added warning prompts for each exception.
     */
    public void addPartSave(ActionEvent actionEvent) throws Exception {
        String inv = addPartInventoryTextField.getText();
        String price = addPartPriceTextField.getText();
        String max = addPartMaxTextField.getText();
        String min = addPartMinTextField.getText();
        String name = addPartNameTextField.getText();
        String poly = addPartPolyTextField.getText();
        String id = addPartIdTextField.getText();

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
            } else if (addPartInHouseRadial.isSelected() && !(poly.matches("\\d+"))) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Enter a non negative integer value for Machine ID.");
                alert.showAndWait();
            } else if (inv.isEmpty() || price.isEmpty() || max.isEmpty() || min.isEmpty() || name.isEmpty() || poly.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Enter a value for all fields.");
                alert.showAndWait();
            } else if (addPartInHouseRadial.isSelected()) {
                InHouse inHousePart = new InHouse(Integer.parseInt(id), name, Double.parseDouble(price), Integer.parseInt(inv),
                        Integer.parseInt(min), Integer.parseInt(max), Integer.parseInt(poly));
                Inventory.addPart(inHousePart);
                enterMainForm(actionEvent);
            } else if (addPartOutsourcedRadial.isSelected()) {
                Outsourced outsourcedPart = new Outsourced(Integer.parseInt(id), name, Double.parseDouble(price), Integer.parseInt(inv),
                        Integer.parseInt(min), Integer.parseInt(max), poly);
                Inventory.addPart(outsourcedPart);
                enterMainForm(actionEvent);
            }
        } catch (Exception exception) {
            System.out.println("Unknown Exception.");
        }
    }

    /**
     * @param actionEvent Sends back to main form without adding part.
     * RUNTIME ERROR Exit method didn't work. actionEvent was missing so entered as arg.
     */
    public void addPartCancel(ActionEvent actionEvent) throws Exception { enterMainForm(actionEvent); }

}

