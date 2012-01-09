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
import com.barbeau.networks.SearchSpace;
import com.barbeau.networks.astar.LinkXY;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

/**
 * This class parses a Genie XDSL file and produces a network filled with BayesNode nodes
 * @author Sean Barbeau
 */
public class GenieParser extends DefaultHandler {

    StringBuilder name = new StringBuilder(40); //holds the string for the names of elements
    StringBuilder csvCoordinatesText = new StringBuilder(1000); //holds the KML text containing spatial data
    String temp;  //holds temporary tag string values
    String nodeName;  //holds the temp value of node name    
    boolean readingNodes = false;  //Flag which handles reading nodes
    boolean readingStates = false;
    int rowCount= 0;
    ArrayList rowNames = new ArrayList();
    boolean readingProbabilities = false;  //Flag which handles reading the probabilities

    StringBuffer probabilities = new StringBuffer(100);
    StringBuffer parents = new StringBuffer(10);
    boolean readingParents = false;

    //Variables used to hold network information
    SearchSpace searchSpace;

    //Generate random node positions
    Random rand = new Random();
    int min = 100, max = 700;

    public GenieParser() {
    }

    public void parseXDSLFile(String networkFileName, SearchSpace searchSpace) {
        //Save reference to SearchSpace
        this.searchSpace = searchSpace;

        //Read the XML file
        File file = new File(networkFileName);
        try {
            parseNetworkFromXDSL(new FileInputStream(file));
        } catch (SAXException ex) {
            Logger.getLogger(GenieParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(GenieParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GenieParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Event Handlers for SAX parsing
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        
        probabilities.setLength(0);
        
//        if (qName.equalsIgnoreCase("nodes")) {
//            readingNodes = true;
//        } else {
//            readingNodes = false;
//        }

        //System.out.println("qName: " + qName);

        //System.out.println("There are " + attributes.getLength() + " attributes.");

        int length = attributes.getLength();
        
        if (qName.equalsIgnoreCase("cpt")) {
            readingNodes = true;
            for(int i = 0;i < length; i++){
                //System.out.println("Qname: " + attributes.getQName(i) + ", value = " + attributes.getValue(i));
                nodeName = attributes.getValue(i);
            }
        } else {
            readingNodes = false;
        }

        if (qName.equalsIgnoreCase("probabilities")) {
            readingProbabilities = true;
        } else {
            readingProbabilities = false;
        }

        if (qName.equalsIgnoreCase("state")) {
            //System.out.println("Incrementing row count...");
            rowCount++;

            //Save row names
            for(int i = 0;i < length; i++){
                //System.out.println("Qname: " + attributes.getQName(i) + ", value = " + attributes.getValue(i));
                rowNames.add(attributes.getValue(i));
            }
        }

        if (qName.equalsIgnoreCase("parents")) {
            readingParents = true;
        } else {
            readingParents = false;
        }
       
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        temp = new String(ch, start, length);
        //System.out.println("characters: " + temp);
      
        //If we're reading the HREF qName, then get the text to possibly append to HREF
        if (readingParents) {
            parents.append(temp);
        }

        //If we're reading the Coordinates qName, then get the text to possibly append to the coordinates CSV file
        if (readingProbabilities) {
             probabilities.append(temp);
        }

        //System.out.println("tag content: " + temp);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //Do nothing when we've finished reading an XML tag element
        //		if(qName.equalsIgnoreCase("href")) {
        //			//Save Href link, and replace "&amp;" with "&"
        //			//href = temp.replace("&amp;", "&");
        //			href = temp;
        //		}

        //When we reache the end of the 'cpt' element, we have all the information for one node
        if (qName.equalsIgnoreCase("cpt")) {
            //Create a node based on the currently saved information
            System.out.println("----------------------");
            System.out.println(nodeName + " node is being created...");
            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            int randomX = rand.nextInt(max - min + 1) + min;
            int randomY = rand.nextInt(max - min + 1) + min;

            //Create new node with parsed information (label, x, y)
            BayesNode newNode = new BayesNode(nodeName, randomX, randomY);
            LinkXY link = null;

            //Convert probabilities to Matrix
            System.out.println("Node probabilities: " + probabilities.toString().trim());
            String parsedProb[] = probabilities.toString().trim().split(" ");
            double numProb[] = new double[parsedProb.length];
            //System.out.println("Parsed probs:");
            for(int i = 0; i< parsedProb.length; i++){
                //System.out.println(Double.valueOf(parsedProb[i]));
                numProb[i] = Double.valueOf(parsedProb[i]);
            }

            Matrix matrix = new Matrix(numProb, rowCount);
            newNode.setMatrix(matrix);            

            //System.out.println("Row Names (total of " + rowCount + "):");
//            Iterator iterator = rowNames.iterator();
//            while(iterator.hasNext())
//			System.out.println(iterator.next().toString());

            newNode.setRowNames((ArrayList) rowNames.clone());

            //Gets parents of this new node
            if(parents.length() != 0){
                System.out.println("Parents = " + parents.toString().trim());
                String parsedParents[] = parents.toString().trim().split(" ");
                 for(int i = 0; i< parsedParents.length; i++){
                    if(!parsedParents[i].trim().equals("")){
                        //System.out.println(parsedParents[i]);
                        //Since parents should always proceed children in teh XDSL file, the below line should always return a node
                        BayesNode parentNode = (BayesNode) searchSpace.findNode(parsedParents[i]);
                        newNode.addConnToParent(parentNode); //Connect new node to parent
                        parentNode.addConnToChild(newNode);  //Connect parent to new node

                        //Create link for drawing the graph
                        link = new LinkXY(parentNode, newNode);
                    }
                 }
            }else{
                System.out.println("No parents for " + newNode.label);
            }
            
            //Add new node to search space list
            searchSpace.add(newNode);

            if(link != null){
                searchSpace.add(link);
            }

            
            //Reset row count
            rowCount = 0;
            rowNames.clear();
            //Reset parents
            parents.setLength(0);

        }
        
        //System.out.println("endElement: " + uri + ", " + localName + ", " + qName);

    }

    /**
     * Parses a Genie Network from an inputstream pointing to an XDSL file
     * @param inputStream
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws IOException
     */
    private void parseNetworkFromXDSL(InputStream inputStream) throws SAXException, ParserConfigurationException, IOException {

        //get a SAX parser factory
        SAXParserFactory spf = SAXParserFactory.newInstance();

        //get a new instance of parser
        SAXParser sp = spf.newSAXParser();

        //Parses the file (this method blocks until parsing is complete)
        sp.parse(inputStream, this);

        System.out.println("Parsed Genie file and completed Network setup.");

        Iterator i = searchSpace.getNodeIterator();

        while(i.hasNext()){
            BayesNode temp = (BayesNode)i.next();
            System.out.println("Node " + temp.label);
            Matrix matrix = temp.getMatrix();
            matrix.print(3, 6);

        }
        
    }
}
