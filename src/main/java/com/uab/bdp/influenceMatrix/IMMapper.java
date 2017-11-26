package com.uab.bdp.influenceMatrix;

import com.google.gson.Gson;
import com.uab.bdp.model.Submission;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class IMMapper extends Mapper<LongWritable, Text, Text, MapWritable> {
    private Gson gson = new Gson();

    public static final Text UP_VOTE =  new Text(Submission.UP_VOTE_KEY);
    public static final Text DOWN_VOTE = new Text(Submission.DOWN_VOTE_KEY);
    public static final Text OVER_18 = new Text(Submission.OVER_18_KEY);
    public static final Text NUM_COMMENTS = new Text(Submission.NUM_COMMENTS_KEY);

    @Override
    protected void map(LongWritable key, Text json, Context context)
            throws IOException, InterruptedException {
        Submission submission = get(json);

        if (StringUtils.isEmpty(submission.getSubreddit())
                || submission.getUps() == null
                || submission.getDowns() ==  null
                || submission.getOver_18() ==  null
                || submission.getNumberOfComments() == null) {
            return;
        }

        Text subReddit =  new Text(submission.getSubreddit());
        MapWritable outWritable = new MapWritable();

        outWritable.put(UP_VOTE, new DoubleWritable(submission.getUps()));
        outWritable.put(DOWN_VOTE,  new DoubleWritable(submission.getDowns()));
        outWritable.put(OVER_18, new BooleanWritable(submission.getOver_18()));
        outWritable.put(NUM_COMMENTS, new DoubleWritable(submission.getNumberOfComments()));

        context.write(subReddit, outWritable);
    }

    private Submission get(Text json) {
        return gson.fromJson(json.toString(), Submission.class);
    }
}
