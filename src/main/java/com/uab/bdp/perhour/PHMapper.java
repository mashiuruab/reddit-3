package com.uab.bdp.perhour;

import com.google.gson.Gson;
import com.uab.bdp.model.Submission;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class PHMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private Gson gson = new Gson();

    @Override
    protected void map(LongWritable key, Text json, Context context)
            throws IOException, InterruptedException {
        Submission submission = get(json);

        if (StringUtils.isEmpty(submission.getCreated())
                || submission.getNumberOfComments() == null) {
            return;
        }

        String hourOfDay = calculateHour(submission.getCreated());

        context.write(new Text(hourOfDay),  new IntWritable(submission.getNumberOfComments()));
    }

    private Submission get(Text json) {
        return gson.fromJson(json.toString(), Submission.class);
    }


    private static String calculateHour(String timestampString) {
        long timestamp = (Long.parseLong(timestampString) * 1000);
        Date date = new Date(timestamp);
        Calendar c = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        c.setTime(date);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
        return dateFormat.format(c.getTime());
    }
}
