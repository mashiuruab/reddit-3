package com.uab.bdp.influenceMatrix;

import com.google.gson.Gson;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IMReducer extends Reducer<Text, MapWritable, Text, Text> {
    private Gson gson = new Gson();

    @Override
    protected void reduce(Text subreddit, Iterable<MapWritable> values, Context context)
            throws IOException, InterruptedException {

        double totalUpVote = 0.0;
        double totalDownVote = 0.0;
        double totalNumberOfComments = 0.0;
        double totalOver18 =  0.0;
        double totalSubreddit = 0.0;

        for(MapWritable value : values) {
            totalSubreddit++;

            totalUpVote += ((DoubleWritable)value.get(IMMapper.UP_VOTE)).get();
            totalDownVote += ((DoubleWritable)value.get(IMMapper.DOWN_VOTE)).get();
            totalNumberOfComments += ((DoubleWritable)value.get(IMMapper.NUM_COMMENTS)).get();

            Boolean over18 = ((BooleanWritable)value.get(IMMapper.OVER_18)).get();

            if (over18) {
                totalOver18++;
            }
        }

        double upvote = totalUpVote / totalSubreddit;
        double downvote = totalDownVote / totalSubreddit;
        double numberOfComments =  totalNumberOfComments / totalSubreddit;
        double over18 = totalOver18 / totalSubreddit;

        Map<String,Double> outMap = new HashMap<String, Double>();

        outMap.put(IMMapper.UP_VOTE.toString(), upvote);
        outMap.put(IMMapper.DOWN_VOTE.toString(), downvote);
        outMap.put(IMMapper.OVER_18.toString(), over18);
        outMap.put(IMMapper.NUM_COMMENTS.toString(), numberOfComments);


        context.write(subreddit, new Text(gson.toJson(outMap)));
    }
}
