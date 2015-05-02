/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.gui;

import engine.entities.units.Unit;
import engine.game.FactionManager;
import engine.physics.Coordinate;
import engine.world.Level;
import engine.world.LevelManager;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Christopher Hittner
 */
public class UnitSelection {
    private static ArrayList<String> selectedUnits = new ArrayList<>();
    
    public static void shiftClick(double x, double y){
        selectedUnits.addAll(getClickedUnits(x,y));
    }
    
    public static ArrayList<String> getClickedUnits(double x, double y){
        
        ArrayList<String> result = new ArrayList<>();
        
        try{
            Level l = LevelManager.getLevel();
            Camera c = GUI.getGUI().getCamera();
            for(int i = 0; i < l.getUnits().size(); i++){
                Unit u = l.getUnits().get(i);
                
                int[] position = c.getPlanarCoordinate(u.getPosition());
                
                int radius = (int)(GUI.getGUI().getPixelsPerRadian() * Math.asin(u.getSize()/Coordinate.relativeDistance(c.getPosition(), u.getPosition())));
                
                //Mouse hints for Units
                if(Math.sqrt(Math.pow(position[0] - GUI.getGUI().getController().getMouseX(),2) + Math.pow(position[1] - GUI.getGUI().getController().getMouseY(),2)) < radius){
                    result.add(u.getName());
                }
                
            }
        } catch(NullPointerException npe){
            
        }
        return result;
    }
    
    public static void click(double x, double y){
        selectedUnits.clear();
        shiftClick(x,y);
    }
    
    public static ArrayList<String> getSelectedUnits(){ return selectedUnits;}
    
    
    public static void drawSelection(Graphics g, Camera c, Level l){
        Graphics2D g2 = (Graphics2D) g;
        for(int i = 0; i < l.getUnits().size(); i++){
            Unit u = l.getUnits().get(i);
            
            String factID = FactionManager.getFactionOf(u.getName());
                
            if("Player".equals(factID)){
                //Player's Unit
                g2.setColor(new Color(0,0,255,192));
            } else /*if(!"Player".equals(factID))*/{
                if(FactionManager.getRelationship("Player",factID))
                    g2.setColor(new Color(0,255,0,192));
                else
                    g2.setColor(new Color(255,0,0,192));
            }
            
            int[] position = c.getPlanarCoordinate(u.getPosition());
            int radius = (int)(GUI.getGUI().getPixelsPerRadian() * Math.asin(u.getSize()/Coordinate.relativeDistance(c.getPosition(), u.getPosition())));
            
            
            for(String s : UnitSelection.getSelectedUnits())
                        if(s.equals(u.getName())){
                            g2.drawRect(position[0] - radius - 1, position[1] - radius - 1, 2 * (radius+1), 2 * (radius+1));
                            g2.drawRect(position[0] - radius - 2, position[1] - radius - 2, 2 * (radius+2), 2 * (radius+2));
                            drawUnitTooltip(g2, u, position[0] + radius, position[1] - radius);
                            break;
                        }

            //Mouse hints for Units
            try {if(Math.sqrt(Math.pow(position[0] - GUI.getGUI().getController().getMouseX(),2) + Math.pow(position[1] - GUI.getGUI().getController().getMouseY(),2)) < radius){
                g2.drawRect(position[0] - radius - 1, position[1] - radius - 1, 2 * (radius+1), 2 * (radius+1));
                drawUnitTooltip(g2, u, GUI.getGUI().getController().getMouseX(), GUI.getGUI().getController().getMouseY());
            }}catch(NullPointerException e){}
        }
    }
    
    
    public static void drawUnitTooltip(Graphics2D g2, Unit u, int x, int y){
        String faction = FactionManager.getFactionOf(u.getName());
        int width = Math.max(150,8*u.getName().length() + 50);
        
        g2.setColor(new Color(g2.getColor().getRed(), g2.getColor().getGreen(), g2.getColor().getBlue(), 64));
        
        g2.fillRect(x+10, y+10, width, 55);
        
        g2.setColor(Color.BLACK);
        
        if("Player".equals(faction) || FactionManager.getRelationship("Player", faction))
            g2.drawString("Name: " + u.getName(), x + 14, y+25);
        else
            g2.drawString("Name: Unknown", x + 14, y+25);
        
        String type = u.getType();
        g2.drawString("Unit Type: " + type, x + 14, y+40);
        
        if("Player".equals(faction))
            g2.drawString("Relationship: Yours", x + 14, y+55);
        else if(FactionManager.getRelationship("Player", faction))
            g2.drawString("Relationship: Ally", x + 14, y+55);
        else
            g2.drawString("Relationship: Hostile", x + 14, y+55);
        
        
        g2.drawRect(x+10, y+10, width, 55);
    }
    
    
}
