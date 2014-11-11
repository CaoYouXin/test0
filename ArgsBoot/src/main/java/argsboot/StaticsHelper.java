package argsboot;

import java.util.List;

@RuntimeData
public class StaticsHelper {

	private static final StaticsHelper instance = new StaticsHelper();
	
	private StaticsHelper() {
	}

	public static synchronized StaticsHelper val() {
		return instance;
	}
	
	private Module root = new Module("");
	
	private Module getRootModule() {
		return root;
	}
	
	public Module getModuleByChain(List<String> chain) {
		int index = 0, size = chain.size();
		Module module = getRootModule();
		while(null != module && index < size) {
			module = module.getSubModule(chain.get(index++));
		}
		return module;
	}

	public Module createModuleChain(List<String> chain) {
		int index = 0, size = chain.size();
		Module module = getRootModule();
		while(index < size) {
			String name = chain.get(index++);
			if (null == module.getSubModule(name)) {
				module.addSubModule(new Module(name));
			}
			module = module.getSubModule(name);
		}
		return module;
	}

    public Command getCommandByChain(List<String> chain, String cmd) {
        Module moduleByChain = this.getModuleByChain(chain);
        if (null == moduleByChain) return null;
        return moduleByChain.getCommand(cmd);
    }

    public Command createCommandByChain(List<String> chain, String cmd, CommandHandler handler) {
        Module moduleByChain = this.getModuleByChain(chain);
        if (null == moduleByChain) {
            moduleByChain = this.createModuleChain(chain);
            Command command = new Command(handler);
            moduleByChain.addCommand(cmd, command);
            return command;
        }
        Command command = moduleByChain.getCommand(cmd);
        return command.setHandler(handler);
    }

	public Module getModuleByChainCreateIfNull(List<String> chain) {
		Module moduleByChain = this.getModuleByChain(chain);
		if (null == moduleByChain) {
			moduleByChain = this.createModuleChain(chain);
		}
		return moduleByChain;
	}
}
