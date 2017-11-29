package com.uab.bdp.perhour;

import com.google.gson.Gson;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PHReducer extends Reducer<Text, IntWritable, Text, Text> {
    private Gson gson = new Gson();

    @Override
    protected void reduce(Text hourOfDay, Iterable<IntWritable> values, Context context)
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

        context.write(hourOfDay, new Text(gson.toJson(outMap)));
    }
}
