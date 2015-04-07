/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.game;

import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class SpawnManager {
    private static ArrayList<Spawner> spawners = new ArrayList<>();
    
    public static void cycle(double factor){
        for(Spawner s : spawners)
            s.cycle(factor);
    }
    
    public static void addSpawner(Spawner s){ spawners.add(s); }
    public static void addSpawners(ArrayList<Spawner> sp){ spawners.addAll(sp); }
    
    public static ArrayList<Spawner> getSpawners(){ return spawners; }
    
}
