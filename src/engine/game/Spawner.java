/*
 * The Spawner class is an abstract class upon which spawners for different types
 * of factions can be created.
 */
package engine.game;

/**
 *
 * @author Christopher
 */
public abstract class Spawner {
    private final byte FACTION_ID;
    
    public Spawner(byte id){
        FACTION_ID = id;
    }

    public abstract void cycle(double time);
    
    public final byte factionID(){ return FACTION_ID; }
}
