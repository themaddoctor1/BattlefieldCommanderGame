/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.interfaces.brains;

import engine.entities.interfaces.brains.behaviors.*;
import engine.entities.units.helicopters.Helicopter;
import engine.entities.units.Unit;
import engine.game.FactionManager;
import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.world.LevelManager;

/**
 *
 * @author Christopher Hittner
 */
public class HelicopterBrain extends UnitBrain{

    public HelicopterBrain(String nm) {
        super(
                nm, 
                new BasicCombatBehavior(),
                new HoveringMovementBehavior(), 
                new HelicopterOrderProcess(new DefaultUnitOrderProcess()),
                new SightAwarenessProcess(Math.toRadians(180), Math.toRadians(0)));
    }
    
    
    public void act(Unit me, double factor) {
        actBoarding(me);
        
        if(((Helicopter) me).hasPilot()) actCombat(me,factor);
        else destinations.clear();
        
        actMovement(me,factor);
        actAwareness(me, factor);
    }
    

    
    
}
