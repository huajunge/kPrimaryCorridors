package graph;

import org.apache.commons.collections15.Factory;

public class VertexFactory implements Factory {

	private int n = 0;
	@Override
	public Number create() {
		// TODO Auto-generated method stub
		return new Integer(n++);
	}

}
