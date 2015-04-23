/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.interfaces.brains.behaviors.orderprocess;

import engine.game.FactionManager;
import engine.physics.Coordinate;

/**
 *
 * @author Christopher Hittner
 */
public class AircraftOrderProcess extends OrderProcess{

    public AircraftOrderProcess(OrderProcess op) {
        super(op);
    }

    @Override
    protected boolean processOrder(String order) {
        
        
        if(order.indexOf("attack ") == 0 || order.indexOf("defend ") == 0){
            //Units won't attack allies, so it may or may not matter (unless the code changes, of course)
            
            getOwner().setMove(true);
            getOwner().setShoot(true);
            
            processOrder("orbit " + order.substring(7));
            
        } else if(order.indexOf("follow ") == 0){
            
            getOwner().setMove(true);
            
            String targetName = order.substring(7);
            
            getOwner().getDestinations().clear();
            getOwner().getDestinations().add(targetName);
        } else if(order.indexOf("orbit ") == 0){
            
            String coord = order.substring(6);
            getOwner().getDestinations().clear();
            
            try {
                double X = Double.parseDouble(coord.substring(0,coord.indexOf(" ")));
                coord = coord.substring(coord.indexOf(" ") + 1);
                double Z = Double.parseDouble(coord);
                
                
                getOwner().getDestinations().add(new Coordinate(X,0,Z));
            } catch(Exception e){
                
                String targetName = coord;
                
                getOwner().getDestinations().add("[UNIT]" + targetName);
            }
            
        } else {
            return false;
        }
        return true;
    }

}
