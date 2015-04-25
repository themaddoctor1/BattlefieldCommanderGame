/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.gui;

import engine.entities.units.Unit;
import engine.game.FactionManager;
import engine.physics.Coordinate;
import engine.physics.Vector;
import engine.scripts.PauseScript;
import engine.world.LevelManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author Christopher Hittner
 */
public class GameController extends Controller{

    public GameController(String nm) {
        super(nm, new int[0]);
        int[] keys = {
                  KeyEvent.VK_UP            //Camera Up
                , KeyEvent.VK_DOWN          //Camera Down
                , KeyEvent.VK_LEFT          //Camera Left
                , KeyEvent.VK_RIGHT         //Camera Right
                , KeyEvent.VK_SHIFT         //Allow Multi-Unit Selection
                , KeyEvent.VK_CONTROL       //Hold with A to select all units
                , KeyEvent.VK_A             //Hold to choose attack location when right-clicking
                , KeyEvent.VK_B             //Hold to choose boarding target when right-clicking
                , KeyEvent.VK_P        //Press to pause the game
        };
        
        setKeyCodes(keys);
        
        setKeyboardState(keys[8], true);
    }

    @Override
    public void execute(){
        
        double cameraSpeed = 0.2;
        
        if(getKeyboardState(0))
            GUI.getGUI().getCamera().getPosition().addVector(new Vector(cameraSpeed,Math.toRadians(0),0));
        if(getKeyboardState(1))
            GUI.getGUI().getCamera().getPosition().addVector(new Vector(cameraSpeed,Math.toRadians(180),0));
        if(getKeyboardState(2))
            GUI.getGUI().getCamera().getPosition().addVector(new Vector(cameraSpeed,Math.toRadians(90),0));
        if(getKeyboardState(3))
            GUI.getGUI().getCamera().getPosition().addVector(new Vector(cameraSpeed,Math.toRadians(270),0));
        if(getKeyboardState(5) && getKeyboardState(6)){
            //Select all applicable units
            UnitSelection.getSelectedUnits().clear();
            for(int i = 0; i < LevelManager.getLevel().getUnits().size(); i++){
                Unit u = LevelManager.getLevel().getUnits().get(i);
                //If the Unit belongs to the player or the Shift key is pressed
                if("Player".equals(FactionManager.getFactionOf(u.getName())) || getKeyboardState(4))
                    UnitSelection.getSelectedUnits().add(u.getName());
            }
        }
        if(!getKeyboardState(8)){
            (new PauseScript(LevelManager.getRunStatus())).execute(null);
            this.setKeyboardState(getKeyCodeOf(8), true);
        }
    }
    
    @Override
    public void executeLeftClick(MouseEvent me){
        
        if(getKeyboardState(4))
            UnitSelection.shiftClick(me.getX(), me.getY());
        else
            UnitSelection.click(me.getX(), me.getY());
    }
    
    @Override
    public void executeRightClick(MouseEvent me){
        
        
        double dispX = me.getX() - GUI.getGUI().getCenterX();
        double dispY = me.getY() - GUI.getGUI().getCenterY();
        double disp = Math.sqrt(Math.pow(dispX, 2) + Math.pow(dispY, 2));
        double theta = disp / GUI.getGUI().getPixelsPerRadian();
        double lambda = Math.atan(dispY/dispX);
        if(dispX < 0)
            lambda = lambda + Math.toRadians(180);
        lambda += GUI.getGUI().getCamera().getAxialRot() - Math.PI/2.0;
        
        lambda = Math.PI - lambda;
        
        Coordinate c = new Coordinate(
                  (Math.tan(theta) * Math.cos(lambda)) * GUI.getGUI().getCamera().getPosition().Y() + GUI.getGUI().getCamera().getPosition().X()
                , 0
                , (Math.tan(theta) * Math.sin(lambda)) * GUI.getGUI().getCamera().getPosition().Y() + GUI.getGUI().getCamera().getPosition().Z()
        );
        
        String target;
        
        try {
            target = UnitSelection.getClickedUnits(me.getX(), me.getY()).get(0);
        } catch(Exception e){
            target = c.X() + " " + c.Z();
        }
        
        for(int i = UnitSelection.getSelectedUnits().size() - 1; i >= 0; i--) try{
            String nm = UnitSelection.getSelectedUnits().get(i);
            if(GUI.getGUI().getController().getKeyboardState(6))        //The attack key (default is "A") is held down
                GUI.getGUI().parseUserInput("UNIT_ORDER", nm + " attack " + target, "Player");
            else if(GUI.getGUI().getController().getKeyboardState(7))   //The attack key (default is "B") is held down
                GUI.getGUI().parseUserInput("UNIT_ORDER", nm + " board " + target, "Player");
            else
                GUI.getGUI().parseUserInput("UNIT_ORDER", nm + " move to " + target, "Player");
        } catch(Exception e){
            UnitSelection.getSelectedUnits().remove(i);
        }
    }

}
