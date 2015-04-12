/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.items;

import engine.entities.TimeBombProjectile;
import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.world.LevelManager;

/**
 *
 * @author Christopher Hittner
 */
public class TimeBombLauncher extends Weapon{
    
    private final double timer;
    
    public TimeBombLauncher(String NAME, Resource AMMO, double PROJ_VEL, double PROJ_MASS, double EXP_POW, double FIRE_TIME, double START_DIST, double TIMER) {
        super(0, NAME, AMMO, PROJ_VEL, PROJ_MASS, EXP_POW, FIRE_TIME, START_DIST);
        timer = TIMER;
    }
    
    @Override
    public void fireProjectile(Coordinate start, Vector vel, String nm){
        
        LevelManager.getLevel().getProjectiles().add(new TimeBombProjectile(start, vel, projMass, projExpPow, nm, timer));
        
    }

}
