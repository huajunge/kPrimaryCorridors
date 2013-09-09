package track.tsm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.graph.Graph;
import graph.WeightedEdge;

import track.Track;

public class METS_JIT<V> extends TSM_JIT<V> {

	public METS_JIT(List<Track<V>> tracks, Graph<V, WeightedEdge> g) {
		super(tracks, g);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Map<Track<V>, Double> getSimilarity(Track<V> from, List<Track<V>> to) {
		
		HashMap<Track<V>,Double> dists = new HashMap<Track<V>,Double>();
		
		for(Track<V> t : to){
			dists.put(t, this.getSimilarity(from, t));
		}
		
		return dists;
	}

	@Override
	public Map<Track<V>, Double> getSimilarity(List<Track<V>> from, Track<V> to) {
		HashMap<Track<V>,Double> dists = new HashMap<Track<V>,Double>();
		
		for(Track<V> t : from){
			dists.put(t, this.getSimilarity(t, to));
		}
		
		return dists;
	}


	@Override
	public Double getSimilarity(Track<V> from, Track<V> to) {
		
		if(Double.isNaN(r_dists.get(from, to))){
			if(from.id_value == to.id_value){
				r_dists.set(from, to, 0.);
			} else {
				
				V newNode = (V) new Object();
				g.addVertex(newNode);
				
				for(V v : to.getNodes()){
					g.addEdge(new WeightedEdge(g.getEdgeCount(),0.), newNode, v);
				}
				
				DijkstraDistance<V,WeightedEdge> alg = new DijkstraDistance<V, WeightedEdge>(g,new WeightedEdge.weightTransformer(),false);
				HashMap<V, Number> distmap = new HashMap<V, Number>(alg.getDistanceMap(newNode,from.getNodes()));
				
				double max = Double.NEGATIVE_INFINITY;
				
				for(V r : from.getNodes()){
					double tmp = (Double) distmap.get(r);
					if(max < tmp){
						max = tmp;
					}
				}
				
				g.removeVertex(newNode);
				
				
				r_dists.set(from, to, max);//sumDists / from.getNodes().size());
			}
		}
		
		return r_dists.get(from, to);
	}

}
