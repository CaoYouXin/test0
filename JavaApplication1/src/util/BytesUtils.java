/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.UnsupportedEncodingException;
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

    public static short decodeShort(byte[] data, Offset offset) {
        return 0;
    }
    
    public static byte[] encodeShort(short s) {
        return new byte[0];
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
            if (null == this.bytes) {
                this.offset = new Offset();
                this.bytes = new byte[this.initLength(data.length)];
                System.arraycopy(data, 0, this.bytes, 0, data.length);
                return this;
            }

            if (this.bytes.length - this.offset.get() < data.length) {
                // 扩容
                byte[] newBytes = new byte[this.bytes.length * 2];
                System.arraycopy(this.bytes, 0, newBytes, 0, this.offset.get());
                this.bytes = newBytes;
            }
            System.arraycopy(data, 0, this.bytes, this.offset.forwardROld(data.length), data.length);
            return this;
        }

        private int initLength(int length) {
            int len = this.offset.forwardRNew(length) / 2 * 4;
            return (len < 16) ? 16 : len;
        }
    }

}
