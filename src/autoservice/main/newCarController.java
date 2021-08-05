package autoservice.main;

import autoservice.model.Car;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class newCarController {
    @FXML
    private AnchorPane newCarPane;

    @FXML
    private TextField newCarField;

    @FXML
    private Label newCarLbl;

    @FXML
    private Button newCarSaveBtn;

    @FXML
    private Button newCarClearBtn;

    @FXML
    void newCarClearAct(ActionEvent event) {
        newCarField.setText("");

    }

    @FXML
    void newCarSaveAct(ActionEvent event) {
        String carName = newCarField.getText();
        Car car = new Car();
        try {
            car.setCompany(carName);
            addCar(car);
            JOptionPane.showMessageDialog(null, "Yeni avtomobil əlavə edilmişdir");
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Xəta baş verdi");
            exception.printStackTrace();
        }

        MainController.carStage.close();
    }

    void addCar(Car car) throws Exception {
        DbHelper db = new DbHelper();
        String sql = "insert into car_company\n" +
                "values(nextval('car_seq'),?,'1')";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, car.getCompany());
            ps.execute();

        }
    }
}