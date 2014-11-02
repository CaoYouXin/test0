package argsboot;

import java.util.ArrayList;
import java.util.List;

import utils.Suc;
import utils.primewrapper.IntWrapper;

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
		Suc module = new Suc(true), params = new Suc(false), cfgParams = new Suc(false);
		IntWrapper configIndex = new IntWrapper(-1);
		args.forEach((arg) -> {
			if (params.val()) {
				if (null == this.commandParams) {
					this.commandParams = new ArrayList<>();
				}
				this.commandParams.add(arg);
				return;
			}
			if (cfgParams.val() && !arg.startsWith("-")) {
				this.configParams.get(configIndex.val()).add(arg);
				return;
			}
			if (arg.startsWith("-")) {
				if (module.val()) {
					this.commandName = this.moduleChain.remove(this.moduleChain.size() - 1);
					module.val(false);
				}
				if (arg.equalsIgnoreCase("-p")) {
					params.val(true);
				} else {
					if (null == this.configNames) {
						this.configNames = new ArrayList<>();
					}
					this.configNames.add(arg.substring(1, arg.length()));
					configIndex.inc(1);
					if (null == this.configParams) {
						this.configParams = new ArrayList<>();
					}
					if (configIndex.val() >= this.configParams.size()) {
						this.configParams.add(configIndex.val(), new ArrayList<>());
					}
					cfgParams.val(true);
				}
				return;
			}
			if (null == this.moduleChain) {
				this.moduleChain = new ArrayList<>();
			}
			this.moduleChain.add(arg);
		});
		if (module.val()) {
			this.commandName = this.moduleChain.remove(this.moduleChain.size() - 1);
		}
	}
	
	String call() {
		Module module = Modules.val().getRootModuleByChain(moduleChain);
		if (null == module) {
			throw new NullModuleException();
		}
		
		Command command = module.getCommand(commandName);
		if (null == command) {
			throw new NullCommandException();
		}
		
		return command.cmd(configNames, configParams, commandParams);
	}

	@Override
	public String toString() {
		return "OneCall [moduleChain=" + moduleChain + ", commandName="
				+ commandName + ", configNames=" + configNames
				+ ", configParams=" + configParams + ", commandParams="
				+ commandParams + "]";
	}

}
