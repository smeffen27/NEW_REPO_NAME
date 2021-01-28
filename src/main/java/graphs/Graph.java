package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

/**
 * Class for representing a simple directed graph. The graph uses an adjacency list to manage the
 * nodes and edges.
 * 
 * @author Julian
 */
public class Graph {
    //---------------------------------------------------------------------------------------------

    /**
     * Static class to representing an edge of a graph.
     * 
     * @author Julian
     *
     */
    static class Edge {
		Node destinationNode; 
		int weight;

		public Edge(final Node node,final int edgeWeight) {
			destinationNode = node;
			weight = edgeWeight;
		}

		public Node getDestNode() { return destinationNode; }
		public int getCost() { return weight; }
	}

    //---------------------------------------------------------------------------------------------

	/**
     * Static class to representing a vertex of a graph.
	 * 
	 * @author Julian
	 *
	 */
	static class Node {
        List<Edge> adjacencyList = new ArrayList<>();
		String label; 

		public Node(final String nodeLabel) {
			label = nodeLabel;
		}

		public String getLabel() { return label; }
		public Iterator<Edge> getEdges() { return adjacencyList.iterator(); }
        public String toString() { return label; }
        public void addEdge(final Edge edge) { adjacencyList.add(edge); }

		public Edge getEdgeTo(final Node node) {
			for (Edge edge : adjacencyList) {
				if (edge.destinationNode.equals(node)) { return edge; }
			}
			
			return null;
		}
	}

    //---------------------------------------------------------------------------------------------

    /**
     * For testing purposes only.
     * 
     * @param args test
     */
    public static void main(final String[] args) {
        Graph graph = new Graph();
        graph.addNode("1");
        graph.addNode("2");
        graph.addNode("3");
        graph.addNode("4");
        graph.addNode("5");
        graph.addNode("6");
        graph.addNode("7");
        graph.addNode("8");
        graph.addNode("9");
        graph.addNode("10");
        graph.addNode("11");
        graph.addNode("12");
        graph.addNode("13");

        graph.addEdge("1", "2", 1);
        graph.addEdge("1", "6", 1);
        graph.addEdge("6", "7", 1);
        graph.addEdge("7", "8", 1);
        graph.addEdge("8", "9", 1);
        graph.addEdge("2", "9", 1);
        graph.addEdge("2", "3", 1);
        graph.addEdge("3", "4", 1);
        graph.addEdge("3", "5", 1);
        graph.addEdge("9", "10", 1);
        graph.addEdge("10", "11", 1);
        graph.addEdge("11", "12", 1);
        graph.addEdge("11", "13", 1);

        System.out.println(graph.toString());

//        System.out.println(graph.checkConnection("1", "2"));
//        System.out.println(graph.checkConnection("2", "1"));
//        System.out.println(graph.checkConnection("2", "4")+ "\n");
//        
//        System.out.println(graph.getNeighbors("1").toString());
        
//        graph.bfs("1"); 
        graph.dfs("1");
    }

    //=============================================================================================

	/** Map that contains all <code>Node</code>s of the graph. */
	private Map<String, Node> nodeSet = new HashMap<>();

    //---------------------------------------------------------------------------------------------

	/**
     * Get the <code>Node</code> with the given label.
	 * 
     * @param label The label of the searched node
     * @return The searched node
     * @throws RuntimeException If the node is already defined
	 */
	public Node getNode(final String label) throws NoSuchElementException {
		Node node = nodeSet.get(label);
		if (node == null) { throw new NoSuchElementException(); }
		
		return node;
	}

    //---------------------------------------------------------------------------------------------

	/**
     * Add a new <code>Node</code> with the given label to this graph.
     * 
     * @param label The label of the new node
     * @return The newly created node
     * @throws RuntimeException If the node is already defined
     */
    public Node addNode(final String label) throws RuntimeException {
        if (nodeSet.containsKey(label)) { throw new RuntimeException("Node already defined!"); };
        
        Node node = new Node(label);
        nodeSet.put(label, node);
        
        return node;
    }

    //---------------------------------------------------------------------------------------------

	/**
     * Add an <code>Edge</code> between the <code>Node</code>s with the given labels and with the
     * given weight. Because this is a undirected graph the edge is added both from the start node
     * to the destination node and reverse.
     * 
     * @param firstNodeLabel The start <code>Node</code> of the <code>Edge</code>
     * @param secondNodeLabel The destination <code>Node</code> of the <code>Edge</code>
     * @param weight The weight of the <code>Edge</code>
     */
    public void addEdge(final String firstNodeLabel, final String secondNodeLabel,
            final int weight) {
        if (checkConnection(firstNodeLabel, secondNodeLabel)) {
            throw new RuntimeException("Edge exists!");
        }

        Node srcNode = getNode(firstNodeLabel);
		Node destNode = getNode(secondNodeLabel);
		
        srcNode.addEdge(new Edge(destNode, weight));  
        destNode.addEdge(new Edge(srcNode, weight));  
	}
	
    //---------------------------------------------------------------------------------------------

    /**
     * Checks whether there is a connection between the nodes for the given labels. Time complexity
     * should be O(|V|).
     * 
     * @param firstNodeLabel The first node
     * @param secondNodeLabel The second node
     * @return Whether there is a connection between the nodes for the given labels
     */
    public boolean checkConnection(final String firstNodeLabel, final String secondNodeLabel) {
        Node firstNode = getNode(firstNodeLabel);
        Node secondNode = getNode(secondNodeLabel);
        
        boolean isConnected = (firstNode.getEdgeTo(secondNode) == null) ? false : true;
        
        return isConnected;
    }
    
    //---------------------------------------------------------------------------------------------

    /**
     * Get a list of all neighbors of the node with the given label. Time complexity should be
     * O(deg(v)).
     * 
     * @param nodeLabel The stated label
     * @return A list with the labels of all neighbors
     */
    public List<String> getNeighbors(final String nodeLabel) {
        List<String> neigbors = new ArrayList<>();
        
        Node node = getNode(nodeLabel);
        Iterator<Edge> edgeIterator = node.getEdges();
        while (edgeIterator.hasNext()) {
            Edge edge = (Edge) edgeIterator.next();
            String destinationNodeLabel = edge.destinationNode.label;
            
            neigbors.add(destinationNodeLabel);
        }
        
        return neigbors;
    }
    
    //---------------------------------------------------------------------------------------------

    /**
     * Traverse the graph from the node with the given label. The graph is traversed with a
     * breadth-first search algorithm. Time complexity should be O(|V| + |E|).
     * 
     * @param startNodeLabel The label of the node to start
     */
    public void bfs(final String startNodeLabel) {
        Queue<Node> queue = new LinkedList<>();
        Map<String, Boolean> labelToIsVisited = new HashMap<String, Boolean>();
        Node node, destNode; 
        Iterator<Edge> edgeIterator; Edge edge;
        
        Node startNode = getNode(startNodeLabel);
        
        queue.add(startNode);
        labelToIsVisited.put(startNodeLabel, true);
        while (!queue.isEmpty()) {
            node = queue.poll();
            
            System.out.println(node.label);
            
            edgeIterator = node.getEdges();
            while (edgeIterator.hasNext()) {
                edge = (Edge) edgeIterator.next();
                destNode = edge.destinationNode;
                
                if (labelToIsVisited.get(destNode.label) == null) {
                    queue.add(destNode);
                    labelToIsVisited.put(destNode.label, true);
                }
            }
        }
    }
    
    //---------------------------------------------------------------------------------------------

    /**
     * Traverse the graph from the node with the given label. The graph is traversed with a
     * Depth-first search algorithm. Time complexity should be O(|V| + |E|).
     * 
     * @param startNodeLabel The label of the node to start
     */
    public void dfs(final String startNodeLabel) {
        Stack<Node> stack = new Stack<>();
        Map<String, Boolean> labelToIsVisited = new HashMap<String, Boolean>();
        Node node, destNode;
        Iterator<Edge> edgeIterator; Edge edge;
        
        Node startNode = getNode(startNodeLabel);
        
        stack.push(startNode);
        labelToIsVisited.put(startNodeLabel, true);
        while (!stack.isEmpty()) {
            node = stack.pop();
            
            System.out.println(node.label);
            
            edgeIterator = node.getEdges();
            while (edgeIterator.hasNext()) {
                edge = (Edge) edgeIterator.next();
                destNode = edge.destinationNode;
                
                if (labelToIsVisited.get(destNode.label) == null) {
                    stack.add(destNode);
                    labelToIsVisited.put(destNode.label, true);
                }
            }
        }
    }
    
    //---------------------------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();
	    for (Map.Entry<String, Node> entries : nodeSet.entrySet()) {
	        String key = entries.getKey();
	        Node value = entries.getValue();
	        
	        builder.append("Node ").append(key).append(" is connected to: ").append("\n");
	        Iterator<Edge> iterator = value.getEdges();
	        while (iterator.hasNext()) {
	            builder.append(iterator.next().destinationNode.label);
                builder.append(", ");
            }
	        builder.setLength(builder.length() - 2);
	        builder.append("\n");
        }

	    return builder.toString();
	}
	
	//---------------------------------------------------------------------------------------------
}
