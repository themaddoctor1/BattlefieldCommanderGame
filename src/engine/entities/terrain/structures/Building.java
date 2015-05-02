/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.entities.terrain.structures;

import engine.entities.terrain.TerrainElement;
import engine.physics.Coordinate;
import engine.scripts.TimeLimitedScript;

/**
 *
 * @author Christopher
 */
public class Building extends TerrainElement{
    
    private final TimeLimitedScript function;
    private String factID;
    
    public Building(Coordinate coord, double[] dimensions, String fac, TimeLimitedScript script) {
        super(coord, dimensions);
        function = script;
        factID = fac;
    }
    
    public final void cycle(double factor){
        String[] params = {factor + ""};
        if(function != null)
            function.execute(params);
    }
    
}
