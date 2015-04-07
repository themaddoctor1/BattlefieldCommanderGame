/*
 * An Item is anything that can be used.
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
