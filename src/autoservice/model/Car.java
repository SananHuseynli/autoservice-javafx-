package autoservice.model;

public class Car extends Model {
    private String company;


    public Car(String company) {
        this.company = company;
    }

    public Car() {

    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return company;

    }
}
