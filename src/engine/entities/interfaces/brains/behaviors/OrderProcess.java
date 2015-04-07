/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.interfaces.brains.behaviors;

import engine.entities.interfaces.brains.UnitBrain;
import engine.game.FactionManager;
import engine.world.LevelManager;

/**
 *
 * @author Christopher Hittner
 */
public abstract class OrderProcess {
    protected OrderProcess process = null;
    protected String owner = null;
    
    public OrderProcess(OrderProcess op){
        process = op;
    }
    
    public OrderProcess(){
        
    }
    
    public OrderProcess(UnitBrain u){
        try {
            owner = u.getName();
        } catch(NullPointerException npe){
            
        }
    }
    
    public final void setOwner(UnitBrain u){
        try {
            owner = u.getName();
            try{ process.setOwner(u); } catch(NullPointerException e){e.printStackTrace();}
        } catch(NullPointerException npe){
            
        }
    }
    
    public final void setOwner(String nm){
        owner = nm;
        try {
        process.setOwner(nm);
        } catch(NullPointerException e){}
    }
    
    public final void setSubprocess(OrderProcess op){
        process = op;
        process.setOwner(owner);
    }
    
    public final void giveOrder(String order, byte factID){
        
        try {
        if(factID != FactionManager.getFactionOf(owner))
            return;
        } catch(Exception e){
            
        }
        
        
        if(!processOrder(order))
            process.giveOrder(order, factID);
        
    }
    
    /**
     * Processes orders and returns whether or not the order could be processed so that alternate orders can be checked.
     * @param order
     * @return
     */
    protected abstract boolean processOrder(String order);

    
    public final UnitBrain getOwner(){
        return LevelManager.getLevel().getUnit(owner).getBrain(); 
    }
    
    
}
