/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.interfaces.brains.behaviors;

import engine.game.FactionManager;
import engine.physics.Coordinate;

/**
 *
 * @author Christopher Hittner
 */
public class GroundUnitOrderProcess extends OrderProcess{

    public GroundUnitOrderProcess(OrderProcess op) {
        super(op);
    }

    @Override
    protected boolean processOrder(String order) {
        
        
        getOwner().setBoard(false);
        
        if(order.indexOf("follow ") == 0){
            
            getOwner().setMove(true);
            
            String targetName = order.substring(7);
            
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
                
                getOwner().getDestinations().add(new Coordinate(X,0.5,Z));
            } catch(Exception e){
                getOwner().setMove(true);
                
                String targetName = order.substring(8);
                
                //getOwner().setBoard(false);
                
                getOwner().getDestinations().add("[UNIT]" + targetName);
            }
        } else {
            return false;
        }
        return true;
    }

}
