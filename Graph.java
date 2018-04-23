import java.awt.*;
import java.util.*;
import javax.swing.*;
/**
 * Graph class with nested Node class, NodeData class, and Edge class
 * Extends JComponent and displays Graph in a graphic window
 *
 * @author Chenwei(Georgina) Xu
 * @version CSC212, May 4, 2017 (May the force be with you)
 */
public class Graph<V,E> extends JComponent {
    /** master node list */
    public ArrayList<Node> node_lst;

    /** master edge list */
    public ArrayList<Edge> edge_lst;

    public int seniors;

    public int juniors;

    public int sophomores;

    public int first_years;

    /** constructor for graph class */
    public Graph(int S, int J, int P, int F)
    {
        node_lst = new ArrayList<Node>();
        edge_lst = new ArrayList<Edge>();
        seniors = S;
        juniors = J;
        sophomores = P;
        first_years = F;

        setMinimumSize(new Dimension(800,800));
	      setPreferredSize(new Dimension(800,800));
    }

    /** add a node in the graph */
    public Node addNode(V string, Point point)
    {
        NodeData node_data = new NodeData(string,point);
        Node new_node = new Node(node_data);
        node_lst.add(new_node);

        return new_node;
    }

    /** add an edge in the graph */
    public Edge addEdge(E data, Node head, Node tail)
    {
        Edge new_edge = new Edge(data, head, tail);
        boolean exist = false;
        for(Edge e : edge_lst)
        {
            if(e.equals(new_edge))
            {
                exist = true;
            }
        }
        if(!exist)
        {
            edge_lst.add(new_edge);
            head.addEdgeRef(new_edge);
            tail.addEdgeRef(new_edge);
        }
        return new_edge;
    }

    /** remove a node in the graph */
    public void removeNode(Node node)
    {
        ArrayList<Edge> nl = node.edge;
        while(nl.size()!=0)
        {
            this.removeEdge((Edge)nl.get(0));
        }
        node_lst.remove(node);
    }

    /** remove an edge with edge name in the graph */
    public void removeEdge(Edge e)
    {
        edge_lst.remove(e);
        Node head = e.getHead();
        head.removeEdgeRef(e);

        Node tail = e.getTail();
        tail.removeEdgeRef(e);
    }

    /** remove an edge with head and tail in the graph */
    public void removeEdge(Node head, Node tail)
    {
        Edge edge_remove = null;
        Edge new_edge = new Edge(null, head, tail);
        for(Edge e : edge_lst)
        {
            if(e.equals(new_edge))
            {
                edge_remove = e;
            }
        }
        if(edge_remove != null)
        {
            removeEdge(edge_remove);
        }
    }

    /** get an edge with index */
    public Edge getEdge(int i)
    {
        Edge edge = null;
        try
        {
            edge = edge_lst.get(i);
        } catch(IndexOutOfBoundsException e)
        {
            System.out.println("Not valid index.");
        }
        return edge;
    }

    /** get an edge with head & tail */
    public Edge getEdgeRef(Node head, Node tail)
    {
        Edge edge = null;
        Edge new_edge = new Edge(null, head, tail);

        for(Edge e : edge_lst)
        {
            if(e.equals(new_edge))
            {
                edge = e;
            }
        }
        return edge;
    }

    /** get a node */
    public Node getNode(int i)
    {
        Node node = null;
        try
        {
            node = node_lst.get(i);
        } catch(IndexOutOfBoundsException e)
        {
            System.out.println("Not valid index.");
        }
        return node;
    }

    /** return No.of edges in the graph */
    public int numEdges()
    {
        return edge_lst.size();
    }

    /** return No.of nodes in the graph */
    public int numNodes()
    {
        return node_lst.size();
    }

    /** return other nodes in the graph except the ones in the group */
    public HashSet<Node> otherNodes(HashSet<Node> group)
    {
        HashSet<Node> other_node = new HashSet<Node>();
        for(Node n : node_lst)
        {
            if(!group.contains(n))
            {
                other_node.add(n);
            }
        }
        return other_node;
    }

    /** print out all the nodes & edges in the graph */
    public void print()
    {
       System.out.println("All the nodes: ");
       for (Node n : node_lst)
       {
           n.print();
           System.out.println();
       }

       System.out.println("All the edges: ");
       for (Edge e : edge_lst)
       {
           e.print();
       }
    }

    /** Depth-first search */
    private void DFS(Node node,HashSet<Edge> edges)
    {
        //System.out.println(node.getData().getLabel());
        node.setVisited("true");
        for(Edge e : node.edge)
        {
            Node new_node = e.oppositeTo(node);
            if(new_node.visited == false)
            {
                edges.add(e);
                e.setVisited("true");
                DFS(new_node, edges);
            }
        }
    }

    /** depth-first traversal */
    public HashSet<Edge> depthFirstTraversal(Node start)
    {
        HashSet<Edge> edges = new HashSet<Edge>();
        for(Node n : node_lst)
        {
            n.setVisited("false");
        }

        for(Edge e : edge_lst)
        {
            e.setVisited("false");
        }

        DFS(start,edges);

        return edges;
    }

    /** breadth-first traversal */
    public HashSet<Edge> breadthFirstTraversal(Node start)
    {
        HashSet<Edge> edges = new HashSet<Edge>();
        Queue<Node> node_queue = new LinkedList<Node>();
        for(Node n : node_lst)
        {
            n.setVisited("false");
        }

        for(Edge e : edge_lst)
        {
            e.setVisited("false");
        }

        node_queue.add(start);
        start.setVisited("true");

        while(!node_queue.isEmpty())
        {
            Node v = node_queue.remove();
            //System.out.println(v.getData().getLabel());
            ArrayList<Node> neighbors = v.getNeighbors();

            for(Node n : neighbors)
            {
                if(n.visited == false)
                {
                    n.setVisited("true");
                    node_queue.add(n);

                    Edge e = v.edgeTo(n);
                    e.setVisited("true");
                    edges.add(e);
                }
            }
        }
        repaint();
        return edges;
    }

    /** check the consistency of the graph */
    public void check()
    {
        boolean check1 = true;
        boolean check2 = true;
        boolean check3 = true;
        boolean check4 = true;

        for(Edge e : edge_lst)
        {
            if((e.getHead().edge.contains(e) == false) ||
                    (e.getTail().edge.contains(e) == false))
            {
                System.out.println("Edge " + e.getData() +
                        " is not registered in its head/tail node's edge list.");
                check1 = false;
            }

            if((node_lst.contains(e.getHead()) == false) ||
                    (node_lst.contains(e.getTail()) == false))
            {
                System.out.println("Edge " + e.getData() +
                        "'s head/tail node is not registered in master node list.");
                check2 = false;
            }
        }

        for(Node n : node_lst)
        {
            ArrayList<Edge> el = n.edge;
            for(Edge edge : el)
            {
                if(!((edge.getHead() == n) || (edge.getTail() == n)))
                {
                    System.out.println("Node " + n.getData().getLabel() +
                            " is not registered as a head/tail node in its edge " +
                            edge.getData());
                    check3 = false;
                }
                if(!edge_lst.contains(edge))
                {
                    System.out.println("Edge " + edge.getData() +
                        " of Node " + n.getData().getLabel() +
                            " is not registered in master edge list.");
                    check4 = false;
                }
            }
        }

        boolean consistency = (check1 && check2 && check3 && check4);
        if (consistency == true)
        {
            System.out.println("Graph consistent.");
        }
    }

    /** return all the endpoints of a group of edge */
    public HashSet<Node> endpoints(HashSet<Edge> edges)
    {
        HashSet<Node> end_points = new HashSet<Node>();
        for(Edge e : edges)
        {
            end_points.add(e.getHead());
            end_points.add(e.getTail());
        }
        return end_points;
    }

    /** find the node with the smallest value */
    private Node findSmallest(HashMap<Node,Double> distance, HashSet<Node> unvisited)
    {
        Node min = (Node)unvisited.toArray()[0];
        Double min_dis = distance.get(min);

        for(Node n : unvisited)
        {
            if(distance.get(n) < min_dis)
            {
                min = n;
                min_dis = distance.get(n);
            }
        }
        return min;
    }

    /** shortest paths to all destinations */
    public HashMap<Node,Double> distances(Node start)
    {
        /* a hashset of all visited nodes */
        HashSet<Node> visited = new HashSet<Node>();

        for(Node n : node_lst)
        {
            if(n == start)
            {
                start.dis_to_all_nodes.put(n, 0.0);
            }
            else
            {
                start.dis_to_all_nodes.put(n, Double.POSITIVE_INFINITY);
            }

            start.edges_to_all_nodes.put(n, new ArrayList<Edge>());
        }

        while(visited.size() != node_lst.size())
        {
            /* find all unvisited nodes */
            HashSet<Node> unvisited = this.otherNodes(visited);

            /* find the node that has the smallest value in all unvisited nodes */
            Node min = findSmallest(start.dis_to_all_nodes,unvisited);
            visited.add(min);

            for(Node n : min.getNeighbors())
            {
                if(!visited.contains(n))
                {
                    Edge edge_to = min.edgeTo(n);
                    double new_dis = start.dis_to_all_nodes.get(min) + (double)((Integer)edge_to.getData());
                    if(new_dis < start.dis_to_all_nodes.get(n))
                    {
                        /* update the new distance */
                        start.dis_to_all_nodes.put(n, new_dis);

                        /* update the shortest edge route from Node start to Node n */
                        start.edges_to_all_nodes.get(n).clear();
                        start.edges_to_all_nodes.get(n).addAll(start.edges_to_all_nodes.get(min));
                        start.edges_to_all_nodes.get(n).add(edge_to);
                    }
                }
            }
        }
        return start.dis_to_all_nodes;
    }

    /**
     *  Displays the graph in the graphics window
     *  @param g The graphics object to draw into
     */
    public void paintComponent(Graphics g)
    {
        for(Graph.Edge e : this.edge_lst)
        {
            if(e.visited == false)
            {
                g.setColor(Color.black);
            }
            else
            {
                g.setColor(Color.ORANGE);
            }
            Point p1 = (Point)e.getHead().getData().getCoor();
            Point p2 = (Point)e.getTail().getData().getCoor();
            g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());

            int mid_px = (int)(p1.getX() + p2.getX())/2;
            int mid_py = (int)(p1.getY() + p2.getY())/2;
            g.drawString(e.getData().toString(), mid_px, mid_py);
        }

        for(int i = 0; i < this.node_lst.size(); i++)
        {
            Node n = this.node_lst.get(i);
            String label = (String)n.getData().getLabel();
            Point center = (Point)n.getData().getCoor();

            if((n.visited == false) && (i >= 0) && (i < this.seniors))
            {
                g.setColor(Color.red);
                int x = (int)center.getX();
                int y = (int)center.getY();
                g.fillOval(x, y, 5, 5);
            }
            else if ((n.visited == false) && (i >= this.seniors) && (i < this.seniors+this.juniors))
            {
                g.setColor(Color.green);
                int x = (int)center.getX();
                int y = (int)center.getY();
                g.fillOval(x, y, 5, 5);
            }
            else if ((n.visited == false) && (i > (this.seniors+this.juniors)) && (i < this.seniors+this.juniors+this.sophomores))
            {
                g.setColor(Color.yellow);
                int x = (int)center.getX();
                int y = (int)center.getY();
                g.fillOval(x, y, 5, 5);
            }
            else if (n.visited == false && (i >= this.seniors+this.juniors+this.sophomores) && (i < this.seniors+this.juniors+this.sophomores+this.first_years))
            {
                g.setColor(Color.blue);
                int x = (int)center.getX();
                int y = (int)center.getY();
                g.fillOval(x, y, 5, 5);
            }
            else if(n.visited != false)
            {
                g.setColor(Color.pink);
                int x = (int)center.getX();
                int y = (int)center.getY();
                g.fillOval(x, y, 5, 5);
            }

            g.setColor(Color.black);
            String s = "("+center.getX()+","+center.getY()+")";
            g.drawString(label+"  "+s, (int)center.getX()-5, (int)center.getY());
        }
    }


    /** nested class Node */
    public class Node
    {
        /** value stored in node */
        private NodeData data;

        /** all the edges it points to */
        public ArrayList<Edge> edge;

        /** represents if this node is visited */
        public boolean visited;

        /** stores distances to all other nodes from this node */
        public HashMap<Node,Double> dis_to_all_nodes = new HashMap<Node,Double>();

        /** stores an arrayList of paths to all other nodes from this node */
        public HashMap<Node,ArrayList<Edge>> edges_to_all_nodes = new HashMap<Node,ArrayList<Edge>>();

        /** constructor for empty node */
        public Node()
        {
            this.data = null;
            this.edge = new ArrayList<Edge>();
            visited = false;
        }

        /** constructor for node with data */
        public Node(NodeData data)
        {
            this.data = data;
            this.edge = new ArrayList<Edge>();
            visited = false;
        }

        /** manipulator for field visited */
        public void setVisited(String s)
        {
            if(s.equals("true"))
            {
                this.visited = true;
            }
            else if(s.equals("false"))
            {
                this.visited = false;
            }
        }

        /** accessor for data */
        public NodeData getData()
        {
            return data;
        }

        /** manipulator for data */
        public void setData(V s, Point p)
        {
            NodeData nd = new NodeData(s,p);
            this.data = nd;
        }

        /** add an edge */
        protected void addEdgeRef(Edge e)
        {
            if(!edge.contains(e))
            {
                edge.add(e);
            }
        }

        /** remove an edge */
        protected void removeEdgeRef(Edge e)
        {
            if(edge.contains(e))
            {
                edge.remove(e);
            }
        }

        /** return all the neighbors */
        public ArrayList<Node> getNeighbors()
        {
            ArrayList<Node> nl = new ArrayList<Node>();
            for(Edge e : this.edge)
            {
                nl.add(e.oppositeTo(this));
            }
            return nl;
        }

        /** check if node is a neighbor */
        public boolean isNeighbor(Node node)
        {
            ArrayList<Node> neighbors = this.getNeighbors();
            if(neighbors.contains(node))
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        /** return the edge to a specific node */
        public Edge edgeTo(Node n)
        {
            Edge ed = null;
            for(Edge e : edge)
            {
                if(e.oppositeTo(this) == n)
                {
                    ed = e;
                }
            }
            return ed;
        }

        /** print out data and all the neighbors of a node (for debugging) */
        public void print()
        {
            System.out.println("Data: " + this.getData());
            System.out.println("Neighbors: ");
            for(Node n : this.getNeighbors())
            {
                System.out.print(n.getData() + "  ");
            }
            System.out.println();
        }
    }


    /** nested NodeData class, which is the data type for Node */
    public class NodeData
    {
        /** label data */
        protected V label;

        /** point data */
        protected Point coordinate;

        /** constructor for NodeData */
        public NodeData(V label, Point coord)
        {
            this.label = label;
            coordinate = coord;
        }

        /** Accessor for label */
        public V getLabel()
        {
            return label;
        }

        /** Accessor for coordinate */
        public Point getCoor()
        {
            return coordinate;
        }

        /** manipulator for label */
        public void setLabel(V label)
        {
            this.label = label;
        }

        /** manipulator for coordinate */
        public void setCoor(Point point)
        {
            coordinate = point;
        }
    }



    /** nested class Edge */
    public class Edge
    {
        /** value stored in edge */
        private E data;

        /** the head node */
        private Node head;

        /** the tail node */
        private Node tail;

        /** boolean field representing if the edge is visited */
        public boolean visited;

        /** constructor for edge */
        public Edge(E data, Node head, Node tail)
        {
            this.data = data;
            this.head = head;
            this.tail = tail;
            visited = false;
        }

        /** manipulator for visited */
        public void setVisited(String s)
        {
            if(s.equals("true"))
            {
                this.visited = true;
            }
            else if(s.equals("false"))
            {
                this.visited = false;
            }
        }

        /** accessor for data */
        public E getData()
        {return data;}

        /** accessor for head */
        public Node getHead()
        {return head;}

        /** accessor for tail */
        public Node getTail()
        {return tail;}

        /** manipulator for data */
        protected void setData(E data)
        {this.data = data;}

        /** overriding hashCode */
        public int hashCode()
        {
            int hash = head.hashCode() + tail.hashCode();
            return hash;
        }

        /** check if two edges are equal */
        public boolean equals(Object o)
        {
            boolean result = false;
            if(getClass() == o.getClass())
            {
                Edge e = (Edge)o;
                if((this.head == e.head) && (this.tail == e.tail))
                {
                    result = true;
                }
                else if((this.tail == e.head) && (this.head == e.tail))
                {
                    result = true;
                }
            }
            return result;
        }

        /** return the opposite end of an edge */
        public Node oppositeTo(Node node)
        {
            Node n = null;
            if(this.getHead() == node)
            {
                n = this.getTail();
            }
            else if(this.getTail() == node)
            {
                n = this.getHead();
            }
            return n;
        }

        /** print out the data, head & tail of an edge */
        public void print()
        {
            System.out.println("Data: " + this.getData() + " Head: "+ this.getHead().getData() +
                    " Tail: " + this.getTail().getData());
        }
    }
}
