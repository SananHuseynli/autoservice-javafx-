package autoservice.model;

public class Model {
    private int id;
    private  Integer active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", active=" + active +
                '}';
    }
}
