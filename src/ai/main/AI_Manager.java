/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.main;

import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class AI_Manager {
    
    
    private static ArrayList<CentralBrain> brains = new ArrayList<>();
    
    public static void addBrain(CentralBrain cb){ brains.add(cb); }
    public static void start(){ for(CentralBrain cb : brains) cb.start(); }
    
    public static ArrayList<CentralBrain> getBrains(){ return brains; }
    
    
}
