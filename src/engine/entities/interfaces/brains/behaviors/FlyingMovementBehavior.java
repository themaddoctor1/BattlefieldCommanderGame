/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.interfaces.brains.behaviors;

import engine.entities.interfaces.brains.UnitBrain;
import engine.entities.units.Aircraft;
import engine.entities.units.Unit;
import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.world.LevelManager;

/**
 *
 * @author Christopher Hittner
 */
public class FlyingMovementBehavior extends MovementBehavior {

    public FlyingMovementBehavior(UnitBrain brain) {
        super(brain);
    }
    
    public FlyingMovementBehavior(){ super(); }

    @Override
    public void actMovement(Unit me, double factor) {
        
        //Destinations should never be empty, but the else statement is just for in case.
        
        Aircraft plane = (Aircraft) me;
        
        if(getOwner().getDestinations().size() > 0){
            
            Coordinate destination = null;
            
            try {
                //The destination is a Coordinate, and must be orbited.
                destination = (Coordinate) getOwner().getDestinations().get(0);
                
            } catch(Exception e){
                try {
                    //The target is a Unit
                    Unit target = LevelManager.getLevel().getUnit(((String)(getOwner().getDestinations().get(0))).substring("[UNIT]".length()));
                    
                    //The destination is a Coordinate, and must be orbited.
                    destination = target.getPosition();

                } catch(Exception ex){
                    return;
                }
            }
            
            
            double dist = Math.sqrt(Math.pow(me.getPosition().X() - destination.X() , 2) + Math.pow(me.getPosition().Z() - destination.Z() , 2));

            Vector acc = null;

            double dir = -Math.PI * Math.signum(Math.sin(plane.getVelocity().getAngleXZ() - (new Vector(plane.getPosition(),destination)).getAngleXZ())) / 2.0;


            /*
            if((plane.getVelocity().getAngleXZ() - (new Vector(plane.getPosition(),destination)).getAngleXZ())%(2*Math.PI) < Math.PI)
                dir = 1;
            else dir = -1;
            */      

            //Default sideways turning
            acc = new Vector(dist*Math.pow(plane.getTurnRate(), 2),
                    plane.getVelocity().getAngleXZ() + dir,
                    0);

            if(Vector.cosOfAngleBetween(plane.getVelocity(), new Vector(plane.getPosition(),destination)) > 0)
                acc = new Vector(dist*Math.pow(plane.getTurnRate(), 2),
                    plane.getVelocity().getAngleXZ() - dir,
                    0);

            if(plane.getVelocity().getMagnitude() > dist*plane.getTurnRate()){
                //Slow down
                acc.addVectorToThis(new Vector(-plane.maxAcc(), plane.getVelocity().getAngleXZ(),0));

            }else if(plane.getVelocity().getMagnitude() < dist*plane.getTurnRate()){

                //Speed up
                acc.addVectorToThis(new Vector(plane.maxAcc(), plane.getVelocity().getAngleXZ() + Math.PI,0));

            }


            plane.getVelocity().addVectorToThis(new Vector(acc,factor));
            //Keeps the Aircraft from overspeeding or underspeeding.
            if(plane.getVelocity().getMagnitude() < plane.minVel())
                plane.getVelocity().multiplyMagnitude(plane.minVel()/plane.getVelocity().getMagnitude());
            if(plane.getVelocity().getMagnitude() > plane.maxVel())
                plane.getVelocity().multiplyMagnitude(plane.maxVel()/plane.getVelocity().getMagnitude());
            
        } else {
            
            
        }
        
    }

}
