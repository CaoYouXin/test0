/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package partition;

import static utils.DateUtils.before;
import static utils.DateUtils.beforeOrEqual;
import static utils.DateUtils.formatDate;
import static utils.DateUtils.later;
import static utils.DateUtils.parseDate;
import static utils.DateUtils.TIMEUNIT.day;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import cache.Cache;
import cache.Synchronizer;
import db.DB;
import db.DBConfig;
import db.MyResultSet;
import db.sql.Partition;
import db.sql.SQLBuilder;

/**
 *
 * @author CPU
 */
public final class PartitionManager {

	private static class TablePart {

		private String table;
		private int partId;

		public TablePart(String table, int partId) {
			this.table = table;
			this.partId = partId;
		}

		@Override
		public int hashCode() {
			int hash = 3;
			hash = 43 * hash + Objects.hashCode(this.table);
			hash = 43 * hash + this.partId;
			return hash;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final TablePart other = (TablePart) obj;
			if (!Objects.equals(this.table, other.table)) {
				return false;
			}
			if (this.partId != other.partId) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			return table + partId;
		}

		private String nextPart() {
			return table + (partId + 1);
		}

	}

	private static final Cache<TablePart, Date> cache = new Cache<>(new Synchronizer<TablePart, Date>() {

		@Override
		public boolean put(TablePart key, Date value) {
			return DB.instance().preparedExecute(new SQLBuilder(DBConfig.SCHEMA, DBConfig.PARTITION_INFO)
					.insertWithUpdate("`date`=?", "table", "part", "date"), 1,
					key.table, key.partId, value, value);
		}

		@Override
		public boolean remove(TablePart key, Date value) {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		}

		@Override
		public void init(Map<TablePart, Date> data) {
			MyResultSet rs = DB.instance().simpleQuery(new SQLBuilder(DBConfig.SCHEMA, DBConfig.PARTITION_INFO)
					.select(null, "table", "part", "date"));
			while (rs.next()) {
				String table = rs.getString("table");
				data.put(new TablePart(table, rs.getInt("part")), rs.getDate("date"));
				Integer count = TABLE_PARTCOUNT.get(table);
				if (null == count) {
					count = 1;
				} else {
					count++;
				}
				TABLE_PARTCOUNT.put(table, count);
			}
		}
	});

	private static final Map<String, Integer> TABLE_PARTCOUNT = new ConcurrentHashMap<>();
	private static final int MAX_COUNT = 500000;

	public synchronized static void insert(String table, Insertion ins) {
//		insert(table, parseDateTime("2014-06-27 00:00:00"), ins);
		insert(table, new Date(), ins);
	}
	
	public synchronized static void insert(String table, Date now, Insertion ins) {
		cache.init();
		TablePart key = new TablePart(table, TABLE_PARTCOUNT.get(table) - 1);
		boolean suc = true;
		if (beforeOrEqual(cache.get(key), now)) {
			System.out.println("如果需要就添加分区");
			// 如果需要就添加分区
			MyResultSet rs = DB.instance().simpleQuery(String.format("SELECT count(*) as `count` FROM `%s`.`%s` PARTITION (%s)",
					DBConfig.SCHEMA, table, key.toString()));
			while (rs.next()) {
				long count = rs.getLong("count");
				String lessThan = formatDate(later(7, day, now));
				if (MAX_COUNT <= count) {
					// 添加分区
					suc = DB.instance().simpleExecute(new SQLBuilder(DBConfig.SCHEMA, table)
							.addPartition(new Partition(key.nextPart(),
											String.format("TO_DAYS('%s')", lessThan))));
					if (suc && cache.put(new TablePart(table, key.partId + 1), parseDate(lessThan))) {
						TABLE_PARTCOUNT.put(table, key.partId + 1 + 1);
					} else {
						suc = false;
					}
				} else {
					// 修改分区限制
					suc = DB.instance().simpleExecute(new SQLBuilder(DBConfig.SCHEMA, table)
							.reorganizePartition(new Partition(key.toString(),
											String.format("TO_DAYS('%s')", lessThan))));
					if (!(suc && cache.put(key, parseDate(lessThan)))) {
						suc = false;
					}
				}
			}
		}
		if (suc) {
			ins.insert(now);
		}
	}

	public static void select(String table, Date from, Date to, Selection sec) {
		cache.init();
		List<String> partitionList = new ArrayList<>();
		boolean firstAdd = true, lastAdded = false;
		for (int i = 0; i < TABLE_PARTCOUNT.get(table); i++) {
			TablePart key = new TablePart(table, i);
			Date lessThan = cache.get(key);
			if (firstAdd && before(from, lessThan)) {
				partitionList.add(key.toString());
				firstAdd = false;
				if (before(to, lessThan)) {
					lastAdded = true;
				}
			}
			if (!lastAdded) {
				partitionList.add(key.toString());
				if (before(to, lessThan)) {
					lastAdded = true;
				}
			}
		}
		sec.select(partitionList);
	}

}
