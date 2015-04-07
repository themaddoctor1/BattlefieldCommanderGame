/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.gui.overlays.icons;

import engine.gui.overlays.GraphicImage;
import java.awt.Graphics2D;

/**
 *
 * @author Christopher Hittner
 */
public class ArrowIcon extends GraphicImage{
    
    private double rot = 0;
    
    public ArrowIcon(double rotation){
        rot = rotation;
    }
    
    @Override
    public void drawImage(int X, int Y, int WIDTH, int HEIGHT, Graphics2D g) {
        
        g.rotate(Math.toRadians(90) + rot, X + (WIDTH/2), Y + (HEIGHT/2));
        
        g.drawLine(X + WIDTH/2, Y + HEIGHT, X, Y + HEIGHT/2);
        g.drawLine(X + WIDTH/2, Y + HEIGHT, X+WIDTH, Y + HEIGHT/2);
        g.drawLine(X + WIDTH, Y + HEIGHT/2, X, Y + HEIGHT/2);
        
        g.drawRect(X + WIDTH/4, Y, WIDTH/2, HEIGHT/2);
        
        g.rotate(-Math.toRadians(90) - rot, X + (WIDTH/2), Y + (HEIGHT/2));
        
    }

}
