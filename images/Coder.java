/**
 * 文 件 名:  Coder
 * 版    权:  Quanten Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zping
 * 修改时间:  2017/8/12 0012
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanten.core.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author zping
 * @version 2017/8/12 0012
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Coder
{
	/*SHA 加密*/
	public static final String KEY_SHA = "SHA";

	/*MD5 加密*/
	public static final String KEY_MD5 = "MD5";

	public static final String KEY_SHA_1 = "SHA-1";

	/**
	 * BASE64解密
	 *
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64 (String key) throws Exception
	{
		return (new BASE64Decoder ()).decodeBuffer (key);
	}

	/**
	 * BASE64加密
	 *
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64 (byte[] key) throws Exception
	{
		return (new BASE64Encoder ()).encodeBuffer (key);
	}

	/**
	 * MD5加密
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5 (byte[] data) throws Exception
	{
		MessageDigest md5 = MessageDigest.getInstance (KEY_MD5);
		md5.update (data);
		return md5.digest ();
	}

	/**
	 * SHA加密
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA (byte[] data) throws Exception
	{
		MessageDigest sha = MessageDigest.getInstance (KEY_SHA);
		sha.update (data);
		return sha.digest ();
	}

	/**
	 * SHA-1 加密
	 *
	 * @param data
	 * @return
	 */
	public static String encryptSHA1 (String data) throws Exception
	{
		MessageDigest md = MessageDigest.getInstance (KEY_SHA_1);

		md.update (data.getBytes ("utf-8"), 0, data.length ());
		byte[] sha1hash = md.digest ();
		return convertToHex (sha1hash);
	}

	/**
	 * 转成16进制数据
	 *
	 * @param data
	 * @return
	 */
	public static String convertToHex (byte[] data)
	{
		StringBuffer buf = new StringBuffer ();
		for (int i = 0; i < data.length; i++)
		{
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do
			{
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append ((char) ('0' + halfbyte));
				else
					buf.append ((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			}
			while (two_halfs++ < 1);
		}
		return buf.toString ();
	}
}
