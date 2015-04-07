/*
 * This Spawner spawns Drop Pods over the map.
 */
package engine.game;

import engine.entities.units.soldiers.*;
import engine.entities.units.testUnits.Drop_Pod;
import engine.physics.Coordinate;
import engine.world.LevelManager;

/**
 *
 * @author Christopher
 */
public class DropPodSpawner extends Spawner{
    private final int X, Y, R, RATE;
    private double time = 0;
    private static int count = 0;
    
    public DropPodSpawner(int x, int y, int radius, int time, byte id) {
        super(id);
        X = x;
        Y = y;
        R = radius;
        RATE = time;
    }

    @Override
    public void cycle(double factor) {
        
        if(time >= RATE){
            
            double angle = Math.random() * 2 * Math.PI;
            double dist = Math.random() * R;
            
            String name = count + "";
            count++;
            
            LevelManager.getLevel().addUnit(new Drop_Pod(
                            new Coordinate(X + dist*Math.cos(angle), 1000, Y + dist*Math.sin(angle))
                            ,"Pod-"+name
                            , new Infantry(new Coordinate(0,0,0), "INF-" + name)), factionID());
            
            FactionManager.addFactionMember("INF-"+name, factionID());
            
            time = 0;
            
        }
        time += factor;
    }
    
}
