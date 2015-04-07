/*
 * This class is meant to be a Factory Pattern-based class meant for providing
 * a central place for writing and grabbing your own GUIOverlay objects.
 */

package engine.gui.overlays;

import engine.gui.GUI;
import engine.gui.overlays.buttons.*;
import engine.gui.overlays.icons.*;

/**
 *
 * @author Christopher Hittner
 */
public class GUIOverlayBuilder {
    private static GUIOverlay gameOverlay = null;
    
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
        buttons.addButton(new UnitControlButton(width - 45, height - 95, 20, 20, "unload all units", new SquareIcon(new ArrowIcon(Math.toRadians(0)))));
        buttons.addButton(new UnitControlButton(width - 105, height - 95, 20, 20, "ascend", new SquareIcon(new ArrowIcon(Math.toRadians(90)))));
        buttons.addButton(new UnitControlButton(width - 75, height - 95, 20, 20, "descend", new SquareIcon(new ArrowIcon(Math.toRadians(270)))));
        } catch(Exception e){
            e.printStackTrace();
        }
        gameOverlay.setButtonOverlay(buttons);
    }
    
}
