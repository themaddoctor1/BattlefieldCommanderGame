/*
 * CurvedPolygon is a class that allows one to draw polygons with curved sides.
 */

package engine.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import engine.physics.Coordinate;

/**
 *
 * @author Christopher Hittner
 */
public class CurvedPolygon {
    
    private Coordinate[] coords;
    private double divisions;
    
    public CurvedPolygon(Coordinate[] c, double divides){
        coords = c;
        divisions = divides;
    }
    
    public void drawPolygon(Graphics g, Camera c){
        for(int i = 0; i < coords.length; i++)
            drawCurvedLine(g, c, divisions, coords[i], coords[(i+1)%coords.length]);
        
    }
    
    public void fillPolygon(Graphics g, Camera c){
        Graphics2D g2 = (Graphics2D) g;
        
        ArrayList<Coordinate> coordinates = getDrawnCoordinates();
        
        Coordinate control = coordinates.get(0);
        
        for(int i = 0; i < coordinates.size(); i++){
            int[]   aCoord = c.getPlanarCoordinate(control)
                  , bCoord = c.getPlanarCoordinate(coordinates.get(i))
                  , cCoord = c.getPlanarCoordinate(coordinates.get((i+1)%coordinates.size()));
            int[]   xpts = {aCoord[0], bCoord[0], cCoord[0]}
                    , ypts = {aCoord[1], bCoord[1], cCoord[1]};
            
            g2.fillPolygon(new Polygon(xpts,ypts,3));
            
        }
        
    }
    
    public ArrayList<Coordinate> getDrawnCoordinates(){
        
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        
        for(int i = 0; i < coords.length; i++){
            
            Coordinate start = coords[i];
            Coordinate finish = coords[(i+1)%coords.length];
            
            for(int j = 0; j < divisions; j++){
                coordinates.add(new Coordinate(
                          start.X() + (finish.X() - start.X())*j/(divisions)
                        , start.Y() + (finish.Y() - start.Y())*j/(divisions)
                        , start.Z() + (finish.Z() - start.Z())*j/(divisions)
                ));

            }
        }
        
        return coordinates;
        
    }
    
    public static void drawCurvedLine(Graphics g, Camera c, double divisions, Coordinate start, Coordinate finish){
        for(int i = 0; i < divisions; i++){
            Coordinate a = new Coordinate(
                      start.X() + (finish.X() - start.X())*i/(divisions)
                    , start.Y() + (finish.Y() - start.Y())*i/(divisions)
                    , start.Z() + (finish.Z() - start.Z())*i/(divisions)
            );
            
            Coordinate b = new Coordinate(
                      start.X() + (finish.X() - start.X())*(i+1)/(divisions)
                    , start.Y() + (finish.Y() - start.Y())*(i+1)/(divisions)
                    , start.Z() + (finish.Z() - start.Z())*(i+1)/(divisions)
            );
            
            int[] A = c.getPlanarCoordinate(a);
            int[] B = c.getPlanarCoordinate(b);
            
            //camera.drawCoordinate(g, a);
            ((Graphics2D)g).drawLine(A[0], A[1], B[0], B[1]);
            //camera.drawCoordinate(g, b);
            
            
        }
    }
    
}
