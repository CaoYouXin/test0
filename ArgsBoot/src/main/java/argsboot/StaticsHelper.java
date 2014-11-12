package argsboot;

import utils.StringUtils;
import utils.Suc;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
		return this.root;
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

    public boolean batchAddCommand(String chain, Map<String, Class> stringClassMap) {
        List<String> chainList = Arrays.asList(StringUtils.split(chain, "."));
        Module moduleByChain = this.getModuleByChain(chainList);
        if (null == moduleByChain) {
            moduleByChain = this.createModuleChain(chainList);
        }
        for (Map.Entry<String, Class> entry : stringClassMap.entrySet()) {
            System.out.println("NULL FOUND [entry.getValue()]: " + moduleByChain);
            try {
                moduleByChain.addCommand(entry.getKey(), new Command((CommandHandler) entry.getValue().newInstance()));
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public boolean batchAddConfig(String key, Map<String, Class> stringClassMap) {
        int index = key.indexOf(':');
        String chain = key.substring(0, index);
        String cmdName = key.substring(index + 1);

        Command command = this.getCommandByChain(Arrays.asList(StringUtils.split(chain, ".")), cmdName);
        if (null == command) {
            return false;
        }

        for (Map.Entry<String, Class> entry : stringClassMap.entrySet()) {
            try {
                command.addCfg(entry.getKey(), new Config((ConfigHandler<?>) entry.getValue().newInstance()));
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public boolean clearModule(String[] chain) {
        Suc suc = new Suc();
        Module moduleByChain = this.getModuleByChain(Arrays.asList(chain));
        if (null != moduleByChain) {
            suc.val(moduleByChain.clearCommands());
            suc.val(moduleByChain.clearSubModules());
        }
        return suc.val();
    }

    public void print() {
        System.out.println(this.root.toString());
    }

}
