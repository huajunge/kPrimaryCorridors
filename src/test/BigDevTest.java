package test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import kcc.Cluster;
import kcc.KCCResult;
import kcc.KCentralCorridors;
import kcc.Old_KCentralCorridor;
import kpc.KPCResult;
import kpc.KPrimaryCorridors;


import track.Track;
import track.tsm.GNTS_All;
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

public class BigDevTest {

	
	public BigDevTest() {
		Graph<SpatialNode, WeightedEdge> g;g = new UndirectedSparseGraph<SpatialNode, WeightedEdge>();
	    // Add some vertices. From above we defined these to be type Integer.
		
		
		SpatialNode one = new SpatialNode(1, "");
		SpatialNode two = new SpatialNode(2, "");
		SpatialNode three = new SpatialNode(3, "");
		SpatialNode four = new SpatialNode(4, "");
		SpatialNode five = new SpatialNode(5, "");
		SpatialNode six = new SpatialNode(6, "");
		SpatialNode seven = new SpatialNode(7, "");
		SpatialNode eight = new SpatialNode(8, "");
		SpatialNode nine = new SpatialNode(9, "");
		SpatialNode ten = new SpatialNode(10, "");
		SpatialNode eleven = new SpatialNode(11, "");
		SpatialNode twelve = new SpatialNode(12, "");
		SpatialNode thirteen = new SpatialNode(13, "");
		SpatialNode fourteen = new SpatialNode(14, "");
		SpatialNode fifteen = new SpatialNode(15, "");
		SpatialNode sixteen = new SpatialNode(16, "");
		SpatialNode seventeen = new SpatialNode(17, "");
		SpatialNode eighteen = new SpatialNode(18, "");
		SpatialNode nineteen = new SpatialNode(19, "");
		SpatialNode twenty = new SpatialNode(20, "");
		
		ArrayList<SpatialNode> nodeList = new ArrayList<SpatialNode>();
		nodeList.add(twenty);
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
		nodeList.add(twelve);
		nodeList.add(thirteen);
		nodeList.add(fourteen);
		nodeList.add(fifteen);
		nodeList.add(sixteen);
		nodeList.add(seventeen);
		nodeList.add(eighteen);
		nodeList.add(nineteen);
		
		
		g.addVertex(twenty);
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
		g.addVertex(twelve);
		g.addVertex(thirteen);
		g.addVertex(fourteen);
		g.addVertex(fifteen);
		g.addVertex(sixteen);
		g.addVertex(seventeen);
		g.addVertex(eighteen);
		g.addVertex(nineteen);

		
		// Note that the default is for undirected edges, our Edges are Strings.
		
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), one, two); 
		g.addEdge(new WeightedEdge(g.getEdgeCount(),2.), one, six);
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), two, three);
		g.addEdge(new WeightedEdge(g.getEdgeCount(),2.), two, seven);
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), three, four);
		//g.addEdge(new WeightedEdge(g.getEdgeCount(),20.), three, eight);
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), four, five);
		g.addEdge(new WeightedEdge(g.getEdgeCount(),2.), four, nine);
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),2.), five, ten);
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), six, seven);
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), six, eleven);
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), seven, eight);
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), seven, twelve);
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), eight, nine);
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), eight, thirteen);
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), nine, ten);
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), nine, fourteen);
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), ten, fifteen);
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), eleven, twelve);
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), eleven, sixteen);
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), twelve, thirteen);
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), twelve, seventeen);
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), thirteen, fourteen);
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), thirteen, eighteen);
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), fourteen, fifteen);
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), fourteen, nineteen);
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), fifteen, twenty);
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), sixteen, seventeen);
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), seventeen, eighteen);
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), eighteen, nineteen);
		
		g.addEdge(new WeightedEdge(g.getEdgeCount(),1.), nineteen, twenty);
		
		 
		
		/**DijkstraDistance<Integer,WeightedEdge> alg = new DijkstraDistance<Integer, WeightedEdge>(g,new WeightedEdge.weightTransformer(),false);
		
		HashMap<Integer,Number> maps = new HashMap<Integer,Number>(alg.getDistanceMap(0));
		
		for(Integer i : maps.keySet()){
			System.out.println("The shortest path distance from " + 0 + " to " + i + " is: " + maps.get(i));
		}*/
		
		
		ArrayList<Track<SpatialNode>> routes = new ArrayList<Track<SpatialNode>>();
		SpatialNode[] routeC = {sixteen, seventeen, eighteen, nineteen, twenty, fifteen, ten};
		SpatialNode[] routeB = {one, two, three, four, nine, ten};
		SpatialNode[] routeD = {six, seven, eight, nine, ten};
		SpatialNode[] routeA = {sixteen, seventeen, eighteen, nineteen, twenty};
		
		
		/*routes.add(new Track<SpatialNode>(0,one.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(1,two.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(2,three.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(3,four.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(4,five.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(5,six.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(6,seven.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(7,eight.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(8,nine.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(9,ten.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(10,eleven.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(11,twelve.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(12,thirteen.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(13,fourteen.getSingleNodeRoute()));
		routes.add(new Track<SpatialNode>(14,fifteen.getSingleNodeRoute()));*/
		
		Track<SpatialNode> tA = new Track<SpatialNode>(0,new LinkedList<SpatialNode>(java.util.Arrays.asList(routeA)));
		Track<SpatialNode> tB = new Track<SpatialNode>(1,new LinkedList<SpatialNode>(java.util.Arrays.asList(routeB)));
		Track<SpatialNode> tC = new Track<SpatialNode>(2,new LinkedList<SpatialNode>(java.util.Arrays.asList(routeC)));
		Track<SpatialNode> tD = new Track<SpatialNode>(3,new LinkedList<SpatialNode>(java.util.Arrays.asList(routeD)));
		
		routes.add(tA);
		routes.add(tB);
		routes.add(tC);
		routes.add(tD);
		
		
		//TSM<SpatialNode> tsm2 = new GNTS_All<SpatialNode>(routes,g);
		
		//TSM<SpatialNode> tsm2 = new MRTS_All<SpatialNode>(routes,g);
		//tsm2.getTSM().print();
		
		/*for(int i = 0; i < 15; i++){
			double tmp = 0;
			for(int j = 15; j < 19; j++){
				
				tmp += tsm.getSimilarity(routes.get(j),routes.get(i));
				tmp += tsm.getSimilarity(routes.get(i),routes.get(j));
				System.out.println(i + " " + j + " " + tmp);
			}
			nodeList.get(i).setLabel(tmp + "");
			
		}*/
		
		

		/*List<Track<SpatialNode>> candidates = new ArrayList<Track<SpatialNode>>();
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
		}*/
		
		List<Track<SpatialNode>> allTracks = new ArrayList<Track<SpatialNode>>();
		allTracks.addAll(routes);
		//allTracks.addAll(candidates);
		TSM<SpatialNode> tsmNew = new MRTS_All<SpatialNode>(allTracks, g);
		
		
		
		//KCCResult<SpatialNode> kcc = KCentralCorridors.kCentralCorridors(2, g, tsmNew, routes, candidates, true, true);
		
		//System.out.println(kcc.toString());
		
		//tsmNew.getTSM().print();
		
		System.out.println(tsmNew.getSimilarity(tA, tA) + " " + 
				tsmNew.getSimilarity(tA, tB) + " " + 
				tsmNew.getSimilarity(tA, tC) + " " + 
				tsmNew.getSimilarity(tA, tD));
		System.out.println(tsmNew.getSimilarity(tB, tA) + " " + 
				tsmNew.getSimilarity(tB, tB) + " " + 
				tsmNew.getSimilarity(tB, tC) + " " + 
				tsmNew.getSimilarity(tB, tD));
		System.out.println(tsmNew.getSimilarity(tC, tA) + " " + 
				tsmNew.getSimilarity(tC, tB) + " " + 
				tsmNew.getSimilarity(tC, tC) + " " + 
				tsmNew.getSimilarity(tC, tD));
		System.out.println(tsmNew.getSimilarity(tD, tA) + " " + 
				tsmNew.getSimilarity(tD, tB) + " " + 
				tsmNew.getSimilarity(tD, tC) + " " + 
				tsmNew.getSimilarity(tD, tD));
		
		
		/*for(Cluster<SpatialNode> c : kcc.getClusters()){
			System.out.println(c.getCentroid().id_value + " size: " + c.getTracksInCorridor().size() + " nodes: " + c.getCentroid().toString());
		}*/
		
		/**
		 * clustering i expect:
		 * 
		 * first cluster (0) (6-7-8-9-10) with B/D (1/3)
		 * second cluster (1) (16-17-18-19-20) with A/C (0/2)
		 * 
		 */
		
		
		//KPCResult<SpatialNode> results = KPrimaryCorridors.kprimarycorridor(2, tsm);
		
		//System.out.println(results);
		 
		//ViewGraph.visualizeGraph(g);
		
		
	}
	
	public static void main(String[] args) {
		new BigDevTest();
	}
}
