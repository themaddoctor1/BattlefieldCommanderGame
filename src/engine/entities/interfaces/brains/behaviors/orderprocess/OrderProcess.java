/*
 * The OrderProcess class is a framework for designing a flow for processing
 * orders (pretty much self-explanatory, in my opinion). It uses the Decorator 
 */

package engine.entities.interfaces.brains.behaviors.orderprocess;

import engine.entities.interfaces.brains.UnitBrain;
import engine.entities.interfaces.brains.behaviors.BrainBehavior;
import engine.game.FactionManager;
import engine.world.LevelManager;

/**
 *
 * @author Christopher Hittner
 */
public abstract class OrderProcess extends BrainBehavior{
    protected OrderProcess process = null;
    
    public OrderProcess(OrderProcess op){
        process = op;
        op.setOwner(getOwnerName());
    }
    
    public OrderProcess(){
        
    }
    
    public OrderProcess(UnitBrain u){
        super(u);
    }
    
    public final void setSubprocess(OrderProcess op){
        process = op;
        process.setOwner(this.getOwner().getName());
    }
    
    public final void giveOrder(String order, String factID){
        
        try {
        if(!factID.equals(FactionManager.getFactionOf(this.getOwner().getName())))
            return;
        } catch(Exception e){
            
        }
        
        
        if(!processOrder(order) && process != null)
            process.giveOrder(order, factID);
        
    }
    
    @Override
    public void setOwner(String nm){
        super.setOwner(nm);
        if(process != null)
            process.setOwner(nm);
    }
    
    /**
     * Processes orders and returns whether or not the order could be processed so that alternate orders can be checked.
     * @param order
     * @return
     */
    protected abstract boolean processOrder(String order);

    
    
}
