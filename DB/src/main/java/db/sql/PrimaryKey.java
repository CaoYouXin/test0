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
public final class PrimaryKey {

	private List<String> pKeys;

	public PrimaryKey(String... keys) {
		pKeys = new ArrayList<>(Arrays.asList(keys));
	}

	@Override
	public String toString() {
		return String.format("PRIMARY KEY (%s)", wrap(pKeys));
	}

}
