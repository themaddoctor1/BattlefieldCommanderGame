/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.entities.units.testUnits;

import engine.entities.interfaces.brains.UnitBrain;
import engine.entities.interfaces.brains.behaviors.movementbehavior.MovementBehavior;
import engine.entities.units.Unit;
import engine.physics.*;

/**
 *
 * @author Christopher
 */
public class DropPodMovementBehavior extends MovementBehavior {

    public DropPodMovementBehavior(UnitBrain b) {
        super(b);
    }
    
    public DropPodMovementBehavior(){ super(); }
    
    @Override
    public void actMovement(Unit me, double factor) {
        Coordinate position = me.getPosition();
        Vector velocity = me.getVelocity();
        if(     -velocity.getMagnitudeY() >= 0.9*Math.sqrt(Math.abs(2*(40 - 10) * (position.Y() - me.getSize())))
                && !me.onGround()
                ){
            
            //System.out.println(velocity.getMagnitude() + "  >=  " + 0.9*Math.sqrt(Math.abs(2*(40 - 10) * (position.Y() - me.getSize()))));
            
            Vector acceleration = (new Vector(40*factor, 0, Math.toRadians(90)));
            //System.out.println(acc);
            me.getVelocity().addVectorToThis(acceleration);
        } else if(position.Y() > 10){
            Vector acceleration = (new Vector(0.9 * 40*factor,0,Math.toRadians(-90)));
            me.getVelocity().addVectorToThis(acceleration);
        }
        
    }

    
}
