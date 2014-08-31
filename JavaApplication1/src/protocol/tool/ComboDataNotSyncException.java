/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package protocol.tool;

/**
 *
 * @author caolisheng
 */
public class ComboDataNotSyncException extends RuntimeException {

    /**
     * Creates a new instance of <code>ComboDataNotSyncException</code> without
     * detail message.
     */
    public ComboDataNotSyncException() {
    }

    /**
     * Constructs an instance of <code>ComboDataNotSyncException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ComboDataNotSyncException(String msg) {
        super(msg);
    }
}
