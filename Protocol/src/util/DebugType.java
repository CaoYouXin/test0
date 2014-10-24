package util;

import java.util.Objects;


public enum DebugType {

	BytesUtils(/*"BytesUtils", */false);
	
//	private String name;
	private boolean isOn;
	
	private DebugType(/*String name, */boolean isOn) {
//		this.name = name;
		this.isOn = isOn;
	}
	
	public static DebugType val(String name) {
		for (DebugType dt : DebugType.values()) {
			if (Objects.equals(name, dt.name())) {
				return dt;
			}
		}
		return null;
	}

	public boolean val() {
		return this.isOn;
	}
	
}
