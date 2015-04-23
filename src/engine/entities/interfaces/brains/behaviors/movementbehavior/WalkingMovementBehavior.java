/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.interfaces.brains.behaviors.movementbehavior;

import engine.entities.interfaces.brains.UnitBrain;
import engine.entities.items.Item;
import engine.entities.units.Unit;
import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.world.LevelManager;

/**
 *
 * @author Christopher Hittner
 */
public class WalkingMovementBehavior extends MovementBehavior {

    public WalkingMovementBehavior(UnitBrain brain) {
        super(brain);
    }
    
    public WalkingMovementBehavior(){ super(); }
    
    
    @Override
    public void actMovement(Unit me, double factor){
        if(getOwner().getDestinations().size() > 0 && getOwner().willMove()){
            Coordinate destination = null;
            try {
                destination = (Coordinate) getOwner().getDestinations().get(0);
            } catch(Exception e){
                try {
                    destination = LevelManager.getLevel().getUnit(((String)(getOwner().getDestinations().get(0))).substring("[UNIT]".length())).getPosition();
                } catch(Exception ex){
                    if(getOwner().getDestinations().get(0).toString().indexOf("[UNIT]") == 0){
                        getOwner().getDestinations().remove(0);
                    }
                    return;
                }
            }
            
            if(Math.sqrt(Math.pow(me.getPosition().X() - destination.X() , 2) + Math.pow(me.getPosition().Z() - destination.Z() , 2)) > 2){

                Vector direction = new Vector(me.getPosition(), destination).unitVector();
                direction = (new Vector(1, direction.getAngleXZ(), 0));
                
                double mass = 70;
                for(Item i : me.getInventory().getItems())
                    mass += i.mass();
                
                direction.multiplyMagnitude(140/mass);
                
                me.getVelocity().addVectorToThis(new Vector(me.getVelocity(),-1));
                me.getVelocity().addVectorToThis(direction);

            } else {
                getOwner().getDestinations().remove(0);
                actMovement(me,factor);
            }
        } else {
            me.getVelocity().multiplyMagnitude(0);
        }
    }
}
