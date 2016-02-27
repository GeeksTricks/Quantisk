package su.allabergen.quantisk.model;

/**
 * Created by Rabat on 20.02.2016.
 */
public class Totalrank {
    private int rate;
    private int person_id;
    private int site_id;

    public Totalrank() {
    }

    public Totalrank(int rate, int person_id, int site_id) {
        this.rate = rate;
        this.person_id = person_id;
        this.site_id = site_id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getSite_id() {
        return site_id;
    }

    public void setSite_id(int site_id) {
        this.site_id = site_id;
    }

}
