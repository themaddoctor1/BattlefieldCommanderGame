/*
 * FactionManager deals with storing data on what the factions in play are,
 * and who is in them.
 */

package engine.game;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author Christopher Hittner
 */
public class FactionManager {
    private static Hashtable factions = new Hashtable();
    private static Hashtable relationships = new Hashtable();
    
    public static void addFaction(String name, ArrayList<String> membs, ArrayList<Boolean> alliances){
        
        //First, we add the faction and the list of names.
        ArrayList<String> members = new ArrayList<>();
        
        if(membs != null){
            members.addAll(membs);
        } else {
            members.addAll(new ArrayList<String>());
        }
        
        factions.put(name, members);
        
        //Then, we create a relationship hash for the new faction.
        ArrayList<String> factions = getFactionNames();
        
        Hashtable relations = new Hashtable();
        
        //And have it hate everyone by default.
        for(String s : factions){
            relations.put(s, false);
        }
        
        //Adds it
        relationships.put(name, relations);
        
        //Finally, have everybody possess a relationship with the new faction
        Enumeration keys = relationships.keys();
        
        while(keys.hasMoreElements()){
            Hashtable hash = (Hashtable) relationships.get(keys.nextElement());
            
            //Of course, they all hate each other by default.
            hash.put(name, false);
            
        }
        
        
        
    }
    
    public static void addFactionMember(String name, String factID){
        if(!getFactionNames().contains(factID))
            addFaction(factID,null,null);
        
        ((ArrayList<String>)factions.get(factID)).add(name);
        
    }
    
    public static int getNumFactions(){
        return factions.size();
    }
    
    public static ArrayList<String> getFactionNames(){
        //Get every key and add the value of the key to the result list.
        Enumeration list = factions.keys();
        ArrayList<String> result = new ArrayList<>();
        
        while(list.hasMoreElements())
            result.add((String)list.nextElement());
        
        //Then, send it off.
        return result;
    }
    
    public static ArrayList<String> getMembers(String factID){
        return (ArrayList<String>) factions.get(factID);
    }
    
    public static String getFactionOf(String name){
        Enumeration list = factions.keys();
        
        while(list.hasMoreElements()){
            String faction = (String) list.nextElement();
            
            if(((ArrayList<String>)(factions.get(faction))).contains(name))
                return faction;
            
            
        }
        
        return "";
        
    }
    
    public static void setRelationship(String ID_A, String ID_B, boolean status){
        Hashtable A = (Hashtable) relationships.get(ID_A);
        A.remove(ID_B);
        A.put(ID_B, status);
        
        Hashtable B = (Hashtable) relationships.get(ID_B);
        B.remove(ID_A);
        B.put(ID_A, status);
    }

    public static boolean getRelationship(String a, String b) {
        if(a.equals(b))
            return true;
        try {
            return (boolean) ((Hashtable)relationships.get(a)).get(b);
        } catch(Exception e){
            return false;
        }
    }

    public static void removeMember(String name) {
        
        Enumeration list = factions.keys();
        
        //Find them and destroy them!
        while(list.hasMoreElements()){
            ArrayList<String> names = (ArrayList<String>) factions.get(list.nextElement());
            
            names.remove(name);
            
        }
    }

    public static void removeFaction(String s) {
        factions.remove(s);
        relationships.remove(s);
        
        Enumeration relats = relationships.keys();
        
        while(relats.hasMoreElements()){
            Hashtable subHash = (Hashtable) relats.nextElement();
            Enumeration enu = subHash.keys();
            while(enu.hasMoreElements()){
                String nm = (String) enu.nextElement();
                if(nm.equals(s))
                    subHash.remove(s);
            }
        }
        
    }
    
    
}
