/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idgen;

import cn.clscpu.storehouse.cache.Cache;
import cn.clscpu.storehouse.cache.Synchronizer;
import cn.clscpu.storehouse.util.db.DB;
import cn.clscpu.storehouse.util.db.DBConfig;
import cn.clscpu.storehouse.util.db.MyResultSet;
import cn.clscpu.storehouse.util.db.sql.SQLBuilder;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author CPU
 */
public final class IDGenerator {

	private static Cache<String, Long> cache = new Cache<>(new Synchronizer<String, Long>() {

		@Override
		public boolean put(String key, Long value) {
			return DB.instance().preparedExecute(new SQLBuilder(DBConfig.SCHEMA, DBConfig.IDGENERATOR).insertWithUpdate("`id`=?", "table", "id"), 1, key, value, value);
		}

		@Override
		public boolean remove(String key, Long value) {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		}

		@Override
		public void init(Map<String, Long> data) {
			MyResultSet rs = DB.instance().simpleQuery(new SQLBuilder(DBConfig.SCHEMA, DBConfig.IDGENERATOR).select("1=1", "table", "id"));
			while (rs.next()) {
				data.put(rs.getString("table"), rs.getLong("id"));
			}
		}

	});

	public synchronized static void next(String table, UseId use) {
		Long id = cache.get(table);
		if (null == id) {
			id = new Long(1);
		}
		if (use.useId(id)) {
			cache.put(table, id + 1);
		}
	}
	
	public synchronized static void nextEX(String table, UseIdEX use) throws SQLException {
		Long id = cache.get(table);
		if (null == id) {
			id = new Long(1);
		}
		if (use.useId(id)) {
			cache.put(table, id + 1);
		}
	}

}
