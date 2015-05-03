/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.units;

import engine.entities.interfaces.brains.AircraftBrain;
import engine.entities.interfaces.brains.UnitBrain;
import engine.entities.items.Inventory;
import engine.physics.Coordinate;
import engine.physics.Vector;

/**
 *
 * @author Christopher Hittner
 */
public class Aircraft extends AirUnit{

    protected final double MIN_VEL, TURN_RATE;
    
    public Aircraft(Coordinate coord, float HP, float size, Inventory i, int troopCapacity, int vehicleSpace, int flierSpace, boolean requiresPilot, double acc, double topSpeed, double minSpeed, double turnRate, String name) {
        super(coord, HP, size, i, new AircraftBrain(name), troopCapacity, vehicleSpace, flierSpace, requiresPilot, acc, topSpeed);
        MIN_VEL = minSpeed;
        TURN_RATE = turnRate;
        brain.giveOrder("orbit " + (coord.X()) + " " + (coord.Z()+Math.sqrt(minSpeed*topSpeed)/Math.pow(turnRate, 2)), "-2");
        velocity = new Vector(Math.sqrt(minSpeed*topSpeed),0,0);
    }
    
    /*
    public void cycle(double factor){
        //velocity.addVectorToThis(new Vector(9.81*factor,0,Math.toRadians(90)));
        super.cycle(factor);
        //System.out.println(Math.toDegrees(velocity.getAngleXZ()));
    }
    */
    
    public double getTurnRate() {
        return TURN_RATE;
    }

    public double minVel() {
        return MIN_VEL;
    }

}
