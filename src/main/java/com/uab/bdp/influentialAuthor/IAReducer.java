package com.uab.bdp.influentialAuthor;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class IAReducer extends Reducer<Text, IntWritable, Text, DoubleWritable> {
    @Override
    protected void reduce(Text author, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        Double totalComment = 0.0;
        Double totalSubmission = 0.0;

        for (IntWritable  value : values) {
            totalComment += value.get();
            totalSubmission++;
        }

        Double contribution =  totalComment / totalSubmission;

        context.write(author,  new DoubleWritable(contribution));
    }
}
