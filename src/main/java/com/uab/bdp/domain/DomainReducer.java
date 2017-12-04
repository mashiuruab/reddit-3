package com.uab.bdp.domain;

import com.google.gson.Gson;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DomainReducer extends Reducer<Text, MapWritable, Text, Text>{
    private Gson gson  = new Gson();

    @Override
    protected void reduce(Text key, Iterable<MapWritable> values, Context context)
            throws IOException, InterruptedException {

        Map<String, Double> outMap = new HashMap<String, Double>();

        for(MapWritable map : values) {
            for(Map.Entry<Writable, Writable> entry : map.entrySet()) {
                Text domain = (Text) entry.getKey();
                IntWritable count = (IntWritable) entry.getValue();

                if (outMap.containsKey(domain.toString())) {
                    double prev  = outMap.get(domain.toString());
                    prev += count.get();
                    outMap.put(domain.toString(), prev);
                } else {
                    outMap.put(domain.toString(), 1.0);
                }
            }
        }

        Text output = new Text(gson.toJson(outMap));

        context.write(key, output);
    }
}
