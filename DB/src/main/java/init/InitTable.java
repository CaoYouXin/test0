/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package init;

import java.util.ArrayList;
import java.util.List;

import utils.Suc;
import utils.flag.FlagSupport;
import db.DB;
import db.sql.ColumnInfo;
import db.sql.Index;
import db.sql.Partitioning;
import db.sql.PrimaryKey;
import db.sql.SQLBuilder;

/**
 *
 * @author CPU
 */
public class InitTable extends Init {

	private static final String DROP_IF_EXIST = "DropIfExist";
	private FlagSupport flags = new FlagSupport();
	private String schema;
	private String table;
	private List<ColumnInfo> cols;
	private List<Index> indexes;
	private PrimaryKey pk;
	private Partitioning partitioning;

	public InitTable(boolean dropIfExist, String schema, String table) {
		this.flags.set(DROP_IF_EXIST, dropIfExist);
		this.schema = schema;
		this.table = table;
	}

	public InitTable addColumn(ColumnInfo info) {
		if (cols == null) {
			cols = new ArrayList<>();
		}
		cols.add(info);
		return this;
	}

	public InitTable setPk(PrimaryKey pk) {
		this.pk = pk;
		return this;
	}

	public InitTable addIndex(Index index) {
		if (indexes == null) {
			indexes = new ArrayList<>();
		}
		indexes.add(index);
		return this;
	}

	public InitTable setPartitioning(Partitioning partitioning, SetPartition setter) {
		this.partitioning = partitioning;
		if (null == partitioning) {
			return this;
		}
		for (int i = 0; i < partitioning.getCount(); i++) {
			partitioning.addPartition(setter.set(i));
		}
		return this;
	}

	@Override
	public boolean init() {
		Suc suc = new Suc();
		flags.flagDo((params) -> {
			suc.val(DB.instance().simpleExecute(String.format("DROP TABLE IF EXISTS `%s`.`%s`", schema, table), 0));
		});
		return suc.val()
			&& DB.instance().simpleExecute(new SQLBuilder(schema, table).createTable(cols, pk, indexes, partitioning), 0);
	}

	@Override
	public String toString() {
		String sql = new SQLBuilder(schema, table).createTable(cols, pk, indexes, partitioning);
		return String.format("SQL=[%s].", sql);
	}

}
