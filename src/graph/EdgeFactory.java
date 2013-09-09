package graph;

import org.apache.commons.collections15.Factory;

public class EdgeFactory implements Factory {

	private int n = 0;
	@Override
	public WeightedEdge create() {
		WeightedEdge g = new WeightedEdge(n++,1.);
		
		return g;
	}

}
