package com.df.dianping;

import android.location.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PoiResultData {
	public static  Map<String, Object> getData(String name) {
		Map<String, Object> map = new HashMap<String, Object>();;
		map.put("area", "Melbourne");
		map.put("restaurant_name","Crown");
		map.put("restaurant_average_cost",55);
		map.put("restaurant_taste_score",11);
		map.put("restaurant_environment_score",12);
		map.put("restaurant_service_score",15);
		map.put("restaurant_address","8 Ellingworth parade,Boxhill");
        map.put("restaurant_stars",10);
		return map;
	}
    public static ArrayList<Map<String, Object>> createData()
	{
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", "Mo-Mo");
			map.put("price", 6147);
			map.put("addr", "8 Ellingworth parade");
			map.put("distance", "5.8km");
			map.put("busy", 2);
			map.put("promo", false);
			map.put("card", false);
			map.put("checkin", false);
			map.put("star", 50);
			map.put("area", "Melbourne");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("name", "Ni-ni");
			map.put("price", 6285);
			map.put("addr", "9 Ellingworth parade");
			map.put("distance", "890m");
			map.put("busy", 1);
			map.put("promo", false);
			map.put("card", false);
			map.put("checkin", false);
			map.put("star", 50);
			map.put("area", "Boxhill");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("name", "Mi-mi꣩");
			map.put("price", 660);
			map.put("addr", "10 Ellingworth parade");
			map.put("distance", "8.1km");
			map.put("busy", 0);
			map.put("promo", true);
			map.put("card", true);
			map.put("checkin", false);
			map.put("star", 45);
			map.put("area", "Springvale");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("name", "Si-si");
			map.put("price", 660);
			map.put("addr", "11 Ellingworth parade");
			map.put("distance", "10km");
			map.put("busy", 1);
			map.put("promo", false);
			map.put("card", false);
			map.put("checkin", false);
			map.put("star", 40);
			map.put("area", "Clayton");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("name", "Bi-bi");
			map.put("price", 669);
			map.put("addr", "11 Ellingworth parade");
			map.put("distance", "5.0km");
			map.put("busy", 2);
			map.put("promo", true);
			map.put("card", false);
			map.put("checkin", false);
			map.put("star", 45);
			map.put("area", "Boxhill");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("name", "NNCY");
			map.put("price", 6240);
			map.put("addr", "12 Ellingworth parade");
			map.put("distance", "7.4km");
			map.put("busy", 1);
			map.put("promo", false);
			map.put("card", false);
			map.put("checkin", false);
			map.put("star", 50);
			map.put("area", "Richmond");
			list.add(map);


			map.put("name", "Na-na");
			map.put("price", 6136);
			map.put("addr", "13 Ellingworth parade");
			map.put("distance", "2.0km");
			map.put("busy", 0);
			map.put("promo", false);
			map.put("card", true);
			map.put("checkin", false);
			map.put("star", 50);
			map.put("area", "Clayton");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("name", "Zi-zi");
			map.put("price", 6324);
			map.put("addr", "14 Ellingworth parade");
			map.put("distance", "4.0km");
			map.put("busy", 1);
			map.put("promo", false);
			map.put("card", false);
			map.put("checkin", false);
			map.put("star", 50);
			map.put("area", "Boxhill");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("name", "Lu-lu");
			map.put("price", 676);
			map.put("addr", "676 Ellingworth parade");
			map.put("distance", "4.4km");
			map.put("busy", 2);
			map.put("promo", false);
			map.put("card", false);
			map.put("checkin", false);
			map.put("star", 45);
			map.put("area", "Doncaster");
			list.add(map);


		return list;
	}


    public static ArrayList<Map<String,Object>> searchDatebase(double currentLatitude , double currentLongitude) {
        String strDistance;
        System.out.println("PoiresultData: latitude:AAA"+currentLatitude);
        System.out.println("PoiresultData: longitude:AAA"+currentLongitude);
        Location startPoint=new Location("locationA");
        startPoint.setLatitude(currentLatitude);
        startPoint.setLongitude(currentLongitude);

        Location endPoint=new Location("locationA");
        endPoint.setLatitude(-37.839820);
        endPoint.setLongitude(144.942879);

        double distance=startPoint.distanceTo(endPoint);
        //-37.81981981981982

        System.out.println("distance calculate:"+ distance);
        System.out.println("nnnnnn");
        if(distance<1000){
            distance =(int) distance;
            strDistance =""+distance+"m";
        }else{
            distance = ((int)(distance/100))/10.0;
            strDistance =""+distance+"Km";
        }
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "Mo-Mo");
        map.put("price", 6147);
        map.put("addr", "8 Ellingworth parade");
        map.put("distance", strDistance);
        map.put("busy", 2);
        map.put("promo", false);
        map.put("card", false);
        map.put("checkin", false);
        map.put("star", 50);
        map.put("area", "Melbourne");
        list.add(map);
        return list;
    }
}
