/*
 * The Movable interface is used by all Objects that can move.
 */
package engine.entities.interfaces;

/**
 *
 * @author Christopher
 */
public interface Movable {
    
    /**
     * Moves Entities with this interface.
     * @param factor The factor to multiply the movement amount by
     */
    public void move(double factor);
    
}
