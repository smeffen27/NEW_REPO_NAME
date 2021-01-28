package graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.TreeSet;

/**
 * Class for representing a simple directed graph. The graph uses an adjacency list to manage the
 * nodes and edges.
 * 
 * @author Julian
 */
public class DirectedGraph {
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
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("1");
        graph.addNode("2");
        graph.addNode("3");

        graph.addEdge("1", "2", 1);
        graph.addEdge("1", "3", 1);
        graph.addEdge("2", "3", 1);
        
        System.out.println(graph.toString());
    }

    //=============================================================================================

	/** Map that contains all <code>Node</code>s of the graph. */
	private Map<String, Node> nodeSet = new HashMap<String, Node>();

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
     * Add an <code>Edge</code> between the <code>Node</code>s with the given labels and with the
     * given weight. Because this is a directed graph the edge is only added from the start node to
     * the destination node.
     * 
     * @param startNode The start <code>Node</code> of the <code>Edge</code>
     * @param destinationNode The destination <code>Node</code> of the <code>Edge</code>
     * @param weight The weight of the <code>Edge</code>
     */
	public void addEdge(final String startNode, final String destinationNode, final int weight) {
		Node srcNode = getNode(startNode);
		Node destNode = getNode(destinationNode);
		srcNode.addEdge(new Edge(destNode, weight));  
	}
	
    //---------------------------------------------------------------------------------------------
	
	/**
     * Get a map with the labels of the node and the distance to the node with the given label.
     * 
     * @param startNodeLabel
     * @return A map with the labels of the nodes as key and the distance to the node with the
     *         given label as value
     */
	public Map<String, Integer> dijkstra(final String startNodeLabel) {       
        Collection<Node> nodes = nodeSet.values();        
        Map<String, Integer> labelToDistance = new HashMap<>();

        PriorityQueue<Node> queue = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(final Node o1,final Node o2) {
                
                return 0;
            }
        });
        queue.addAll(nodes);
        
        Node lowestDistanceNode, destNode;
        Iterator<Edge> edgeIterator; Edge edge;
        
        Node startNode = getNode(startNodeLabel);
        labelToDistance.put(startNodeLabel, 0);
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
        builder.append("\n");

        return builder.toString();
    }
    
    //---------------------------------------------------------------------------------------------
}
