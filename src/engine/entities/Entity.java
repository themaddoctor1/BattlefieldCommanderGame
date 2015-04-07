/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities;

import engine.physics.Coordinate;
import engine.world.LevelManager;

/**
 *
 * @author Christopher Hittner
 */
public abstract class Entity {
    protected Coordinate coordinate;
    private long ID;
    
    /**
     * Creates an Entity.
     * @param coord An array with size 3.
     */
    public Entity(Coordinate coord){
        coordinate = coord;
        ID = LevelManager.assignID();
    }
    
    public abstract void cycle(double factor);
    public Coordinate getPosition(){ return coordinate; }
    public long getID(){ return ID;}
    
    public boolean equals(Entity other){
        return this.ID == other.ID;
    }
    
}
