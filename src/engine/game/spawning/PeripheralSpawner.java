/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.game.spawning;

import engine.entities.terrain.TerrainElement;
import engine.entities.units.helicopters.AH64_Apache;
import engine.entities.units.helicopters.Helicopter;
import engine.entities.units.soldiers.Infantry;
import engine.game.FactionManager;
import engine.gui.Camera;
import engine.gui.GUI;
import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.world.LevelManager;

/**
 *
 * @author Christopher Hittner
 */
public class PeripheralSpawner extends Spawner{
    
    private int RATE;
    private double time = 0;
    private static int count = 0;
    
    public PeripheralSpawner(int time, String id) {
        super(id);
        RATE = time;
    }

    @Override
    public void cycle(double factor) {
        requestSpawn();
        
        time += factor;
    }

    @Override
    public void requestSpawn() {
        if(time > RATE){
            String name = count + "";
            count++;
            
            Camera camera = GUI.getGUI().getCamera();
            
            double placementAngle = 2 * Math.PI * Math.random();
            double theta = Math.atan(Math.sqrt(Math.pow(GUI.getGUI().getCenterX(),2) + Math.pow(GUI.getGUI().getCenterY(),2)) / GUI.getGUI().getPixelsPerRadian());
            double minDist = Math.tan(theta) * camera.getPosition().Y();
            
            Coordinate coord = new Coordinate(camera.getPosition().X(), 50, camera.getPosition().Z());
            coord.addVector(new Vector(minDist * 4, placementAngle, 0));
            
            Helicopter helo = new AH64_Apache(coord, "HELO-" + name);
            helo.board(new Infantry(coord, "PILOT-" + name));
            
            LevelManager.getLevel().addUnit(helo, factionID());
            
            time = 0;
            
        }
    }

    @Override
    public void centerOn(TerrainElement elem) {
        throw new UnsupportedOperationException();
    }

}
