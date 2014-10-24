/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package init;

import java.util.Map;

/**
 * with single data source.
 * @author CPU
 */
public class InitDataWSDS extends InitData {

	private Map<Object, Object> datasource;
	
	public InitDataWSDS(String schema, String table, Map<Object, Object> datasource, String... columns) {
		super(schema, table, columns);
		this.datasource = datasource;
	}

	public InitDataWSDS addData(int i, Object... row) {
		row[i] = this.datasource.get(row[i]);
		this.addData(row);
		return this;
	}

}
