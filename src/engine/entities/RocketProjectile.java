/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.entities;

import engine.physics.Coordinate;
import engine.physics.Vector;

/**
 *
 * @author Christopher
 */
public class RocketProjectile extends Projectile{
    
    private final double acceleration;
    
    public RocketProjectile(Coordinate coord, Vector vel, double mss, double expPow, String sh, double acc) {
        super(coord, vel, mss, expPow, sh);
        acceleration = acc;
    }
    
    @Override
    public void cycle(double factor){
        //The RocketProjectile should be constantly accelerating forward.
        if(velocity.getMagnitude() > 0) this.velocity.addVectorToThis(new Vector(velocity.unitVector(), acceleration * factor));
        
        //Other than that, it acts exactly like a normal projectle.
        super.cycle(factor);
        
    }
    
}
