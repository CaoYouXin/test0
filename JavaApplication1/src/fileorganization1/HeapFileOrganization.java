/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fileorganization1;

import fileorganizationi.IFileOrganization;
import fileorganizationi.IKey;
import fileorganizationi.IRecord;

/**
 * @desc 记录长短不一，是自描述的。但是可以约定一些规范，来方便读写该类文件（JDK中的DataOutputStream/DataInputStream就是个很好的例子）。
 * 这类文件有很多，如图片文件，音频文件和视频文件。。。读写这类文件，大都需要事先定制一份详细的文件数据堆放顺序规则，有时还会与其它类型
 * 文件混合使用，如索引（开辟一块索引区域，优先读写。但是，对于大文件，可以将索引单独作为一个文件分离粗来）
 * @author caolisheng
 */
public class HeapFileOrganization implements IFileOrganization {

	/**
	 * @desc 遍历整个文件，找到包含IKey的IRecord为止
	 * @param key
	 * @return 
	 */
    @Override
    public IRecord search(IKey key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	/**
	 * 
	 * @param record 自描述，不带主键
	 * @return 
	 */
    @Override
    public boolean insert(IRecord record) {
        byte[] content = record.write();
        return true;
    }

	/**
	 * @针对记录的修改 由于木有主键，所以无法定位需要修改的记录
	 * @param record
	 * @return 
	 */
    @Override
    public boolean modify(IRecord record) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	/**
	 * 
	 * @param key
	 * @return 
	 */
    @Override
    public boolean delete(IKey key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
