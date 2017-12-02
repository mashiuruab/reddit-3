package com.uab.bdp.categorization;

import com.google.gson.Gson;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CGReducer extends Reducer<Text, MapWritable, Text, Text> {
    private Gson gson = new Gson();

    @Override
    protected void reduce(Text key, Iterable<MapWritable> values, Context context)
            throws IOException, InterruptedException {

        double totalSubmission = 0.0;
        double totalSelf = 0;
        double totalText = 0;
        double totalImage = 0;
        double totalRichType = 0;
        double totalVideo = 0;

        for (MapWritable map : values) {

            for (MapWritable.Entry<Writable, Writable> entry : map.entrySet()) {
                Text entryKey = (Text) entry.getKey();

                if (entryKey.equals(CGMapper.IS_SELF)) {
                    totalSelf++;
                }

                if(entryKey.equals(CGMapper.SELF_TEXT)) {
                    totalText++;
                }

                if(entryKey.equals(CGMapper.RICH)) {
                    totalRichType++;
                }

                if(entryKey.equals(CGMapper.VIDEO)) {
                    totalVideo++;
                }

                if(entryKey.equals(CGMapper.IMAGE)) {
                    totalImage++;
                }

            }

            totalSubmission++;
        }

        Map<String, Double> outMap = new HashMap<String, Double>();
        outMap.put(CGMapper.IS_SELF.toString(), totalSelf);
        outMap.put(CGMapper.SELF_TEXT.toString(), totalText);
        outMap.put(CGMapper.RICH.toString(), totalRichType);
        outMap.put(CGMapper.VIDEO.toString(), totalVideo);
        outMap.put(CGMapper.IMAGE.toString(), totalImage);
        outMap.put("totalSubmission", totalSubmission);

        context.write(key, new Text(gson.toJson(outMap)));
    }
}
