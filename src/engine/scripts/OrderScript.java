/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.scripts;

import engine.game.FactionManager;
import engine.gui.GUI;

/**
 *
 * @author Christopher Hittner
 */
public class OrderScript extends EncapsulatedScript{
    
    private final String order;
    
    public OrderScript(String CMD){
        order = CMD;
    }

    @Override
    protected void script(String[] params) {
        String name = params[0];
        
        GUI.getGUI().parseUserInput("UNIT_ORDER", name + " " + order, FactionManager.getFactionOf(name));
        
    }
    
}
