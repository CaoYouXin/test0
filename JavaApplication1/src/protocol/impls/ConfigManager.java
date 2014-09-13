package protocol.impls;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import protocol.spi.exceptions.NoConfigException;

/**
 * 
 * @author caolisheng
 */
public class ConfigManager {

	private static Map<String, Map<Class, List<String>>> configs = new HashMap<>();
	
	public static Map<Class, List<String>> config(String path) throws NoConfigException {
		Map<Class, List<String>> ret = configs.get(path);
		if (null == ret)
			return initConfig(path);
		return ret;
	}

	private static synchronized Map<Class, List<String>> initConfig(String path) throws NoConfigException {
		Map<Class, List<String>> ret = configs.get(path);
		if (null != ret)
			return ret;
		
		ret = new HashMap<>();
		
		Properties props = new Properties();
        try {
            props.loadFromXML(new FileInputStream(path));
        } catch (IOException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            throw new NoConfigException("Config File Can not open.");
        }

        props.forEach((Object t, Object u) -> {
            // 添加到config中
        });

        //排序整理
        
        
		Map<Class, List<String>> unmodifiableMap = Collections.unmodifiableMap(ret);
		configs.put(path, unmodifiableMap);
		return unmodifiableMap;
	}
	
}
