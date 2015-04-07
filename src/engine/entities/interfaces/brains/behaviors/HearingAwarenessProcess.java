/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.interfaces.brains.behaviors;

import engine.entities.units.Unit;
import engine.physics.Coordinate;
import engine.world.LevelManager;
import java.util.ArrayList;

/**
 *
 * @author Christopher Hittner
 */
public class HearingAwarenessProcess extends AwarenessProcess{
    
    private final double THRESHOLD;
    
    public HearingAwarenessProcess(double threshold){
        super();
        THRESHOLD = threshold;
    }
    
    public HearingAwarenessProcess(AwarenessProcess ap, double threshold){
        super(ap);
        THRESHOLD = threshold;
    }
    
    @Override
    public void actAwareness(Unit me, double factor) {
        
    }

    @Override
    protected ArrayList<Unit> seenUnits(Unit me) {
        ArrayList<Unit> heard = new ArrayList<>();
        
        for(int i = 0; i < LevelManager.getLevel().getUnits().size(); i++){
            Unit u = LevelManager.getLevel().getUnits().get(i);
            
            if(!u.equals(me)){
                //I tried to make it work a bit like it's measured in decibels. Decibels is the log of intensity times ten (deci- is 1/10th of a whole).
                double loudness = 10 * Math.log10(u.getSize() * Math.pow(THRESHOLD,1.25) / Math.cbrt(Coordinate.relativeDistance(me.getPosition(), u.getPosition()) - (me.getSize() + u.getSize())) + 
                                        (Math.pow(u.getVelocity().getMagnitude(),1.5) + 0.1) * Math.pow(u.getSize(),3)
                                        /
                                        ((Math.sqrt(me.getVelocity().getMagnitude())+0.1) * Math.pow(Coordinate.relativeDistance(me.getPosition(), u.getPosition()) - (me.getSize() + u.getSize()),2))
                );

                //Add it if the sound is over the threshold.
                if(loudness >= THRESHOLD){
                    heard.add(u);
                }
            }
        }
        
        return heard;
    }

    @Override
    protected void combatProcess(Unit targ) {
        
    }

}
