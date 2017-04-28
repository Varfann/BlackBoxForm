/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackboxform;

import com.blackbox.starter.events.*;
import com.blackbox.starter.models.EventBlock;
import com.blackbox.starter.util.blockchain.ChainDetecter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Varfann
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    public CheckBox repaireCheck;
    @FXML
    public CheckBox stopCheck;
    @FXML
    public CheckBox startCheck;
    @FXML
    public CheckBox accidentCheck;
    @FXML
    public CheckBox breakageCheck;
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
    private List<EventBlock> eventBlockChain = new ArrayList<>();


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
        int startPosition = -1;
        int endPosition = -1;
        if (!fromValueField.getText().isEmpty() && !toValueField.getText().isEmpty() && showAllCheckBox.isSelected()) {
            startPosition = (Integer.parseInt(fromValueField.getText()) > -1) ? Integer.parseInt(fromValueField.getText()) : 0;
            endPosition = (Integer.parseInt(toValueField.getText()) < 100) ? Integer.parseInt(toValueField.getText()) : 99;
        }

        try {
            eventBlockChain = chainDetecter.getListFromFile(startPosition, endPosition);
            ObservableList data = FXCollections.observableArrayList();

            List<CarEvent> carEventList = chainDetecter.getEventList(eventBlockChain);
            Set<Class> selectedTypeSet = new HashSet<>();
            if (repaireCheck.isSelected()) selectedTypeSet.add(CarRepairEvent.class);
            if (startCheck.isSelected()) selectedTypeSet.add(CarStartEvent.class);
            if (stopCheck.isSelected()) selectedTypeSet.add(CarStopEvent.class);
            if (accidentCheck.isSelected()) selectedTypeSet.add(CarAccidentEvent.class);
            if (breakageCheck.isSelected()) selectedTypeSet.add(CarBreakingEvent.class);
            data.addAll(chainDetecter.getEventWithType(carEventList, selectedTypeSet).stream()
                    .map(CarEvent::toString).collect(Collectors.toList()));
            listViewMain.setItems(data);


        } catch (Exception e) {
            alert(e.getMessage());
        }
    }

    @FXML
    private void checkValidationButtonAction(ActionEvent event) {
        if (eventBlockChain.isEmpty()) {
            alert("List is empty. \nPlease, click to 'show' for load list.");
            return;
        }
        if (publicKey == null) {
            alert("Empty public key \nPlease, add public key first");
        } else try {
            boolean isChainCorrect = chainDetecter.isChainCorrect(eventBlockChain);
            String message = isChainCorrect ? "Chain is correct!" : "Chain is not correct!";
            alert(message);
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }


    private void alert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Black box Form");
        //alert.setHeaderText("Empty quary result");
        alert.setHeaderText(message);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        chainDetecter = new ChainDetecter();
        publicKey = new File("public.key");
    }

}
