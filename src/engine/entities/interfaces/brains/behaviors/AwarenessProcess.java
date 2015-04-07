/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.interfaces.brains.behaviors;

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
