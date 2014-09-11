package test0;

import java.util.Arrays;

import org.junit.Test;

import util.BytesUtils;
import util.BytesUtils.Offset;
import util.Debugger;

/**
 * 
 * @author caolisheng
 */
public class BytesUtilsTester {

	@Test
	public void testShortCoding() {
		Debugger.tc(() -> {
			byte[] buf = null;
			for (int s = Short.MIN_VALUE; s <= Short.MAX_VALUE; s++) {
				buf = BytesUtils.encodeShort((short) s);
				short bs = BytesUtils.decodeShort(buf, new Offset());
				if (bs != s) {
					System.err.println(String.format("Wrong with %d while bs = %d, and buf = %s", s, bs
							, Arrays.toString(buf)));
				}
			}
		}, (cost) -> {
			System.out.println(String.format("testShortCoding cost %fs.", cost / Math.pow(10, 9)));
		});
	}

}
