/*
 * A BrainBehavior is a class that processes aspects of a UnitBrain's function.
 * This class makes partial use of the Observer pattern. By the way, it is meant
 * to observe a UnitBrain of the provided name.
 */
package engine.entities.interfaces.brains.behaviors;

import engine.entities.interfaces.brains.UnitBrain;
import engine.world.LevelManager;

/**
 *
 * @author Christopher
 */
public abstract class BrainBehavior {
    private String owner = null;
    
    public BrainBehavior(UnitBrain brain){
        try {
            owner = brain.getName();
        } catch(NullPointerException npe){
            
        }
    }
    
    public BrainBehavior() {
        
    }
    
    public final void setOwner(UnitBrain u){
        setOwner(u.getName());
        
    }
    
    public void setOwner(String nm){ owner = nm;}
    
    public final UnitBrain getOwner(){ return LevelManager.getLevel().getUnit(owner).getBrain();}
    public String getOwnerName(){ return owner;}
}
