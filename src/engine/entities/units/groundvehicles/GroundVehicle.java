/*
 * A GroundVehicle is an object that moves like a car. It can be
 * used to describe cars, tanks, etc.
 */
package engine.entities.units.groundvehicles;

import engine.entities.units.soldiers.Soldier;
import engine.entities.interfaces.CarriesTroops;
import engine.entities.interfaces.brains.GroundVehicleBrain;
import engine.entities.items.Inventory;
import engine.entities.units.AirUnit;
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
public class GroundVehicle extends Unit implements CarriesTroops {
    
    protected final double MAX_VEL, ACC;
    protected final boolean needsPilot;
    protected Vector direction = new Vector(1,0,0);
    protected Soldier[] soldiers;
    protected GroundVehicle[] vehicles;
    protected AirUnit[] fliers;
    
    /**
     *
     * @param coord The initial position.
     * @param HP The base health.
     * @param size The radius.
     * @param i The Unit's inventory.
     * @param vel The maximum speed.
     * @param acc The vehicle's 
     * @param troopCapacity The amount of Soldiers the craft can carry.
     * @param vehicleSpace The number of GroundVehicles the craft can carry.
     * @param flierSpace The number of AirUnits the craft can carry.
     * @param requiresPilot Whether or not the craft requires a pilot.
     * @param name The name of the craft.
     */
    public GroundVehicle(Coordinate coord, float HP, float size, Inventory i, double vel, double acc, int troopCapacity, int vehicleSpace, int flierSpace, boolean requiresPilot, String name) {
        super(coord, HP, size, i, new GroundVehicleBrain(name));
        MAX_VEL = vel;
        ACC = acc;
        
        needsPilot = requiresPilot;
        
        soldiers = new Soldier[troopCapacity];
        vehicles = new GroundVehicle[vehicleSpace];
        fliers = new AirUnit[flierSpace];
    }
    
    public void cycle(double factor){
        
        if(velocity.getMagnitude() > 0.0)
            direction = velocity.unitVector();
        
        super.cycle(factor);
        
        velocity.multiplyMagnitude(Math.min(1, MAX_VEL / velocity.getMagnitude()));
        
    }
    
    public double getMaxVel(){ return MAX_VEL; }
    public double getMaxAcc(){ return ACC; }
    public Vector getFacing(){ return direction; }
    
    public boolean hasPilot(){
        
        if(!needsPilot)
            return true;
        
        for(Soldier s : soldiers)
            if(s != null)
                return true;
        
        return false;
    }
    
    
    @Override
    public Unit board(Unit u) {
        
        if(!canBoard(u))
            return u;
        
        if(u instanceof Soldier) for(int i = 0; i < soldiers.length; i++)
            if(soldiers[i] == null){
                soldiers[i] = (Soldier) u;
                return null;
            }
        if(u instanceof GroundVehicle) for(int i = 0; i < vehicles.length; i++)
            if(vehicles[i] == null){
                vehicles[i] = (GroundVehicle) u;
                return null;
            }
        if(u instanceof AirUnit) for(int i = 0; i < fliers.length; i++)
            if(fliers[i] == null){
                fliers[i] = (AirUnit) u;
                return null;
            }
        
        return u;
        
    }

    @Override
    public Unit unboard(String nm) {
        Unit result = null;
        
        for(int i = 0; i < soldiers.length; i++)
            if(soldiers[i] != null) if(soldiers[i].getName().toLowerCase().equals(nm.toLowerCase())){
                result = soldiers[i];
                soldiers[i] = null;
                break;
            }
        
        if(result == null) for(int i = 0; i < vehicles.length; i++)
            if(vehicles[i] != null) if(vehicles[i].getName().toLowerCase().equals(nm.toLowerCase())){
                result = vehicles[i];
                vehicles[i] = null;
                break;
            }
        
        if(result == null) for(int i = 0; i < fliers.length; i++)
            if(fliers[i] != null) if(fliers[i].getName().toLowerCase().equals(nm.toLowerCase())){
                result = fliers[i];
                fliers[i] = null;
                break;
            }
        
        if(result != null){
            result.getPosition().addVector(new Vector(result.getPosition(),this.getPosition()));
            result.getPosition().addVector(new Vector(getSize() + result.getSize(), 0, 0));
        }
        
        return result;
    }

    @Override
    public ArrayList<Unit> unloadAll(boolean removePilot) {
        ArrayList<Unit> units = new ArrayList<>();
        
        boolean pilotIn = removePilot;
        
        for(Soldier u : soldiers)
            if(pilotIn){
                if(u != null)
                units.add(u);
            }else pilotIn = true;
        soldiers = new Soldier[soldiers.length];
        
        for(GroundVehicle u : vehicles)
            if(u != null)
                units.add(u);
        vehicles = new GroundVehicle[vehicles.length];
        
        for(AirUnit u : fliers)
            if(u != null)
                units.add(u);
        fliers = new AirUnit[fliers.length];
        
        for(int i = 0; i < units.size(); i++){
            //Moves it to the center of the transport for future relocation.
            units.get(i).getPosition().addVector(new Vector(units.get(i).getPosition(),this.getPosition()));
            //Makes sure it is outside.
            units.get(i).getPosition().addVector(new Vector(getSize() + units.get(i).getSize(), 2*Math.PI*i/units.size(), 0));
            //Makes sure it is sitting on the ground.
            units.get(i).getPosition().addVector(new Vector(this.getPosition().Y() + 0.1 - this.getSize() - units.get(i).getPosition().Y(), 0, Math.toRadians(90)));
            //Sets velocity equal to the vehicle's velocity.
            units.get(i).getVelocity().multiplyMagnitude(0);
            units.get(i).getVelocity().addVectorToThis(velocity);
        }
        
        
        return units;
    }

    @Override
    public boolean canBoard(Unit u) {
        
        if(FactionManager.getRelationship(FactionManager.getFactionOf(getName()), FactionManager.getFactionOf(u.getName())))
            if(u instanceof Soldier){
                for(Soldier s : soldiers)
                    if(s == null)
                        return true;
            } else if(u instanceof GroundVehicle){
                for(GroundVehicle s : vehicles)
                    if(s == null)
                        return true;
            } else if(u instanceof AirUnit){
                for(AirUnit s : fliers)
                    if(s == null)
                        return true;
            }
        
        return false;
        
    }
    
}
