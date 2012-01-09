/**
 * Copyright 2005 Sean J. Barbeau

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.barbeau.networks.astar;

import com.barbeau.networks.visualization.MapDisplay;
import com.barbeau.networks.Link;
import java.awt.*;

/**
 * This class defines the one-way links that attach nodes to each other and have X and Y locations,
 * used for GUI operations
 * @author Sean Barbeau
 */
public class LinkXY extends Link{
        
        
    //Graphics characteristics of this link
    private Color color;
    
    /**
     * Creates a new instance of Link between two nodes
     * @param nodeA
     * @param nodeB 
     */
    public LinkXY(NodeXY nodeA, NodeXY nodeB) {
        super(nodeA, nodeB);        
        
        //Set default color
        this.color = Color.LIGHT_GRAY;        
    }
    
    /**
     * This function draws an arrow from this.nodeA to this.nodeB with an arrow head pointing at nodeB
     * @param g graphics to use when drawing.  It is called by the map when it is refreshed
     * @param map map to use when drawing
     */
    public void draw_Link(Graphics g, MapDisplay map) {        
        
        //Set color for graphics
        g.setColor(this.color);
        
        //Get coordinates of nodes (takes into account the size of node and scale of map
        int nodeAx = (int)((((NodeXY) nodeA).location.x + ((NodeXY) nodeA).size/2) * map.SCALE);
        int nodeAy = (int)((((NodeXY) nodeA).location.y + ((NodeXY) nodeA).size/2) * map.SCALE);
        int nodeBx = (int)((((NodeXY) nodeB).location.x + ((NodeXY) nodeB).size/2) * map.SCALE);
        int nodeBy = (int)((((NodeXY) nodeB).location.y + ((NodeXY) nodeB).size/2) * map.SCALE);
        
        double stroke = .1;
        
        //Get direction of line      
        double tempDirection = Math.atan2(nodeAx-nodeBx,nodeAy-nodeBy);
                
        //Build Arrow head
        Polygon temp_Head = new Polygon();
        int i1=12+(int)(stroke*2);
        int i2=6+(int)stroke;
        
        //Add points to polygon
        temp_Head.addPoint(nodeBx,nodeBy);
        temp_Head.addPoint(nodeBx+getX(i1,tempDirection+.5),nodeBy+getY(i1,tempDirection+.5));
        temp_Head.addPoint(nodeBx+getX(i2,tempDirection),nodeBy+getY(i2,tempDirection));
        temp_Head.addPoint(nodeBx+getX(i1,tempDirection-.5),nodeBy+getY(i1,tempDirection-.5));
        temp_Head.addPoint(nodeBx,nodeBy);
        
        //Draw line to nodeB from nodeA
        g.drawLine(nodeBx,nodeBy,nodeAx,nodeAy);
        
        //Draw and fill arrow head
        g.drawPolygon(temp_Head);
        g.fillPolygon(temp_Head);
   }
    /**
     * This function draws an arrow from this.nodeA to this.nodeB with an arrow head pointing at nodeB 
     * It is called by the algorithm to define the color of the link
     * @param map map to draw link on
     * @param color color of link
     */
    public void drawLink(MapDisplay map, Color color) {
          
        //Graphic object
        Graphics g;
        
        //Set graphics object
        g = map.getGraphics();
        
        //Set color of graphic object
        g.setColor(color);
        
        //Set color for link
        this.color = color;
        
        //Get coordinates of nodes (takes into account the size of node and scale of map
        int nodeAx = (int)((((NodeXY) nodeA).location.x + ((NodeXY) nodeA).size/2) * map.SCALE);
        int nodeAy = (int)((((NodeXY) nodeA).location.y + ((NodeXY) nodeA).size/2) * map.SCALE);
        int nodeBx = (int)((((NodeXY) nodeB).location.x + ((NodeXY) nodeB).size/2) * map.SCALE);
        int nodeBy = (int)((((NodeXY) nodeB).location.y + ((NodeXY) nodeB).size/2) * map.SCALE);
        
        double stroke = .1;
        
        //Get direction of line      
        double tempDirection = Math.atan2(nodeAx-nodeBx,nodeAy-nodeBy);
                
        //Build Arrow head
        Polygon tempHead = new Polygon();
        int i1=12+(int)(stroke*2);
        int i2=6+(int)stroke;
        
        //Add points to polygon
        tempHead.addPoint(nodeBx,nodeBy);
        tempHead.addPoint(nodeBx+getX(i1,tempDirection+.5),nodeBy+getY(i1,tempDirection+.5));
        tempHead.addPoint(nodeBx+getX(i2,tempDirection),nodeBy+getY(i2,tempDirection));
        tempHead.addPoint(nodeBx+getX(i1,tempDirection-.5),nodeBy+getY(i1,tempDirection-.5));
        tempHead.addPoint(nodeBx,nodeBy);
        
        //Draw line to nodeB from nodeA
        g.drawLine(nodeBx,nodeBy,nodeAx,nodeAy);
        
        //Draw and fill arrow head
        g.drawPolygon(tempHead);
        g.fillPolygon(tempHead);
        
    }
    
   private static int getY(int length, double direction) {return (int)(length * Math.cos(direction));}
   
   private static int getX(int length, double direction) {return (int)(length * Math.sin(direction));}

   /**
    * Resets the link status to its defaults
    */
    @Override
   public void resetToDefault() {
       super.resetToDefault();              
       this.color = Color.LIGHT_GRAY;      
   }
    
}
