/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.gui.overlays;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Christopher Hittner
 */
public class DisplayOverlay {
    ArrayList<DisplayElement> elements = new ArrayList<>();
    
    public DisplayOverlay(){}
    public DisplayOverlay(ArrayList<DisplayElement> de){ elements = de; }
    
    
    public void draw(Graphics g){
        for(DisplayElement e : elements)
            e.draw(g);
    }
    
}
