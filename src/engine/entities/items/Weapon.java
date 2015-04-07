/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.items;

import engine.entities.Projectile;
import engine.entities.units.soldiers.Soldier;
import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.world.LevelManager;

/**
 *
 * @author Christopher Hittner
 */
public class Weapon extends Item{
    
    protected String name;
    protected double projVel, projMass, projExpPow, startDist, fireRate;
    protected Resource ammo;
    private long lastUsed;
    
    public Weapon(double MASS, String NAME, Resource AMMO, double PROJ_VEL, double PROJ_MASS, double EXP_POW, double FIRE_TIME, double START_DIST){
        super(MASS);
        name = NAME;
        ammo = AMMO;
        projVel = PROJ_VEL;
        projMass = PROJ_MASS;
        projExpPow = EXP_POW;
        startDist = START_DIST;
        fireRate = FIRE_TIME;
        lastUsed = System.currentTimeMillis();
    }

    /**
     * Fires the weapon.
     * @param factor
     * @param extraParam The parameter for this object, written as {X1,Y1,Z1}{X2,Y2,Z2} for the coordinate of the owner and target, in that order.
     */
    @Override
    public void use(double factor, String extraParam){
        if((double)(System.currentTimeMillis() - lastUsed)/1000 < fireRate || ammo.amt() <= 0)
            return;
        //Parses the target position
        String paramA = (extraParam.substring(extraParam.indexOf("}")+2,extraParam.length()-1)) + ",";
        String nm = extraParam.substring(1,extraParam.indexOf("}"));
        
        
        double[] targ = new double[3];
        for(int i = 0; i < 3; i++){
            targ[i] = Double.parseDouble(paramA.substring(0, paramA.indexOf(",")));
            paramA = paramA.substring(paramA.indexOf(",")+1);
            
        }
        
        
        Coordinate start = new Coordinate(LevelManager.getLevel().getUnit(nm).getPosition().X()
                                        , LevelManager.getLevel().getUnit(nm).getPosition().Y()
                                        , LevelManager.getLevel().getUnit(nm).getPosition().Z());
        
        //Finds the aim direction
        Vector dir = new Vector(new Coordinate(0,0,0), new Coordinate(targ[0],targ[1],targ[2]));
        
        double xz = Math.random() * 2*Math.PI;
        double y = Math.random() * 2*Math.PI;
        
        if(System.currentTimeMillis() - lastUsed < 750 && LevelManager.getLevel().getUnit(nm) instanceof Soldier){
            dir.addVectorToThis(new Vector(Math.pow(10,10)*(projMass*projVel)/(mass*lastUsed), xz, y));
        }
        
        //Makes dir a unit Vector.
        dir = dir.unitVector();
        
        start.addVector(new Vector(dir,startDist));
        
        Vector vel = new Vector(dir,projVel);
        vel.addVectorToThis(LevelManager.getLevel().getUnit(nm).getVelocity());
        
        LevelManager.getLevel().getProjectiles().add(new Projectile(start, vel, projMass, projExpPow, nm));
        
        lastUsed = System.currentTimeMillis();
        
        ammo.modAmt(-1);
    }

    public double getMuzzleVelocity() {
        return projVel;
    }

    public Resource getAmmo() {
        return ammo;
    }
    
}
