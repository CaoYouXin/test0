/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.sql;

import java.util.ArrayList;
import java.util.List;

import consts.Constants;


/**
 *
 * @author CPU
 */
public final class Partitioning {

	public static enum PartitionBy {

		range,
		hash;
	}

	private PartitionBy pb;
	private String parameter;
	private int count;
	private List<Partition> parts;

	public Partitioning(PartitionBy pb, String parameter, int count) {
		this.pb = pb;
		this.parameter = parameter;
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void addPartition(Partition part) {
		if (null == this.parts) {
			this.parts = new ArrayList<>();
		}
		this.parts.add(part);
	}

	@Override
	public String toString() {
		switch (pb) {
			case range:
				StringBuilder sb = new StringBuilder();
				parts.stream().forEach((part) -> {
					sb.append(',').append(part.toString());
				});
				return String.format("PARTITION BY RANGE (%s) (%s)", parameter, sb.substring(1));
			case hash:
				return String.format("PARTITION BY HASH (%s) PARTITIONS %d", parameter, count);
			default:
				throw new RuntimeException(String.format("绝对零概率事件（%s）", Constants.MOBILE_NUMBER));
		}
	}

}
