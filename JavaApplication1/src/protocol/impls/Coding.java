/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package protocol.impls;

import protocol.spi.coding.ICoding;
import protocol.spi.coding.IData4Coding;
import protocol.impls.Data;
import util.BytesUtils;
import util.BytesUtils.Offset;

/**
 *
 * @author caolisheng
 */
public class Coding implements ICoding {

    @Override
    public IData4Coding decoding(byte[] data) {
        Offset offset = new Offset();
        String configFile = BytesUtils.getString(data, offset.forward(1), data[0]);
        IData4Coding decodedData = new Data(getConfigPath(configFile));
//        decodedData.
        return decodedData;
    }

    @Override
    public byte[] encoding(IData4Coding data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
      * @desc 其实就是加上文件夹的路径
      * @param configFile
      * @return 
      */
    private String getConfigPath(String configFile) {
        return configFile;
    }
    
}
