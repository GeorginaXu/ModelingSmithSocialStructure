
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.*;

/**
 * Class that runs a GUI which displays a graph and interact with user
 *
 * @author Chenwei (Georgina) Xu
 * @version May 4, 2017
 */
public class GraphVisual {
        /** map to be displayed */
				private Graph graph;

				private int[][] matrix = null;

        /** current mode for GraphGUI */
        private InputMode mode = InputMode.DEFAULT_MODE;

        /** instruction that displays at the top of the window */
        private JLabel instruction;

        /** node that user clicks */
        private Graph.Node nodeUnderMouse;

        /** starting node for a traversal, which is clicked and selected by the user */
        private Graph.Node traversal_start;

        /** starting node for a path-finding */
        private Graph.Node path_start;

        /** ending node for a path-finding */
        private Graph.Node path_end;

        /** name of node that is displayed on the Node */
        private int num_student = 1;

				public ArrayList<Graph.Node> node_cut = new ArrayList<Graph.Node>();


  /**
	 *  Creates a GraphGUI of a specified Graph
   *
	 *  @param graph Specifies the MapGrid to be displayed
	 */
		public GraphVisual(String filename, int S, int J, int P, int F) {

	      graph = new Graph<String,Integer>(S,J,P,F);
				try{
					matrix = new ReadMatrix(filename).getMatrix();
				} catch(IOException ie) {
					System.out.println("invalid filename");
				}

				int magnitude = 620/((matrix.length/4)+1);
				for (int node = 0; node < matrix.length; node++) {
					int randx = 0;
					int randy = 0;
					if(node>=0 && node <matrix.length/4) {
						randx = 20;
						randy = 20 + node * magnitude;
					}
					else if (node >= matrix.length/4 && node < matrix.length/2) {
						randx = 20 + (node-matrix.length/4) * magnitude;
						randy = 20 + (matrix.length/4) * magnitude;
					}
					else if (node >= matrix.length/2 && node < matrix.length*3/4) {
						randx = 20 + (node-matrix.length/2 + 1) * magnitude;
						randy = 20;
					}
					else if (node >= matrix.length*3/4 && node < matrix.length) {
						randx = 20 + (matrix.length/4 + 1) * magnitude;
						randy = 20 + (node-matrix.length*3/4) * magnitude;
					}
					Graph.Node n = graph.addNode(Integer.toString(num_student++), new Point(randx,randy));
				}

				for (int i = 0; i < matrix.length; i++) {
					for (int j = 0; j < matrix[0].length; j++) {
						if(i>j && matrix[i][j]==1) {
							Graph.Node node_i = graph.getNode(i);
							Graph.Node node_j = graph.getNode(j);
							Graph.Edge e = graph.addEdge(1, node_i, node_j);
						}
					}
				}
		}

   /** Sets up the GUI window */
    public void createAndShowGUI() {
        // Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Create and set up the window.
        JFrame frame = new JFrame("House Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add components
        createComponents(frame);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

        /**
	 *  set up the GUI contents.
	 *
	 *  @param pane  The pane of the JFrame of JApplet
	 */
	public void createComponents(Container pane) {

      JPanel panel1 = new JPanel();
      panel1.setLayout(new FlowLayout());
      instruction = new JLabel("Instruction: Don't forget to repaint to initial state after each traversal");
      panel1.add(instruction);

			JPanel panel2 = new JPanel();
			panel2.setLayout(new BorderLayout());

			JButton addNode = new JButton("Add Node");
      addNode.addActionListener(new AddListener("node"));
			panel2.add(addNode, BorderLayout.NORTH);

			JButton removeNode = new JButton("Remove Node");
      removeNode.addActionListener(new RemoveListener("node"));
			panel2.add(removeNode, BorderLayout.SOUTH);

      JButton addEdge = new JButton("Add Edge");
      addEdge.addActionListener(new AddListener("edge"));
      panel2.add(addEdge, BorderLayout.WEST);

      JButton removeEdge = new JButton("Remove Edge");
      removeEdge.addActionListener(new RemoveListener("edge"));
      panel2.add(removeEdge, BorderLayout.EAST);

      JPanel panel3 = new JPanel();
      panel3.setLayout(new GridLayout(5,1));

      JButton traversalButton = new JButton("Traversal Mode");
      traversalButton.addActionListener(new TraversalListener(""));
      panel3.add(traversalButton);

      JButton bftButton = new JButton("breadth-first traversal");
      bftButton.addActionListener(new TraversalListener("bft"));
      panel3.add(bftButton);

      JButton dftButton = new JButton("depth-first traversal");
      dftButton.addActionListener(new TraversalListener("dft"));
      panel3.add(dftButton);

      JButton path = new JButton("Path Mode");
      path.addActionListener(new PathListener());
      panel3.add(path);

      JButton back = new JButton("Repaint to initial state");
      back.addActionListener(new TraversalListener("back"));
      panel3.add(back);

      JPanel panel4 = new JPanel();
      panel4.setLayout(new BorderLayout());
      panel4.add(panel2, BorderLayout.NORTH);
      panel4.add(panel3, BorderLayout.SOUTH);

      JPanel panel5 = new JPanel();
      panel5.setLayout(new FlowLayout());
      panel5.add(graph);
      GraphMouseListener gml = new GraphMouseListener();
      graph.addMouseListener(gml);
      graph.addMouseMotionListener(gml);

			pane.setLayout(new BorderLayout());
			pane.add(panel1,BorderLayout.NORTH);
      pane.add(panel4,BorderLayout.EAST);
      pane.add(panel5,BorderLayout.WEST);
	}

  /**
	 *  main method to run GraphGUI
	 */
	public static void main(String[] args)
	{
		int seniors = Integer.parseInt(args[1]);
		int juniors = Integer.parseInt(args[2]);
		int sophomores = Integer.parseInt(args[3]);
		int first_years = Integer.parseInt(args[4]);
		final GraphVisual GUI = new GraphVisual(args[0], seniors, juniors,sophomores,first_years);
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    GUI.createAndShowGUI();
                }
            });
	}

        /** enum types for recording the input mode */
        enum InputMode {
            DEFAULT_MODE, ADD_NODE_MODE, REMOVE_NODE_MODE,
            ADD_EDGE_MODE, REMOVE_EDGE_MODE, TRAVERSAL, PATH
        }

        /**
        * Returns a node found within the drawing radius of the given location,
        * or null if none
        *
         *  @param x  the x coordinate of the location
         *  @param y  the y coordinate of the location
         *  @return  a node if there is one around this location,
         *  or a null reference if not
         */
        public Graph.Node findNearbyNode(int x, int y) {
            Graph.Node nearNode = null;
            ArrayList<Graph.Node> nl = graph.node_lst;
            for(Graph.Node n : nl)
            {
                Point center = (Point)n.getData().getCoor();
                if(center.distance(x, y) <= 15)
                {
                    nearNode = n;
                }
            }
            return nearNode;
        }

        /** Event handler for Path Mode button */
        private class PathListener implements ActionListener {
            public void actionPerformed(ActionEvent e)
            {
                mode = InputMode.PATH;
                path_start = null;
                path_end = null;
                instruction.setText("Drag between two nodes to find out the shortest path.");
            }
        }

        /** Event handler for traversal buttons */
        private class TraversalListener implements ActionListener {
            String traversal_type;

            public TraversalListener(String s)
            {
                traversal_type = s;
            }

            public void actionPerformed(ActionEvent e)
            {
                mode = InputMode.TRAVERSAL;
                if(traversal_type.equals("bft"))
                {
                    if(traversal_start == null)
                    {
                        System.out.println("Please select a node first.");
                    }
                    else
                    {
                        graph.breadthFirstTraversal(traversal_start);
                        traversal_start = null;
										// 		node_cut = graph.nodeCut();
										// 		if(node_cut == null) {
										// 			System.out.println("This graph doesn't have a node cut of size 3");
										// 		}
										// 		else {
										// 			System.out.println("the node cuts are: ");
										// 			node_cut.get(0).print();
										// 			node_cut.get(1).print();
										// 			node_cut.get(2).print();
										// 		}
                     }
                }
                else if (traversal_type.equals("dft"))
                {
                    if(traversal_start == null)
                    {
                        System.out.println("Please select a node first.");
                    }
                    else
                    {
                        graph.depthFirstTraversal(traversal_start);
                        traversal_start = null;
                    }
                }
                else if (traversal_type.equals("back"))
                {
                    ArrayList<Graph.Node> nl = graph.node_lst;
                    traversal_start = null;
                    path_start = null;
                    path_end = null;

                    for(Graph.Node n : nl)
                    {
                        n.setVisited("false");
                    }

                    ArrayList<Graph.Edge> el = graph.edge_lst;
                    for(Graph.Edge ed : el)
                    {
                        ed.setVisited("false");
                    }
                }
                else
                {
                    instruction.setText("Click a node and select a way of traversal. "
                            + "Don't forget to repaint back to initail state after each time.");
                }
                graph.repaint();
            }
        }

        /** Listener & Event handler for Add buttons */
        private class AddListener implements ActionListener {
            private String element;

            public AddListener(String s)
            {
                element = s;
            }

            /** Event handler for AddPoint button */
            public void actionPerformed(ActionEvent e) {
                if(element.equals("node"))
                {
                    mode = InputMode.ADD_NODE_MODE;
                    instruction.setText("Click to add node. Drag to move nodes.");
                }
                else if(element.equals("edge"))
                {
                    mode = InputMode.ADD_EDGE_MODE;
                    instruction.setText("Drag between two nodes to add edge.");
                }
            }
        }

        /** Listener & event handler for Remove buttons */
        private class RemoveListener implements ActionListener {
            private String element;

            public RemoveListener(String s)
            {
                element = s;
            }

            /** Event handler for RmvPoint button */
            public void actionPerformed(ActionEvent e) {
                if(element.equals("node"))
                {
                    mode = InputMode.REMOVE_NODE_MODE;
                    instruction.setText("Click to remove node.");
                }
                else if(element.equals("edge"))
                {
                    mode = InputMode.REMOVE_EDGE_MODE;
                    instruction.setText("Drag between two nodes to remove edge.");
                }
            }
        }

        /** Mouse listener for several modes */
        private class GraphMouseListener extends MouseAdapter implements MouseMotionListener {

            /** Responds to click event depending on mode */
            public void mouseClicked(MouseEvent e) {
                switch (mode) {
                case ADD_NODE_MODE:
                    if(findNearbyNode(e.getX(),e.getY()) == null)
                    {
                        graph.addNode(Integer.toString(num_student++),new Point(e.getPoint()));
                    }
                    else
                    {
                        Toolkit.getDefaultToolkit().beep();
                    }
                    break;

                case REMOVE_NODE_MODE:
                    Graph.Node n = findNearbyNode(e.getX(),e.getY());

                    if(n != null)
                    {
                        graph.removeNode(n);
                    }
                    else
                    {
                        Toolkit.getDefaultToolkit().beep();
                    }
                    break;

                case TRAVERSAL:
                    Graph.Node start = findNearbyNode(e.getX(),e.getY());

                    if(start != null)
                    {
                        traversal_start = start;
                    }
                    else
                    {
                        Toolkit.getDefaultToolkit().beep();
                    }
                    break;
                }
                graph.repaint();
            }

            /** Records point under mousedown event in anticipation of possible drag */
            public void mousePressed(MouseEvent e) {
                if(e.getPoint() != null)
                {
                    if(mode == InputMode.PATH)
                    {
                        path_start = findNearbyNode(e.getX(),e.getY());
                    }
                    else
                    {
                        nodeUnderMouse = findNearbyNode(e.getX(),e.getY());
                    }
                }
            }

            /** Responds to mouseup event */
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();
                if(p != null)
                {
                    if(mode == InputMode.ADD_EDGE_MODE)
                    {
                        if(nodeUnderMouse != null)
                        {
                            Graph.Node tail = findNearbyNode((int)e.getPoint().getX(), (int)e.getPoint().getY());
                            if((tail != null) && (tail != nodeUnderMouse))
                            {
                                graph.addEdge(1, tail, nodeUnderMouse);
                            }
                            nodeUnderMouse = null;
                        }
                    }
                    else if(mode == InputMode.REMOVE_EDGE_MODE)
                    {
                        if(nodeUnderMouse != null)
                        {
                            Graph.Node tail = findNearbyNode((int)e.getPoint().getX(), (int)e.getPoint().getY());
                            if((tail != null) && (tail != nodeUnderMouse))
                            {
                                graph.removeEdge(nodeUnderMouse, tail);
                            }
                        }
                    }
                    else if(mode == InputMode.PATH)
                    {
                        if((path_start != null))
                        {
                            Graph.Node path_end = findNearbyNode((int)e.getPoint().getX(), (int)e.getPoint().getY());
                            if((path_end != null) && (path_end != path_start))
                            {
                                graph.distances(path_start);
                                ArrayList<Graph.Edge> edges_to = (ArrayList<Graph.Edge>)path_start.edges_to_all_nodes.get(path_end);
                                for(Graph.Edge edge_to : edges_to)
                                {
                                    edge_to.setVisited("true");
                                }
                                System.out.println("Shortest path from " + path_start.getData().getLabel() +
                                    " to " + path_end.getData().getLabel() + " has a cost of " +
                                    path_start.dis_to_all_nodes.get(path_end));
                            }
                        }
                    }
                }
                graph.repaint();
            }

            /** Responds to mouse drag event */
            public void mouseDragged(MouseEvent e) {
                switch(mode) {
                    case ADD_NODE_MODE:
                        if(nodeUnderMouse != null)
                        {
                            /* update the coordinates of node when dragged */
                            nodeUnderMouse.setData(nodeUnderMouse.getData().getLabel(), e.getPoint());

                            /* update the length of all its edge when node is dragged */
                            ArrayList<Graph.Edge> el = nodeUnderMouse.edge;
                            for(Graph.Edge edge : el)
                            {
                                edge.setData(1);
                            }
                        }
                        else
                        {
                            Toolkit.getDefaultToolkit().beep();
                        }
                        break;
                }
                graph.repaint();
            }

            // Empty but necessary to comply with MouseMotionListener interface.
            public void mouseMoved(MouseEvent e) {}
        }

}
