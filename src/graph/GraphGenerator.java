package graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.io.GraphMLReader;
import edu.uci.ics.jung.io.GraphMLWriter;
import edu.uci.ics.jung.io.graphml.GraphMLReader2;

public class GraphGenerator {
	
	public static Graph<Integer, WeightedEdge> createPlanarUSG(int num_v){
		Graph<Integer, WeightedEdge> g = new UndirectedSparseGraph<Integer, WeightedEdge>();
		
		
		int dim_size = (int) Math.round(Math.sqrt(num_v));
		int count = 0;
		
		int[][] varray = new int[dim_size][dim_size];
		
		for(int i = 0; i < dim_size; i++){
			for(int j = 0; j < dim_size; j++){
				g.addVertex((Integer)count);
				varray[i][j] = count;
				count++;
			}	
		}
		
		Random rand = new Random();
		
		for(int i = 0; i < dim_size; i++){
			for(int j = 0; j < dim_size; j++){
				try {
					// left
					if(rand.nextBoolean()){
					g.addEdge(new WeightedEdge(g.getEdgeCount(), 1.), varray[i][j], varray[i - 1][j]);
					}
				} catch (ArrayIndexOutOfBoundsException e) {

				}
				try {
					// right
					if(rand.nextBoolean()){
						g.addEdge(new WeightedEdge(g.getEdgeCount(), 1.), varray[i][j], varray[i + 1][j]);
					}
				} catch (ArrayIndexOutOfBoundsException e) {

				}
				try {
					// down
					if(rand.nextBoolean()){
					g.addEdge(new WeightedEdge(g.getEdgeCount(), 1.), varray[i][j], varray[i][j + 1]);
					}
				} catch (ArrayIndexOutOfBoundsException e) {

				}
				try {
					// up
					if(rand.nextBoolean()){
					g.addEdge(new WeightedEdge(g.getEdgeCount(), 1.), varray[i][j],	varray[i][j - 1]);
					}
				} catch (ArrayIndexOutOfBoundsException e) {

				}
				if(g.getNeighborCount(varray[i][j]) == 0){
					int tmp = rand.nextInt(num_v);
					g.addEdge(new WeightedEdge(g.getEdgeCount(), 1.), varray[i][j],	tmp);
				}
				
			}
		}
		
		
		return g;
		
	}
	
	public static Graph<Integer, WeightedEdge> createUndirectedSparseGraph(int num_v, int num_e){
		Graph<Integer, WeightedEdge> g = new UndirectedSparseGraph<Integer, WeightedEdge>();
		
		ArrayList<Integer> verticies = new ArrayList<Integer>();
		
		System.out.println("Creating verticies");
		for(int i = 0; i < num_v; i++){
			verticies.add(i);
			g.addVertex((Integer)i);
		}
		
		//int[][] edges = new int[num_v][num_v];
		
		Random random = new Random();
		System.out.println("Creating edges");
		for(int i = 0; i < num_e; i++){
			int rand1 = random.nextInt(num_v);
			int rand2 = random.nextInt(num_v);
			
			if(rand1 != rand2){
				g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), rand1, rand2);
			}
			
		}
		
		System.out.println("Ensure connectivity");
		
		DijkstraDistance<Integer,WeightedEdge> alg = new DijkstraDistance<Integer, WeightedEdge>(g,new WeightedEdge.weightTransformer(),false);
		HashMap<Integer, Number> distmap = new HashMap<Integer, Number>(alg.getDistanceMap(verticies.get(0)));
		//HashMap<Integer,Double> dists = new HashMap<Integer,Number>()
		for(Integer i : verticies){
			if(!distmap.containsKey(i)){
				g.addEdge(new WeightedEdge(g.getEdgeCount(),1.),0,i);
			}
		}
		
		return g;
		
	}
	
	public static  void writeGraphOut(Graph<Integer, WeightedEdge> g, String filename){
		
		GraphMLWriter<Integer, WeightedEdge> graphWriter =
                new GraphMLWriter<Integer, WeightedEdge> ();

		try {
			PrintWriter out = new PrintWriter(
			             new BufferedWriter(
			                 new FileWriter(filename)));
			
			
			graphWriter.save(g, out);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public static void main(String[] args) {
		//int edges = (int) Math.floor((Integer.parseInt(args[0]) * 1.75));
		Graph<Integer, WeightedEdge> g = GraphGenerator.createPlanarUSG(Integer.parseInt(args[0]));
		GraphGenerator.writeGraphOut(g, args[0] + "_nodes.gml");
		ViewGraph.visualizeGraph(g);

	}

}
