package graph;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class SpatialNode implements Comparable<SpatialNode>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6678867033642246379L;
	private int id;
	private HashMap<Integer,Integer> pointsContained;
	private double x,y;
	private String label;
	

	
	public SpatialNode(int id){
		this.id = id;
		pointsContained = new HashMap<Integer,Integer>();
		this.x = -1.;
		this.y = -1.;
	}
	
	public SpatialNode(int id, String label){
		this.id = id;
		this.label = label;
		pointsContained = new HashMap<Integer,Integer>();
		this.x = -1.;
		this.y = -1.;
	}
	

	public SpatialNode(int id, double x, double y) {
		this.id = id;
		pointsContained = new HashMap<Integer,Integer>();
		this.x = x;
		this.y = y;
	}

	


	public String getLabel() {
		return label;
	}



	public void setLabel(String label) {
		this.label = label;
	}



	public HashMap<Integer, Integer> getPointsContained() {
		return pointsContained;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public int getID(){
		return id;
	}

	public String toString(){
		return id + "," + x + "," + y;
	}
	


	@Override
	public boolean equals(Object obj) {
		
		SpatialNode other = (SpatialNode) obj;
		
		if(this.getX() == other.getX()){
			if(this.getY() == other.getY()){
				if(this.getID() == other.getID()){
					return true;
				}
			}
		}
		
		return false;
	}


	/**public void incrementTrip(int parseInt) {
		Integer value = this.getPointsContained().get(parseInt);
		if(value == null){
			value = 0;
		}
		this.getPointsContained().put(parseInt, value + 1);
		
	}**/

	@Override
	public int compareTo(SpatialNode arg0) {
		Integer dis = id;
		Integer dat = arg0.getID();
		return dis.compareTo(dat);
	}

	public LinkedList<SpatialNode> getSingleNodeRoute(){
		SpatialNode[] route1 = {this};
		return new LinkedList<SpatialNode>(java.util.Arrays.asList(route1));
	}
	
	
}
