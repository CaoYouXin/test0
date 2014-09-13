/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author caolisheng
 */
public enum TypeSize {

    Boolean(20),
    Byte(20),
    Char(40),
    Short(40),
    Integer(80),
    Long(120),
    Float(80),
    Double(120),
    String(130),
    Other(150);

    private int size;

    private TypeSize(int size) {
        this.size = size;
    }
    
    public int size() {
        return this.size;
    }

    public static TypeSize val(String type) {
        for (TypeSize ats : TypeSize.values()) {
            String aname = ats.name();
            if (aname.equalsIgnoreCase(type)) {
                return ats;
            }
        }
        return Other;
    }

	public static boolean isOther(Object o) {
		return Other == val(o.getClass().getSimpleName());
	}

}
