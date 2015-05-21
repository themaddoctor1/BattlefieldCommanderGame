/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.gui.overlays;

import engine.gui.overlays.displayelements.DisplayElement;
import engine.gui.GUI;

/**
 *
 * @author Christopher
 */
public class ImageFrame extends DisplayElement{

    public ImageFrame(GraphicImage bi) {
        super(0, 0, 0, 0, bi);
    }
    
    public ImageFrame(GraphicImage bi, int x, int y, int size){
        super(x,y,0, size,bi);
    }

    @Override
    public boolean shouldAct() {
        return true;
    }
    
}
