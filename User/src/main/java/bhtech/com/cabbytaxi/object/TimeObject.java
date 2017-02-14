package bhtech.com.cabbytaxi.object;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.CalendarContract;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by thanh_nguyen02 on 23/12/2015.
 */
public class TimeObject extends BaseObject {
    public static final String TIME_PATTERN = "HH:mm";
    public static final String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";
    public static String utcTimeFormat = "yyyy-MM-dd'T'hh:mm:ss'Z'";
    public static TimeZone currentTimeZone = TimeZone.getDefault();
    public static TimeZone serverTimeZone = TimeZone.getTimeZone("Etc/GMT");

    public static Calendar calendar = Calendar.getInstance();
    public static final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

    public static Date getCurrentDateTime() {
        calendar = Calendar.getInstance();
        return new Date(String.valueOf(calendar.getTime()));
    }

    public static String getCurrentDateTime(DateFormat dateFormat, DateFormat timeFormat) {
        return getCurrentDate(dateFormat) + " " + getCurrentTime(timeFormat);
    }

    public static String getCurrentDate() {
        calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

    public static String getCurrentDate(DateFormat dateFormat) {
        calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

    public static String getCurrentTime(DateFormat dateFormat) {
        calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

    public static String getCurrentTime() {
        calendar = Calendar.getInstance();
        return timeFormat.format(calendar.getTime());
    }

    public static int getCurrentSecond() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.SECOND);
    }

    public static int getCurrentMinute() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    public static int getCurrentHour() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR);
    }

    public static int getCurrentDay() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int getCurrentMonth() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

    public static String parseDate(Date date, DateFormat dateFormat) {
        return dateFormat.format(date);
    }

    public static String getStringCurrentMonth() {
        calendar = Calendar.getInstance();
        switch (calendar.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
                return "January";
            case Calendar.FEBRUARY:
                return "February";
            case Calendar.MARCH:
                return "March";
            case Calendar.APRIL:
                return "April";
            case Calendar.MAY:
                return "May";
            case Calendar.JUNE:
                return "June";
            case Calendar.JULY:
                return "July";
            case Calendar.AUGUST:
                return "August";
            case Calendar.SEPTEMBER:
                return "September";
            case Calendar.OCTOBER:
                return "October";
            case Calendar.NOVEMBER:
                return "November";
            case Calendar.DECEMBER:
                return "December";
            default:
                return null;
        }
    }

    public static String getStringMonth(int month) {
        switch (month) {
            case Calendar.JANUARY:
                return "January";
            case Calendar.FEBRUARY:
                return "February";
            case Calendar.MARCH:
                return "March";
            case Calendar.APRIL:
                return "April";
            case Calendar.MAY:
                return "May";
            case Calendar.JUNE:
                return "June";
            case Calendar.JULY:
                return "July";
            case Calendar.AUGUST:
                return "August";
            case Calendar.SEPTEMBER:
                return "September";
            case Calendar.OCTOBER:
                return "October";
            case Calendar.NOVEMBER:
                return "November";
            case Calendar.DECEMBER:
                return "December";
            default:
                return null;
        }
    }

    public static int getCurrentDateofMonth() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    public static int getDayOfWeek() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static String getStringDayOfWeek() {
        calendar = Calendar.getInstance();
        switch ((calendar.get(Calendar.DAY_OF_WEEK))) {
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
            case Calendar.SUNDAY:
                return "Sunday";
            default:
                return null;
        }
    }

    public static ArrayList<String> getListMonth() {
        ArrayList<String> list = new ArrayList<>();
        list.add("January");
        list.add("February");
        list.add("March");
        list.add("April");
        list.add("May");
        list.add("June");
        list.add("July");
        list.add("August");
        list.add("September");
        list.add("October");
        list.add("November");
        list.add("December");
        return list;
    }

    public static Date getDateFromTimeUTC(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getTimeUTCFromDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static void addReminderInCalendar(Context context, String title, String description, Date beginTime) {
        /** Inserting an event in calendar. */

        Calendar cal = Calendar.getInstance();
        cal.setTime(beginTime);
        Uri EVENTS_URI = Uri.parse(getCalendarUriBase(true) + "events");
        ContentResolver cr = context.getContentResolver();
        TimeZone timeZone = TimeZone.getDefault();

        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.TITLE, title);
        values.put(CalendarContract.Events.DESCRIPTION, description);
        values.put(CalendarContract.Events.ALL_DAY, false);
        values.put(CalendarContract.Events.DTSTART, cal.getTimeInMillis());
        cal.add(Calendar.HOUR, 1);
        values.put(CalendarContract.Events.DTEND, cal.getTimeInMillis());
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
        values.put(CalendarContract.Events.HAS_ALARM, 1);
        Uri event = cr.insert(EVENTS_URI, values);

        //Adding reminder for event added.
        Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(true) + "reminders");
        values = new ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID, Long.parseLong(event.getLastPathSegment()));
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        values.put(CalendarContract.Reminders.MINUTES, ContantValuesObject.CALENDAR_REMINDERS_FIRSTTIME);
        cr.insert(REMINDERS_URI, values);

        values = new ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID, Long.parseLong(event.getLastPathSegment()));
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        values.put(CalendarContract.Reminders.MINUTES, ContantValuesObject.CALENDAR_REMINDERS_SECONDTIME);
        cr.insert(REMINDERS_URI, values);
    }

    //Returns Calendar Base URI, supports both new and old OS
    private static String getCalendarUriBase(boolean eventUri) {
        Uri calendarURI = null;
        try {
            if (android.os.Build.VERSION.SDK_INT <= 7) {
                calendarURI = (eventUri) ? Uri.parse("content://calendar/") :
                        Uri.parse("content://calendar/calendars");
            } else {
                calendarURI = (eventUri) ? Uri.parse("content://com.android.calendar/") :
                        Uri.parse("content://com.android.calendar/calendars");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendarURI.toString();
    }
}
