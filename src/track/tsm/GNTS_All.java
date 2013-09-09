package track.tsm;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.graph.Graph;
import graph.WeightedEdge;

import java.util.HashMap;
import java.util.List;

import matrix.FalseMatrix;
import matrix.FullMatrix;
import matrix.SparseMatrix;

import track.Track;



public class GNTS_All<V> extends TSM_All<V> {




	public GNTS_All(List<Track<V>> tracks, Graph<V, WeightedEdge> g) {
		super(tracks, g);
		this.computeAllSimilarity(tracks);
	}

	/**
	 * avg_min Distance
	 */
	@Override
	protected void computeAllSimilarity(List<Track<V>> tracks) {
		
		r_dists = new FullMatrix(tracks.size(),tracks.size());
		
		for(int i = 0; i < tracks.size(); i++){
			for(int j = 0; j < tracks.size(); j++){
				if(j == i){
					r_dists.set(tracks.get(i), tracks.get(j), 0.);
				} else {
					//double sumDists = 0.;
					double max = Double.NEGATIVE_INFINITY; //hoff distance
					for(V v : tracks.get(i).getNodes()){
						DijkstraDistance<V,WeightedEdge> alg = new DijkstraDistance<V, WeightedEdge>(g,new WeightedEdge.weightTransformer(),false);
						HashMap<V, Number> distmap = new HashMap<V, Number>(alg.getDistanceMap(v,tracks.get(j).getNodes()));
						double min = Double.POSITIVE_INFINITY;
						for(V r : tracks.get(j).getNodes()){  //returns min dist from r in track j to v in track i
							double tmp = (Double) distmap.get(r);
							if(min > tmp){
								min = tmp;
							}
							
						}
						if(max < min){
							max = min;
						}
						//sumDists += min;
						
					}
					r_dists.set(tracks.get(i), tracks.get(j),max); //Math.pow(sumDists / tracks.get(i).getNodes().size(),2));
				}
			}
		}
		
		
	}

	
	
}
