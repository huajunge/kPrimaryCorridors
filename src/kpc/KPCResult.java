package kpc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;


import track.Track;

public class KPCResult<V> {
	
	private ArrayList<Track<V>> medoids;
	private ArrayList<LinkedList<Track<V>>> clusters;

	public KPCResult(ArrayList<Track<V>> medoids,
			ArrayList<LinkedList<Track<V>>> clusters) {
		this.medoids = medoids;
		this.clusters = clusters;
	}

	


	public ArrayList<Track<V>> getCenters() {
		return medoids;
	}

	public void setCenters(ArrayList<Track<V>> centers) {
		this.medoids = centers;
	}

	public ArrayList<LinkedList<Track<V>>> getClusters() {
		return clusters;
	}

	public void setClusters(ArrayList<LinkedList<Track<V>>> clusters) {
		this.clusters = clusters;
	}
	
	
	@Override
	public boolean equals(Object o){
		
		
		
		KPCResult other = (KPCResult) o;
		
		
		//compare centers
		HashSet set = new HashSet();
		
		for(int i = 0; i < medoids.size(); i++){
			set.add(medoids.get(i));
		}
		
		HashSet oSet = new HashSet();
		
		for(int i = 0; i < other.getCenters().size(); i++){
			oSet.add(other.getCenters().get(i));
		}
		
		if(set.containsAll(oSet) && oSet.containsAll(set)){
			//System.out.println("centers are the same");
		} else {
			return false;
		}
		
		//compare clusters
		
		for(LinkedList<Track<V>> routesInOuterCluster : clusters){
			
			if(other.getClusters().contains(routesInOuterCluster)){
				//System.out.println(routesInOuterCluster);
				//System.out.println(other.getClusters().get(other.getClusters().indexOf(routesInOuterCluster)));
			} else {
				return false;
			}
			//System.out.println(clusters.indexOf(routesInOuterCluster) + " cluster is equal");
		}
		
		return true;
		
	}
	
	
	public String toString(){
		String tmp = "KMetoidResult:\n";
		
		for(int i = 0; i < medoids.size(); i++){
			tmp += i + "," + medoids.get(i).getLabel() + "\n";
			
			
				
		}
		tmp += "-------\n";
		
		for(int i = 0; i < medoids.size(); i++){
			
			Track<V> center = medoids.get(i);
			
			for(Track r : clusters.get(i)){
				tmp += i + "," + r.getLabel() + "\n";
			}
					
		}
		
		return tmp;
	}
	
}
