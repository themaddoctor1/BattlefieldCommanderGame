/*
 * The HasInventory interface is for Objects that have an Inventory.
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
