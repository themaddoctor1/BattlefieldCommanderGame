/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.interfaces.brains.behaviors.movementbehavior;

import engine.entities.interfaces.brains.UnitBrain;
import engine.entities.interfaces.brains.behaviors.BrainBehavior;
import engine.entities.units.Unit;
import engine.world.LevelManager;

/**
 *
 * @author Christopher Hittner
 */
public abstract class MovementBehavior extends BrainBehavior{
    
    public MovementBehavior(UnitBrain brain){
        super(brain);
    }
    
    public MovementBehavior(){
        super();
    }
    
    /**
     * Moves if possible.
     * @param me The owner of the Brain. A Unit object will send itself to fulfill this parameter.
     * @param factor The time multiplier.
     */
    public abstract void actMovement(Unit me, double factor);
}
