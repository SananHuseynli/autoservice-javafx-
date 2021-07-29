package autoservice.main;

import autoservice.model.Customer;
import autoservice.model.Service;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        editServiceTxt.setText("");
        editPriceField.setText("");

    }

    @FXML
    void editServiceSaveAct(ActionEvent event) throws Exception {
        Service service=getServiceById(MainController.serviceId);
        String serviceName=editServiceTxt.getText();
        String price=editPriceField.getText();
        try {
            service.setService(serviceName);
            service.setPrice(price);
            updateService(service);
            JOptionPane.showMessageDialog(null,"Xidmət yenilənmişdir");
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null,"Xəta baş verdi");
            ex.printStackTrace();
        }
        MainController.editServiceStage.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showServiceOldData();

    }

    public Service getServiceById(Integer serviceId) throws Exception {
        DbHelper db = new DbHelper();
        Service service = new Service();
        String sql = "select s.id,s.service_name,s.service_price from service s where s.active=1 and s.id=?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setInt(1, serviceId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                service.setId(rs.getInt("id"));
                service.setService(rs.getString("service_name"));
                service.setPrice(rs.getString("service_price"));
            }
        }
        return service;

    }

    void updateService(Service service) throws Exception {
        DbHelper db = new DbHelper();
        String sql = "update service \n"
                + "set service_name='" + editServiceTxt.getText() + "',service_price='" + editPriceField.getText() + "'where id=" + service.getId();
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.executeUpdate();
        }

    }

    void showServiceOldData() {
        try {
            Service service = getServiceById(MainController.serviceId);
            editServiceTxt.setText(service.getService());
            editPriceField.setText(service.getPrice());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}



