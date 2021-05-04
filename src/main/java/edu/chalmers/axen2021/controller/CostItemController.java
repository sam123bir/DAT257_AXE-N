package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.model.CostItem;
import edu.chalmers.axen2021.utils.StringUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

/**
 * Controller class for the applications modalWindowItem.fxml.
 * Handles all event triggered in the modalWindowItem (cost item).
 * @author Sam Salek
 * @author Oscar Arvidson
 */
@FXMLController
public class CostItemController implements Initializable {

    /**
     * The name label in the .fxml file.
     */
    @FXML private Label nameLabel;

    /**
     * TextField representing the value of the cost.
     */
    @FXML private TextField valueTextField;

    @FXML private TextArea commentTextArea;

    /**
     * The cost item.
     */
    private CostItem costItem;

    public CostItemController(CostItem costItem) {
        this.costItem = costItem;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLabel.setText(costItem.getName());

        valueTextField.setText(StringUtils.removeTrailingZeros(costItem.getValue()));

        commentTextArea.setText(costItem.getComment());
        initTextFieldProperties();
    }

    /**
     * Init method for input TextFields properties.
     * Adds focus lost property.
     * Adds only allowing doubles property.
     */
    private void initTextFieldProperties(){

        //Adds property to TextField allowing users to only input numbers and ".".
        valueTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue.matches("[0-9]*" + "[.]?" + "[0-9]*")){
                valueTextField.setText(oldValue);
            }
        });

        //Adds focus lost property to textFields.
        valueTextField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue){
                //Make sure that the textField has a readable value.
                if(valueTextField.getText().equals("") || valueTextField.getText().equals(".")){
                    valueTextField.setText("0.0");
                }

                costItem.setValue(Double.parseDouble(valueTextField.getText()));
            }
        });

        commentTextArea.focusedProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(!newValue){
                if(commentTextArea.getText() == null ) {
                    commentTextArea.setText("");
                }

                costItem.setComment(commentTextArea.getText());
            }
        }));
    }
}
