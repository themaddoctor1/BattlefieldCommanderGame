/*
 * The Controller class is meant to provide objects that manage key preses and
 * mouse actions.
 */

package engine.gui;

import engine.entities.units.Unit;
import engine.game.FactionManager;
import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.world.LevelManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


/*
 * @author Christopher Hittner
*/
public abstract class Controller {
    
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
    public final void setKeyboardState(int code, boolean value){
        for(int i  = 0; i < keyCodes.length; i++){
            if(code == keyCodes[i])
                status[i] = value;
        }
    }
    
    public final boolean getKeyboardState(int index){
        return status[index];
    }
    
    /**
     * Sets whether or not the mouse is being pressed.
     * @param value The boolean value
     */
    public final void setMouseState(boolean value){
        mousePressed = value;
    }
    
    public final int getMouseX(){ return GUI.getGUI().getMousePosition().x; }
    public final int getMouseY(){ return GUI.getGUI().getMousePosition().y; }
    
    public final boolean getMouseState(){
        return mousePressed;
    }
    
    public final int getNumerOfControls(){ return keyCodes.length; }
    
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
    
    public abstract void executeLeftClick(MouseEvent me);
    public abstract void executeRightClick(MouseEvent me);
    
    public abstract void execute();
    
    public void setKeyCodes(int[] codes){
        keyCodes = codes;
        status = new boolean[codes.length];
        for(boolean b : status){
            b = false;
        }
    }

    
    
}
