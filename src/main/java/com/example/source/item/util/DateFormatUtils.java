package com.example.source.item.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateFormatUtils {

    private static final ThreadLocal<? extends DateFormat> df  = new ThreadLocal<DateFormat>(){

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
        }
    };

    public static DateFormat getDateFormat(){
        return df.get();
    }

}
