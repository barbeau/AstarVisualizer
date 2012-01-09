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
 * This class holds the location information for a node
 * @author Sean Barbeau
 */
public class Location {
    
    public int x; //X coord
    public int y; //Y coord
    
    /** 
     * Creates a new instance of Location 
     */
        public Location(int x, int y)  {
        this.x = x;
        this.y = y;
        }
    
}
