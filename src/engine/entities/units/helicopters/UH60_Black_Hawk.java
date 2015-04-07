/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.units.helicopters;

import engine.entities.items.Inventory;
import engine.entities.items.InventoryBuilder;
import engine.physics.Coordinate;

/**
 *
 * @author Christopher Hittner
 */
public class UH60_Black_Hawk extends Helicopter{

    public UH60_Black_Hawk(Coordinate coord, String name) {
        super(coord, 2500, 9, InventoryBuilder.buildUH60BlackHawkLoadout(), 4, 0, 0, true, 15, 75, name);
    }

}
