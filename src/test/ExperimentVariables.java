package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import graph.FloydWarshall;
import track.Track;

import edu.uci.ics.jung.graph.Graph;

public class ExperimentVariables<V, E> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2513523772855559903L;
	
	private final int k;
	private final int number_of_nodes;
	private final int number_of_tracks;
	private final Graph<V,E> graph;
	private final ArrayList<Track<Integer>> routes;
	private final FloydWarshall<V,E> fw_dists;
	
	/**
	 * @param k
	 * @param number_of_nodes
	 * @param number_of_tracks
	 * @param graph
	 * @param routes
	 */
	public ExperimentVariables(int k, int number_of_nodes,
			int number_of_tracks, Graph<V, E> graph,
			ArrayList<Track<Integer>> routes, FloydWarshall<V,E> fw_dists) {
		super();
		this.k = k;
		this.number_of_nodes = number_of_nodes;
		this.number_of_tracks = number_of_tracks;
		this.graph = graph;
		this.routes = routes;
		this.fw_dists = fw_dists;
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

	public ArrayList<Track<Integer>> getTracks() {
		return routes;
	}
	
	


	public FloydWarshall<V, E> getFw_dists() {
		return fw_dists;
	}

	public static ExperimentVariables readInEV(String filename){
		ExperimentVariables ev = null;
		try {
			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			ev = (ExperimentVariables) in.readObject();
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
	
	public static void main(String[] args) {
		ExperimentVariables ev = ExperimentVariables.readInEV(args[0]);
		System.out.println(ev.getNumber_of_nodes());
		System.out.println(ev.getNumber_of_tracks());
		Random r = new Random();
		System.out.println(((Track) ev.getTracks().get(r.nextInt(ev.getTracks().size()))).getNodes().size());
		System.out.println("K" + ev.getK());
		
		
	}
	
	
}
