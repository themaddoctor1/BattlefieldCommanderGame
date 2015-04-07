/*
 * The Health interface is used by Objects that have health. The method names
 * should be self-explanatory. For instance, the harm methods harms Objects
 * by the stated amount, getHP gets the HP, and so on.
 */
package engine.entities.interfaces;

/**
 *
 * @author Christopher
 */
public interface Health {
    
    public void harm(float amt);
    public void heal(float amt);
    public void setHP(float amt);
    
    public boolean testLiving();
    public void killSelf();
    
    public double getHP();
    public double getMaxHP();
    
}
