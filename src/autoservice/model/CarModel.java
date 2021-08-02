package autoservice.model;

public class CarModel extends Model {
    private String modelName;
    private Car car;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return car+"  "+modelName;
    }
}
