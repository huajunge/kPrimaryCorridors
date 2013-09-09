package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;

import edu.uci.ics.jung.graph.Graph;


public class FloydWarshall<V,E> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5;
	
	private ArrayList<ArrayList<Double>> storedValueMatrix;
	private HashMap<V, Integer> nodeIDs;
	
	private HashMap<V,Double> dist;  //FW should return this to be consistent with Dijkstra's
	
	public FloydWarshall(Graph<V,E> g){
		
		storedValueMatrix = new ArrayList<ArrayList<Double>>();//new double[g.getVertexCount()][g.getVertexCount()];
		
		for(int i = 0; i < g.getVertexCount(); i++){
			storedValueMatrix.add(new ArrayList<Double>());
			for(int j = 0; j < g.getVertexCount(); j++){
				if(i == j){
					storedValueMatrix.get(i).add(0.);
				} else {
					storedValueMatrix.get(i).add(Double.POSITIVE_INFINITY);
				}
			}
		}
		
		nodeIDs = new HashMap<V, Integer>();
		
		ArrayList<V> array = new ArrayList<V>(g.getVertices());
		
		for(int i = 0; i < array.size(); i++){
			nodeIDs.put(array.get(i),i);
		}
		
		for(V v : g.getVertices()){
			for(V n : g.getNeighbors(v)){
				if(v.equals(n)){
					//my graph creation is random, this is a self-edge
				} else {
					storedValueMatrix.get(nodeIDs.get(v)).set(nodeIDs.get(n),1.);// = 1;
				}
			}
		}
		
		
		
		for(int k = 0; k < g.getVertexCount(); k++){
			for(int i = 0; i < g.getVertexCount(); i++){
				for(int j = 0; j < g.getVertexCount(); j++){
					storedValueMatrix.get(i).set(j,Math.min(storedValueMatrix.get(i).get(j), storedValueMatrix.get(i).get(k) + storedValueMatrix.get(k).get(j)));
				}
			}
		}
		
	}
	
	public double getDistance(V source, V dest){
		return storedValueMatrix.get(nodeIDs.get(source)).get(nodeIDs.get(dest));
	}
	
	
	
}
