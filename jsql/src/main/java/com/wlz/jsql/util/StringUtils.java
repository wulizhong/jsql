package com.wlz.jsql.util;



import java.util.Arrays;
import java.util.List;

public class StringUtils {


	public static String removeChar(String str, int index){
		String str1 = str.substring(0,index);
		String str2 = str.substring(index+1);
		return str1+str2;
	}

	public static String join(Object[] array, String split){
		if(array == null)
			return null;
		return join(Arrays.asList(array),split);
	}
//	public static String join(Integer[] array, String split){
//		if(array == null)
//			return null;
//		return join(Arrays.asList(array),split);
//	}
//	public static String join(Long[] array, String split){
//		if(array == null)
//			return null;
//		return join(Arrays.asList(array),split);
//	}
	
	public static String join(List<?> list, String split){
	
		if(list == null)
			return null;
		if(CollectionUtils.isEmpty(list)){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0;i<list.size();i++){
			sb.append(list.get(i));
			if(i<list.size()-1){
				sb.append(split);
			}
		}
		return sb.toString();
	}
	
	public static boolean isEmpty(CharSequence str){
		if(str == null)
			return true;
		return str.length() == 0;
	}
	
	public static boolean isNotEmpty(CharSequence str){
		return !isEmpty(str);
	}

//	public static final CharSequenceTranslator UNESCAPE_JAVA =
//			new AggregateTranslator(
//					new OctalUnescaper(),     // .between('\1', '\377'),
//					new UnicodeUnescaper(),
//					new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_UNESCAPE()),
//					new LookupTranslator(
//							new String[][] {
//									{"\\\\", "\\"},
//									{"\\\"", "\""},
//									{"\\'", "'"},
//									{"\\", ""}
//							})
//			);
//
//	public static final CharSequenceTranslator UNESCAPE_ECMASCRIPT = UNESCAPE_JAVA;
//	public static final CharSequenceTranslator UNESCAPE_XML =
//			new AggregateTranslator(
//					new LookupTranslator(EntityArrays.BASIC_UNESCAPE()),
//					new LookupTranslator(EntityArrays.APOS_UNESCAPE()),
//					new NumericEntityUnescaper()
//			);
//	public static final String unescapeEcmaScript(final String input) {
//		return UNESCAPE_ECMASCRIPT.translate(input);
//	}
//	public static final String unescapeXml(final String input) {
//		return UNESCAPE_XML.translate(input);
//	}
}
