/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package init;

import java.util.ArrayList;
import java.util.List;

import utils.Script;
import utils.Suc;

/**
 * Composite
 * 
 * @author CPU
 */
public abstract class Init implements Script {

	abstract boolean init();

	private static List<Init> list = new ArrayList<>();

	public final Init add(Init init) {
		list.add(init);
		return this;
	}

	@Override
	public final boolean execute() {
		Suc suc = new Suc(true);
		Suc sucOnce = new Suc(true);
		errors.clear();
		list.forEach((init) -> {
			if (suc.val()) {
				sucOnce.val(suc.val(init.init()));
			} else {
				sucOnce.val(init.init());
			}
			sucOnce.success((params) -> {
				return errors.add(init);
			});
		});
		return suc.val();
	}

	private static List<Init> errors = new ArrayList<>();

}
