/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
