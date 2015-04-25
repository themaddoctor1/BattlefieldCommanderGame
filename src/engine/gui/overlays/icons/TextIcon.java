/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.gui.overlays.icons;

import engine.gui.overlays.GraphicImage;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Christopher Hittner
 */
public class TextIcon extends GraphicImage{
    private final String text;
    
    public TextIcon(GraphicImage g, String c){
        super(g);
        text = c;
    }
    
    public TextIcon(String c){
        super();
        text = c;
    }
    
    @Override
    public void drawImage(int X, int Y, int WIDTH, int HEIGHT, Graphics2D g) {
        g.setFont(new Font("Courier New", Font.BOLD, HEIGHT));
        g.drawString(text, X + (int)(WIDTH * 1/2.4) - (HEIGHT * text.length())/4, Y + HEIGHT * 5/6);
    }
}
