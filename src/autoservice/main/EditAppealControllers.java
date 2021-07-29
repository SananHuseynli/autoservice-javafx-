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

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    void editAppeal_SaveAction(ActionEvent event) throws Exception {
        Apply apply=getApplyById(MainController.appealId);
        Customer customer=editAppeal_CustomerCombo.getSelectionModel().getSelectedItem();
        Status status=editAppeal_StatusCombo.getSelectionModel().getSelectedItem();
        Service service=editAppeal_ServiceCombo.getSelectionModel().getSelectedItem();
        try{
            apply.setCustomer(customer);
            apply.setStatus(status);
            apply.setService(service);
            updateAppeal(apply);
            JOptionPane.showMessageDialog(null,"Xidmət yenilənmişdir");
        }catch (Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Xəta baş verdi");
        }


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
        showOldAppealData();
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
    public Apply getApplyById(Integer applyId)throws Exception {
        DbHelper db = new DbHelper();
        Apply apply = new Apply();
        String sql = "select a.id,c.\"name\",s.status,ser.service_name from appeal a\n" +
                "inner join customer c on a.customer_id = c.id\n" +
                "inner join status s on a.status_id = s.id\n" +
                "inner join service ser on a.service_id = ser.id\n" +
                "where a.active=1 and a.id=?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setInt(1, applyId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setName(rs.getString("name"));
                Status status = new Status();
                status.setStatus(rs.getString("status"));
                Service service = new Service();
                service.setService(rs.getString("service_name"));
                apply.setId(rs.getInt("id"));
                apply.setCustomer(customer);
                apply.setService(service);
                apply.setStatus(status);

            }
            return apply;

        }
    }
    void updateAppeal(Apply apply)throws Exception {
        DbHelper db = new DbHelper();
        String sql = "update appeal \n" +
                "set customer_id=" + (editAppeal_CustomerCombo.getSelectionModel().getSelectedItem().getId()) + ",status_id=" + (editAppeal_StatusCombo.getSelectionModel().getSelectedItem().getId()) + ",service_id=" + (editAppeal_ServiceCombo.getSelectionModel().getSelectedItem().getId()) +
                " where id=" + apply.getId();
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }
        void showOldAppealData(){
          try {
              Apply apply=getApplyById(MainController.appealId);
              editAppeal_CustomerCombo.getSelectionModel().select(apply.getCustomer());
              editAppeal_StatusCombo.getSelectionModel().select(apply.getStatus());
              editAppeal_ServiceCombo.getSelectionModel().select(apply.getService());
              editAppeal_CarLbl.setText(apply.getCustomer().getCar().getCompany());
              editAppeal_ModelNameLbl.setText(apply.getCustomer().getModel().getModelName());
          } catch (Exception exception) {
              exception.printStackTrace();
          }
        }
}
