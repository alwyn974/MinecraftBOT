package me.herrphoenix.obamabot.util;

/**
 * @author HerrPhoenix
 */
public class Vector {
    private double x, y, z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector multiply(double factor) {
        return new Vector(x * factor, y * factor, z * factor);
    }

    public Vector add(Vector vec) {
        return new Vector(x + vec.getX(), y + vec.getY(), z + vec.getZ());
    }

    public Vector subtract(Vector vec) {
        return new Vector(x - vec.getX(), y - vec.getY(), z - vec.getZ());
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
