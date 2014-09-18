/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.sql;

import static utils.StringUtils.wrap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author CPU
 */
public final class Index {

	private boolean isUnique;
	private String name;
	private List<String> cols;

	public Index(boolean isUnique, String name, String... cols) {
		this.isUnique = isUnique;
		this.name = name;
		this.cols = new ArrayList<>(Arrays.asList(cols));
	}

	@Override
	public String toString() {
		return String.format("%s KEY `%s` (%s) USING BTREE", isUnique ? "UNIQUE" : "", name, wrap(cols));
	}

}
