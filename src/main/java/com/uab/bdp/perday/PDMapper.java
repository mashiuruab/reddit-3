package com.uab.bdp.perday;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class PDMapper extends Mapper<LongWritable, Text, Text, MapWritable> {
}
