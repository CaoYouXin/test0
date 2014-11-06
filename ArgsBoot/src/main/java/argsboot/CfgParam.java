package argsboot;

import java.util.List;


public class CfgParam<CH extends CommandHandler> {
	
	private List<String> params;
	private CH handler;
	
	CfgParam(List<String> params, CH handler) {
		super();
		this.params = params;
		this.handler = handler;
	}

	public List<String> getParams() {
		return params;
	}

	public CH getHandler() {
		return handler;
	}
	
}
