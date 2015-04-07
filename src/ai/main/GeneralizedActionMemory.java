/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.main;

import engine.world.LevelManager;

/**
 *
 * @author Christopher
 */
public class GeneralizedActionMemory extends ActionMemory{

    public GeneralizedActionMemory(String nm) {
        super(getMeaningOf(nm));
    }
    
    public GeneralizedActionMemory(ActionMemory am){
        super(am.getName());
        this.getReactions().addAll(am.getReactions());
    }
    
    public String getActorType(){
        if(getName().contains(" ascend")){
            return getName().substring(0,getName().indexOf(" ascend"));
            
        } else if(getName().contains(" attack ")){
            return getName().substring(0,getName().indexOf(" attack "));
            
        } else if(getName().contains(" board ")){
            return getName().substring(0,getName().indexOf(" board "));
            
        } else if(getName().contains(" defend ")){
            return getName().substring(0,getName().indexOf(" defend "));
            
        } else if(getName().contains(" descend")){
            return getName().substring(0,getName().indexOf(" descend"));
            
        } else if(getName().contains(" follow ")){
            return getName().substring(0,getName().indexOf(" follow "));
            
        } else if(getName().contains(" hold position")){
            return getName().substring(0,getName().indexOf(" hold position"));
            
        } else if(getName().contains(" hold fire")){
            return getName().substring(0,getName().indexOf(" hold fire"));
            
        } else if(getName().contains(" move to ")){
            return getName().substring(0,getName().indexOf(" move to "));
            
        } else if(getName().contains(" open fire")){
            return getName().substring(0,getName().indexOf(" open fire"));
            
        } else if(getName().contains(" orbit ")){
            return getName().substring(0,getName().indexOf(" orbit "));
            
        } else if(getName().contains(" stop")){
            return getName().substring(0,getName().indexOf(" stop"));
            
        } else if(getName().contains(" unload ")){
            return getName().substring(0,getName().indexOf(" unload "));
            
        }
        return getName();
    }
    
    /**
     *
     * @param am The memory
     * @return Whether or not the ActionMemory is compatible
     */
    public boolean addActionMemoryReaction(ActionMemory am){
        //Makes sure the ActionMemory is relevant to this action category
        if(getMeaningOf(am.getName()).equals(getName()))
            this.getReactions().addAll(am.getReactions());
        else return false;
        return true;
    }
    
    
}
