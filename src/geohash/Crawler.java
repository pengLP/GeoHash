package geohash;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;


//爬虫类
public class Crawler {

    //经度 -90 ~ 90
    private double longitude = 39.960767;

    //纬度 -180 ~ 180
    private double latitude  = 116.456299;

    private int currentPage = 1;

    //高德API key
    private String key = "8a66e1872cf4d05eff1d2282a6964bd3";
    private String requestUrl ;

    public Crawler( double latitude,double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;

        try {
            requestUrl = "http://restapi.amap.com/v3/place/around?key="+key+"&location="+String.valueOf(latitude)+","+String.valueOf(longitude)+"&output=json&radius=1000&keywords="+URLEncoder.encode("美食","utf-8")+"&offset=100&page="+currentPage;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // System.out.println(requestUrl);
    }

    private String sendHttpRequest(){

        try {
            URL url=new URL(requestUrl);
            URLConnection connection =url.openConnection();
            InputStream IS=connection.getInputStream();
            InputStreamReader ISR =new InputStreamReader(IS,"UTF-8");
            BufferedReader BR =new BufferedReader(ISR);

            String line;
            StringBuilder builder=new StringBuilder();
            while((line=BR.readLine())!=null){
                builder.append(line);

            }

            BR.close();
            ISR.close();
            IS.close();

            return builder.toString();

        }catch (IOException e){
            e.printStackTrace();
        }

        return null;

    }

    public ArrayList generateLocationList(){

        ArrayList list = new ArrayList();

        String jsonStr = sendHttpRequest();

        JSONObject jsonObject = JSONObject.fromObject(jsonStr);

        JSONArray poisArray = jsonObject.getJSONArray("pois");


        if (poisArray.size()!=0){
            for (int i=0; i<poisArray.size();i++){

                JSONObject locationBean = poisArray.getJSONObject(i);

                String name = locationBean.getString("name");
                String address = locationBean.getString("address");
                String location = locationBean.getString("location");

                System.out.println(name);
                System.out.println(address);
                System.out.println(location);
                System.out.println(new GeoHash(calcLong(location),calcLat(location)).getGeoHashBase32For9());
                //LocationBean bean = new LocationBean(name,calcLong(location),calcLat(location),address);
                Bean bean = new Bean(name,address,location,new GeoHash(calcLong(location),calcLat(location)).getGeoHashBase32For9());
                System.out.println();

                FileUtil.writeToFile(name);
                FileUtil.writeToFile(address);
                FileUtil.writeToFile(location);
                for (String s:bean.getHashs()) {
                    FileUtil.writeToFile(s);
                }
                FileUtil.writeToFile("\n");


                list.add(bean);

            }
        }

        return list;

    }


    private double calcLong(String location){
        String[] temp = location.split(",");
        return Double.valueOf(temp[1]);
    }

    private double calcLat(String location){
        String[] temp = location.split(",");
        return Double.valueOf(temp[0]);
    }

    public static void main(String[] args){
        new Crawler(121.459866,31.232913).generateLocationList();
    }
}
