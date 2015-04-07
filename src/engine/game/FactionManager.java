/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.game;

import java.util.ArrayList;

/**
 *
 * @author Christopher Hittner
 */
public class FactionManager {
    private static ArrayList<String> factNames = new ArrayList<>();
    private static ArrayList<ArrayList<String>> members = new ArrayList<>();
    private static ArrayList<ArrayList<Boolean>> allied = new ArrayList<>();
    
    public static void addFaction(String name, ArrayList<String> membs, ArrayList<Boolean> relationships){
        
        factNames.add(name);
        if(membs != null){
            members.add(membs);
        } else {
            members.add(new ArrayList<String>());
        }
        
        if(relationships != null){
            allied.add(relationships);
        } else{
            allied.add(new ArrayList<Boolean>());
            /*
            for(boolean b : allied.get(allied.size()-1))
                b = false;
                    */
        }
        
        for(ArrayList<Boolean> list : allied){
            int init = list.size();
            for(int i = init; i < factNames.size(); i++){
                list.add(false);
            }
        }
        
    }
    
    public static void addFactionMember(String name, byte factID){
        if(factNames.size() - 1 < factID){
            addFaction("" + factNames.size(),null,null);
            
            addFactionMember(name,factID);
                
        } else {
            members.get(factID).add(name.toLowerCase());
        } 
        
    }
    
    public static int getNumFactions(){
        return factNames.size();
    }
    
    public static ArrayList<String> getFactionNames(){
        return factNames;
    }
    
    public static ArrayList<String> getMembers(byte factID){
        return members.get(factID);
    }
    
    public static byte getFactionOf(String name){
        for(byte i = 0; i < members.size(); i++)
            for(int j = 0; j < members.get(i).size(); j++)
                if(members.get(i).get(j).equals(name.toLowerCase()))
                    return i;
        return -1;
    }
    
    public static void setRelationship(byte ID_A, byte ID_B, boolean status){
        allied.get(ID_A).remove(ID_B);
        allied.get(ID_A).add(ID_B, status);
        
        allied.get(ID_B).remove(ID_A);
        allied.get(ID_B).add(ID_A, status);
    }

    public static boolean getRelationship(byte a, byte b) {
        if(a == b)
            return true;
        try {
            return allied.get(b).get(a);
        } catch(Exception e){
            return false;
        }
    }

    public static void removeMember(String name) {
        for(int i = 0; i < members.size(); i++)
            for(int j = members.get(i).size()-1; j >= 0; j--)
                if(members.get(i).get(j).equals(name))
                    members.get(i).remove(j);
    }
    
    
}
