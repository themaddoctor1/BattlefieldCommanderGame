/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
