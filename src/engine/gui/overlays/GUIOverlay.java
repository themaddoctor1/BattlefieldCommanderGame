/*
 * A GUIOVerlay holds Buttons and Displays, which run commands and display stuff.
 */

package engine.gui.overlays;

import java.awt.Graphics;

/**
 *
 * @author Christopher Hittner
 */
public class GUIOverlay {
    private ButtonOverlay buttons = new ButtonOverlay();
    private DisplayOverlay displays = new DisplayOverlay();
    
    public GUIOverlay(){}
    
    public void draw(Graphics g){
        buttons.draw(g);
        displays.draw(g);
    }
    
    public ButtonOverlay getButtonOverlay(){ return buttons; }
    
    public void setButtonOverlay(ButtonOverlay bo){buttons = bo;}
    public void setDisplayOverlay(DisplayOverlay d){displays = d;}
    
    
}
