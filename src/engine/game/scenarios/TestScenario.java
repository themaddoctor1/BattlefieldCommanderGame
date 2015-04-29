/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.game.scenarios;

import engine.entities.units.soldiers.Infantry;
import engine.game.FactionManager;
import engine.game.spawning.DropPodSpawner;
import engine.game.spawning.SpawnManager;
import engine.physics.Coordinate;
import engine.world.Level;
import engine.world.LevelManager;

/**
 *
 * @author Christopher
 */
public class TestScenario extends Scenario {
    
    
    @Override
    protected void buildTerrain(Level l){
        
    }
    
    @Override
    protected void loadEntities(Level l) {
        l.getUnits().add(new Infantry(new Coordinate(20,0,0),"Alice"));
        l.getUnits().add(new Infantry(new Coordinate(0,0,20),"Bob"));
        l.getUnits().add(new Infantry(new Coordinate(-20,0,0),"Chris"));
        l.getUnits().add(new Infantry(new Coordinate(0,0,-20),"Dave"));
            
            
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
        SpawnManager.getSpawners().clear();
        SpawnManager.addSpawner(new DropPodSpawner(0, 0, 40, 5,"Test_Enemy"));
        SpawnManager.addSpawner(new DropPodSpawner(0, 0, 40, 5,"Test_Ally"));
    }
    
    

}
