/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.scripts;

import ai.main.AI_Manager;
import ai.main.CentralBrain;
import engine.gui.GUI;
import engine.gui.overlays.GUIOverlayBuilder;
import engine.world.LevelManager;

/**
 *
 * @author Christopher Hittner
 */
public class PauseScript extends EncapsulatedScript{
    
    private boolean pause;
    
    public PauseScript(boolean shouldPause) {
        pause = shouldPause;
    }

    @Override
    protected void script(String[] params) {
        if(pause){
            LevelManager.stopSimulation();
            GUI.getGUI().setGUIOverlay(GUIOverlayBuilder.getPauseOverlay(GUI.getGUI().getWidth(), GUI.getGUI().getHeight()));
            for(CentralBrain cb : AI_Manager.getBrains())
                cb.pause();
        } else {
            LevelManager.startSimulation();
            GUI.getGUI().setGUIOverlay(GUIOverlayBuilder.getGameOverlay(GUI.getGUI().getWidth(), GUI.getGUI().getHeight()));
            for(CentralBrain cb : AI_Manager.getBrains())
                cb.activate();
        }
    }

}
