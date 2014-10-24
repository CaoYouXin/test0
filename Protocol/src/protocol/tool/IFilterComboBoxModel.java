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
public interface IFilterComboBoxModel<T> {
    
    boolean filter(T perfix);
    
}
