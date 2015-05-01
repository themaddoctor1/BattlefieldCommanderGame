/*
 * A Level is a series of Entities that are continuously simulated and accessed
 * by the LevelManager.
 */

package engine.world;

import engine.entities.terrain.TerrainElement;
import engine.entities.units.Unit;
import engine.entities.*;
import engine.game.FactionManager;
import engine.physics.Coordinate;
import engine.physics.Vector;
import java.util.ArrayList;

/**
 *
 * @author Christopher Hittner
 */
public class Level {
    
    
    ArrayList<TerrainElement> terrain = new ArrayList<>();
    ArrayList<Unit> units = new ArrayList<>();
    ArrayList<Projectile> projectiles = new ArrayList<>();
    
    public Level(){
        
    }
    
    public Level(ArrayList<TerrainElement> terr, ArrayList<Unit> people){
        terrain.addAll(terr);
        units.addAll(people);
    }
    
    
    
    
    
    public Level(ArrayList<Entity> entities){
        
        for(Entity e : entities){
            if(e instanceof TerrainElement)
                terrain.add((TerrainElement) e);
            else if(e instanceof Unit)
                units.add((Unit) e);
            else if(e instanceof Projectile)
                projectiles.add((Projectile) e);
        }
    }
    
    public void cycle(double factor){
        /*
        executeTerrainCollisions();
        
        executeEntityCycles(factor);
        */
    }
    
    public void executeTerrainCollisions(){
        for(int i = 0; i < terrain.size(); i++){ TerrainElement te = terrain.get(i); 
            for(int j = 0; j < units.size(); j++){
                Unit u = units.get(j);
                Coordinate terr = te.getPosition(), un = u.getPosition();

                //This and the for loop check whether to collide
                boolean colliding = te.collidingWith(u);

                if(colliding){

                    double[] hitBox = te.getSize();
                    double size = u.getSize();

                    double x = Math.signum(un.X() - terr.X()) * ((size + hitBox[0]/2.0) - Math.abs(un.X() - terr.X()));

                    double z = Math.signum(un.Z() - terr.Z()) * ((size + hitBox[2]/2.0) - Math.abs(un.Z() - terr.Z()));

                    double xRatio = Math.abs(x/hitBox[0]), /*yRatio = Math.abs(y/hitBox[1]),*/ zRatio = Math.abs(z/hitBox[2]);


                    if(un.Y() > (terr.Y() + hitBox[1]/2.0)){
                        //On the top of a TerrainElement

                        u.fallDamage();

                        u.getPosition().addVector(new Vector((terr.Y() + hitBox[1]/2.0 + size) - un.Y(), 0, Math.toRadians(90)));
                        u.getVelocity().addVectorToThis(new Vector(u.getVelocity().getMagnitudeY(), 0, -Math.toRadians(90)));
                    } else if(/*xRatio >= yRatio && */xRatio < zRatio){
                        u.getPosition().addVector(new Vector(x, 0, 0));
                    } else if(/*zRatio >= yRatio && */zRatio < xRatio){
                        u.getPosition().addVector(new Vector(z, Math.toRadians(90), 0));
                    }



                }

            }
        }
    }
    
    public ArrayList<Projectile> getProjectiles(){ return projectiles; }
    public ArrayList<TerrainElement> getTerrain(){ return terrain; }
    public ArrayList<Unit> getUnits(){ return units; }
    
    public void addUnit(Unit u, String factID){
        units.add(u);
        FactionManager.addFactionMember(u.getName(), (factID));
    }
    
    
    public void executeEntityCycles(double factor) {
        for(TerrainElement te : terrain)
            te.cycle(factor);
        
        for(int i = 0; i < units.size(); i++)
            try {
                units.get(i).cycle(factor);
            } catch(Exception e){
                
            }
        
        for(int i = 0; i < projectiles.size(); i++){
            projectiles.get(i).cycle(factor);
        }
    }

    public Unit getUnit(String name) {
        for(int i = 0; i < units.size(); i++){
            Unit u = units.get(i);
            if(u.getName().toLowerCase().equals(name.toLowerCase()))
                return u;
        }
        return null;
    }

    public void executeEntityCollisions() {
        for(Unit u : units) for(Unit v : units) if(!u.getName().equals(v.getName())){
            double minDist = 0.9 * (u.getSize() + v.getSize());
            double actualDist = Coordinate.relativeDistance(v.getPosition(), u.getPosition());
            if(actualDist < minDist){
                Vector uChange = new Vector(new Vector(u.getPosition(),v.getPosition()).unitVector(),-(minDist - actualDist) * v.getSize() / (minDist/0.9));
                Vector vChange = new Vector(new Vector(v.getPosition(),u.getPosition()).unitVector(),-(minDist - actualDist) * u.getSize() / (minDist/0.9));
                u.getPosition().addVector(uChange);
                v.getPosition().addVector(vChange);
            }
        }
    }
    
    
    
}
