/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package protocol.business;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import protocol.coding.IData4Coding;
import protocol.coding.NoConfigException;

/**
 *
 * @author caolisheng
 */
public class Data implements IData4Bizz, IData4Coding {

	private String configPath;
	private boolean configed = false;
	private Map<Class, List<String>> config;

	public Data(String configPath) {
		this.configPath = configPath;
	}
	
	@Override
	public Object get(String key) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void set(String key, Object value) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public <T> List<T> query(Class<T> clazz) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public <T> void add(Map<String, T> data) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public <T> List<String> config(Class<T> clazz) throws NoConfigException {
		if (this.configed)
			if (null == this.config)
				throw new NoConfigException("Actually Not Configed yet OR Config missing.");
			else
				return this.config.get(clazz);
			
		if (null == this.configPath)
			throw new NoConfigException("No Config File specified.");
		
		Properties props = new Properties();
		try {
			props.loadFromXML(new FileInputStream(this.configPath));
		} catch (IOException ex) {
			Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
			throw new NoConfigException("Config File Can not open.");
		}
		
		props.forEach((Object t, Object u) -> {
			// 添加到config中
		});
		
		//排序整理
		
		this.configed = true;
		return this.config.get(clazz);
	}
	
}
