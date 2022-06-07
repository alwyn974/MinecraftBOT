package re.alwyn974.minecraft.bot.entity;

/**
 * The EntityPos store the position of the bot and the view
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.4
 * @since 1.0.0
 */
public class EntityPos {

    private double x;
    private double y;
    private double z;
    private double yaw;
    private double pitch;

    /**
     * Set the entity position
     *
     * @param x     the x coordinate
     * @param y     the y coordinate
     * @param z     the z coordinate
     * @param yaw   the yaw view of the entity
     * @param pitch the pitch view of the entity
     */
    public EntityPos(double x, double y, double z, double yaw, double pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * Get the x coordinate
     *
     * @return the x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Get the y coordinate
     *
     * @return the y coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Get the z coordinate
     *
     * @return the z coordinate
     */
    public double getZ() {
        return z;
    }

    /**
     * Get the yaw view
     *
     * @return the yaw view
     */
    public double getYaw() {
        return yaw;
    }

    /**
     * Get the pitch view
     *
     * @return the pitch view
     */
    public double getPitch() {
        return pitch;
    }

    /**
     * Set the x coordinate of the entity
     *
     * @param x the x coordinate
     */
    public EntityPos setX(double x) {
        this.x = x;
        return this;
    }

    /**
     * Set the y coordinate of the entity
     *
     * @param y the y coordinate
     */
    public EntityPos setY(double y) {
        this.y = y;
        return this;
    }

    /**
     * Set the z coordinate of the entity
     *
     * @param z the z coordinate
     */
    public EntityPos setZ(double z) {
        this.z = z;
        return this;
    }

    /**
     * Increment the x coordinate of the entity
     * @param x the x coordinate
     */
    public EntityPos addX(double x) {
        this.x += x;
        return this;
    }

    /**
     * Increment the y coordinate of the entity
     * @param y the y coordinate
     */
    public EntityPos addY(double y) {
        this.y += y;
        return this;
    }

    /**
     * Increment the z coordinate of the entity
     * @param z the z coordinate
     */
    public EntityPos addZ(double z) {
        this.z += z;
        return this;
    }

    /**
     * Set the yaw view of the entity
     *
     * @param yaw the yaw view
     */
    public EntityPos setYaw(double yaw) {
        this.yaw = yaw;
        return this;
    }

    /**
     * Set the pitch view of the entity
     *
     * @param pitch the pitch view
     */
    public EntityPos setPitch(double pitch) {
        this.pitch = pitch;
        return this;
    }

    @Override
    public String toString() {
        return String.format("[%g %g %g] Yaw: %g Pitch %g", x, y, z, yaw, pitch);
    }

}
