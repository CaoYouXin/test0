package argsboot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@StaticData
class Command {

	private CommandHandler handler;
	private Map<String, Config> configs;
	
	Command(CommandHandler handler) {
        this.handler = handler;
	}

    Command setHandler(CommandHandler handler) {
        this.handler = handler;
        return this;
    }

	Command addCfg(String name, Config cfg) {
		if (null == this.configs) {
			this.configs = new HashMap<>();
		} 
		this.configs.put(name, cfg);
		return this;
	}
	
	public String cmd(List<String> configs, List<List<String>> cfgParams, List<String> params) {
		if (null == this.handler) {
			throw new LackOfHandlerException();
		}

		if (configs.size() != cfgParams.size()) {
			throw new LackOfParamsException();
		}
		
		int index = 0;
		for (String cfg : configs) {
			Config config = this.configs.get(cfg);
			if (null == config) {
				System.err.println(String.format("lack of config, %s", cfg));
			}
			config.cfg(cfgParams.get(index++), handler);
		}
		
		return this.handler.fn(params);
	}
	
}
