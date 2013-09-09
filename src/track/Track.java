package track;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;


public class Track<V> implements Serializable, Comparable<Track<V>> {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8958159771415430896L;
	private LinkedList<V> route;  //set of nodes
	public int id_value;
	private String label;
	private V startNode, endNode;

	
	public Track(int id_value){
		super();
		this.id_value = id_value;
		this.route = new LinkedList<V>();
	}
	
	public Track(int id_value, LinkedList<V> route) {
		super();
		this.route = route;
		this.id_value = id_value;
	}
	
	
	
	



	public LinkedList<V> getNodes(){
		return route;
	}
	
	/**public void setRoute(V[] v){
		for(int i = 0; i < v.length; i++){
			route.add(v[i]);
		}
	}**/
	
	public void setRoute(LinkedList<V> v){
		route = v;
	}
	
	public boolean appendNode(V v){
		
		if(route.contains(v) || v == null){
			return false; //we dont want duplicate nodes
		} else {
			return route.add(v);
		}
	}
	
	public String toString(){
		
		String tmp  = "";
		for(V v : getNodes()){
			tmp += ", " + v;
		}
		return id_value + tmp;
	}
	

	public String getLabel() {
		if(label == null){
			label = id_value+"";
		}
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	

	public V getStartNode() {
		return startNode;
	}

	public void setStartNode(V startNode) {
		this.startNode = startNode;
	}

	public V getEndNode() {
		return endNode;
	}

	public void setEndNode(V endNode) {
		this.endNode = endNode;
	}

	@Override
	public int compareTo(Track<V> o) {
		
		for(V v : route){
			if(!o.getNodes().contains(v)){
				return -1;
			}
		}
		
		for(V v : o.getNodes()){
			if(!route.contains(v)){
				return 1;
			}
		}
		
		
		return 0;
		
	}

	@Override
	public boolean equals(Object obj) {
		
		Track<V> o = (Track<V>) obj;
		
		for(V v : route){
			if(!o.getNodes().contains(v)){
				return false;
			}
		}
		
		for(V v : o.getNodes()){
			if(!route.contains(v)){
				return false;
			}
		}
		
		
		return true;
	}
	
	
	

}
