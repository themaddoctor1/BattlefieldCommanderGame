/*
 * The UnitControlButton is a button that only works if a Unit is selected.
 */

package engine.gui.overlays.buttons;

import engine.gui.GUI;
import engine.gui.UnitSelection;
import engine.gui.overlays.GraphicImage;
import engine.scripts.OrderScript;

/**
 *
 * @author Christopher Hittner
 */
public class UnitControlButton extends GUIButton{

    public UnitControlButton(int x, int y, int w, int h, OrderScript es, GraphicImage bi) {
        super(x, y, w, h, es, bi);
    }

    @Override
    public boolean shouldAct() {
        return !UnitSelection.getSelectedUnits().isEmpty();
    }

    @Override
    public void clickEvent() {
        for(String name : UnitSelection.getSelectedUnits()){
            String[] params = {name};
            script.execute(params);
            
        }
    }

}
