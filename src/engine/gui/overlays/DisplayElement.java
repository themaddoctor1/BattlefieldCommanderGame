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
public abstract class DisplayElement {
    
    public final int X, Y, WIDTH, HEIGHT;
    
    private final GraphicImage IMG;
    
    public DisplayElement(int x, int y, int w, int h, GraphicImage bi){
        X = x;
        Y = y;
        WIDTH = w;
        HEIGHT= h;
        IMG = bi;
    }
    
    
    public final void draw(Graphics g){
        IMG.draw(X,Y,WIDTH,HEIGHT,g);
    }
    
    public abstract boolean shouldAct();
    
}
