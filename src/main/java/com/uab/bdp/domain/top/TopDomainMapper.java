package com.uab.bdp.domain.top;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uab.bdp.domain.DomainMapper;
import com.uab.bdp.domain.model.DomainModel;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class TopDomainMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {
    private Gson gson = new Gson();

    private IntWritable one  = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text json, Context context)
            throws IOException, InterruptedException {
        String[] splitted = json.toString().split("\\s+");

        if (splitted.length != 2) {
            return;
        }

        Map<String,  Double> domainMap = get(splitted[1]);

        for (Map.Entry<String, Double> entry : domainMap.entrySet()) {
            context.write(new Text(entry.getKey()),  new DoubleWritable(entry.getValue()));
        }
    }

    private Map<String, Double> get(String json) {
        Type type = new TypeToken<Map<String, Double>>(){}.getType();
        return gson.fromJson(json, type);
    }
}
