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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    private Button serviceBtn;

    @FXML
    private Button customerBtn;

    @FXML
    private Button applyBtn;

    @FXML
    private AnchorPane Pane;


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
    private ImageView editIcon1;

    @FXML
    private ImageView editIcon2;

    @FXML
    private ImageView serviceDeleteIcon;

    @FXML
    private ImageView appealDeleteIcon;

    @FXML
    private ImageView customerDeleteIcon;

    @FXML
    private ImageView addCarId;

    @FXML
    private ImageView deleteCarId;

    @FXML
    private ImageView addModelİd;

    @FXML
    private ImageView deleteModelİd;

    @FXML
    private Button carsId;

    @FXML
    private Button modelsİd;

    @FXML
    private ListView<CarModel> modelListId;
    @FXML
    private ListView<Car> carListİd;

    @FXML
    private StackPane stactListView;


    @FXML
    private StackPane stackPane;


    private String btnName = "";

    private String buttonName = "";

    private void showServicesList() {
        ObservableList<Service> servicesList = getServiceList();
        col_servicesİd.setCellValueFactory(new PropertyValueFactory<Service, Integer>("id"));
        col_services.setCellValueFactory(new PropertyValueFactory<Service, String>("service"));
        col_serPrice.setCellValueFactory(new PropertyValueFactory<Service, String>("price"));
        serviceTable.setItems(servicesList);
        serviceTable.setVisible(true);
        buttonName = "serviceTable";

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
        stackPane.setVisible(true);
        showApplyList();
        customerTable.setVisible(false);
        serviceTable.setVisible(false);
        btnName = "apply";
        editIcon1.setVisible(true);
        editIcon.setVisible(false);
        editIcon2.setVisible(false);
        appealDeleteIcon.setVisible(true);
        customerDeleteIcon.setVisible(false);
        serviceDeleteIcon.setVisible(false);


    }


    @FXML
    void customerAct(ActionEvent event) throws Exception {
        stackPane.setVisible(true);
        showCustomerList();
        applyTable.setVisible(false);
        serviceTable.setVisible(false);
        btnName = "customer";
        editIcon1.setVisible(false);
        editIcon2.setVisible(false);
        editIcon.setVisible(true);
        customerDeleteIcon.setVisible(true);
        serviceDeleteIcon.setVisible(false);
        appealDeleteIcon.setVisible(false);


    }

    @FXML
    void serviceAct(ActionEvent event) {
        stackPane.setVisible(true);
        showServicesList();
        applyTable.setVisible(false);
        customerTable.setVisible(false);
        btnName = "service";
        editIcon1.setVisible(false);
        editIcon.setVisible(false);
        editIcon2.setVisible(true);
        serviceDeleteIcon.setVisible(true);
        customerDeleteIcon.setVisible(false);
        appealDeleteIcon.setVisible(false);


    }

    @FXML
    void showCustomerList() throws Exception {
        ObservableList<Customer> customerList = getCustomerList();
        col_CustomerId.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
        col_CustomerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        col_CustomerPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        col_CustomerModel.setCellValueFactory(new PropertyValueFactory<Customer, CarModel>("abc"));
        col_carNum.setCellValueFactory(new PropertyValueFactory<Customer, String>("carNum"));
        customerTable.setItems(customerList);
        customerTable.setVisible(true);
        buttonName = "customerTable";


    }

    public ObservableList<Customer> getCustomerList() {
        DbHelper db = new DbHelper();
        ObservableList<Customer> list = FXCollections.observableArrayList();
        String sql = "select c.id,c.name,c.phone,car.company_name,model.model_name,c.car_num from customer c " +
                "inner join car_company car on c.company_id = car.id " +
                "inner join car_model model on c.model_id = model.id where c.active=1 order by id";
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
        String sql = "select a.id,c.name,s.status,ser.service_name,ser.service_price from appeal a \n" +
                "                inner join customer c on a.customer_id = c.id\n" +
                "                inner join status s on a.status_id = s.id\n" +
                "                inner join service ser on a.service_id = ser.id where a.active=1";

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

    public ObservableList<Car> getCarList() {
        DbHelper db = new DbHelper();
        ObservableList<Car> list = FXCollections.observableArrayList();
        String sql = "select id,company_name from car_company where active=1";
        try (Connection c = db.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt("id"));
                car.setCompany(rs.getString("company_name"));
                list.add(car);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }


    public static ObservableList<CarModel> getModelList() {
        DbHelper db = new DbHelper();
        ObservableList<CarModel> list = FXCollections.observableArrayList();
        String sql = "select m.id,m.model_name,c.company_name from car_model m\n" +
                "inner join car_company c on m.car_id = c.id where m.active=1 order by car_id";
        try (Connection c = db.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                CarModel model = new CarModel();
                Car car = new Car();
                model.setId(rs.getInt("id"));
                model.setModelName(rs.getString("model_name"));
                car.setCompany(rs.getString("company_name"));
                model.setCar(car);
                list.add(model);
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
        buttonName = "appealTable";

    }

    public static Stage newCustomerStage = new Stage();
    public static Stage newServiceStage = new Stage();
    public static Stage newAppealStage = new Stage();

    @FXML
    void addClick(MouseEvent event) throws Exception {
        switch (btnName) {
            case "customer":
                Parent customerRoot = FXMLLoader.load(getClass().getResource("newcustomer.fxml"));
                newCustomerStage.setTitle("Add new customer");
                newCustomerStage.setScene(new Scene(customerRoot, 400, 700));
                newCustomerStage.setResizable(false);
                newCustomerStage.show();
                break;

            case "service":
                Parent serviceRoot = FXMLLoader.load(getClass().getResource("newservice.fxml"));
                newServiceStage.setTitle("Add new Service");
                newServiceStage.setScene(new Scene(serviceRoot, 536, 485));
                newServiceStage.setResizable(false);
                newServiceStage.setResizable(false);
                newServiceStage.show();
                break;
            case "apply":

                Parent applyRoot = FXMLLoader.load(getClass().getResource("newappeal.fxml"));
                newAppealStage.setTitle("Add new Appeal");
                newAppealStage.setScene(new Scene(applyRoot, 536, 485));
                newAppealStage.setResizable(false);
                newAppealStage.show();

        }

    }


    public static int id;
    public static Stage editCustomerStage = new Stage();

    @FXML
    void editClick(MouseEvent ev) throws Exception {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();

        id = customer.getId();
        if (customer != null) {
            Parent customerRoot = FXMLLoader.load(getClass().getResource("editcustomer.fxml"));
            editCustomerStage.setTitle("Update customer");
            editCustomerStage.setScene(new Scene(customerRoot, 400, 700));
            editCustomerStage.setResizable(false);
            editCustomerStage.show();

        }
    }


    public static int appealId;
    public static Stage editAppealStage = new Stage();

    @FXML
    void editClick1(MouseEvent event) throws Exception {
        Apply apply = applyTable.getSelectionModel().getSelectedItem();
        appealId = apply.getId();
        if (apply != null) {
            Parent appealRoot = FXMLLoader.load(getClass().getResource("editappeal.fxml"));
            editAppealStage.setTitle("Update appeal");
            editAppealStage.setScene(new Scene(appealRoot, 400, 700));
            editAppealStage.setResizable(false);
            editAppealStage.show();
        }

    }


    public static int serviceId;
    public static Stage editServiceStage = new Stage();

    @FXML
    void editClick2(MouseEvent event) throws Exception {
        Service service = serviceTable.getSelectionModel().getSelectedItem();
        serviceId = service.getId();
        if (service != null) {
            Parent serviceRoot = FXMLLoader.load(getClass().getResource("editservice.fxml"));
            editServiceStage.setTitle("Update service");
            editServiceStage.setScene(new Scene(serviceRoot, 536, 485));
            editServiceStage.setResizable(false);
            editServiceStage.show();
        }
    }

    @FXML
    void serviceDeleteClick(MouseEvent event) throws Exception {
        int isConfirmed;
        Integer serviceId = serviceTable.getSelectionModel().getSelectedItem().getId();
        if (serviceId != null) {
            isConfirmed = JOptionPane.showConfirmDialog(null, "Xidməti silmət istəyirsiniz?", "Xidməti sil", JOptionPane.YES_NO_OPTION);
            if (isConfirmed == JOptionPane.YES_OPTION) {
                deleteService(serviceId);
                JOptionPane.showMessageDialog(null, "Xidmət silinmişdir");
            }
        }
        showServicesList();
    }

    @FXML
    void customerDeleteClick(MouseEvent event) throws Exception {
        int isConfirmed;
        Integer customerId = customerTable.getSelectionModel().getSelectedItem().getId();
        if (customerId != null) {
            isConfirmed = JOptionPane.showConfirmDialog(null, "Müştərini silmək istədiyinizə əminsiniz?", "Müştərini sil", JOptionPane.YES_NO_OPTION);
            if (isConfirmed == JOptionPane.YES_OPTION) {
                deleteCustomer(customerId);
                JOptionPane.showMessageDialog(null, "Müştəri silinmişdir");
            }
            showCustomerList();
        }

    }

    @FXML
    void appealDeleteClick(MouseEvent event) throws Exception {
        int isConfirmed;
        Integer appealId = applyTable.getSelectionModel().getSelectedItem().getId();
        if (appealId != null) {
            isConfirmed = JOptionPane.showConfirmDialog(null, "Müraciəti silmək istədiyinizə əminsiniz?", "Müraciəti sil", JOptionPane.YES_NO_OPTION);
            if (isConfirmed == JOptionPane.YES_OPTION) {
                deleteAppeal(appealId);
                JOptionPane.showMessageDialog(null, "Müraciər silinmişdir");
            }
        }
        showApplyList();
    }

    void deleteService(Integer serviceId) throws Exception {
        DbHelper db = new DbHelper();
        String sql = "update service set active=0 where id=?;";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, serviceId);
            ps.executeUpdate();

        }
    }

    void deleteCustomer(Integer customerId) throws Exception {
        DbHelper db = new DbHelper();
        String sql = "update customer set active=0 where id=?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.executeUpdate();

        }
    }

    void deleteAppeal(Integer appealId) throws Exception {
        DbHelper db = new DbHelper();
        String sql = "update appeal set active=0 where id=?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, appealId);
            ps.executeUpdate();
        }

    }

    @FXML
    void carsAct(ActionEvent event) {
        carListİd.setItems(getCarList());
        modelListId.setVisible(false);
        carListİd.setVisible(true);

    }

    @FXML
    void modelsAct(ActionEvent event) {
        modelListId.setItems(getModelList());
        carListİd.setVisible(false);
        modelListId.setVisible(true);

    }

    static Stage carStage = new Stage();

    @FXML
    void addCarClick(MouseEvent event) throws Exception {
        Parent carRoot = FXMLLoader.load(getClass().getResource("newcar.fxml"));
        carStage.setTitle("Add new car");
        carStage.setScene(new Scene(carRoot, 570, 204));
        carStage.setResizable(false);
        carStage.show();

    }

    @FXML
    void deleteCarClick(MouseEvent event) throws Exception {
        int isConfirmed;
        Integer carId = carListİd.getSelectionModel().getSelectedItem().getId();
        if (carId != null) {
            isConfirmed = JOptionPane.showConfirmDialog(null, "Avtomobili silmət istəyirsiniz?", "Xidməti sil", JOptionPane.YES_NO_OPTION);
            if (isConfirmed == JOptionPane.YES_OPTION) {
                deleteCar(carId);
                JOptionPane.showMessageDialog(null, "Avtomobil silinmişdir");
            }
            showCarListView();
        }

    }

    void showCarListView() {
        ObservableList<Car> list = getCarList();
        carListİd.setItems(list);
        carListİd.setVisible(true);
    }

    void showModelList() {
        ObservableList<CarModel> list = getModelList();
        modelListId.setItems(list);
        modelListId.setVisible(true);
    }

    static Stage modelStage = new Stage();

    @FXML
    void addModelClick(MouseEvent event) throws Exception {
        Parent modelRoot = FXMLLoader.load(getClass().getResource("newModel.fxml"));
        modelStage.setTitle("Add new model");
        modelStage.setScene(new Scene(modelRoot, 517, 253));
        modelStage.setResizable(false);
        modelStage.show();

    }


    @FXML
    void deleteModelClick(MouseEvent event) throws Exception {
        int isConfirmed;
        Integer modelId = modelListId.getSelectionModel().getSelectedItem().getId();
        if (modelId != null) {
            isConfirmed = JOptionPane.showConfirmDialog(null, "Modeli silmət istəyirsiniz?", "Xidməti sil", JOptionPane.YES_NO_OPTION);
            if (isConfirmed == JOptionPane.YES_OPTION) {
                deleteModel(modelId);
                JOptionPane.showMessageDialog(null, "Model silinmişdir");
            }
            showModelList();
        }

    }

    @FXML
    void closeListClick(MouseEvent event) {
        carListİd.setVisible(false);
        modelListId.setVisible(false);

    }


    void deleteCar(Integer carId) throws Exception {
        DbHelper db = new DbHelper();
        String sql = "update car_company set active=0 where id=?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, carId);
            ps.executeUpdate();
        }
    }

    void deleteModel(Integer modelId) throws Exception {
        DbHelper db = new DbHelper();
        String sql = "update car_model set active=0 where id=?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, modelId);
            ps.executeUpdate();
        }

    }


}



