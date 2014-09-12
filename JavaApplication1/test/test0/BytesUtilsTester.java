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

	
	public void testIntCoding() {
		class Valider {
			byte[] buf = null;
			void valid(int i) {
				buf = BytesUtils.encodeInt(i);
				int bi = BytesUtils.decodeInt(buf, new Offset());
				if (bi != i) {
					System.err.println(String.format("Wrong with %d while bi = %d, and buf = %s", i, bi
							, Arrays.toString(buf)));
				}
			}
		};
		Valider v = new Valider();
		Debugger.tc(() -> {
			for (int i = Integer.MIN_VALUE; i < Integer.MAX_VALUE; i++) {
				v.valid(i);
				if (i == Integer.MAX_VALUE - 1)  {
					System.out.println("Fuck Stoping.");
				}
			}
			v.valid(Integer.MAX_VALUE);
		}, (cost) -> {
			System.out.println(String.format("testIntCoding cost %fs.", cost / Math.pow(10, 9)));
		});
	}

	@Test
	public void testLongCoding() {
		class Valider {
			byte[] buf = null;
			void valid(long l) {
				buf = BytesUtils.encodeLong(l);
				long bl = BytesUtils.decodeLong(buf, new Offset());
				if (bl != l) {
					System.err.println(String.format("Wrong with %d while bl = %d, and buf = %s", l, bl
							, Arrays.toString(buf)));
				}
			}
		};
		Valider v = new Valider();
		Debugger.tc(() -> {
			for (long l = Long.MIN_VALUE; l != 0; l /= 2) {
				v.valid(l);
			}
			for (long l = Long.MAX_VALUE; l != 0; l /= 2) {
				v.valid(l);
			}
		}, (cost) -> {
			System.out.println(String.format("testShortCoding cost %fs.", cost / Math.pow(10, 9)));
		});
	}

	@Test
	public void testFLoatCoding() {
		float f = -1.3f;
		byte[] buf = BytesUtils.encodeFloat(f);
		float bf = BytesUtils.decodeFloat(buf, new Offset());
		System.out.println(f == bf);
	}
	
}
