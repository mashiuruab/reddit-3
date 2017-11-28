package com.uab.bdp.perday;

import com.google.gson.Gson;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PDReducer extends Reducer<Text, IntWritable, Text, Text> {
    private Gson gson = new Gson();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        double totalSubmission = 0.0;
        double totalComment = 0.0;

        for (IntWritable value : values) {
            totalComment += value.get();
            totalSubmission++;
        }

        Map<String, Double> outMap = new HashMap<String, Double>();

        outMap.put("comment", totalComment);
        outMap.put("submission", totalSubmission);

        context.write(key, new Text(gson.toJson(outMap)));
    }
}
