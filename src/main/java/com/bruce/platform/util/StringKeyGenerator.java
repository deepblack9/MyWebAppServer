package com.bruce.platform.util;

import java.util.Date;
import java.util.Random;

/**
 * 生成指定长度的随机字符串工具类
 * 
 * @author Aladding
 * 2010-1-15
 */
public class StringKeyGenerator {
	private static Random m_generator = null;
	private static StringBuffer m_strbKeyChars = new StringBuffer(
			"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");

	static {
		Date dateNow = new Date();
		m_generator = new Random(dateNow.getTime());
	}

	/**
	 * 生成指定长度的随机字符串
	 * 
	 * @param size 字符串长度
	 * @return
	 */
	public static String generateKeyBySize(int size) {
		StringBuffer strbKey = new StringBuffer();
		for (int i = 0; i < size; ++i) {
			int nValue = m_generator.nextInt();

			if (nValue < 0) {
				nValue *= -1;
			}

			nValue %= 62;

			strbKey.append(m_strbKeyChars.charAt(nValue));
		}
		String strValue = strbKey.toString();

		return strValue;
	}
}