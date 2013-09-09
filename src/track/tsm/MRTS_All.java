package track.tsm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.graph.Graph;
import graph.WeightedEdge;

import track.Track;
import matrix.FalseMatrix;
import matrix.FullMatrix;

public class MRTS_All<V> extends TSM_All<V> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2175945940061737297L;

	public MRTS_All(List<Track<V>> tracks, Graph<V, WeightedEdge> g) {
		super(tracks, g);
		this.computeAllSimilarity(tracks);
	}

	/**
	 * Hausdorff Distance
	 */
	@Override
	protected void computeAllSimilarity(List<Track<V>> tracks) {
		
		
		r_dists = new FullMatrix(tracks.size(),tracks.size());
		
		for(int i = 0; i < tracks.size(); i++){
						
			V newNode = (V) new Object();
			g.addVertex(newNode);
			
			for(V v : tracks.get(i).getNodes()){
				g.addEdge(new WeightedEdge(g.getEdgeCount(),0.), newNode, v);
			}
			
			DijkstraDistance<V,WeightedEdge> alg = new DijkstraDistance<V, WeightedEdge>(g,new WeightedEdge.weightTransformer(),false);
			HashMap<V, Number> distmap = new HashMap<V, Number>(alg.getDistanceMap(newNode));
			
			g.removeVertex(newNode);
			
			for(int j = 0; j < tracks.size(); j++){
				if(j == i){
					r_dists.set(tracks.get(i), tracks.get(j), 0.);
				} else {
					double sumDists = 0.; //avg node dist
					double max = Double.NEGATIVE_INFINITY; //hoff distance
					for(V r : tracks.get(j).getNodes()){
						double tmp;
						try {
							tmp =  (Double) distmap.get(r);
						} catch (NullPointerException e){
							tmp = Double.MAX_VALUE;
						}
						if(max < tmp){
							max = tmp;
						}
						sumDists += tmp;
					}
					r_dists.set(tracks.get(j), tracks.get(i), max); // nhd
					//r_dists.set(tracks.get(j), tracks.get(i), sumDists / tracks.get(j).getNodes().size()); //avg n d
				}
			}

			
		}
		
		
		
		
	}



}
