/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.units.groundvehicles;

import engine.entities.items.Inventory;
import engine.physics.Coordinate;

/**
 *
 * @author Christopher Hittner
 */
public class Humvee extends GroundVehicle{

    public Humvee(Coordinate coord, String name) {
        super(coord, 2500, 2, null, 25, 5, 4, 0, 0, true, name);
    }

}
