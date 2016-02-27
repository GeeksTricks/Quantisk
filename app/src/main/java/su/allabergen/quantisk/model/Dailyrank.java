package su.allabergen.quantisk.model;

/**
 * Created by Rabat on 20.02.2016.
 */
public class Dailyrank {
    private int rank;
    private String day;
    private int site_id;
    private int person_id;

//    /v1/dailyrank/? person_id = <person_id> & site_id = <site_id> & start_date = <start_date> & end_date = <end_date>

    public Dailyrank() {
    }

    public Dailyrank(int rank, String day, int site_id, int person_id) {
        this.rank = rank;
        this.day = day;
        this.site_id = site_id;
        this.person_id = person_id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getSite_id() {
        return site_id;
    }

    public void setSite_id(int site_id) {
        this.site_id = site_id;
    }
}
