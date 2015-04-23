/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.interfaces.brains.behaviors.orderprocess;

import engine.entities.interfaces.CarriesTroops;
import engine.entities.units.groundvehicles.GroundVehicle;
import engine.world.LevelManager;

/**
 *
 * @author Christopher Hittner
 */
public class TransportOrderProcess extends OrderProcess{

    public TransportOrderProcess(OrderProcess op){
        super(op);
    }
    
    public TransportOrderProcess(){ super(); }
    
    @Override
    protected boolean processOrder(String order) {
        
        if(order.indexOf("unload") == 0){
            //Units won't attack allies, so it may or may not matter (unless the code changes, of course)
            
            try {
                if(order.substring(6).equals(" all units"))
                    LevelManager.getLevel().getUnits().addAll(((CarriesTroops) LevelManager.getLevel().getUnit(getOwner().getName())).unloadAll(true));
                else if(order.substring(6).equals(" passengers"))
                    LevelManager.getLevel().getUnits().addAll(((CarriesTroops) LevelManager.getLevel().getUnit(getOwner().getName())).unloadAll(false));
                else
                    LevelManager.getLevel().getUnits().add(((CarriesTroops) LevelManager.getLevel().getUnit(getOwner().getName())).unboard(order.substring(7)));
            } catch(Exception ex){
                ex.printStackTrace();
            }
            
        } else return false;
        return true;
    }

}
