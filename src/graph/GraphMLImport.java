package graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.io.GraphMLReader;

public class GraphMLImport {

	
public static Graph<SpatialNode, WeightedEdge> importSpatialFile(String nodeFile, String edgeFile){
	
	
		Graph<SpatialNode, WeightedEdge> g = new UndirectedSparseGraph<SpatialNode, WeightedEdge>();
		HashMap<Integer,SpatialNode> nodes = new HashMap<Integer,SpatialNode>();
		
		System.out.println("Importing Nodes: " + nodeFile);
		try{
			//import nodes
			Scanner scanLine = new Scanner(new File(nodeFile));
			
			scanLine.nextLine();
			
			while(scanLine.hasNextLine()){
				Scanner scan = new Scanner(scanLine.nextLine()).useDelimiter(",");
				int id = Integer.parseInt(scan.next());
				double x = scan.nextDouble();
				double y = scan.nextDouble();
				
				nodes.put(id,new SpatialNode(id, x,y));
				
				
			}
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
		
		System.out.println("Importing Edges: " + edgeFile);
		try {
			//import edges
			Scanner scanLine = new Scanner(new File(edgeFile));
			
			scanLine.nextLine();
			
			while(scanLine.hasNextLine()){
				Scanner scan = new Scanner(scanLine.nextLine()).useDelimiter(",");
				int id = Integer.parseInt(scan.next());
				String label = scan.next();
				double dist = Double.parseDouble(scan.next());
				Integer source = Integer.parseInt(scan.next());
				Integer dest = Integer.parseInt(scan.next());
				
				try {
					g.addVertex(nodes.get(source));
					g.addVertex(nodes.get(dest));
					
					g.addEdge(new WeightedEdge(id, dist, label), nodes.get(source), nodes.get(dest));
				} catch (IllegalArgumentException e){
					//node doesn't exsist
				}
				
				
			}
			
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return g;
	}
	
	public static Graph<Integer, WeightedEdge> readGraph(String filename){
		
		Graph<Integer, WeightedEdge> g = new UndirectedSparseGraph<Integer, WeightedEdge>();
		
		GraphMLReader gmlr;
		try {
			gmlr = new GraphMLReader(new VertexFactory(), new EdgeFactory());
			
			//Next we need a Graph to store the data that we are reading in from GraphML. This is also an Undirected Graph because it needs to match to the type of graph we are reading in.
			
			 
			//Here we read in our graph. filename is our .graphml file, and graph is where we will store our graph.
			gmlr.load(filename, g);
			
			
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
		return g;
		

	}
	
	public static void main(String[] args) {
		int nodes = 100;
		int edges = (int) Math.floor(nodes * 1.75);
		Graph<Integer, WeightedEdge> g = GraphGenerator.createUndirectedSparseGraph(nodes, edges);
		GraphGenerator.writeGraphOut(g, args[1]);
		Graph<Integer, WeightedEdge> newg = GraphMLImport.readGraph(args[1]);
		System.out.println("AHH");
		
	}
	
}
