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

/**
 * This class allows an implementation of the A*Star algorithm with different heuristics depending on which is
 * picked by the user.
 * Each of the functions in this class need a new option for each new heuristic
 * Current options for Heuristics:
       1:  Fewest Links
       2:  Shortest Distance
 * When adding a new heuristic, another option must be added to the "switch" statement in both the getCost() and
 * getEstimatedCostToGoal() statements
 *
 * @author Sean J. Barbeau
 */
public class HeuristicsNode extends NodeXY {
       
    /**
     * Creates a new instance of a Heuristics Node
     * @param label label for the node
     * @param x x position of the node
     * @param y y position of the node
     */
    protected HeuristicsNode(String label, int x, int y){
        super(label, x, y);
    }    
    
    
    /**
     * This function gets the real cost betweeen this node and node_B
     * @param nodeB
     * @param heuristic
     * @return actual cost from this node to node_B
     */
    @Override
    public double getCost(NodeXY nodeB, int heuristic){
        //Get the cost from one node to another according to a heuristic
        
        //Initialize variables
        double cost = 0;
        
        //Select heuristic
        switch (heuristic) {
            case 1:
                //Fewest Links
                cost = 1;
                break;
            case 2:
                //Shortest Distance - measure the distance between nodes using D = sqrt( (x2-x1)squared + (y2-y1)squared )
                double totalDistance = 0;
                
                int tempx = this.location.x - nodeB.location.x;
                int tempy = this.location.y - nodeB.location.y;
        
                tempx = tempx * tempx;
                tempy = tempy * tempy;
                
                try {
                    totalDistance = Math.sqrt(tempx + tempy);
                }        
                catch(Exception E)
                {
                    System.out.println("Error in get_Distance()  "+E);
                    totalDistance = 0;//In case of error
                }
                cost = totalDistance;
                break;
            //case 3:
                //XXXXXXXXXXXXXX - method to calculate a new heuristic
                //ADD NEW HEURISTIC HERE:  CALCULATE THE COST AND SET THE VARIABLE cost = TO THE CALCULATED COST
                //break;
            default:
                //Use fewest Links as default
                cost = 1; //It is known that 1 will definitely be an underestimate, so 1 is used
                break;
         }
        
        //Return the cost calculated by the heuristic
        return cost;
    }
    

    /**
     * This function gets the estimated cost between this node and the goal node
     * @param goalNode
     * @param heuristic type of heuristic to be used
     * @return estimated cost to goal
     */
    @Override
    public double getEstimatedCostToGoal(NodeXY goalNode, int heuristic) {
        //Initialize variable for cost
        double estCost = 0;
        
        //Select which heuristic to use
        switch(heuristic) {
            case 1:
                //Fewest Links Heuristic
                estCost = 1; //It is known that 1 will definitely be an underestimate, so 1 is used
                break;
            case 2:
                //Shortest Distance                
                estCost = this.getCost(goalNode, heuristic); //Uses the other function to calculate distance between nodes,
                //since the direct distance from this node to the goal is an underestimate of the total distance to the goal
                break;
            //case 3:
                //XXXXXXXXXXXXXX - method to calculate a new heuristic
                //ADD NEW HEURISTIC HERE:  CALCULATE THE ESTIMATED COST TO THE GOAL AND SET THE VARIABLE est_cost = TO THE CALCULATED COST
                //break;
            default:
                //Use Fewest Links as default
                estCost = 1; //It is known that 1 will definitely be an underestimate, so 1 is used
                break;
        }
        //Return value
        return estCost;
    }
    
    
    
}
