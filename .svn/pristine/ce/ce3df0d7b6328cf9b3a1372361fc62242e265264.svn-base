package com.jp.util;

import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {

	/**
	 * 得到输入字符串的全拼拼音(非汉字不转化)
	 * @param src
	 * @return
	 */
	public static String getPinyinFull(String src) {
		char[] srcCharArray = src.toCharArray();
		String[] chinaCharArray = new String[srcCharArray.length];

		HanyuPinyinOutputFormat pyformat = new HanyuPinyinOutputFormat();
		pyformat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		pyformat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		pyformat.setVCharType(HanyuPinyinVCharType.WITH_V);

		String resultstr = "";
		try {
			for (int i = 0; i < srcCharArray.length; i++) {
				if (Character.toString(srcCharArray[i]).matches(
						"[\\u4E00-\\u9FA5]+")) {
					chinaCharArray = PinyinHelper.toHanyuPinyinStringArray(
							srcCharArray[i], pyformat);
					resultstr += chinaCharArray[0];
				} else {
					resultstr += Character.toString(srcCharArray[i]);
				}
			}
			return resultstr;
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 得到所有汉字首字母
	 * @param str
	 * @return
	 */
	public static String getPinYinFirstChar(String str) {
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
	}
	
	/**
	 * 得到所有汉字首字母
	 * @param str
	 * @return
	 */
	public static String getFirstChar(String str) {
		String pinyin = getPinYinFirstChar(str);
		String sub = pinyin.substring(0, 1);
		Pattern pattern = Pattern.compile("[0-9]*"); 
	    if((pattern.matcher(sub).matches())){
	    	sub = "#";	
	    }
		return sub;
	}

	/**
	 * 将字符串转移为ASCII码
	 * @param cnStr
	 * @return
	 */
	public static String getCnASCII(String cnStr) {
		StringBuffer strBuf = new StringBuffer();
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			//System.out.println(Integer.toHexString(bGBK[i] & 0xff));
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

	public static void main(String[] args) {
		String pinyin = getPinyinFull("翟兴志");
		System.out.println(pinyin);
		
		String pinyinfirstchar = getPinYinFirstChar("翟兴志");
		System.out.println(pinyinfirstchar);

		String asciistr = getCnASCII("翟兴志");
		System.out.println(asciistr);
		
		String firstChar = getFirstChar("aa翟兴志");
		System.out.println(firstChar);
		
	}

}
