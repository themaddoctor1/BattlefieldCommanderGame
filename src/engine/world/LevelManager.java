/*
 * The LevelManager class manages a single Level object, which contains the
 * Entities that the user is interested in.
 */

package engine.world;

import ai.main.AI_Manager;
import ai.main.CentralBrain;
import engine.game.FactionManager;
import engine.game.SpawnManager;
import engine.gui.GUI;
import java.util.ArrayList;

/**
 *
 * @author Christopher Hittner
 */
public class LevelManager {
    
    private static long nextID = Long.MIN_VALUE;
    
    private static long timeChecked = System.nanoTime();
    
    private static Level level = new Level();
    
    private static final ArrayList<String> events = new ArrayList<>();
    
    private static int cycles = 0;
    
    private static final int BRAIN_CYCLE_RATE = 1;
    
    public static void addEvent(String event){
        events.add(event);
        for(CentralBrain cb : AI_Manager.getBrains()){
            cb.addReactionMemory(event);
        }
    }
    
    
    public static ArrayList<String> getEvents(){ return events;}
    
    public static void setLevel(Level l){ level = l;}
    public static Level getLevel(){ return level;}
    
    /**
     * Combines the actions of the setLevel and getLevel methods.
     * @param l The new Level.
     * @return The old Level.
     */
    public static Level swapLevels(Level l){
        Level old = level;
        level = l;
        return old;
    }
    
    public static void cycleLevel(double time){
        //level.cycle(time);
        
        //Entity-Terrain collisions
        level.executeTerrainCollisions();
        
        //Entity-Entity collisions
        level.executeEntityCollisions();
        
        level.executeEntityCycles(time);
        
        SpawnManager.cycle(time);
        
        cycles++;
        
    }
    
    
    
    public static long assignID(){
        long ID = nextID;
        nextID++;
        return ID;
    }
    
    private static double confirmCheck(){
        double time = (getNanoTimeSinceLastCycle() / Math.pow(10,9));
        timeChecked = System.nanoTime();
        return time;
    }
    
    private static long getNanoTimeSinceLastCycle(){
        return System.nanoTime() - timeChecked;
    }
    
    
    public static void startSimulation(){
        if(WorldThread.getRunStatus())
            return;
        (new WorldThread(2500)).start();
        
    }
    
    public static void stopSimulation(){
        WorldThread.setRunStatus(false);
    }
    
    public static int getCyclesSoFar(){ return cycles;}
    public static int getFrequencyOfThinking(){ return BRAIN_CYCLE_RATE; }
    
    /*
     * This subclass is meant to create a Thread that only this class can use.
     * The purpose of the thread is to execute and cycle the simulation in real time.
    */
    private static class WorldThread extends Thread {
        private static boolean run = false;
        private static double CYCLE_TIME;

        private WorldThread(){CYCLE_TIME = -1;}
        private WorldThread(double fps){CYCLE_TIME = 1.0/fps;}
        
        @Override
        public void run(){
            setRunStatus(true);
            
            for(String s : FactionManager.getFactionNames()){
                    if(!s.equals("Player")){
                    AI_Manager.addBrain(new CentralBrain(s));
                }
            }
            
            for(CentralBrain cb : AI_Manager.getBrains())
                cb.start();
            
            confirmCheck();
            
            while(run){
                double time = confirmCheck();
                while(time < CYCLE_TIME){
                    GUI.getGUI().redraw();
                    time += confirmCheck();
                }
                if(time < 0.05)
                    cycleLevel(time);
            }
        }
        
        private static void setRunStatus(boolean val){
            run = val;
        }
        
        private static boolean getRunStatus() {
            return run;
        }
        
    }
    
    
    
}
