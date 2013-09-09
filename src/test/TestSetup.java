package test;

import edu.uci.ics.jung.graph.Graph;
import graph.FloydWarshall;
import graph.GraphGenerator;
import graph.WeightedEdge;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import track.Track;
import track.TrackGenerator;

public class TestSetup {
	
	public static void main(String[] args) {
		
		int nodes = Integer.parseInt(args[0]);
		int tracks = Integer.parseInt(args[1]);
		int track_length = Integer.parseInt(args[2]);
		int k = Integer.parseInt(args[3]);
		
		int edges = (int) Math.floor((nodes * 3));
		
		System.out.println("Create graph");
		Graph<Integer, WeightedEdge> g = GraphGenerator.createUndirectedSparseGraph(nodes, edges);
		System.out.println("Nodes:" + g.getVertexCount() + " Edges:" + g.getEdgeCount());
		
		
		System.out.println("Create routes");
		ArrayList<Track<Integer>> routes = TrackGenerator.generateRoutes(g, tracks, track_length);
		
		FloydWarshall<Integer, WeightedEdge> fw = new FloydWarshall<Integer, WeightedEdge>(g);
		
		System.out.println("Creating EX object");
		ExperimentVariables<Integer, WeightedEdge> ev = new ExperimentVariables<Integer, WeightedEdge>(k, nodes, tracks, g, routes, fw);
		
		
		System.out.println("Saving EX object...");
		try {
			FileOutputStream fileOut = new FileOutputStream("Syn-" + nodes + "n-" + tracks + "t-" + track_length + "l-" + k + "k.vars");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(ev);
			out.close();
		} catch(IOException e){
			e.printStackTrace();
		}
		
		System.out.println("Saved: " + "Syn-" + nodes + "n-" + tracks + "t-" + track_length + "l-" + k + "k.vars");
		
	}

}
