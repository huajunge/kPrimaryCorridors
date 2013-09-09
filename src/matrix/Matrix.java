package matrix;

import java.io.Serializable;

public abstract class Matrix<V> implements Comparable<Matrix<V>>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5911715564321655419L;
	protected final int rows, columns;
	protected final double setValue;
	
	
	public int getRowDimension() {
		return rows;
	}

	public int getColumnDimension() {
		return columns;
	}

	public Matrix(int rows, int columns){
		this.rows = rows;
		this.columns = columns;
		this.setValue = 0;
	}
	
	public Matrix(int rows, int columns, Double value){
		this.rows = rows;
		this.columns = columns;
		this.setValue = value;
	}
	
	public abstract Double get(V r, V c);
	
	public abstract void set(V r, V c, Double d);
	
	public final Double getDefaultValue(){
		return setValue;
	}

	public abstract int compareTo(Matrix<V> m);
	
	public abstract void print();
	

}
