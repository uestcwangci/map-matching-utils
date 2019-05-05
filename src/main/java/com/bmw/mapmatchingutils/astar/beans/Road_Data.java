package com.bmw.mapmatchingutils.astar.beans;
import java.util.Objects;

public class Road_Data {
    private int id;
    private double A,B,C;
    private double point1x,point1y;
    private double point2x,point2y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Road_Data road_data = (Road_Data) o;


        boolean flag1 = Double.compare(road_data.point1x, point1x) == 0 &&
                Double.compare(road_data.point1y, point1y) == 0 &&
                Double.compare(road_data.point2x, point2x) == 0 &&
                Double.compare(road_data.point2y, point2y) == 0;

        return flag1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(point1x, point1y, point2x, point2y);
    }

    public Road_Data(int id, double point1x, double point1y, double point2x, double point2y) {
        this.id = 1;
        this.point1x = point1x;
        this.point1y = point1y;
        this.point2x = point2x;
        this.point2y = point2y;
    }

    public Road_Data(double point1x, double point1y, double point2x, double point2y) {
        this.point1x = point1x;
        this.point1y = point1y;
        this.point2x = point2x;
        this.point2y = point2y;
    }

    @Override
    public String toString() {
        return "\n"+"Road_Data{" +
                "A=" + A +
                ", B=" + B +
                ", C=" + C +
                ", point1x=" + point1x +
                ", point1y=" + point1y +
                ", point2x=" + point2x +
                ", point2y=" + point2y +
                '}';
    }
    public Road_Data() {
    }

    public Road_Data(int id, double a, double b, double c, double point1x, double point1y, double point2x, double point2y) {
        this.id = 1;
        A = a;
        B = b;
        C = c;
        this.point1x = point1x;
        this.point1y = point1y;
        this.point2x = point2x;
        this.point2y = point2y;
    }

    public Road_Data(double a, double b, double c, double point1x, double point1y, double point2x, double point2y) {
        A = a;
        B = b;
        C = c;
        this.point1x = point1x;
        this.point1y = point1y;
        this.point2x = point2x;
        this.point2y = point2y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getA() {
        return A;
    }

    public void setA(double a) {
        A = a;
    }

    public double getB() {
        return B;
    }

    public void setB(double b) {
        B = b;
    }

    public double getC() {
        return C;
    }

    public void setC(double c) {
        C = c;
    }

    public double getPoint1x() {
        return point1x;
    }

    public void setPoint1x(double point1x) {
        this.point1x = point1x;
    }

    public double getPoint1y() {
        return point1y;
    }

    public void setPoint1y(double point1y) {
        this.point1y = point1y;
    }

    public double getPoint2x() {
        return point2x;
    }

    public void setPoint2x(double point2x) {
        this.point2x = point2x;
    }

    public double getPoint2y() {
        return point2y;
    }

    public void setPoint2y(double point2y) {
        this.point2y = point2y;
    }
}
