/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.entities.terrain.structures;

import engine.entities.terrain.TerrainElement;
import engine.entities.units.Unit;
import engine.game.FactionManager;
import engine.physics.Coordinate;
import engine.scripts.TimeLimitedScript;
import engine.world.LevelManager;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class Building extends TerrainElement{
    
    private final TimeLimitedScript function;
    private String factID = "";
    private double influence;
    private final double MAX_INFLUENCE;
    
    public Building(Coordinate coord, double[] dimensions, String fac, TimeLimitedScript script) {
        super(coord, dimensions);
        function = script;
        factID = fac;
        MAX_INFLUENCE = Math.sqrt(dimensions[0]*dimensions[1]*dimensions[2] / Math.min(dimensions[0], Math.min(dimensions[1], dimensions[2])));
        if(factID == null)
            influence = 0;
        else influence = MAX_INFLUENCE;
        
    }
    
    public final void cycle(double factor){
        String[] params = {factor + ""};
        if(function != null)
            function.execute(params);
        
        String claimer = influencingFaction();
        
        if(claimer != null){
            if(!claimer.equals(factID) && !FactionManager.getRelationship(factID, claimer))
                influence -= factor;
            else
                influence += factor;
        } else {
            influence -= factor * influence / MAX_INFLUENCE;
        }
        
        if(influence <= 0)
            changeOwnerTo(claimer);
        
        influence = Math.max(0, Math.min(influence, MAX_INFLUENCE));
        
    }
    
    public void changeOwnerTo(String fac){
        factID = fac;
        if(factID == null)
            influence = 0;
        else influence = MAX_INFLUENCE;
    }

    /**
     * Determines who is controlling this structure.
     * @return The faction's name, or null.
     */
    public String influencingFaction() {
        ArrayList<Unit> units = LevelManager.getLevel().getUnits();
        double reach = MAX_INFLUENCE * Math.max(1, Math.log(MAX_INFLUENCE+1));
        
        String factionName = null;
        
        for(int i = 0; i < units.size(); i++){
            Unit u = units.get(i);
            
            if(Coordinate.relativeDistance(u.getPosition(), coordinate) <= reach){
                
                String nm = FactionManager.getFactionOf(u.getName());
                
                if(!nm.equals(factionName) && factionName != null)
                    return null;
                else
                    factionName = nm;
            }
            
        }
        
        return factionName;
        
    }

    public String getFactionID() { return factID; }
    
}
