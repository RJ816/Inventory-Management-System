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
import static java.lang.System.*;


/**
 *Class for JavaFX modifyPartForm controller.
 * FUTURE ENHANCEMENT Create separate controller classes for each form.
 * Modify auto generated IDs to allow reuse of deleted IDs.
 * Move exception warnings to point of entry for each text field.
 * @author Robin Jiang
 */
public class ModifyPartController implements Initializable {

    @FXML
    private TextField modifyPartIdTextField;
    @FXML
    private Label modifyPartPolyLabel;
    @FXML
    private TextField modifyPartNameTextField;
    @FXML
    private TextField modifyPartInventoryTextField;
    @FXML
    private TextField modifyPartMaxTextField;
    @FXML
    private TextField modifyPartMinTextField;
    @FXML
    private TextField modifyPartPriceTextField;
    @FXML
    private TextField modifyPartPolyTextField;
    @FXML
    private RadioButton modifyPartInHouseRadial;
    @FXML
    private RadioButton modifyPartOutsourcedRadial;

    /**
     * @param url Initialize url path.
     * @param resourceBundle Initialize resource bundle path.
     * RUNTIME ERROR Did not create override method. Implemented proper initialize override.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Part part = MainController.selectedPart;
        modifyPartIdTextField.setText(Integer.toString(part.getId()));
        modifyPartNameTextField.setText(part.getName());
        modifyPartInventoryTextField.setText(Integer.toString(part.getStock()));
        modifyPartMaxTextField.setText(Integer.toString(part.getMax()));
        modifyPartMinTextField.setText(Integer.toString(part.getMin()));
        modifyPartPriceTextField.setText(Double.toString(part.getPrice()));
        if (part instanceof InHouse) {
            modifyPartInHouseRadial.setSelected(true);
            InHouse inHousePart = (InHouse) part;
            modifyPartPolyTextField.setText(Integer.toString(inHousePart.getMachineId()));
        } else if ((part instanceof Outsourced)) {
            modifyPartOutsourcedRadial.setSelected(true);
            Outsourced outsourcedPart = (Outsourced) part;
            modifyPartPolyTextField.setText(outsourcedPart.getCompanyName());
        }
    }

    /**
     * @param actionEvent Change in house class specific label and text field.
     * RUNTIME ERROR Radial buttons could be deselected and not mutually exclusive. Added radial buttons in a toggle group.
     */
    public void modifyPartInHouseSelected(ActionEvent actionEvent) { modifyPartPolyLabel.setText("Machine ID"); }

    /**
     * @param actionEvent Change outsourced class specific label and text field.
     * RUNTIME ERROR Radial buttons could be deselected and not mutually exclusive. Added radial buttons in a toggle group.
     */
    public void modifyPartOutsourcedSelected(ActionEvent actionEvent) { modifyPartPolyLabel.setText("Company Name"); }

    /**
     * @param actionEvent Saves updated part.
     * RUNTIME ERROR Input exceptions not shown to end users. Added warning prompts for each exception.
     */
    public void modifyPartSave(ActionEvent actionEvent) throws Exception{
        String inv = modifyPartInventoryTextField.getText();
        String price = modifyPartPriceTextField.getText();
        String max = modifyPartMaxTextField.getText();
        String min = modifyPartMinTextField.getText();
        String name = modifyPartNameTextField.getText();
        String poly = modifyPartPolyTextField.getText();
        String id = modifyPartIdTextField.getText();
        int partIndex = Inventory.allParts.indexOf(MainController.selectedPart);
        Part part = MainController.selectedPart;
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
            } else if (modifyPartInHouseRadial.isSelected() && !(poly.matches("\\d+"))) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Enter a non negative integer value for Machine ID.");
                alert.showAndWait();
            } else if (inv.isEmpty() || price.isEmpty() || max.isEmpty() || min.isEmpty() || name.isEmpty() || poly.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Enter a value for all fields.");
                alert.showAndWait();
            } else if (modifyPartInHouseRadial.isSelected()) {
                if (part instanceof InHouse) {
                    part.setId(Integer.parseInt(id));
                    part.setName(name);
                    part.setPrice(Double.parseDouble(price));
                    part.setStock(Integer.parseInt(inv));
                    part.setMin(Integer.parseInt(min));
                    part.setMax(Integer.parseInt(max));
                    ((InHouse) part).setMachineId(Integer.parseInt(poly));
                    Inventory.updatePart(partIndex, part);
                    enterMainForm(actionEvent);
                } else {
                    InHouse inHousePart = new InHouse(Integer.parseInt(id), name, Double.parseDouble(price), Integer.parseInt(inv),
                            Integer.parseInt(min), Integer.parseInt(max), Integer.parseInt(poly));
                    Inventory.updatePart(partIndex, inHousePart);
                    enterMainForm(actionEvent);
                }

            } else if (modifyPartOutsourcedRadial.isSelected()) {
                if (part instanceof Outsourced) {
                    part.setId(Integer.parseInt(id));
                    part.setName(name);
                    part.setPrice(Double.parseDouble(price));
                    part.setStock(Integer.parseInt(inv));
                    part.setMin(Integer.parseInt(min));
                    part.setMax(Integer.parseInt(max));
                    ((Outsourced) part).setCompanyName(poly);
                    Inventory.updatePart(partIndex, part);
                    enterMainForm(actionEvent);
                } else {
                    Outsourced outsourcedPart = new Outsourced(Integer.parseInt(id), name, Double.parseDouble(price), Integer.parseInt(inv),
                            Integer.parseInt(min), Integer.parseInt(max), poly);
                    Inventory.updatePart(partIndex, outsourcedPart);
                    enterMainForm(actionEvent);
                }

            }
        } catch (Exception exception) {
            System.out.println("Unknown exception.");
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
     * @param actionEvent Sends back to main form without modifying part.
     * RUNTIME ERROR Exit method didn't work. actionEvent was missing so entered as arg.
     */
    public void modifyPartCancel(ActionEvent actionEvent) throws Exception {
        enterMainForm (actionEvent);
    }
}


