package autoservice.main;

import autoservice.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
    private ComboBox<Status> statusCombo;

    @FXML
    private Label newService_carNameLbl;

    @FXML
    private Label newService_modelNameLbl;

    @FXML
    private Button newService_saveBtn;

    @FXML
    private Button ok;

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
        ObservableList<Status>listStatus=getStatusList();
        statusCombo.setItems(listStatus);
    }

    @FXML
    void okAct(ActionEvent event) {
        Customer customer = customerCombo.getSelectionModel().getSelectedItem();
        if (customer != null) {
            newService_carNameLbl.setText(customer.getCar().getCompany());
            newService_modelNameLbl.setText(customer.getModel().getModelName());

        }

    }

    public ObservableList<Status> getStatusList() {
        DbHelper db = new DbHelper();
        ObservableList<Status> list = FXCollections.observableArrayList();
        String sql = "select id,status from status where active=1";
        try (Connection c = db.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Status status = new Status();
                status.setId(rs.getInt("id"));
                status.setStatus(rs.getString("status"));
                list.add(status);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
}



