/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

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
            if (!CharUtils.isEnglishLetter(c))
                return false;
        }
        return true;
    }
    
}
