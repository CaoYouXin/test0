package argsboot;

@StaticData
public class Config {

	private ConfigHandler handler;
	
	Config() {
	}

	public void cfg(String[] params, CommandHandler handler) {
		if (null == this.handler) {
			throw new LackOfHandlerException();
		}
		this.handler.fn(new CfgParam(params, handler));
	}

}
