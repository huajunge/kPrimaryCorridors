package track.tsm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import track.Track;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.graph.Graph;
import graph.WeightedEdge;

public class MRTS_JIT<V> extends TSM_JIT<V> {

	public MRTS_JIT(List<Track<V>> tracks, Graph<V, WeightedEdge> g) {
		super(tracks, g);
		// TODO Auto-generated constructor stub
	}

	/**
	 * this sucks
	 */
	@Override
	public Map<Track<V>, Double> getSimilarity(Track<V> from, List<Track<V>> to) {
		//need to attach a virtual node to each track in @to
		HashMap<Track<V>,Double> dists = new HashMap<Track<V>,Double>();
		
		for(Track<V> t : to){
			dists.put(t, this.getSimilarity(from, t));
		}
		
		return dists;
		
	}

	/**
	 * Compute H(f,t), attach virtual node to "to", compute dists for each from
	 * 	this is the version that mrts is good at, reverse is bad
	 */
	@Override
	public Map<Track<V>, Double> getSimilarity(List<Track<V>> from, Track<V> to) {
		
		HashMap<Track<V>,Double> dists = new HashMap<Track<V>,Double>();
		
		//LinkedList<Track<V>> compute = new LinkedList<Track<V>>(from); //was using this for commented out section, remove "from" from this line
		
		boolean compute = false;
		
		for(Track<V> t : from){
			Double tmp = r_dists.get(t, to);
			if(Double.isNaN(tmp) || tmp == this.getTSM().getDefaultValue()){
				//compute.add(t);
				compute = true;
			} else {
				dists.put(t, r_dists.get(t, to));
			}
		}
		
		if(compute){
			dists.clear();
			
			V newNode = (V) new Object();
			g.addVertex(newNode);
			
			for(V v : to.getNodes()){
				g.addEdge(new WeightedEdge(g.getEdgeCount(),0.), newNode, v);
			}
			
			DijkstraDistance<V,WeightedEdge> alg = new DijkstraDistance<V, WeightedEdge>(g,new WeightedEdge.weightTransformer(),false);
			HashMap<V, Number> distmap = new HashMap<V, Number>(alg.getDistanceMap(newNode));
			
			g.removeVertex(newNode);
			
			for(Track<V> t : from){
				if(t.id_value == to.id_value){
					r_dists.set(t, to, 0.);
				} else {
					double max = Double.NEGATIVE_INFINITY; //hoff distance
					for(V r : t.getNodes()){
						double tmp;
						try {
							tmp =  (Double) distmap.get(r);
						} catch (NullPointerException e){
							tmp = Double.MAX_VALUE;
						}
						if(max < tmp){
							max = tmp;
						}
					}
					r_dists.set(t, to, max);//sumDists / t.getNodes().size());
				}
				dists.put(t, r_dists.get(t, to));
			}
			
		} else {
			//System.out.println("Didn't need to recompute this set");
		}
		
		return dists;
	}

	@Override
	public Double getSimilarity(Track<V> o, Track<V> t) {

		if(Double.isNaN(r_dists.get(o, t))){
			LinkedList l =  new LinkedList();
			l.add(o);
			this.getSimilarity(l,t);
			
		}
		
		return r_dists.get(o, t);
	}

}
