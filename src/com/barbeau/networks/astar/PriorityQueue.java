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
import java.util.*;

/**
 * This object is used to hold nodes and sort them by the estimated cost to get to the goal, estimated by a heuristic
 * @author Sean J. Barbeau
 */
public class PriorityQueue extends LinkedList {
    
    /** Creates a new instance of PriorityQueue */
    public PriorityQueue() {
        super();
    }
    
    /**
     * This method finds the appropriate location to store the new node that is being added to the queue, and adds it to the queue
     * NOTE:  uses the "Comparable" extention to the Node class and function "compare_To" to find where it should be inserted into the queue.
     * @param newNode node to be added to the queue
     */
    public void add(Comparable newNode) {
        //*****************************************************************************************************
        //* 
        
        try
        {
            for (int i = 0 ; i < this.size() ; i++)  {
                if (newNode.compareTo(get(i)) <= 0) {
                    add(i, newNode);  //Add this new node to the list
                    return;
                }
            }
            //If execution reaches this point then add it as the last node in the list
            addLast(newNode);
        }
        catch (Exception e)
        {
         System.out.println("Error in adding element in PriorityQueue object:" + e)   ;
        }
        
}

/* public void sort() {
    //This function sorts the Priority Queue in case any elements comparable features have changed
    try {
        for (int i = 0 ; i < this.size() ; i++)  {
            for (int j = 0 ; j < this.size() ; j++)  {
                if (((Comparable)get(j)).compareTo(get(i)) >= 0) {                    
                    if(i == 0) {
                        this.addFirst(this.remove(j)); //Move to beginning
                    }
                    else {
                        if(i == this.size()) {
                            this.addLast(this.remove(j));  //Move to end
                        }
                        else {
                            this.add(i, this.remove(j)); //Move to current position
                        }
                    }                  
                }
            }
        }
    }
    catch (Exception e) {
        System.out.println("Error sorting PriorityQueue object:" + e)   ;
    }
} */
    /**
     * This is a test method to print the current contents of the Priority Queue in order from lowest (first) to highest (last) 
     */
    public void printContents() {
        
        try
        {
            System.out.println("------------------------");
            System.out.println("PriorityQueue Contents:");
            System.out.println(this.size() + " elements.");
            while (!this.isEmpty())  {
                HeuristicsNode test = (HeuristicsNode)removeFirst();
                System.out.println(test.label + " at cost of " + test.get_total_heuristic_cost());
                }
            
        } 
        catch (Exception e)
        {
         System.out.println("Error in printing PriorityQueue contents:" + e)   ;
        }
    }
}
