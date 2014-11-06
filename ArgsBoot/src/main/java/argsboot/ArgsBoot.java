package argsboot;

import utils.ArrayUtils;
import utils.Debugger;

import java.util.Arrays;
import java.util.Set;

public class ArgsBoot {

	public static final boolean debugging = true;
	
	public static final String TO_BE_CONTINUED = "#to be continued...#";
	
	private static Loader LOADER = null;
	
	/**
	 * 命令行启动时调用
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @ifloader
	 * start a command tool
	 * @else
	 * @args[0] Loader的类路径
	 * @args[1] reload服务启动的端口，-1表示不启动reload服务
	 * @args[2] Boot路径（第一批命令加载的源）
	 * @args[3..a] module chain
	 * @args[a+1] command
	 * @args[a+2..b] config
	 * @args[b+1..c] params
	 * @endif
	 */
	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		System.out.println(System.getProperty("java.class.path"));
        if ("loader".equalsIgnoreCase(args[0])) {
			connectToALoaderServer(args[1], Integer.valueOf(args[2]));
			return;
		} else {
			if ("default".equalsIgnoreCase(args[0])) {
				setLoader((Loader) Class.forName("argsboot.loader.DefaultLoader").newInstance());
			} else {
				setLoader((Loader) Class.forName(args[0]).newInstance());
			}
			load(ArrayUtils.asSet(args[2]));
		}
		OneCall theCall = new OneCall(Arrays.asList(Arrays.copyOfRange(args, 3, args.length)));
		Debugger.debug(() -> {
            int index = 0;
            for (String arg : args) {
                System.out.println(String.format("arg[%d] is %s", index++, arg));
            }
            System.out.println(theCall.toString());
        });
		String to_be_continued = theCall.call();
		if (TO_BE_CONTINUED.equals(to_be_continued) && !"-1".equals(args[1])) {
			establishALoaderServer(Integer.valueOf(args[1]));
		}
	}
	
	private static void connectToALoaderServer(String ip, int port) {
		// TODO Auto-generated method stub
		
	}

	private static void establishALoaderServer(int port) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 程序中调用
	 * @param args
	 * @param handler
	 * @return
	 */
	public static <R> R call(CompletedHandler<R> handler, String... args) {
		OneCall theCall = new OneCall(Arrays.asList(args));
//		String result = theCall.call();
        String result = "aaa";
		if (null != handler) {
			return handler.fn(result);
		} else {
			return null;
		}
	}

	/**
	 * 加载命令
	 * @param loader loader具有以下要求
	 * @1 如果没有则创建 moduleChain 的 module，如果存在则先清空为只有根节点的子树
	 * @2 加载 module 为根的子树中所有的 command，这个过程中可能创建若干节点
	 * @param paths 单纯的文件路径的集合（可以是文件夹或者jar包）
	 * @param moduleChain 起到过滤和加锁的作用
	 * @return 加载过程是否完成
	 */
	public static boolean load(Loader loader, Set<String> paths, String... moduleChain) {
		return loader.load(paths, moduleChain, StaticsHelper.val()) ? updatePathsCache(paths, moduleChain) : false;
	}
	
	public static boolean load(Set<String> paths, String... moduleChain) {
		return (null != LOADER) ? load(LOADER, paths, moduleChain) : false;
	}
	
	private static boolean updatePathsCache(Set<String> paths, String[] moduleChain) {
		StaticsHelper.val().getModuleByChain(Arrays.asList(moduleChain)).updatePaths(paths);
		return false;
	}

	/**
	 * 重新加载命令，之前的丢弃掉
	 * @param loader 详见 load 方法中的 loader
	 * @param moduleChain 起到过滤和加锁的作用
	 * @return 加载过程是否完成
	 */
	public static boolean reload(Loader loader, String... moduleChain) {
		Module module = StaticsHelper.val().getModuleByChain(Arrays.asList(moduleChain));
		return loader.isReloadSupported() ? loader.load(module.getPaths(), moduleChain, StaticsHelper.val()) : false;
	}
	
	public static boolean reload(String... moduleChain) {
		return (null != LOADER) ? reload(LOADER, moduleChain) : false;
	}
	
	/**
	 * 设置加载器
	 * @param loader
	 * @return true：loader有效，且设置成功；false：loader为空，且设置失败
	 */
	public static synchronized boolean setLoader(Loader loader) {
		if (null == loader) {
			return false;
		}
		LOADER = loader;
		return true;
	}
	
}
