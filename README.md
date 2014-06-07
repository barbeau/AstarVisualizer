A* algorithm implementation
===============================

Created by Sean J. Barbeau, 2005

This project is an implementation and visualization of network algorithms such as the A* (i.e., A star) search algorithm.

Details are avaialble on the project website:
https://github.com/barbeau/AstarVisualizer/wiki

Currently, only A* search is fully implemented.  A Bayesian network is partially implemented.

This application was created in the NetBeans IDE 4.1 w/ the JDK 1.5.0_04 and is editable in that environment
by opening this root directory in NetBeans.

The executable is `\dist\NetworkAlgorithms.jar`, and is launched in Windows by double-clicking on it from a normal window.  To be safe you may want to copy the entire root and subdirectories to your hard drive and then execute it.

The source code is in `\src\` sub folders.

Both input files should be located in the same directory as the .jar file to load from their default location, although the file browser buttons
can be used to specify files in other locations.

### Program execution

Setup - To execute Astar algorithm, choose the Astar configuration in Netbeans near compile buttons at top of screen.

1.  Start application
2.  Click on "Load files"
3.  Select Start and Goal nodes and select heuristic
4.  User can disable nodes so they won't be used in the search path by selecting the 'Enable/Disable Node' radio button option and clicking on the map. 
5.  User can move nodes by selecting the 'Move Node' radio button option and clicking and dragging a node around the map.
6.  User can select to either progress step-by-step through the algorithm or immediately finish the algorithm by toggling the "Step-by-Step" button.
7.  User can select the length of the step time from .1 to 10 seconds by moving the "Step time" slider.
8.  Click "Start" to begin algorithm execution.  6) and 7) can also be performed during execution of the algorithm.
9.  After algorithm finishes (or during execution if desired) click "Reset" to reset the algorithm to its initial state.  You can either run the algorithm again or load a new set of files.

The output from the algorithm (path from start to goal node) should be shown in the "A* Log" text box within the application window as well as the graphical display.
Start and goal nodes are shown in blue, current node being processed is in green, and any disabled nodes are red.

Links are shown as light blue if they are traveled, and as dark blue if they are part of the path after the algorithm finds a path.

To add more heuristics, add a new enumeration value to the `Heuristic` enumeration, and modify 
the `getCost(Node nodeB, Heuristic heuristic)` and `getEstimatedCostToGoal(Node goal_node, Heuristic heuristic)` functions 
in the class `HeuristicsNode` to add a new heuristic calculation as part of the `switch()` statement.  Then pass the 
`Heuristic` enumeration value used for that `switch()` statement into the `Heuristic` argument of the AstarSearch object when it is created. 

### References used for A* algorithm

1. Russel, Norvig.  "Artificial Intelligence:  A Modern Approach".  Prentice Hall 2003.
2. Lugar.  "Artificial Intelligence:  Structures and Strategies for Complex Problem Solving".  Addison Wesley, 2005.
3. Winston.  "Artificial Intelligence". Addison-Wesley Publishing Company, 1992.
4. DevX.com Bulletin, "Breadth first search with java", Available online at http://news.devx.com/printthread.php?t=145297.
5. Brackeen.  "Game Character Path Finding in Java", informit.com, available online at http://www.informit.com/articles/article.asp?p=101142&seqNum=2&rl=1.
6. Heyes-Jones, "A* algorithm Tutorial", available online at http://www.geocities.com/jheyesjones/astar.html.
7. Adair, Ball, and Pawlan.  "The Java Turtorial, Trail: 2D Graphics", available online at http://java.sun.com/docs/books/tutorial/2d/index.html.
8. Adair, Ball, and Pawlan.  "Stroking and Filling Graphics Primitives", available online at http://java.sun.com/docs/books/tutorial/2d/display/strokeandfill.html.
9. Sun Microsystems.  "How to Use File Choosers", available online at http://java.sun.com/docs/books/tutorial/uiswing/components/filechooser.html.
10. Sun Microsystems.  "Maintaining a Priority Queue and Displaying Text in Multiple Styles", available online at http://java.sun.com/developer/JDCTechTips/2002/tt0821.html.
11. Sun Microsystems.  "Java Forums - Drawing arrows", available online at http://forum.java.sun.com/thread.jspa?threadID=378460&tstart=135.
