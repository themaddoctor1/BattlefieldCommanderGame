/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.gui.overlays.icons;

import engine.gui.overlays.GraphicImage;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Christopher Hittner
 */
public class SquareIcon extends GraphicImage {
    
    public SquareIcon(GraphicImage img){
        super(img);
    }
    
    @Override
    public void drawImage(int X, int Y, int WIDTH, int HEIGHT, Graphics2D g) {
        g.drawRect(X, Y, WIDTH, HEIGHT);
        g.drawRect(X+1, Y+1, WIDTH-2, HEIGHT-2);
        
    }

}
