package com.bmw.mapmatchingutils.astar;

import com.bmw.mapmatchingutils.astar.beans.Foot_Data;
import com.bmw.mapmatchingutils.astar.beans.PointData;
import com.bmw.mapmatchingutils.astar.beans.Road_Data;

import java.util.List;
import java.util.Map;


public class AstarTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DBHelper dbhelper = new DBHelper();
		Map<Road_Data, Double> roadData = dbhelper.query_road(); // ������ݿ��ж�ȡ��road��
		Map<PointData, List<PointData>> pointData = dbhelper.query_point(); // ������ݿ��ж�ȡ��point��AStarʹ��
		A_star_getDistance a = new A_star_getDistance();
		a.initData(roadData, pointData);
		Foot_Data start = new Foot_Data(1.0,1.0,0.0,new Road_Data(1,0.4,1,3.3));
		Foot_Data goal1 = new Foot_Data(1.0,2.0,0.0,new Road_Data(1,0.4,1,3.3));
		a.init(start, goal1);
		System.out.println(a.getDistance());
		
		Foot_Data goal2 = new Foot_Data(1.0,8.5,0.0,new Road_Data(1,8.05,1,10));
		a.init(start, goal2);
		System.out.println(a.getDistance());
		
		
//		A_star A = new A_star();
//		A.initData(roadData, pointData);
//		PointData start0 = new PointData(1.0,0.4,0.0);
//		PointData goal01 = new PointData(1.0,8.05,0.0);
//		A.init(start0);
//		System.out.println(A.GetPath(goal01));
	}

}
