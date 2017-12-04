package com.uab.bdp.domain.top;

import com.google.gson.Gson;
import com.uab.bdp.domain.DomainMapper;
import com.uab.bdp.domain.model.DomainModel;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class TopDomainMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private Gson gson = new Gson();

    private Text SELF = new Text(DomainMapper.SELF_KEY);
    private IntWritable one  = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text json, Context context)
            throws IOException, InterruptedException {
        DomainModel model  = get(json);

        if (StringUtils.isEmpty(model.getDomain())) {
            return;
        }

        if (model.getDomain().startsWith(DomainMapper.SELF_KEY)) {
            context.write(SELF, one);
        } else {
            context.write(new Text(model.getDomain()), one);
        }
    }

    private DomainModel get(Text json) {
        return gson.fromJson(json.toString(), DomainModel.class);
    }
}
