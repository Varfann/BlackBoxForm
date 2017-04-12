/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackboxform;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

/**
 *
 * @author Varfann
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button fileSelectorButton;
    @FXML
    private Button showButton;
    @FXML
    private Button checkValidationButton;
    
    @FXML
    private void fileSelectorButtonAction(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            //TODO
        } else {
            //TODO
        }
    }
    
    @FXML
    private void showButtonAction(ActionEvent event) {
        /*TODO*/
    }
    
    @FXML
    private void checkValidationButtonAction(ActionEvent event) {
        /*TODO*/
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
