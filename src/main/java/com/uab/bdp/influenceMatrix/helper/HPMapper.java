package com.uab.bdp.influenceMatrix.helper;

import com.google.gson.Gson;
import com.uab.bdp.influenceMatrix.helper.model.DataModel;
import com.uab.bdp.influenceMatrix.helper.model.GenericModel;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class HPMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {
    private Gson gson = new Gson();

    @Override
    protected void map(LongWritable key, Text json, Context context)
            throws IOException, InterruptedException {

        String[] splitter = json.toString().split("\\s+");

        GenericModel dataModel = get(splitter[1]);

        /*context.write(new Text(splitter[0]),
                new DoubleWritable(dataModel.getComment()));*/
        context.write(new Text(splitter[0]),
                new DoubleWritable(dataModel.getSubmission()));
    }

    private GenericModel get(String json) {
        return gson.fromJson(json, GenericModel.class);
    }
}
