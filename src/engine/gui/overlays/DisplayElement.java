/*
 * A DisplayElement is used to draw images on the screen. Each one contains a
 * GraphicImage object, which is a Decorator object that can be used for making
 * unique designs for buttons, menus, etc.
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
