package argsboot;


public class CfgParam {
	
	private String[] params;
	private CommandHandler handler;
	
	CfgParam(String[] params, CommandHandler handler) {
		super();
		this.params = params;
		this.handler = handler;
	}

	public String[] getParams() {
		return params;
	}

	public CommandHandler getHandler() {
		return handler;
	}
	
}
