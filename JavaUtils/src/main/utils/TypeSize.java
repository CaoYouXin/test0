/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author caolisheng
 */
public enum TypeSize {

    Boolean((byte) 10),
    Byte((byte) 10),
    Char((byte) 20),
    Short((byte) 20),
    Integer((byte) 40),
    Long((byte) 80),
    Float((byte) 40),
    Double((byte) 80),
    String((byte) 100),
    Object((byte) 110),
    Array((byte) 120);

    private byte size;

    private TypeSize(byte size) {
        this.size = size;
    }
    
    public byte size() {
        return this.size;
    }

    public static TypeSize val(String type) {
        for (TypeSize ats : TypeSize.values()) {
            String aname = ats.name();
            if (aname.equalsIgnoreCase(type)) {
                return ats;
            }
        }
        return Object;
    }

	public static TypeSize val(Object o) {
		Class<? extends Object> clazz = o.getClass();
		TypeSize val = val(clazz.getSimpleName());
		return Object == val ? (clazz.isArray() ? Array : Object) : val;
	}

}
