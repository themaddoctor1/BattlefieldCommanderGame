/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.entities.interfaces;

import engine.entities.items.Inventory;

/**
 *
 * @author Christopher
 */
public interface HasInventory {
    
    public Inventory getInventory();
    public void setInventory(Inventory i);
    
}
