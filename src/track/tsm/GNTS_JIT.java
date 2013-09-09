package track.tsm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.graph.Graph;
import graph.WeightedEdge;

import track.Track;

public class GNTS_JIT<V> extends TSM_JIT<V> {

	public GNTS_JIT(List<Track<V>> tracks, Graph<V, WeightedEdge> g) {
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
	public Double getSimilarity(Track<V> o, Track<V> t) {

		if(Double.isNaN(r_dists.get(o, t))){
			if(o == t){
				r_dists.set(o, t, 0.);
			} else {
				double max = Double.NEGATIVE_INFINITY; //hoff distance
				for(V v : o.getNodes()){
					DijkstraDistance<V,WeightedEdge> alg = new DijkstraDistance<V, WeightedEdge>(g,new WeightedEdge.weightTransformer(),false);
					HashMap<V, Number> distmap = new HashMap<V, Number>(alg.getDistanceMap(v,t.getNodes()));
					double min = Double.POSITIVE_INFINITY;
					for(V r : t.getNodes()){
						double tmp = (Double) distmap.get(r);
						if(min > tmp){
							min = tmp;
						}
					}
					if(max < min){
						max = min;
					}
				}
				r_dists.set(o, t, max);// sumDists / o.getNodes().size());
			}
		}
		
		return r_dists.get(o, t);
	}

}
