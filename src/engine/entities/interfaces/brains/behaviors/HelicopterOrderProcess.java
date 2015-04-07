/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.interfaces.brains.behaviors;

import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.world.LevelManager;

/**
 *
 * @author Christopher Hittner
 */
public class HelicopterOrderProcess extends OrderProcess{
    
    public HelicopterOrderProcess(OrderProcess op) {
        super(op);
    }
    
    
    @Override
    protected boolean processOrder(String order) {
        
        if(order.equals("ascend")){
            try {
                ((Coordinate) getOwner().getDestinations().get(0)).addVector(new Vector(10,0,Math.toRadians(90)));
            } catch(Exception e){
                getOwner().getDestinations().add(0, "[ASCEND]" + (int)(1 + LevelManager.getLevel().getUnit(getOwner().getName()).getPosition().Y()/10));
            }
        } else if(order.equals("descend")){
            try {
                ((Coordinate) getOwner().getDestinations().get(0)).addVector(new Vector(10,0,Math.toRadians(90)));
            } catch(Exception e){
                getOwner().getDestinations().add(0, "[DESCEND]" + (int)Math.max(LevelManager.getLevel().getUnit(getOwner().getName()).getPosition().Y()/10 - 1, 0));
            }
        } else if(order.indexOf("follow ") == 0){
            
            getOwner().setMove(true);
            
            String targetName = "[UNIT]" + order.substring(7);
            
            getOwner().getDestinations().clear();
            getOwner().getDestinations().add(targetName);
        } else if(order.equals("hold position") || order.equals("stop")){
            getOwner().setMove(false);
        } else if(order.indexOf("move to ") == 0){
            
            getOwner().setMove(true);
            
            String coord = order.substring(8);
            getOwner().getDestinations().clear();
            
            try {
                double X = Double.parseDouble(coord.substring(0,coord.indexOf(" ")));
                coord = coord.substring(coord.indexOf(" ") + 1);
                double Z = Double.parseDouble(coord);
                
                getOwner().getDestinations().add(new Coordinate(X,LevelManager.getLevel().getUnit(getOwner().getName()).getPosition().Y(),Z));
            } catch(Exception e){
                getOwner().setMove(true);

                String targetName = order.substring(8);
                
                getOwner().getDestinations().add("[UNIT]" + targetName);
            }
        } else {
            return false;
        }
        return true;
    }

}
