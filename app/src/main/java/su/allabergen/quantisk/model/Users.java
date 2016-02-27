package su.allabergen.quantisk.model;

/**
 * Created by Rabat on 25.02.2016.
 */
public class Users {
    private int id;
    private String login;

    public Users() {
    }

    public Users(String login, int id) {
        this.login = login;
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
