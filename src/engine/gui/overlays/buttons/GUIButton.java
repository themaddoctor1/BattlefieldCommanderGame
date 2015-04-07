/*
 * A GUIButton is used to run a command stored as a String whenever it is activated.
 */
package engine.gui.overlays.buttons;

import engine.gui.GUI;
import engine.gui.UnitSelection;
import engine.gui.overlays.DisplayElement;
import engine.gui.overlays.GraphicImage;
import java.awt.Graphics;

/**
 *
 * @author Christopher
 */
public abstract class GUIButton extends DisplayElement{
    
    public final String CMD;
    
    
    public GUIButton(int x, int y, int w, int h, String cmd, GraphicImage bi){
        super(x,y,w,h,bi);
        CMD = cmd;
    }
    
    
    public boolean testClick(int x, int y){
        return(
                x >= X && x < X+WIDTH
             && y >= Y && y < Y+HEIGHT
                );
    }
    
    public boolean click(int x, int y){
        if(testClick(x,y) && shouldAct()){
            clickEvent();
            return true;
        }
        return false;
    }
    
    public void clickEvent(){
        for(String name : UnitSelection.getSelectedUnits())
            GUI.getGUI().parseUserInput(name + " " + CMD, (byte) 0);
    }
    
}
