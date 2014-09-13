/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocol.impls;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import protocol.spi.exceptions.DataWrongTypeException;
import protocol.spi.business.Data2Object;
import protocol.spi.business.IData4Bizz;
import protocol.spi.business.Object2Data;
import protocol.spi.coding.IData4Coding;
import protocol.spi.exceptions.NoConfigException;
import util.TypeSize;

/**
 *
 * @author caolisheng
 */
public class Data implements IData4Bizz, IData4Coding {

    private String configPath;
    private boolean configed = false;
    private Map<Class, List<String>> config;
    private Map<String, Object> data;

    public Data(String configPath) {
        this.configPath = configPath;
        this.data = new HashMap<>();
    }

    private Object get(String key) {
        return this.data.get(key);
    }

    private void set(String key, Object value, boolean isCheckType) throws DataWrongTypeException, NoConfigException {
        if (null == value) {
            return;
        }
        
        if (isCheckType) {
	        this.checkType(key, value.getClass());
        }
        
        this.data.put(key, value);
    }

	private void checkType(String key, Class clazz) throws NoConfigException, DataWrongTypeException {
		List<String> keys = this.config(clazz);
		if (!keys.contains(key)) {
		    throw new DataWrongTypeException(String.format("%s got the wrong type[%s].", key, clazz.getName()));
		}
	}

    @Override
    public <T> List<T> query(Class<T> clazz) throws NoConfigException {
        List<T> ret = new ArrayList<>();
        List<String> keys = this.config(clazz);
        keys.forEach((key) -> {
            Object value = this.data.get(key);
            ret.add((T) value);
        });
        return ret;
    }

    @Override
    public <T> void add(Map<String, T> data) throws DataWrongTypeException, NoConfigException {
        for (Entry<String, T> entry : data.entrySet()) {
            this.set(entry.getKey(), entry.getValue(), false);
        }
    }

    @Override
    public <T> List<String> config(Class<T> clazz) throws NoConfigException {
        if (this.configed) {
            if (null == this.config) {
                throw new NoConfigException("Actually Not Configed yet OR Config missing.");
            } else {
                return this.config.get(clazz);
            }
        }

        if (null == this.configPath) {
            throw new NoConfigException("No Config File specified.");
        }

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

    @Override
    public List<Class> allConfigs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public Boolean getBoolean(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Byte getByte(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Character getChar(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Short getShort(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getInt(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getLong(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Float getFloat(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getDouble(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getString(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getObject(String key, Data2Object<T> trans) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] getObjects(String key, Data2Object<T> trans) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set(String key, Boolean value) throws NoConfigException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(String key, Byte value) throws NoConfigException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(String key, Character value) throws NoConfigException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(String key, Short value) throws NoConfigException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(String key, Integer value) throws NoConfigException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(String key, Long value) throws NoConfigException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(String key, Float value) throws NoConfigException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(String key, Double value) throws NoConfigException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(String key, String value) throws NoConfigException {
		this.set(key, value, true);
	}

	@Override
	public <T> void set(String key, T value, Object2Data<T> trans)
			throws NoConfigException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void set(String key, T[] value, Object2Data<T> trans)
			throws NoConfigException {

		if (null == value || 0 == value.length) {
			return;
		}
		
		Class<? extends Object> clazz = value[0].getClass();
		this.checkType(key, clazz);
		
		String myConfigPath = clazz.getName().replaceAll(".", File.separator);
		
		Data[] data = new Data[value.length];
		for (int i = 0; i < data.length; i++) {
			data[i] = new Data(myConfigPath);
			trans.trans(data[i], value[i]);
		}
		
		this.set(key, data, false);
	}

}

