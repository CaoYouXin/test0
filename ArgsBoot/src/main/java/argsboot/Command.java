package argsboot;

import java.util.HashMap;
import java.util.Map;

@StaticData
public class Command {

	private CommandHandler handler;
	private Map<String, Config> cfgs;
	
	Command() {
	}
	
	Command addCfg(String name, Config cfg) {
		if (null == this.cfgs) {
			this.cfgs = new HashMap<>();
		} 
		this.cfgs.put(name, cfg);
		return this;
	}
	
	public String cmd(String[] configs, String[][] cfgParams, String[] params) {
		if (null == this.handler) {
			throw new LackOfHandlerException();
		}
		
		int index = 0;
		for (String cfg : configs) {
			Config config = this.cfgs.get(cfg);
			if (null == config) {
				System.err.println(String.format("lack of config, %s", cfg));
			}
			config.cfg(cfgParams[index++], handler);
		}
		
		return this.handler.fn(params);
	}
	
}
