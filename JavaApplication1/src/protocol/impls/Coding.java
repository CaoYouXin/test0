/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocol.impls;

import java.util.Collections;
import java.util.List;

import protocol.spi.coding.ICoding;
import protocol.spi.coding.IData4Coding;
import protocol.spi.exceptions.NoConfigException;
import util.BytesUtils;
import util.BytesUtils.Bytes;
import util.BytesUtils.Offset;
import util.TypeSize;

/**
 *
 * @author caolisheng
 */
public class Coding implements ICoding {

    @Override
    public IData4Coding decoding(byte[] data) {
        Offset offset = new Offset();
        String configFile = BytesUtils.decodeString(data, offset.forward(1), data[0]);
        IData4Coding decodedData = new Data(getConfigPath(configFile));
//        decodedData.
        return decodedData;
    }

    /**
     * @desc 其实就是加上文件夹的路径
     * @param configFile
     * @return
     */
    private String getConfigPath(String configFile) {
        return configFile;
    }

    @Override
    public byte[] encoding(IData4Coding data) throws NoConfigException {
        Bytes encodedData = new Bytes();
        List<Class<? extends Object>> allTypes = data.allConfigs();
        this.sortBySize(allTypes);
        for (Class<? extends Object> clazz : allTypes) {
        	encodedData.append((byte) TypeSize.val(clazz.getSimpleName()).size());
            List<? extends Object> values = data.query(clazz);
            encodedData.append(BytesUtils.encodeInt(values.size()));
            for(Object value : values) {
            	if (value instanceof Byte) {
            		encodedData.append((byte) value);
            	} else if (value instanceof String) {
            		byte[] encodedString = BytesUtils.encodeString((String) value);
            		encodedData.append(BytesUtils.encodeInt(encodedString.length));
            		encodedData.append(encodedString);
            	} else {
            		this.toBytes(encodedData, value);
            	}
            }
        }
        return encodedData.get();
    }

    /**
     * @problems 如下
     * @1. 这里可能有该死的递归
     * @2. 移位操作还有待验证啊（已经转移到BytesUtils里的）
     * @param value
     * @return
     * @throws NoConfigException 
     */
    private Bytes toBytes(Bytes encodedData, Object value) throws NoConfigException {
        if (value instanceof Boolean) {
            return encodedData.append(BytesUtils.encodeBoolean((boolean) value));
        } else if (value instanceof Character) {
            return encodedData.append(BytesUtils.encodeChar((char) value));
        } else if (value instanceof Short) {
            return encodedData.append(BytesUtils.encodeShort((short) value));
        } else if (value instanceof Integer) {
        	return encodedData.append(BytesUtils.encodeInt((int) value));
        } else if (value instanceof Long) {
        	return encodedData.append(BytesUtils.encodeLong((long) value));
        } else if (value instanceof Float) {
        	return encodedData.append(BytesUtils.encodeFloat((float) value));
        } else if (value instanceof Double) {
        	return encodedData.append(BytesUtils.encodeDouble((double) value));
        } else if (value instanceof IData4Coding) {
        	return encodedData.append(this.encoding((IData4Coding) value));
        } else if (value instanceof IData4Coding[]) {
        	IData4Coding[] datas = (IData4Coding[]) value;
        	encodedData.append(BytesUtils.encodeInt(datas.length));
        	for (IData4Coding data : datas) {
        		encodedData.append(this.encoding(data));
        	}
        	return encodedData;
        }
        return encodedData;
    }

    /**
     * @desc 占空间小的基本类型排在前面
     * @param allTyps
     */
    private void sortBySize(List<Class<? extends Object>> allTyps) {
        Collections.sort(allTyps, (c1, c2) -> {
            return TypeSize.val(c1.getSimpleName()).size() - TypeSize.val(c2.getSimpleName()).size();
        });
    }

//    public static void main(String[] args) {
////        List<Integer> list = new ArrayList<>();
////        list.add(2);
////        list.add(3);
////        list.add(1);
////        list.add(4);
////        list.add(5);
////        Collections.sort(list, (i1, i2) -> {
////            return i1 -i2;
////        });
////        list.forEach((i) -> {
////            System.out.print(i + "\t");
////        });
//        
////        int i = 0;
////        Object o = i;
////        System.out.println(o instanceof Integer);
//        
////        int i2 = Short.MIN_VALUE - 1;
////        short s = (short) i2;
////        System.out.println(s + " vs. " + Short.MAX_VALUE);
//    }
}