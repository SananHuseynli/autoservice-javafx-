package autoservice.model;

public class Status extends Model {
    private String status;

    public Status(String status) {
        this.status = status;
    }

    public Status() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return   status ;

    }
}
