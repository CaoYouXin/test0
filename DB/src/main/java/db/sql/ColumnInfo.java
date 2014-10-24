/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.sql;

/**
 *
 * @author CPU
 */
public final class ColumnInfo {

	public static enum Type {

		integer("int(11)"),
		bitint("bigint(20)"),
		shorttext("varchar(100)"),
		longtext("varchar(1000)"),
		bool("bit(1)"),
		datetime("datetime");

		private String type;

		private Type(String type) {
			this.type = type;
		}

	}

	private String name;
	private Type type;

	public ColumnInfo(String name, Type type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public String toString() {
		return String.format("`%s` %s NOT NULL", name, type.type);
	}

}
