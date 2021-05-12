package edu.chalmers.axen2021.controller.items;

import edu.chalmers.axen2021.controller.FXMLController;
import edu.chalmers.axen2021.controller.RootController;
import edu.chalmers.axen2021.model.managers.ProjectManager;
import edu.chalmers.axen2021.utils.StringUtils;
import edu.chalmers.axen2021.model.projectdata.ApartmentItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the applications lagenhetsDataItem.
 * @author Oscar Arvidson
 * @author Sam Salek
 * @author Malte Åkvist
 */
@FXMLController
public class ApartmentItemController implements Initializable {

    /**
     * Root controller.
     */
    private RootController rootController = RootController.getInstance();

    /**
     * Main root node for this .fxml.
     */
    @FXML private VBox mainVBox;

    /**
     * MenuButton in the .fxml for choosing apartment type.
     */
    @FXML private MenuButton apartmentTypeMenuButton;

    /**
     * TextField in the .fxml for BOA input.
     */
    @FXML private TextField BOATextField;

    /**
     * TextField in the .fxml for amount input.
     */
    @FXML private TextField amountTextField;

    /**
     * TextField in the .fxml for rent/month low.
     */
    @FXML private TextField rentPerMonthLowTextField;

    /**
     * TextField in the .fxml for kr/kvm low.
     */
    @FXML private TextField krPerKvmLowTextField;

    /**
     * TextField in the .fxml for rent/month high.
     */
    @FXML private TextField rentPerMonthHighTextField;

    /**
     * TextField in the .fxml for kr/kvm high.
     */
    @FXML private TextField krPerKvmHighTextField;

    /**
     * TextField in the .fxml for full BOA.
     */
    @FXML private TextField fullBOATextField;

    /**
     * TextField in the .fxml for full BOA percent.
     */
    @FXML private TextField fullBOAPercentTextField;

    /**
     * TextField in the .fxml for bidrag.
     */
    @FXML private TextField bidragTextField;

    /**
     * Apartment for this controller's instance.
     */
    private ApartmentItem apartmentItem;

    /**
     * Constructor for ApartmentItemController.
     * @param apartmentItem Apartment.
     */
    public ApartmentItemController(ApartmentItem apartmentItem) {
        this.apartmentItem = apartmentItem;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBackgroundColor();
        setInitialValues();
        initMenuButtonProperties();
        initBOATextField();
        initAmountTextField();
    }

    /**
     * This method goes back and forth between two different colors for every other ApartmentItem views background color.
     * Reason is to differentiate the different ApartmentItem views when multiple of them are added to the project.
     */
    private void setBackgroundColor() {
        // Gets the index of this apartmentItem in the active project
        int itemIndex = ProjectManager.getInstance().getActiveProject().getApartmentItems().indexOf(apartmentItem);

        // Go back and forth between the background color
        if(itemIndex % 2 == 0) {
            mainVBox.setStyle("-fx-background-color: #f4f4f4");
        } else {
            mainVBox.setStyle("-fx-background-color: #e3e3e3");
        }
    }

    /**
     * Sets the initial values for the .fxml nodes.
     */
    private void setInitialValues() {
        // Set apartmentType to first selectable value in the MenuButton if its null in the model.
        if(apartmentItem.getApartmentType() == null) {
            apartmentItem.setApartmentType(apartmentTypeMenuButton.getItems().get(0).getText());
        }
        apartmentTypeMenuButton.setText(apartmentItem.getApartmentType());
        BOATextField.setText(StringUtils.removeTrailingZeros(apartmentItem.getBOA()));
        amountTextField.setText(String.valueOf(apartmentItem.getAmount()));

        rentPerMonthLowTextField.setText(StringUtils.removeTrailingZeros(apartmentItem.getRentPerMonthLow()));
        krPerKvmLowTextField.setText(StringUtils.removeTrailingZeros(apartmentItem.getRentPerMonthLow()));
        rentPerMonthHighTextField.setText(StringUtils.removeTrailingZeros(apartmentItem.getRentPerMonthHigh()));
        krPerKvmHighTextField.setText(StringUtils.removeTrailingZeros(apartmentItem.getRentPerMonthHigh()));
        fullBOATextField.setText(StringUtils.removeTrailingZeros(apartmentItem.getFullBOA()));
        fullBOAPercentTextField.setText(StringUtils.removeTrailingZeros(apartmentItem.getFullBOAPercent()));
        bidragTextField.setText(StringUtils.removeTrailingZeros(apartmentItem.getBidrag()));
    }

    /**
     * Init method for the MenuButtons properties.
     * Makes it possible to switch between apartment types.
     */
    private void initMenuButtonProperties() {
        for (MenuItem menuItem : apartmentTypeMenuButton.getItems()) {
            menuItem.setOnAction(actionEvent -> {
                apartmentTypeMenuButton.setText(menuItem.getText());
                apartmentItem.setApartmentType(menuItem.getText());
            });
        }
    }

    /**
     * Initializes the BOATextField by adding listeners.
     */
    private void initBOATextField() {
        //Adds property to TextField allowing users to only input numbers and ".".
        BOATextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue.matches("[0-9]*" + "[.]?" + "[0-9]*")){
                BOATextField.setText(oldValue);
            }
        });

        // Adds focus lost property to textFields.
        BOATextField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue){
                //Make sure that the textField has a readable value.
                if(BOATextField.getText().equals("") || BOATextField.getText().equals(".")){
                    BOATextField.setText("0.0");
                }
                // Remove unnecessary zeroes
                BOATextField.setText(StringUtils.removeTrailingZeros(Double.parseDouble(BOATextField.getText())));
                apartmentItem.setBOA(Double.parseDouble(BOATextField.getText()));
                rootController.updateAllLabels();
            }
        });
    }

    /**
     * Initializes the amountTextField by adding listeners.
     */
    private void initAmountTextField() {
        // Adds property to TextField allowing users to only input numbers and.
        amountTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue.matches("\\d*")){
                amountTextField.setText(oldValue);
            }
        });

        // Adds focus lost property to textFields.
        amountTextField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue){
                //Make sure that the textField has a readable value.
                if(amountTextField.getText().equals("")){
                    amountTextField.setText("0");
                }
                // Remove unnecessary zeroes
                amountTextField.setText(StringUtils.removeTrailingZeros(Double.parseDouble(amountTextField.getText())));
                apartmentItem.setAmount(Integer.parseInt(amountTextField.getText()));
                rootController.updateAllLabels();
            }
        });
    }

    /**
     * Opens the confirmation window when removing an apartmentItem.
     */
    @FXML
    private void openRemoveConfirmation(){
        rootController.openConfirmationView(apartmentItem);
    }
}