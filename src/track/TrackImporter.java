package track;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import graph.SpatialNode;
import graph.WeightedEdge;

import edu.uci.ics.jung.graph.Graph;

public class TrackImporter {
	
	
	public static <E> ArrayList<Track<SpatialNode>> importTrackFull(Graph<SpatialNode,WeightedEdge> g, String filename){
		ArrayList<Track<SpatialNode>> Tracks = new ArrayList<Track<SpatialNode>>();
		
		try {
			Scanner scanLine = new Scanner(new File(filename));
			
			scanLine.nextLine(); //burn header
			
			String oldID = "-1";
			int i = -1;
			
			ArrayList<SpatialNode> nodesInTrack = null;
			
			ArrayList<SpatialNode> array = new ArrayList<SpatialNode>(g.getVertices());
			Collections.sort(array);
			
			while(scanLine.hasNext()){
				Scanner scan = new Scanner(scanLine.nextLine()).useDelimiter(",");
				
				String id = scan.next();
				//System.out.println(id);
				
				if(oldID.equals(id)){
					int source = Integer.parseInt(scan.next());
					
					int dest = Integer.parseInt(scan.next());
					
					SpatialNode src = null;
					SpatialNode dst = null;
					
					
					int source_spot = Collections.binarySearch(array, new SpatialNode(source));
					if(source_spot >= 0){
						src = array.get(source_spot);
					}
					
					int dest_spot = Collections.binarySearch(array, new SpatialNode(dest));
					if(dest_spot >= 0){
						dst = array.get(dest_spot);
					}
					
					nodesInTrack.add(src);
					nodesInTrack.add(dst);
				} else {
					//need to save old Track
					if(nodesInTrack != null){
						i++;
						Track<SpatialNode> r = new Track<SpatialNode>(i);
						r.setLabel(id);
						
						for(int j = 1; j < nodesInTrack.size() - 1; j++){
							if(g.findEdge(nodesInTrack.get(j-1), nodesInTrack.get(j)) == null){
								//cant include this then
							}
							r.appendNode(nodesInTrack.get(j));
						}
						if(!r.getNodes().isEmpty()){
							Tracks.add(r);
						}
						
					}
					
					//begin new Track
					oldID = id;
					
					nodesInTrack = new ArrayList<SpatialNode>();
					
					int source = Integer.parseInt(scan.next());
					
					int dest = Integer.parseInt(scan.next());
					
					SpatialNode src = null;
					SpatialNode dst = null;
							
					for(SpatialNode n : g.getVertices()){
						if(n.getID() == source){
							src = n;
						}
						if(n.getID() == dest){
							dst = n;
						}
					}
					
					nodesInTrack.add(src);
					nodesInTrack.add(dst);
					
				}
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return Tracks;
	}

}
