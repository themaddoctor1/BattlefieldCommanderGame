/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.gui;

import engine.entities.units.Unit;
import engine.game.FactionManager;
import static engine.gui.WorldDrawer.drawUnitTooltip;
import engine.physics.Coordinate;
import engine.world.Level;
import engine.world.LevelManager;
import java.awt.Color;
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
    
}
