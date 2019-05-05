package com.bmw.mapmatchingutils.astar.beans;


import javax.annotation.Nonnull;

public class Foot_Data implements Comparable<Foot_Data>{
    private double footx,footy,distance,scale;
    private Road_Data road_data;

    public Foot_Data(double footx, double footy, double distance) {
        this.footx = footx;
        this.footy = footy;
        this.distance = distance;
    }

    public Foot_Data(double footx, double footy, double distance, double scale, Road_Data road_data) {
        this.footx = footx;
        this.footy = footy;
        this.distance = distance;
        this.scale = scale;
        this.road_data = road_data;
    }

    public Road_Data getRoad_data() {
        return road_data;
    }

    public void setRoad_data(Road_Data road_data) {
        this.road_data = road_data;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public Foot_Data(double footx, double footy, double distance, Road_Data road_data) {
        this.footx = footx;
        this.footy = footy;
        this.distance = distance;
        this.road_data = road_data;
    }

    @Override
    public String toString() {
        return "Foot_Data{" +
                "footx=" + footx +
                ", footy=" + footy +
                ", distance=" + distance +
                '}';
    }

    public double getFootx() {
        return footx;
    }

    public void setFootx(double footx) {
        this.footx = footx;
    }

    public double getFooty() {
        return footy;
    }

    public void setFooty(double footy) {
        this.footy = footy;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(@Nonnull Foot_Data o) {
        if(this.distance<o.distance)return -1;
        else if(this.distance==o.distance)return 0;
        else return 1;
    }
}
