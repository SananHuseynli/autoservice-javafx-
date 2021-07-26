package autoservice.main;

import autoservice.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainController {

    @FXML
    private Button serviceBtn;

    @FXML
    private Button customerBtn;

    @FXML
    private Button applyBtn;


    @FXML
    private TableView<Apply> applyTable;


    @FXML
    private TableColumn<Apply, Integer> col_id;

    @FXML
    private TableColumn<Apply, Customer> col_customer;

    @FXML
    private TableColumn<Apply, Service> col_service;

    @FXML
    private TableColumn<Apply, Status> col_status;
    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> col_CustomerId;

    @FXML
    private TableColumn<Customer, String> col_CustomerName;

    @FXML
    private TableColumn<Customer, String> col_CustomerPhone;

    @FXML
    private TableColumn<Customer, Car> col_CustomerCar;

    @FXML
    private TableColumn<Customer, CarModel> col_CustomerModel;
    @FXML
    private TableColumn<Customer, String> col_carNum;

    @FXML
    private TableView<Service> serviceTable;

    @FXML
    private TableColumn<Service, Integer> col_servicesİd;

    @FXML
    private TableColumn<Service, String> col_services;

    @FXML
    private TableColumn<Service, String> col_serPrice;

    @FXML
    private ImageView customerIcon;

    @FXML
    private ImageView serviceIcon;

    @FXML
    private ImageView appealIcon;

    @FXML
    private ImageView addIcon;

    @FXML
    private ImageView editIcon;

    @FXML
    private ImageView deleteIcon;


    private String btnName = "";

    private String buttonName="";


    private void showServicesList() {
        ObservableList<Service> servicesList = getServiceList();
        col_servicesİd.setCellValueFactory(new PropertyValueFactory<Service, Integer>("id"));
        col_services.setCellValueFactory(new PropertyValueFactory<Service, String>("service"));
        col_serPrice.setCellValueFactory(new PropertyValueFactory<Service, String>("price"));
        serviceTable.setItems(servicesList);
        serviceTable.setVisible(true);
        buttonName="serviceTable";

    }
    private void nameAppTable(){
      applyTable.setVisible(true);
        buttonName="appealTable";
    }

    public ObservableList<Service> getServiceList() {
        DbHelper db = new DbHelper();
        ObservableList<Service> list = FXCollections.observableArrayList();
        String sql = "select *from service where active=1 order by id";
        try (Connection c = db.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Service service = new Service();
                service.setId(rs.getInt("id"));
                service.setService(rs.getString("service_name"));
                service.setPrice(rs.getString("service_price"));
                list.add(service);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }


    @FXML
    void applyAct(ActionEvent event) throws Exception {
        btnName = "apply";
        showApplyList();
        customerTable.setVisible(false);
        serviceTable.setVisible(false);

    }




    @FXML
    void customerAct(ActionEvent event) throws Exception {
        btnName = "customer";
        showCustomerList();
        applyTable.setVisible(false);
        serviceTable.setVisible(false);

    }

    @FXML
    void serviceAct(ActionEvent event) {
        btnName = "service";
        showServicesList();
        applyTable.setVisible(false);
        customerTable.setVisible(false);

    }

    @FXML
    void showCustomerList() throws Exception {
        ObservableList<Customer> customerList = getCustomerList();
        col_CustomerId.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
        col_CustomerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        col_CustomerCar.setCellValueFactory(new PropertyValueFactory<Customer, Car>("car"));
        col_CustomerPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        col_CustomerModel.setCellValueFactory(new PropertyValueFactory<Customer, CarModel>("model"));
        col_carNum.setCellValueFactory(new PropertyValueFactory<Customer, String>("carNum"));
        customerTable.setItems(customerList);
        customerTable.setVisible(true);
        buttonName="customerTable";





    }

    public ObservableList<Customer> getCustomerList() {
        DbHelper db = new DbHelper();
        ObservableList<Customer> list = FXCollections.observableArrayList();
        String sql = "select c.id,c.name,c.phone,car.company_name,model.model_name,c.car_num from customer c\n" +
                "inner join car_company car on c.company_id = car.id\n" +
                "inner join car_model model on c.model_id = model.id";
        try (Connection c = db.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Customer customer = new Customer();
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String carNum = rs.getString("car_num");
                customer.setId(id);
                customer.setName(name);
                customer.setPhone(phone);
                customer.setCarNum(carNum);
                Car car = new Car();
                car.setCompany(rs.getString("company_name"));
                customer.setCar(car);
                CarModel model = new CarModel();
                model.setModelName(rs.getString("model_name"));
                customer.setModel(model);
                list.add(customer);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }


    public ObservableList<Apply> getApplyList() {

        DbHelper db = new DbHelper();
        ObservableList<Apply> list = FXCollections.observableArrayList();
        String sql = "select a.id,c.name,s.status,ser.service_name,ser.service_price from appeal a\n" +
                "inner join customer c on a.customer_id = c.id\n" +
                "inner join status s on a.status_id = s.id\n" +
                "inner join service ser on a.service_id = ser.id";

        try (Connection c = db.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Apply apply = new Apply();
                Integer id = rs.getInt("id");
                apply.setId(id);
                Customer customer = new Customer();
                customer.setName(rs.getString("name"));
                apply.setCustomer(customer);
                Status status = new Status();
                status.setStatus(rs.getString("status"));
                apply.setStatus(status);
                Service service = new Service();
                Service price = new Service();
                service.setService(rs.getString("service_name"));
                price.setPrice(rs.getString("service_price"));
                apply.setService(service);
                apply.setPrice(price);
                list.add(apply);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    @FXML
    void showApplyList() throws Exception {
        ObservableList<Apply> applist = getApplyList();
        col_id.setCellValueFactory(new PropertyValueFactory<Apply, Integer>("id"));
        col_customer.setCellValueFactory(new PropertyValueFactory<Apply, Customer>("customer"));
        col_service.setCellValueFactory(new PropertyValueFactory<Apply, Service>("service"));
        col_status.setCellValueFactory(new PropertyValueFactory<Apply, Status>("status"));
        applyTable.setItems(applist);
        applyTable.setVisible(true);

    }



    @FXML
    void addClick(MouseEvent event) throws Exception {
        switch (btnName) {
            case "customer":
                Parent customerRoot = FXMLLoader.load(getClass().getResource("newcustomer.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Add new customer");
                stage.setScene(new Scene(customerRoot, 400, 700));
                stage.show();
                break;

            case "service":
                Parent serviceRoot = FXMLLoader.load(getClass().getResource("newservice.fxml"));
                Stage serviceStage = new Stage();
                serviceStage.setTitle("Add new Service");
                serviceStage.setScene(new Scene(serviceRoot, 536, 485));
                serviceStage.setResizable(false);
                serviceStage.show();
                break;
            case "apply":

                Parent applyRoot = FXMLLoader.load(getClass().getResource("newappeal.fxml"));
                Stage applyStage = new Stage();
                applyStage.setTitle("Add new Appeal");
                applyStage.setScene(new Scene(applyRoot, 536, 485));
                applyStage.setResizable(false);
                applyStage.show();

        }

    }


    @FXML
    void deleteClick(MouseEvent event) {

    }

    @FXML
    void editClick(MouseEvent event) throws Exception {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        Apply apply = applyTable.getSelectionModel().getSelectedItem();
        Service service = serviceTable.getSelectionModel().getSelectedItem();
        // if(customer!=null && apply==null && service==null){
        switch (buttonName) {
            case "customerTable":
                Parent editCustomerRoot = FXMLLoader.load(getClass().getResource("editcustomer.fxml"));
                Stage customerStage = new Stage();
                customerStage.setTitle("Update customer");
                customerStage.setScene(new Scene(editCustomerRoot, 400, 700));
                customerStage.show();
                break;
            case "serviceTable":
                // else if(service!=null && customer==null && apply==null){
                Parent editServiceRoot = FXMLLoader.load(getClass().getResource("editservice.fxml"));
                Stage serviceStage = new Stage();
                serviceStage.setTitle("Update service");
                serviceStage.setScene(new Scene(editServiceRoot, 400, 700));
                serviceStage.show();
                break;
            //  }else if (apply!=null && customer==null && service==null){
            case "appealTable":
                Parent editAppealRoot = FXMLLoader.load(getClass().getResource("editappeal.fxml"));
                Stage appealStage = new Stage();
                appealStage.setTitle("Update appeal");
                appealStage.setScene(new Scene(editAppealRoot, 400, 700));
                appealStage.show();

        }

    }










    void deleteService(Integer serviceId)throws Exception{
        DbHelper db=new DbHelper();
        String sql="update service set active=0 where id=?;";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1,serviceId);
            ps.execute();

        }
    }
}

