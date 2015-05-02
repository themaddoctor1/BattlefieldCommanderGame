/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.game.spawning;

import engine.entities.terrain.TerrainElement;
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

    private int X, Y = 0, Z, R, RATE;
    private double time = 0;
    private static int count = 0;
    
    public SpontaneousSpawner(int x, int z, int radius, int time, String id) {
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
            
            LevelManager.getLevel().addUnit(new Infantry(
                            new Coordinate(X + dist*Math.cos(angle), Y, Z + dist*Math.sin(angle))
                            ,"INF-"+name), factionID());
            
            FactionManager.addFactionMember("INF-"+name, factionID());
            
            time = 0;
            
        }
    }
    
}
