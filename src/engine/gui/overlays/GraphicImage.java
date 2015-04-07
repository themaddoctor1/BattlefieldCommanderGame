/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.gui.overlays;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Christopher
 */
public abstract class GraphicImage {
    
    private GraphicImage subimage = null;
    private Color color = Color.BLACK;
    
    public GraphicImage(){}
    public GraphicImage(GraphicImage img){subimage = img;}
    
    public GraphicImage(GraphicImage img, Color c){
        subimage = img;
        setColor(c);
    }
    
    public final void draw(int X, int Y, int WIDTH, int HEIGHT, Graphics g){
        g.setColor(color);
        drawImage(X,Y,WIDTH,HEIGHT,(Graphics2D) g);
        try {
            subimage.draw(X, Y, WIDTH, HEIGHT, g);
        } catch(Exception e){}
        
    }

    public abstract void drawImage(int X, int Y, int WIDTH, int HEIGHT, Graphics2D g);
    public final void setColor(Color c){ color = c; }
    public final Color getColor(){ return color; }
    
    
}
