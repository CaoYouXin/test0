package argsboot;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import utils.StringUtils;
import utils.dynamicinit.KeyIdentifier;

@StaticData
public class Module implements KeyIdentifier<String> {

	private Set<Module> subModules;
	private Set<String> paths; // 绑定的路径，只增不减
	private Map<String, Command> commands;
	
	private String name;
	
	Module(String name) {
		this.name = name;
	}
	
	@Override
	public String key() {
		return this.name;
	}
	
	public boolean hasSubModules() {
		return null != this.subModules;
	}
	
	public Module getSubMoule(String name) {
		if (StringUtils.isEmpty(name) || !hasSubModules()) {
			return null;
		}
		for (Module module : this.subModules) {
			if (name.equals(module.name)) {
				return module;
			}
		}
		return null;
	}
	
	public Module addSubModule(Module module) {
		if (null == module) {
			return this;
		}
		if (null == this.subModules) {
			this.subModules = new HashSet<>();
		}
		this.subModules.add(module);
		return this;
	}
	
	public Module removeSubModule(Module module) {
		if (null == this.subModules || null == module) {
			return this;
		}
		if (this.subModules.remove(module) && this.subModules.isEmpty()) {
			this.subModules = null;
		}
		return this;
	}
	
	public boolean clearSubModules() {
		if (this.hasSubModules()) {
			this.subModules.clear();
		}
		return true;
	}
	
	public boolean hasCommands() {
		return null != this.commands;
	}
	
	Command getCommand(String name) {
		if (StringUtils.isEmpty(name) || !hasCommands()) {
			return null;
		}
		return this.commands.get(name);
	}
	
	public Module addCommand(String name, Command module) {
		if (StringUtils.isEmpty(name)) {
			return this;
		}
		if (null == this.commands) {
			this.commands = new HashMap<>();
		}
		this.commands.put(name, module);
		return this;
	}
	
	public Module removeCommand(String name) {
		if (null == this.commands || StringUtils.isEmpty(name)) {
			return this;
		}
		if (null != this.commands.remove(name) && this.commands.isEmpty()) {
			this.commands = null;
		}
		return this;
	}

	public boolean clearCommands() {
		if (this.hasCommands()) {
			this.commands.clear();
		}
		return true;
	}
	
	Set<String> getPaths() {
		if (null == this.paths) {
			this.paths = new HashSet<>();
		}
		return paths;
	}

	void updatePaths(Set<String> paths) {
		if (null == this.paths) {
			this.paths = new HashSet<>();
		}
		this.paths.addAll(paths);
	}
	
}
