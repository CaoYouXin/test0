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
public final class Partition {

	private String name;
	private String lessThan;

	public Partition(String name, String lessThan) {
		this.name = name;
		this.lessThan = lessThan;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("PARTITION %s VALUES LESS THAN (%s) ENGINE = InnoDB", name, lessThan);
	}

}
