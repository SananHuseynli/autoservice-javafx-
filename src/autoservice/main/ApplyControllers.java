package autoservice.main;

import autoservice.model.*;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ApplyControllers implements Initializable {
    @FXML
    private AnchorPane newServicePane;

    @FXML
    private Label newService_customerLbl;

    @FXML
    private Label newService_carLbl;

    @FXML
    private Label newService_modelLbl;

    @FXML
    private ComboBox<Customer> customerCombo;

    @FXML
    private Label newService_serviceLbl;

    @FXML
    private ComboBox<Service> serviceCombo;

    @FXML
    private Label newService_carNameLbl;

    @FXML
    private Label newService_modelNameLbl;

    @FXML
    private Button newService_saveBtn;

    @FXML
    void customerAct(ActionEvent event) {


        }



    @FXML
    void serviceAction(ActionEvent event) {

    }

    @FXML
    private Button newService_cancelBtn;

    @FXML
    void cancelAction(ActionEvent event) {

    }

    @FXML
    void saveAction(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerCombo.setPromptText("Select Customer");
        MainController main = new MainController();
        ObservableList<Customer> listCustomer = main.getCustomerList();
        customerCombo.setItems(listCustomer);
        serviceCombo.setPromptText("Select service");
        ObservableList<Service> listService = main.getServiceList();
        serviceCombo.setItems(listService);
        Customer customer = customerCombo.getSelectionModel().getSelectedItem();
        if (customer != null) {
            setCustomerCar(customer);


        }
        }
        void setCustomerCar(Customer select){
            newService_carNameLbl.setText(select.getCar().getCompany());
            newService_modelNameLbl.setText(select.getModel().getModelName());

        }

    }

