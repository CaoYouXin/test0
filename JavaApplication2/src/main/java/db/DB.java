/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author CPU
 *
 * 关系数据库操作类： 1、普通查询：参数必须是不含占位符的字符串 2、带占位符的查询：参数可以是String(sql)，Object... 3、普通增删改：参数同样必须是String(sql)
 * 4、带占位符的增删改：参数可以是String(sql)，Object... 注：Object...需要通过instanceof检测，代换成合适的字符串
 * 另注：Object的类型除包括char之外的七大类型外，还对java.util.Date类型做了特殊处理， 替换成了long，其余各种类型，都调用toString方法
 */
public final class DB {

	//Begin 单例模式
	private static final DB INSTANCE = new DB();

	public static DB instance() {
		return INSTANCE;
	}
	//End 单例模式

	private DataSource ds;

	private DB() {
		try {
			InitialContext ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MySQLDB");
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, ds.toString());
		} catch (NamingException ex) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public MyResultSet simpleQuery(String sql) {
		String[] labels = parseLabels(sql);
		try (Connection conn = ds.getConnection()) {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			return new MyResultSet(rs, labels);
		} catch (SQLException ex) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
			return new MyResultSet();
		}
	}

	public MyResultSet preparedQuery(String sql, Object... params) {
		String[] labels = parseLabels(sql);
		try (Connection conn = ds.getConnection()) {
			PreparedStatement stat = conn.prepareStatement(sql);
			parsePlaceholders(stat, params);
			ResultSet rs = stat.executeQuery();
			return new MyResultSet(rs, labels);
		} catch (SQLException ex) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
			return new MyResultSet();
		}
	}

	public boolean simpleExecute(String sql, int expected) {
		boolean isInsert = isInsert(sql);
		boolean isDrop = isDrop(sql);
		try (Connection conn = ds.getConnection()) {
			Statement stat = conn.createStatement();
			int ret = stat.executeUpdate(sql);
			return asExpected(isInsert, ret, expected, isDrop);
		} catch (SQLException ex) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	public boolean simpleExecute(String sql) {
		try (Connection conn = ds.getConnection()) {
			Statement stat = conn.createStatement();
			stat.executeUpdate(sql);
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	public boolean simpleExecute(Connection conn, String sql, int expected) throws SQLException {
		boolean isInsert = isInsert(sql);
		boolean isDrop = isDrop(sql);
		Statement stat = conn.createStatement();
		int ret = stat.executeUpdate(sql);
		return asExpected(isInsert, ret, expected, isDrop);
	}

	public boolean simpleExecute(Connection conn, String sql) throws SQLException {
		Statement stat = conn.createStatement();
		stat.executeUpdate(sql);
		return true;
	}

	public boolean preparedExecute(String sql, int expected, Object... params) {
		boolean isInsert = isInsert(sql);
		boolean isDrop = isDrop(sql);
		try (Connection conn = ds.getConnection()) {
			PreparedStatement stat = conn.prepareStatement(sql);
			parsePlaceholders(stat, params);
			int ret = stat.executeUpdate();
			return asExpected(isInsert, ret, expected, isDrop);
		} catch (SQLException ex) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	public boolean preparedExecute(Connection conn, String sql, int expected, Object... params) throws SQLException {
		boolean isInsert = isInsert(sql);
		boolean isDrop = isDrop(sql);
		PreparedStatement stat = conn.prepareStatement(sql);
		parsePlaceholders(stat, params);
		int ret = stat.executeUpdate();
		return asExpected(isInsert, ret, expected, isDrop);
	}

	public boolean batchExecute(String sql, int expected, List<Object[]> params) {
		boolean isInsert = isInsert(sql);
		boolean isDrop = isDrop(sql);
		Connection conn = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stat = conn.prepareStatement(sql);
			for (Object[] objects : params) {
				parsePlaceholders(stat, objects);
				stat.addBatch();
			}
			int[] counts = stat.executeBatch();
			conn.commit();
			int ret = 0;
			for (int count : counts) {
				ret += count;
			}
			return asExpected(isInsert, ret, expected, isDrop);
		} catch (SQLException ex) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
			if (null != conn) {
				try {
					conn.rollback();
				} catch (SQLException e) {
					Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
				}
			}
			return false;
		} finally {
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException ex) {
					Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

	public boolean batchExecute(String sql, int expected, RowParams params) {
		boolean isInsert = isInsert(sql);
		boolean isDrop = isDrop(sql);
		Connection conn = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stat = conn.prepareStatement(sql);
			for (int i = 0; i < expected; i++) {
				Object[] objects = params.row(i);
				parsePlaceholders(stat, objects);
				stat.addBatch();
			}
			int[] counts = stat.executeBatch();
			conn.commit();
			int ret = 0;
			for (int count : counts) {
				ret += count;
			}
			return asExpected(isInsert, ret, expected, isDrop);
		} catch (SQLException ex) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
			if (null != conn) {
				try {
					conn.rollback();
				} catch (SQLException e) {
					Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
				}
			}
			return false;
		} finally {
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException ex) {
					Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

	public void batchExecute(Connection conn, String sql, int expected, List<Object[]> params) throws SQLException {
		PreparedStatement stat = conn.prepareStatement(sql);
		for (Object[] objects : params) {
			parsePlaceholders(stat, objects);
			stat.addBatch();
		}
		stat.executeBatch();
	}
	
	public void batchExecute(Connection conn, String sql, int expected, RowParams params) throws SQLException {
		PreparedStatement stat = conn.prepareStatement(sql);
		for (int i = 0; i < expected; i++) {
			Object[] objects = params.row(i);
			parsePlaceholders(stat, objects);
			stat.addBatch();
		}
		stat.executeBatch();
	}

	public boolean transaction(Transaction trans) {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			trans.trans(conn);
			conn.commit();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
			if (null != conn) {
				try {
					conn.rollback();
				} catch (SQLException e) {
					Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
				}
			}
			return false;
		} finally {
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException ex) {
					Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

	private static boolean asExpected(boolean isInsert, int ret, int expected, boolean isDrop) {
//		System.out.println(String.format("%s %d %d %s", isInsert, ret, expected, isDrop));
		if (isDrop) {
			return true;
		}
		return isInsert ? (asExpectedWhenInsert(ret, expected)) : expected == ret;
	}

	private static boolean asExpectedWhenInsert(int ret, int expected) {
		return (ret >= expected && ret <= (2 * expected));
	}

	private boolean isInsert(String sql) {
		return sql.trim().toUpperCase().startsWith("INSERT");
	}

	private void parsePlaceholders(PreparedStatement stat, Object[] params) throws SQLException {
		int index = 1;
		for (Object obj : params) {
			if (obj instanceof Integer) {
				stat.setInt(index++, (int) obj);
			} else if (obj instanceof Short) {
				stat.setShort(index++, (short) obj);
			} else if (obj instanceof Byte) {
				stat.setByte(index++, (byte) obj);
			} else if (obj instanceof Boolean) {
				stat.setBoolean(index++, (boolean) obj);
			} else if (obj instanceof Long) {
				stat.setLong(index++, (long) obj);
			} else if (obj instanceof Date) {
				stat.setTimestamp(index++, new Timestamp(((Date) obj).getTime()));
			} else if (obj instanceof String) {
				stat.setString(index++, (String) obj);
			} else if (obj instanceof Float) {
				stat.setFloat(index++, (float) obj);
			} else if (obj instanceof Double) {
				stat.setDouble(index++, (double) obj);
			} else {
				stat.setString(index++, obj.toString());
			}
		}
	}

	private String[] parseLabels(String sql) {
		if (isShowTables(sql)) {
			return new String[]{"Tables_in_test"};
		}
		String upperSql = sql.toUpperCase();
		String select = "SELECT";
		sql = sql.substring(upperSql.indexOf(select) + select.length());
		upperSql = sql.toUpperCase();
		String from = "FROM";
		sql = sql.substring(0, upperSql.indexOf(from));
		String[] labels = sql.split(",");
		for (int i = 0; i < labels.length; i++) {
			String label = labels[i];
			if (label.contains("`")) {
				label = label.replaceAll("`", "");
			}
			if (label.toLowerCase().contains("as")) {
				labels[i] = label.substring(label.toLowerCase().indexOf("as")+2).trim();
			} else if (label.contains(".")) {
				labels[i] = label.substring(label.indexOf(".")+1).trim();
			} else {
				labels[i] = label.trim();
			}
		}
		return labels;
	}

	private boolean isShowTables(String sql) {
		return sql.toUpperCase().startsWith("SHOW TABLES IN");
	}

	private boolean isDrop(String sql) {
		return sql.toUpperCase().startsWith("DROP");
	}

}
