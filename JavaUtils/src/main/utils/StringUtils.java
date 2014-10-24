/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author caolisheng
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        return null == str || str.isEmpty();
    }

    public static boolean isEnglish(String str) {
        for (char c : str.toCharArray()) {
            if (!CharUtils.isEnglishLetter(c)) {
                return false;
            }
        }
        return true;
    }

    public static String join(List<? extends Object> os, String seprator) {
    	if (null == os) {
    		return "";
    	}
        StringBuilder sb = new StringBuilder();
        os.stream().forEach((Object o) -> {
            sb.append(seprator).append(o.toString());
        });
        return sb.length() > 0 ? sb.substring(1) : "";
    }

    public static String wrap(List<String> list) {
    	if (null == list) {
    		return "";
    	}
        StringBuilder sb = new StringBuilder();
        list.stream().forEach((string) -> {
            sb.append(",`").append(string).append('`');
        });
        return list.size() > 0 ? sb.substring(1) : "";
    }

    public static String wrap(String... cols) {
        StringBuilder sb = new StringBuilder();
        for (String string : cols) {
            sb.append(",`").append(string).append('`');
        }
        return cols.length > 0 ? sb.substring(1) : "";
    }

    public static String replace(String... cols) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<cols.length; i++) {
            sb.append(",?");
        }
        return cols.length > 0 ? sb.substring(1) : "";
    }

    public static String map(String... cols) {
        StringBuilder sb = new StringBuilder();
        for (String string : cols) {
            sb.append(",`").append(string).append("`=?");
        }
        return cols.length > 0 ? sb.substring(1) : "";
    }

    public static List<Integer> splitToIntList(String string, String seprator) {
        List<Integer> nums = new ArrayList<>();
        String[] split = string.split(seprator);
        for (int i = 0; i < split.length; i++) {
            nums.add(Integer.parseInt(split[i]));
        }
        return nums;
    }

    public static String Md5(String plainText) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i));
            }
            return sb.toString();//32位的加密 
//            return sb.toString().substring(8, 24);//16位的加密 
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(StringUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String valueOf(Object o) {
        return String.format("\"%s\"", o);
    }

}
