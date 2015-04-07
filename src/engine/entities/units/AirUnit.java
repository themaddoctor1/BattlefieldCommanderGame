/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.units;

import engine.entities.units.groundvehicles.GroundVehicle;
import engine.entities.units.soldiers.Soldier;
import engine.entities.interfaces.CarriesTroops;
import engine.entities.interfaces.brains.UnitBrain;
import engine.entities.items.Inventory;
import engine.game.FactionManager;
import engine.physics.*;
import java.util.ArrayList;

/**
 *
 * @author Christopher Hittner
 */
public abstract class AirUnit extends Unit implements CarriesTroops{
    protected Soldier[] soldiers;
    protected GroundVehicle[] vehicles;
    protected AirUnit[] fliers;
    
    protected boolean needsPilot;
    
    /**
     * The maximum value that the magnitude of engineAcc and velocity can have.
     */
    protected final double MAX_ACC, MAX_VEL;
    
    public AirUnit(Coordinate coord, float HP, float size, Inventory i, UnitBrain u, int troopCapacity, int vehicleSpace, int flierSpace, boolean requiresPilot, double acc, double topSpeed) {
        super(coord, HP, size, i, u);
        velocity = new Vector(0,0,0);
        MAX_ACC = acc;
        MAX_VEL = topSpeed;
        
        needsPilot = requiresPilot;
        
        soldiers = new Soldier[troopCapacity];
        vehicles = new GroundVehicle[vehicleSpace];
        fliers = new AirUnit[flierSpace];
    }
    
    public void cycle(double factor){
        //Engine force
        
        super.cycle(factor);
        
        //Air resistance, designed to enforce the top speed of the AirUnit
        //velocity.addVectorToThis(new Vector(velocity.unitVector() ,-Math.pow(velocity.getMagnitude(), 2) * Math.sqrt(Math.pow(MAX_ACC, 2) - Math.pow(9.81, 2)) / Math.pow(MAX_VEL, 2)));
        
        velocity.multiplyMagnitude(Math.min(1, MAX_VEL / velocity.getMagnitude()));
        
    }
    
    
    public double maxAcc() { return MAX_ACC; }
    public double maxVel() { return MAX_VEL; }
    
    
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
        ArrayList<Unit> units = new ArrayList<Unit>();
        
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
