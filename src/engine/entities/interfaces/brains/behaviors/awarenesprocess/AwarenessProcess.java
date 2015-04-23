/*
 * An AwarenessProcess is a class used by a UnitBrain to determine who is
 * able to be seen. It uses the Decorator Pattern to allow for combinations
 * of AwarenessProcesses to be used in conjunction.
 */

package engine.entities.interfaces.brains.behaviors.awarenesprocess;

import engine.entities.interfaces.brains.behaviors.BrainBehavior;
import engine.entities.units.Unit;
import engine.world.LevelManager;
import java.util.ArrayList;

/**
 *
 * @author Christopher Hittner
 */
public abstract class AwarenessProcess extends BrainBehavior{
    
    private final AwarenessProcess decorator;
    
    public AwarenessProcess(){
        this(null);
    }
    
    public AwarenessProcess(AwarenessProcess ap){
        decorator = ap;
    }
    
    public final ArrayList<Unit> getUnitsAwareOf(Unit me){
        ArrayList<Unit> result = seenUnits(me);
        if(decorator != null)
            result.addAll(decorator.seenUnits(me));
        
        return result;
        
    }
    
    public abstract void actAwareness(Unit me, double factor);
    
    protected abstract ArrayList<Unit> seenUnits(Unit me);
    
    public void combatNotification(Unit targ){
        combatProcess(targ);
        if(decorator != null)
            decorator.combatNotification(targ);
    }
    
    /**
     * Method that is called to run processes that are needed when the owner of this object attacks another Unit.
     * @param targ The Unit being attacked.
     */
    protected abstract void combatProcess(Unit targ);
    
}
