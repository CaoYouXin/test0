package argsboot;

import java.util.List;

@RuntimeData
public class Modules {

	private static final Modules instance = new Modules();
	
	private Modules() {
	}

	public static synchronized Modules val() {
		return instance;
	}
	
	private Module root = new Module("");
	
	private Module getRootModule() {
		return root;
	}
	
	public Module getRootModuleByChain(List<String> chain) {
		int index = 0, size = chain.size();
		Module module = getRootModule();
		while(null != module && index < size) {
			module = module.getSubMoule(chain.get(index++));
		}
		return module;
	}

	public Module createChain(List<String> chain) {
		int index = 0, size = chain.size();
		Module module = getRootModule();
		while(index < size) {
			String name = chain.get(index++);
			if (null == module.getSubMoule(name)) {
				module.addSubModule(new Module(name));
			}
			module = module.getSubMoule(name);
		}
		return module;
	}
	
}
