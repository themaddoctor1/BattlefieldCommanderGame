/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.terrain.structures;

import engine.entities.terrain.TerrainElement;
import engine.game.spawning.SpawnManager;
import engine.game.spawning.Spawner;
import engine.physics.Coordinate;
import engine.scripts.TimeLimitedScript;

/**
 *
 * @author Christopher Hittner
 */
public class SpawnerBuilding extends Building{
    
    private Spawner spawner;
    
    public SpawnerBuilding(Coordinate coord, double[] dimensions, String fac, TimeLimitedScript script, Spawner spawn) {
        super(coord, dimensions, fac, script);
        spawn.centerOn(new TerrainElement(coord, dimensions));
        SpawnManager.addSpawner(spawn);
        spawner = spawn;
    }

    @Override
    public void changeOwnerTo(String fac) {
        super.changeOwnerTo(fac); //To change body of generated methods, choose Tools | Templates.
        spawner.setOwner(fac);
    }
    
    

}
