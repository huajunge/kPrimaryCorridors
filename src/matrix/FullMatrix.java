package matrix;

import java.io.Serializable;
import java.util.HashMap;

public class FullMatrix<V> extends Matrix<V> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1815312180136774003L;
	//change to float for big matricies
	private HashMap<V,HashMap<V,Double>> array;
	
	public FullMatrix(int rows, int columns) {
		super(rows, columns);
		array = new HashMap<V,HashMap<V,Double>>();
	}
	
	public FullMatrix(int rows, int columns, Double value) {
		super(rows, columns, value);
		array = new HashMap<V,HashMap<V,Double>>();
	}

	@Override
	public Double get(V r, V c) {
		if(array.containsKey(r)){
			HashMap<V,Double> map = array.get(r);
			if(map.containsKey(c)){
				return array.get(r).get(c);
			}
		}
		return this.getDefaultValue();
	}
	
	public void set(V r, V c, Double d){
		HashMap<V,Double> map;
		
		if(array.containsKey(r)){
			map = array.get(r);
		} else {
			map = new HashMap<V,Double>();
		}
		
		map.put(c, d);
		array.put(r, map);
	}



	@Override
	public int compareTo(Matrix<V> m) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void print(){
		for(HashMap<V, Double> v : array.values()){
			System.out.print(v.keySet() + " ");
			for(Double m : v.values()){
				System.out.print(m + " ");
			}
			System.out.println("");
		}
		System.out.println("");
		/*for(Integer i = 0; i < rows; i++){
			for(Integer j = 0; j < columns; j++){
				 System.out.print(array[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("");*/
	}

}
