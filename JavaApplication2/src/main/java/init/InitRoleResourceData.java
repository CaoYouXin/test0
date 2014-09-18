/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package init;

import db.DB;
import db.sql.SQLBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CPU
 */
public class InitRoleResourceData implements Init {

	private String schema, table;
	private String[] columns;
	private List<Object[]> data;
	private Map<String, Integer> datasource;
	
	public InitRoleResourceData(String schema, String table, Map<String, Integer> datasource, String... columns) {
		this.schema = schema;
		this.table = table;
		this.datasource = datasource;
		this.columns = columns;
	}

	public InitRoleResourceData addData(Integer roleId, String... row) {
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
