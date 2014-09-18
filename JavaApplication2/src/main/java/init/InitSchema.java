/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package init;

import db.DB;
import db.sql.SQLBuilder;

/**
 *
 * @author CPU
 */
public class InitSchema implements Init {

	private String schema;

	public InitSchema(String schema) {
		this.schema = schema;
	}

	@Override
	public boolean init() {
		return DB.instance().simpleExecute(String.format("drop database if exists `%s`", schema), 0)
				&& DB.instance().simpleExecute(new SQLBuilder(schema, null).createSchema(), 1);
	}

}
