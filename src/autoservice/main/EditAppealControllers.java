package autoservice.main;

import autoservice.model.Apply;
import autoservice.model.Customer;
import autoservice.model.Service;
import autoservice.model.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class EditAppealControllers implements Initializable {
    @FXML
    private AnchorPane editAppeal_Pane;

    @FXML
    private Label editAppeal_CustomerLbl;

    @FXML
    private Label editAppeal_CarLbl;

    @FXML
    private Label editAppeal_ModelLbl;

    @FXML
    private ComboBox<Customer> editAppeal_CustomerCombo;

    @FXML
    private Label editAppeal_ServiceLbl;

    @FXML
    private ComboBox<Service> editAppeal_ServiceCombo;

    @FXML
    private Label editAppeal_CarNameLbl;

    @FXML
    private Label editAppeal_ModelNameLbl;

    @FXML
    private Button editAppeal_SaveBtn;

    @FXML
    private Button editAppeal_CancelBtn;

    @FXML
    private Button editAppeal_Ok;

    @FXML
    private ComboBox<Status> editAppeal_StatusCombo;

    @FXML
    private Label editAppeal_Statuslbl;

    @FXML
    void editAppeal_CancelAction(ActionEvent event) {

    }

    @FXML
    void editAppeal_OkAct(ActionEvent event) {

    }

    @FXML
    void editAppeal_SaveAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editAppeal_CustomerCombo.setPromptText("Select Customer");
        MainController main=new MainController();
        ObservableList<Customer> listCustomer = main.getCustomerList();
        editAppeal_CustomerCombo.setItems(listCustomer);
        editAppeal_ServiceCombo.setPromptText("Select service");
        ObservableList<Service> listService = main.getServiceList();
        editAppeal_ServiceCombo.setItems(listService);
        ObservableList<Status>listStatus=getStatusList();
        editAppeal_StatusCombo.setItems(listStatus);
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
