/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.game.scenarios;

import engine.entities.terrain.structures.*;
import engine.entities.units.soldiers.*;
import engine.game.*;
import engine.game.spawning.*;
import engine.physics.*;
import engine.world.*;

/**
 *
 * @author Christopher
 */
public class TestScenario extends Scenario {
    
    
    @Override
    protected void preloadProcedure(Level l) {
        SpawnManager.getSpawners().clear();
    }
    
    
    @Override
    protected void buildTerrain(Level l){
        l.getTerrain().add(new DropPodLandingPad(new Coordinate(15,0,15), "Test_Ally"));
        l.getTerrain().add(new DropPodLandingPad(new Coordinate(15,0,-15), "Test_Ally"));
        l.getTerrain().add(new DropPodLandingPad(new Coordinate(-15,0,15), "Test_Enemy"));
        l.getTerrain().add(new DropPodLandingPad(new Coordinate(-15,0,-15), "Test_Enemy"));
    }
    
    
    @Override
    protected void loadEntities(Level l) {
        //l.getUnits().add(new Infantry(new Coordinate(20,0,0),"Alice"));
        //l.getUnits().add(new Infantry(new Coordinate(0,0,20),"Bob"));
        //l.getUnits().add(new Infantry(new Coordinate(-20,0,0),"Chris"));
        //l.getUnits().add(new Infantry(new Coordinate(0,0,-20),"Dave"));
            
            
        for(int i = 0; i < l.getUnits().size(); i++)
            FactionManager.addFactionMember(l.getUnits().get(i).getName(), "Player");
    }

    @Override
    protected void addOpponents(Level l) {
        for(String s : FactionManager.getFactionNames())
            FactionManager.removeFaction(s);
        
        FactionManager.addFaction("Player", null, null);
        FactionManager.addFaction("Test_Enemy", null, null);
        FactionManager.addFaction("Test_Ally", null, null);
    }

    @Override
    protected void establishRelationships(Level l) {
        //FactionManager.setRelationship(0, 1, true);
        
        FactionManager.setRelationship("Player", "Test_Enemy", false); //This is actually useless in theory, but it's here just for in case.
        FactionManager.setRelationship("Player", "Test_Ally", true);
    }

    @Override
    protected void generateSpawners(Level l) {
        
        //SpawnManager.addSpawner(new DropPodSpawner(0, 0, 40, 5,"Test_Enemy"));
        //SpawnManager.addSpawner(new DropPodSpawner(0, 0, 40, 5,"Test_Ally"));
        //SpawnManager.addSpawner(new PeripheralSpawner(15, "Player"));
        
        SpawnManager.addSpawner(new PeripheralSpawner(60, "Test_Ally"));
        SpawnManager.addSpawner(new PeripheralSpawner(60, "Test_Enemy"));
    }

    
    

}
