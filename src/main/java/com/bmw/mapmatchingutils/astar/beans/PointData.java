package com.bmw.mapmatchingutils.astar.beans;

import java.util.Objects;

public class PointData implements Comparable<PointData>{
    private double x,y,priority;

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    public PointData(double x, double y, double priority) {
        this.x = x;
        this.y = y;
        this.priority = priority;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "PointData{" +
                "x=" + x +
                ", y=" + y +
                ", priority=" + priority +
                '}';
    }

    //鎸夌収r鐨勫ぇ灏忎粠灏忓埌澶ф帓搴�
    @Override
    public int compareTo(PointData o) {
        if(this.priority<o.priority)return -1;
        else if(this.priority==o.priority)return 0;
        else return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointData pointData = (PointData) o;
        return Double.compare(pointData.x, x) == 0 &&
                Double.compare(pointData.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
