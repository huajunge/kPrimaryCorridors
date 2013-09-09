package track.tsm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import track.Track;
import edu.uci.ics.jung.graph.Graph;
import graph.WeightedEdge;

public abstract class TSM_All<V> extends TSM<V> implements Serializable {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5971389329389642906L;

	public TSM_All(List<Track<V>> tracks, Graph<V, WeightedEdge> g) {
		super(tracks, g);
		
	}

	
	protected abstract void computeAllSimilarity(List<Track<V>> tracks);


	
	/**
	 * 
	 * @param o
	 * @param t
	 * @return ASYMMETRIC similarity
	 */
	public final Double getSimilarity(Track<V> o, Track<V> t){
		return r_dists.get(o, t);
	}
	
	public final Map<Track<V>, Double> getSimilarity(Track<V> from, List<Track<V>> to) {
		HashMap<Track<V>,Double> map = new HashMap<Track<V>,Double>();
		
		for(Track<V> t : to){
			map.put(t, this.getSimilarity(from, t));
		}
		
		return map;
	}

	@Override
	public final Map<Track<V>, Double> getSimilarity(List<Track<V>> from, Track<V> to) {
		HashMap<Track<V>,Double> map = new HashMap<Track<V>,Double>();
		
		for(Track<V> t : from){
			map.put(t, this.getSimilarity(t, to));
		}
		
		return map;
	}


	
}
