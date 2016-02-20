package su.allabergen.quantisk.model;

/**
 * Created by Rabat on 20.02.2016.
 */
public class Wordpairs {
    private int id;
    private int distance;
    private int person_id;
    private String keyword1;
    private String keyword2;

    public Wordpairs() {
    }

    public Wordpairs(int distance, String keyword1, String keyword2, int person_id) {
        this.distance = distance;
        this.keyword1 = keyword1;
        this.keyword2 = keyword2;
        this.person_id = person_id;
    }

    public Wordpairs(int id, int distance, String keyword1, String keyword2, int person_id) {
        this(distance, keyword1, keyword2, person_id);
        this.id = id;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(String keyword1) {
        this.keyword1 = keyword1;
    }

    public String getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(String keyword2) {
        this.keyword2 = keyword2;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }
}
