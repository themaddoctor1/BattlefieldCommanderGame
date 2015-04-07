/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.entities.interfaces.brains.behaviors;

import engine.entities.interfaces.brains.UnitBrain;
import engine.world.LevelManager;

/**
 *
 * @author Christopher
 */
public abstract class BrainBehavior {
    private String owner = null;
    
    public BrainBehavior(UnitBrain brain){
        try {
            owner = brain.getName();
        } catch(NullPointerException npe){
            
        }
    }
    
    public BrainBehavior() {
        
    }
    
    public final void setOwner(UnitBrain u){
        owner = u.getName();
    }
    
    public final void setOwner(String nm){ owner = nm;}
    
    public final UnitBrain getOwner(){ return LevelManager.getLevel().getUnit(owner).getBrain();}
}
