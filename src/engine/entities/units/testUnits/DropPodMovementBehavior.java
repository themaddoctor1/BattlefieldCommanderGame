/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.entities.units.testUnits;

import engine.entities.interfaces.brains.UnitBrain;
import engine.entities.interfaces.brains.behaviors.movementbehavior.MovementBehavior;
import engine.entities.terrain.TerrainElement;
import engine.entities.units.Unit;
import engine.physics.*;
import engine.world.LevelManager;

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
        
        double height = position.Y() - me.getSize();
        
        double offset = 0;
        for(int i = 0; i < LevelManager.getLevel().getTerrain().size(); i++){
            TerrainElement te = LevelManager.getLevel().getTerrain().get(i);
            double diff = te.getPosition().Y() + te.getSize()[1]/2.0;
            if(te.getPosition().Y() + te.getSize()[1]/2.0 > offset
                    && (Math.max(te.getPosition().X() - te.getSize()[0]/2, Math.min(position.X(), te.getPosition().X() + te.getSize()[0]/2)) == position.X())
                    && (Math.max(te.getPosition().Z() - te.getSize()[2]/2, Math.min(position.Z(), te.getPosition().Z() + te.getSize()[2]/2)) == position.Z()))
                offset = diff;
            
        }
        
        height -= offset;
        
        if(     -velocity.getMagnitudeY() >= 0.9*Math.sqrt(Math.abs(2*(40 - 10) * (height)))
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
