/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.items;

/**
 *
 * @author Christopher Hittner
 */
public abstract class Item {
    
    protected double mass;
    
    public Item(double mass){ this.mass = mass;}
    
    public double mass(){ return mass; }
    
    public abstract void use(double factor, String extraParam);
    
}
