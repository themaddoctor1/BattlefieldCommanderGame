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
public class M1_Abrams extends GroundVehicle {

    public M1_Abrams(Coordinate coord, String name) {
        super(coord, 100000, (float)(3.5), InventoryBuilder.buildM1AbramsLoadout(), 15, 3, 4, 0, 0, true, name);
    }
    
}
