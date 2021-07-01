package autoservice.model;

public class Model {
    private Integer id;
    private  Integer active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
