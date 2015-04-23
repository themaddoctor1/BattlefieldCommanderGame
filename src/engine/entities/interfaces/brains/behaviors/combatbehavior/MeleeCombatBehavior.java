/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.entities.interfaces.brains.behaviors.combatbehavior;

import engine.entities.interfaces.brains.behaviors.combatbehavior.CombatBehavior;
import engine.entities.interfaces.brains.UnitBrain;
import engine.entities.units.Unit;
import engine.game.FactionManager;
import engine.physics.Coordinate;
import engine.world.LevelManager;

/**
 *
 * @author Christopher
 */
public class MeleeCombatBehavior extends CombatBehavior{
    
    public MeleeCombatBehavior(UnitBrain brain){ super(brain);}
    public MeleeCombatBehavior(){ super();}
    
    @Override
    public void actCombat(Unit me, double factor) {
        for(Unit u : LevelManager.getLevel().getUnits())
            if(Coordinate.relativeDistance(u.getPosition(), me.getPosition()) <= u.getSize() + me.getSize() && !FactionManager.getRelationship(FactionManager.getFactionOf(me.getName()), FactionManager.getFactionOf(u.getName()))){
                u.harm(5*(float)factor);
            }
    }

    @Override
    public void attackUnit(Unit me, Unit targ) {
        
    }
    
}
