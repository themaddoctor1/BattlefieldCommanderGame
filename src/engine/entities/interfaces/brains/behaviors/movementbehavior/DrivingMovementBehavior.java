/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.interfaces.brains.behaviors.movementbehavior;

import engine.entities.interfaces.brains.UnitBrain;
import engine.entities.units.Unit;
import engine.entities.units.groundvehicles.GroundVehicle;
import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.world.LevelManager;

/**
 *
 * @author Christopher Hittner
 */
public class DrivingMovementBehavior extends MovementBehavior {

    public DrivingMovementBehavior(UnitBrain brain) {
        super(brain);
    }
    public DrivingMovementBehavior(){ super(); }

    @Override
    public void actMovement(Unit me, double factor) {
        GroundVehicle car = (GroundVehicle) me;
        
        if(getOwner().getDestinations().size() > 0 && getOwner().willMove() && car.hasPilot()){
            //boolean movingToUnit = false;
            
            
            Coordinate destination = null;
            try {
                destination = (Coordinate) getOwner().getDestinations().get(0);
            } catch(Exception e){
                try {
                    destination = LevelManager.getLevel().getUnit(((String)(getOwner().getDestinations().get(0))).substring("[UNIT]".length())).getPosition();
                    //movingToUnit = true;
                } catch(Exception ex){
                    if(getOwner().getDestinations().get(0).toString().indexOf("[UNIT]") == 0){
                        getOwner().getDestinations().remove(0);
                    }
                    
                    //Stop the car
                    Vector acc = new Vector(new Vector(car.getFacing().unitVector(),-1),car.getMaxAcc()*factor);
                    
                    car.getVelocity().addVectorToThis(acc);
                    
                    return;
                }
            }
            
            destination = new Coordinate(destination.X(), me.getPosition().Y(), destination.Z());
            
            double dist = Math.sqrt(Math.pow(me.getPosition().X() - destination.X() , 2) + Math.pow(me.getPosition().Z() - destination.Z() , 2));
            if(dist > 5){
                
                Vector acc;
                
                if(dist < 0.9 * Math.pow(me.getVelocity().getMagnitude(),2)/(2*car.getMaxAcc())){
                    //Stop to avoid impact
                    acc = (new Vector(new Vector(car.getFacing().unitVector(),-1),car.getMaxAcc()));
                } else {
                    
                    double cosine = Vector.cosOfAngleBetween(car.getFacing(), new Vector(car.getPosition(),destination));
                    
                    /*
                    if(cosine > Math.cos(Math.toRadians(10)) || car.getVelocity().getMagnitude() < Math.sqrt(car.getMaxAcc())){
                        //Drive forward
                        acc = (new Vector(car.getFacing().unitVector(),car.getMaxAcc()));
                        
                        
                    } else if(car.getMaxAcc() < 2.1 * car.getVelocity().getMagnitude() / dist){
                        System.out.println("Slowing down...");
                        //Slow down
                        acc = (new Vector(car.getFacing().unitVector(),-car.getMaxAcc()));
                    
                    } else {
                        //Turn
                        double dir = -Math.PI * Math.signum(Math.sin(car.getFacing().getAngleXZ() - (new Vector(car.getPosition(),destination)).getAngleXZ())) / 2.0;
                        
                        acc = new Vector(car.getMaxAcc(),car.getFacing().getAngleXZ() + dir, 0);
                    }
                    */
                    
                    if(cosine > Math.cos(Math.toRadians(10)) || car.getVelocity().getMagnitude() < Math.sqrt(car.getMaxAcc())){
                        
                        acc = (new Vector(car.getFacing().unitVector(),car.getMaxAcc()));
                        
                        
                    } else {
                        
                        if(car.getMaxAcc() < 2.1 * Math.pow(car.getVelocity().getMagnitude(),2) / dist){
                            
                            //Slow down
                            acc = (new Vector(car.getFacing().unitVector(),-car.getMaxAcc()));

                        } else {
                            //Turn
                            double dir = -Math.PI * Math.signum(Math.sin(car.getFacing().getAngleXZ() - (new Vector(car.getPosition(),destination)).getAngleXZ())) / 2.0;
                            
                            acc = new Vector(car.getMaxAcc() * Math.min(1, car.getVelocity().getMagnitude() / Math.sqrt(car.getMaxAcc())) ,car.getFacing().getAngleXZ() + dir, 0);
                        }
                    }
                    
                    
                    
                }
                
                //Cars should accelerate along the ground, not up and down!
                acc = new Vector(acc.getMagnitude(), acc.getAngleXZ(), 0);
                
                car.getVelocity().addVectorToThis(new Vector(acc,factor));
            } else {
                getOwner().getDestinations().remove(0);
                actMovement(me,factor);
            }
        } else if(car.getVelocity().getMagnitude() > 0){
            //Stop the vehicle; there's nowhere to go.
            Vector acc = new Vector(new Vector(car.getFacing().unitVector(),-1),car.getMaxAcc()*factor);
            
            
            car.getVelocity().addVectorToThis(acc);
        }
    }

}
