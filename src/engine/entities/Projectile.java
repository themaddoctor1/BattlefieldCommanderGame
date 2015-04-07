/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        shooter = sh;
    }

    @Override
    public void cycle(double factor) {
        
        velocity.addVectorToThis(new Vector(9.81 * factor, 0, -Math.toRadians(90)));
        
        move(factor);
        for(Unit u : LevelManager.getLevel().getUnits())
            if(Coordinate.relativeDistance(u.getPosition(), coordinate) < u.getSize() && !u.getName().equals(shooter)){
                u.harm((float)(velocity.getMagnitude()*mass));
                
                if(u.getHP() <= 0)
                    LevelManager.addEvent(shooter + " killed " + u.getName());
                
                killSelf();
                return;
            }
        if(coordinate.Y() <= 0){
            killSelf();
        }
        
    }

    @Override
    public void move(double factor) {
        coordinate.addVector(new Vector(velocity, factor));
    }

    private void killSelf() {
        if(explosiveEnergy > 0) for(int i = 0; i < LevelManager.getLevel().getUnits().size(); i++){
            Unit u = LevelManager.getLevel().getUnits().get(i);
            //Hurt all Units in range of its explosive damage
            u.harm((float)(Math.max(explosiveEnergy/
                    (Coordinate.relativeDistance(coordinate, u.coordinate))
                 - 25200, 0)));
            
            
        }
        
        //Remove this entity after doing that
        LevelManager.getLevel().getProjectiles().remove(this);
        
    }

}
