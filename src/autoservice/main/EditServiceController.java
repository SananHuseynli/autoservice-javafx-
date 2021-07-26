package autoservice.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class EditServiceController implements Initializable {

    @FXML
    private AnchorPane editServicePane;

    @FXML
    private Label editServiceLbl;

    @FXML
    private TextField editServiceTxt;

    @FXML
    private Label editServicePriceLbl;

    @FXML
    private TextField editPriceField;

    @FXML
    private Label editServiceAznLbl;

    @FXML
    private Button editServiceSaveBtn;

    @FXML
    private Button editServiceClearBtn;

    @FXML
    void editServiceClearact(ActionEvent event) {

    }

    @FXML
    void editServiceSaveAct(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
