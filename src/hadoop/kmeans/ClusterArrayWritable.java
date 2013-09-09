package hadoop.kmeans;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Writable;

public class ClusterArrayWritable extends ArrayWritable {

	/**
	 * @param valueClass
	 * @param values
	 */
	public ClusterArrayWritable(Class<? extends Writable> valueClass,
			Writable[] values) {
		super(valueClass, values);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param valueClass
	 */
	public ClusterArrayWritable(Class<? extends Writable> valueClass) {
		super(valueClass);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public ClusterArrayWritable(String[] arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object toArray() {
		// TODO Auto-generated method stub
		return super.toArray();
	}

	@Override
	public Writable[] get() {
		// TODO Auto-generated method stub
		return super.get();
	}

	

}
