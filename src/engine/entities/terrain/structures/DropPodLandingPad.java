/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.terrain.structures;

import engine.game.spawning.DropPodSpawner;
import engine.game.spawning.Spawner;
import engine.physics.Coordinate;
import engine.scripts.TimeLimitedScript;

/**
 *
 * @author Christopher Hittner
 */
public class DropPodLandingPad extends SpawnerBuilding{

    public DropPodLandingPad(Coordinate coord, String fac) {
        super(new Coordinate(coord.X(), .5, coord.Z()), defaultSize(), fac, null, new DropPodSpawner(0,0,0,5, fac));
    }
    
    private static double[] defaultSize(){
        double[] size = {6,0.2,6};
        return size;
    }

}
