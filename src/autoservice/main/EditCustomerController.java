package autoservice.main;

import autoservice.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.print.DocFlavor;
import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EditCustomerController implements Initializable, CustomerService {

    @FXML
    private AnchorPane editCustPane;

    @FXML
    private Label editCustNameLbl;

    @FXML
    private TextField editCustNameField;

    @FXML
    private TextField editCustPhoneField;

    @FXML
    private Label editCustPhoneLbl;

    @FXML
    private Label editCustCarLbl;

    @FXML
    private ComboBox<Car> editCustCarCombo;

    @FXML
    private Label editCustModelLbl;

    @FXML
    private ComboBox<CarModel> editCustModelCombo;

    @FXML
    private Label editCustCarNumLbl;

    @FXML
    private TextField editCustNumField;

    @FXML
    private Button editCustSaveBtn;

    @FXML
    private Button editCustCancelBtn;
    @FXML
    private TableView<Apply> applyTable;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableView<Service> serviceTable;


    @FXML
    void editCancelAct(ActionEvent event) {


    }


    @FXML
    void editSaveAct(ActionEvent event) throws Exception {
        Customer customer = getCustomerById(MainController.id);
        String newName = editCustNameField.getText();
        String newPhone = editCustPhoneField.getText();
        Car car = editCustCarCombo.getValue();
        CarModel model = editCustModelCombo.getValue();
        String newCarNum = editCustNumField.getText();
        try {
            customer.setName(newName);
            customer.setPhone(newPhone);
            customer.setCar(car);
            customer.setModel(model);
            customer.setCarNum(newCarNum);
            updateCustomer(customer);
            JOptionPane.showMessageDialog(null, "Müştəri yenilənmişdir");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Xəta baş verdi");
            ex.printStackTrace();
        }



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Car> listCar = getCarList();
        ObservableList<CarModel> listModel = getModelList();
        editCustCarCombo.setPromptText("Select Car");
        editCustCarCombo.setItems(listCar);
        editCustModelCombo.setPromptText("Select Model");
        editCustModelCombo.setItems(listModel);
        showCustomerOldData();


    }


    void updateCustomer(Customer customer) throws Exception {
        DbHelper db = new DbHelper();
        String sql = "update customer \n " +
                "set name='" + editCustNameField.getText() + "',phone='" + editCustPhoneField.getText() + "',company_id=" + (editCustCarCombo.getSelectionModel().getSelectedItem().getId()) + ",model_id=" + (editCustModelCombo.getSelectionModel().getSelectedItem().getId())
                + ",car_num='" + editCustNumField.getText() + "' where customer.id=" + customer.getId();
//     String sql = "update customer set name='"+editCustNameField.getText()+"' where customer.id="+customer.getId();
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.executeUpdate();
//            c.commit();

        };
    }

    public Customer getCustomerById(Integer customerId) throws Exception {
        DbHelper db = new DbHelper();
        Customer customer = new Customer();
        String sql = "select c.id,c.name,car.company_name,model.model_name,c.phone,c.car_num from customer c \n" +
                "inner join car_company car on c.company_id=car.id\n" +
                "inner join car_model model on c.model_id=model.id\n" +
                "where c.active=1 and c.id=?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt("id"));
                car.setCompany(rs.getString("company_name"));
                CarModel model = new CarModel();
                model.setId(rs.getInt("id"));
                model.setModelName(rs.getString("model_name"));
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setCar(car);
                customer.setModel(model);
                customer.setPhone(rs.getString("phone"));
                customer.setCarNum(rs.getString("car_num"));
            }
        }
        return customer;
    }

    public ObservableList<Car> getCarList() {
        DbHelper db = new DbHelper();
        ObservableList<Car> list = FXCollections.observableArrayList();
        String sql = "select company_name from car_company";
        try (Connection c = db.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Car car = new Car();
                car.setCompany(rs.getString("company_name"));
                list.add(car);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }


    public ObservableList<CarModel> getModelList() {
        DbHelper db = new DbHelper();
        ObservableList<CarModel> list = FXCollections.observableArrayList();
        String sql = "select * from car_model";
        try (Connection c = db.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                CarModel model = new CarModel();
                model.setModelName(rs.getString("model_name"));
                list.add(model);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;


    }
    public void showCustomerOldData(){
        try {
            Customer customer=getCustomerById(MainController.id);
            editCustNameField.setText(customer.getName());
            editCustNumField.setText(customer.getCarNum());
            editCustPhoneField.setText(customer.getPhone());
            editCustCarCombo.getSelectionModel().select(customer.getCar());
            editCustModelCombo.getSelectionModel().select(customer.getModel());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}