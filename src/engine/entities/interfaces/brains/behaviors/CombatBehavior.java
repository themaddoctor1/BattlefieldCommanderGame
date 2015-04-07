/*
 * A CombatBehavior is any BrainBehavior that can be used to act out
 * combat-based processes. Some CombatBehaviors can be written to
 * use AwarenessProcesses that can be used to affect who is able
 * to be attacked.
 */
package engine.entities.interfaces.brains.behaviors;

import engine.entities.interfaces.brains.UnitBrain;
import engine.entities.units.Unit;

/**
 *
 * @author Christopher
 */
public abstract class CombatBehavior extends BrainBehavior{
    
    public CombatBehavior(UnitBrain brain){ super(brain); }
    public CombatBehavior(){ super(); }
    
    public abstract void actCombat(Unit me, double factor);
    public abstract void attackUnit(Unit me, Unit targ);
    
}
