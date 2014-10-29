package argsboot;

import java.util.List;
import java.util.Map;

@RuntimeData
public class Modules {

	private static Map<String, Module> rootModules;
	
	static Module getRootModule(String name) {
		return rootModules.get(name);
	}
	
	static Module getRootModuleByChain(List<String> chain) {
		int index = 0, size = chain.size();
		Module module = getRootModule(chain.get(index++));
		while(null != module && index < size) {
			module = module.getSubMoule(chain.get(index++));
		}
		return module;
	}
	
}
