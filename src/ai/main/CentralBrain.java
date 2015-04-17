/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.main;

import engine.entities.units.Unit;
import engine.game.FactionManager;
import engine.gui.GUI;
import engine.world.LevelManager;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class CentralBrain extends Thread{
    private final ArrayList<ActionMemory> pastActions = new ArrayList<>();
    private ArrayList<GeneralizedActionMemory> ratings = new ArrayList<>();
    private final String factionID;
    
    public CentralBrain(String factID){
        factionID = factID;
    }
    
    @Override
    public void run(){
        ArrayList<String> prevOrders = new ArrayList<>();
        
        while(true){
            try{
                Thread.sleep(10);
            } catch(InterruptedException e){}
            
            ArrayList<String> orders = getOrders();
            
            //For every possible order
            for(String s : orders){
                //Checks to see if this order has been done already
                boolean alreadyDone = prevOrders.contains(s);
                
                //System.out.println("[" + alreadyDone + "] " + s);
                
                //If it hasn't, then try to execute it.
                if(!alreadyDone){
                    try {
                        GUI.getGUI().parseUserInput(s, factionID);
                        addActionMemory(new ActionMemory(s));
                    } catch(NullPointerException e){}
                }
                
            }
            
            prevOrders = orders;
            
            //GUI.getGUI().parseUserInput(getBestAction(), factionID);
            
        }
        
    }
    
    
    public ActionMemory getBestMemory(){
        ActionMemory result = pastActions.get(0);
        for(int i = 1; i < pastActions.size(); i++)
            if(result.getValueOf() < pastActions.get(i).getValueOf())
                result = pastActions.get(i);
        
        return result;
    }
    
    public void clean(){
        for(int i = pastActions.size() - 1; i >= 0; i++)
            for(int j = i-1; j >= 0; i++)
                if(pastActions.get(i).getName().equals(pastActions.get(j).getName())){
                    pastActions.get(i).combineMemories(pastActions.get(j));
                    if(pastActions.get(i).getReactions().size() <= 0)
                        pastActions.remove(i);
                    else
                        pastActions.remove(j);
                }
    }
    
    public synchronized void addActionMemory(ActionMemory am){pastActions.add(am); }
    public synchronized void addReactionMemory(ReactionMemory rm){ for(ActionMemory am : pastActions) am.addReactionMemory(rm); }
    public synchronized void addReactionMemory(String param){ for(ActionMemory am : pastActions){
        am.addReactionMemory(new ReactionMemory(/*factionID + "^" + */param, am.getAge()));
    }}
    
    public String outputMemoryLog(){
        String result = "";
        
        for(ActionMemory am : pastActions)
            result += am.toString() + "\n";
        
        return result;
    }
    
    /**
     * Sorts the Generalized Action Memories, and then returns it.
     * @return The list of Generalized Action Memories
     */
    public ArrayList<GeneralizedActionMemory> sortActionRankings(){
        
        ratings = new ArrayList<>();
        
        //Priming the particular response.
        ratings.add(new GeneralizedActionMemory("Infantry attack Infantry"));
        
        //Sorts through every previous action.
        for(int i = 0; i < pastActions.size(); i++){
            //Checks for duplicate actions that can be added to the list of ratings.
            boolean added = false;
            for(GeneralizedActionMemory gam : ratings)
                if(!added)
                    added = (added || gam.addActionMemoryReaction(pastActions.get(i)));
            
            //If the action is new, add a Generalized Memory for it.
            if(!added)
                ratings.add(new GeneralizedActionMemory(pastActions.get(i)));
            
        }
        
        //Creates the new list.
        ArrayList<GeneralizedActionMemory> result = new ArrayList<>();
        
        //Sorts the elements in order from best to worst.
        while(!ratings.isEmpty()){
            int max = 0;
            for(int j = 1; j < ratings.size(); j++){
                if(ratings.get(j).getValueOf() > ratings.get(max).getValueOf()){
                    max = j;
                }
            }
            
            result.add(ratings.remove(max));
            
            
        }
        
        //Replaces the old list with the new one.
        ratings = result;
        
        return ratings;
        
    }
    
    public String getBestAction() {
        
        try {
            return getBestMemory().getName();
        } catch(Exception e){
            return "";
        }
        
        
        //return "";
        
    }
    
    public ArrayList<String> getOrders(){
        
        ArrayList<String> orders = new ArrayList<>();
        
        sortActionRankings();
        
        //For every Unit
        for(int i = 0; i < LevelManager.getLevel().getUnits().size(); i++){
            Unit u = LevelManager.getLevel().getUnits().get(i);
            GeneralizedActionMemory g = null;
            
            //If the Unit is commanded by this Brain
            if(FactionManager.getFactionOf(u.getName()).equals(factionID)) for(GeneralizedActionMemory gam : ratings){
                if(gam.getActorType().equals(u.getType()))
                    try{
                        if(g.getValueOf() < gam.getValueOf())
                            g = gam;
                    } catch(NullPointerException e){
                        g = gam;
                    }
            }
            
            if(g != null){
                String order = g.getName();
                order = u.getName() + order.substring(u.getType().length());
                
                for(int j = 0; j < LevelManager.getLevel().getUnits().size(); j++){
                    Unit v = LevelManager.getLevel().getUnits().get(j);
                    if(v.getType().equals(order.substring(order.length() - v.getType().length()))){
                        order = order.substring(0, order.length() - v.getType().length()) + v.getName();
                        break;
                    }
                }
                
                System.out.println(order);
                
                orders.add(order);
            }
            
        }
        
        if(orders.isEmpty()) {
            //Choose a random enemy to kill
            try {
                String yourUnit = FactionManager.getMembers(factionID).get((int)(Math.random() * FactionManager.getMembers(factionID).size()));
                
                String enemyUnit = null;
                for(int i = 0; i < FactionManager.getNumFactions(); i++) if(!FactionManager.getRelationship(factionID, (i + "")))
                    enemyUnit = FactionManager.getMembers(i + "").get((int)(Math.random() * FactionManager.getMembers(i + "").size()));

                if(LevelManager.getLevel().getUnit(enemyUnit) != null){
                    try {
                        orders.add(yourUnit + " attack " + enemyUnit);
                    } catch(NullPointerException npe){}
                }
            } catch(Exception e){
                
            }
        }
        
        return orders;
        
    }
    
}
