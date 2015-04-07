/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.main;

/**
 *
 * @author Christopher
 */
public abstract class Memory {
    
    private final String name;
    private final long time;
    
    public Memory(String nm){
        name = nm;
        time = System.nanoTime();
    }
    
    public long getAge(){ return System.nanoTime() - time; }
    public String getName(){ return name;}
    public long getTimeOf(){ return time;}
    //public abstract String toString();
    
    public abstract double getPolarityOf();
    
    public double getRelevanceOf(){
        return Math.pow(10,9) / (System.nanoTime() - time);
    }
    
    public final double getValueOf(){
        return Math.signum(getPolarityOf()) * Math.sqrt(Math.abs(getPolarityOf() * getRelevanceOf()));
    }
    
    public String toString(){
        return this.getName() + " " + this.getValueOf() + " [" + this.getPolarityOf() + "|" + this.getRelevanceOf() + "|" + this.getAge()/Math.pow(10, 9) + "]";
    }
    
    
}
