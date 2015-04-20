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
 * @author Christopher
 */
public class LetterIcon extends GraphicImage {
    
    private final String letter;
    
    public LetterIcon(GraphicImage g, String c){
        super(g);
        letter = c.substring(0, 1);
    }
    
    public LetterIcon(String c){
        super();
        letter = c.substring(0, 1);
    }
    
    @Override
    public void drawImage(int X, int Y, int WIDTH, int HEIGHT, Graphics2D g) {
        g.setFont(new Font("Courier New", Font.BOLD, HEIGHT));
        g.drawString(letter, X + WIDTH * 1/4, Y + HEIGHT * 5/6);
    }
    
}
