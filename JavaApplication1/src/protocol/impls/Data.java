/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocol.impls;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import protocol.spi.business.Data2Object;
import protocol.spi.business.IData4Bizz;
import protocol.spi.business.Object2Data;
import protocol.spi.coding.IData4Coding;
import protocol.spi.exceptions.DataWrongTypeException;
import protocol.spi.exceptions.NoConfigException;

/**
 *
 * @author caolisheng
 */
public class Data implements IData4Bizz, IData4Coding {

    private String configPath;
    private boolean configed = false;
    private Map<Class<? extends Object>, List<String>> config;
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

	private void checkType(String key, Class<? extends Object> clazz) throws NoConfigException, DataWrongTypeException {
		List<String> keys = this.config(clazz);
		if (!keys.contains(key)) {
		    throw new DataWrongTypeException(String.format("%s got the wrong type[%s].", key, clazz.getName()));
		}
	}

    @SuppressWarnings("unchecked")
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

        this.config = ConfigManager.config(this.configPath);
        this.configed = true;
        return this.config.get(clazz);
    }

    @Override
    public List<Class<? extends Object>> allConfigs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public Boolean getBoolean(String key) {
		return (Boolean) this.get(key);
	}

	@Override
	public Byte getByte(String key) {
		return (Byte) this.get(key);
	}

	@Override
	public Character getChar(String key) {
		return (Character) this.get(key);
	}

	@Override
	public Short getShort(String key) {
		return (Short) this.get(key);
	}

	@Override
	public Integer getInt(String key) {
		return (Integer) this.get(key);
	}

	@Override
	public Long getLong(String key) {
		return (Long) this.get(key);
	}

	@Override
	public Float getFloat(String key) {
		return (Float) this.get(key);
	}

	@Override
	public Double getDouble(String key) {
		return (Double) this.get(key);
	}

	@Override
	public String getString(String key) {
		return (String) this.get(key);
	}

	@Override
	public <T> T getObject(String key, Data2Object<T> trans) {
		Data data = (Data) this.get(key);
		return trans.trans(data);
	}

	@Override
	public <T> List<T> getObjects(String key, Data2Object<T> trans) {
		List<T> ret = new ArrayList<>();
		Data[] data = (Data[]) this.get(key);
		for (Data d : data) {
			ret.add(trans.trans(d));
		}
		return ret;
	}

	@Override
	public void set(String key, Boolean value) throws NoConfigException {
		this.set(key, value, false);
	}

	@Override
	public void set(String key, Byte value) throws NoConfigException {
		this.set(key, value, false);
	}

	@Override
	public void set(String key, Character value) throws NoConfigException {
		this.set(key, value, false);
	}

	@Override
	public void set(String key, Short value) throws NoConfigException {
		this.set(key, value, false);
	}

	@Override
	public void set(String key, Integer value) throws NoConfigException {
		this.set(key, value, false);
	}

	@Override
	public void set(String key, Long value) throws NoConfigException {
		this.set(key, value, false);
	}

	@Override
	public void set(String key, Float value) throws NoConfigException {
		this.set(key, value, false);
	}

	@Override
	public void set(String key, Double value) throws NoConfigException {
		this.set(key, value, false);
	}

	@Override
	public void set(String key, String value) throws NoConfigException {
		this.set(key, value, true);
	}

	@Override
	public <T> void set(String key, T value, Object2Data<T> trans)
			throws NoConfigException {

		if (null == value) {
			return;
		}
		
		Class<? extends Object> clazz = value.getClass();
		this.checkType(key, clazz);
		
		String myConfigPath = clazz.getName().replaceAll(".", File.separator);
		Data data = new Data(myConfigPath);
		
		trans.trans(data, value);
		this.set(key, data, false);
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

