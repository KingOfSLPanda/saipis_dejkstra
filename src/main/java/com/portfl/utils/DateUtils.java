package com.portfl.utils;

import com.portfl.constants.Constants;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vlad on 22.03.17.
 */
public class DateUtils {

    private static Calendar calendar = Calendar.getInstance();

    public static Date getNextDayDate() {
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, Constants.Common.ONE);
        return calendar.getTime();
    }

}
