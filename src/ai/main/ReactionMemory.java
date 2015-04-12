/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.main;

import engine.game.FactionManager;

/**
 *
 * @author Christopher
 */
public class ReactionMemory extends Memory{
    
    private double polarValue;
    private double relevance;
    
    public ReactionMemory(String param, long parentAge) {
        super(param);
        this.init(calculatePolarValue(param), parentAge);
    }
    
    public ReactionMemory(String nm, double polVal, long parentAge){
        super(nm);
        init(polVal, parentAge);
    }
    
    private void init(double polVal, long parentAge){
        polarValue = polVal;
        relevance = Math.pow(10, 9) / (parentAge);
    }
    
    /*
    * Uses the String[] param input to find the polarity of the memory
    */
    private double calculatePolarValue(String param){
        String parameter = param;//param.substring(param.indexOf("^") + 1);
        String fact = FactionManager.getFactionOf(parameter.substring(0, parameter.indexOf(" killed ")));
        
        try {
            String actor = param.substring(0, parameter.indexOf(" killed "));
            String victim = param.substring(parameter.indexOf(" killed ") + 8);
            
            if(FactionManager.getFactionOf(victim).equals(fact))
                return -2;
            else if(FactionManager.getFactionOf(actor).equals(fact))
                return 2;
            else if(FactionManager.getRelationship(FactionManager.getFactionOf(actor), fact) == FactionManager.getRelationship(FactionManager.getFactionOf(victim), fact))
                return 0;
            else if(FactionManager.getRelationship(FactionManager.getFactionOf(actor), fact) && !FactionManager.getRelationship(FactionManager.getFactionOf(victim), fact))
                return 1;
            else if(FactionManager.getRelationship(FactionManager.getFactionOf(victim), fact) && !FactionManager.getRelationship(FactionManager.getFactionOf(actor), fact))
                return -1;
            else
                return 0;
            
        } catch(Exception e){
            return 0;
        }
    }
    
    @Override
    public double getPolarityOf() {
        return polarValue;
    }
    
    @Override
    public double getRelevanceOf(){
        return relevance;
    }

    
}
