/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.interfaces.brains.behaviors.movementbehavior;

import engine.entities.interfaces.brains.UnitBrain;
import engine.entities.units.Unit;
import engine.entities.units.helicopters.Helicopter;
import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.world.LevelManager;

/**
 *
 * @author Christopher Hittner
 */
public class HoveringMovementBehavior extends MovementBehavior{

    public HoveringMovementBehavior(UnitBrain brain) {
        super(brain);
    }
    
    public HoveringMovementBehavior(){ super(); }

    @Override
    public void actMovement(Unit me, double factor) {
        
        
        Helicopter helo = (Helicopter) me;
        double maxHorizAcc = Math.sqrt(Math.pow(helo.maxAcc(),2) - Math.pow(9.81,2));
        helo.getEngineAcceleration().multiplyMagnitude(0);
        
        if(helo.hasPilot())
        if(getOwner().getDestinations().size() > 0 && getOwner().willMove()){
            Coordinate destination = null;
            try {
                destination = (Coordinate) getOwner().getDestinations().get(0);
            } catch(Exception e){
                try {
                    destination = LevelManager.getLevel().getUnit(((String)(getOwner().getDestinations().get(0))).substring("[UNIT]".length())).getPosition();
                } catch(Exception ex){
                    if(getOwner().getDestinations().get(0).toString().indexOf("[ASCEND]") == 0){
                        //Ascend
                        double dest = Math.max(10*Integer.parseInt(getOwner().getDestinations().get(0).toString().substring(8)),helo.getSize());
                        
                        if(me.getVelocity().getMagnitudeY() <= Math.sqrt(19.62 * Math.abs(dest - me.getPosition().Y())))
                            helo.getEngineAcceleration().addVectorToThis(new Vector(helo.maxAcc(), 0, Math.toRadians(90)));
                        else if(me.getPosition().Y() >= dest){
                            getOwner().getDestinations().remove(0);
                            actMovement(me,factor);
                        }
                    } else if(getOwner().getDestinations().get(0).toString().indexOf("[DESCEND]") == 0){
                        //Decend
                        int dest = Math.max(0, 10*Integer.parseInt(getOwner().getDestinations().get(0).toString().substring(9)));
                        if(me.getVelocity().getMagnitudeY() <= Math.sqrt(2 * helo.maxAcc() * Math.abs(dest - me.getPosition().Y())))
                            helo.getEngineAcceleration().addVectorToThis(new Vector(helo.maxAcc(), 0, Math.toRadians(90)));
                        else if(me.getPosition().Y() <= dest){
                            getOwner().getDestinations().remove(0);
                            actMovement(me,factor);
                        }
                    } else {
                        getOwner().getDestinations().remove(0);
                        actMovement(me,factor);
                    }
                    return;
                }
            }
            
            double dist = Math.sqrt(Math.pow(me.getPosition().X() - destination.X() , 2) + Math.pow(me.getPosition().Z() - destination.Z() , 2));
            
            if(dist > 5){
                //Enacts movement towards destination
                double maxAllowedVelocity = Math.sqrt(maxHorizAcc*dist);
                //Slows down when too close
                
                if(helo.getVelocity().getMagnitude() > maxAllowedVelocity){
                    helo.getEngineAcceleration().addVectorToThis(new Vector(helo.maxAcc(), helo.getVelocity().getAngleXZ() + Math.PI, Math.atan(9.81/maxHorizAcc)));
                } else {
                    helo.getEngineAcceleration().addVectorToThis(new Vector(helo.maxAcc(), new Vector(helo.getPosition(), destination).getAngleXZ(), Math.atan(9.81/maxHorizAcc)));
                }

            } else {
                getOwner().getDestinations().remove(0);
                actMovement(me,factor);
            }
        } else {
            helo.getEngineAcceleration().addVectorToThis(new Vector(helo.maxAcc(), helo.getVelocity().getAngleXZ() + Math.PI, Math.atan(9.81/maxHorizAcc)));
        }
        
        
    }

}
