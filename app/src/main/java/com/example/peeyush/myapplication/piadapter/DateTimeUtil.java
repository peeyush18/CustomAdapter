package com.example.peeyush.myapplication.piadapter;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {
    private static final String TAG = "DateTimeUtil";

    /**
     * METHOD TO WRITE THE GOT VALUE INTO THE RESPECIVE TABLE IN THE DATABASE *
     */
    public static String changeDateFormat(String date, String DateFormat,
            String from_Format) {
        if (date == null || date.length() == 0) {
            return "NA";
        }
        Date parsed = new Date();
        java.text.DateFormat inputFormat = new SimpleDateFormat(from_Format);
        // inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        java.text.DateFormat outputFormat = new SimpleDateFormat(DateFormat);

        try {
            parsed = inputFormat.parse(date);
            Log.d(TAG, "PARSED DATE IN TRY " + parsed);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        String outputText = outputFormat.format(parsed);
        Log.d(TAG, "DATE PARSED " + outputText);
        return outputText;
    }

    public static boolean isValidDate(String inDate, String format) {

        if (inDate == null) {
            return false;
        }

        // set the format to use as a constructor argument
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        if (inDate.trim().length() != dateFormat.toPattern().length()) {
            return false;
        }

        dateFormat.setLenient(false);

        try {
            // parse the inDate parameter
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    public static String setupDateFormat(int mMonth, int mDate, int mYear,
            int mHour, int mMinute) {
        String mDeliveryTime = new String();
        String date = new String();
        String month = new String();
        String hour = new String();
        String minute = new String();
        int months = mMonth + 1;
        if (mDate <= 9) {
            date = "0" + mDate;
        } else {
            date = mDate + "";
        }

        if (months <= 9) {
            month = "0" + months;
        } else {
            month = "" + months;
        }

        if (mHour <= 9) {
            hour = "0" + mHour;
        } else {
            hour = "" + mHour;
        }

        if (mMinute <= 9) {
            minute = "0" + mMinute;
        } else {
            minute = "" + mMinute;
        }
        if (mHour == 0 && mMinute == 0) {
            mDeliveryTime = "" + mYear + "-" + month + "-" + date;
        } else {
            mDeliveryTime = "" + mYear + "-" + month + "-" + date + " " + hour
                    + ":" + minute + ":" + "00";
        }
        return mDeliveryTime;
    }

    public static int compareDate(String firstDate, String secondDate,
            String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date1 = sdf.parse(firstDate);
            Date date2 = sdf.parse(secondDate);
            int dateDiff = date1.compareTo(date2);
            if (dateDiff > 0) {
                // Log.v("app", "Date1 is after Date2");
                return 1;
            } else if (dateDiff < 0) {
                // Log.v("app", "Date1 is before Date2");
                return -1;
            } else if (dateDiff == 0) {
                // Log.v("app", "Date1 is equal to Date2");
                return 0;
            }
            return -2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -2;
    }

    public static String getDateBeforeDays(int days) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        return dateFormat.format(cal.getTime());
    }

    public static String getTodayFordate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    public static String getFordate(Calendar cal) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return dateFormat.format(cal.getTime());
    }

    public static String getDateStr(Calendar cal, String dateFormatStr) {
        DateFormat dateFormat = new SimpleDateFormat(dateFormatStr, Locale.US);
        return dateFormat.format(cal.getTime());
    }

    public static String getDateStr(String timeInMillis, String dateFormatStr) {
        if (timeInMillis == null || timeInMillis.isEmpty()) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        long forDate = 0;
        try {
            forDate = Long.parseLong(timeInMillis);
        } catch (NumberFormatException e) {
            return timeInMillis;
        }
        calendar.setTimeInMillis(forDate);
        return getDateStr(calendar, dateFormatStr);
    }

    public static String getFullFormatedDate(String timeInMillis) {
        return getDateStr(timeInMillis, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurrentTimeToDisplay() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss",
                Locale.US);
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    public static String getCurrentDateTimeToDisplay() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.US);
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    public static Calendar getTime(String timeStr){
        Date date = getDate("HH:mm" , timeStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static Date getDate(String format, String dateStr) {
        SimpleDateFormat formats = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = formats.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getTimeToDisplay(String timeFormat) {
        DateFormat dateFormat = new SimpleDateFormat(timeFormat, Locale.US);
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    public static String getFirstDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,
                Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(c.getTime());
    }

    public static String getLastDay() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,
                Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(c.getTime());
    }

    public static String getTodayStartTimeInMillis() {
        Calendar cal = Calendar.getInstance();
        long time = cal.getTimeInMillis();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        return "" + cal.getTimeInMillis();
    }

    public static String getTodaysDateTime() {
        return getTodaysDate("yyyy-MM-dd HH:mm:ss");
    }

    public static String getTodaysDate() {
        return getTodaysDate("yyyy-MM-HH");
    }


    public static String getTodaysDate(String format) {
        Date date = new Date();
        SimpleDateFormat iDate = new SimpleDateFormat(format);
        String todaysDate = iDate.format(date);

        return todaysDate;
    }

    public static String getCurrentTime24() {
        String currentTime = getTodaysDate("HH:mm");
        return currentTime;
    }

    public static String getCurrentTime() {
        String currentTime = getTodaysDate("hh:mm a");
        return currentTime;
    }

    public static Calendar getCalendarForTime24(String timeStr) {
        return getCalendarTimeFor("HH:mm", timeStr);
    }

    public static Calendar getCalendarForTime(String timeStr) {
        return getCalendarTimeFor("hh:mm a", timeStr);
    }

    public static Calendar getCalendarTimeFor(String format, String timeStr) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            cal.setTime(sdf.parse(timeStr));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return cal;
    }

    public static Calendar getCalenderForDate(String dateStr) {

        Date date = getDate("yyyy-MM-dd", dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date getDateForDate(String dateStr) {
        Date date = getDate("yyyy-MM-dd", dateStr);
        return date;
    }

    public static long getTodayTimeAt(String time) {
        String[] times = time.split(":");
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);
        return getTodayTimeAt(hour, minute);
    }

    public static long getTodayTimeAt(int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        return cal.getTimeInMillis();
    }

    public static boolean isTimeToday(long startTime) {

        Calendar today = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(startTime);
        return today.get(Calendar.DAY_OF_MONTH) == startDate.get(Calendar.DAY_OF_MONTH)
                && today.get(Calendar.MONTH) == startDate.get(Calendar.MONTH);
    }

    public static String getTimeText(String timeStr) {
        try {
            long time = Long.parseLong(timeStr);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(time);
            return cal.get(Calendar.DAY_OF_MONTH) + " " + getMonthName(cal.get(Calendar.MONTH))
                    + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);

        } catch (NumberFormatException e) {
            return timeStr;
        }
    }

    public static String getTimeDifferenceText(String startTimeStr, String endTimeStr) {
        try {
            long startTime = Long.parseLong(startTimeStr);
            long endTime = Long.parseLong(endTimeStr);
            startTime = (endTime - startTime) / (1000 * 60 * 60);
            startTime = (startTime < 0) ? startTime * -1 : startTime;
            return "" + startTime;
        } catch (NumberFormatException e) {

        }
        return "0";

    }

    public static String getDateTextFor(String dateLongStr) {
        long time =0;
        try{
            time  = Long.parseLong(dateLongStr);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return getDateStr(calendar, "yyyy-MM-dd");
    }

    public static boolean isAutoTimeEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global
                    .getInt(context.getContentResolver(), Settings.Global.AUTO_TIME, 0) > 0;
        } else {
            return Settings.System
                    .getInt(context.getContentResolver(), Settings.System.AUTO_TIME, 0) > 0;
        }
    }

    public static boolean isAutoTimeZoneEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global
                    .getInt(context.getContentResolver(), Settings.Global.AUTO_TIME_ZONE, 0) > 0;
        } else {
            return Settings.System
                    .getInt(context.getContentResolver(), Settings.System.AUTO_TIME_ZONE, 0) > 0;
        }
    }


    public static String getMonthName(int month) {
        switch (month) {
            case 0:
                return "JAN";
            case 1:
                return "FEB";
            case 2:
                return "MAR";
            case 3:
                return "APR";
            case 4:
                return "MAY";
            case 5:
                return "JUN";
            case 6:
                return "JUL";
            case 7:
                return "AUG";
            case 8:
                return "SEP";
            case 9:
                return "OCT";
            case 10:
                return "NOV";
            case 11:
                return "DEC";
            default:
                return "JAN";
        }
    }

    public static String[] getPrevMonthsName(int currentMonth, int previousCount) {
        String[] monthNames = new String[previousCount];
        for (int i = 0; i < previousCount; i++) {
            monthNames[i] = getMonthName((currentMonth - i >= 0) ?
                            currentMonth - i :
                            11 - (i - currentMonth)
            );
        }

        return monthNames;
    }
}
