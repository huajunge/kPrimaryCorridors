package kcc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import edu.uci.ics.jung.graph.Graph;


import track.Track;

public class KCCResult<V> {
	

	private ArrayList<Cluster<V>> clusters;

	
	
	/**
	 * @param clusters
	 */
	public KCCResult(ArrayList<Cluster<V>> clusters) {
		super();
		this.clusters = clusters;
	}

	public ArrayList<Cluster<V>> getClusters() {
		return clusters;
	}

	public void setClusters(ArrayList<Cluster<V>> clusters) {
		this.clusters = clusters;
	}

	@Override
	public boolean equals(Object arg0) {
		KCCResult<V> old = (KCCResult<V>) arg0;
		
		if(clusters.containsAll(old.getClusters())){
			if(old.getClusters().containsAll(clusters)){
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		String tmp = "KCC Result \n";
		
		for(int i = 0; i < clusters.size(); i++){
			Cluster<V> c = clusters.get(i);
			for(V v : c.getCentroid().getNodes()){
				tmp += i + "," + v.toString() + "\n";
			}
			
		}
		
		return tmp;
	}

	public <E> void printCorridors(Graph<V,E> g) {
		for(int i = 0; i < clusters.size(); i++){
			Cluster<V> c = clusters.get(i);
			Track<V> centroid = c.getCentroid();
			for(V v : centroid.getNodes()){
				System.out.println(i + "," + v.toString());
			}
			
			
		}
	}
	
	
	public <E> void printEdges(Graph<V,E> g) {
		for(int i = 0; i < clusters.size(); i++){
			Cluster<V> c = clusters.get(i);
			Track<V> centroid = c.getCentroid();
			for(int j = 0; j < centroid.getNodes().size()-1; j++){
				V v1 = centroid.getNodes().get(j);
				V v2 = centroid.getNodes().get(j+1);
				System.out.println(i + "," + g.findEdge(v1, v2));
			}
		
			
			
		}
	}
	
	public void printSummary(){
		for(int i = 0; i < clusters.size(); i++){
			Cluster<V> c = clusters.get(i);
			System.out.println("Cluster " + i + " with size " + c.getTracksInCorridor().size());
			
			
		}
	}
	
	public <E> void printTracks(Graph<V,E> g) {
		for(int i = 0; i < clusters.size(); i++){
			Cluster<V> c = clusters.get(i);
			for(Track<V> t : c.getTracksInCorridor()){
				for(int j = 0; j < t.getNodes().size() - 1; j++){
					V v1 = t.getNodes().get(j);
					V v2 = t.getNodes().get(j+1);
					System.out.println(i + "," +v1.toString() + "," + v2.toString());
				}
			}
		}
	}
	

	
	
	
}
