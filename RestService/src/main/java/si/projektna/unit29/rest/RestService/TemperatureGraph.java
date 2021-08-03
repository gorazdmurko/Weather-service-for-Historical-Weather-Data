package si.projektna.unit29.rest.RestService;

import java.util.Date;

public class TemperatureGraph {

    private String location;
    private Date date;
    private int width;
    private int height;
    private String image;

    public TemperatureGraph() {
    }

    public TemperatureGraph(String location, Date date, int width, int height, String image) {
        this.location = location;
        this.date = date;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public String getLocation() { return location; }
    public Date getDate() { return date; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public String getImage() { return image; }

    public void setLocation(String location) { this.location = location; }
    public void setDate(Date date) { this.date = date; }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public void setImage(String image) { this.image = image; }

    @Override
    public String toString() {
        return "TemperatureGraph{" +
                "location='" + location + '\'' +
                ", date=" + date +
                ", width=" + width +
                ", height=" + height +
                ", image='" + image + '\'' +
                '}';
    }
}
