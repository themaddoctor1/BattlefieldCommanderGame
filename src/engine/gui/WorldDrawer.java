/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.gui;

import engine.entities.terrain.TerrainElement;
import engine.entities.units.Unit;
import engine.entities.*;
import engine.entities.terrain.structures.Building;
import engine.game.FactionManager;
import engine.world.*;
import engine.gui.*;
import engine.physics.Coordinate;
import engine.physics.Vector;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author WHS-D4B1W8
 */
public class WorldDrawer {
    
    public static void drawWorld(Graphics g, Camera c){
        drawWorld(g, c, LevelManager.getLevel());
    }
    
    public static void drawWorld(Graphics g, Camera c, Level l){
        Graphics2D g2 = (Graphics2D) g;
        
        //c.getDirection().addVectorToThis(new Vector(0.001, 0, c.getY() -Math.toRadians(90)));
        
        //Draws the horizon
        g2.setColor(new Color(0,0,0));
        if(Math.sin(c.getY()) != 0){
            int[] straightDown = c.getPlanarCoordinate(new Coordinate(c.getPosition().X(), c.getPosition().Y() - 1, c.getPosition().Z()));
            int height = (int)(GUI.getGUI().getPixelsPerRadian()*Math.PI);
            
            int horizWidth = (int)(GUI.getGUI().getPixelsPerRadian()*Math.PI/Math.pow(Math.sin(-c.getY()),4));
            
            straightDown[0] -= horizWidth/2;
            straightDown[1] -= height/2;
            
            g2.drawOval(straightDown[0], straightDown[1], horizWidth, height);
        } else {
            g2.drawLine(0, GUI.getGUI().getCenterY(), GUI.getGUI().getWidth(), GUI.getGUI().getCenterY());
        }
        
        
        drawUnits(g, c, l);
        drawTerrain(g, c, l);
        
        //Draws every projectile
        g2.setColor(Color.BLACK);
        try{
            for(int i = 0; i < l.getProjectiles().size(); i++){
                Projectile p = l.getProjectiles().get(i);
                int[] position = c.getPlanarCoordinate(p.getPosition());
                int radius = (int)(GUI.getGUI().getPixelsPerRadian() * Math.asin(0.2/Coordinate.relativeDistance(c.getPosition(), p.getPosition()))) + 1;
                
                g2.fillOval(position[0] - radius, position[1] - radius, 2 * radius, 2 * radius);
                
            }

        } catch(NullPointerException npe){
            
        }
        
    }
    
    
    
    public static void drawUnits(Graphics g, Camera c, Level l){
        
        Graphics2D g2 = (Graphics2D) g;
        
        
        //Draws all of the Units.
        try{
            for(int i = 0; i < l.getUnits().size(); i++){
                Unit u = l.getUnits().get(i);
                String factID = FactionManager.getFactionOf(u.getName());
                
                if("Player".equals(factID)){
                    //Player's Unit
                    g2.setColor(new Color(0,0,255,128));
                } else /*if(!"Player".equals(factID))*/{
                    if(FactionManager.getRelationship("Player",factID))
                        g2.setColor(new Color(0,255,0,128));
                    else
                        g2.setColor(new Color(255,0,0,128));
                /*} else {
                    //Unknown Unit
                    g2.setColor(new Color(0,0,0,128));*/
                }
                int[] position = c.getPlanarCoordinate(u.getPosition());
                
                
                int radius = (int)(GUI.getGUI().getPixelsPerRadian() * Math.asin(u.getSize()/Coordinate.relativeDistance(c.getPosition(), u.getPosition())));
                g2.fillOval(position[0] - radius, position[1] - radius, 2 * radius, 2 * radius);
                g2.setColor(new Color(g2.getColor().getRed(),g2.getColor().getGreen(),g2.getColor().getBlue()));
                g2.drawOval(position[0] - radius, position[1] - radius, 2 * radius, 2 * radius);
                
                
                
            }
        } catch(NullPointerException npe){
            
        }
    }
    
    
    
    public static void drawTerrain(Graphics g, Camera c, Level l){
        
        Graphics2D g2 = (Graphics2D) g;
        
        //Draws every TerrainElement
        try{
            g2.setColor(new Color(128,128,128,128));
            for(TerrainElement t : l.getTerrain()){
                
                
                Color def = g2.getColor();
                Color outline = g2.getColor();
                
                if(t instanceof Building){
                    
                    String factID = ((Building) t).getFactionID();
                    
                    if(factID != null){
                        if("Player".equals(factID)){
                            //Player's Building
                            outline = (new Color(0,0,255));
                        } else {
                            if(FactionManager.getRelationship("Player", factID))
                                outline = (new Color(0,255, 0));
                            else{
                                outline = (new Color(255,0,0));
                            }
                        }
                    }
                }
                    
                for(Polygon p : buildTerrainPolygons(t, c)){
                    g2.fillPolygon(p);
                    if(!def.equals(outline)){
                        g2.setColor(outline);
                        g2.drawPolygon(p);
                        g2.setColor(def);
                    }
                }
                
            }

        } catch(NullPointerException npe){
            
        }
    }
    
    public static ArrayList<Polygon> buildTerrainPolygons(TerrainElement t, Camera c){
        
        ArrayList<Polygon> result = new ArrayList<>();
        
        ArrayList<int[]> coordinates = new ArrayList<>();
            for(int i = 0; i < 8; i++){
                Coordinate coord = new Coordinate(t.getPosition().X() + (-0.5 + i%2)*(t.getSize()[0]),t.getPosition().Y() + (-0.5 + (i/2)%2)*(t.getSize()[1]),t.getPosition().Z() + (-0.5 + (i/4)%2)*(t.getSize()[2]));
                coordinates.add(c.getPlanarCoordinate(coord));
            }

        Polygon p = new Polygon();

        //g2.setColor(new Color(255,0,0,128));

        p.addPoint(coordinates.get(0)[0], coordinates.get(0)[1]);
        p.addPoint(coordinates.get(1)[0], coordinates.get(1)[1]);
        p.addPoint(coordinates.get(3)[0], coordinates.get(3)[1]);
        p.addPoint(coordinates.get(2)[0], coordinates.get(2)[1]);
        result.add(p);
        p = new Polygon();

        p.addPoint(coordinates.get(4)[0], coordinates.get(4)[1]);
        p.addPoint(coordinates.get(5)[0], coordinates.get(5)[1]);
        p.addPoint(coordinates.get(7)[0], coordinates.get(7)[1]);
        p.addPoint(coordinates.get(6)[0], coordinates.get(6)[1]);
        result.add(p);
        p = new Polygon();



        //g2.setColor(new Color(0,255,0,128));

        p.addPoint(coordinates.get(0)[0], coordinates.get(0)[1]);
        p.addPoint(coordinates.get(1)[0], coordinates.get(1)[1]);
        p.addPoint(coordinates.get(5)[0], coordinates.get(5)[1]);
        p.addPoint(coordinates.get(4)[0], coordinates.get(4)[1]);
        result.add(p);
        p = new Polygon();

        p.addPoint(coordinates.get(2)[0], coordinates.get(2)[1]);
        p.addPoint(coordinates.get(3)[0], coordinates.get(3)[1]);
        p.addPoint(coordinates.get(7)[0], coordinates.get(7)[1]);
        p.addPoint(coordinates.get(6)[0], coordinates.get(6)[1]);
        result.add(p);
        p = new Polygon();



        //g2.setColor(new Color(0,0,255,128));

        p.addPoint(coordinates.get(0)[0], coordinates.get(0)[1]);
        p.addPoint(coordinates.get(2)[0], coordinates.get(2)[1]);
        p.addPoint(coordinates.get(6)[0], coordinates.get(6)[1]);
        p.addPoint(coordinates.get(4)[0], coordinates.get(4)[1]);
        result.add(p);
        p = new Polygon();

        p.addPoint(coordinates.get(1)[0], coordinates.get(1)[1]);
        p.addPoint(coordinates.get(3)[0], coordinates.get(3)[1]);
        p.addPoint(coordinates.get(7)[0], coordinates.get(7)[1]);
        p.addPoint(coordinates.get(5)[0], coordinates.get(5)[1]);
        result.add(p);
        
        return result;

                
    }
    
}
