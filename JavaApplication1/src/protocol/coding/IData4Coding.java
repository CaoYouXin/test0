/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package protocol.coding;

import java.util.List;
import java.util.Map;
import protocol.business.DataWrongTypeException;

/**
 * @desc 为了方便编码，数据类应该具有什么样的接口呢？
 * 1. 按类型查询和添加数据
 * 2. 按类型读取配置（配置由第三方工具软件生成）
 * 总之，相同类型的数据写在一起，其内部顺序按KeyName（纯英文）的自然顺序
 * @author caolisheng
 */
public interface IData4Coding {
	
	<T> List<T> query(Class<T> clazz) throws NoConfigException;
	<T> void add(Map<String, T> data) throws DataWrongTypeException, NoConfigException;
	
	<T> List<String> config(Class<T> clazz) throws NoConfigException;
	
}
