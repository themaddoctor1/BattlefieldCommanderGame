/*
 * This interface is used by the GroundVehicle and AirUnit classes, as well as any
 * subclasses they may have, or any other classes that want to store Units.
 * The purpose of this class is to assist with the simulation of transportation
 * capabilities for vehicles and aircraft.
 */
package engine.entities.interfaces;

import engine.entities.units.Unit;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public interface CarriesTroops {
    
    public Unit board(Unit u);
    
    public Unit unboard(String nm);
    
    public ArrayList<Unit> unloadAll(boolean removePilot);
    
    public boolean canBoard(Unit u);
    
}
