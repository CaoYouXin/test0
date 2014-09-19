/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package init;

import utils.Suc;
import utils.flag.FlagSupport;
import db.DB;
import db.sql.SQLBuilder;

/**
 *
 * @author CPU
 */
public class InitSchema extends Init {

	private static final String DROP_IF_EXIST = "DropIfExist";
	private FlagSupport flags = new FlagSupport();
	private String schema;

	public InitSchema(boolean dropIfExist, String schema) {
		this.flags.set(DROP_IF_EXIST, dropIfExist);
		this.schema = schema;
	}

	@Override
	public boolean init() {
		Suc suc = new Suc();
		flags.flagDo((params) -> {
			return suc.val(DB.instance().simpleExecute(String.format("drop database if exists `%s`", schema), 0));
		});
		return suc.val() && DB.instance().simpleExecute(new SQLBuilder(schema, null).createSchema(), 1);
	}

}
