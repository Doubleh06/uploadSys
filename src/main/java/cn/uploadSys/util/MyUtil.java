package cn.uploadSys.util;

import com.google.common.base.Joiner;

import java.beans.IntrospectionException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtil {
    public static String JoinStrBySymbol(List<String> strList, String symbol) {
        return Joiner.on(symbol).join(strList);
    }

    //unicode 转中文
    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            //group 6728
            String group = matcher.group(2);
            //ch:'木' 26408
            ch = (char) Integer.parseInt(group, 16);
            //group1 \u6728
            String group1 = matcher.group(1);
            str = str.replace(group1, ch + "");
        }
        return str;
    }

    public static BigDecimal getDurations(String startTime,String endTime){
        BigDecimal duration = BigDecimal.ZERO;
        BigDecimal startTime_h = new BigDecimal(startTime.split(":")[0]);
        BigDecimal startTime_m = new BigDecimal(startTime.split(":")[1]);
        BigDecimal endTime_h = new BigDecimal(endTime.split(":")[0]);
        BigDecimal endTime_m = new BigDecimal(endTime.split(":")[1]);
        return endTime_h.subtract(startTime_h).add(endTime_m.subtract(startTime_m));

    }


    public static Integer getStartAndEndDate(Integer year, Integer month){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH, month-1);//Java月份才0开始算
        return cal.getActualMaximum(Calendar.DATE);
    }

    public static String makeTimeId(){
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmssSSS");
        return sdf.format(new Date())+((int)(Math.random()*900)+100);
    }
}
