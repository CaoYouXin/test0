/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package protocol.business;

/**
 * @desc 业务上，网络数据都是如何被访问和生成的呢？
 * 1. 键值对，底层与2是一致的，只不过是一种用时空换来了更好的访问方式。如果2的过程可以自动化
 * 的与1做相互转化，会是极好的，既方便使用又省流量（可能需要开发过程中利用其它工具来完成）。
 * 2. 紧密的排列着各种数据类型，注意长度不确定的类型还会在其开头有一个标志长度的int
 * @author caolisheng
 */
public interface IData4Bizz {
	
	Object get(String key);
	
	void set(String key, Object value);
	
}
