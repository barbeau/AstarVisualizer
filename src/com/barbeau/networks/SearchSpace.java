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

package com.barbeau.networks;
import java.util.*;

/**
 * This class defines the "search space" for the algorithm, which are a list of nodes that can be visited
 * @author Sean J. Barbeau
 */
 public class SearchSpace {
       
    private java.util.List nodes; //A list of all nodes that are loaded into memory from the info in the text files
    private java.util.List links;  //A list of all links that connect the nodes
    
    /**
     * Default constructor for SearchSpace
     */
    public SearchSpace() {
        nodes = new ArrayList();
        links = new ArrayList();
    }
    
    //*****************************************************
    //* Functions for list operations on the search space *
    //*****************************************************
    
    /**
     * Add node to search space
     * @param n node to be added to search space
     */
    public void add(Node n){
        this.nodes.add(n);
    }
    
    /**
     * Remove node from search space
     * @param n node to be removed from the search space
     */
    public void remove(Node n){
        this.nodes.remove(n);
    }
    
    /**
     * Get number of nodes in search space
     * @return number of nodes in the search space
     */
    public int getNodeSize() {
        return this.nodes.size();
    }
    
    /**
     * Get iterator to loop through nodes in search space
     * @return iterator to loop through nodes in search space
     */
    public Iterator getNodeIterator(){
        return this.nodes.iterator();
    } 
    
    //**********************************************
    //* Functions for list operations on the links *
    //**********************************************
    
    /**
     * Add a link to the search space
     * @param l link to be added to the search space
     */
    public void add(Link l){
        this.links.add(l);
    }

    /**
     * Removes a link from the search space
     * @param l link to be removed from the search space
     */
    public void remove(Link l){
        this.links.remove(l);
    }
    
    /**
     * Gets the number of links in the search space
     * @return the number of links in the search space
     */
    public int getLinkSize() {
        return this.links.size();
    }
    
    /**
     * Gets the iterator to loop through links
     * @return the iterator to loop through links
     */
    public Iterator getLinkIterator(){
        return this.links.iterator();
    }       

    /**
     * This function finds and returns a node within the search space with a particular label, or returns NULL if it couldn't be found
     * @param label the label of the node to search for
     * @return a node represented by the input label, or null if the node isn't found
     */
   public Node findNode(String label){        
        //Temp node used to loop through search_space
        Node tempNode;
        
        //Get iterator to loop through search_space
        Iterator i = this.nodes.iterator();
        
        while (i.hasNext()) {
            //Get next node
            tempNode = (Node)i.next();
            //Compare two strings - NOTE:  THIS IS CASE-SENSITIVE!!!!!
            if(tempNode.label.equals(label)) {
                return tempNode;  //found node!
            }        
        }

        //If it reaches this return statement then the node wasn't found
        return null;
    }

   /**
    * This function finds and returns a link within the search space from nodeA to nodeB, or returns NULL if it couldn't be found
    * @param nodeA
    * @param nodeB
    * @return a link within the search space from nodeA to nodeB, or returns NULL if it couldn't be found
    */
   public Link findLink(Node nodeA, Node nodeB){
        
        //Temp link used to loop through search_space
        Link temp_link;
        
        //Get iterator to loop through search_space
        Iterator i = this.links.iterator();
        
        while (i.hasNext()) {
            //Get next node
            temp_link = (Link)i.next();
            //Compare two strings - NOTE:  THIS IS CASE-SENSITIVE!!!!!
            if((nodeA.label + "->" + nodeB.label).equals(temp_link.label)) {
                return temp_link;  //found link!
            }        
        }

        //If it reaches this return statement then the link wasn't found
        return null;
    }
    
}
