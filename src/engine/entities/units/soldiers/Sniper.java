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
 * @author Christopher Hittner
 */
public class Sniper extends Soldier{

    public Sniper(Coordinate coord, Inventory i, String name) {
        super(coord, InventoryBuilder.buildSniperLoadout(), name);
    }

}
