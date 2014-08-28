/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package protocol.coding;

/**
 *
 * @author caolisheng
 */
public class NoConfigException extends Exception {

	/**
	 * Creates a new instance of <code>NoConfigException</code> without detail message.
	 */
	public NoConfigException() {
	}

	/**
	 * Constructs an instance of <code>NoConfigException</code> with the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public NoConfigException(String msg) {
		super(msg);
	}
}
