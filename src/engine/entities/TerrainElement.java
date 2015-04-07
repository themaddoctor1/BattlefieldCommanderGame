/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities;

import engine.entities.units.Unit;
import engine.physics.Coordinate;
import engine.world.LevelManager;

/**
 *
 * @author Christopher Hittner
 */
public class TerrainElement extends Entity{
    private double[] size;
    
    public TerrainElement(Coordinate coord, double[] dimensions) {
        super(coord);
        size = dimensions;
    }
    
    @Override
    public void cycle(double factor) {
        
    }
    
    public double[] getSize(){ return size; }

    public boolean collidingWith(Unit u) {
        return ((Math.abs(u.getPosition().X() - getPosition().X()) < u.getSize() + (size[0]/2.0)) && (Math.abs(u.getPosition().Y() - getPosition().Y()) < u.getSize() + (size[1]/2.0)) && (Math.abs(u.getPosition().Z() - getPosition().Z()) < u.getSize() + (size[2]/2.0)));
    }


}
