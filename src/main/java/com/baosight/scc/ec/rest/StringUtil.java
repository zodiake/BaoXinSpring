package com.baosight.scc.ec.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * 字符串操作工具类
 * 
 * @author libo
 * 
 */
public class StringUtil {

	private static final int TWO_BYTE_START = 0x100;

	/**
	 * 替换字符串中的下划线并根据isFirstUpperCase参数确定下划线后首字母是否大�? 例如�? abc_dec 替换后为 abcDec
	 * 
	 * @param src
	 * @param isFirstUpperCase
	 * @return
	 */
	public static String replaceUnderline(String src, boolean isFirstUpperCase) {
		if (src == null || src.length() == 0) {
			return src;
		}

		String[] res = src.split("_");
		if (res.length == 1) {
			return src.toLowerCase();
		}

		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < res.length; i++) {
			String temp = res[i];

			if (temp == null || temp.trim().length() == 0) {
				continue;
			}

			temp = temp.trim().toLowerCase();

			if (i > 0) {
				temp = temp.substring(0, 1).toUpperCase() 
						+ temp.substring(1);
			}

			buffer.append(temp);
		}// end for
		return buffer.toString();
	}

	/**
	 * 将字符串根据分割符分割为字符串数�?
	 * 
	 * @param s
	 * @param delimiters
	 * @param trimTokens
	 * @param ignoreEmptyTokens
	 * @return
	 */
	public static String[] split(String s, String delimiters,
			boolean trimTokens, boolean ignoreEmptyTokens) {
		StringTokenizer st = new StringTokenizer(s, delimiters);
		List tokens = new ArrayList();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!(ignoreEmptyTokens && token.length() == 0)) {
				tokens.add(token);
			}
		}
		return (String[]) tokens.toArray(new String[tokens.size()]);
	}

	/**
	 * 将一个数组中的内容连接成�?个字符串，各个数组中的元素使用参数delim分隔
	 * 
	 * @param arr
	 * @param delim
	 * @return
	 */
	public static String arrayToString(String[] arr, String delim) {
		if (arr == null) {
			return "null";
		} else {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < arr.length; i++) {
				if (i > 0) {
					sb.append(delim);
				}
				sb.append(arr[i]);
			}
			return sb.toString();
		}
	}

	/**
	 * 本地字符集转换成unicode�?
	 * 
	 * @return java.lang.String
	 */
	public static String native2unicode(String s) {
		if (s == null || s.length() == 0) {
			return null;
		}
		byte[] buffer = new byte[s.length()];

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) >= TWO_BYTE_START) {
				return s;
			}
			buffer[i] = (byte) s.charAt(i);
		}
		return new String(buffer);
	}

	/**
	 * unicode转为本地字符�? 
	 * @param s Unicode编码的字符串 
	 * @return String
	 */
	public static String unicode2native(String s) {
		if (s == null || s.length() == 0) {
			return null;
		}
		char[] buffer = new char[s.length() * 2];
		char c;
		int j = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) >= TWO_BYTE_START ) {
				c = s.charAt(i);
				byte[] buf = ("" + c).getBytes();
				buffer[j++] = (char) buf[0];
				buffer[j++] = (char) buf[1];
			} else {
				buffer[j++] = s.charAt(i);
			}
		}
		return new String(buffer, 0, j);
	}

	/**
	 * 对字符串中制定的字符进行替换
	 * 
	 * @param inString
	 * @param oldPattern
	 * @param newPattern
	 * @return
	 */
	public static String replace(String inString, String oldPattern,
			String newPattern) {
		if (inString == null) {
			return null;
		}
		if (oldPattern == null || newPattern == null) {
			return inString;
		}

		StringBuffer sbuf = new StringBuffer();
		// output StringBuffer we'll build up
		int pos = 0; // Our position in the old string
		int index = inString.indexOf(oldPattern);
		// the index of an occurrence we've found, or -1
		int patLen = oldPattern.length();
		while (index >= 0) {
			sbuf.append(inString.substring(pos, index));
			sbuf.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sbuf.append(inString.substring(pos));

		// remember to append any characters to the right of a match
		return sbuf.toString();
	}

	/**
	 * 替换输入字符串中的HTML字符< �? >
	 * 
	 * @param old
	 * @return
	 */
	public static String replaceHtml(String old) {
		String rt = replace(old, "<", "&lt;");
		rt = replace(rt, ">", "&gt;");
		rt = replace(rt, "'", "&quot;");
		rt = replace(rt, "'", "&quot;");
		rt = replace(rt, "\"", "&quot;");
		return rt;
	}

	/**
	 * 生成随机字符串
	 * 
	 * @param 字符串长度
	 * @return
	 */
	
	 public static String getRandString(int len) {   
	      String str = "";  
	      
	      Random r = new Random();
	      for(int i = 0; i < len; i++) {
	          int rand = r.nextInt(57) + 65;
	          str += new Character((char)rand);
	      }
	      
	      return str;
	  }

	 /**
	  * @判断是否是空字符串
	  * @param str
	  * @return
	  */
	 public static boolean isEmpty(String str)
	 {
		 if (str==null)
			 return true;
		 if (str.trim().length()==0)
			 return true;
		 return false;
	 }
	 public static boolean isEmptyObj(Object obj){
		 try{
			 String str=(String)obj;
			 return isEmpty(str);
			 }
		 catch(Exception e){
			 return false;
		 }
		 
	 }
}