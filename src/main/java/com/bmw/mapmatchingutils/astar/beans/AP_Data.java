package com.bmw.mapmatchingutils.astar.beans;


public class AP_Data implements Comparable<AP_Data>{
    private double x, y, h,b,r,a;
    private double rssi,rssi_cali;
    private String Mac;

    public AP_Data(double x, double y, double h, double b, double r, double a, double rssi, double rssi_cali, String mac) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.b = b;
        this.r = r;
        this.a = a;
        this.rssi = rssi;
        this.rssi_cali = rssi_cali;
        Mac = mac;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public AP_Data(double x, double y, double h, double b, double r, double rssi, double rssi_cali, String mac) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.b = b;
        this.r = r;
        this.rssi = rssi;
        this.rssi_cali = rssi_cali;
        Mac = mac;
    }

    public double getRssi_cali() {
        return rssi_cali;
    }

    public void setRssi_cali(double rssi_cali) {
        this.rssi_cali = rssi_cali;
    }

    public double getRssi() {
        return rssi;
    }

    public void setRssi(double rssi) {
        this.rssi = rssi;
    }

    public String getMac() {
        return Mac;
    }

    public void setMac(String mac) {
        Mac = mac;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
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

    public void setY(double y) {
        this.y = y;
    }


    @Override
    public String toString() {
        return "AP_Data{" +
                "r=" + r +
                '}';
    }

   
    @Override
    public int compareTo(AP_Data o) {
        if(this.r<o.r)return -1;
        else if(this.r==o.r)return 0;
        else return 1;
    }
}
