package argsboot;

import java.util.List;

/**
 * argsboot中一次调用的运行时对象。
 * @author caoyouxin
 *
 */
@RuntimeData
public class OneCall {

	private List<String> moduleChain;
	private String commandName;
	private List<String> configNames;
	private List<List<String>> configParams;
	private List<String> commandParams;
	
	OneCall(List<String> args) {
		
	}
	
	String call() {
		Module module = Modules.getRootModuleByChain(moduleChain);
		if (null == module) {
			throw new NullModuleException();
		}
		
		Command command = module.getCommand(commandName);
		if (null == command) {
			throw new NullCommandException();
		}
		
		return command.cmd(configNames, configParams, commandParams);
	}

}
