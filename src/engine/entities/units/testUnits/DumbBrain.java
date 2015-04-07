/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.entities.units.testUnits;

import engine.entities.interfaces.brains.UnitBrain;
import engine.entities.interfaces.brains.behaviors.*;
import engine.entities.units.Unit;

/**
 *
 * @author Christopher
 */
public class DumbBrain extends UnitBrain{

    public DumbBrain(String nm) {
        super(nm, new BasicCombatBehavior(), new DropPodMovementBehavior(), new TransportOrderProcess(), new SightAwarenessProcess(Math.toRadians(180), 0));
    }

    @Override
    public void act(Unit me, double factor) {
        actMovement(me,factor);
    }
    
}
