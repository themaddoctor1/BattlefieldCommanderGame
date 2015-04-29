/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.game.scenarios;

import ai.main.CentralBrain;
import engine.entities.Entity;
import engine.world.Level;
import engine.world.LevelManager;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public abstract class Scenario {
    
    public final void loadScenario(){
        Level level = new Level();
        buildTerrain(level);
        addOpponents(level);
        loadEntities(level);
        establishRelationships(level);
        generateSpawners(level);
        LevelManager.setLevel(level);
    }
    
    protected abstract void buildTerrain(Level l);
    protected abstract void loadEntities(Level l);
    protected abstract void addOpponents(Level l);
    protected abstract void establishRelationships(Level l);
    protected abstract void generateSpawners(Level l);
    

    
}
