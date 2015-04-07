/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.entities.units.testUnits;

import engine.entities.interfaces.CarriesTroops;
import engine.entities.interfaces.brains.UnitBrain;
import engine.entities.interfaces.brains.behaviors.*;
import engine.entities.items.Inventory;
import engine.entities.units.AirUnit;
import engine.entities.units.Unit;
import engine.entities.units.groundvehicles.GroundVehicle;
import engine.entities.units.soldiers.Soldier;
import engine.game.FactionManager;
import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.world.LevelManager;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class Drop_Pod extends Unit implements CarriesTroops{
    
    protected Soldier[] soldiers = {null};

    public Drop_Pod(Coordinate coord, String nm, Soldier s) {
        super(coord, 1000, 2, null, new DumbBrain(nm));
        
        soldiers[0] = s;
        
    }
    
    @Override
    public void cycle(double factor){
        super.cycle(factor);
        
        if(getPosition().Y() <= getSize() + 0.1 && getVelocity().getMagnitude() < 1)
            LevelManager.getLevel().getUnits().addAll(unloadAll(true));
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
        
        for(int i = 0; i < units.size(); i++){
            //Moves it to the center of the transport for future relocation.
            units.get(i).getPosition().addVector(new Vector(units.get(i).getPosition(),this.getPosition()));
            //Makes sure it is outside.
            units.get(i).getPosition().addVector(new Vector(getSize() + units.get(i).getSize(), 2*Math.PI*(Math.random() + i/units.size()), 0));
            //Makes sure it is sitting on the ground.
            units.get(i).getPosition().addVector(new Vector(units.get(i).getSize() - units.get(i).getPosition().Y(), 0, Math.toRadians(90)));
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
            }
        return false;
        
    }
    
    @Override
    public void killSelf(){
        super.killSelf();
        
        for(Soldier s : soldiers){
            if(s != null)
                LevelManager.addEvent(s.getName() + " died aboard " + getName() + ".");
            //FactionManager.removeMember(s.getName());
        }
        soldiers = null;
        
    }
    
    
}
