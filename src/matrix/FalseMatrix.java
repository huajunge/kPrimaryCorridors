package matrix;

public class FalseMatrix<V> extends Matrix<V> {
	
	public FalseMatrix(int rows, int columns){
		super(rows,columns);
	}
	
	public FalseMatrix(int rows, int columns, Double value) {
		super(rows, columns, value);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Double get(V r, V c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set(V r, V c, Double d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compareTo(Matrix<V> m) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
		
	}

	

}
