/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.interfaces.brains;

import engine.entities.interfaces.brains.behaviors.orderprocess.AircraftOrderProcess;
import engine.entities.interfaces.brains.behaviors.combatbehavior.BasicCombatBehavior;
import engine.entities.interfaces.brains.behaviors.orderprocess.DefaultUnitOrderProcess;
import engine.entities.interfaces.brains.behaviors.movementbehavior.FlyingMovementBehavior;
import engine.entities.interfaces.brains.behaviors.awarenesprocess.SightAwarenessProcess;
import engine.entities.units.Aircraft;
import engine.entities.units.Unit;
import engine.game.FactionManager;
import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.world.LevelManager;

/**
 *
 * @author Christopher Hittner
 */
public class AircraftBrain extends UnitBrain{
    
    
    public AircraftBrain(String nm) {
        super(nm, 
                new BasicCombatBehavior(),
                new FlyingMovementBehavior(), 
                new AircraftOrderProcess(new DefaultUnitOrderProcess()),
                new SightAwarenessProcess(Math.toRadians(180), Math.toRadians(0)));
        willMove = true;
    }
    
    public void act(Unit me, double factor) {
        actBoarding(me);
        
        if(((Aircraft) me).hasPilot()) actCombat(me,factor);
        else destinations.clear();
        
        actMovement(me,factor);
        
        actAwareness(me, factor);
        
        
    }
    
}

