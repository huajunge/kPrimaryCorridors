package test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import kcc.KCentralCorridors;
import kcc.Old_KCentralCorridor;
import kpc.KPCResult;
import kpc.KPrimaryCorridors;


import track.Track;
import track.tsm.GNTS_All;
import track.tsm.METS_All;
import track.tsm.MRTS_All;
import track.tsm.MRTS_JIT;
import track.tsm.TSM;

import graph.SpatialNode;
import graph.ViewGraph;

import graph.WeightedEdge;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;

public class DevTest {

	
	public DevTest() {
		Graph<SpatialNode, WeightedEdge> g;g = new UndirectedSparseGraph<SpatialNode, WeightedEdge>();
	    // Add some vertices. From above we defined these to be type Integer.
		
		SpatialNode zero = new SpatialNode(0);
		SpatialNode one = new SpatialNode(1);
		SpatialNode two = new SpatialNode(2);
		SpatialNode three = new SpatialNode(3);
		SpatialNode four = new SpatialNode(4);
		SpatialNode five = new SpatialNode(5);
		SpatialNode six = new SpatialNode(6);
		SpatialNode seven = new SpatialNode(7);
		SpatialNode eight = new SpatialNode(8);
		SpatialNode nine = new SpatialNode(9);
		SpatialNode ten = new SpatialNode(10);
		SpatialNode eleven = new SpatialNode(11);
		
		ArrayList<SpatialNode> nodeList = new ArrayList<SpatialNode>();
		nodeList.add(zero);
		nodeList.add(one);
		nodeList.add(two);
		nodeList.add(three);
		nodeList.add(four);
		nodeList.add(five);
		nodeList.add(six);
		nodeList.add(seven);
		nodeList.add(eight);
		nodeList.add(nine);
		nodeList.add(ten);
		nodeList.add(eleven);
		
		
		g.addVertex(zero);
		g.addVertex(one);
		g.addVertex(two); 
		g.addVertex(three);
		g.addVertex(four);
		g.addVertex(five); 
		g.addVertex(six);
		g.addVertex(seven);
		g.addVertex(eight); 
		g.addVertex(nine);
		g.addVertex(ten);
		g.addVertex(eleven); 

		
		// Note that the default is for undirected edges, our Edges are Strings.
		
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), zero, one); 
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), zero, four);
		
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), one, two);
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), one, five); 
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), two, three);  
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), two, six); 
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), three, seven);  
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), four, five);
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), four, eight);  
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), five, six);
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), five, nine);  
			
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), six, seven); 
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), six, ten); 
	
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), seven, eleven);  
		
		
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), eight, nine);  
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), nine, ten);  
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), ten, eleven);  
		 
		
		/**DijkstraDistance<Integer,WeightedEdge> alg = new DijkstraDistance<Integer, WeightedEdge>(g,new WeightedEdge.weightTransformer(),false);
		
		HashMap<Integer,Number> maps = new HashMap<Integer,Number>(alg.getDistanceMap(0));
		
		for(Integer i : maps.keySet()){
			System.out.println("The shortest path distance from " + 0 + " to " + i + " is: " + maps.get(i));
		}*/
		
		
		ArrayList<Track<SpatialNode>> routes = new ArrayList<Track<SpatialNode>>();
		SpatialNode[] route1 = {zero, one, two, three};
		SpatialNode[] route2 = {zero, one, two, three};
		SpatialNode[] route3 = {eight, nine, ten, eleven};
		SpatialNode[] route4 = {eight, nine, ten, eleven};
		
		
		
		/*routes.add(new Track<SpatialNode>(0,zero.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(1,one.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(2,two.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(3,three.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(4,four.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(5,five.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(6,six.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(7,seven.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(8,eight.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(9,nine.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(10,ten.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(11,eleven.getSingleNodeRoute()));*/
		
		routes.add(new Track<SpatialNode>(0,new LinkedList<SpatialNode>(java.util.Arrays.asList(route1))));
		routes.add(new Track<SpatialNode>(1,new LinkedList<SpatialNode>(java.util.Arrays.asList(route2))));
		routes.add(new Track<SpatialNode>(2,new LinkedList<SpatialNode>(java.util.Arrays.asList(route3))));
		routes.add(new Track<SpatialNode>(3,new LinkedList<SpatialNode>(java.util.Arrays.asList(route4))));
		
		
		TSM<SpatialNode> tsm = new MRTS_JIT<SpatialNode>(routes,g);
		
		TSM<SpatialNode> tsm2 = new METS_All<SpatialNode>(routes,g);
		tsm2.getTSM().print();
		
		/*for(int i = 0; i < 12; i++){
			double tmp = 0;
			for(int j = 12; j < 15; j++){
				tmp += tsm.getSimilarity(routes.get(j),routes.get(i));
				tmp += tsm.getSimilarity(routes.get(i),routes.get(j));
			}
			nodeList.get(i).setLabel(tmp + "");
			
		}
		*/
		//ViewGraph.visualizeGraph(g);
		
		List<Track<SpatialNode>> candidates = new ArrayList<Track<SpatialNode>>();
		DijkstraShortestPath<SpatialNode,WeightedEdge> dsp = new DijkstraShortestPath<SpatialNode,WeightedEdge>(g);
		ArrayList<SpatialNode> nodes = new ArrayList<SpatialNode>(g.getVertices());
		for(int i = 0; i < g.getVertexCount(); i++){
			for(int j = 0; j < g.getVertexCount(); j++){
				if(i != j){
					Random rand = new Random();
					Track<SpatialNode> tmpTrack = new Track<SpatialNode>(rand.nextInt(500));
					List<WeightedEdge> edges = dsp.getPath(nodes.get(i), nodes.get(j));
					for(WeightedEdge e : edges){
						Pair<SpatialNode> p = g.getEndpoints(e);
						tmpTrack.appendNode(p.getFirst());
						tmpTrack.appendNode(p.getSecond());
					}
					candidates.add(tmpTrack);
				}
			}
		}
		
		//KCentralCorridor kcc = new KCentralCorridor("mrts", routes, g, 1);
		
		KPCResult<SpatialNode> results = KPrimaryCorridors.kprimarycorridor(2, tsm);
		
		List<Track<SpatialNode>> allTracks = new ArrayList<Track<SpatialNode>>();
		allTracks.addAll(routes);
		allTracks.addAll(candidates);
		TSM<SpatialNode> tsmNew = new MRTS_All<SpatialNode>(allTracks, g);
		
		//KCentralCorridors.kCentralCorridors(2, tsmNew, routes, candidates, true, true);
		
		System.out.println(results);
		 
		
		
		
	}
	
	public static void main(String[] args) {
		new DevTest();
	}
}
