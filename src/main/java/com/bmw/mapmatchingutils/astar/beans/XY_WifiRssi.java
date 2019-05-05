package com.bmw.mapmatchingutils.astar.beans;

import java.util.LinkedHashMap;
import java.util.Map;

public class XY_WifiRssi {
	private double x;
	private double y;
	private Map<String, Double> wifiMap = new LinkedHashMap<>();
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
	public Map<String, Double> getWifiMap() {
		return wifiMap;
	}
	public void setWifiMap(Map<String, Double> wifiMap) {
		this.wifiMap = wifiMap;
	}
	public XY_WifiRssi(double x, double y, Map<String, Double> wifiMap) {
		super();
		this.x = x;
		this.y = y;
		this.wifiMap = wifiMap;
	}
	@Override
	public String toString() {
		return "XY_WifiRssi [x=" + x + ", y=" + y + ", wifiMap=" + wifiMap + "]"+"\n";
	}

}
