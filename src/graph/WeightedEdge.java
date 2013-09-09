package graph;

import java.io.Serializable;

import org.apache.commons.collections15.Transformer;

public class WeightedEdge implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4090184416463826527L;
	private int id;
	private Double weight;
	private String label;
	
	
	public WeightedEdge(int id, Double weight){
		this.id = id;
		this.weight = weight;
		this.label = id + "";
	}
	
	public WeightedEdge(int id, Double weight, String label){
		this.id = id;
		this.weight = weight;
		this.label  = label;
	}

	public int getID(){
		return id;
	}

	public Double getWeight() {
		return weight;
	}

	public String toString(){
		return id + "";
	}
	
	
	
	
	public String getLabel() {
		return label;
	}




	public static class weightTransformer implements Transformer<WeightedEdge,Double> {

		@Override
		public Double transform(WeightedEdge arg0) {
			
			return arg0.getWeight();
		}
		
		
		
	}
	
}
