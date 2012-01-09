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

/**
 * This class defines a generic directed link in a network of Nodes
 * @author Sean Barbeau
 */
public class Link {
    protected Node nodeA;  //Starting point of link
    protected Node nodeB;  //Ending point of link
    public String label; //Name of Node
    public boolean enabled = true;  //Enabled state of link
    public boolean traveled = false;  //Whether the link has been traveled or not

    public Link(Node nodeA, Node nodeB) {
        //Assign nodes
        this.nodeA = nodeA;
        this.nodeB = nodeB;

        //Set label
        this.label = nodeA.label + "->" + nodeB.label;
    }

    /**
     * Resets the link to the default state
     */
    public void resetToDefault() {
        this.enabled = true;
        this.traveled = false;
    }

}
