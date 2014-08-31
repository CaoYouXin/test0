/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

/**
 * @desc 把一句if语句包装在一个类中，顺便用一下lambda表达式。
 * @author caolisheng
 */
public class Debugger {
    
    private static final boolean isDebugging = true;
    
    public interface DoDebug {
        void debug();
    }
    
    public static void debug(DoDebug fn) {
        if (isDebugging)
            fn.debug();
    }
    
}
