/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
