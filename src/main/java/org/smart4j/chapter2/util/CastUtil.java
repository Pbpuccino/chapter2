package org.smart4j.chapter2.util;

/**
 * Created by gcb on 2018/2/23.
 */
public class CastUtil {

    /**
     * 转换成String型
     * @param obj
     * @return
     */
    public static String castString(Object obj){
        return castString(obj,"");
    }

    /**
     * 转为String型（可指定默认值）
     */
    public static String castString(Object obj , String defaultValue){
        return obj != null ? String.valueOf(obj) : defaultValue;
    }


    /**
     * 转为double型
     */
    public static double castDouble(Object obj){
        return castDouble(obj , 0);
    }

    /**
     * 转为double型（可指定默认值）
     */
    public static double castDouble(Object obj , double defaultValue){
        double doubleValue = defaultValue;
        if(obj != null){
            String strValue = castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                try{
                    doubleValue = Double.parseDouble(strValue);
                }catch (NumberFormatException e){
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    /**
     * 转为long型
     */
    public static  long castLong(Object obj){
        return castLong(obj , 0 );
    }

    /**
     * 转为long型（可指定默认值）
     */
    public static long castLong(Object obj , long defaultValue){
        long longValue = defaultValue;
        if(obj != null){
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)){
                try{
                    longValue = Long.parseLong(strValue);
                }catch (NumberFormatException e){
                    longValue = defaultValue;
                }
            }
        }
        return longValue;
    }

    /**
     * 转为int型
     */
    public static int castInt(Object obj){
        return castInt(obj , 0);
    }

    /**
     * 转为int型（可指定默认值）
     */
    public static int castInt(Object obj , int defaultValue){
        int intValue = defaultValue;
        if (obj != null){
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)){
                try {
                    intValue = Integer.parseInt(strValue);
                }catch (NumberFormatException e){
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }

    /**
     * 转为boolean型
     */
    public static boolean castBoolean(Object obj){
        return castBoolean(obj , false);
    }

    /**
     * 转为boolean型(可指定默认值）
     */
    public static boolean castBoolean(Object obj , boolean defaultValue){
        boolean booleanVaule = defaultValue;
        if (obj != null){
            booleanVaule = Boolean.parseBoolean(castString(obj));
        }
        return booleanVaule;
    }
}
