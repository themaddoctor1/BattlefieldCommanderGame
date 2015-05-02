/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.gui.overlays.displayelements;

import engine.gui.GUI;
import engine.gui.UnitSelection;
import engine.gui.WorldDrawer;
import engine.gui.overlays.GraphicImage;
import engine.world.LevelManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Christopher Hittner
 */
public class BattlefieldDisplay extends GraphicImage{
    
    public BattlefieldDisplay(){super();}
    
    @Override
    public void drawImage(int X, int Y, int WIDTH, int HEIGHT, Graphics2D g2) {
        
        GUI gui = GUI.getGUI();
        
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        WorldDrawer.drawWorld(g2, gui.getCamera());
        UnitSelection.drawSelection(g2, GUI.getGUI().getCamera(), LevelManager.getLevel());
        g2.setColor(Color.BLACK);
        
        //Center of screen crosshair
        g2.drawLine(gui.getCenterX(), gui.getCenterY() - 15, gui.getCenterX(), gui.getCenterY() + 15);
        g2.drawLine(gui.getCenterX() - 15,gui.getCenterY(),gui.getCenterX() + 15,gui.getCenterY());
        
        //Mouse crosshair
        try{
            int x = gui.getController().getMouseX(), y = gui.getController().getMouseY();
            g2.drawLine(x, y - 15, x, y + 15);
            g2.drawLine(x - 15, y, x + 15, y);
        } catch(NullPointerException e){
            
        }
        
        for(int i = 0; i < LevelManager.getEvents().size(); i++)
            g2.drawString(LevelManager.getEvents().get(LevelManager.getEvents().size() - i - 1), 15, 15*(i+1));
    }

}
