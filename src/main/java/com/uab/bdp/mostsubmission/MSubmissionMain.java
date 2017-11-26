package com.uab.bdp.mostsubmission;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class MSubmissionMain {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        Job job = new Job(conf, "Most Submission Per Subreddit");

        job.setJarByClass(MSubmissionMain.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setMapperClass(MSubmissionMapper.class);
        job.setReducerClass(MSubmissionReducer.class);

        job.setNumReduceTasks(20);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        for(int counter = 0; counter < args.length -1;  counter++) {
            FileInputFormat.addInputPath(job, new Path(args[counter]));
        }

        FileOutputFormat.setOutputPath(job, new Path(args[args.length-1]));

        job.waitForCompletion(true);
    }
}
