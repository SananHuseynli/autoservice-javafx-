package autoservice.main;

import autoservice.model.Car;
import autoservice.model.CarModel;
import autoservice.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class EditCustomerController implements Initializable {
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
    void editCancelAct(ActionEvent event) {

    }

    @FXML
    void editSaveAct(ActionEvent event) {
        String newName=editCustNameField.getText();
        String newPhone=editCustPhoneField.getText();
        Car car=editCustCarCombo.getSelectionModel().getSelectedItem();
        CarModel model=editCustModelCombo.getSelectionModel().getSelectedItem();
        String newCarNum=editCustNumField.getText();
        Customer customer=new Customer();
        try{
            customer.setName(newName);
            customer.setPhone(newPhone);
            customer.setCar(car);
            customer.setModel(model);
            customer.setCarNum(newCarNum);
            updateCustomer(customer);
            JOptionPane.showMessageDialog(null,"Customer updated");
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null,"Error");
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
    void updateCustomer(Customer customer) throws Exception {
        DbHelper db=new DbHelper();
        String sql="update customer \n" +
                "set name=?,phone=?,company_id=?,model_id=?,car_num=?\n" +
                "where id=?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1,customer.getName());
            ps.setString(2,customer.getPhone());
            ps.setInt(3,customer.getCar().getId());
            ps.setInt(4,customer.getModel().getId());
            ps.setInt(5,customer.getId());
            ps.execute();

        }
    }


}