package track.tsm;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import edu.uci.ics.jung.graph.Graph;
import graph.WeightedEdge;

import matrix.Matrix;

import track.Track;

public abstract class TSM<V> implements Comparable<TSM<V>>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7102972378955815225L;
	protected Matrix<Track<V>> r_dists;
	private List<Track<V>> tracks;
	protected Graph<V,WeightedEdge> g;
	
	
	public TSM(List<Track<V>> tracks, Graph<V,WeightedEdge> g){
		this.g = g;
		this.tracks = tracks;	
		
	}
	

	public final Matrix<Track<V>> getTSM(){
		return r_dists;
	}
	
	/**
	 * 
	 * @param o
	 * @param t
	 * @return ASYMMETRIC similarity
	 */
	public abstract Double getSimilarity(Track<V> o, Track<V> t);
	
	public abstract Map<Track<V>,Double> getSimilarity(Track<V> from, List<Track<V>> to);
	
	public abstract Map<Track<V>,Double> getSimilarity(List<Track<V>> from, Track<V> to);
	
	/**
	 * Return Weak Frechet Distance
	 * 
	 * Probably want to override in subclasses, but this should get the job done
	 * 
	 * @param o
	 * @param t
	 * @return
	 */
	public Double getWeakFrechetDistance(Track<V> o, Track<V> t){
		return Math.max(this.getSimilarity(o, t), this.getSimilarity(t, o));
	}

	public final List<Track<V>> getTracks() {
		return tracks;
	}

	@Override
	public final int compareTo(TSM<V> other){
		return this.getTSM().compareTo(other.getTSM());
	}
	
	@Override
	public String toString(){
		return r_dists.toString();
	}

	
}
