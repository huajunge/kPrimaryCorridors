package matrix;

import java.util.HashMap;

public class SparseMatrix<V> extends Matrix<V> {
	
	private HashMap<V,HashMap<V,Double>> array;

	public SparseMatrix(int rows, int columns) {
		super(rows, columns);
		// TODO Auto-generated constructor stub
		array = new HashMap<V,HashMap<V,Double>>();
	}
	
	public SparseMatrix(int rows, int columns, Double value){
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

	@Override
	public void set(V r, V c, Double d) {
		
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
	public void print() {
		System.out.println(" --- Matrix --- ");
		for(Integer i = 0; i < rows; i++){
			for(Integer j = 0; j < columns; j++){
				try{
					System.out.print(array.get(i).get(j) + " ");
				} catch (NullPointerException e){
					System.out.print(" ");
				}
			}
			System.out.println("");
		}
		System.out.println("");
		
	}

}
