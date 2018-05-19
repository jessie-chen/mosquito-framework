package org.mosquito.framework.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** .
 * 字符串操作工具类
 *  .
 * @author chenl
 * 
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	protected static final Logger LOGGER = LoggerFactory.getLogger(StringUtils.class);

	private static final String UPPERALPHA_NUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * 获取UUID字符串
	 * 
	 * @param isContainsDash
	 *            是否包含-符号
	 * @return isContainsDash为true，则返回包含破折号的UUID字符串，否则不包含
	 */
	public static String getUUID(boolean isContainsDash) {
		if (isContainsDash) {
			return UUID.randomUUID().toString();
		} else {
			return UUID.randomUUID().toString().replaceAll("-", StringUtils.EMPTY);
		}

	}

	/**
	 * 功能描述: 使用给定的分隔符合并字符串数组成为新的字符串。
	 * 
	 * @param array
	 *            要合并的字符串数组
	 * @param separator
	 *            分隔符
	 * @return 由分隔符分隔字符串数组合并的新的字符串
	 */
	public static String join(Object[] array, String separator) {

		if (ObjectUtils.isNullOrEmpty(array)) {
			return StringUtils.EMPTY;
		}
		StringBuffer buf = new StringBuffer();
		int length = array.length;
		for (int i = 0; i < length - 1; i++) {
			Object b = array[i];
			if (ObjectUtils.isNullOrEmpty(b)) {
				continue;
			}
			buf.append(b);
			buf.append(separator);
		}
		buf.append(array[length - 1]);
		return buf.toString();
	}

	/**
	 * 生成指定长度的随机数
	 * 
	 * @param length
	 *            随机数的长度
	 * @return 随机数
	 */
	public static String randomString(int length) {

		Random randGen = null;
		char[] numbersAndLetters = null;
		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			randGen = new Random();
			// 由于数据当前使用的字符集默认为不区分大小写，为了不造成不必要的问题，全部随机为大写
			numbersAndLetters = UPPERALPHA_NUM.toCharArray();
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(UPPERALPHA_NUM.length())];
		}
		return new String(randBuffer);
	}

	/**
	 * 将字符串转换为字节数组，默认编码为UTF-8
	 * 
	 * @param str
	 *            待转换的字符串
	 * @return 字节数组
	 */
	public static byte[] stringToByte(String str) {
		return stringToByte(str, "UTF-8");
	}

	/**
	 * 根据编码，将自己字符串转换为字节数组
	 * 
	 * @param str
	 *            待转换字符串
	 * @param charEncode
	 *            字符编码
	 * @return 转换后字节数组
	 */
	public static byte[] stringToByte(String str, String charEncode) {
		byte[] destObj = null;
		try {
			if (!StringUtils.isNotEmpty(str)) {
				destObj = new byte[0];
				return destObj;
			} else {
				destObj = str.getBytes(charEncode);
			}
		} catch (UnsupportedEncodingException e) {
			LOGGER.info("字符串转换为带编码的字节数组异常: " + e);
			throw new IllegalArgumentException(e);
		}
		return destObj;
	}

	/**
	 * 将字节数组转换为字符串，默认编码为UTF-8
	 * 
	 * @param srcObj
	 *            字节数组
	 * @return 字符串
	 */
	public static String byteToString(byte[] srcObj) {
		return byteToString(srcObj, "UTF-8");
	}

	/**
	 * 根据编码将字节数组转换为字符串
	 * 
	 * @param srcObj
	 *            字节数组
	 * @param charEncode
	 *            编码
	 * @return 字符串
	 */
	public static String byteToString(byte[] srcObj, String charEncode) {
		String destObj = null;
		try {
			destObj = new String(srcObj, charEncode);
		} catch (UnsupportedEncodingException e) {
			LOGGER.info("字节数组转换为带编码的字符串异常: " + e);
			throw new IllegalArgumentException(e);
		}
		return destObj.replaceAll("\0", " ");
	}

	/**
	 * 过滤表情字符串
	 * 
	 * @param emojStr
	 *            过滤的字符串
	 * @return 过滤后的字符串
	 */
	public static String removeNonBmpUnicode(String emojStr) {
		if (ObjectUtils.isNullOrEmpty(emojStr)) {
			return emojStr;
		}
		String pattern = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
		Pattern emoji = Pattern.compile(pattern);
		Matcher emojiMatcher = emoji.matcher(emojStr);
		emojStr = emojiMatcher.replaceAll(EMPTY);
		return emojStr;
	}

	/**
	 * 获取默认字符串
	 * 
	 * @param str
	 *            需判断的字符串
	 * @param defaultStr
	 *            默认字符串
	 * @return 判断后的取值
	 */
	public static String defaultString(final String str, final String defaultStr) {
		return ObjectUtils.isNullOrEmpty(str) ? defaultStr : str.trim();
	}
}
