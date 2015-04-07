/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
