/*
 * The Spawner class is an abstract class upon which Spawners for different types
 * of factions can be created.
 */
package engine.game.spawning;

import engine.entities.terrain.TerrainElement;

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
    
    public abstract void requestSpawn();
    
    public final String factionID(){ return FACTION_ID; }

    public abstract void centerOn(TerrainElement elem);
}
