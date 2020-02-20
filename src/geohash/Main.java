package geohash;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        //爬虫

        double lat = 39.909231;
        double longi = 116.397428;

        for (int i=0;i<100;i++){
            for (int j=0;j<100;j++){
                new Crawler(lat,longi).generateLocationList();
                longi -= 0.001;
            }
            lat += 0.001;
        }
    }
}
