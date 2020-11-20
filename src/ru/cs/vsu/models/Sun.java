package ru.cs.vsu.models;

import ru.cs.vsu.points.RealPoint;

public class Sun {
    private RealPoint o;
    private double rOfSun;
    private double rOfRay;
    private int l;

    public Sun(RealPoint o, double rOfSun, double rOfRay, int l) {
        this.o = o;
        this.rOfSun = rOfSun;
        this.rOfRay = rOfRay;
        this.l = l;
    }

    public Sun(double x, double y, double rOfSun, double rOfRay, int l) {
        o = new RealPoint(x,y);
        this.rOfSun = rOfSun;
        this.rOfRay = rOfRay;
        this.l = l;
    }

    public RealPoint getO() {
        return o;
    }

    public void setrOfSun(double rOfSun) {
        this.rOfSun = rOfSun;
    }

    public void setrOfRay(double rOfRay) {
        this.rOfRay = rOfRay;
    }

    public double getrOfSun() {
        return rOfSun;
    }

    public double getrOfRay() {
        return rOfRay;
    }

    public int getL() {
        return l;
    }

    public RealPoint setO(double x, double y) {

        return  new RealPoint(x,y);

    }


}
