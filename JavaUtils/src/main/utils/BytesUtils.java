/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author caolisheng
 */
public class BytesUtils {

    public static String decodeString(byte[] data, Offset offset, int len) {
        byte[] strSource = new byte[len];
        System.arraycopy(data, offset.forwardROld(len), strSource, 0, len);
        try {
            return new String(strSource, "UTF8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BytesUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static byte[] encodeString(String str) {
        try {
            return str.getBytes("UTF8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BytesUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static boolean decodeBoolean(byte[] data, Offset offset) {
        return 1 == data[offset.forwardROld(1)];
    }
    
    public static byte[] encodeBoolean(boolean b) {
        return new byte[]{ b ? (byte) 1 : (byte) 0 };
    }
    
    public static byte decodeByte(byte[] data, Offset offset) {
        return data[offset.forwardROld(1)];
    }
    
    public static byte[] encodeByte(byte b) {
        return new byte[]{ b };
    }
    
    private static int decodeByte2Int(byte b) {
    	return (b < 0) ? (b & ((1 << 8) - 1)) | (1 << 7) : b;
    }
    
    public static short decodeShort(byte[] data, Offset offset) {
    	int high = decodeByte2Int(data[offset.forwardROld(1)]);
    	int low = decodeByte2Int(data[offset.forwardROld(1)]);
    	Debugger.debug(BytesUtils.class, () -> {
    		System.out.println(String.format("data = %s, (high << 8) = %d", Arrays.toString(data), (high << 8)));
    	});
        return (short) ((high << 8) | low);
    }
    
    private static final int R0 = (1 << 8) - 1;
    
    public static byte[] encodeShort(short s) {
    	int high = s >> 8;
		int low = s & R0;
    	Debugger.debug(BytesUtils.class, () -> {
    		System.out.println(String.format("s = %d, high = %d, low = %d", s, high, low));
    	});
        return new byte[]{ (byte) high, (byte) low };
    }

    public static char decodeChar(byte[] data, Offset offset) {
    	return (char) decodeShort(data, offset);
    }
    
    public static byte[] encodeChar(char c) {
    	return encodeShort((short) c);
    }
    
    public static int decodeInt(byte[] data, Offset offset) {
    	int l0 = decodeByte2Int(data[offset.forwardROld(1)]);
    	int l1 = decodeByte2Int(data[offset.forwardROld(1)]);
    	int l2 = decodeByte2Int(data[offset.forwardROld(1)]);
    	int l3 = decodeByte2Int(data[offset.forwardROld(1)]);
    	return (l0 << 24) | (l1 << 16) | (l2 << 8) | l3;
    }

    public static byte[] encodeInt(int i) {
    	int l0 = i >> 24;
    	int l1 = (i >> 16) & R0;
    	int l2 = (i >> 8) & R0;
    	int l3 = i & R0;
    	return new byte[]{ (byte) l0, (byte) l1, (byte) l2, (byte) l3 };
    }
    
    private static long decodeByte2Long(byte b) {
    	return decodeByte2Int(b);
    }
    
    public static long decodeLong(byte[] data, Offset offset) {
    	long ret = 0;
    	for(int i = 64 -8; i >= 0; i -= 8) {
    		ret = (decodeByte2Long(data[offset.forwardROld(1)]) << i) | ret;
    	}
    	return ret;
    }
    
    public static byte[] encodeLong(long l) {
    	byte[] bs = new byte[8];
    	for (int index = 0, i = 64 - 8; i >= 0; i -= 8) {
    		bs[index++] = (byte) ((l >> i) & R0);
    	}
    	return bs;
    }
    
    public static float decodeFloat(byte[] data, Offset offset) {
    	return Float.intBitsToFloat(decodeInt(data, offset));
    }
    
    public static byte[] encodeFloat(float f) {
    	return encodeInt(Float.floatToIntBits(f));
    }
    
    public static double decodeDouble(byte[] data, Offset offset) {
    	return Double.longBitsToDouble(decodeLong(data, offset));
    }
    
    public static byte[] encodeDouble(double d) {
    	return encodeLong(Double.doubleToLongBits(d));
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    public static class Offset {

        private int offset = 0;

        public Offset forward(int step) {
            this.offset += step;
            return this;
        }

        public int forwardROld(int step) {
            int old = this.offset;
            this.offset += step;
            return old;
        }

        public int forwardRNew(int step) {
            this.offset += step;
            return this.offset;
        }

        public int get() {
            return this.offset;
        }
    }

    public static class Bytes {

        private byte[] bytes;
        private Offset offset;

        public byte[] get() {
            if (null == this.bytes)
                return new byte[0];
            byte[] ret = new byte[this.offset.get()];
            System.arraycopy(this.bytes, 0, ret, 0, this.offset.get());
            return ret;
        }

        public Bytes append(byte[] data) {
        	if (null == data)
        		return this;
        	
            if (null == this.bytes) {
                this.offset = new Offset();
                this.bytes = new byte[this.initLength(data.length)];
                System.arraycopy(data, 0, this.bytes, 0, data.length);
                return this;
            }

            this.expandIfNeeded(data.length);
            System.arraycopy(data, 0, this.bytes, this.offset.forwardROld(data.length), data.length);
            return this;
        }

		private void expandIfNeeded(int len) {
			if (this.bytes.length - this.offset.get() < len) {
                // 扩容
                byte[] newBytes = new byte[this.bytes.length * 2];
                System.arraycopy(this.bytes, 0, newBytes, 0, this.offset.get());
                this.bytes = newBytes;
            }
		}

		public Bytes append(byte b) {
			if (null == this.bytes) {
				this.offset = new Offset();
                this.bytes = new byte[this.initLength(1)];
                this.bytes[0] = b;
                return this;
			}
			
			this.expandIfNeeded(1);
            this.bytes[this.offset.forwardROld(1)] = b;
            return this;
		}

        private int initLength(int length) {
            int len = this.offset.forwardRNew(length) / 2 * 4;
            return (len < 16) ? 16 : len;
        }
    }

}