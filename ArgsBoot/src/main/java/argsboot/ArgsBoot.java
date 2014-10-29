package argsboot;

import java.util.Arrays;
import java.util.List;

import utils.Debugger;

public class ArgsBoot {

	public static boolean debugging = false;
	
	public static void main(String[] args) {
		Debugger.debug(() -> {
			int index = 0;
			for (String arg : args) {
				System.out.println(String.format("arg[%d] is %s", index++, arg));
			}
		});
		load(Arrays.asList(args[0]));//load boot commands
		new OneCall(Arrays.asList(Arrays.copyOfRange(args, 1, args.length))).call();
	}
	
	public static <R> R call(List<String> args, CompletedHandler<R> handler) {
		OneCall theCall = new OneCall(args);
		String result = theCall.call();
		if (null != handler) {
			return handler.fn(result);
		} else {
			return null;
		}
	}

	/**
	 * 加载命令
	 * @param paths 单纯的文件路径的集合（可以是文件夹或者jar包）
	 * @param moduleChain 起到过滤和加锁的作用
	 * @return 加载过程是否完成
	 */
	public static boolean load(List<String> paths, String... moduleChain) {
		return false;
	}
	
	/**
	 * 重新加载命令，之前的丢弃掉
	 * @param moduleChain 起到过滤和加锁的作用
	 * @return 加载过程是否完成
	 */
	public static boolean reload(String... moduleChain) {
		throw new Error("Unimplemented method invoked!");
	}
	
}