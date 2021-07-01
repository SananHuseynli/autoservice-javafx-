/*package autoservice.main;

import autoservice.model.Apply;
import autoservice.model.Customer;
import autoservice.model.Service;
import autoservice.model.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class ApplyControllers {
    @FXML
    private TableView<Apply> applyTable;

    @FXML
    private TableColumn<Apply,Integer> col_id;

    @FXML
    private TableColumn<Apply, Customer> col_customer;

    @FXML
    private TableColumn<Apply,Service> col_service;

    @FXML
    private TableColumn<Apply,Service> col_price;

    @FXML
    private TableColumn<Apply, Status> col_status;



    @FXML
    void applyAct(ActionEvent event) {
        col_id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        col_customer.setCellValueFactory(new PropertyValueFactory<>("Müştəri"));
        col_service.setCellValueFactory(new PropertyValueFactory<>("Xidmət"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("Qiymət"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("Status"));
        getApplyList();




    }

    public void getApplyList() {
        ObservableList<Apply> list = FXCollections.observableArrayList();
        String sql = "select a.id,c.name,s.status,ser.service_name,ser.service_price from appeal a\n" +
                "inner join customer c on a.customer_id = c.id \n" +
                "inner join status s on a.status_id = s.id \n" +
                "inner join service ser on a.service_id = ser.id";

        try (Connection c = DbHelper.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Apply apply = new Apply();
                apply.setId(rs.getInt("id"));
                Customer customer = new Customer();
                customer.setName(rs.getString("name"));
                apply.setCustomer(customer);
                Status status = new Status();
                status.setStatus(rs.getString("status"));
                apply.setStatus(status);
                Service service = new Service();
                service.setService(rs.getString("service_name"));
                service.setPrice(rs.getString("service_price"));
                apply.setService(service);
                list.add(apply);
                applyTable.setItems(list);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
*/