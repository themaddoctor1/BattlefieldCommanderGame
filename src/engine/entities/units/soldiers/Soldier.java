/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.units.soldiers;

import engine.entities.interfaces.brains.SoldierBrain;
import engine.entities.items.Inventory;
import engine.entities.units.Unit;
import engine.physics.Coordinate;

/**
 *
 * @author Christopher Hittner
 */
public class Soldier extends Unit{
    
    /**
     * This constructor creates a basic Soldier.
     * @param coord
     * @param i
     * @param name
     */
    public Soldier(Coordinate coord, Inventory i, String name) {
        super(coord, 20, 1, i, new SoldierBrain(name));
    }
    
    /**
     * This constructor allows for a different brain to be used when creating, for instance, a Sniper.
     * @param coord
     * @param i
     * @param nm
     * @param u
     */
    public Soldier(Coordinate coord, Inventory i, String nm, SoldierBrain u){
        super(coord, 20, 1, i, u);
    }
    
}
