package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import track.Track;
import track.tsm.TSM;

import edu.uci.ics.jung.graph.Graph;
import graph.SpatialNode;
import graph.WeightedEdge;

public class CaseExperimentVariables<V, E> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2513523772855559903L;
	
	private final int k;
	private final int number_of_nodes;
	private final int number_of_tracks;
	private final Graph<V,E> graph;
	private final ArrayList<Track<V>> routes;
	private final ArrayList<Track<V>> candidates;
	private final TSM<V> tsm;
	
	/**
	 * @param k
	 * @param number_of_nodes
	 * @param number_of_tracks
	 * @param graph
	 * @param routes
	 */
	public CaseExperimentVariables(int k, int number_of_nodes, int number_of_tracks, Graph<V, E> graph,	ArrayList<Track<V>> routes, ArrayList<Track<V>> candidates, TSM<V> tsm) {
		super();
		this.k = k;
		this.number_of_nodes = number_of_nodes;
		this.number_of_tracks = number_of_tracks;
		this.graph = graph;
		this.routes = routes;
		this.tsm = tsm;
		this.candidates = candidates;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getK() {
		return k;
	}

	public int getNumber_of_nodes() {
		return number_of_nodes;
	}

	public int getNumber_of_tracks() {
		return number_of_tracks;
	}

	public Graph<V, E> getGraph() {
		return graph;
	}
	
	
	
	public ArrayList<Track<V>> getCandidates() {
		return candidates;
	}

	public ArrayList<Track<V>> getRoutes() {
		return routes;
	}

	public TSM<V> getTsm() {
		return tsm;
	}

	public static CaseExperimentVariables readInEV(String filename){
		CaseExperimentVariables ev = null;
		try {
			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			ev = (CaseExperimentVariables) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ev;
	}
	
	
	
	@Override
	public String toString() {
		String tmp = "";
		tmp += "Graph size: " + this.getGraph().getVertexCount() + " nodes, " + this.getGraph().getEdgeCount() + " edges\n";
		tmp += "Numer of tracks " + this.getRoutes().size() + " Number of candidates " + this.getCandidates().size() + "\n";
		
		return tmp;
	}

	public static void main(String[] args) {
		CaseExperimentVariables<SpatialNode,WeightedEdge> ev = CaseExperimentVariables.readInEV(args[0]);
		/*System.out.println(ev.getNumber_of_nodes());
		System.out.println(ev.getNumber_of_tracks());
		Random r = new Random();
		System.out.println(((Track) ev.getRoutes().get(r.nextInt(ev.getRoutes().size()))).getNodes().size());
		System.out.println("K" + ev.getK());*/
		
		for(Track<SpatialNode> t : ev.getCandidates()){
			System.out.println(t.toString());
		}
		
	}
	
	
}
