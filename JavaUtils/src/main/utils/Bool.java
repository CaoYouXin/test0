/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Objects;

/**
 *
 * @author CPU
 */
public enum Bool {

	True(true),
	False(false),
	Null(null);

	private Boolean isTrue;

	private Bool(Boolean isTrue) {
		this.isTrue = isTrue;
	}

	public Boolean val() {
		return isTrue;
	}
	
	public static Bool valuseOf(Boolean isTrue) {
		for (Bool bool : values()) {
			if (Objects.equals(isTrue, bool.isTrue)) {
				return bool;
			}
		}
		return null;
	}
	
	public String strVal() {
		return StringUtils.valueOf(toString());
	}

	public String boolStrVal() {
		if (null == isTrue) {
			return "null";
		}
		if (isTrue) {
			return "true";
		} else {
			return "false";
		}
	}
	
}
