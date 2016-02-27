package su.allabergen.quantisk.model;

/**
 * Created by Rabat on 20.02.2016.
 */
public class Sites {
    private int id;
    private String name;

    public Sites() {
    }

    public Sites(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
