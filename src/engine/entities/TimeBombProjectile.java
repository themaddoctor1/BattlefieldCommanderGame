/*
 * A TimeBombProjectile is a Projectile that does not die upon impact.
 */

package engine.entities;

import engine.entities.units.Unit;
import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.world.LevelManager;

/**
 *
 * @author Christopher Hittner
 */
public class TimeBombProjectile extends Projectile {
    private final double FUSE;
    private double time = 0;

    public TimeBombProjectile(Coordinate coord, Vector vel, double mss, double expPow, String sh, double timer) {
        super(coord, vel, mss, expPow, sh);
        FUSE = timer;
    }
    
    @Override
    public void cycle(double factor){
        
        super.cycle(factor);
        
        for(Unit u : LevelManager.getLevel().getUnits())
            if(Coordinate.relativeDistance(u.getPosition(), coordinate) < u.getSize() && !u.getName().equals(shooter)){
                Vector relative = new Vector(velocity, 1);
                relative.addVectorToThis(new Vector(u.getVelocity(), -1));
                double vel = relative.getMagnitude() * Vector.cosOfAngleBetween(relative, new Vector(getPosition(), u.getPosition()));
                velocity.addVectorToThis(new Vector(new Vector(getPosition(), u.getPosition()).unitVector(), -1.5* vel));
            }
        if(coordinate.Y() <= 0){
            velocity.addVectorToThis(new Vector(1.5 * velocity.getMagnitudeY(), 0, Math.toRadians(90)));
        }
        
        time += factor;
    }
    
    @Override
    public void killSelf(){
        if(time < FUSE)
            return;
        super.killSelf();
    }
    
    
}
