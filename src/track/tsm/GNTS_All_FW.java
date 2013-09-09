package track.tsm;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.graph.Graph;
import graph.WeightedEdge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import matrix.FalseMatrix;
import matrix.FullMatrix;
import matrix.SparseMatrix;

import graph.FloydWarshall;
import track.Track;



public class GNTS_All_FW<V> extends TSM_All<V> {


	private FloydWarshall<V,WeightedEdge> dists;


	public GNTS_All_FW(List<Track<V>> tracks, Graph<V, WeightedEdge> g, FloydWarshall<V,WeightedEdge> dists) {
		super(tracks, g);
		this.dists = dists;
		this.computeAllSimilarity(tracks);
	}

	/**
	 * avg_min Distance
	 */
	
	protected void computeAllSimilarity(List<Track<V>> tracks) {
		
		r_dists = new FullMatrix(tracks.size(),tracks.size());
		
		final int sizeOfTracks = tracks.size();
		
		for(int i = 0; i < sizeOfTracks; i++){
			for(int j = 0; j < sizeOfTracks; j++){
				if(j == i){
					r_dists.set(tracks.get(i), tracks.get(j), 0.);
				} else {
					double max = Double.NEGATIVE_INFINITY; //hoff distance
					for(V v : tracks.get(i).getNodes()){
						double min = Double.POSITIVE_INFINITY;
						for(V r : tracks.get(j).getNodes()){  //returns min dist from r in track j to v in track i
							double tmp = (Double) dists.getDistance(v, r);
							if(min > tmp){
								min = tmp;
							}
							
						}
						if(max < min){
							max = min;
						}
						
						
					}
					r_dists.set(tracks.get(i), tracks.get(j),max); 
				}
			}
		}
		
		
	}


	

	
	
}
