package argsboot;

import java.util.List;

@StaticData
class Config {

	private ConfigHandler<?> handler;
	
	Config() {
	}

    public Config(ConfigHandler<?> configHandler) {
        this.handler = configHandler;
    }

    public void cfg(List<String> params, CommandHandler handler) {
		if (null == this.handler) {
			throw new LackOfHandlerException();
		}
		this.handler.fn(new CfgParam(params, handler));
	}

}
