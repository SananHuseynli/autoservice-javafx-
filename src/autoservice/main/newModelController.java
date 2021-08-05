package autoservice.main;

import autoservice.model.Car;
import autoservice.model.CarModel;
import autoservice.model.Service;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Observable;
import java.util.ResourceBundle;

public class newModelController implements Initializable {
    @FXML
    private AnchorPane newModelPane;

    @FXML
    private ComboBox<Car> newModelCarCombo;

    @FXML
    private TextField newModelNameField;

    @FXML
    private Button newModelSaveBtn;

    @FXML
    private Button newModelClearBtn;

    @FXML
    void newModelClearAct(ActionEvent event) {

    }

    static int car_id;

    void addModel(CarModel model) throws Exception {
        DbHelper db = new DbHelper();
        String sql = "insert into car_model " +
                "values(nextval('model_seq'),?,1,?)";

        String sql2 = "select id from car_company where company_name='" +
                newModelCarCombo.getSelectionModel().getSelectedItem() + "'";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql);
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql2)) {
            ps.setString(1, model.getModelName());
            System.out.println(newModelCarCombo.getSelectionModel().getSelectedItem());
            while (rs.next()) {
                car_id = rs.getInt("id");
            }
            ps.setInt(2, car_id);
            ps.execute();

        }
    }

    @FXML
    void newModelSaveAct(ActionEvent event) {
        String model = newModelNameField.getText();
        Car car = newModelCarCombo.getSelectionModel().getSelectedItem();
        CarModel carModel = new CarModel();
        try {
            carModel.setModelName(model);
            carModel.setCar(car);
            addModel(carModel);
            JOptionPane.showMessageDialog(null, "Yeni model əlavə edilmişdir");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Xəta baş verdi");
            ex.printStackTrace();
        }
        MainController.modelStage.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NewCustomerController car = new NewCustomerController();
        ObservableList<Car> list = car.getCarList();
        newModelCarCombo.setItems(list);
    }
}
