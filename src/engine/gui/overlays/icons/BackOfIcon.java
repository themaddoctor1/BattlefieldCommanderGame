/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.gui.overlays.icons;

import engine.gui.overlays.GraphicImage;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Christopher Hittner
 */
public class BackOfIcon extends GraphicImage{
    
    @Override
    public void drawImage(int X, int Y, int WIDTH, int HEIGHT, Graphics2D g) {
        g.fillRect(X, Y, WIDTH, HEIGHT);
    }

}
