/*
 * A class that builds loadouts and items.
 */
package engine.entities.items;

/**
 *
 * @author Christopher
 */
public class InventoryBuilder {
    
    //For creating loadouts, I will likely reference http://en.wikipedia.org/wiki/Equipment_of_the_United_States_Army
    
    public static Inventory buildAH64ApacheLoadout(){
        Inventory inv = new Inventory(null);
        
        //Insert Rocket Launcher implementation here
        
        //Gun - M230
        inv.addItem(new Weapon(55.9, "M230", new Resource("30x113mm",1200),805,0.4,1,0.096,8.1));
        
        return inv;
    }
    
    public static Inventory buildInfantryLoadout(){
        Inventory inv = new Inventory(null);
        
        //Primary Weapon - M4A1
        inv.addItem(new Weapon(2.88, "M4A1", new Resource("5.56x45mm NATO",300),880,0.004,0.1,0.075,0.6));
        
        //Sidearm - M9
        inv.addItem(new Weapon(0.952, "M9", new Resource("9x19mm Parabellum",120),381,0.0075,0.1,0.5,0.6));
        
        return inv;
    }
    
    
    public static Inventory buildSniperLoadout(){
        Inventory inv = new Inventory(null);
        
        //Primary Weapon - M4A1
        inv.addItem(new Weapon(13.5, "M107", new Resource(".50 BMG",50),853,0.045,.5,1,0.6));
        
        //Sidearm - M9
        inv.addItem(new Weapon(0.952, "M9", new Resource("9x19mm Parabellum",120),381,0.0075,0.1,0.5,0.6));
        
        return inv;
    }
    
    public static Inventory buildHumveeLoadout(){
        Inventory inv = new Inventory(null);
        
        //Weapon - M134 Minigun
        inv.addItem(new Weapon(38.5, "M134", new Resource("7.62x51mm NATO",5000),853,0.01,0.1,0.015,2.1));
        
        return inv;
    }
    
    public static Inventory buildM1AbramsLoadout(){
        Inventory inv = new Inventory(null);
        
        //Main Cannon - 120mm Gun
        inv.addItem(new Weapon(1190, "Rheinmetall 120mm Gun", new Resource("120mm Shell",40),1700,5,100,10,3.6));
        
        //Secondary - M2
        inv.addItem(new Weapon(17, "M2", new Resource(".50 BMG",50),890,0.045,0.1,0.11,3.6));
        
        return inv;
    }
    
    public static Inventory buildStrykerLoadout(){
        Inventory inv = new Inventory(null);
        
        //Main Cannon - M68A2
        inv.addItem(new Weapon(1282, "M68A2", new Resource("105mm Shell",40),1250,4,50,6,3.6));
        
        //Secondary - M240
        inv.addItem(new Weapon(11.5, "M240", new Resource("7.62x51mm NATO",1000),890,0.01,0.1,0.071,3.6));
        
        return inv;
    }
    
    
    public static Inventory buildUH60BlackHawkLoadout(){
        Inventory inv = new Inventory(null);
        
        //Weapon - M134 Minigun (It is supposed to have two, but I added one to reduce data usage)
        inv.addItem(new Weapon(38.5, "M134", new Resource("7.62x51mm NATO",6000),853,0.01,0.1,0.015,2.1));
        
        return inv;
    }
    
    
    public static Inventory buildEmptyLoadout(){
        Inventory inv = new Inventory(null);
        
        return inv;
    }
    
}
