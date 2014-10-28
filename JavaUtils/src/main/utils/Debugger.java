/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @desc 把一句if语句包装在一个类中，顺便用一下lambda表达式。
 * @author caolisheng
 */
public class Debugger {

	public interface DoDebug {
		void debug();
	}

	public static void debug(DoDebug fn) {
		if (isDebugging())
			fn.debug();
	}

	private static boolean isDebugging() {
		Exception hack = new Exception();
		StackTraceElement[] stackTrace = hack.getStackTrace();
		for (StackTraceElement stackTraceElement : stackTrace) {
			Class<?> mainClass = null;
			try {
				mainClass = Class.forName(stackTraceElement.getClassName());
			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
				return false;
			}
			Method mainMethod = null;
			try {
				mainMethod = mainClass.getMethod("main", String[].class);
			} catch (NoSuchMethodException e) {
//				e.printStackTrace();
				continue;
			} catch (SecurityException e) {
//				e.printStackTrace();
				return false;
			}
			if (null != mainMethod) {
				Field debuggingField;
				try {
					debuggingField = mainClass.getField("debugging");
				} catch (NoSuchFieldException e) {
//					e.printStackTrace();
					continue;
				} catch (SecurityException e) {
//					e.printStackTrace();
					return false;
				}
				if (null != debuggingField) {
					try {
						return debuggingField.getBoolean(mainClass);
					} catch (IllegalArgumentException | IllegalAccessException e) {
//						e.printStackTrace();
						return false;
					}
				} else {
					return false;
				}
			}
		}
		return true;
	}

	public static void debug(DebugType dt, DoDebug fn) {
		if (dt.val())
			fn.debug();
	}

	public static void debug(Class<? extends Object> clazz, DoDebug fn) {
		DebugType dt = DebugType.val(clazz.getSimpleName());
		if (null == dt) {
			fn.debug();
			return;
		}
		if (dt.val())
			fn.debug();
	}
	
	public interface DoSth {
		void doSth();
	}

	public interface AfterTimeCounting {
		void doATC(long cost);
	}
	
	/**
	 * time counting
	 * @param doSth
	 * @param doATC
	 */
	public static void tc(DoSth doSth, AfterTimeCounting doATC) {
		long start = System.nanoTime();
		doSth.doSth();
		long cost = System.nanoTime() - start;
		doATC.doATC(cost);
	}
	
	public interface ATCEnhanced {
		void doATC(long[] costs);
	}
	
	public static void tcEnhanced(ATCEnhanced doATC, DoSth... doSths) {
		tcEnhanced(doSths, doATC);
	}
	
	public static void tcEnhanced(DoSth[] doSths, ATCEnhanced doATC) {
		long[] costs = new long[doSths.length];
		long start;
		int i = 0;
		for (DoSth doSth : doSths) {
			start = System.nanoTime();
			doSth.doSth();
			costs[i++] = System.nanoTime() - start;
		}
		doATC.doATC(costs);
	}
	
}
