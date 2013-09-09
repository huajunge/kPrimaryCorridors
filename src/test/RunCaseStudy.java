package test;


import graph.SpatialNode;
import graph.ViewGraph;
import graph.WeightedEdge;
import kcc.KCCResult;
import kcc.KCentralCorridors;

public class RunCaseStudy {

	
	public static  void main(String[] args) {
		
		String expSetupFilename = args[0];
		//String TSM_Version = args[1];
		
		System.out.println(expSetupFilename);
		//System.out.println(TSM_Version);
		
		System.out.println("Loading Experimental Setup...");
		CaseExperimentVariables<SpatialNode, WeightedEdge> ex = CaseExperimentVariables.readInEV(expSetupFilename);
		
		System.out.println(ex.toString());
		
		//ViewGraph.visualizeGraph(ex.getGraph());
		
		int k = Integer.parseInt(args[1]);
		
		//KCCResult<SpatialNode> kccresult = KCentralCorridors.kCentralCorridors(k, ex.getGraph(), ex.getTsm(), ex.getRoutes(), ex.getRoutes(), true, true);
		KCCResult<SpatialNode> kccresult = KCentralCorridors.kCentralCorridors(k, ex.getGraph(), ex.getTsm(), ex.getRoutes(), ex.getCandidates(), true, true);
		
		//kccresult.printSummary();
		
		//kccresult.printCorridors(ex.getGraph());
		
		kccresult.printEdges(ex.getGraph());
		
		//kccresult.printTracks(ex.getGraph());
	}
}
