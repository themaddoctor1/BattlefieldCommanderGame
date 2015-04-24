/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.game.spawning;

import engine.entities.units.soldiers.Infantry;
import engine.entities.units.testUnits.Drop_Pod;
import engine.game.FactionManager;
import engine.physics.Coordinate;
import engine.world.LevelManager;

/**
 *
 * @author Christopher
 */
public class SpontaneousSpawner extends Spawner{

    private final int X, Y, R, RATE;
    private double time = 0;
    private static int count = 0;
    
    public SpontaneousSpawner(int x, int y, int radius, int time, String id) {
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
            
            LevelManager.getLevel().addUnit(new Infantry(
                            new Coordinate(X + dist*Math.cos(angle), 0, Y + dist*Math.sin(angle))
                            ,"INF-"+name), factionID());
            
            FactionManager.addFactionMember("INF-"+name, factionID());
            
            time = 0;
            
        }
        time += factor;
    }
    
    
}
