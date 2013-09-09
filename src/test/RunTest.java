package test;

import kpc.KPCResult;
import kpc.KPrimaryCorridors;
import edu.uci.ics.jung.graph.Graph;
import graph.WeightedEdge;
import track.tsm.*;

public class RunTest {

	
	public static <V, E> void main(String[] args) {
		
		String expSetupFilename = args[0];
		String TSM_Version = args[1];
		
		System.out.println(expSetupFilename);
		System.out.println(TSM_Version);
		
		System.out.println("Loading Experimental Setup...");
		ExperimentVariables<V, WeightedEdge> ex = ExperimentVariables.readInEV(expSetupFilename);
		
		TSM<V> tsm = null;
		
		System.out.println("Computing TSM...");
		if(TSM_Version.equals("gnts_all")){
			tsm = new GNTS_All(ex.getTracks(), (Graph<V, WeightedEdge>) ex.getGraph());
		} else if (TSM_Version.equals("mets_all")){
			tsm = new METS_All(ex.getTracks(), (Graph<V, WeightedEdge>) ex.getGraph());
		} else if (TSM_Version.equals("mrts_all")){
			tsm = new MRTS_All(ex.getTracks(), (Graph<V, WeightedEdge>) ex.getGraph());
		} else if (TSM_Version.equals("gnts_all_fw")){
			tsm = new GNTS_All_FW(ex.getTracks(), (Graph<V, WeightedEdge>) ex.getGraph(), ex.getFw_dists());
		} else if (TSM_Version.equals("mets_jit")){
			tsm = new METS_JIT(ex.getTracks(), (Graph<V, WeightedEdge>) ex.getGraph());
		} else if (TSM_Version.equals("mrts_jit")){
			tsm = new MRTS_JIT(ex.getTracks(), (Graph<V, WeightedEdge>) ex.getGraph());
		} else {
			System.out.println("Wrong input");
			System.exit(-1);
		}
		
		System.out.println("TSM done");
		
		//System.out.println("Computing kPC...");
		//KPCResult<V> results = KPrimaryCorridors.kprimarycorridor(ex.getK(), tsm);
		
		//System.out.println(results.toString());
		
	}
	
}
