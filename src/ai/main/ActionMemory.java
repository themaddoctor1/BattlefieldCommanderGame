/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.main;

import engine.world.LevelManager;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class ActionMemory extends Memory{
    
    private final ArrayList<ReactionMemory> results = new ArrayList<>();
    
    public ActionMemory(String nm) {
        super(nm);
    }
    
    public String getActorType(){
        if(getName().contains(" ascend")){
            return LevelManager.getLevel().getUnit(getName().substring(0,getName().indexOf(" ascend"))).getType();
            
        } else if(getName().contains(" attack ")){
            return LevelManager.getLevel().getUnit(getName().substring(0,getName().indexOf(" attack "))).getType();
            
        } else if(getName().contains(" board ")){
            return LevelManager.getLevel().getUnit(getName().substring(0,getName().indexOf(" board "))).getType();
            
        } else if(getName().contains(" defend ")){
            return LevelManager.getLevel().getUnit(getName().substring(0,getName().indexOf(" defend "))).getType();
            
        } else if(getName().contains(" descend")){
            return LevelManager.getLevel().getUnit(getName().substring(0,getName().indexOf(" descend"))).getType();
            
        } else if(getName().contains(" follow ")){
            return LevelManager.getLevel().getUnit(getName().substring(0,getName().indexOf(" follow "))).getType();
            
        } else if(getName().contains(" hold position")){
            return LevelManager.getLevel().getUnit(getName().substring(0,getName().indexOf(" hold position"))).getType();
            
        } else if(getName().contains(" hold fire")){
            return getName().substring(0,getName().indexOf(" hold fire"));
            
        } else if(getName().contains(" move to ")){
            return LevelManager.getLevel().getUnit(getName().substring(0,getName().indexOf(" move to "))).getType();
            
        } else if(getName().contains(" open fire")){
            return LevelManager.getLevel().getUnit(getName().substring(0,getName().indexOf(" open fire"))).getType();
            
        } else if(getName().contains(" orbit ")){
            return LevelManager.getLevel().getUnit(getName().substring(0,getName().indexOf(" orbit "))).getType();
            
        } else if(getName().contains(" stop")){
            return LevelManager.getLevel().getUnit(getName().substring(0,getName().indexOf(" stop"))).getType();
            
        } else if(getName().contains(" unload ")){
            return LevelManager.getLevel().getUnit(getName().substring(0,getName().indexOf(" unload "))).getType();
            
        }
        return getName();
    }
            
    
    /**
     * Gets the meaning of an action.
     * @param nm The action.
     * @return The translated, more general meaning.
     */
    public static String getMeaningOf(String nm){
        try {
        if(nm.contains(" ascend")){
            return LevelManager.getLevel().getUnit(nm.substring(0,nm.indexOf(" ascend"))).getType() + " ascend";
            
        } else if(nm.contains(" attack ")){
            return LevelManager.getLevel().getUnit(nm.substring(0,nm.indexOf(" attack "))).getType()
                    + " attack "
                    + LevelManager.getLevel().getUnit(nm.substring(nm.indexOf(" attack ") + 8));
            
        } else if(nm.contains(" board ")){
            return LevelManager.getLevel().getUnit(nm.substring(0,nm.indexOf(" board "))).getType()
                    + " board "
                    + LevelManager.getLevel().getUnit(nm.substring(nm.indexOf(" board ") + 7));
            
        } else if(nm.contains(" defend ")){
            return LevelManager.getLevel().getUnit(nm.substring(0,nm.indexOf(" defend "))).getType()
                    + " defend "
                    + LevelManager.getLevel().getUnit(nm.substring(nm.indexOf(" defend ") + 8));
            
        } else if(nm.contains(" descend")){
            return LevelManager.getLevel().getUnit(nm.substring(0,nm.indexOf(" descend"))).getType() + " descend";
            
        } else if(nm.contains(" follow ")){
            return LevelManager.getLevel().getUnit(nm.substring(0,nm.indexOf(" follow "))).getType()
                    + " follow "
                    + LevelManager.getLevel().getUnit(nm.substring(nm.indexOf(" follow ") + 8));
            
        } else if(nm.contains(" hold position")){
            return LevelManager.getLevel().getUnit(nm.substring(0,nm.indexOf(" hold position"))).getType() + " hold position";
            
        } else if(nm.contains(" hold fire")){
            return LevelManager.getLevel().getUnit(nm.substring(0,nm.indexOf(" hold fire"))).getType() + " hold fire";
            
        } else if(nm.contains(" move to ")){
            return LevelManager.getLevel().getUnit(nm.substring(0,nm.indexOf(" move to "))).getType()
                    + " move to "
                    + LevelManager.getLevel().getUnit(nm.substring(nm.indexOf(" move to ") + 9));
            
        } else if(nm.contains(" open fire")){
            return LevelManager.getLevel().getUnit(nm.substring(0,nm.indexOf(" open fire"))).getType() + " open fire";
            
        } else if(nm.contains(" orbit ")){
            return LevelManager.getLevel().getUnit(nm.substring(0,nm.indexOf(" orbit "))).getType()
                    + " orbit "
                    + LevelManager.getLevel().getUnit(nm.substring(nm.indexOf(" orbit ") + 7));
            
        } else if(nm.contains(" stop")){
            return LevelManager.getLevel().getUnit(nm.substring(0,nm.indexOf(" stop"))).getType() + " stop";
            
        } else if(nm.contains(" unload ")){
            return LevelManager.getLevel().getUnit(nm.substring(0,nm.indexOf(" unload "))).getType()
                    + " unload "
                    + LevelManager.getLevel().getUnit(nm.substring(nm.indexOf(" unload ") + 8));
            
        }
        return nm;
        } catch(Exception e){
            return nm;
        }
    }
    
    @Override
    public double getPolarityOf() {
        double result = 0;
        for(ReactionMemory rm : results)
            result += Math.signum(rm.getPolarityOf()) * Math.pow(rm.getPolarityOf(),2);
        
        return Math.signum(result) * Math.sqrt(Math.abs(result));
    }

    @Override
    public double getRelevanceOf() {
        double result = 0;
        for(ReactionMemory rm : results)
            result += rm.getRelevanceOf();
        
        return result;
    }
    
    public void combineMemories(ActionMemory other){
        if(this.getAge() > other.getAge()){
            other.combineMemories(this);
            return;
        } else if(this.getAge() == other.getAge())
            return;
        
        results.addAll(other.getReactions());
        other.getReactions().clear();
        
    }
    
    public void addReactionMemory(ReactionMemory rm){ results.add(rm); }
    public ArrayList<ReactionMemory> getReactions(){ return results; }
    
    public String toString(){
        String result = super.toString();
        
        for(ReactionMemory  rm: results)
            result += "\n    " + rm.toString();
        
        return result;
    }
    
}
