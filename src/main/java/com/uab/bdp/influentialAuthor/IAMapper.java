package com.uab.bdp.influentialAuthor;

import com.google.gson.Gson;
import com.uab.bdp.model.Submission;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class IAMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private Gson gson =  new Gson();

    @Override
    protected void map(LongWritable key, Text json, Context context)
            throws IOException, InterruptedException {
        Submission submission = get(json);

        Text author = new Text(submission.getAuthor());
        IntWritable numberOfComment = new IntWritable(submission.getNumberOfComments());

        context.write(author, numberOfComment);
    }

    private Submission get(Text json) {
        return gson.fromJson(json.toString(), Submission.class);
    }
}
