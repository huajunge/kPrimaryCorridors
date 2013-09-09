package hadoop.kmeans;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.*;

public class PointTuple implements Writable {
	
	private long id;
	private double x, y;
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		id = in.readLong();
		x = in.readDouble();
		y = in.readDouble();
		
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeLong(id);
		out.writeDouble(x);
		out.writeDouble(y);
		
	}
	
	public String toString(){
		return "PointTuple: " + id + "\t x:" + x  + "\t y:" + y;
	}

	public static double distance(PointTuple pointOut, PointTuple pointTuple) {
		double tmp = 0;
		
		tmp = Math.sqrt(Math.pow((pointTuple.x - pointOut.x),2) + Math.pow((pointTuple.y - pointOut.y),2));
		
		return tmp;
	}

}
