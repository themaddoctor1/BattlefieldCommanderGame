/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.entities.terrain;

import engine.physics.Coordinate;
import engine.scripts.TimeLimitedScript;

/**
 *
 * @author Christopher
 */
public class Building extends TerrainElement{
    
    private TimeLimitedScript function;
    
    public Building(Coordinate coord, double[] dimensions, TimeLimitedScript script) {
        super(coord, dimensions);
        function = script;
    }
    
    public void cycle(double factor){
        String[] params = {factor + ""};
        function.execute(params);
    }
    
}
