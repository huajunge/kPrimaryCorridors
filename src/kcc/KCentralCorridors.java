package kcc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import edu.uci.ics.jung.graph.Graph;

import track.Track;
import track.tsm.*;

public abstract class KCentralCorridors {

	public static <V,E> KCCResult<V> kCentralCorridors(int k, Graph<V,E> g, TSM<V> tsm,  List<Track<V>> tracks, List<Track<V>> candidates, boolean randomCenters, boolean naive){
		
		
		
		ArrayList<Cluster<V>> clusters = new ArrayList<Cluster<V>>(k);
		ArrayList<Cluster<V>> oldClusters = new ArrayList<Cluster<V>>(k);
		
		//assign (random or fixed) centers
		if(randomCenters){
			Random rand = new Random();
			for(int i = 0; i < k; i++){
				clusters.add(new Cluster<V>(candidates.get(rand.nextInt(candidates.size()))));
			}
		} else {
			for(int i = 0; i < k; i++){
				clusters.add(new Cluster<V>(candidates.get(candidates.size() % (i+1))));
			}
		}
		
		int count = 0;
		while(KCentralCorridors.clustersChanged(clusters, oldClusters)){
			count++;
			System.out.println("Iteration: " + count);
			
			oldClusters =  KCentralCorridors.getCopyClusterList(clusters);
			
			
			System.out.println("Assigning tracks to candidates");
			//1. Assign tracks (T) to closest candidates (L)
			
			//lets try computing it in reverse
			//ArrayList<HashMap<Track<V>,Double>> clusterDists = new ArrayList<HashMap<Track<V>,Double>>();
			//ALL DISTS COMPUTED
			/*for(int i = 0; i < clusters.size(); i++){
				Cluster<V> c = clusters.get(i);
				//compute from c to all tracks (or reverse, whatever)
				//if distmap shows c closer to t, set it inside t structure itself
				HashMap<Track<V>,Double> dists = new HashMap<Track<V>,Double>(tsm.getSimilarity(tracks, c.getCentroid()));
				double dist = KCentralCorridors.wcss(tsm, tracks, c.getCentroid());
				clusterDists.add(dists);
			}*///ASSIGN DISTANCES
			for(Track<V> t : tracks){
				double closestValue = Double.POSITIVE_INFINITY;
				Cluster<V> closestClus = null;
				for(int i = 0; i < clusters.size(); i++){
					//HashMap<Track<V>,Double> dists = clusterDists.get(i);
					if(KCentralCorridors.wcss(tsm, t, clusters.get(i).getCentroid()) < closestValue){
						closestValue = KCentralCorridors.wcss(tsm, t, clusters.get(i).getCentroid());
						closestClus = clusters.get(i);
					}
					//t.getCentroidMap().put(clusters.get(i).getCentroid(), dists.get(t));
				//}
				}
				closestClus.addTrack(t);
			}
			
			
			System.out.println("Checking for empty clusters");
			//1.1 check for empty clusters, add one from a different cluster
			for(int i = 0; i < k; i++){
				Cluster<V> c = clusters.get(i);
				if(c.getTracksInCorridor().size() < 1){
					Random rand = new Random();
					List<Track<V>> list = clusters.get(rand.nextInt(k)).getTracksInCorridor();
					if(list.size() < 1){
						i = i - 1;
					} else {
						c.addTrack(list.remove(rand.nextInt(list.size())));
					}
				}
			}
			
			System.out.println("Choosing new centers for corridors");
			//2. Choose new centers for all clusters (C)
			
			ArrayList<Double> sums = new ArrayList<Double>(k);
			
			if(naive){
				for(int i = 0; i < k; i++){
					Cluster<V> c = clusters.get(i);
					double closestValue2 = Double.POSITIVE_INFINITY;
					Track<V> potentialTrack = null;
					for(int j = 0; j < candidates.size(); j++){
						Track<V> cand = candidates.get(j);
						double tmp = KCentralCorridors.wcss(tsm, c.getTracksInCorridor(), cand);
						if(tmp < closestValue2){
							closestValue2 = tmp;
							potentialTrack = cand;
						}
					}
					clusters.set(i, new Cluster<V>(potentialTrack));
					sums.add(closestValue2);
					
				}
				
			} else {
				//only search 'close' candidates, TSM needs to be computing JIT
				int prunedCalcs = 0;
				for(int i = 0; i < k; i++){
					System.out.println("cluster " + i);
					Cluster<V> c = clusters.get(i);
					double closestValue = Double.POSITIVE_INFINITY;
					Track<V> potentialTrack = null;
					HashSet<V> nodeBoundary = c.getNodeBoundary(g, tsm);
					int siz = candidates.size();
					for(Track<V> tcan : candidates){
						//System.out.println("candidate: " + siz--);
						// this needs to be done smarter, at least in one direction  (entire candidate list to one track, etc)
						if(!Collections.disjoint(nodeBoundary,tcan.getNodes())){
							Track<V> cand = tcan;
							//HashMap<Track<V>,Double> ldists = new HashMap<Track<V>,Double>(tsm.getSimilarity(clusters.get(i).getTracksInCorridor(), cand));
							//HashMap<Track<V>,Double> rdists = new HashMap<Track<V>,Double>(tsm.getSimilarity(cand, clusters.get(i).getTracksInCorridor()));
							double tmp = KCentralCorridors.wcss(tsm, clusters.get(i).getTracksInCorridor(), cand);
							if(tmp < closestValue){
								closestValue = tmp;
								potentialTrack = cand;
							}
														
						} else {
							prunedCalcs++;
							//don't bother with this one
							//System.out.println("Have candidates but not contained");
						}
					}
					if(potentialTrack == null){
						Random rand = new Random();
						clusters.set(i, clusters.get(rand.nextInt(k)));
					} else {
						clusters.set(i, new Cluster<V>(potentialTrack));
					}
				}
				System.out.println("Pruned TSM calculations: " + prunedCalcs);
			}
			
			//swapping phase
			/*ArrayList<Cluster<V>> swapClusters = KCentralCorridors.getCopyClusterList(clusters);
			for(int i = 0; i < k; i++){
				//iterate through each cluster, swap in new centers (at random? all?), calc total score and change if better
				double curScore = KCentralCorridors.calculateTotalScore(swapClusters, tsm);
				for(int j = 0; j < candidates.size(); j++){
					ArrayList<Cluster<V>> testClusters = KCentralCorridors.getCopyClusterList(swapClusters);
					Track<V> cand = candidates.get(j);
					testClusters.set(i, new Cluster<V>(cand));
					KCentralCorridors.assignClusters(tracks, testClusters, tsm);
					double tmpScore =  KCentralCorridors.calculateTotalScore(testClusters, tsm);
					if(tmpScore < curScore){
						swapClusters = KCentralCorridors.getCopyClusterList(testClusters);
					}
				}
			}
			clusters =  KCentralCorridors.getCopyClusterList(swapClusters);
			*/
		}
		
		
		//System.out.println(oldClusters.toString());
		
		
		KCentralCorridors.assignClusters(tracks, oldClusters, tsm);
		return new KCCResult<V>(oldClusters);		
	}
	
	/*private static <V> double calculateWCSS(TSM<V> tsm, List<Track<V>> tracks, Track<V> center){
		double sum = 0.;
		for(Track<V> t : tracks){
			sum += Math.pow(tsm.getWeakFrechetDistance(t,center),2);
		}
		
		return sum;
	}*/
	
	private static <V> ArrayList<Cluster<V>> getCopyClusterList(ArrayList<Cluster<V>> clus){
		ArrayList<Cluster<V>> tmp = new ArrayList<Cluster<V>>();
		
		for(Cluster<V> c : clus){
			tmp.add(c.copyCluster());
		}
		
		return tmp;
		
	}
	
	private static <V> Double wcss(TSM<V> tsm, Track<V> from, Track<V> center){
		ArrayList<Track<V>> array = new ArrayList<Track<V>>();
		array.add(from);
		return KCentralCorridors.wcss(tsm, array, center);
	}
	
	private static <V> Double wcss(TSM<V> tsm, List<Track<V>> tracks, Track<V> center){
		
		HashMap<Track<V>,Double> ldists = new HashMap<Track<V>,Double>(tsm.getSimilarity(tracks, center));
		HashMap<Track<V>,Double> rdists = new HashMap<Track<V>,Double>(tsm.getSimilarity(center, tracks));
		double tmp = 0.;
		for(Track<V> t : tracks){
			//weak frechet distance
			//tmp += Math.pow(Math.min(10000., Math.max(ldists.get(t), rdists.get(t))),2);
			//avg of NHD or AND
			tmp += Math.pow(Math.min(10000., (ldists.get(t) + rdists.get(t))/2.),2);
		}
		return tmp;
	}
	
	private static <V> boolean clustersChanged(ArrayList<Cluster<V>> cur, ArrayList<Cluster<V>> old){
		
		System.out.println("testing");
		
		if(cur.containsAll(old)){
			if(old.containsAll(cur)){
				return false;
			}
		}
		return true;
		
	}
	
	private static <V> double calculateTotalScore(ArrayList<Cluster<V>> clusters, TSM<V> tsm){
		double sum = 0.;
		
		for(int i = 0; i < clusters.size(); i++){
			sum += KCentralCorridors.wcss(tsm, clusters.get(i).getTracksInCorridor(), clusters.get(i).getCentroid());
		}
		
		return sum;
	}
	
	private static <V> void assignClusters(List<Track<V>> tracks, ArrayList<Cluster<V>> clusters, TSM<V> tsm){
		for(Track<V> t : tracks){
			double closestValue = Double.POSITIVE_INFINITY;
			Cluster<V> closestClus = null;
			for(int i = 0; i < clusters.size(); i++){
				//HashMap<Track<V>,Double> dists = clusterDists.get(i);
				if(KCentralCorridors.wcss(tsm, t, clusters.get(i).getCentroid()) < closestValue){
					closestValue = KCentralCorridors.wcss(tsm, t, clusters.get(i).getCentroid());
					closestClus = clusters.get(i);
				}
				//t.getCentroidMap().put(clusters.get(i).getCentroid(), dists.get(t));
			//}
			}
			closestClus.addTrack(t);
		}
	}
	
}
