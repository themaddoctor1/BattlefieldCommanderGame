/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.entities.interfaces.brains.behaviors.combatbehavior;

import engine.entities.interfaces.brains.UnitBrain;
import engine.entities.items.Item;
import engine.entities.items.Weapon;
import engine.entities.units.Unit;
import engine.game.FactionManager;
import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.world.LevelManager;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class LimitedLeadingCombatBehavior extends CombatBehavior{
    
    private final double thetaMax;
    
    public LimitedLeadingCombatBehavior(UnitBrain brain, double theta){
        super(brain);
        thetaMax = theta;
    }
    
    
    public LimitedLeadingCombatBehavior(double theta){
        super();
        thetaMax = theta;
    }
    
    
    @Override
    public void actCombat(Unit me, double factor) {
        
        UnitBrain brain = this.getOwner();
        
        if(!brain.willShoot()){
            brain.setAttack(false);
            return;
        }
        
        try {
            
            //Tries to find a unit to shoot at, if possible
            
            ArrayList<Unit> units;
            try {
                units = getOwner().getAwarenessProcess().getUnitsAwareOf(me);
            } catch(Exception e){
                e.printStackTrace();
                units = LevelManager.getLevel().getUnits();
            }
            Unit targ;
            
            boolean targeting;
            try {
                String targetName = brain.getDestinations().get(0).toString().substring(6);
                boolean relationship = FactionManager.getRelationship(FactionManager.getFactionOf(brain.getName()), FactionManager.getFactionOf(targetName));
                targeting = (brain.getDestinations().get(0).toString().indexOf("[UNIT]") == 0 && !relationship);
            } catch(Exception ex){
                targeting = false;
            }
            
            //Gets closest Unit
            if(targeting)
                try{
                    targ = LevelManager.getLevel().getUnit(brain.getDestinations().get(0).toString().substring(6));
                } catch(NullPointerException npe){
                    targ = units.get(0);
                    targeting = false;
                }
            else
                targ = units.get(0);
            
            if(!targeting)
            for(int i = 1; i < units.size(); i++){
                Unit u = units.get(i);

                if(targ.getName().equals(brain.getName()) || FactionManager.getRelationship(FactionManager.getFactionOf(brain.getName()), FactionManager.getFactionOf(targ.getName()))) {
                    targ = u;

                } else if(FactionManager.getRelationship(FactionManager.getFactionOf(brain.getName()), FactionManager.getFactionOf(u.getName())) == false
                        && (Coordinate.relativeDistance(me.getPosition(), u.getPosition()) < Coordinate.relativeDistance(me.getPosition(), targ.getPosition())) ){ 
                        targ = u;
                }
            }
            
            
            
            //Attacks the target if it is a legitimate target
            if(!FactionManager.getRelationship(FactionManager.getFactionOf(me.getName()), FactionManager.getFactionOf(targ.getName())))
                attackUnit(me, targ);
            else
                brain.setAttack(false);
            
            
            
        } catch(Exception e){
            brain.setAttack(false);
        }
    }
    
    /**
     * Attacks a Unit, as long as it is in range.
     * @param me The owner of the Brain. A Unit object will send itself to fulfill this parameter.
     * @param targ The Unit being targeted.
     */
    @Override
    public void attackUnit(Unit me, Unit targ){
        
        me.getBrain().getAwarenessProcess().combatNotification(targ);
        
        for(Item weapon : me.getInventory().getItems())
            if(weapon instanceof Weapon && ((Weapon) weapon).getAmmo().amt() > 0){
                //The Entity will attempt to shoot if the target is in range.
                double V = ((Weapon) weapon).getMuzzleVelocity();
                double X = Math.sqrt(Math.pow(me.getPosition().X() - targ.getPosition().X(), 2) + Math.pow(me.getPosition().Z() - targ.getPosition().Z(), 2));
                double Y = targ.getPosition().Y() - me.getPosition().Y();
                
                //Makes sure the Unit will only aim a certain amount away from the target in order to hit it
                if(Math.pow(V, 4) > 9.81*(9.81*Math.pow(X, 2)+2*Y*Math.pow(V, 2)) && ((Weapon) weapon).getAmmo().amt() > 0){
                    double angle = Math.atan((Math.pow(V, 2) - Math.sqrt( Math.pow(V, 4) - 9.81 * (9.81 * Math.pow(X, 2) + 2*Y*Math.pow(V, 2) ) ) ) / (9.81*X));
                    
                    if(thetaMax >= Math.acos(Vector.cosOfAngleBetween(
                            new Vector(me.getPosition(), targ.getPosition()),
                            new Vector(1, new Vector(
                                    me.getPosition(), 
                                    targ.getPosition()).getAngleXZ(), 
                                    angle)
                    ))){
                        ((Weapon) weapon).use(1,"{" + me.getName() + "}{" + Math.cos((new Vector(me.getPosition(),targ.getPosition())).getAngleXZ()) + "," + Math.atan(angle) + "," + Math.sin((new Vector(me.getPosition(),targ.getPosition())).getAngleXZ()) + "}");

                        me.getBrain().setAttack(true);
                        return;
                    }
                }
            }
        
        me.getBrain().setAttack(false);
    }
    
}
