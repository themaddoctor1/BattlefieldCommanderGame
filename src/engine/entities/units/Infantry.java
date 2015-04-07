/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.entities.units;

import engine.entities.items.Inventory;
import engine.entities.items.InventoryBuilder;
import engine.entities.units.soldiers.Soldier;
import engine.physics.Coordinate;

/**
 *
 * @author Christopher
 */
public class Infantry extends Soldier{

    public Infantry(Coordinate coord, String name) {
        super(coord, InventoryBuilder.buildInfantryLoadout(), name);
    }
    
}
