package servlet;

import geohash.Bean;
import geohash.NearByFinder;
import net.sf.json.JSONArray;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

public class SearchNear extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String longitude = request.getParameter("longitude");
        String latitude = request.getParameter("latitude");
        if(longitude != null && latitude != null) {
            System.out.println("====>" + latitude);
            NearByFinder nearByFinder = new NearByFinder(Double.valueOf(latitude),Double.valueOf(longitude));
            List<Bean> beans = nearByFinder.getNearByList();

            JSONArray jsonArray = JSONArray.fromObject(beans);
            System.out.println(beans);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(jsonArray);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String longitude = request.getParameter("longitude");
        String latitude = request.getParameter("latitude");
        if(longitude != null && latitude != null) {
            System.out.println("====>" + latitude);
            NearByFinder nearByFinder = new NearByFinder(Double.valueOf(latitude),Double.valueOf(longitude));
            List<Bean> beans = nearByFinder.getNearByList();

            JSONArray jsonArray = JSONArray.fromObject(beans);
            System.out.println(beans);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(jsonArray);
        }

    }
}
