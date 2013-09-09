package track;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;


import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;
import graph.GraphGenerator;

public class TrackGenerator {

	public static <E, V> ArrayList<Track<V>> generateRoutes(Graph<V,E> g, int numRoutes){
		
		return TrackGenerator.generateRoutes(g, numRoutes, 50);
		
	}
	
	public static <E, V> ArrayList<Track<V>> generateCandidateRoutesFromStreets(Graph<V,E> g){
		
		return null;
	}
	
	public static <E, V> ArrayList<Track<V>> generateNChoose2Paths(Graph<V,E> g){
		
		ArrayList<Track<V>> routes = new ArrayList<Track<V>>();
		
		DijkstraShortestPath<V, E> ds = new DijkstraShortestPath<V, E>(g, true);
		
		double target_distance = 20.;
		
		/**
		 * this will get crazy.
		 */
		for(V v : g.getVertices()){
			for(V v2 : g.getVertices()){
				if(!v.equals(v2)){
					Track<V> r = new Track<V>(routes.size());

					if(ds.getDistance(v,v2) != null && (Double) ds.getDistance(v, v2) > target_distance){
						List<E> nodesList = ds.getPath(v,v2);
						for(E e : nodesList){
							Pair<V> p = g.getEndpoints(e);
							for(V v3 : p){
								r.appendNode(v3);
							}
							
						}
						
						if(!r.getNodes().isEmpty()){
							routes.add(r);
						}
					}
					
					
				}
			}
		}
		
		return routes;
	}
	
	public static <E, V> ArrayList<Track<V>> generateRoutes(Graph<V,E> g, int numRoutes, int length){
		
		Random rand = new Random();

		ArrayList<Track<V>> routes = new ArrayList<Track<V>>();
		
		ArrayList<V> nodes = new ArrayList<V>(g.getVertices());
		
		
		
		for(int i = 0; i < numRoutes; i++){
			
			Track<V> r = new Track<V>(i);
			
			HashSet<V> set = new HashSet<V>();
			
			while(set.size() < length){
				set.add(nodes.get(rand.nextInt(nodes.size())));
			}

			for(V v : set){
				r.appendNode(v);
			}
			
			routes.add(r);
			
		}
		
		return routes;
		
	}
	
	public static void main(String[] args) {
		ArrayList tracks = TrackGenerator.generateNChoose2Paths(GraphGenerator.createUndirectedSparseGraph(1000, 3000));
		System.out.println(tracks.size());
	}
	
}
