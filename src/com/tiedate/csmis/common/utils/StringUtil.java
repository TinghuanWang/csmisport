package com.tiedate.csmis.common.utils ;

import java.util.* ;

/**
 *
 * <p>Title: </p>
 *
 * <p>Description: </p>
 * 实现String相关的一些辅助方法
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: 上海铁大电信</p>
 *
 * @author zhangfm
 * @version 1.0
 */
public class StringUtil {
  private static Hashtable chinese_numbers = new Hashtable(20) ;

  static {
    chinese_numbers.put(Integer.valueOf(1), "一") ;
    chinese_numbers.put(Integer.valueOf(2), "二") ;
    chinese_numbers.put(Integer.valueOf(3), "三") ;
    chinese_numbers.put(Integer.valueOf(4), "四") ;
    chinese_numbers.put(Integer.valueOf(5), "五") ;
    chinese_numbers.put(Integer.valueOf(6), "六") ;
    chinese_numbers.put(Integer.valueOf(7), "七") ;
    chinese_numbers.put(Integer.valueOf(8), "八") ;
    chinese_numbers.put(Integer.valueOf(9), "九") ;
    chinese_numbers.put(Integer.valueOf(10), "十") ;
    chinese_numbers.put(Integer.valueOf(11), "十一") ;
    chinese_numbers.put(Integer.valueOf(12), "十二") ;
    chinese_numbers.put(Integer.valueOf(13), "十三") ;
    chinese_numbers.put(Integer.valueOf(14), "十四") ;
    chinese_numbers.put(Integer.valueOf(15), "十五") ;
    chinese_numbers.put(Integer.valueOf(16), "十六") ;
    chinese_numbers.put(Integer.valueOf(17), "十七") ;
    chinese_numbers.put(Integer.valueOf(18), "十八") ;
    chinese_numbers.put(Integer.valueOf(19), "十九") ;
    chinese_numbers.put(Integer.valueOf(20), "二十") ;
    chinese_numbers.put(Integer.valueOf(21), "二十一") ;
    chinese_numbers.put(Integer.valueOf(22), "二十二") ;
    chinese_numbers.put(Integer.valueOf(23), "二十三") ;
    chinese_numbers.put(Integer.valueOf(24), "二十四") ;
    chinese_numbers.put(Integer.valueOf(25), "二十五") ;
    chinese_numbers.put(Integer.valueOf(26), "二十六") ;
    chinese_numbers.put(Integer.valueOf(27), "二十七") ;
    chinese_numbers.put(Integer.valueOf(28), "二十八") ;
    chinese_numbers.put(Integer.valueOf(29), "二十九") ;
    chinese_numbers.put(Integer.valueOf(30), "三十") ;

  }

  /**
   * private constructor,prevent instantite
   */
  private StringUtil() {
  }

  /**
   * 判断str是否是空字符串
   * @param str String
   * @return boolean return true if str is null or equals "",
   * else return false
   */
  public static boolean isEmptyString(String str) {
    return (str == null || str.equals("")) ? true : false ;
  }

  /**
   * 判断str是否是空字符串
   * @param str String
   * @return boolean return true if str is null or equals "",
   * else return false
   */
  public static boolean isNotEmpty(String str) {
    return (str == null || str.equals("")) ? false : true ;
  }

  /**
   * 把null转化为空字符串
   * @param str String
   * @return String
   */
  public static String nullToEmpty(String str) {
    return (str == null) ? "" : str ;
  }

  /**
   * 把null转化为空字符串
   * @param str String
   * @return String
   */
  public static String nullToEmpty(Object obj) {
    return (obj == null) ? "" : nullToEmpty(obj.toString()) ;
  }

  /**
   * 把null转化为空字符串
   * @param str String
   * @return String
   */
  public static String nullToAnother(Object obj, String other) {
    return (obj == null || (obj.toString() != null && obj.toString().length() == 0))
      ? other : nullToEmpty(obj.toString()) ;
  }

  /**
   * array是否包含str
   * @param array String[]
   * @param str String
   * @return boolean
   */
  public static boolean isArrayContainsValue(String[] array, String str) {
    if (array == null || str == null) {
      return false ;
    }
    Arrays.sort(array) ;
    return Arrays.binarySearch(array, str) >= 0 ;
  }

  /**
   * 构建一个ArrayList,其元素为objects,
   * Arrays.asList()方法返回的ArrayList大小固定，不能修改
   * @param objects Object[]
   * @return List
   */
  public static List arrayToList(Object[] objects) {
    if (objects == null) {
      return new ArrayList() ;
    }
    List arrayList = new ArrayList(objects.length) ;
    for (int i = 0 ; i < objects.length ; i++) {
      arrayList.add(objects[i]) ;
    }
    return arrayList ;
  }

  /**
   * 得到一个文件名的扩展名,此文件名不是文件的全路径
   * @param fileName String
   * @return String
   */
  public static String getFileSuffix(String fileName) {
    if (isEmptyString(fileName)) {
      return "" ;
    }

    String suffix = "" ;
    int at = fileName.lastIndexOf(".") ;
    if (at >= 0) {
      suffix = fileName.substring(at + 1) ;
    }
    return suffix ;
  }

  /**
   * 返回count个str相加的结果
   * @param str String
   * @param count int
   * @return String
   */
  public static String copyStr(String str, int count) {
    StringBuffer sbf = new StringBuffer(str.length() * count) ;
    for (int i = 0 ; i < count ; i++) {
      sbf.append(str) ;
    }
    return sbf.toString() ;
  }

  /**
   * 用replacement去替换toReplace里面的aChar
   *
   * @param toReplace 原始的字符串
   * @param aChar char
   * @param replacement String
   * @return 替换后的字符串
   */
  public static String replaceAllStr(String toReplace, char aChar,
                                     String replacement) {
    return replaceAllStr(toReplace, new String(new char[] {aChar}), replacement) ;
  }

  /**
   * 用replacement去替换toReplace里面的aChar
   *
   * @param toReplace 原始的字符串
   * @param aStr String
   * @param replacement String
   * @return 替换后的字符串
   */
  public static String replaceAllStr(String toReplace, String aStr,
                                     String replacement) {
    if (toReplace == null) {
      return null ;
    }

    if (aStr == null || replacement == null) {
      return toReplace ;
    }

    int index = 0 ;
    while ((index = toReplace.indexOf(aStr, index)) >= 0) {
      toReplace = toReplace.substring(0, index) + replacement + toReplace.substring(index + aStr.length()) ;
      index += replacement.length() ;
    }
    return toReplace ;
  }

  /**
   * 去掉数组中重复的项，返回一个项唯一的数组, 返回的数组可能不是原来的顺序
   *
   * @param objectList 要处理的数组
   * @return 去掉重复项的数组
   */
  public static Object[] uniqueObjects(List objectList) {
    if (objectList == null) {
      return null ;
    }
    TreeSet treeSet = new TreeSet() ;
    for (int i = 0 ; i < objectList.size() ; i++) {
      if (objectList.get(i) != null) {
        treeSet.add(objectList.get(i)) ;
      }
    }
    return treeSet.toArray(new Object[0]) ;
  }

  /**
   * 判断numStr是否是整数数字
   * @param numStr String
   * @return boolean
   */
  public static boolean isIntNumber(String numStr) {
    if (isEmptyString(numStr)) {
      return false ;
    }
    try {
      Integer.parseInt(numStr) ;
      return true ;
    } catch (NumberFormatException ex) {
      return false ;
    }
  }

  /**
   * 判断numStr是否是浮点数数字
   * @param numStr String
   * @return boolean
   */
  public static boolean isFloatNumber(String numStr) {
    if (isEmptyString(numStr)) {
      return false ;
    }
    try {
      Float.parseFloat(numStr) ;
      return true ;
    } catch (NumberFormatException ex) {
      return false ;
    }
  }

  /**
   * 把array中的元素用split作为分隔符连接起来
   * @param array String[]
   * @return String
   */
  public static String arrayToString(String[] array) {
    return arrayToString(array, ",") ;
  }

  /**
   * 把array中的元素用split作为分隔符连接起来
   * @param array String[]
   * @param split String
   * @return String
   */
  public static String arrayToString(String[] array, String split) {
    if (array == null || array.length == 0) {
      return "" ;
    }
    if (isEmptyString(split)) {
      split = "," ;
    }

    StringBuffer buffer = new StringBuffer() ;
    for (int i = 0 ; i < array.length ; i++) {
      if (array[i] != null && array[i].length() > 0) {
        if (buffer.length() > 0) {
          buffer.append(split) ;
        }
        buffer.append(array[i]) ;
      }
    }

    return buffer.toString() ;

  }

  /**
   * 增加url参数
   * @param url String
   * @param paraName String
   * @param paraValue String
   * @return String
   */
  public static String addUrlParameter(String url, String paraName, String paraValue) {
    int index = url.indexOf("?") ;
    if (index >= 0) {
      url += "&" ;
    } else {
      url += "?" ;
    }

    return url + paraName + "=" + paraValue ;
  }

  /**
   * 转化数字为中文数字
   * @param value int
   * @return String
   */
  public static String getChineseNum(int value) {
    if (value < 1 || value > 30) {
      throw new RuntimeException("数值必须属于闭区间[1,30]") ;
    }

    return (String) chinese_numbers.get(Integer.valueOf(value)) ;
  }
}
