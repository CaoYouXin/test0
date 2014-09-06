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
public class BytesUtils {

    public static String getString(byte[] data, Offset offset, int len) {
        byte[] strSource = new byte[len];
        System.arraycopy(data, offset.forwardROld(len), strSource, 0, len);
        return new String(strSource);
    }
    
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
    
}
