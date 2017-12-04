package com.uab.bdp.domain;

import com.google.gson.Gson;
import com.uab.bdp.domain.model.DomainModel;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DomainMapper extends Mapper<LongWritable, Text, Text, MapWritable> {
    private Gson gson = new Gson();

    public static final String SELF_KEY = "self";

    private IntWritable one = new IntWritable(1);
    private Text SELF = new Text("self");

    @Override
    protected void map(LongWritable key, Text json, Context context)
            throws IOException, InterruptedException {
        DomainModel dModel = get(json);

        if (StringUtils.isEmpty(dModel.getDomain())
                || StringUtils.isEmpty(dModel.getCreated())) {
            return;
        }

        String domain = dModel.getDomain();
        String date = calculateDate(dModel.getCreated());

        MapWritable outWritable = new MapWritable();

        if (domain.startsWith(SELF_KEY)) {
            outWritable.put(SELF, one);
        } else {
            outWritable.put(new Text(domain), one);
        }

        context.write(new Text(date) , outWritable);
    }

    private DomainModel get(Text json) {
        return gson.fromJson(json.toString(),DomainModel.class);
    }

    private static String calculateDate(String timestampString) {
        long timestamp = (Long.parseLong(timestampString) * 1000);
        Date date = new Date(timestamp);
        Calendar c = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        c.setTime(date);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(c.getTime());
    }
}
