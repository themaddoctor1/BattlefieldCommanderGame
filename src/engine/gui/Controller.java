/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.gui;

import engine.entities.units.Unit;
import engine.game.FactionManager;
import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.world.LevelManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


/* @author Christopher Hittner


*/
public class Controller {
    
    private boolean mousePressed;
    
    private boolean[] status;
    private int[] keyCodes;
    private String name;
    
    /**
     * Creates a Controller with key codes
     * @param nm The name of the Controller
     * @param codes The array of codes.
     * @precondition No two entries in the array are the same.
     */
    public Controller(String nm, int[] codes){
        name = nm;
        keyCodes = codes;
        status = new boolean[codes.length];
        for(boolean b : status){
            b = false;
        }
    }
    
    /**
     * Sets a new code at an index, given it actually exists.
     * @param index
     * @param newCode
     */
    public void setKeyCode(int index, int newCode){
        try{
            keyCodes[index] = newCode;
        } catch(Exception ex){
            
        }
    }
    
    /**
     * Sets whether or not a key is being pressed.
     * @param code The key code
     * @param value The boolean value
     */
    public void setKeyboardState(int code, boolean value){
        for(int i  = 0; i < keyCodes.length; i++){
            if(code == keyCodes[i])
                status[i] = value;
        }
    }
    
    public boolean getKeyboardState(int index){
        return status[index];
    }
    
    /**
     * Sets whether or not the mouse is being pressed.
     * @param value The boolean value
     */
    public void setMouseState(boolean value){
        mousePressed = value;
    }
    
    public int getMouseX(){ return GUI.getGUI().getMousePosition().x; }
    public int getMouseY(){ return GUI.getGUI().getMousePosition().y; }
    
    public boolean getMouseState(){
        return mousePressed;
    }
    
    public int getNumerOfControls(){ return keyCodes.length; }
    
    public String getName(){
        return name;
    }
    
    public String toString(){
        String result = "Controller " + name + "\n";
        for(int i = 0; i < 11; i++){
            result += "Index " + i + ": " + "\n"
                    + "    Code: " + keyCodes[i] + "\n"
                    + "    Name: " + KeyEvent.getKeyText(keyCodes[i]) + "\n"
                    + "    Pressed: " + status[i] + "\n\n";
        }
        return result;
    }
    
    public void executeLeftClick(MouseEvent me){
        
        if(getKeyboardState(4))
            UnitSelection.shiftClick(me.getX(), me.getY());
        else
            UnitSelection.click(me.getX(), me.getY());
    }
    
    public void executeRightClick(MouseEvent me){
        
        
        double dispX = me.getX() - GUI.getGUI().getCenterX();
        double dispY = me.getY() - GUI.getGUI().getCenterY();
        double disp = Math.sqrt(Math.pow(dispX, 2) + Math.pow(dispY, 2));
        double theta = disp / GUI.getGUI().getPixelsPerRadian();
        double lambda = Math.atan(dispY/dispX);
        if(dispX < 0)
            lambda = lambda + Math.toRadians(180);
        lambda += GUI.getGUI().getCamera().getAxialRot() - Math.PI/2.0;
        
        lambda = Math.PI - lambda;
        
        Coordinate c = new Coordinate(
                  (Math.tan(theta) * Math.cos(lambda)) * GUI.getGUI().getCamera().getPosition().Y() + GUI.getGUI().getCamera().getPosition().X()
                , 0
                , (Math.tan(theta) * Math.sin(lambda)) * GUI.getGUI().getCamera().getPosition().Y() + GUI.getGUI().getCamera().getPosition().Z()
        );
        
        String target;
        
        try {
            target = UnitSelection.getClickedUnits(me.getX(), me.getY()).get(0);
        } catch(Exception e){
            target = c.X() + " " + c.Z();
        }
        
        for(String nm : UnitSelection.getSelectedUnits()){
            if(GUI.getGUI().getController().getKeyboardState(6))        //A
                GUI.getGUI().parseUserInput(nm + " attack " + target, (byte) 0);
            else if(GUI.getGUI().getController().getKeyboardState(7))   //B
                GUI.getGUI().parseUserInput(nm + " board " + target, (byte) 0);
            else
                GUI.getGUI().parseUserInput(nm + " move to " + target, (byte) 0);
        }
    }
    
    public void execute(){
        
        double cameraSpeed = 0.2;
        
        if(getKeyboardState(0))
            GUI.getGUI().getCamera().getPosition().addVector(new Vector(cameraSpeed,Math.toRadians(0),0));
        if(getKeyboardState(1))
            GUI.getGUI().getCamera().getPosition().addVector(new Vector(cameraSpeed,Math.toRadians(180),0));
        if(getKeyboardState(2))
            GUI.getGUI().getCamera().getPosition().addVector(new Vector(cameraSpeed,Math.toRadians(90),0));
        if(getKeyboardState(3))
            GUI.getGUI().getCamera().getPosition().addVector(new Vector(cameraSpeed,Math.toRadians(270),0));
        if(getKeyboardState(5) && getKeyboardState(6)){
            UnitSelection.getSelectedUnits().clear();
            for(Unit u : LevelManager.getLevel().getUnits())
                if(FactionManager.getFactionOf(u.getName()) == 0 || getKeyboardState(4))
                    UnitSelection.getSelectedUnits().add(u.getName());
        }
    }

    
    
}
