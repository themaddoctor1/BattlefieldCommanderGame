/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
