/*
 * A DropPodSpawner spawns Drop Pods over the map.
 */
package engine.game.spawning;

import engine.entities.terrain.TerrainElement;
import engine.game.spawning.Spawner;
import engine.entities.units.soldiers.*;
import engine.entities.units.testUnits.Drop_Pod;
import engine.game.FactionManager;
import engine.physics.Coordinate;
import engine.world.LevelManager;

/**
 *
 * @author Christopher
 */
public class DropPodSpawner extends Spawner{
    private int X, Y = 0, Z, R, RATE;
    private double time = 0;
    private static int count = 0;
    
    public DropPodSpawner(int x, int z, int radius, int time, String id) {
        super(id);
        X = x;
        Z = z;
        R = radius;
        RATE = time;
    }

    @Override
    public void cycle(double factor) {
        requestSpawn();
        
        time += factor;
    }

    @Override
    public void centerOn(TerrainElement element) {
        X = (int) element.getPosition().X();
        Y = (int)(element.getPosition().Y() + element.getSize()[1]/2.0);
        Z = (int) element.getPosition().Z();
        R = (int) (Math.min(element.getSize()[0], element.getSize()[2])/2.0);
    }

    @Override
    public void requestSpawn() {
        
        if(time >= RATE){
            
            double angle = Math.random() * 2 * Math.PI;
            double dist = Math.random() * R;
            
            String name = count + "";
            count++;
            
            LevelManager.getLevel().addUnit(new Drop_Pod(
                            new Coordinate(X + dist*Math.cos(angle), 1000, Z + dist*Math.sin(angle))
                            ,"Pod-"+name
                            , new Infantry(new Coordinate(0,0,0), "INF-" + name)), factionID());
            
            FactionManager.addFactionMember("INF-"+name, factionID());
            
            time = 0;
            
        }
    }
    
}
