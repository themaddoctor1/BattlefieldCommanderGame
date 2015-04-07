/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.items;

import java.util.ArrayList;

/**
 *
 * @author Christopher Hittner
 */
public class Inventory {
    private ArrayList<Item> items = new ArrayList<Item>();
    
    public Inventory(ArrayList<Item> stuff){
        try{
            items.addAll(stuff);
        } catch(NullPointerException e){
            
        }
    }
    
    public void addItem(Item item){
        items.add(item);
    }
    
    public ArrayList<Item> getItems(){ return items;}
    public Item getItem(int index){ return items.get(index);}
    public int size(){ return items.size();}
}
