package com.uab.bdp.domain.usage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TopDomainUsageMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {
    private Gson gson = new Gson();
    private List<String> targetDomain  = Arrays.asList(
            "self",//1
            "i.redd.it",//2
            "youtube.com",//3
            "imgur.com",//4
            "i.imgur.com",//5
            "reddit.com",//6
            "i.reddituploads.com",//7
            "twitter.com",//8
            "gfycat.com",//9
            "soundcloud.com",//10
            "pinterest.com",//11
            "washingtonpost.com",//12
            "theguardian.com",//13
            "nytimes.com",//14
            "miamiherald.com",//15
            "independent.co.uk",//16
            "m.imgur.com",//17
            "reuters.com",//18
            "instagram.com"//19
    );

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        String[] splitted  = value.toString().split("\\s+");

        if (splitted.length != 2) {
            System.out.println("Not splitted properly");
            return;
        }

        Map<String, Double> domainMap =  get(splitted[1]);

        String domainToSearch = targetDomain.get(0);
        Double count =  0.0;

        if (domainMap.containsKey(domainToSearch)) {
            count = domainMap.get(domainToSearch);
        }

        context.write(new Text(splitted[0]), new DoubleWritable(count));

    }

    private Map<String, Double> get(String json) {
        Type type = new TypeToken<Map<String, Double>>(){}.getType();
        return gson.fromJson(json, type);
    }
}
