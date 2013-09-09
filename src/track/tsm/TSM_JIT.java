package track.tsm;

import java.util.List;
import java.util.Map;

import matrix.SparseMatrix;

import Jama.Matrix;

import edu.uci.ics.jung.graph.Graph;
import graph.WeightedEdge;

import track.Track;

public abstract class TSM_JIT<V> extends TSM<V> {

	public TSM_JIT(List<Track<V>> tracks, Graph<V, WeightedEdge> g) {
		super(tracks, g);
		
		/**
		 * we need to do this without the r_dists variable, its too big, its too strong
		 */
		this.r_dists = new SparseMatrix(tracks.size(),tracks.size(), Double.NaN);
	}

	
	public abstract Map<Track<V>, Double> getSimilarity(Track<V> from, List<Track<V>> to);

	
	public abstract Map<Track<V>, Double> getSimilarity(List<Track<V>> from, Track<V> to);


	public abstract Double getSimilarity(Track<V> o, Track<V> t);
	
	
	

}
