/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.entities.units.soldiers;

import engine.entities.items.Inventory;
import engine.entities.items.InventoryBuilder;
import engine.physics.Coordinate;

/**
 *
 * @author Christopher
 */
public class Grenadier extends Soldier{

    public Grenadier(Coordinate coord, String name) {
        super(coord, InventoryBuilder.buildDemolitionsLoadout(), name);
    }
    
}
