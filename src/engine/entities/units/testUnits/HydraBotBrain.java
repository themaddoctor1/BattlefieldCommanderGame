/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.units.testUnits;

import engine.entities.interfaces.brains.behaviors.movementbehavior.WalkingMovementBehavior;
import engine.entities.interfaces.brains.behaviors.combatbehavior.MeleeCombatBehavior;
import engine.entities.interfaces.brains.behaviors.orderprocess.DefaultUnitOrderProcess;
import engine.entities.interfaces.brains.behaviors.orderprocess.GroundUnitOrderProcess;
import engine.entities.interfaces.brains.behaviors.awarenesprocess.SightAwarenessProcess;
import engine.entities.interfaces.brains.UnitBrain;
import engine.entities.interfaces.brains.behaviors.*;
import engine.entities.items.Item;
import engine.entities.units.Unit;
import engine.entities.units.testUnits.Hydra_Bot;
import engine.game.FactionManager;
import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.world.LevelManager;
import java.util.ArrayList;

/**
 *
 * @author Christopher Hittner
 */
public class HydraBotBrain extends UnitBrain{
    
    private final float CLONE_TIME;
    private double time = 0;
    private static int pastClones = 0;
    
    public HydraBotBrain(String nm) {
        super(nm + "-", new MeleeCombatBehavior(), new WalkingMovementBehavior(), new GroundUnitOrderProcess(new DefaultUnitOrderProcess()), new SightAwarenessProcess(Math.toRadians(180), 0));
        CLONE_TIME = (float)(30 * (0.5 + Math.random()));
    }
    
    
    @Override
    public void act(Unit me, double factor) {
        
        
        willShoot = true;
        
        //actBoarding(me);
        
        chooseTarget(me);
        
        actCombat(me,factor);
        
        actMovement(me,factor);
        
        actCloning(me, factor);
        
        actAwareness(me, factor);
    }
    
    
    public void actCloning(Unit me, double factor) {
        time += factor;
        if(time >= CLONE_TIME){
            
            double angle = Math.random() * 2 * Math.PI;
            Coordinate position = new Coordinate(me.getPosition().X() + 2 * me.getSize() * Math.cos(angle), me.getPosition().Y(), me.getPosition().Z() + 2 * me.getSize() * Math.sin(angle));
            
            String nm = name.substring(0, name.indexOf("-") + 1) + pastClones;
            pastClones++;
            
            String factID = FactionManager.getFactionOf(name);
            
            LevelManager.getLevel().addUnit(new Hydra_Bot(position, nm), factID);
            
            time = 0;
        }
    }
    
    
    private void chooseTarget(Unit me) {
        if(destinations.isEmpty())
            try{
                ArrayList<Unit> units = LevelManager.getLevel().getUnits();
                Unit targ = units.get(0);
                for(int i = 1; i < units.size(); i++){
                    Unit u = units.get(i);
                    if(Math.abs(u.getPosition().Y() - me.getPosition().Y()) < me.getSize() + u.getSize())
                        if(targ.getName().equals(name)
                                || FactionManager.getRelationship(FactionManager.getFactionOf(name), FactionManager.getFactionOf(targ.getName()))) {
                            targ = u;

                        } else if(FactionManager.getRelationship(FactionManager.getFactionOf(name), FactionManager.getFactionOf(u.getName())) == false
                                && (Coordinate.relativeDistance(me.getPosition(), u.getPosition()) < Coordinate.relativeDistance(me.getPosition(), targ.getPosition())) ){ 
                                targ = u;
                        }
                }
                
                giveOrder("attack " + targ.getName(), FactionManager.getFactionOf(name));
                
            } catch(Exception e){
                e.printStackTrace();
            }
    }

}
