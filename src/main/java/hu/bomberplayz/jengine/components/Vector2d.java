package hu.bomberplayz.jengine.components;

public class Vector2d {
    private double x;
    private double y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d() {
        this(0, 0);
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

    public void add(Vector2d vector) {
        this.x += vector.getX();
        this.y += vector.getY();
    }

    public void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void subtract(Vector2d vector) {
        this.x -= vector.getX();
        this.y -= vector.getY();
    }

    public void subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
    }

    public void multiply(Vector2d vector) {
        this.x *= vector.getX();
        this.y *= vector.getY();
    }

    public void multiply(double x, double y) {
        this.x *= x;
        this.y *= y;
    }

    public void divide(Vector2d vector) {
        this.x /= vector.getX();
        this.y /= vector.getY();
    }

    public void divide(double x, double y) {
        this.x /= x;
        this.y /= y;
    }

    public void set(Vector2d vector) {
        this.x = vector.getX();
        this.y = vector.getY();
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    public double getLengthSquared() {
        return x * x + y * y;
    }

    public double getAngle() {
        return Math.atan2(y, x);
    }

    public void setLength(double length) {
        double angle = getAngle();
        this.x = Math.cos(angle) * length;
        this.y = Math.sin(angle) * length;
    }

    public void setAngle(double angle) {
        double length = getLength();
        this.x = Math.cos(angle) * length;
        this.y = Math.sin(angle) * length;
    }

    public void rotate(double angle) {
        double length = getLength();
        this.x = Math.cos(getAngle() + angle) * length;
        this.y = Math.sin(getAngle() + angle) * length;
    }

    public void rotate(double angle, Vector2d center) {
        double length = getLength();
        this.x = Math.cos(getAngle() + angle) * length + center.getX();
        this.y = Math.sin(getAngle() + angle) * length + center.getY();
    }




}
