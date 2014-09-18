/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package init;

import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.sql.SQLBuilder;

/**
 *
 * @author CPU
 */
public class InitData implements Init {

	private String schema, table;
	private String[] columns;
	private List<Object[]> data;

	public InitData(String schema, String table, String... columns) {
		this.schema = schema;
		this.table = table;
		this.columns = columns;
	}

	public InitData addData(Object... row) {
		if (data == null) {
			data = new ArrayList<>();
		}
		data.add(row);
		return this;
	}
	
	public InitData setData(List<Object[]> data) {
		this.data = data;
		return this;
	}

	@Override
	public boolean init() {
		return DB.instance().batchExecute(new SQLBuilder(schema, table).insert(columns), data.size(), data);
	}

}
