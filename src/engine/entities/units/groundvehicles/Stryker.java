/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.units.groundvehicles;

import engine.entities.items.InventoryBuilder;
import engine.physics.Coordinate;

/**
 *
 * @author Christopher Hittner
 */
public class Stryker extends GroundVehicle {

    public Stryker(Coordinate coord, String name) {
        super(coord, 100000, (float)(3.5), InventoryBuilder.buildStrykerLoadout(), 25, 4, 11, 0, 0, true, name);
    }
    
}
