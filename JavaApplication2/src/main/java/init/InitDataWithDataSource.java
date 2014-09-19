/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package init;

import java.util.ArrayList;
import java.util.Map;

import db.DB;
import db.sql.SQLBuilder;

/**
 *
 * @author CPU
 */
public class InitDataWithDataSource extends InitData {

	private Map<String, Integer> datasource;
	
	public InitDataWithDataSource(String schema, String table, String from, String to, Map<String, Integer> datasource, String... columns) {
		super(schema, table, columns);
		// TODO
	}

	public InitDataWithDataSource addData(Integer roleId, String... row) {
		if (data == null) {
			data = new ArrayList<>();
		}
		for (int i = 0; i < row.length; i++) {
			String sk = row[i];
			Integer resourceId = datasource.get(sk);
			data.add(new Object[]{ roleId, resourceId});
		}
		return this;
	}
	
	@Override
	public boolean init() {
		return DB.instance().batchExecute(new SQLBuilder(schema, table).insert(columns), data.size(), data);
	}

}
