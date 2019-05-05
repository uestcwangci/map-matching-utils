package com.bmw.mapmatchingutils.astar;

import com.bmw.mapmatchingutils.astar.beans.Foot_Data;
import com.bmw.mapmatchingutils.astar.beans.PointData;
import com.bmw.mapmatchingutils.astar.beans.Road_Data;
import types.RoadPosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;


public class A_star_getDistance {
    private PriorityQueue<PointData> frontier = new PriorityQueue<>();
    private Map<PointData,PointData> came_from = new HashMap<>();
    private Map<PointData,Double> cost_so_far = new HashMap<>();
    private Map<Road_Data,Double> roadData;
    private Map<Road_Data,Double> newRoadData = new HashMap<>();
    private Map<PointData,List<PointData>> mapData;
    private Map<PointData,List<PointData>> newMapData = new HashMap<>();
    private Foot_Data start,goal;
    private boolean issameroad;

    //������� ���Ҽ����ʼ������
    public void init(RoadPosition start, RoadPosition end) {
        init(new Foot_Data(start.position.x, start.position.y, start.distance, start.fraction, start.road_data),
                new Foot_Data(end.position.x, end.position.y, end.distance, end.fraction, end.road_data));
    }
    public void init(Foot_Data start,Foot_Data goal){
    	newMapData.clear();
        frontier.clear();
        came_from.clear();
        cost_so_far.clear();
        frontier.add(new PointData(start.getFootx(),start.getFootx(),0.0));
        this.start = start;
        this.goal = goal;
        
		
		
        newMapData.putAll(mapData);
        newRoadData.putAll(roadData);
        double sx = start.getFootx();
        double sy = start.getFooty();
        double gx = goal.getFootx();
        double gy = goal.getFooty();
        
        Road_Data r1 = start.getRoad_data();
        PointData pointData11 = new PointData(r1.getPoint1x(),r1.getPoint1y(),0.0);
        PointData pointData12 = new PointData(r1.getPoint2x(),r1.getPoint2y(),0.0);
        double p11x = pointData11.getX();
        double p11y = pointData11.getY();
        double p12x = pointData12.getX();
        double p12y = pointData12.getY();
//        System.out.println(r1.toString());
        
        Road_Data r2 = goal.getRoad_data();
        PointData pointData21 = new PointData(r2.getPoint1x(),r2.getPoint1y(),0.0);
        PointData pointData22 = new PointData(r2.getPoint2x(),r2.getPoint2y(),0.0);
        double p21x = pointData21.getX();
        double p21y = pointData21.getY();
        double p22x = pointData22.getX();
        double p22y = pointData22.getY();
//        System.out.println(r2.toString());
        
        newRoadData.remove(r1);//�Ƴ�·��1
        newRoadData.remove(r2);//�Ƴ�·��2
        
        
        double d = (sx-p11x)*(sx-p11x)+(sy-p11y)*(sy-p11y);//·��11����
        newRoadData.put(new Road_Data(p11x,p11y,sx,sy), Math.sqrt(d));//���·��11
        d = (sx-p12x)*(sx-p12x)+(sy-p12y)*(sy-p12y);//·��12����
        newRoadData.put(new Road_Data(sx,sy,p12x,p12y), Math.sqrt(d));//���·��12
        
        d = (gx-p21x)*(gx-p21x)+(gy-p21y)*(gy-p21y);//·��21����
        newRoadData.put(new Road_Data(p21x,p21y,gx,gy), Math.sqrt(d));//���·��21
        d = (gx-p22x)*(gx-p22x)+(gy-p22y)*(gy-p22y);//·��22����
        newRoadData.put(new Road_Data(gx,gy,p22x,p22y), Math.sqrt(d));//���·��22
        
        
//        System.out.println(newRoadData.toString());
        //������յ㲻��һ��·����
        if (!r1.equals(r2)) {
            issameroad = false;
            List<PointData> l11 = new ArrayList<>();
            List<PointData> l12 = new ArrayList<>();
            List<PointData> value1 = new ArrayList<>();
            for (Map.Entry<PointData, List<PointData>> entry : newMapData.entrySet()) {
            	//�޸ĵ�11·��
                if (entry.getKey().equals(pointData11)) {
                    l11.addAll(entry.getValue());
                    l11.remove(pointData12);
                    l11.add(new PointData(start.getFootx(), start.getFooty(), 0.0));
                }
            	//�޸ĵ�12·��
                if (entry.getKey().equals(pointData12)) {
                    l12.addAll(entry.getValue());
                    l12.remove(pointData11);
                    l12.add(new PointData(start.getFootx(), start.getFooty(), 0.0));
                }
            }
        	//���start��·��
            value1.add(pointData11);value1.add(pointData12);
            newMapData.put(pointData11,l11);
            newMapData.put(pointData12,l12);
            newMapData.put(new PointData(start.getFootx(),start.getFooty(),0.0)
                    ,value1);
            
            
            List<PointData> l21 = new ArrayList<>();
            List<PointData> l22 = new ArrayList<>();
            List<PointData> value2 = new ArrayList<>();
            for (Map.Entry<PointData, List<PointData>> entry : newMapData.entrySet()) {
                if (entry.getKey().equals(pointData21)) {
                    l21.addAll(entry.getValue());
                    l21.remove(pointData22);
                    l21.add(new PointData(goal.getFootx(), goal.getFooty(), 0.0));
                }
                if (entry.getKey().equals(pointData22)) {
                    l22.addAll(entry.getValue());
                    l22.remove(pointData21);
                    l22.add(new PointData(goal.getFootx(), goal.getFooty(), 0.0));
                }
            }
            
            value2.add(pointData21);value2.add(pointData22);
            newMapData.put(pointData21,l21);
            newMapData.put(pointData22,l22);
            newMapData.put(new PointData(goal.getFootx(),goal.getFooty(),0.0)
                    ,value2);
            
//            System.out.println(newMapData.toString());
        }
        else//����յ���һ��·���� �򲻸���
            issameroad = true;
    }

    public void initData(Map<Road_Data,Double> roadData,Map<PointData,List<PointData>> mapData){
        this.roadData = roadData;
        this.mapData = mapData;
    }

    private double heuristic(PointData pointData1,PointData pointData2)
    {
        return Math.abs(pointData1.getX() - pointData2.getX()) + Math.abs(pointData1.getY() - pointData2.getY());
    }


    //���·��������point
    //��������1��1�� ����2��2��
    //�������1��1�� ��1��2�� ��2��2��
    public List<PointData> GetPath(){

        PointData start0 = new PointData(start.getFootx(),start.getFooty(),0.0);
        PointData goal0 = new PointData(goal.getFootx(),goal.getFooty(),0.0);
		
        cost_so_far.put(start0,0.0);
        List<PointData> path = new ArrayList<>();
        while(!frontier.isEmpty())
        {
            PointData current = frontier.poll();
            double new_cost;
            double mx,my,MX,MY;
            if(current.equals(goal0))
            {
            	System.out.println("break");
                break;
            }
            List<PointData> neighbor = newMapData.get(current);
            System.out.println("neighborsize = "+ neighbor.size());
            Iterator<PointData> iter = neighbor.iterator();
            while(iter.hasNext()) {
                PointData next = (PointData) iter.next();
                if (current.getX() < next.getX()) {
                    mx = current.getX();
                    MX = next.getX();
                } else {
                    mx = next.getX();
                    MX = current.getX();
                }
                if (current.getY() < next.getY()) {
                    my = current.getY();
                    MY = next.getY();
                } else {
                    my = next.getY();
                    MY = current.getY();
                }
                Road_Data road = new Road_Data(mx,my,MX,MY);
                double cost1 = cost_so_far.get(current);
                double cost2 = newRoadData.get(road);
                new_cost = cost1 + cost2;
                if(!cost_so_far.containsKey(next)||new_cost < cost_so_far.get(next))
                {
                    cost_so_far.put(next,new_cost);
                    double priority = new_cost + heuristic(goal0, next);
                    next.setPriority(priority);
                    frontier.add(next);
                    came_from.put(next,current);
                }
            }
        }
//        System.out.println(came_from.toString());
        while(!goal0.equals(start0))
        {
            path.add(goal0);
            goal0 = came_from.get(goal0);
        }
        path.add(start0);
        return path;
    }

    public double getDistance() {
        double distance = 0;
        if (issameroad) {
            distance = Math.sqrt(Math.pow(start.getFootx() - goal.getFootx(), 2) +
                    Math.pow(start.getFooty() - goal.getFooty(), 2));
        }
        else {List<PointData> pointData = GetPath();
        System.out.println(pointData.toString());
            PointData pointData0 = pointData.get(0);
            for (int i=1;i<pointData.size();i++)
            {
            	PointData pointData1 = pointData.get(i);
            	Road_Data road = new Road_Data(pointData1.getX(),pointData1.getY(),pointData0.getX(),pointData0.getY());
                distance = distance + newRoadData.get(road);
                pointData0 = pointData1;
            }
        }
        return distance;
    }

}