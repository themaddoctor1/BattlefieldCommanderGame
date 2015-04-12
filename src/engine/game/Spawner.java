/*
 * The Spawner class is an abstract class upon which Spawners for different types
 * of factions can be created.
 */
package engine.game;

/**
 *
 * @author Christopher
 */
public abstract class Spawner {
    private final String FACTION_ID;
    
    public Spawner(String id){
        FACTION_ID = id;
    }

    public abstract void cycle(double time);
    
    public final String factionID(){ return FACTION_ID; }
}
