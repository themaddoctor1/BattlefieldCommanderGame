/*
 * The ButtonOverlay class is used to execute and manage buttons.
 */
package engine.gui.overlays;

import engine.gui.overlays.buttons.GUIButton;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class ButtonOverlay {
    
    private ArrayList<GUIButton> buttons = new ArrayList<>();
    
    public ButtonOverlay(ArrayList<GUIButton> b){
        buttons = b;
    }

    public ButtonOverlay() {}
    
    public void addButton(GUIButton b){ buttons.add(b);}
    public void addButtons(ArrayList<GUIButton> b){ buttons.addAll(b);}
    
    public void draw(Graphics g){
        for(GUIButton b : buttons)
            b.draw(g);
    }
    
    public boolean click(int x, int y) {
        boolean result = false;
        for(GUIButton b : buttons){
            result = b.click(x, y) || result;
        }
        return result;
    }

    
}
