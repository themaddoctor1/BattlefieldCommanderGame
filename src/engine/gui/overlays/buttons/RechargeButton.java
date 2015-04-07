/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.gui.overlays.buttons;

import engine.gui.overlays.GraphicImage;

/**
 *
 * @author Christopher Hittner
 */
public class RechargeButton extends GUIButton {
    
    private long lastAct;
    private final double chargeTimeMillis;
    
    public RechargeButton(int x, int y, int w, int h, String cmd, GraphicImage bi, double time) {
        super(x, y, w, h, cmd, bi);
        chargeTimeMillis = 1000 * time;
        lastAct = System.currentTimeMillis();
    }

    @Override
    public boolean shouldAct() {
        return System.currentTimeMillis() - lastAct >= chargeTimeMillis;
    }
    
    @Override
    public boolean click(int x, int y){
        if(super.click(x,y)){
            lastAct = System.currentTimeMillis();
            return true;
        }
        return false;
    }

}
