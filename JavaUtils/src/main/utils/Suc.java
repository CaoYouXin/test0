/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author CPU
 */
public final class Suc {

	private boolean suc;

	public Suc(boolean suc) {
		this.suc = suc;
	}

	public Suc() {
	}

	public boolean val() {
		return this.suc;
	}

	public boolean val(boolean suc) {
		return this.suc = suc;
	}

	public Object success(GeneralFn fn) {
		int i = 0, j =1;
		return this.suc ? fn.fn(i, j) : null;
	}

	public Object fail(GeneralFn fn) {
		return !this.suc ? fn.fn() : null;
	}
	
}
