package si.projekna.unit29.wsITF;

import java.io.Serializable;
import java.util.Date;

public class TempData implements Serializable {

    private static final long serialVersionUID = 1392489506317664479L;

    private String location;
    private String country;
    private Date time;
    private float temp_c;

    public TempData() {
    }

    public TempData(String location, String country, Date time, float temp_c) {
        this.location = location;
        this.country = country;
        this.time = time;
        this.temp_c = temp_c;
    }

    public String getLocation() { return location; }
    public String getCountry() { return country; }
    public Date getTime() {
        return time;
    }
    public float getTemp_c() {
        return temp_c;
    }

    @Override
    public String toString() {
        return "TempData{" +
                "time=" + time +
                ", temp_c=" + temp_c +
                '}';
    }

}
