package com.uab.bdp.categorization;

import com.google.gson.Gson;
import com.uab.bdp.categorization.model.CGSubmission;
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

public class CGMapper extends Mapper<LongWritable, Text, Text, MapWritable> {
    private Gson gson = new Gson();
    private IntWritable  one = new IntWritable(1);

    public static final String IMAGE_KEY = "image";
    public static final String AUDIO_KEY = "audio";
    public static final String VIDEO_KEY = "video";

    public static Text IS_SELF = new Text(CGSubmission.IS_SELF_KEY);
    public static Text SELF_TEXT = new Text(CGSubmission.SELF_TEXT_KEY);
    public static Text IMAGE = new Text(IMAGE_KEY);
    public static Text AUDIO = new Text(AUDIO_KEY);
    public static Text VIDEO = new Text(VIDEO_KEY);

    @Override
    protected void map(LongWritable key, Text json, Context context)
            throws IOException, InterruptedException {
        CGSubmission cgSubmission = get(json);

        String date =  calculateDate(cgSubmission.getCreated_utc());

        if (StringUtils.isEmpty(date)) {
            return;
        }

        Text day = new Text(date);
        MapWritable outMap = new MapWritable();

        if (cgSubmission.getSelfKey() != null && cgSubmission.getSelfKey()) {
            outMap.put(IS_SELF, one);
        }

        if (!StringUtils.isEmpty(cgSubmission.getSelfText())) {
            outMap.put(SELF_TEXT, one);
        }

        if (cgSubmission.getPreview() != null) {
            outMap.put(IMAGE, one);
        }

        if (cgSubmission.getMedia() != null
                && cgSubmission.getMedia().getOembed() != null
                && AUDIO_KEY.equals(cgSubmission.getMedia().getOembed().getType())) {
            outMap.put(AUDIO, one);
        }

        if (cgSubmission.getMedia() != null
                && cgSubmission.getMedia().getOembed() != null
                && VIDEO_KEY.equals(cgSubmission.getMedia().getOembed().getType())) {
            outMap.put(VIDEO, one);
        }

        context.write(day, outMap);
    }

    private CGSubmission get(Text json) {
        return gson.fromJson(json.toString(), CGSubmission.class);
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
