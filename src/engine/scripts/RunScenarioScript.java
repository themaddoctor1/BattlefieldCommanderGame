/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.scripts;

import engine.game.scenarios.Scenario;
import engine.world.LevelManager;

/**
 *
 * @author Christopher
 */
public class RunScenarioScript extends EncapsulatedScript{
    
    Scenario scenario;
    
    public RunScenarioScript(Scenario s){
        scenario = s;
    }
    
    @Override
    protected void script(String[] params) {
        if(LevelManager.getRunStatus())
            LevelManager.stopSimulation();
        scenario.loadScenario();
        LevelManager.startSimulation();
    }
    
}
