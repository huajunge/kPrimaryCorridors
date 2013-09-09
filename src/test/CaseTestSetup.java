package test;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;
import graph.GraphGenerator;
import graph.GraphMLImport;
import graph.SpatialNode;
import graph.WeightedEdge;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import track.Track;
import track.TrackGenerator;
import track.TrackImporter;
import track.tsm.MRTS_All;
import track.tsm.MRTS_JIT;
import track.tsm.TSM;

public class CaseTestSetup {
	
	public static void main(String[] args) {
				
		Graph<SpatialNode, WeightedEdge> g = GraphMLImport.importSpatialFile(args[0], args[1]);
		
		System.out.println("Nodes: " + g.getVertexCount());
		System.out.println("Edges: " + g.getEdgeCount());
		
		System.out.println("Importing routes: " + args[2]);
		ArrayList<Track<SpatialNode>> routes = TrackImporter.importTrackFull(g, args[2]);
		System.out.println("Number of routes: " + routes.size());
		
		ArrayList<Track<SpatialNode>> candidates = new ArrayList<Track<SpatialNode>>();
		DijkstraShortestPath<SpatialNode,WeightedEdge> dsp = new DijkstraShortestPath<SpatialNode,WeightedEdge>(g,false);
		ArrayList<SpatialNode> nodes = new ArrayList<SpatialNode>(g.getVertices());
		
		int numberOfRoadsAllowed = Integer.parseInt(args[4]);
		
		int candidateNumber = 5000;
		System.out.println("Generating shortest paths...");
		Random rand = new Random();
		for(int i = 0; i < g.getVertexCount(); i++){
		
			if(candidateNumber < 0){
				break;
			}
			for(int j = 0; j < g.getVertexCount(); j++){
				if(i != j){
					Track<SpatialNode> tmpTrack = new Track<SpatialNode>(rand.nextInt(5000000));
					List<WeightedEdge> edges = dsp.getPath(nodes.get(i), nodes.get(j));
					HashSet<String> roadCount = new HashSet<String>();
					for(WeightedEdge e : edges){
						Pair<SpatialNode> p = g.getEndpoints(e);
						tmpTrack.appendNode(p.getFirst());
						tmpTrack.appendNode(p.getSecond());
						roadCount.add(e.getLabel());
					}
					
					if(roadCount.size() <= numberOfRoadsAllowed && tmpTrack.getNodes().size() > 15){
						candidates.add(tmpTrack);
						candidateNumber--;
					}
					
				}
			}
		}
		
		System.out.println("Number of SP's: " + candidates.size());
		
		System.out.println("Computing TSM...");
		List<Track<SpatialNode>> allTracks = new ArrayList<Track<SpatialNode>>();
		allTracks.addAll(routes);
		allTracks.addAll(candidates);
		TSM<SpatialNode> tsm = new MRTS_All<SpatialNode>(allTracks, g);
		
		System.out.println("Routes: " + routes.size());
		
		int k = Integer.parseInt(args[3]);
		
		CaseExperimentVariables<SpatialNode, WeightedEdge> ev = new CaseExperimentVariables<SpatialNode, WeightedEdge>(k, g.getVertexCount(), routes.size(), g, routes, candidates, tsm);
		
		System.out.println("Saving EX object...");
		String filename = "NHD-" + numberOfRoadsAllowed + "-" + candidateNumber + "caseStudy.vars";
		try { 
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(ev);
			out.close();
		} catch(IOException e){
			e.printStackTrace();
		}
		
		System.out.println("Saved: " + filename);
		
		
	}

}
