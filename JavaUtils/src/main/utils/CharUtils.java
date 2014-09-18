/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 *
 * @author caolisheng
 */
public class CharUtils {
	
	public static boolean isEnglishLetter(char c) {
		return (c >= 65 && c <= 90) || (c >= 97 && c <= 122);
	}
	
}
