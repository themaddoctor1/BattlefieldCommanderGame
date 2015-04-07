/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.units.testUnits;

import engine.entities.units.Unit;
import engine.physics.Coordinate;

/**
 *
 * @author Christopher Hittner
 */
public class Hydra_Bot extends Unit{

    public Hydra_Bot(Coordinate coord, String nm) {
        super(coord, 5, (float) .5, null, new HydraBotBrain(nm));
    }
    
}
