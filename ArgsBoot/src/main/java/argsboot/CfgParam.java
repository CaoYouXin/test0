package argsboot;

import java.util.List;


public class CfgParam {
	
	private List<String> params;
	private CommandHandler handler;
	
	CfgParam(List<String> params, CommandHandler handler) {
		super();
		this.params = params;
		this.handler = handler;
	}

	public List<String> getParams() {
		return params;
	}

	public CommandHandler getHandler() {
		return handler;
	}
	
}
