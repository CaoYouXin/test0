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
	private Map<String, Command> commands;
	
	private String name;
	
	Module(String name) {
		this.name = name;
	}
	
	@Override
	public String key() {
		return this.name;
	}
	
	boolean hasSubModules() {
		return null != this.subModules;
	}
	
	Module getSubMoule(String name) {
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
	
	Module addSubModule(Module module) {
		if (null == module) {
			return this;
		}
		if (null == this.subModules) {
			this.subModules = new HashSet<>();
		}
		this.subModules.add(module);
		return this;
	}
	
	Module removeSubModule(Module module) {
		if (null == this.subModules || null == module) {
			return this;
		}
		if (this.subModules.remove(module) && this.subModules.isEmpty()) {
			this.subModules = null;
		}
		return this;
	}
	

	boolean hasCommands() {
		return null != this.commands;
	}
	
	Command getCommand(String name) {
		if (StringUtils.isEmpty(name) || !hasCommands()) {
			return null;
		}
		return this.commands.get(name);
	}
	
	Module addCommand(String name, Command module) {
		if (StringUtils.isEmpty(name)) {
			return this;
		}
		if (null == this.commands) {
			this.commands = new HashMap<>();
		}
		this.commands.put(name, module);
		return this;
	}
	
	Module removeCommand(String name) {
		if (null == this.commands || StringUtils.isEmpty(name)) {
			return this;
		}
		if (null != this.commands.remove(name) && this.commands.isEmpty()) {
			this.commands = null;
		}
		return this;
	}
	
}
