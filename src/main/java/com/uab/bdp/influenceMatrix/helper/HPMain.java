package com.uab.bdp.influenceMatrix.helper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class HPMain {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        Job job = new Job(conf,"Helper NumberOfComments, ups, downs, over_18");

        job.setJarByClass(HPMain.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(DoubleWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

        job.setMapperClass(HPMapper.class);
        job.setReducerClass(HPReducer.class);

        job.setNumReduceTasks(1);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        for(int counter = 0; counter < args.length -1;  counter++) {
            FileInputFormat.addInputPath(job, new Path(args[counter]));
        }

        FileOutputFormat.setOutputPath(job, new Path(args[args.length-1]));

        job.waitForCompletion(true);
    }
}
