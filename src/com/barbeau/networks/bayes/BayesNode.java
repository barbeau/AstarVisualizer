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

package com.barbeau.networks.bayes;

import Jama.Matrix;
import com.barbeau.networks.Node;
import com.barbeau.networks.astar.HeuristicsNode;
import java.util.ArrayList;



/**
 * This class defines a node that contains properties that are used in Bayesian networks
 *
 * @author Sean Barbeau
 */
public class BayesNode extends HeuristicsNode {

    /**
     * Matrix used to hold probabilities
     */
    Matrix matrix;

    /**
     * Names of the rows of the Matrix
     */
    ArrayList rowNames;
    
    public BayesNode(String label, int x, int y) {
        super(label, x, y);

    }

    public ArrayList getRowNames() {
        return rowNames;
    }

    public void setRowNames(ArrayList rowNames) {
        this.rowNames = rowNames;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    
}
