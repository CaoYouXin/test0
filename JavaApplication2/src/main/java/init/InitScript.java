/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package init;

import java.util.ArrayList;
import java.util.List;

import utils.Script;

/**
 *
 * @author CPU
 */
public class InitScript implements Init, Script {

	protected List<Init> scripts = new ArrayList<>();

	@Override
	public boolean execute() {
		boolean suc = true;
		for (Init script : scripts) {
			if (!script.init()) {
				suc = false;
				break;
			}
		}
		return suc;
	}

	@Override
	public boolean init() {
		return execute();
	}

}
