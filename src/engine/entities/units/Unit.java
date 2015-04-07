/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.units;

import engine.entities.interfaces.brains.UnitBrain;
import engine.entities.MovableEntity;
import engine.entities.TerrainElement;
import engine.entities.interfaces.*;
import engine.entities.items.Inventory;
import engine.entities.items.Item;
import engine.game.FactionManager;
import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.world.LevelManager;
import java.util.ArrayList;

/**
 *
 * @author Christopher Hittner
 */
public abstract class Unit extends MovableEntity implements Health, HasInventory {
    
    protected double radius;
    
    protected Inventory inv;
    protected float maxHP, hp;
    
    protected Vector velocity = new Vector(0,0,0);
    
    protected UnitBrain brain;
    
    public Unit(Coordinate coord, float HP, float size, Inventory i, UnitBrain u) {
        super(coord);
        maxHP = HP;
        hp = HP;
        radius = size;
        if(i == null)
            inv = new Inventory(new ArrayList<Item>());
        else
            inv = i;
        
        brain = u;
    }
    
    /**
     * This method MUST be overwritten by ALL superclasses of this class.
     * @param factor The time dilation.
     */
    @Override
    public void cycle(double factor) {
        
        if(getPosition().Y() < getSize()){
            fallDamage();
            
            getVelocity().addVectorToThis(new Vector(getVelocity().getMagnitudeY(),0,-Math.toRadians(90)));
            
            getPosition().addVector(new Vector(getSize() - getPosition().Y(),0,Math.toRadians(90)));
        } else if(getPosition().Y() > getSize()){
            getVelocity().addVectorToThis(new Vector(-9.81 * factor, 0, Math.toRadians(90)));
        }
        
        move(factor);
        
        //If true, then the Unit is dead.
        if(hp <= 0){
            killSelf();
            return;
        }
        
        
        try{
            if(LevelManager.getCyclesSoFar() % LevelManager.getFrequencyOfThinking() == 0)
                brain.act(this, LevelManager.getFrequencyOfThinking()*factor);
        } catch(NullPointerException e){
            //This Unit is brain dead... ha, get it? Brain dead? Because it has
            //no brain...? :P
        }
    }

    @Override
    public void move(double factor){
        coordinate.addVector(new Vector(velocity,factor));
    }

    @Override
    public void harm(float amt) {hp = Math.max(hp - amt, 0);}
    
    public void fallDamage(){
        if(velocity.getMagnitude() > 5)
            harm((float)(Math.pow(velocity.getMagnitude(),2)*Math.sqrt(maxHP) * (-Math.sin(velocity.getAngleY()) - 0.1)/2.0 ));
        if(hp <= 0){
            killSelf();
            LevelManager.addEvent(getName() + " died from fall damage.");
        }
    }
    
    @Override
    public void heal(float amt) { hp = Math.min(hp + amt, maxHP); }

    @Override
    public void setHP(float amt) { hp = amt; }

    @Override
    public boolean testLiving() { return(hp <= 0); }

    @Override
    public void killSelf() {
        LevelManager.getLevel().getUnits().remove(this);
    }
    
    @Override
    public double getHP() { return hp; }

    @Override
    public double getMaxHP() { return maxHP; }
    
    public Inventory getInventory(){return inv; }
    
    public void setInventory(Inventory i){ inv = i;}
    
    public Vector getVelocity(){ return velocity; }

    public double getSize() { return radius; }

    public String getName() { return brain.getName(); }
    
    public void giveOrder(String order, byte factionID){
        brain.giveOrder(order, factionID);
    }

    public String getType() {
        String type = getClass().getName();
        
        while(type.contains("."))
            type = type.substring(type.indexOf(".") + 1);
        return type.replaceAll("_", " ");
    }

    public boolean onGround() {
        if(getPosition().Y() <= getSize())
            return true;
        else {
            for(TerrainElement te : LevelManager.getLevel().getTerrain())
                if(te.collidingWith(this) && getPosition().Y() > (te.getPosition().Y() + te.getSize()[1]/2.0))
                    return true;
        }
        return false;
    }

    public UnitBrain getBrain() {
        return brain;
    }
    
}
