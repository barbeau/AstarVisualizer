package com.barbeau.networks.visualization;

import com.barbeau.networks.Node;
import com.barbeau.networks.SearchSpace;
import com.barbeau.networks.astar.HeuristicsNode;
import com.barbeau.networks.astar.LinkXY;
import com.barbeau.networks.Location;
import com.barbeau.networks.astar.NodeXY;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class defines the GUI elements used to display a search space, nodes, and links
 * @author Sean Barbeau
 */
public class MapDisplay extends JPanel implements MouseListener, MouseMotionListener {
    
    //Variable that contains the search space and all the nodes
    private SearchSpace search_space = new SearchSpace();  //Search space for algorithm
    //private HeuristicsNode start_node;  //Start node for search algorithm
    //private HeuristicsNode goal_node;  //Goal node for search algorithm
    private LinkedList path = null; //Variable that holds the path if the goal node is found
    private NodeXY node_being_moved = null; //Variable that holds the node being moved by clicking and dragging mouse
    
    //Size of search space
    private int SEARCH_SPACE_WIDTH;
    private int SEARCH_SPACE_HEIGHT;
    
    //Define normal size of nodes on map;
    private int NORMAL_NODE_SIZE;
    //Define size of current node on map;
    private int EXPANDED_NODE_SIZE;
    //Defines the scale to which the map will drawn to the search_space size
    public double SCALE;
    
    //Text box to print messages to the user
    private javax.swing.JTextArea text_log;
    //Char for newline for text log
    private static String newline = "\n";
    
    //Tells whether an active map is loaded (for enabling and disabling or moving nodes)
    private boolean allow_clicks = false;
    
    //Tells whether a node can be moved using mouse
    private boolean allow_move_node = false;
    
    //Tells whether a node can be disabled/enabled using mouse
    private boolean allow_disable_node = true;
    
    /** Creates a new instance of MapDisplay */
    public MapDisplay() {
        super();
        
        //Set background color
        setBackground(Color.WHITE);
        
        //Add listeners to detect mouse clicks and drags
        addMouseListener(this);
        addMouseMotionListener(this);
    }
  
    @Override
    public void paintComponent(Graphics g) {
        //****************************************************************************
        //* This function is the basic function that paints the map and its contents *
        //****************************************************************************
        
        //Set backgound color
        this.setBackground(Color.WHITE);
        
        //Draw panel
        super.paintComponent(g);
        
        //Draw the contents of the panel
        paintContents(g);
        
    }
    
    public void paintContents(Graphics g) {
        //**********************************************************
        //* Draw the current contents of the map (nodes and lines) *
        //**********************************************************
       
       //*** Draw all links on map ***
       Iterator i2 = this.search_space.getLinkIterator();
                        
       LinkXY temp_link; //Variable to loop through search space
        
            while (i2.hasNext()) {
                temp_link = (LinkXY)i2.next();  //Get next link
                
                //System.out.println("Redrawing map - link " + temp_link.label);
            
                temp_link.draw_Link(g, this);  //Draw link on map                            
            }
       
       //*** Emphasize traveled links by drawing them on top of previous links on map ***
       Iterator i4 = this.search_space.getLinkIterator();
                        
            while (i4.hasNext()) {
                temp_link = (LinkXY)i4.next();  //Get next link
                
                if(temp_link.traveled == true) {
                    //System.out.println("Redrawing map - traveled link " + temp_link.label);
            
                    temp_link.draw_Link(g, this);  //Draw link on map                            
                }               
            }

        //*** Emphasize disabled links by drawing them on top of previous links on map ***
       Iterator i3 = this.search_space.getLinkIterator();
                        
            while (i3.hasNext()) {
                temp_link = (LinkXY)i3.next();  //Get next link
                
                if(temp_link.enabled ==false) {
                    //System.out.println("Redrawing map - disabled link " + temp_link.label);
            
                    temp_link.draw_Link(g, this);  //Draw link on map                            
                }               
            }
       
       //*** Emphasize path to goal by drawing them on top of previous links on map ***
       if(this.path != null) {
        
            NodeXY temp_node;
            NodeXY previous_node = null;
            
            boolean first_node = true;
            
            Iterator i = path.iterator();  //Get iterator to loop through search_space
            
            //Print path with no newline between node labels
            while (i.hasNext()) {
                //Get next node
                temp_node = (NodeXY)i.next();
                    
                //Draw connections
                if(first_node == true) {
                    //Do nothing if first node since there is no previous node
                    first_node = false;
                }
                else {
                    //***Paint path on map ***
                    //Get link that defines that connection
                    try {
                        temp_link = (LinkXY) this.search_space.findLink(previous_node, temp_node);
                                        
                        if(temp_link != null) {
                            //System.out.println("Redrawing map - path link " + temp_link.label);
                            temp_link.draw_Link(g, this);                
                        }
                    }
                    catch(Exception e) {
                        System.out.println("Error in MapDisplay.paintContents: " + e);
                    }
                                                                        
                }
                
                //Set previous node
                previous_node = temp_node;
            }
        }
       
       //*** Draw nodes on the map ***
       Iterator i = this.search_space.getNodeIterator();
                        
       HeuristicsNode temp_node2; //Variable to loop through search space
        
            while (i.hasNext()) {
                temp_node2 = (HeuristicsNode)i.next();  //Get next node
//                temp_node2 = (Node) i.next();
//                if(temp_node2 instanceof HeuristicsNode){
//                    ((HeuristicsNode)temp_node2).drawNode(g, this);  //Draw node on map
//                }

                 temp_node2.drawNode(g, this);  //Draw node on map
                //System.out.println("Redrawing map - node " + temp_node2.label);
            
                                            
            }      
        
    }

    /**
     * This function clears the image of the map to a white background
     */
    public void clear() {        
        
        //Set backgound color
        this.setBackground(Color.WHITE);
        
        //Draw panel
        super.paintComponent(this.getGraphics());
    }
    
    public void setSearchSpace(SearchSpace search_space, int SEARCH_SPACE_WIDTH, int SEARCH_SPACE_HEIGHT, double SCALE, int NORMAL_NODE_SIZE, int EXPANDED_NODE_SIZE) {
        //************************************************************
        //* This function sets the search_space variables of the map *
        //************************************************************
        this.search_space = search_space;
        
        //Set up dimensions of search space and scale to draw it to
        this.SEARCH_SPACE_WIDTH = SEARCH_SPACE_WIDTH;
        this.SEARCH_SPACE_HEIGHT = SEARCH_SPACE_HEIGHT;
        this.SCALE = SCALE;    
        this.NORMAL_NODE_SIZE = NORMAL_NODE_SIZE;
        this.EXPANDED_NODE_SIZE = EXPANDED_NODE_SIZE;
    }
    
    public void setPath(LinkedList path) {
        //The path will be stored in this variable if it is found after the algorithm runs
        this.path = path;
    }
    
    public void setTextLog(javax.swing.JTextArea text_log) {
        //Sets text log
        this.text_log = text_log;
    }
    
    public void setAllowClicks(boolean value) {
        this.allow_clicks = value;
    }
    
    public void setAllowDisableNode (boolean value) {
        this.allow_disable_node = value;
    }
    
    public void setAllowMoveNode (boolean value) {
        this.allow_move_node = value;
    }
    
    public int getNormalNodeSize() {
        return this.NORMAL_NODE_SIZE;
    }
    
    public int getExpandedNodeSize() {
        return this.EXPANDED_NODE_SIZE;
    }
    
    public void print_to_log(String text) {
        //This function allows this object to print messages to the user
        try 
        {
            //This method prints output shown to the user in the textbox on the screen & prints to System.out as well
            this.text_log.append(text + newline); //Adds newline character so each entry is a new line
       
            //Moves cursor to the end of the text area to keep new text in view
            this.text_log.setCaretPosition(text_log.getDocument().getLength());

            //Print out to output screen also
            System.out.println(text);     
        }
        catch (Exception e)
        {
            System.out.println("Error in print_to_log:" + e);     
        }      
   }
    
    //************************************************
    //* Methods to handle mouse clicks and movements *
    //************************************************
    // Handles the event of the user pressing down the mouse button.
    public void mousePressed(MouseEvent e){
        //Detect if map has been loaded to prevent from null accesses to variables        
        if(this.allow_clicks == true) {
            //Create temp location to hold current mouse click
            Location location = new Location(e.getX(), e.getY());
        
            NodeXY temp_node;
            temp_node = this.get_closest_Node(location);
            
            node_being_moved = temp_node;
            
            if(allow_disable_node == true) {
                //If node = enabled, then disable it, and vice versa
                if(temp_node.enabled == true) {
                    //Set node as disabled
                    temp_node.enabled = false;
            
                    //Draw node as disabled
                    temp_node.drawNode(this, Color.RED, temp_node.size);
            
                    //Print message to user
                    this.print_to_log("Disabled node " + temp_node.label);
                }
                else {
                    //Set node as enabled
                    temp_node.enabled = true;
            
                    //Draw node as enabled
                    temp_node.drawNode(this, Color.BLACK, temp_node.size);
            
                    //Print message to user
                    this.print_to_log("Enabled node " + temp_node.label);
                } 
            }            
        }
    }
    public void mouseReleased(MouseEvent e){
        //System.out.println("Released mouse on map: x = " + e.getX() + ", y = " + e.getY());        
	}
    public void mouseDragged(MouseEvent e){
         //System.out.println("Dragging mouse: x = " + e.getX() + ", y = " + e.getY());
         //Used to move a node
         if(this.allow_clicks == true && this.allow_move_node == true) {
            updateLocation(e);           
         }           
}
    public void updateLocation(MouseEvent e) {
        //**************************************************************
        //* Moves the currently selected node in this.node_being_moved *
        //**************************************************************
        int x = e.getX();
        int y = e.getY();
        
        //Check to make sure x and y are within the bounds of the map displayed
        //For X
        if(x < 0) {
            x = 1;
        }
        if(x > this.getWidth()) {
            x = this.getWidth() - 1;
        }
        //For Y
        if(y < 0) {
            y = 1;
        }
        if(y > this.getHeight()) {
            y = this.getHeight() - 1;
        }
        
        //Convert coordinates according to the scale of map to the search space coordinates
        x = (int) (x / this.SCALE);
        y = (int) (y / this.SCALE);
        
        //Covert coordinates to stay within search space
        if(x > this.SEARCH_SPACE_WIDTH) {
            x = this.SEARCH_SPACE_WIDTH - 1;            
        }        
        if(y > this.SEARCH_SPACE_HEIGHT) {
            y = this.SEARCH_SPACE_HEIGHT - 1;            
        }
        
        System.out.println("Dragging node " + node_being_moved.label + " - new location: x = " + x + ", y = " + y);
        
        //Set location variable
        Location location = new Location(x, y);
        
        //Set node's new location
        this.node_being_moved.setLocation(location);
        
        //Repaint map
        this.repaint();
        
}
    
   public void mouseMoved(MouseEvent e){}
   public void mouseClicked(MouseEvent e){}
   public void mouseExited(MouseEvent e){}
   public void mouseEntered(MouseEvent e){}
   
   public NodeXY get_closest_Node(Location location) {
    //*************************************************************************************************   
    //* This function gets and returns the node closest to the given location within the search space *
    //*************************************************************************************************
       
        //Get closest node from Search space
        Iterator i = this.search_space.getNodeIterator();
           
        NodeXY temp_node = null; //Variable to loop through search space
        NodeXY closest_node = null;  //Variable to hold the closest node
        
        double current_min = -1;
        double temp_min = 0;
        
            while (i.hasNext()) {
                //Get next node
                temp_node = (NodeXY)i.next();
                //System.out.println("Checking node " + temp_node.label);
                if (current_min == -1) {
                    //Get first node and set initial distance
                    closest_node = temp_node;
                    Location temp_loc = new Location ((int)(temp_node.location.x * this.SCALE), (int)(temp_node.location.y * this.SCALE));
                    current_min = get_Distance(location, temp_loc);
                    //System.out.println("First node set is " + temp_node.label);
                }
                else {
                    //Compare to see if this node is closer
                    Location temp_loc2 = new Location ((int)(temp_node.location.x * this.SCALE), (int)(temp_node.location.y * this.SCALE));
                    temp_min = get_Distance(location, temp_loc2);
                    //System.out.println("Comparing temp_min = " + temp_min + " to current_min = " + current_min);
                    if (temp_min < current_min) {
                        //Get new closest node
                        closest_node = temp_node;
                        current_min = temp_min;
                    }
                //System.out.println("Current closest node = " + closest_node);
                }
            }
       
       return closest_node;
   }
   
   public double get_Distance(Location A, Location B) {
        //******************************************************************************************************
        //* This function measures the distance between two points Location A (x1, y1) and Location B (x2, y2) *
        //******************************************************************************************************
        
        double total_distance = 0;
                
        int tempx = A.x - B.x;
        int tempy = A.y - B.y;
        
        tempx = tempx * tempx;
        tempy = tempy * tempy;
                
        try {
            total_distance = Math.sqrt(tempx + tempy);
        }        
        catch(Exception E)
        {
            System.out.println("Error in get_Distance()  "+E);
        }
        
        return total_distance;
    }
}
