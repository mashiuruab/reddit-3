package com.uab.bdp.KMeans.TFIDF;

import com.google.gson.Gson;
import com.uab.bdp.KMeans.TFIDF.model.TfDocCountContainer;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TFReducer extends Reducer<Text, MapWritable, Text, Text> {
    private Gson  gson = new Gson();

    @Override
    protected void reduce(Text subredditId, Iterable<MapWritable> values, Context context)
            throws IOException, InterruptedException {
        List<Double> tfidfList = new ArrayList<Double>();

        double totalNumberOfDocuments = 0;

        Map<String, TfDocCountContainer> termDocCountMap = new HashMap<String, TfDocCountContainer>();
        Map<String, Double> TF = new HashMap<String, Double>();
        Map<String, Double> IDF =  new HashMap<String, Double>();
        TfDocCountContainer container;


        for (MapWritable tfMap : values) {

            if (totalNumberOfDocuments == 1000) {
                break;
            }

            for(Map.Entry<Writable, Writable> tfEntry : tfMap.entrySet()) {
                Text termKey = (Text) tfEntry.getKey();
                DoubleWritable termFreqValue = (DoubleWritable) tfEntry.getValue();


                if (termDocCountMap.containsKey(termKey.toString())) {
                    container = termDocCountMap.get(termKey.toString());
                    container.setAggregatedTF(container.getAggregatedTF() + termFreqValue.get());
                    container.setTotalDoc(container.getTotalDoc() + 1);

                } else {
                    container = new TfDocCountContainer();
                    container.setTotalDoc(1);
                    container.setAggregatedTF(termFreqValue.get());
                }


                termDocCountMap.put(termKey.toString(),  container);
            }

            totalNumberOfDocuments++;
        }

        for(Map.Entry<String, TfDocCountContainer> entry : termDocCountMap.entrySet()) {
            TfDocCountContainer valueContainer = entry.getValue();
            double termFrequency =  valueContainer.getAggregatedTF() / valueContainer.getTotalDoc();
            TF.put(entry.getKey(), termFrequency);
        }


        for(Map.Entry<String, TfDocCountContainer> entry : termDocCountMap.entrySet()) {
            double idfValue = totalNumberOfDocuments / entry.getValue().getTotalDoc();
            idfValue = Math.log(idfValue);
            IDF.put(entry.getKey(), idfValue);
        }


        for(String key : TF.keySet()) {
            double value = TF.get(key) * IDF.get(key);
            tfidfList.add(value);
        }

        context.write(subredditId, new Text(gson.toJson(tfidfList)));
    }
}
