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

import java.util.ArrayList;

/**
 * Defines a generic Node in a generic network
 * @author Sean Barbeau
 */
public class Node {
    public String label; //Name of Node
    public int numConn;  //Number of one way connections to other nodes
    public boolean enabled;  //Sets node as enabled or disabled - allows user to disable certain nodes to reroute search algorithm.

    protected java.util.List children = new ArrayList();  //Holds nodes that this node is connected to (NOTE:  these are directed connections)
    protected java.util.List parents = new ArrayList();  //Holds parent nodes that are connected to this node (NOTE:  these are directed connections, and connections to parents are only required for transversing the graph "upstream", which is not done in algorithms such as Astar)

    public Node(String label) {

        //Set name of node
        this.label = label;

        //Initialize the node as enabled and not visited by default
        this.enabled = true;
    }

    /**
     * This method records a directed connection from this node to a new child new_node
     * @param childNode the child node to be connected to
     */
    public void addConnToChild(Node childNode) {
        try
        {
            //Add node to list
            children.add(childNode);
            System.out.println("Connected " + this.label + " (parent)----> " + childNode.label + "(child)");
        }
        catch (Exception e)
        {
            System.out.println("Error adding connection to child note: " + e);
        }
    }

    /**
     * This method returns a list of nodes that this node is connected to (i.e., child nodes, directed connections)
     * @return a list of nodes that this node is connected to (i.e., child nodes)
     */
    public java.util.List getChildren() {
        return this.children;
    }

    /**
     * This method records a directed connection from this node to a new parent new_node
     * @param parentNode the parent node to be connected to
     */
    public void addConnToParent(Node parentNode) {
        try
        {
            //Add node to list
            parents.add(parentNode);
            System.out.println("Connected " + this.label + " (child)----> " + parentNode.label + "(parent)");
        }
        catch (Exception e)
        {
            System.out.println("Error adding connection to parent note: " + e);
        }
    }

    /**
     * This method returns a list of nodes that are connected to this node (i.e., parent nodes, directed connections)
     * @return a list of nodes that are connected to this node (i.e., parents nodes)
     */
    public java.util.List getParents() {
        return this.parents;
    }
}
