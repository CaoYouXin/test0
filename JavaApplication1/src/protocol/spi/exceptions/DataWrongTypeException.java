/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package protocol.spi.exceptions;

/**
 *
 * @author caolisheng
 */
public class DataWrongTypeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance of <code>DataWrongTypeException</code> without detail message.
	 */
	public DataWrongTypeException() {
	}

	/**
	 * Constructs an instance of <code>DataWrongTypeException</code> with the specified detail
	 * message.
	 *
	 * @param msg the detail message.
	 */
	public DataWrongTypeException(String msg) {
		super(msg);
	}
}
