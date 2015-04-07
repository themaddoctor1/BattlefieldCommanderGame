/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.interfaces.brains.behaviors;

import engine.entities.units.Unit;
import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.world.LevelManager;
import java.util.ArrayList;

/**
 *
 * @author Christopher Hittner
 */
public class SightAwarenessProcess extends AwarenessProcess {
    
    private final double FOV, VISIBLE_SIZE;
    private Vector looking = new Vector(1,0,0);
    
    public SightAwarenessProcess(double fov, double size){
        super();
        FOV = fov;
        VISIBLE_SIZE = size;
    }
    
    public SightAwarenessProcess(AwarenessProcess ap, double fov, double size){
        super(ap);
        FOV = fov;
        VISIBLE_SIZE = size;
    }
    
    @Override
    protected ArrayList<Unit> seenUnits(Unit me) {
        ArrayList<Unit> units = LevelManager.getLevel().getUnits();
        ArrayList<Unit> visible = new ArrayList<>();
        
        for(int i = 0; i < units.size(); i++){
            
            double viewAngle = Math.acos(Vector.cosOfAngleBetween(new Vector(me.getPosition(), units.get(i).getPosition()), looking));
            double screenSize = Math.atan(units.get(i).getSize() / (new Vector(me.getPosition(), units.get(i).getPosition()).getMagnitude()));
            
            if(!me.equals(units.get(i)))
            if(
                    viewAngle <= FOV && 
                    screenSize >= VISIBLE_SIZE)
                visible.add(units.get(i));
        }
        
        return visible;
    }

    @Override
    public void actAwareness(Unit me, double factor) {
        try {
            if(!me.getBrain().getDestinations().isEmpty()){
                String targ = me.getBrain().getDestinations().get(0).toString();
                
                if(targ.indexOf("[UNIT]") == 0) try{
                    looking = new Vector(me.getPosition(), LevelManager.getLevel().getUnit(targ).getPosition()).unitVector();
                } catch(Exception e){
                    
                } else if(!me.getBrain().willAttack()){
                    String Z = targ.substring(1, targ.indexOf(")"));
                    Z = Z.substring(Z.indexOf(",") + 1);
                    Z = Z.substring(Z.indexOf(",") + 1);
                    double z = Double.parseDouble(Z);
                    looking = new Vector(me.getPosition(), new Coordinate(Double.parseDouble(targ.substring(1, targ.indexOf(","))), me.getSize(), z)).unitVector();
                }
                
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void combatProcess(Unit targ) {
        looking = new Vector(LevelManager.getLevel().getUnit(getOwner().getName()).getPosition(), targ.getPosition()).unitVector();
    }

}
