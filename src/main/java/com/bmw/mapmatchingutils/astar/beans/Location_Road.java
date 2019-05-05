package com.bmw.mapmatchingutils.astar.beans;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class Location_Road {

    //定点到线 直接更新location的坐标 算法复杂度 O(N*logN)
    public static List<Foot_Data> location2road(Location_Data location_data, Map<Road_Data,Double> roadData){
        double x = location_data.getX();
        double y = location_data.getY();
        List<Foot_Data> FootList = new ArrayList<>();
        // 算法复杂度 O(N)
        for (Map.Entry<Road_Data, Double> entry : roadData.entrySet()) {
            Road_Data road_data = entry.getKey();
            double distance;
            double a = road_data.getA();
            double b = road_data.getB();
            double c = road_data.getC();
            double x1 = road_data.getPoint1x();
            double x2 = road_data.getPoint2x();
            double y1 = road_data.getPoint1y();
            double y2 = road_data.getPoint2y();
            double footx =(b*b*x-a*b*y-a*c)/(a*a+b*b);//垂足x
            double footy =(a*a*y-a*b*x-b*c)/(a*a+b*b);//垂足y
            //如果垂足在线段point1_point2上
            if(road_data.getPoint1x()<=footx && footx <=road_data.getPoint2x()
                    &&road_data.getPoint1y()<=footy && footy <=road_data.getPoint2y()) {
                distance = Math.abs(a * x + b * y + c) / Math.sqrt(a * a + b * b);
                double l1 = Math.sqrt(footx - x1)*(footx - x1) + (footy - y1)*(footx - y1);
                double l2 = Math.sqrt(x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1);
                double scale = l1/l2;
                Foot_Data foot_data = new Foot_Data(footx, footy, distance,scale, road_data);
                FootList.add(foot_data);
            }
        }
        // 算法复杂度 O(N*logN)
        Collections.sort(FootList);
        System.out.println(FootList.toString());
        return FootList;
    }

}
