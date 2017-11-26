package com.uab.bdp.mostsubmission;


import com.google.gson.Gson;
import com.uab.bdp.model.Submission;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class MSubmissionMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private Gson gson = new Gson();

    private IntWritable one = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text json, Context context)
            throws IOException, InterruptedException {
        Submission submission = get(json);

        if (StringUtils.isEmpty(submission.getSubreddit())
                || submission.getNumberOfComments() ==  null) {
            return;
        }

        Text subredditName = new Text(submission.getSubreddit());

        context.write(subredditName, new IntWritable(submission.getNumberOfComments()));
    }

    private Submission get(Text json) {
        return gson.fromJson(json.toString(),Submission.class);
    }
}
