package track.tsm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.graph.Graph;
import graph.WeightedEdge;

import track.Track;
import matrix.FalseMatrix;
import matrix.FullMatrix;

public class METS_All<V> extends TSM_All<V> {

	public METS_All(List<Track<V>> tracks, Graph<V, WeightedEdge> g) {
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
			for(int j = 0; j < tracks.size(); j++){
				if(j == i){
					r_dists.set(tracks.get(i), tracks.get(j), 0.);
				} else {
										
					V newNode = (V) new Object();
					g.addVertex(newNode);
					
					for(V v : tracks.get(i).getNodes()){
						g.addEdge(new WeightedEdge(g.getEdgeCount(),0.), newNode, v);
					}
					
					DijkstraDistance<V,WeightedEdge> alg = new DijkstraDistance<V, WeightedEdge>(g,new WeightedEdge.weightTransformer(),false);
					HashMap<V, Number> distmap = new HashMap<V, Number>(alg.getDistanceMap(newNode,tracks.get(j).getNodes()));
					
					double max = Double.NEGATIVE_INFINITY;
					//double sumDists = 0.;
					
					for(V r : tracks.get(j).getNodes()){
						double tmp = (Double) distmap.get(r);
						//sumDists += tmp;
						if(max < tmp){
							max = tmp;
						}
					}
					
					g.removeVertex(newNode);
					
					r_dists.set(tracks.get(j), tracks.get(i), max);// sumDists / tracks.get(j).getNodes().size());
				}
			}
		}
		
		
		
		
	}



}
