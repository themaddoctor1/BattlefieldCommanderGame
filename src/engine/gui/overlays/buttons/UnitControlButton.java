/*
 * The UnitControlButton is a button that only works if a Unit is selected.
 */

package engine.gui.overlays.buttons;

import engine.gui.UnitSelection;
import engine.gui.overlays.GraphicImage;

/**
 *
 * @author Christopher Hittner
 */
public class UnitControlButton extends GUIButton{

    public UnitControlButton(int x, int y, int w, int h, String cmd, GraphicImage bi) {
        super(x, y, w, h, cmd, bi);
    }

    @Override
    public boolean shouldAct() {
        return !UnitSelection.getSelectedUnits().isEmpty();
    }

}
