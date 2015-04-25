/*
 * This class is meant to be a Factory Pattern-based class meant for providing
 * a central place for writing and grabbing your own GUIOverlay objects.
 */

package engine.gui.overlays;

import engine.gui.GUI;
import engine.gui.overlays.buttons.*;
import engine.gui.overlays.icons.*;
import engine.scripts.OrderScript;
import engine.scripts.PauseScript;

/**
 *
 * @author Christopher Hittner
 */
public class GUIOverlayBuilder {
    private static GUIOverlay gameOverlay = null;
    private static GUIOverlay pauseOverlay = null;
    
    public static GUIOverlay getGameOverlay(int width, int height){
        if(gameOverlay == null){
            initGameOverlay(width,height);
            return getGameOverlay(width,height);
        }
        return gameOverlay;
    }

    private static void initGameOverlay(int width, int height){
        gameOverlay = new GUIOverlay();
        ButtonOverlay buttons = new ButtonOverlay();
        try{
            buttons.addButton(new UnitControlButton(width - 45, height - 95, 20, 20, new OrderScript("unload all units"), new SquareIcon(new ArrowIcon(Math.toRadians(0)))));
            buttons.addButton(new UnitControlButton(width - 105, height - 95, 20, 20, new OrderScript("ascend"), new SquareIcon(new ArrowIcon(Math.toRadians(90)))));
            buttons.addButton(new UnitControlButton(width - 75, height - 95, 20, 20, new OrderScript("descend"), new SquareIcon(new ArrowIcon(Math.toRadians(270)))));
            
            buttons.addButton(new UnitControlButton(width - 165, height - 95, 20, 20, new OrderScript("open fire"), new SquareIcon(new LetterIcon("A"))));
            buttons.addButton(new UnitControlButton(width - 135, height - 95, 20, 20, new OrderScript("hold fire"), new SquareIcon(new LetterIcon("H"))));
            
            
        } catch(Exception e){
            e.printStackTrace();
        }
        gameOverlay.setButtonOverlay(buttons);
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    
    
    public static GUIOverlay getPauseOverlay(int width, int height){
        if(pauseOverlay == null){
            initPauseOverlay(width,height);
            return getPauseOverlay(width,height);
        }
        return pauseOverlay;
    }

    private static void initPauseOverlay(int width, int height){
        pauseOverlay = new GUIOverlay();
        ButtonOverlay buttons = new ButtonOverlay();
        try{
            
            
            buttons.addButton(new DefaultButton(GUI.getGUI().getCenterX() - 60, GUI.getGUI().getCenterY() - 60, 150, 20, new PauseScript(false), new SquareIcon(new TextIcon("Resume Game"))));
            
            
        } catch(Exception e){
            e.printStackTrace();
        }
        pauseOverlay.setButtonOverlay(buttons);
    }
    
    
    
}
