/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.entities.miscellaneous;

import engine.entities.Entity;
import engine.entities.items.Item;
import engine.entities.units.Unit;
import engine.physics.Coordinate;
import engine.world.LevelManager;

/**
 *
 * @author Christopher
 */
public class ItemEntity extends Entity{
    private Item item;
    private final float size;
    
    public ItemEntity(Coordinate c, Item i, float rad){
        super(c);
        item = i;
        size = rad;
    }
    
    @Override
    public void cycle(double factor) {
        for(int i = 0; i < LevelManager.getLevel().getUnits().size(); i++){
            Unit u = LevelManager.getLevel().getUnits().get(i);
            
            if(Math.sqrt(Math.pow(u.getPosition().X() - getPosition().X(), 2) + Math.pow(u.getPosition().Z() - getPosition().Z(), 2)) < u.getSize()){
                u.getInventory().addItem(item);
                item = null;
                killSelf();
                return;
            }
        }
    }
    
    public Item getItem(){ return item; }

    public void killSelf() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
