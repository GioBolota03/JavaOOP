package com.epam.rd.autotasks.figures;

class Triangle extends Figure{
    private Point a;
    private Point b;
    private Point c;

    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double area() {
        double x = (a.getX() * (b.getY() - c.getY()) + b.getX() *(c.getY() - a.getY()) + c.getX() * (a.getY() - b.getY()));
        return Math.abs(x)/2.0;
    }

    @Override
    public String pointsToString() {
        return "(" + a.getX() + "," + a.getY() + ")" +
                "(" + b.getX() + "," + b.getY() + ")" +
                "(" + c.getX() + "," + c.getY() + ")";
    }

    @Override
    public Point leftmostPoint() {
        Point left = a;
        if(b.getX() < left.getX())
            left = b;
        if(c.getX() < left.getX())
            left = c;
        return left;
    }
}
