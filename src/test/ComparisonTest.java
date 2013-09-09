package test;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;
import graph.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kpc.KPCResult;
import kpc.KPrimaryCorridors;

import track.*;
import track.tsm.*;



public class ComparisonTest {

	
	public ComparisonTest(Integer nodes, Integer numRoutes){
		

		int edges = (int) Math.floor((nodes * 3));
		
		int k = 5;
		
		System.out.println("Create graph");
		Graph<Integer, WeightedEdge> g = GraphGenerator.createUndirectedSparseGraph(nodes, edges);
		System.out.println("Nodes:" + g.getVertexCount() + " Edges:" + g.getEdgeCount());
		
		System.out.println("Create routes");
		ArrayList<Track<Integer>> routes = TrackGenerator.generateRoutes(g, numRoutes);
		
		
		
		FloydWarshall<Integer,WeightedEdge> fw = new FloydWarshall<Integer,WeightedEdge>(g);
		//double[][] v_dists = fw.getDistanceMatrix();
		//double[][] r_dists = RouteCompare.getRouteDistanceMatrix_FW(routes, v_dists);
		
		
		System.out.println("Creating TSM - GNTS_ALL");
		TSM<Integer> gnts = new GNTS_All<Integer>(routes,g);
		
		System.out.println("Creating TSM - GNTS_ALL_FW");
		TSM<Integer> mets = new GNTS_All_FW<Integer>(routes,g,fw);
		
		System.out.println("Creating TSM - MRTS_ALL");
		TSM<Integer> mrts = new MRTS_All<Integer>(routes,g);
		
		System.out.println("Comparing...");
		
		if(gnts.compareTo(mets) != 0 || gnts.compareTo(mrts) != 0){
			System.out.println("Route distances not equal!!-------------------------------------------------");
			System.out.println(gnts.compareTo(mets));
			System.out.println(gnts.compareTo(mrts));
			gnts.getTSM().print();
			mets.getTSM().print();
			mrts.getTSM().print();
			System.exit(-1);
		}
		
		System.out.println("TSM's are equal.");
		
		System.out.println("Computing kPC - GNTS");
		KPCResult<Integer> results1 = KPrimaryCorridors.kprimarycorridor(k, gnts);
		System.out.println("Computing kPC - METS");
		KPCResult<Integer> results2 = KPrimaryCorridors.kprimarycorridor(k, mets);
		System.out.println("Computing kPC - MRTS");
		KPCResult<Integer> results3 = KPrimaryCorridors.kprimarycorridor(k, mrts);
		
		System.out.println("Computing kPC - Doing it Live - GNTS_JIT");
		TSM<Integer> gnts_jit = new GNTS_JIT<Integer>(routes,g);
		KPCResult<Integer> results4 = KPrimaryCorridors.kprimarycorridor(k, gnts_jit);
		System.out.println("Computing kPC - Doing it Live - METS_JIT");
		TSM<Integer> mets_jit = new METS_JIT<Integer>(routes,g);
		KPCResult<Integer> results5 = KPrimaryCorridors.kprimarycorridor(k, mets_jit);
		System.out.println("Computing kPC - Doing it Live - MRTS_JIT");
		TSM<Integer> mrts_jit = new MRTS_JIT<Integer>(routes,g);
		KPCResult<Integer> results6 = KPrimaryCorridors.kprimarycorridor(k, mrts_jit);
		
		
		//ViewGraph.visualizeGraph(g, routes, resultsNaive, false);
		
		if(results1.equals(results2) && results1.equals(results3) && results1.equals(results4) && results1.equals(results5) && results1.equals(results6)){
			System.out.println("kPC results are equal");
		} else {
			System.err.println("not equals!----------------------------------------------------------------");
			System.err.println("GNTS_ALL\n" + results1.toString());
			System.err.println("GNTS_JIT\n" + results4.toString());
			System.err.println("METS_JIT\n" + results5.toString());
			System.err.println("MRTS_JIT\n" + results6.toString());
		}
		
		System.out.println("--Done--");
		
	}
	
	
	
	public static void main(String[] args) {
		new ComparisonTest(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
	}
	
}
