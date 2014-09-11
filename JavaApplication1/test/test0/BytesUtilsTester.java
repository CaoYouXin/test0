package test0;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import util.BytesUtils;
import util.Debugger;
import util.BytesUtils.Offset;

/**
 * Created by caolisheng on 2014/9/11 011.
 */
public class BytesUtilsTester {

	@Test
	public void testShort() {
		Debugger.timeCounting(() -> {
			byte[] buf = null;
			for (int s = Short.MIN_VALUE; s <= Short.MAX_VALUE; s++) {
//		for (int s = -10; s <= 10; s++) {
				buf = BytesUtils.encodeShort((short) s);
				short bs = BytesUtils.decodeShort(buf, new Offset());
				if (bs != s) {
					System.err.println(String.format("Wrong with %d while bs = %d, and buf = %s", s, bs
							, Arrays.toString(buf)));
				}
			}
		}, (cost) -> {
			System.out.println("Time = " + cost / Math.pow(10, 9) + "s");
		});
	}

}
