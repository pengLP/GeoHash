package geohash;


import java.util.ArrayList;
import java.util.List;

public class Bean {

    private String name = "";
    private String address = "";
    private String location = "";
    private List<String> hashs = new ArrayList<>();



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getHashs() {
        return hashs;
    }

    public void setHashs(List<String> hashs) {
        this.hashs = hashs;
    }

    public Bean(String name, String address, String location, List<String> hashs) {
        this.name = name;
        this.address = address;
        this.location = location;
        this.hashs = hashs;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", location='" + location + '\'' +
                ", hashs=" + hashs +
                '}';
    }
}
