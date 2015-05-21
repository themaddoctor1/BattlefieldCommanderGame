/*
 * This class is meant to be a Factory Pattern-based class meant for providing
 * a central place for writing and grabbing your own GUIOverlay objects.
 */

package engine.gui.overlays;

import engine.gui.Controller;
import engine.gui.overlays.displayelements.BattlefieldDisplay;
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
        DisplayOverlay display = new DisplayOverlay();
        try{
            buttons.addButton(new UnitControlButton(width - 45, height - 95, 20, 20, new OrderScript("unload all units"), new SquareIcon(new ArrowIcon(Math.toRadians(0)))));
            buttons.addButton(new UnitControlButton(width - 105, height - 95, 20, 20, new OrderScript("ascend"), new SquareIcon(new ArrowIcon(Math.toRadians(90)))));
            buttons.addButton(new UnitControlButton(width - 75, height - 95, 20, 20, new OrderScript("descend"), new SquareIcon(new ArrowIcon(Math.toRadians(270)))));
            
            buttons.addButton(new UnitControlButton(width - 165, height - 95, 20, 20, new OrderScript("open fire"), new SquareIcon(new LetterIcon("A"))));
            buttons.addButton(new UnitControlButton(width - 135, height - 95, 20, 20, new OrderScript("hold fire"), new SquareIcon(new LetterIcon("H"))));
            
            display.addDisplay(new ImageFrame(new BattlefieldDisplay()));
            
        } catch(Exception e){
            e.printStackTrace();
        }
        gameOverlay.setButtonOverlay(buttons);
        gameOverlay.setDisplayOverlay(display);
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    
    
    public static GUIOverlay getPauseOverlay(){
        if(pauseOverlay == null){
            initPauseOverlay();
            return getPauseOverlay();
        }
        return pauseOverlay;
    }

    private static void initPauseOverlay(){
        pauseOverlay = new GUIOverlay();
        ButtonOverlay buttons = new ButtonOverlay();
        DisplayOverlay display = new DisplayOverlay();
        
        try{
            
            buttons.addButton(new DefaultButton(GUI.getGUI().getCenterX() - 60, GUI.getGUI().getCenterY() - 60, 150, 20, new PauseScript(false), new SquareIcon(new TextIcon("Resume Game"))));
            
            display.addDisplay(new ImageFrame(new TextIcon("Controls"), GUI.getGUI().getCenterX(), GUI.getGUI().getCenterY(), 18));
            Controller c = GUI.getGUI().getController();
            display.addDisplay(new ImageFrame(new TextIcon("Attack - " + (char) c.getKeyCodeOf(6)), GUI.getGUI().getCenterX(), GUI.getGUI().getCenterY() + 22, 14));
            display.addDisplay(new ImageFrame(new TextIcon("Board - " + (char) c.getKeyCodeOf(7)), GUI.getGUI().getCenterX(), GUI.getGUI().getCenterY() + 40, 14));
            display.addDisplay(new ImageFrame(new TextIcon("Pause - " + (char) c.getKeyCodeOf(8)), GUI.getGUI().getCenterX(), GUI.getGUI().getCenterY() + 58, 14));
            
            
            
        } catch(Exception e){
            e.printStackTrace();
        }
        pauseOverlay.setButtonOverlay(buttons);
        pauseOverlay.setDisplayOverlay(display);
    }
    
    
    
}
