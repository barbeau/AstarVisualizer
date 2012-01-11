/**
 * Copyright 2011 Sean J. Barbeau

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
 * This enumeration defines heuristics that can be used with the A* algorithm.
 * 
 * @author Sean J. Barbeau
 */
public enum Heuristic {
    FEWEST_LINKS, 
    SHORTEST_DISTANCE //Shortest Distance - measure the distance between nodes using D = sqrt( (x2-x1)squared + (y2-y1)squared )
}
