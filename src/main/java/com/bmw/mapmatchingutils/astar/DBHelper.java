package com.bmw.mapmatchingutils.astar;

import com.bmw.mapmatchingutils.astar.beans.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class DBHelper {
	private Connection con;
	private Statement st;
	private String dbURL = "jdbc:sqlite:bin/myTest.db";
	private String dbURL0 = "jdbc:sqlite:res/ESPonly.db";
	public List<String> GetMacList(){
		List<String> macList = new ArrayList<>();
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(dbURL0);
			st = con.createStatement();
			//???????MAC???
			ResultSet rs = st.executeQuery("select * from ap_table_id_old");
			while (rs.next()) {
				// read the result set
				String s = "Mac_" + rs.getString("Mac").replace(":", "_");
				macList.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return macList;
	}



	public Map<String, AP_Data> GetmBeanMap(List<String> maclist){
		Map<String, AP_Data> mBeanMap = new HashMap<>();
		AP_Data ap_data = null;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(dbURL0);
			st = con.createStatement();
			//
			ResultSet rs = st.executeQuery("select * from ap_information");
			while (rs.next()) {
				String mac = rs.getString("Mac");
				// double a = cursor.getDouble(cursor.getColumnIndex("a"));
				double b = rs.getDouble("b");
				double h = rs.getDouble("h");
				double x = rs.getDouble("x");
				double y = rs.getDouble("y");
				double rssi_cali = rs.getDouble("rssi_calling");
				ap_data = new AP_Data(x, y, h, b, 1000, 0, -100, rssi_cali, mac);
				mBeanMap.put(mac, ap_data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mBeanMap;
	}

    //??????
    public Map<Road_Data,Double>  query_road() {
        Map<Road_Data,Double> roadDataMap = new HashMap<>();
        try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(dbURL0);
			st = con.createStatement();
			// ???????????
			ResultSet rs = st.executeQuery("select * from road_data");
            while (rs.next()) {
                double A = rs.getDouble("A");
                double B = rs.getDouble("B");
                double C = rs.getDouble("C");
                double point1x = rs.getDouble("point1x");
                double point1y = rs.getDouble("point1y");
                double point2x = rs.getDouble("point2x");
                double point2y = rs.getDouble("point2y");
                double cost = rs.getDouble("cost");
                Road_Data road_data = new Road_Data(A,B,C,point1x,point1y,point2x,point2y);
                roadDataMap.put(road_data,cost);
            }
            return roadDataMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roadDataMap;
    }


    //??????
    public Map<PointData,List<PointData>>  query_point() {
        Map<PointData,List<PointData>> mapData = new HashMap<>();
        try {
        	Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(dbURL0);
			st = con.createStatement();
			// ???????????
			ResultSet rs = st.executeQuery("select * from point_data");
            while (rs.next()) {
                double x = rs.getDouble("x");
                double y = rs.getDouble("y");
                double point1x = rs.getDouble("point1x");
                double point1y = rs.getDouble("point1y");
                double point2x = rs.getDouble("point2x");
                double point2y = rs.getDouble("point2y");
                double point3x = rs.getDouble("point3x");
                double point3y = rs.getDouble("point3y");
                double point4x = rs.getDouble("point4x");
                double point4y = rs.getDouble("point4y");
                PointData pointData = new PointData(x,y,0.0);
                List<PointData> value= new ArrayList<>();
                if (point1x!=-1)
                    value.add(new PointData(point1x,point1y,0.0));
                if (point2x!=-1)
                    value.add(new PointData(point2x,point2y,0.0));
                if (point3x!=-1)
                    value.add(new PointData(point3x,point3y,0.0));
                if (point4x!=-1)
                    value.add(new PointData(point4x,point4y,0.0));
                mapData.put(pointData,value);
            }
            return mapData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapData;
    }




	public List<XY_WifiRssi> GetPointDataList(List<String> maclist){
		List<XY_WifiRssi> wifidatalist = new ArrayList<>();
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(dbURL);
			st = con.createStatement();
			//???????????
			ResultSet rs = st.executeQuery("select * from point_table_1");
			while (rs.next()) {
				Map<String, Double> wifiMap = new LinkedHashMap<>();
				// read the result set
				double x = rs.getDouble("X");
				double y = rs.getDouble("Y");
				for(int i = 0;i<maclist.size();i++)
				{
					double rssi = rs.getDouble(maclist.get(i));
					String s = maclist.get(i).replace("_", ":").substring(4);
					wifiMap.put(s,rssi);
				}
				wifidatalist.add(new XY_WifiRssi(x,y,wifiMap));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wifidatalist;
	}

	public void createLocationTable(){
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(dbURL);
			st = con.createStatement();
			st.executeUpdate("CREATE TABLE location_table (X VARCHAR(8),Y VARCHAR(8))");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void insertLocationTable(Location_Data location){
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(dbURL);
			st = con.createStatement();
			st.executeUpdate("insert into location_table (X,Y) values("+location.getX()+","+location.getY()+")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
