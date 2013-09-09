package kcc;

import edu.uci.ics.jung.graph.Graph;
import graph.SpatialNode;
import graph.WeightedEdge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import kpc.KPCResult;

import track.Track;
import track.tsm.GNTS_All;
import track.tsm.METS_All;
import track.tsm.MRTS_JIT;
import track.tsm.TSM;

public class Old_KCentralCorridor {
	
	private List<Track<SpatialNode>> tracks;
	private Graph<SpatialNode,WeightedEdge> g;
	private int k;
	private ArrayList<LinkedList<Track<SpatialNode>>> clusters;
	private ArrayList<Track<SpatialNode>> centracks;
	private int trackCount;
	private String tsm_type;
	
	/**
	 * @param tracks
	 * @param g
	 * @param k
	 */
	public Old_KCentralCorridor(String tsm_type, List<Track<SpatialNode>> tracks, Graph<SpatialNode, WeightedEdge> g, int k) {
		super();
		this.tracks = tracks;
		this.g = g;
		this.k = k;
		this.tsm_type = tsm_type;
		
		//create cluster array and fill with empty lists
		clusters = new ArrayList<LinkedList<Track<SpatialNode>>>();
		for(int i = 0; i < k; i++){
			clusters.add(new LinkedList<Track<SpatialNode>>());
		}
		
		//create medoid array with empty routes so we can 'set' them late
		centracks = new ArrayList<Track<SpatialNode>>(k);	
		for(int i = 0; i < k; i++){ //this might be redundant
			centracks.add(null);
		}
		
		trackCount = tracks.size()+1;
				
	}
	
	/**
	 * 
	 * @return
	 */
	public KPCResult<SpatialNode> run(){
		
		/**
		 * Place K points into the space represented by the objects that are being clustered. These points represent initial group centroids.
		 * Assign each object to the group that has the closest centroid.
		 * When all objects have been assigned, recalculate the positions of the K centroids.
		 * Repeat Steps 2 and 3 until the centroids no longer move. This produces a separation of the objects into groups from which the metric to be minimized can be calculated.
		 */
		this.initialize();
		
		ArrayList<Track<SpatialNode>> tmpCentracks = new ArrayList<Track<SpatialNode>>(k);
		
		//currentClusters
		//make new clusters
		//check if equal
		
		while(!this.checkIfCentracksEqual(tmpCentracks)){
			this.assignTrajectories();
			tmpCentracks = (ArrayList<Track<SpatialNode>>) centracks.clone();
			this.enumerateCentracks();
		}
		
		
		//on exit, we now have clusters and have found centracks
		
		for(int i = 0; i < k; i++){
			Collections.sort(tmpCentracks.get(i).getNodes());
			System.out.println("cluster: " + centracks.get(i).toString() +  " \n " + clusters.get(i).size() + " " + tmpCentracks.get(i).getNodes().toString());
		}
		
		
		return new KPCResult<SpatialNode>(centracks, clusters);
		
		
	}
	
	
	/**
	 *  (randomly) select k new central corridors, set centracks array
	 * @return
	 */
	private void initialize(){
		
		//random? start with yes
		
		ArrayList<SpatialNode> nodes = new ArrayList<SpatialNode>(g.getVertices());
		
		java.util.Random rand = new java.util.Random();
		
		//set random nodes as initial centracks
		for(int i = 0; i < k; i++){
			LinkedList<SpatialNode> tmpList = new LinkedList<SpatialNode>();
			tmpList.add(nodes.get(rand.nextInt(nodes.size())));
			centracks.set(i, new Track<SpatialNode>(trackCount++,tmpList));
		}

	}
	
	/**
	 * assign trajectories to clusters based on centracks array
	 * @return 
	 */
	private void assignTrajectories(){
		
		clusters.clear();
		for(int i = 0; i < k; i++){
			clusters.add(new LinkedList<Track<SpatialNode>>());
		}
		
		//basically need to calculate distance from each corridor to all tracks (and vise versa)
		//so it is like a c x t matrix...
		//TSM<SpatialNode> tsm = this.getNewTSM(tracks,centracks);
		
		for(Track<SpatialNode> t : tracks){
			
			//double tmp = this.avgHausdorffDistance(t, centracks);
			
			//assign t to closest centrack
			double min = Double.MAX_VALUE;
			int winner = -1;
			for(int i = 0; i < k; i++){
				//List tmpList = new LinkedList();
				//tmpList.add(centracks.get(i));
				double tmp = this.getDistance(centracks.get(i), t, g);
				if(tmp < min){
					winner = i;
					min = tmp;
				}
			}
			clusters.get(winner).add(t);
		}
		
		//check for empty clusters
		for(int i = 0; i < k; i++){
			if(clusters.get(i).isEmpty()){
				//crap, add a random track?
				Random rand = new Random();
				Track rndTrack = tracks.get(rand.nextInt(tracks.size()));
				for(List<Track<SpatialNode>> l : clusters){
					l.remove(rndTrack);
				}
				clusters.get(i).add(rndTrack);
			}
		}
		
	}
	
	/**
	 * given tracks in cluster, find new centracks
	 * do we start over with a single node?
	 * in naive mode, calc avg-haus-dist from each
	 * 
	 * TODO allow for endpoint removal as well, more in the k means style
	 * 
	 * @return centracks
	 */
	private void enumerateCentracks(){
		
		 //ArrayList<Track<SpatialNode>> newCenTracks = new ArrayList<Track<SpatialNode>>(k);
		 
		 //for each cluster, find new centrack
		 
		 for(int i = 0; i < clusters.size(); i++){
			 final LinkedList<Track<SpatialNode>> list = clusters.get(i);
			 //find start node
			 double min = Double.MAX_VALUE;
			 Track<SpatialNode> startTrack = null;
			 ArrayList<SpatialNode> verticies = new ArrayList<SpatialNode>(g.getVertices());
			 
			 PriorityQueue<Track<SpatialNode>> queue = new PriorityQueue<Track<SpatialNode>>(50,
					 			new Comparator<Track<SpatialNode>>() {
				 						public int compare(Track<SpatialNode> t1, Track<SpatialNode> t2){
				 							 double tmpDistt1 = 0.;
				 							 double tmpDistt2 = 0.;
				 							 for(Track<SpatialNode> z : list){
				 								tmpDistt1 += Old_KCentralCorridor.getDistance(t1, z, g);
				 							 }
				 							for(Track<SpatialNode> z : list){
				 								tmpDistt2 += Old_KCentralCorridor.getDistance(t2, z, g);
				 							 }
				 							
				 							if(tmpDistt1 < tmpDistt2){
				 								return 1;
				 							} else if(tmpDistt1 > tmpDistt2){
				 								return -1;
				 							} else {
				 								return 0;
				 							}
				 							
				 						}
			 });
			 
			 for(SpatialNode c : verticies){
				 double tmp = 0.;
				 //we need to find which node to start the enumeration at
				 //to do this, we make each node its own track
				 //ArrayList<Track<SpatialNode>> tmpRoute = new ArrayList<Track<SpatialNode>>();
				 
				 Track<SpatialNode> tmpTrack = new Track<SpatialNode>(trackCount++,c.getSingleNodeRoute());
				 tmpTrack.setStartNode(c);
				 //queue.add(tmpTrack); //for testing purposes
				 
				 //this must be returning infin 
				 for(Track<SpatialNode> t : list){
					 tmp += this.getDistance(tmpTrack, t, g);
				 }
				 
				 if(tmp <= min){
					 min = tmp;
					 startTrack = tmpTrack;
				 }
				
			 } //we now have, for this cluster, a start node
			 //now we start enumerating this start node until similarity goes no longer decreasing
			 
			 
			 queue.add(startTrack);
			 Track<SpatialNode> winner = startTrack;
			 System.out.println(winner.getNodes().toString());
			 double curScore = 0.;
			 
			 for(Track<SpatialNode> t : list){
				 curScore += this.getDistance(winner, t, g);
			 }
			 
			 
			 while(!queue.isEmpty() && Math.random() < .95){
				 Track<SpatialNode> tmpTrack = queue.poll();
				 
				 //enumerate possible new centracks (find endpoints, add neighbors, create a list of neighbors
				 for(Track<SpatialNode> t : this.enumerateNewCentracks(tmpTrack)){
					 double tmpDist = 0.;
					 for(Track<SpatialNode> z : list){
						 tmpDist += this.getDistance(z, t, g);
					 }
					 if(tmpDist <= curScore){
						 curScore = tmpDist;
						 //System.out.println(curScore);
						 queue.add(t);
						 winner = t;
					 }
				 }
			 }
			 centracks.set(i,winner);
			 System.out.println("Winner: " + i + " "  + winner.getNodes().toString());
		 }
		
		
	}
	
	private List<Track<SpatialNode>> enumerateNewCentracks(Track<SpatialNode> track){
		LinkedList<Track<SpatialNode>> list = new LinkedList<Track<SpatialNode>>();
		
		if(track.getStartNode() == null){
			return null; //this is bad
		}
		
		if(track.getEndNode() == null){
			//must be a 1 node track right now, enumerate in all directions
			SpatialNode startNode = track.getStartNode();
			
			//enumerate neighbors of start node
			ArrayList<SpatialNode> neighbors = new ArrayList<SpatialNode>(g.getNeighbors(startNode));
			
			for(SpatialNode neigh : neighbors){
				LinkedList<SpatialNode> tmpList = new LinkedList<SpatialNode>();
				tmpList.add(startNode);
				tmpList.add(neigh);
				Track<SpatialNode> tmpTrack = new Track<SpatialNode>(trackCount++,tmpList);
				tmpTrack.setStartNode(startNode);
				tmpTrack.setEndNode(neigh);
				
				list.add(tmpTrack);
			}
			
			
			 
		} else {
			//this is the 'normal' case, we need to enumerate in both directions
			
			SpatialNode startNode = track.getStartNode();
			SpatialNode endNode = track.getEndNode();
			//enumerate neighbors of start node
			ArrayList<SpatialNode> startNeighbors = new ArrayList<SpatialNode>(g.getNeighbors(startNode));
			
			for(SpatialNode neigh : startNeighbors){
				if(!track.getNodes().contains(neigh)){
					/*LinkedList<SpatialNode> tmpList = new LinkedList<SpatialNode>();
					tmpList.addAll(track.getNodes());
					tmpList.add(neigh);
					Track<SpatialNode> tmpTrack = new Track<SpatialNode>(tracks.size()+1,tmpList);
					tmpTrack.setStartNode(neigh);
					tmpTrack.setEndNode(endNode);*/
					
					list.add(this.generateNewTrackItem(track, neigh, neigh, endNode));
				}	
			}
			
			ArrayList<SpatialNode> endNeighbors = new ArrayList<SpatialNode>(g.getNeighbors(endNode));
			
			for(SpatialNode neigh : endNeighbors){
				if(!track.getNodes().contains(neigh)){
					
					/*LinkedList<SpatialNode> tmpList = new LinkedList<SpatialNode>();
					tmpList.addAll(track.getNodes());
					tmpList.add(neigh);
					Track<SpatialNode> tmpTrack = new Track<SpatialNode>(trackCount++,tmpList);
					tmpTrack.setStartNode(startNode);
					tmpTrack.setEndNode(neigh);*/
					
					list.add(this.generateNewTrackItem(track, neigh, startNode, neigh));
				}	
			}
			
			//also remove start node for shits
			LinkedList<SpatialNode> tmpList = new LinkedList<SpatialNode>();
			tmpList.addAll(track.getNodes());
			tmpList.remove(startNode);
			Track<SpatialNode> tmpTrack = new Track<SpatialNode>(trackCount++,tmpList);
			if(!tmpList.isEmpty()){
				tmpTrack.setStartNode(tmpList.getFirst());
				tmpTrack.setEndNode(endNode);
				
				list.add(tmpTrack);
			}
			
			
			
		}
		
		
		return list;
	}
	
	private Track<SpatialNode> generateNewTrackItem(Track<SpatialNode> track, SpatialNode neigh, SpatialNode start, SpatialNode end){
		LinkedList<SpatialNode> tmpList = new LinkedList<SpatialNode>();
		tmpList.addAll(track.getNodes());
		tmpList.add(neigh);
		Track<SpatialNode> tmpTrack = new Track<SpatialNode>(trackCount++,tmpList);
		tmpTrack.setStartNode(start);
		tmpTrack.setEndNode(end);
		
		return tmpTrack;
	}
	
	private boolean checkIfCentracksEqual(ArrayList<Track<SpatialNode>> newTracks){
		
		boolean cor = true;
		
		if(newTracks.size() != centracks.size()){
			//System.out.println("new tracks and centracks different sizes");
			return false;
		}
		
		for(Track<SpatialNode> t : centracks){
			if(!newTracks.contains(t)){
				return false;
			}
		}
		
		return cor;
	}
	

	private TSM<SpatialNode> getNewTSM(Collection<Track<SpatialNode>> inputTracks, Collection<Track<SpatialNode>> newCenter){
		//if(tsm_type.equals("MRTS")){
			ArrayList<Track<SpatialNode>> union = new ArrayList<Track<SpatialNode>>(inputTracks);
			union.addAll(newCenter);
			//System.out.println(inputTracks.size() + " " + newCenter.size());
			return new METS_All<SpatialNode>(union,g);
		//}
	}
	
	/**
	 * 
	 * this might be causing problems, why cant we just compare straight track to track?
	 * 
	 * @param curTrack
	 * @param curCluster
	 * @return
	 */
	/*private double avgHausdorffDistance(Track<SpatialNode> curTrack, List<Track<SpatialNode>> curCluster){
		
		 if(curCluster.isEmpty()){
			 return Double.MAX_VALUE;
		 }
		double tmp = 0.;
		
		 ArrayList<Track<SpatialNode>> tmpRoute = new ArrayList<Track<SpatialNode>>();
		 tmpRoute.add(curTrack); 
		 TSM<SpatialNode> tsm = this.getNewTSM(curCluster, tmpRoute);
		 
		 tsm.getSimilarity(curCluster, curTrack); //force compute, stored for retrieval
		 tsm.getSimilarity(curTrack, curCluster); //same as above
		 
		 for(Track<SpatialNode> t : curCluster){
			 //weak Frechet distance is max of NHD
			 tmp += Math.max(tsm.getSimilarity(t,curTrack), tsm.getSimilarity(curTrack, t));
		 }
		 
		 tsm.getTSM().print();
		 
		 //System.out.println(curTrack.getNodes().toString() + "  " + tmp);
		 
		 return tmp;
		 
	}*/
	
	public static double getDistance(Track<SpatialNode> first, Track<SpatialNode> second, Graph<SpatialNode,WeightedEdge> g){
		
		if(first == null || second == null){
			return Double.MAX_VALUE;
		}
		
		double tmp = 0.;
		
		ArrayList<Track<SpatialNode>> union = new ArrayList<Track<SpatialNode>>();
		
		union.add(first);
		union.add(second);
		
		//TODO this needs to reflect other one
		TSM<SpatialNode> tsm = new METS_All<SpatialNode>(union,g);
		
		//approximate weak frechet distance
		tmp += Math.max(tsm.getSimilarity(first,second), tsm.getSimilarity(second, first));
		
		//tsm.getTSM().print();
		
		//System.out.println(first.getLabel() + "-" + second.getLabel() + ": " + tmp);
		
		return tmp;
		
		
	}

}


