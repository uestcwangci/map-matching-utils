package com.bmw.mapmatchingutils.astar.beans;

public class Location_Data {
    private double x,y;

    public Location_Data(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public Location_Data() {
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Location_Data{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
