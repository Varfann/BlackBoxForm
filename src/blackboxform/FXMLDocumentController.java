/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackboxform;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.collections.FXCollections; 
import javafx.collections.ObservableList; 
import javafx.scene.control.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

import com.blackbox.starter.util.blockchain.ChainDetecter;
import com.blackbox.starter.models.EventBlock;
import com.blackbox.starter.events.CarEvent;
import java.util.ArrayList;
import java.util.Set;
import javafx.collections.FXCollections;


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
    private ListView listViewMain;
    @FXML
    private CheckBox showAllCheckBox;
    @FXML
    private TextField fromValueField;
    @FXML
    private TextField toValueField;
    
    private ChainDetecter chainDetecter;
    
    private File publicKey;
    
    
    @FXML
    private void fileSelectorButtonAction(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            publicKey = selectedFile;
        } 
    }
    
    @FXML
    private void showButtonAction(ActionEvent event) {
        int startPosition = Integer.parseInt(fromValueField.getText()) > -1 ? Integer.parseInt(fromValueField.getText()) : 0;
        int endPosition = Integer.parseInt(toValueField.getText()) < 100 ? Integer.parseInt(toValueField.getText()) : 99;

        try {
            List<EventBlock> eventBlockChain = chainDetecter.getListFromFile(startPosition,endPosition);
            ObservableList data = FXCollections.observableArrayList();
            
            List<CarEvent> carEventList = new ArrayList<>();
            
            if (showAllCheckBox.isSelected()) {
                carEventList = chainDetecter.getEventList(eventBlockChain);              
            } else {                
                //TreeSet<Class> classList = new TreeSet<Class>();
                carEventList = chainDetecter.getEventList(eventBlockChain);
            }
            
            for (CarEvent eventElement: carEventList) {
                data.add(eventElement.toString());
            }
            listViewMain.setItems(data);
            
            
        } catch(Exception e){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Black box Form");
            alert.setHeaderText("Empty quary result");
            alert.setContentText(e.getMessage());
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {           
                }
            });
        }
    }
    
    @FXML
    private void checkValidationButtonAction(ActionEvent event) {
        if (publicKey == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Black box Form");
            alert.setHeaderText("Empty public key");
            alert.setContentText("Please, add public key first");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {           
                }
            });            
        } else {
            int startPosition = Integer.parseInt(fromValueField.getText()) > -1 ? Integer.parseInt(fromValueField.getText()) : 0;
            int endPosition = Integer.parseInt(toValueField.getText()) < 100 ? Integer.parseInt(toValueField.getText()) : 99;
            try {
                List<EventBlock> eventBlockChain = chainDetecter.getListFromFile(startPosition,endPosition);
                boolean isChainCorrect = chainDetecter.isChainCorrect(eventBlockChain);
                String message = "";
                if (isChainCorrect) {
                    message = "Chain is correct!";
                } else {
                    message = "Chain is not correct!";
                }
            } catch(Exception e){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Black box Form");
                alert.setHeaderText("Empty quary result");
                alert.setContentText(e.getMessage());
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {           
                    }
                });
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chainDetecter = new ChainDetecter();
    }    
    
}
