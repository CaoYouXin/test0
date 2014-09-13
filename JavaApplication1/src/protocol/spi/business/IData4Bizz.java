/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package protocol.spi.business;

import java.util.List;
import java.util.Set;

import protocol.spi.exceptions.DataWrongTypeException;
import protocol.spi.exceptions.NoConfigException;

/**
 * @desc 业务上，网络数据都是如何被访问和生成的呢？
 * 1. 键值对，底层与2是一致的，只不过是一种用时空换来了更好的访问方式。如果2的过程可以自动化
 * 的与1做相互转化，会是极好的，既方便使用又省流量（可能需要开发过程中利用其它工具来完成）。
 * 2. 紧密的排列着各种数据类型，注意长度不确定的类型还会在其开头有一个标志长度的int
 * @author caolisheng
 */
public interface IData4Bizz {

	Boolean getBoolean(String key);
	Byte getByte(String key);
	Character getChar(String key);
	Short getShort(String key);
	Integer getInt(String key);
	Long getLong(String key);
	Float getFloat(String key);
	Double getDouble(String key);
	String getString(String key);
	<T> T getObject(String key, Data2Object<T> trans);
	<T> List<T> getObjects(String key, Data2Object<T> trans);

	void set(String key, Boolean value) throws NoConfigException;
	void set(String key, Byte value) throws NoConfigException;
	void set(String key, Character value) throws NoConfigException;
	void set(String key, Short value) throws NoConfigException;
	void set(String key, Integer value) throws NoConfigException;
	void set(String key, Long value) throws NoConfigException;
	void set(String key, Float value) throws NoConfigException;
	void set(String key, Double value) throws NoConfigException;
	void set(String key, String value) throws NoConfigException;
	<T> void set(String key, T value, Object2Data<T> trans) throws NoConfigException;
	<T> void set(String key, T[] value, Object2Data<T> trans) throws NoConfigException;
	
}
