/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.sql;

import static utils.StringUtils.*;
import java.util.List;

/**
 *
 * @author CPU
 */
public final class SQLBuilder {

	private String schema, table;

	public SQLBuilder(String schemaName, String tableName) {
		this.schema = schemaName;
		this.table = tableName;
	}

	public String insert(String... columns) {
		String format = String.format("INSERT INTO `%s`.`%s`(%s) VALUES(%s)", schema, table, wrap(columns), replace(columns));
		System.out.println(format);
		return format;
	}

	public String insertWithUpdate(String update, String... columns) {
		String format = String.format("INSERT INTO `%s`.`%s`(%s) VALUES(%s) ON DUPLICATE KEY UPDATE %s", schema, table, wrap(columns), replace(columns), update);
		System.out.println(format);
		return format;
	}

	public String update(String whereClause, String... columns) {
		String format = String.format("UPDATE `%s`.`%s` SET %s WHERE %s", schema, table, map(columns), whereClause);
		System.out.println(format);
		return format;
	}

	public String delete(String whereClause) {
		String format = String.format("DELETE FROM `%s`.`%s` WHERE %s", schema, table, whereClause);
		System.out.println(format);
		return format;
	}

	public String select(String whereClause, String... columns) {
		String format = null;
		if (null == whereClause || whereClause.isEmpty()) {
			format = String.format("SELECT %s FROM `%s`.`%s`", wrap(columns), schema, table);
		} else {
			format = String.format("SELECT %s FROM `%s`.`%s` WHERE %s", wrap(columns), schema, table, whereClause);
		}
		System.out.println(format);
		return format;
	}

	public String createSchema() {
		String format = String.format("CREATE DATABASE `%s` DEFAULT CHARACTER SET utf8", schema);
		System.out.println(format);
		return format;
	}

	public String createTable(List<ColumnInfo> cols, PrimaryKey pk, List<Index> uis, Partitioning partitioning) {
		if (null == cols || 0 == cols.size()) {
			throw new RuntimeException("建表时必须至少包含一列！");
		}
		StringBuilder sb = new StringBuilder();
		cols.stream().forEach((col) -> {
			sb.append(',').append(col.toString());
		});
		if (pk != null) {
			sb.append(',').append(pk.toString());
		}
		if (uis != null) {
			uis.stream().forEach((ui) -> {
				sb.append(',').append(ui.toString());
			});
		}
		String format = String.format("CREATE TABLE `%s`.`%s` (%s) ENGINE=InnoDB DEFAULT CHARSET=utf8 %s",
				schema, table, sb.length() > 0 ? sb.substring(1) : "",
				null != partitioning ? partitioning.toString() : "");
		System.out.println(format);
		return format;
	}

	public String addPartition(Partition partition) {
		return String.format("ALTER TABLE `%s`.`%s` ADD PARTITION (%s)", schema, table, partition.toString());
	}

	public String reorganizePartition(Partition partition) {
		return String.format("ALTER TABLE `%s`.`%s` REORGANIZE PARTITION %s INTO (%s)", schema, table,
				partition.getName(), partition.toString());
	}

}
