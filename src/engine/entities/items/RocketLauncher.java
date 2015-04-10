/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.entities.items;

import engine.entities.Projectile;
import engine.entities.RocketProjectile;
import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.world.LevelManager;

/**
 *
 * @author Christopher
 */
public class RocketLauncher extends Weapon{
    
    protected double thrust;
    
    public RocketLauncher(double MASS, String NAME, Resource AMMO, double PROJ_VEL, double PROJ_MASS, double EXP_POW, double FIRE_TIME, double START_DIST, double ACC) {
        super(MASS, NAME, AMMO, PROJ_VEL, PROJ_MASS, EXP_POW, FIRE_TIME, START_DIST);
        thrust = ACC;
    }
    
    @Override
    public void fireProjectile(Coordinate start, Vector vel, String nm){
        
        LevelManager.getLevel().getProjectiles().add(new RocketProjectile(start, vel, projMass, projExpPow, nm, thrust));
        
    }
    
}
