/*
 * The UnitBrain interface is a template for creating classes that give Units a "brain"
 * that allows them to act and follow orders. Each one has processes that can be utilized
 * to allow for various means of acting. They don't have to be used, but it is highly
 * recommended that they be used.
 */
package engine.entities.interfaces.brains;

import engine.entities.interfaces.CarriesTroops;
import engine.entities.interfaces.brains.behaviors.AwarenessProcess;
import engine.entities.interfaces.brains.behaviors.CombatBehavior;
import engine.entities.interfaces.brains.behaviors.MovementBehavior;
import engine.entities.interfaces.brains.behaviors.OrderProcess;
import engine.entities.items.Item;
import engine.entities.items.Weapon;
import engine.entities.units.Unit;
import engine.game.FactionManager;
import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.world.LevelManager;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public abstract class UnitBrain {
    
    protected CombatBehavior combatBehavior = null;
    protected MovementBehavior movementBehavior = null;
    protected OrderProcess orderProcess = null;
    protected AwarenessProcess awarenessProcess = null;
    
    protected ArrayList<Object> destinations = new ArrayList<>();
    protected boolean 
            willBoard = false,
            willShoot = false, 
            willMove = true, 
            attacking = false;
    protected String name;
    
    
    public UnitBrain(String nm, CombatBehavior cb, MovementBehavior mb, OrderProcess op, AwarenessProcess ap) {
        name = nm;
        
        movementBehavior = mb;
        movementBehavior.setOwner(nm);
        orderProcess = op;
        orderProcess.setOwner(nm);
        combatBehavior = cb;
        combatBehavior.setOwner(nm);
        awarenessProcess = ap;
        awarenessProcess.setOwner(nm);
    }
    
    public UnitBrain(String nm){
        name = nm;
    }
    
    
    /**
     * Provides the Unit with instructions to be followed.
     * @param order The order being given.
     * @param factionID The user's faction ID, which is used to authenticate who the user is.
     */
    public final void giveOrder(String order, String factionID){
        orderProcess.giveOrder(order, factionID);
    }
    
    public abstract void act(Unit me, double factor);
    
    
    /**
     * Moves if possible.
     * @param me The owner of the Brain. A Unit object will send itself to fulfill this parameter.
     * @param factor The time multiplier.
     */
    public final void actMovement(Unit me, double factor){
        movementBehavior.actMovement(me,factor);
    }
    
    /**
     * Tries to board Units that can be boarded, if needed.
     * @param me
     */
    public final void actBoarding(Unit me){
        
        if(!willBoard || destinations.size() < 1)
            return;
        
        try {
            String nm = destinations.get(0).toString().substring(6);
            
            Unit boardTarget = LevelManager.getLevel().getUnit(nm);
            
            if(Coordinate.relativeDistance(me.getPosition(), boardTarget.getPosition()) < 1 + me.getSize() + boardTarget.getSize()
                    ){
                if(boardTarget instanceof CarriesTroops){
                    if(((CarriesTroops) boardTarget).canBoard(me)){
                        destinations.clear();
                        me.getVelocity().multiplyMagnitude(0);
                        ((CarriesTroops) boardTarget).board(me);
                        LevelManager.getLevel().getUnits().remove(me);
                    } 
                    willBoard = false;
                }
            }
                
                
        } catch(Exception e){
            
        }
        
    }
    
    
    /**
     * Attacks a hostile if possible.
     * @param me The owner of the Brain. A Unit object will send itself to fulfill this parameter.
     * @param factor The time multiplier.
     */
    public final void actCombat(Unit me, double factor){
        combatBehavior.actCombat(me, factor);
    }
    
    public final void actAwareness(Unit me, double factor){
        awarenessProcess.actAwareness(me, factor);
    }
    
    
    
    public String getName(){ return name; }
    public boolean willBoard(){ return willBoard;}
    public boolean willMove(){ return willMove;}
    public boolean willShoot(){ return willShoot;}
    public boolean willAttack(){ return attacking;}
    public void setBoard(boolean b){ willBoard = b;}
    public void setMove(boolean b){ willMove = b;}
    public void setShoot(boolean b){ willShoot = b;}
    public void setAttack(boolean b){ attacking = b;}
    public ArrayList<Object> getDestinations(){ return destinations; }
    public void setMovementBehavior(MovementBehavior mb){ movementBehavior = mb;}

    public final AwarenessProcess getAwarenessProcess() { return awarenessProcess; }
    
}
