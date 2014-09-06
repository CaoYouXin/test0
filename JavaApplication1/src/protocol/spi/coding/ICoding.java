/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package protocol.spi.coding;

/**
 * @desc 编码规则。额，这个属于表示层？？？
 * @author caolisheng
 */
public interface ICoding {
    
    IData4Coding decoding(byte[] data);
    
    byte[] encoding(IData4Coding data);
    
}
