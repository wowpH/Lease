package com.ph.lease.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author PengHao
 */
public class DateConverter implements Converter<String, Date> {

    private static final SimpleDateFormat DEFAULT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Date convert(String s) {
        Date date = null;
        try {
            date = DEFAULT.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
