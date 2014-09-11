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

	public static void debug(DebugType dt, DoDebug fn) {
		if (dt.val())
			fn.debug();
	}

	public static void debug(Class clazz, DoDebug fn) {
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
