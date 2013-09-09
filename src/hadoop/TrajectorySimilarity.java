package hadoop;

import hadoop.WordCount.IntSumReducer;
import hadoop.WordCount.TokenizerMapper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class TrajectorySimilarity {

	/**
	 *  <KEYIN,VALUEIN,KEYOUT,VALUEOUT>
	 *  KEYIN = line number (corr to a trajectory)
	 *  VALUEIN = edge_gids (comma separated)
	 *  KEYOUT = trajectory id
	 *  VALUEOUT = 
	 * @author mevans
	 *
	 */
	public static class TrackTokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
		
	}
	
	
	/**
	 * @param args
	 */
	 public static void main(String[] args) throws Exception {
	  
	    Configuration conf = new Configuration();
	    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
	    if (otherArgs.length != 2) {
	      System.err.println("Usage: wordcount <in> <out>");
	      System.exit(2);
	    }

	    Job job = new Job(conf, "TSM");
	    job.setJarByClass(TrajectorySimilarity.class);
	    job.setMapperClass(TokenizerMapper.class);
	    job.setCombinerClass(IntSumReducer.class);
	    job.setReducerClass(IntSumReducer.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(IntWritable.class);
	    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
	    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
	    
	    
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	  }

}
