/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.main;

import engine.entities.*;
import engine.entities.units.helicopters.*;
import engine.entities.units.groundvehicles.*;
import engine.entities.units.soldiers.*;
import engine.entities.units.*;
import engine.entities.units.helicopters.AH64_Apache;
import engine.entities.units.testUnits.*;
import engine.game.*;
import engine.gui.*;
import engine.physics.*;
import engine.world.*;
import java.util.ArrayList;

/**
 *
 * @author Christopher Hittner
 */
public class Main {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //LevelManager.getLevel().getTerrain().add(new TerrainElement(new Coordinate(0,0,0), dim));
        try{
            LevelManager.getLevel().getUnits().add(new Grenadier(new Coordinate(20,0,0),"Alice"));
            LevelManager.getLevel().getUnits().add(new Grenadier(new Coordinate(0,0,20),"Bob"));
            LevelManager.getLevel().getUnits().add(new Grenadier(new Coordinate(-20,0,0),"Chris"));
            LevelManager.getLevel().getUnits().add(new Grenadier(new Coordinate(0,0,-20),"Dave"));
            //LevelManager.getLevel().getUnits().add(new Infantry(new Coordinate(4,61,4),"Edward"));
            //LevelManager.getLevel().getUnits().add(new Stryker(new Coordinate(-4,65,-4), "Rhino"));
            
            //Infantry passenger = new Infantry(new Coordinate(0,0,0),"Frank");
            //FactionManager.addFactionMember(passenger.getName(), (byte)(0));
            //LevelManager.getLevel().getUnits().add(new DropPod(new Coordinate(30,1000,0), "Viking", passenger));
            
            SpawnManager.addSpawner(new DropPodSpawner(0, 0, 40, 5,"1"));
            
        } catch(Exception e){e.printStackTrace();}
        //LevelManager.getLevel().getUnits().add(new AH64_Apache(new Coordinate(0,80,0), "Hunter"));
        /*
        LevelManager.getLevel().getUnits().add(new Aircraft(new Coordinate(0,40,-50),2000, 3, null, 0, 0, 0,false, 10, 400, 50, Math.PI, "Hawk"));
        */
        
        //LevelManager.getLevel().getUnits().add(new Hydra_Bot(new Coordinate(-20, 0, -20), "Hydra"));
        
        for(int i = 0; i < LevelManager.getLevel().getUnits().size(); i++)
            FactionManager.addFactionMember(LevelManager.getLevel().getUnits().get(i).getName(), "0");
        //FactionManager.setRelationship(0, 1, true);
        
        FactionManager.addFaction("1", null, null);
        FactionManager.setRelationship("0", "1", false);
        
        //double[] size = {15,60,15};
        //LevelManager.getLevel().getTerrain().add(new TerrainElement(new Coordinate(0,size[1]/2,0),size));
        
        LevelManager.startSimulation();
        
        while(true){
            GUI.getGUI().redraw();
        }
    }
    
}
