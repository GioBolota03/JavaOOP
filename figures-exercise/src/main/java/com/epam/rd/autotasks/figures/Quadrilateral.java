package com.epam.rd.autotasks.figures;

class Quadrilateral extends Figure{
    private Point a;
    private Point b;
    private Point c;
    private Point d;

    public Quadrilateral(Point a, Point b, Point c, Point d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public double area() {
        double x = a.getX() * b.getY() +b.getX() * c.getY() + c.getX() * d.getY() +d.getX() * a.getY() - a.getY() * b.getX() - b.getY() * c.getX() - c.getY() * d.getX() - d.getY() * a.getX();;
        return Math.abs(x)/2.0;
    }

    @Override
    public String pointsToString() {
        return "(" + a.getX() + "," + a.getY() + ")" +
                "(" + b.getX() + "," + b.getY() + ")" +
                "(" + c.getX() + "," + c.getY() + ")" +
                "(" + d.getX() + "," + d.getY() + ")";
    }

    @Override
    public Point leftmostPoint() {
        Point left = a;
        if (b.getX() < left.getX()) left = b;
        if (c.getX() < left.getX()) left = c;
        if (d.getX() < left.getX()) left = d;
        return left;
    }
}
