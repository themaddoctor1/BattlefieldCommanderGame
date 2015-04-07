/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.gui.overlays.buttons;

import engine.gui.UnitSelection;
import engine.gui.overlays.GraphicImage;

/**
 *
 * @author Christopher Hittner
 */
public class DefaultButton extends GUIButton{

    public DefaultButton(int x, int y, int w, int h, String cmd, GraphicImage bi) {
        super(x, y, w, h, cmd, bi);
    }

    @Override
    public boolean shouldAct() {
        return true;
    }

}
