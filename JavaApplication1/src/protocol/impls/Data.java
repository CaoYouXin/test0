/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocol.impls;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import protocol.spi.exceptions.DataWrongTypeException;
import protocol.spi.business.Data2Object;
import protocol.spi.business.IData4Bizz;
import protocol.spi.business.Object2Data;
import protocol.spi.coding.IData4Coding;
import protocol.spi.exceptions.NoConfigException;

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

    private void set(String key, Object value) throws DataWrongTypeException, NoConfigException {
        if (null == value) {
            return;
        }
        List<String> keys = this.config(value.getClass());
        if (!keys.contains(key)) {
            throw new DataWrongTypeException(String.format("%s got the wrong type[%s].", key, value.getClass().getName()));
        }
        this.data.put(key, value);
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
            this.set(entry.getKey(), entry.getValue());
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
	public <T> T getObject(String key, Object2Data<T> trans) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] getObjects(String key, Object2Data<T> trans) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set(String key, Boolean value) throws DataWrongTypeException,
			NoConfigException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(String key, Byte value) throws DataWrongTypeException,
			NoConfigException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(String key, Character value) throws DataWrongTypeException,
			NoConfigException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(String key, Short value) throws DataWrongTypeException,
			NoConfigException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(String key, Integer value) throws DataWrongTypeException,
			NoConfigException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(String key, Long value) throws DataWrongTypeException,
			NoConfigException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(String key, Float value) throws DataWrongTypeException,
			NoConfigException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(String key, Double value) throws DataWrongTypeException,
			NoConfigException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(String key, String value) throws DataWrongTypeException,
			NoConfigException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void set(String key, T value, Data2Object<T> trans)
			throws DataWrongTypeException, NoConfigException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void set(String key, T[] value, Data2Object<T> trans)
			throws DataWrongTypeException, NoConfigException {
		// TODO Auto-generated method stub
		
	}

}
