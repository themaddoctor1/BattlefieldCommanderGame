/*
 * The Helicopter class is a class that is a possible form of an AirUnit. It can carry troops, and acts like a Helicopter.
 */

package engine.entities.units.helicopters;

import engine.entities.interfaces.brains.HelicopterBrain;
import engine.entities.items.Inventory;
import engine.entities.units.AirUnit;
import engine.physics.Coordinate;
import engine.physics.Vector;

/**
 *
 * @author Christopher Hittner
 */
public class Helicopter extends AirUnit{
    
    protected Vector engineAcc = new Vector(9.81,0, Math.toRadians(90));
    
    
    public Helicopter(Coordinate coord, float HP, float size, Inventory i, int troopCapacity, int vehicleSpace, int flierSpace, boolean requiresPilot, double acc, double topSpeed, String name) {
        super(coord, HP, size, i, new HelicopterBrain(name), troopCapacity, vehicleSpace, flierSpace, requiresPilot, acc, topSpeed);
    }
    
    @Override
    public void cycle(double factor){
        Vector acceleration = new Vector(engineAcc, factor);
        velocity.addVectorToThis(acceleration);
        
        //velocity.addVectorToThis(new Vector(9.81*factor,0,Math.toRadians(-90)));
        super.cycle(factor);
    }
    
    /**
     * Returns a Vector describing the direction in which the rotors are currently pointing.
     * @return
     */
    public Vector getEngineAcceleration(){ return engineAcc; }

}
