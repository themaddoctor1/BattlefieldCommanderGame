/*
 * A RechargeButton is a Button that can only be used after a refractory period.
 */

package engine.gui.overlays.buttons;

import engine.gui.overlays.GraphicImage;
import engine.scripts.EncapsulatedScript;

/**
 *
 * @author Christopher Hittner
 */
public class RechargeButton extends GUIButton {
    
    private long lastAct;
    private final double chargeTimeMillis;
    
    public RechargeButton(int x, int y, int w, int h, EncapsulatedScript es, GraphicImage bi, double time) {
        super(x, y, w, h, es, bi);
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
