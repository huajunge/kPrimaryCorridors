package kpc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;




import track.Track;
import track.tsm.TSM;

public abstract class KPrimaryCorridors {
	
	public static <V> KPCResult<V> kprimarycorridor(int k, TSM<V> tsm){
		
		//create medoid array with empty routes so we can 'set' them late
		ArrayList<Track<V>> medoids = new ArrayList<Track<V>>(k);	
		for(int i = 0; i < k; i++){
			medoids.add(null);
		}
		
		boolean randomCenters = false;
		if(randomCenters){ //pick random medoids
			Random rand = new Random();
			for(int i = 0; i < k; i++){
				medoids.set(i,tsm.getTracks().get(rand.nextInt(tsm.getTracks().size())));
			}
		} else { //use fixed centers for comparison purposes
			for(int i = 0; i < k; i++){
				medoids.set(i,tsm.getTracks().get(i));
			}
		}
		
		//create cluster array and fill with empty lists
		ArrayList<LinkedList<Track<V>>> clusters = new ArrayList<LinkedList<Track<V>>>();
		for(int i = 0; i < k; i++){
			clusters.add(new LinkedList<Track<V>>());
		}
				
		ArrayList<Track<V>> oldMedoids = new ArrayList<Track<V>>(k);
		
		while(!medoids.equals(oldMedoids)){
			System.out.println("Iteration");
			
			oldMedoids.clear();
			for(int i = 0; i < medoids.size(); i++){
				oldMedoids.add(medoids.get(i));
			}
			
			//assign tracks to clusters
			for(LinkedList<Track<V>> l : clusters){
				l.clear();
			}
			
			//re-assign clusters
			for(int i = 0; i < tsm.getTracks().size(); i++){
				double closest = Double.POSITIVE_INFINITY;
				int closest_r = -1;
				for(int j = 0; j < medoids.size(); j++){
					double sym = tsm.getSimilarity(tsm.getTracks().get(i), medoids.get(j));// r_dists[i][medoids.get(j).id_value];
					if(sym <= closest){
						closest = sym;
						closest_r = j;
					}
				}
				clusters.get(closest_r).add(tsm.getTracks().get(i));
			}
			
			//PAM (Partition around medoid)  swap each center with every other route, compute and pick smallest
			for(int i = 0; i < k; i++){
				
				//iterate through every possibility
				Track<V> oldMedoid = medoids.get(i);
				double bestScore = Double.POSITIVE_INFINITY;
				
				//try each track as a new medoid for this cluster
				for(Track<V> r : clusters.get(i)){
					medoids.set(i, r);
					//calculate sum of distances to medoid (asymmetric dist, so sum both)
					double tmp = 0.;
					/*for(Double d : tsm.getSimilarity(r, clusters.get(i)).values()){
						tmp += d;
					}*/
					for(Double d : tsm.getSimilarity(clusters.get(i), r).values()){
						tmp += d;
					}
					if(bestScore > tmp){
						bestScore = tmp;
						oldMedoid = r;
					}
				}
				medoids.set(i, oldMedoid);

			}

		}
		
		return new KPCResult<V>(medoids, clusters);
	}

}
