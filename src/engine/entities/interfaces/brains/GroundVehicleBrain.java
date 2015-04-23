/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.entities.interfaces.brains;

import engine.entities.interfaces.brains.behaviors.movementbehavior.DrivingMovementBehavior;
import engine.entities.interfaces.brains.behaviors.orderprocess.DefaultUnitOrderProcess;
import engine.entities.interfaces.brains.behaviors.orderprocess.TransportOrderProcess;
import engine.entities.interfaces.brains.behaviors.orderprocess.GroundUnitOrderProcess;
import engine.entities.interfaces.brains.behaviors.combatbehavior.BasicCombatBehavior;
import engine.entities.interfaces.brains.behaviors.awarenesprocess.SightAwarenessProcess;
import engine.entities.interfaces.brains.behaviors.*;
import engine.entities.units.groundvehicles.GroundVehicle;
import engine.entities.units.Unit;
import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.world.LevelManager;

/**
 *
 * @author Christopher
 */
public class GroundVehicleBrain extends UnitBrain {

    public GroundVehicleBrain(String nm) {
        super(
                nm, 
                new BasicCombatBehavior(), 
                new DrivingMovementBehavior(), 
                new TransportOrderProcess(new GroundUnitOrderProcess(new DefaultUnitOrderProcess())),
                new SightAwarenessProcess(Math.toRadians(180), Math.toRadians(0)));
    }
    
    @Override
    public void act(Unit me, double factor) {
        actBoarding(me);
        
        if(((GroundVehicle)me).hasPilot()) actCombat(me,factor);
        else destinations.clear();
        
        if(me.onGround())
            actMovement(me,factor);
        
        actAwareness(me, factor);
    }
    
    
    
    
}
