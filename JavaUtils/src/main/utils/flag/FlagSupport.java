package utils.flag;

import static utils.StringUtils.Md5;

import java.util.HashMap;
import java.util.Map;

import utils.GeneralFn;

public class FlagSupport {

	public final String default_name = Md5("" + System.currentTimeMillis());
	
	private Map<String, Boolean> flags;
	
	public FlagSupport(String[] names, boolean... bs) {
		this.flags = new HashMap<>();
		int i = 0;
		for (; i < Math.min(names.length, bs.length); i++) {
			this.flags.put(names[i], bs[i]);
		}
		for (; i < names.length; i++) {
			this.flags.put(names[i], false);
		}
		for (int offset = i; i < bs.length; i++) {
			this.flags.put(this.default_name + (i - offset), bs[i]);
		}
	}
	
	public FlagSupport() {
	}

	public final void set(String name, boolean b) {
		this.flags.put(name, b);
	}
	
	/**
	 * fn的参数数组的长度为1,类型是String，即flag name.
	 * @param fn
	 */
	public final void flagDo(GeneralFn fn) {
		this.flags.forEach((String name, Boolean b) -> {
			if (b) {
				fn.fn(name);
			}
		});
	}
	
}
