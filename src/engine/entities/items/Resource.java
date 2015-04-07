/*
 * A Resource is a value that consists of a double and a String to name the
 * value being measured.
 */

package engine.entities.items;

/**
 *
 * @author Christopher Hittner
 */
public class Resource {
    private final String nm;
    private double amt;
    
    public Resource(String name, double amount){
        nm = name;
        amt = amount;
    }
    
    public Resource(String name){
        this(name,0);
    }
    
    public String name(){ return nm;}
    public double amt(){ return amt;}
    
    public void modAmt(double inc){ amt += inc;}
    public void setAmt(double val){ amt = val;}
    
    public String toString(){
        return nm + " = " + amt;
    }
    
}
