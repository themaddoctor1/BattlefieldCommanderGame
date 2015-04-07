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
public class AH64_Apache extends Helicopter{

    public AH64_Apache(Coordinate coord, String name) {
        super(coord, 5000, 8, InventoryBuilder.buildAH64ApacheLoadout(), 2, 0, 0, true, 25, 80, name);
    }

}
