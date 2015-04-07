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
public class XIcon extends GraphicImage{

    @Override
    public void drawImage(int X, int Y, int WIDTH, int HEIGHT, Graphics2D g) {
        g.drawLine(X, Y, X + WIDTH, Y + HEIGHT);
        g.drawLine(X+1, Y, X + WIDTH, Y + HEIGHT-1);
        g.drawLine(X, Y+1, X + WIDTH-1, Y + HEIGHT);
        
        g.drawLine(X + WIDTH, Y, X, Y + HEIGHT);
        g.drawLine(X+WIDTH-1, Y, X, Y + HEIGHT-1);
        g.drawLine(X + WIDTH, Y+1, X+1, Y + HEIGHT);
    }

}
