/*
 * A Projectile object is an Entity that harms Units. It has a velocity, mass, and
 * therefore a momentum, which can be used to calculate damage. It also has the ability
 * to exert splash damage (or explode, if you prefer that explanation).
 */

package engine.entities;

import engine.entities.units.Unit;
import engine.physics.*;
import engine.world.LevelManager;

/**
 *
 * @author Christopher Hittner
 */
public class Projectile extends MovableEntity{
    
    Vector velocity;
    double mass, explosiveEnergy;
    String shooter;
    
    public Projectile(Coordinate coord, Vector vel, double mss, double expPow, String sh) {
        super(coord);
        velocity = vel;
        mass = mss;
        explosiveEnergy = expPow;
        shooter = sh;
    }

    @Override
    public void cycle(double factor) {
        
        velocity.addVectorToThis(new Vector(9.81 * factor, 0, -Math.toRadians(90)));
        
        double forceDrag = -0.003 * Math.pow(velocity.getMagnitude(), 2) * (this.coordinate.Y() / 75000);
        
        //Air resistance
        velocity.addVectorToThis(new Vector(velocity.unitVector(), forceDrag / mass * factor));
        
        move(factor);
        for(Unit u : LevelManager.getLevel().getUnits())
            if(Coordinate.relativeDistance(u.getPosition(), coordinate) < u.getSize() && !u.getName().equals(shooter)){
                u.harm((float)((velocity.getMagnitude())*mass));
                
                if(u.getHP() <= 0) LevelManager.addEvent(shooter + " killed " + u.getName());
                
                killSelf();
                return;
            }
        //If on the ground     or     momentum is less than 0.01
        if(coordinate.Y() <= 0 || velocity.getMagnitude()*mass < 0.01){
            killSelf();
        }
        
    }

    @Override
    public void move(double factor) {
        coordinate.addVector(new Vector(velocity, factor));
    }

    public void killSelf() {
        if(explosiveEnergy > 0) for(int i = 0; i < LevelManager.getLevel().getUnits().size(); i++){
            Unit u = LevelManager.getLevel().getUnits().get(i);
            //Hurt all Units in range of its explosive damage
            double splash = Math.max(explosiveEnergy/
                    (Coordinate.relativeDistance(coordinate, u.coordinate) - (u.getSize())/2.0)
                 - 100, 0);
            
            u.harm((float)(splash));
            
            
        }
        
        //Remove this entity after doing that
        LevelManager.getLevel().getProjectiles().remove(this);
        
    }

}
