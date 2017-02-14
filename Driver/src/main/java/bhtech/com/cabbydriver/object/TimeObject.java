package bhtech.com.cabbydriver.object;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by thanh_nguyen02 on 23/12/2015.
 */
public class TimeObject {
    public static final String TIME_PATTERN = "HH:mm";
    public static Calendar calendar = Calendar.getInstance();
    public static final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());
    public static final DateFormat appDefaultTimeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    public static final DateFormat appDefaultDateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.US);

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

    public static String getStringFromDate(Date date, String format) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        return dateFormat.format(calendar.getTime());
    }

    public static String getStringFromDate(Date date, DateFormat dateFormat) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
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

    public static int getCurrentYear() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
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

    public static String[] getArrayDayOfWeek() {
        return new String[]{"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
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

    public static String[] getArrayMonth() {
        return new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    }

    public static ArrayList<String> getArrayListMonth() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < getArrayMonth().length; i++) {
            list.add(getArrayMonth()[i]);
        }
        return list;
    }

    public static long diffTimeInMiliseconds(Date time1, Date time2) {
        Date diffDays = new Date();
        diffDays.setTime(time1.getTime() - time2.getTime());
        return diffDays.getTime();
    }

    public static Calendar diffTimeInCalendar(Date time1, Date time2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(diffTimeInMiliseconds(time1, time2));
        return calendar;
    }

    /**
     * To get the last day of the entered month, from 1 to 31, depends on which month
     *
     * @param month - month index in a year, start from 0, 0 = Jan, and so on, 11 = Dec
     * @param year  - number of year, start from 1
     * @return - the last day in month, also the number of days in month
     */
    public static int getLastDayOfMonth(int month, int year) {
        Calendar mycal = new GregorianCalendar(year, month, 1);
        return mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static String add0ToDayMonth(int dayOrMonth) {
        if (dayOrMonth < 10) {
            return "0" + dayOrMonth;
        } else {
            return String.valueOf(dayOrMonth);
        }
    }

    public static boolean monthHas31Days(int p) {
        if (p == 1 || p == 3 || p == 5 || p == 7 || p == 8 || p == 10 || p == 12) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }

    public static String getStringDayOfWeek(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        return getArrayDayOfWeek()[cal.get(Calendar.DAY_OF_WEEK) - 1];
    }

    public static int getCurrentWeekOfYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.WEEK_OF_YEAR);
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

    public static Intent addEventToCalendar(Date endTime, String title, int fisrtReminders, int secondReminders) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(endTime);
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis());
        cal.add(Calendar.MINUTE, -secondReminders);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());
        cal.add(Calendar.MINUTE, -fisrtReminders);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());
        intent.putExtra(CalendarContract.Events.HAS_ALARM, true);
        intent.putExtra(CalendarContract.Events.ALLOWED_REMINDERS, CalendarContract.Reminders.METHOD_ALARM);
        intent.putExtra(CalendarContract.Events.TITLE, title);
        //intent.putExtra(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALARM);
        //Lap lai hang nam
        //intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");
        return intent;
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


    public static void datePickerDialog(Context context, DatePickerDialog.OnDateSetListener onSetDate) {
        final Calendar cal = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, onSetDate,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }

    public static Date getFirstDayOfMonth(int listMonthPosition) {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, listMonthPosition);
        cal.set(Calendar.DATE, 1);
        return cal.getTime();
    }

    public static Date getLastDayOfMonth(int listMonthPosition) {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, listMonthPosition);
        cal.set(Calendar.DATE, getLastDayOfMonth(listMonthPosition, cal.get(Calendar.YEAR)));
        return cal.getTime();
    }

    public static Date getFirstDayOfWeek(int position) {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.WEEK_OF_YEAR, position);
        cal.add(Calendar.DATE, -2);
        return cal.getTime();
    }

    public static Date getLastDayOfWeek(Date firstDayOfWeek) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(firstDayOfWeek);
        cal.add(Calendar.DATE, 6);
        return cal.getTime();
    }
}
