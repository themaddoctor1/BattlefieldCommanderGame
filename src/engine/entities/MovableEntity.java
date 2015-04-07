/*
 * The MovableEntity class's purpose is to allow for Entities that move. 'Nuff said.
 */

package engine.entities;

import engine.entities.interfaces.Movable;
import engine.physics.Coordinate;

/**
 *
 * @author Christopher Hittner
 */
public abstract class MovableEntity extends Entity implements Movable{
    
    public MovableEntity(Coordinate coord) {
        super(coord);
    }

    @Override
    public abstract void cycle(double factor);

    @Override
    public abstract void move(double factor);

}
