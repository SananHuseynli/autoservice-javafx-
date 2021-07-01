package autoservice.main;

import autoservice.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private TableColumn<Apply, Service> col_price;

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
    private TableColumn<Customer, String > col_carNum;


    @FXML
    void applyAct(ActionEvent event) throws Exception {

        showApplyList();
        customerTable.setVisible(false);

    }

    @FXML
    void customerAct(ActionEvent event) throws Exception {
       showCustomerList();
       applyTable.setVisible(false);
    }
     @FXML
      void showCustomerList()throws Exception {
         ObservableList<Customer>customerList= getCustomerList();
         col_CustomerId.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("id"));
         col_CustomerName.setCellValueFactory(new PropertyValueFactory<Customer,String>("name"));
         col_CustomerCar.setCellValueFactory(new PropertyValueFactory<Customer,Car>("car"));
         col_CustomerPhone.setCellValueFactory(new PropertyValueFactory<Customer,String >("phone"));
         col_CustomerModel.setCellValueFactory(new PropertyValueFactory<Customer,CarModel>("model"));
         col_carNum.setCellValueFactory(new PropertyValueFactory<Customer,String>("carNum"));
         customerTable.setItems(customerList);
         customerTable.setVisible(true);

    }

    public ObservableList<Customer> getCustomerList() {
        DbHelper db=new DbHelper();
        ObservableList<Customer>list=FXCollections.observableArrayList();
        String sql="select c.id,c.name,c.phone,car.company_name,model.model_name,c.car_num from customer c\n" +
                "inner join car_company car on c.company_id = car.id\n" +
                "inner join car_model model on c.model_id = model.id";
        try (Connection c = db.getConnection();
             Statement st=c.createStatement();
             ResultSet rs = st.executeQuery(sql)){
            while (rs.next()){
                Customer customer=new Customer();
                Integer id=rs.getInt("id");
                String name=rs.getString("name");
                String phone=rs.getString("phone");
                String carNum=rs.getString("car_num");
                customer.setId(id);
                customer.setName(name);
                customer.setPhone(phone);
                customer.setCarNum(carNum);
                Car car=new Car();
                car.setCompany(rs.getString("company_name"));
                customer.setCar(car);
                CarModel model=new CarModel();
                model.setModelName(rs.getString("model_name"));
                customer.setModel(model);
                list.add(customer);
     }
        }catch (Exception ex){
            ex.printStackTrace();
        }
return list;
    }

    @FXML
    void serviceAct(ActionEvent event) {

    }

    public ObservableList<Apply> getApplyList()  {

        DbHelper db = new DbHelper();
        ObservableList<Apply> list = FXCollections.observableArrayList();
        String sql = "select a.id,c.name,s.status,ser.service_name,ser.service_price from appeal a\n" +
                "inner join customer c on a.customer_id = c.id\n" +
                "inner join status s on a.status_id = s.id\n" +
                "inner join service ser on a.service_id = ser.id";

        try (Connection c = db.getConnection();
             Statement st=c.createStatement();
        ResultSet rs = st.executeQuery(sql)){
        while (rs.next()) {
                 Apply apply=new Apply();
                 Integer id=rs.getInt("id");
                 apply.setId(id);
                 Customer customer=new Customer();
                 customer.setName(rs.getString("name"));
                 apply.setCustomer(customer);
                 Status status=new Status();
                 status.setStatus(rs.getString("status"));
                 apply.setStatus(status);
                 Service service=new Service();
                 Service price=new Service();
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
        col_id.setCellValueFactory(new PropertyValueFactory<Apply,Integer>("id"));
        col_customer.setCellValueFactory(new PropertyValueFactory<Apply,Customer>("customer"));
        col_service.setCellValueFactory(new PropertyValueFactory<Apply,Service>("service"));
        col_price.setCellValueFactory(new PropertyValueFactory<Apply,Service>("price"));
        col_status.setCellValueFactory(new PropertyValueFactory<Apply,Status>("status"));
        applyTable.setItems(applist);
        applyTable.setVisible(true);
    }



}

