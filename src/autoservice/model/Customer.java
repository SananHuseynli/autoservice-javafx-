package autoservice.model;

public class Customer extends Model {

    private String name;
    private String phone;
    private Car car;
    private CarModel model;
    private String carNum;

    private String abc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public CarModel getModel() {
        return model;
    }

    public void setModel(CarModel model) {
        this.model = model;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getAbc() {
        return car.toString() + " " + model.getModelName();
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }

    @Override
    public String toString() {
        return name;
    }
}