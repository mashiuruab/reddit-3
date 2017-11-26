package com.uab.bdp.mostsubmission;


import com.google.gson.Gson;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MSubmissionReducer extends Reducer<Text,IntWritable,Text, Text> {
    private Gson gson  = new Gson();

    @Override
    protected void reduce(Text subreddit, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {

        int totalSubmission = 0;
        int totalComment = 0;

        for (IntWritable value : values) {
            totalComment += value.get();
            totalSubmission++;
        }

        Map<String, Integer> outMap = new HashMap<String, Integer>();
        outMap.put("numberOfComments", totalComment);
        outMap.put("numberOfSubmission",  totalSubmission);

        context.write(subreddit, new Text(gson.toJson(outMap)));
    }
}
