package kcc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.graph.Graph;

import track.Track;
import track.tsm.TSM;

public class Cluster<V> {
	
	private final Track<V> centroid;
	private List<Track<V>> tracksInCorridor;
	private Double maxwfd;
	private HashSet<V> nodeBoundary;

	public Cluster(Track<V> centroid){
		if(centroid == null){
			throw new NullPointerException();
		}
		this.centroid = centroid;
		tracksInCorridor = new ArrayList<Track<V>>();
		nodeBoundary = new HashSet<V>();
	}

	public List<Track<V>> getTracksInCorridor() {
		return tracksInCorridor;
	}

	public boolean addTrack(Track<V> arg0){
		return tracksInCorridor.add(arg0);
	}

	public Track<V> getCentroid() {
		return centroid;
	}
	
	public double getMaxWFD(TSM<V> tsm){
		double max = Double.NEGATIVE_INFINITY;
		if(maxwfd == null){
			//compute maxwfd
			for(Track<V> t : tracksInCorridor){
				if(tsm.getWeakFrechetDistance(centroid, t) > max){
					max = tsm.getWeakFrechetDistance(centroid, t);
				}
			}
			maxwfd = max;
		} 
		
		return maxwfd;
		
	}
	
	public <E> HashSet<V> getNodeBoundary(Graph<V,E> g, TSM<V> tsm){
		/*System.out.println("Getting boundary");
		for(Track<V> t : tracksInCorridor){
			nodeBoundary.addAll(t.getNodes());
		}
		
		HashSet<V> newNodes = new HashSet<V>();
		//this is probably super slow
		//could add a virtual node to all nodes in tracks and bfs from there
		for(V v : nodeBoundary){
			DijkstraDistance d = new DijkstraDistance<V, E>(g);
			d.setMaxDistance(this.getMaxWFD(tsm));
			newNodes.addAll(d.getDistanceMap(v).keySet());
		}*/
		nodeBoundary.addAll(g.getVertices());//newNodes);
		System.out.println("Got boundary");
		return nodeBoundary;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Cluster<V> clus = (Cluster<V>) obj;
		
		if(this.centroid.equals(clus.getCentroid())){
			//if(this.tracksInCorridor.size() == clus.getTracksInCorridor().size()){   //not working as intended, tracks arent set to new corridor yet
				return true;
			//}
		}
		
		return false;

	}

	@Override
	public String toString() {
		return "Size:" + tracksInCorridor.size() + " - " + centroid.toString();
	}
	
	public Cluster<V> copyCluster(){
		Cluster<V> tmp = new Cluster<V>(this.centroid);
		for(Track<V> t : this.getTracksInCorridor()){
			tmp.addTrack(t);
		}
		return tmp;
	}
	
	
}
