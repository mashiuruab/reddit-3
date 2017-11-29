package com.uab.bdp.KMeans.TFIDF;

import com.google.gson.Gson;
import com.uab.bdp.KMeans.TFIDF.model.Comment;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class TFMapper extends Mapper<LongWritable, Text, Text, MapWritable> {
    private Gson gson = new Gson();
    private static Map<String, String> HATE_DB = new HashMap<String, String>();

    static {
        try {
            InputStream inputStream =  TFMapper.class.getClassLoader()
                    .getResourceAsStream("vocabulary.csv");
            Reader in = new InputStreamReader(inputStream);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').parse(in);
            for (CSVRecord record : records) {
                String vocabulary = record.get(0);
                String variant_of = record.get(1);

                HATE_DB.put(vocabulary, "");
                HATE_DB.put(variant_of, "");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Comment get(Text json) {
        return gson.fromJson(json.toString(), Comment.class);
    }


    @Override
    protected void map(LongWritable key, Text json, Context context)
            throws IOException, InterruptedException {
        Comment comment = get(json);

        Text subreddit = new Text(comment.getSubreddit());

        Map<String, Double>  termMap = new HashMap<String, Double>();

        StringTokenizer tokenizer = new StringTokenizer(comment.getBody());

        double totalToken = 0;

        while (tokenizer.hasMoreTokens()) {
            String  term = tokenizer.nextToken();

            if (HATE_DB.containsKey(term)) {
                if (termMap.containsKey(term)) {
                    Double counter = termMap.get(term);
                    double totalCount =  counter + 1;
                    termMap.put(term, totalCount);
                } else {
                    termMap.put(term, 1.0);
                }
            }

            totalToken++;
        }

        MapWritable termFrequencyMap  =  new MapWritable();

        for(Map.Entry<String, Double> termPair : termMap.entrySet()) {
            double tf = termPair.getValue() / totalToken;
            termFrequencyMap.put(new Text(termPair.getKey()),  new DoubleWritable(tf));
        }

        context.write(subreddit, termFrequencyMap);
    }
}
