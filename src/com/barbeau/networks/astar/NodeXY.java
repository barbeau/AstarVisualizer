
package com.barbeau.networks.astar;
import com.barbeau.networks.Location;
import com.barbeau.networks.visualization.MapDisplay;
import com.barbeau.networks.Node;
import java.awt.*;

/**
 * This class holds the basic information for a node in a network that has X and Y locations
 * 
 * @author Sean Barbeau
 */
public abstract class NodeXY extends Node implements Comparable {
           
    public NodeXY pathParent;  //This reference to a node will serve as the link to its parent when tracing a path to the goal node.  This way
                              //the path from start to goal can easily be reconstructed when reaching the goal
    public double costFromStart;  //Distance traveled from start node to get to this node
    public double estCostToGoal;  //Estimated distance from this node to the goal, estimated by a heuristic (either # of links or distance traveled
    public Location location;  //Location of node in (x,y)

    //*** Properties of the node used for the graphic MapDisplay ***
    public Color color;  //Variable that holds the current color of the node for the graphic map (used when traversing the path)
    public Color previousColor; //Variable holds the previous color of the node for the graphic map (used when traversing the path)
    public int size; //Defines the current size of the node for the graphic map
    public int previous_size; //Defines the previous size of the node for the graphic map

    /**
     * Creates a new instance of Node with a location to be used for Astar search
     * @param label name of the node
     * @param x X coordinate of the location of the node
     * @param y Y coordinate of the location of the node
     */
    public NodeXY(String label, int x, int y) {
        super(label);
        //Create location property with coords
        this.location = new Location(x, y);
                          
        //Initialize costs
        this.costFromStart = 0;
        this.estCostToGoal = 0;
        
        //Set graphic properties of node
        this.color = Color.DARK_GRAY;
        this.previousColor = Color.DARK_GRAY;
        this.size = 7;
        this.previous_size = 7;
        
        //Print current properties of node
        //System.out.println("Initialized Node '" + this.label + "' to location (" + this.location.x + ", " + this.location.y + ")");
        }

    /**
     * This method calculates the heuristic value f(n) = h(n) + i(n) where h(n) is the cost of the trip from the start node  *
     * to this node "n" and i(n) is the estimated cost to travel from this node "n" to the goal node.  This class will       *
     * be extended to use different heuristics to implement f(n).
     * @return total heuristics cost to the goal
     */
    public double get_total_heuristic_cost() {
               
        return (this.costFromStart + this.estCostToGoal);
    }
    

    /**
     * This function compares two node's costs and returns 1 if this node is greater than, 0 if it is equal to and -1 if it is less than node_B. *
     */
    @Override
    public int compareTo (Object node_B){        
        double cost = this.get_total_heuristic_cost();
        double node_B_Cost = ((NodeXY)node_B).get_total_heuristic_cost();
        
        //Calculate difference in costs
        double temp = cost - node_B_Cost;
        
        if (temp > 0){
            //this node is greater than node_B
            return 1;            
        }
        else {
            if (temp < 0) {
                //this node is less then node_B
                return -1;
            }
            else {
                //this node is equal to node_B
                return 0;
            }
        } 
        
    }
    
    //******************************************************
    //* Graphic methods used to draw nodes on a visual map *
    //******************************************************
    public void drawNode(MapDisplay map, Color color, int size) {
        //***************************************************************************************************
        //* This function Draws the node on the map with the specified color and is called from AstarSearch *
        //***************************************************************************************************
        
        //Graphic object
        Graphics g;       
        //Set graphics object
        g = map.getGraphics();
        //Set previous graphic node properties
        this.previousColor = this.color;
        this.previous_size = this.size;
        
        //Set current graphic node properties
        this.color = color;
        this.size = size;
        
        //Set color of graphic object
        g.setColor(color);
        //Draw circle
        g.fillOval((int)(this.location.x * map.SCALE), (int)(this.location.y * map.SCALE), this.size, this.size);                    
        //Draw text Label
        g.drawString(this.label, (int)(this.location.x * map.SCALE), (int)(this.location.y * map.SCALE));
    }
    
    public void drawNode(Graphics g, MapDisplay map) {
        //*****************************************************************************************
        //* This function draws the node on the map and is called by the map when it is refreshed *
        //*****************************************************************************************
                     
        //Set color of graphic object
        g.setColor(this.color);
        //Draw circle
        g.fillOval((int)(this.location.x * map.SCALE), (int)(this.location.y * map.SCALE), this.size, this.size);                    
        //Draw text Label
        g.drawString(this.label, (int)(this.location.x * map.SCALE), (int)(this.location.y * map.SCALE));
    }
    
    public void emphasizeCurrentNode(MapDisplay map) {
        //This function emphasizes the current node by showing it as expanded and green
        
        //Graphic object
        Graphics g;       
        //Set graphics object
        g = map.getGraphics();        
        
        //Set previous graphic node properties
        this.previousColor = this.color;
        this.previous_size = this.size;
        
        //Set current graphic node properties
        this.color = Color.GREEN;
        this.size = map.getExpandedNodeSize();
        
        //Set color of graphic object
        g.setColor(color);
        //Draw circle
        g.fillOval((int)(this.location.x * map.SCALE), (int)(this.location.y * map.SCALE), this.size, this.size);                    
        //Draw text Label
        g.drawString(this.label, (int)(this.location.x * map.SCALE), (int)(this.location.y * map.SCALE));
        
    }
    
    public void deemphasizeCurrentNode(MapDisplay map) {
        //This function deemphasizes the current node by showing it as regular and its previous color
        
        //Graphic object
        Graphics g;       
        //Set graphics object
        g = map.getGraphics();        
        
        //Set current graphic node properties
        //this.color = this.previousColor;
        //If this node wasn't a start or goal node, then paint it the right color (enabled/disabled)
        if (this.previousColor != Color.BLUE) {
            if(this.enabled == true) {
                this.color = Color.BLACK;
            }
            else {
                this.color = Color.RED;
            }
        }
        else {
            this.color = this.previousColor;
        }
               
        this.size = map.getExpandedNodeSize();
        
        //Set color of graphic object
        g.setColor(color);
        //Draw circle
        g.fillOval((int)(this.location.x * map.SCALE), (int)(this.location.y * map.SCALE), this.size, this.size);                    
        //Draw text Label
        g.drawString(this.label, (int)(this.location.x * map.SCALE), (int)(this.location.y * map.SCALE));
    }
    
    public void setLocation (Location location) {
        this.location.x = location.x;
        this.location.y = location.y;
    }
 
  
    //**************************************************************************************
    //* Abstract methods to be implemented in the extended class for specific Heuristics   *
    //**************************************************************************************
    
    //This function gets the real cost betweeen this node and node_B
    public abstract double getCost(NodeXY node_B, int heuristic);
    
    //This function gets the estimated cost between this ndoe and the goal node
    public abstract double getEstimatedCostToGoal(NodeXY goal_node, int heuristic);
        
}
