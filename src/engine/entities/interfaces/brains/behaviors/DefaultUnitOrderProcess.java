/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.interfaces.brains.behaviors;

import engine.game.FactionManager;

/**
 *
 * @author Christopher Hittner
 */
public class DefaultUnitOrderProcess extends OrderProcess{
    
    public DefaultUnitOrderProcess(OrderProcess op) {
        super(op);
    }
    
    public DefaultUnitOrderProcess() {}
    
    @Override
    protected boolean processOrder(String order) {
        
        if(order.indexOf("attack ") == 0 || order.indexOf("defend ") == 0){
            //Units won't attack allies, so it may or may not matter (unless the code changes, of course)
            
            getOwner().setMove(true);
            getOwner().setShoot(true);
            
            getOwner().giveOrder("move to " + order.substring(7), FactionManager.getFactionOf(getOwner().getName()));
            
        } else if(order.indexOf("board ") == 0){
            
            String moveOrder = "move to " + order.substring(6);
            
            getOwner().giveOrder(moveOrder, FactionManager.getFactionOf(getOwner().getName()));
            
            getOwner().setBoard(true);
            
        } else if(order.equals("hold fire")){
            getOwner().setShoot(false);
        } else if(order.equals("open fire")){
            getOwner().setShoot(true);
        } else {
            return false;
        }
        return true;
    }

}
