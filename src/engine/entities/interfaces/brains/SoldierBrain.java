/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.interfaces.brains;

import engine.entities.interfaces.brains.behaviors.movementbehavior.WalkingMovementBehavior;
import engine.entities.interfaces.brains.behaviors.orderprocess.DefaultUnitOrderProcess;
import engine.entities.interfaces.brains.behaviors.orderprocess.GroundUnitOrderProcess;
import engine.entities.interfaces.brains.behaviors.combatbehavior.LimitedLeadingCombatBehavior;
import engine.entities.interfaces.brains.behaviors.awarenesprocess.HearingAwarenessProcess;
import engine.entities.interfaces.brains.behaviors.awarenesprocess.SightAwarenessProcess;
import engine.entities.interfaces.brains.behaviors.*;
import engine.entities.units.Unit;

/**
 *
 * @author Christopher Hittner
 */
public class SoldierBrain extends UnitBrain{
    
    public SoldierBrain(String nm) {
        super(
                nm, 
                new LimitedLeadingCombatBehavior(Math.toRadians(20)), //new LimitedAngleCombatBehavior(Math.toRadians(-80), Math.toRadians(45))
                new WalkingMovementBehavior(), 
                new GroundUnitOrderProcess(new DefaultUnitOrderProcess()),
                new SightAwarenessProcess(new HearingAwarenessProcess(4.0), Math.toRadians(75), Math.toRadians(0.1)));
    }
    
    public void act(Unit me, double factor) {
        
        actBoarding(me);
        
        actCombat(me,factor);
        
        if(destinations.isEmpty())
            return;
        else if(me.onGround()) actMovement(me,factor);
        if(attacking) me.getVelocity().multiplyMagnitude(0.2);
        actAwareness(me, factor);
        
    }

    
    
}
