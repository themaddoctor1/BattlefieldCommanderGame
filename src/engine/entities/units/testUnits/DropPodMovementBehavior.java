/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.entities.units.testUnits;

import engine.entities.interfaces.brains.UnitBrain;
import engine.entities.interfaces.brains.behaviors.MovementBehavior;
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
        double acc = 40;
        
        if(     -velocity.getMagnitudeY() > 0.9*Math.sqrt(2*(acc - 9.81) * (position.Y() - me.getSize()))
                && position.Y() > me.getSize() + 0.01
                ){
            
            Vector acceleration = (new Vector(velocity.unitVector(),-acc*factor));
            //System.out.println(acc);
            me.getVelocity().addVectorToThis(acceleration);
        } else if(position.Y() > 10){
            Vector acceleration = (new Vector(acc*factor,0,Math.toRadians(-90)));
            me.getVelocity().addVectorToThis(acceleration);
        }
        
    }

    
}
