package me.herrphoenix.obamabot.util;

/**
 * @author HerrPhoenix
 */
public class ObamaVector {
    private double x, y, z;

    public ObamaVector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ObamaVector multiply(double factor) {
        return new ObamaVector(x * factor, y * factor, z * factor);
    }

    public ObamaVector add(ObamaVector vec) {
        return new ObamaVector(x + vec.getX(), y + vec.getY(), z + vec.getZ());
    }

    public ObamaVector subtract(ObamaVector vec) {
        return new ObamaVector(x - vec.getX(), y - vec.getY(), z - vec.getZ());
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
