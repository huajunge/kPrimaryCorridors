package hadoop.kmeans;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.URI;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.io.serializer.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.filecache.DistributedCache;

import java.util.ArrayList;

public class KMeansPoints {
	
	public static final String K_VALUE_LABEL = "kmeans.k";
	public static final String CLUSTER_PATH = "/kmeans/clusters.dat";
	
	
	/**
	 *  <KEYIN,VALUEIN,KEYOUT,VALUEOUT>
	 *  KEYIN = offset
	 *  VALUEIN = point information (id, x, y)
	 *  KEYOUT = cluster id
	 *  VALUEOUT = point information (id, x, y)
	 *  
	 * @author mevans
	 *
	 */
	public static class PointMapper extends Mapper<Object, Text, IntWritable, PointTuple>{

		private int k = -1;
		private PointTuple pointOut = null;
		private PointTuple[] clusters = null;
		
		@Override
		protected void setup(Context context) throws IOException,
				InterruptedException {
			
			 System.out.println("--In mapper setup...");
			
			k = Integer.parseInt(context.getConfiguration().get(KMeansPoints.K_VALUE_LABEL));
			
			
			
			FileSystem fs = FileSystem.get(context.getConfiguration());
			FSDataInputStream input = fs.open(new Path(CLUSTER_PATH));
			
			ArrayWritable aw = new ArrayWritable(PointTuple.class);
			aw.readFields(input);
			Writable[] tmparray =  aw.get();
			clusters = new PointTuple[k];
			for(int i = 0; i < tmparray.length; i++){
				clusters[i] = (PointTuple) tmparray[i];
			}
			input.close();

			
		}

		@Override
		protected void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			 
			
			System.out.println("--Mapping...");
			
			String[] parsed = value.toString().split("\t");
			
			pointOut = new PointTuple();
			pointOut.setId(Long.parseLong(parsed[0]));
			pointOut.setX(Double.parseDouble(parsed[1]));
			pointOut.setY(Double.parseDouble(parsed[2]));
			
			double minDist = Double.MAX_VALUE;
			int index = -1;
			
			for(int i = 0; i < clusters.length; i++){
				double tmpDist = PointTuple.distance(pointOut,clusters[i]);
				if(tmpDist < minDist){
					minDist = tmpDist;
					index = i;
				}
			}
			
			
			
			context.write(new IntWritable(index), pointOut);
			
		}
		
		
		
	}
	

	
	public static class MeansReducer extends Reducer<IntWritable, PointTuple, IntWritable, PointTuple> {

		
		protected void reduce(IntWritable arg0, Iterable<PointTuple> arg1, Context context)
				throws IOException, InterruptedException {
			
			double x = 0.;
			double y = 0.;
			int count = 0;
			
			for(PointTuple p : arg1){
				x += p.getX();
				y += p.getY();
				count++;
			}
			
			x = x / count;
			y = y / count;
			
			PointTuple tmp = new PointTuple();
			tmp.setId(arg0.get());
			tmp.setX(x);
			tmp.setY(y);
			
			FileSystem fs = FileSystem.get(context.getConfiguration());
			FSDataInputStream input = fs.open(new Path(CLUSTER_PATH));
			
			ArrayWritable aw = new ArrayWritable(PointTuple.class);
			aw.readFields(input);
			
			Writable[] tmparray =  aw.get();
			PointTuple[] clusters = new PointTuple[tmparray.length];
			for(int i = 0; i < tmparray.length; i++){
				clusters[i] = (PointTuple) tmparray[i];
			}
			
			clusters[arg0.get()] = tmp;
			
			context.write(arg0,tmp);
			input.close();
			
		    FSDataOutputStream strm = fs.create(new Path(CLUSTER_PATH));
		    ArrayWritable aw2 = new ArrayWritable(PointTuple.class,(Writable[]) clusters);
		    
		    aw2.write(strm);
		    strm.flush();
		    strm.close();
			
		}

		@Override
		protected void cleanup(Context context)
				throws IOException, InterruptedException {
			
			//check if we need another loop, set a boolean counter
			//delete tmp files otherwise
			
			super.cleanup(context);
		}
		
		
		
	}
	
	private static Job submitJob(Configuration conf, Path inputDir, Path outputDir) throws Exception {
		
		Job job = new Job(conf, "kmeans points");
	    job.setJarByClass(hadoop.kmeans.KMeansPoints.class);
	    job.setMapperClass(PointMapper.class);
	    job.setCombinerClass(MeansReducer.class);
	    job.setReducerClass(MeansReducer.class);
	    job.setOutputKeyClass(IntWritable.class);
	    job.setOutputValueClass(PointTuple.class);
	    FileInputFormat.addInputPath(job, inputDir);
	    FileOutputFormat.setOutputPath(job, outputDir);
	    
	    return job;
	}
	
	
	/**
	 * @param args
	 */
	 public static void main(String[] args) throws Exception {
	  
		 System.out.println("--Loaded main...");
		 
	    Configuration conf = new Configuration();
	    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
	    if (otherArgs.length != 3) {
	      System.err.println("Usage: kmeans <k> <in> <out>");
	      System.exit(2);
	    }

	    System.out.println("--Setting k in config...");
	    int k = Integer.parseInt(otherArgs[0]);
	    conf.set(K_VALUE_LABEL, otherArgs[0]);
	    
	    
	    
	    PointTuple[] clusters = new PointTuple[k];
	    for(int i = 0; i < k; i++){
	    	PointTuple p = new PointTuple();
	    	p.setId(i);
	    	p.setX(Math.random()*100.);
	    	p.setY(Math.random()*100.);
	    	clusters[i] = p;
	    }
	    
	    System.out.println("--Writing out clusters to file...");
	    FileSystem fs = FileSystem.get(conf);
	    FSDataOutputStream strm = fs.create(new Path(CLUSTER_PATH));
	    
	    ArrayWritable aw = new ArrayWritable(PointTuple.class,(Writable[]) clusters);
	    
	    aw.write(strm);
	    strm.flush();
	    strm.close();
	    
	    System.out.println("--Setting up job...");
	    
	    
	    for(int i = 0; i < 5; i++){
	    	Job kmeansJob = submitJob(conf, new Path(otherArgs[1]), new Path(otherArgs[2]+"."+i));
	    	kmeansJob.waitForCompletion(true);
	    }
	    
	    
	  }


}
