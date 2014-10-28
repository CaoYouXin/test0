package argsboot;

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
		getTheCall(args).call();
	}
	
	public static <R> R call(String[] args, CompletedHandler<R> handler) {
		OneCall theCall = getTheCall(args);
		String result = theCall.call();
		if (null != handler) {
			return handler.fn(result);
		} else {
			return null;
		}
	}

	private static OneCall getTheCall(String[] args) {
		return null;
	}

}
