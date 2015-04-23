package aibroker.util;

import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import net.sourceforge.jdatepicker.impl.UtilCalendarModel;
import org.joda.time.DateTime;
import org.joda.time.Days;

@SuppressWarnings("serial")
public class Moment extends GregorianCalendar {

    public static Moment fromCompactIso(final String str) {
        int year = 1, month = 0, dayOfMonth = 1, hourOfDay = 0, minute = 0, second = 0;
        switch (str.length()) {
            case 14:
                hourOfDay = Integer.parseInt(str.substring(8, 10));
                minute = Integer.parseInt(str.substring(10, 12));
                second = Integer.parseInt(str.substring(12, 14));
            case 8:
                year = Integer.parseInt(str.substring(0, 4));
                month = Integer.parseInt(str.substring(4, 6)) - 1;
                dayOfMonth = Integer.parseInt(str.substring(6, 8));
                break;
            case 6:
                hourOfDay = Integer.parseInt(str.substring(0, 2));
                minute = Integer.parseInt(str.substring(2, 4));
                second = Integer.parseInt(str.substring(4, 6));
        }
        return new Moment(year, month, dayOfMonth, hourOfDay, minute, second);
    }

    public static Moment fromIso(final String str) {
        int year = 1, month = 0, dayOfMonth = 1, hourOfDay = 0, minute = 0, second = 0;
        switch (str.length()) {
            case 19:
                hourOfDay = Integer.parseInt(str.substring(11, 13));
                minute = Integer.parseInt(str.substring(14, 16));
                second = Integer.parseInt(str.substring(17, 19));
            case 10:
                year = Integer.parseInt(str.substring(0, 4));
                month = Integer.parseInt(str.substring(5, 7)) - 1;
                dayOfMonth = Integer.parseInt(str.substring(8, 10));
                break;
            case 8:
                hourOfDay = Integer.parseInt(str.substring(0, 2));
                minute = Integer.parseInt(str.substring(3, 5));
                second = Integer.parseInt(str.substring(6, 8));
                break;
            default:
                throw new BrokerException("Unsupported format");
        }
        return new Moment(year, month, dayOfMonth, hourOfDay, minute, second);
    }

    public static Moment fromIso(final String date, final String time) {
        int year = 1, month = 0, dayOfMonth = 1, hourOfDay = 0, minute = 0, second = 0;
        year = Integer.parseInt(date.substring(0, 4));
        month = Integer.parseInt(date.substring(5, 7)) - 1;
        dayOfMonth = Integer.parseInt(date.substring(8, 10));
        hourOfDay = Integer.parseInt(time.substring(0, 2));
        minute = Integer.parseInt(time.substring(3, 5));
        second = Integer.parseInt(time.substring(6, 8));
        return new Moment(year, month, dayOfMonth, hourOfDay, minute, second);
    }

    public static Moment getAbsloluteBeginning() {
        return new Moment(2000, Calendar.JANUARY, 1);
    }

    public static Moment getAbsoluteEnd() {
        return new Moment(getEndOfToday().get(Calendar.YEAR) + 1, Calendar.DECEMBER, 31);
    }

    public static Moment getBeginningOfToday() {
        final Moment today = new Moment();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        return today;
    }

    public static int getCurrentYear() {
        return getNow().get(Calendar.YEAR);
    }

    public static int getDaysBetween(final Calendar from, final Calendar to) {
        final DateTime t0 = new DateTime(from.getTimeInMillis());
        final DateTime t1 = new DateTime(to.getTimeInMillis());
        return Days.daysBetween(t0, t1).getValue(0);
    }

    public static Moment getEndOfToday() {
        final Moment today = new Moment();
        today.set(Calendar.HOUR_OF_DAY, 23);
        today.set(Calendar.MINUTE, 59);
        today.set(Calendar.SECOND, 50);
        today.set(Calendar.MILLISECOND, 0);
        return today;
    }

    public static Moment getNow() {
        return new Moment();
    }

    public static String indexToShortMonthName(final int month) {
        return shortName.get(month);
    }

    public static int monthNameToIndex(String str) {
        str = str.trim().toUpperCase();
        switch (str) {
            case "IAN":
                return Calendar.JANUARY;
            case "FEB":
                return Calendar.FEBRUARY;
            case "MAR":
                return Calendar.MARCH;
            case "APR":
                return Calendar.APRIL;
            case "MAI":
                return Calendar.MAY;
            case "IUN":
                return Calendar.JUNE;
            case "IUL":
                return Calendar.JULY;
            case "AUG":
                return Calendar.AUGUST;
            case "SEP":
                return Calendar.SEPTEMBER;
            case "OCT":
                return Calendar.OCTOBER;
            case "NOI":
            case "NOV":
                return Calendar.NOVEMBER;
            case "DEC":
                return Calendar.DECEMBER;
        }
        throw new BrokerException("Invalid month specifier: " + str);
    }

    public static Moment valueOf(final Object value) {
        if (value == null) { return getNow(); }
        if (value instanceof Calendar) {
            final Calendar model = (Calendar) value;
            return new Moment(model.get(Calendar.YEAR), model.get(Calendar.MONTH), model.get(Calendar.DAY_OF_MONTH), model.get(Calendar.HOUR_OF_DAY), model.get(Calendar.MINUTE), model.get(Calendar.SECOND));
        }
        if (value instanceof UtilCalendarModel) {
            final UtilCalendarModel model = (UtilCalendarModel) value;
            return new Moment(model.getYear(), model.getMonth(), model.getDay());
        }
        throw new InvalidParameterException(value.getClass().getName());
    }

    private static final Map<Integer, String> shortName = new HashMap<Integer, String>();

    static {
        shortName.put(Calendar.JANUARY, "IAN");
        shortName.put(Calendar.FEBRUARY, "FEB");
        shortName.put(Calendar.MARCH, "MAR");
        shortName.put(Calendar.APRIL, "APR");
        shortName.put(Calendar.MAY, "MAI");
        shortName.put(Calendar.JUNE, "IUN");
        shortName.put(Calendar.JULY, "IUL");
        shortName.put(Calendar.AUGUST, "AUG");
        shortName.put(Calendar.SEPTEMBER, "SEP");
        shortName.put(Calendar.OCTOBER, "OCT");
        shortName.put(Calendar.NOVEMBER, "NOI");
        shortName.put(Calendar.DECEMBER, "DEC");
    }

    public Moment() {
        super();
    }

    public Moment(final int year, final int month, final int dayOfMonth) {
        super(year, month, dayOfMonth);
    }

    public Moment(final int year, final int month, final int dayOfMonth, final int hourOfDay, final int minute, final int second) {
        super(year, month, dayOfMonth, hourOfDay, minute, second);
    }

    public Moment(final long date) {
        setTimeInMillis(date);
    }

    public Moment getBeginningOfDay() {
        return new Moment(get(Calendar.YEAR), get(Calendar.MONTH), get(Calendar.DAY_OF_MONTH), 0, 0, 0);
    }

    public int getDayOfMonth() {
        return get(Calendar.DAY_OF_MONTH);
    }

    public long getDelta(final Moment other, final int field) {
        final long from = getTimeInMillis();
        final long to = other.getTimeInMillis();
        final long delta = to - from;
        switch (field) {
            case Calendar.MINUTE:
                return delta / (60 * 1000);
            case Calendar.SECOND:
                return delta / 1000;
            case Calendar.MILLISECOND:
                return delta;
            default:
                return delta;
        }
    }

    public Moment getEndOfDay() {
        return new Moment(get(Calendar.YEAR), get(Calendar.MONTH), get(Calendar.DAY_OF_MONTH), 23, 59, 59);
    }

    public int getMonth() {
        return get(Calendar.MONTH);
    }

    public Moment getTimeMoment() {
        // TODO optimise this
        return Moment.fromIso(toIsoTime());
    }

    public int getYear() {
        return get(Calendar.YEAR);
    }

    public Moment getYMD() {
        return new Moment(get(Calendar.YEAR), get(Calendar.MONTH), get(Calendar.DAY_OF_YEAR));
    }

    public boolean isHoliday() {
        switch (get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SATURDAY:
            case Calendar.SUNDAY:
                return true;
            default:
                return false;
        }
    }

    public Moment newAdd(final int field, final int amount) {
        final Moment clone = new Moment(getTimeInMillis());
        clone.add(field, amount);
        return clone;
    }

    public String toCompactIsoDate() {
        return String.format("%04d%02d%02d", get(Calendar.YEAR), get(Calendar.MONTH) + 1, get(Calendar.DAY_OF_MONTH));
    }

    public String toCompactIsoDatetime() {
        return new StringBuilder(toCompactIsoDate()).append(toCompactIsoTime()).toString();
    }

    public String toCompactIsoTime() {
        return String.format("%02d%02d%02d", get(Calendar.HOUR_OF_DAY), get(Calendar.MINUTE), get(Calendar.SECOND));
    }

    public String toIsoDate() {
        return String.format("%04d-%02d-%02d", get(Calendar.YEAR), get(Calendar.MONTH) + 1, get(Calendar.DAY_OF_MONTH));
    }

    public String toIsoDatetime() {
        final StringBuilder sb = new StringBuilder(toIsoDate());
        sb.append(" ");
        sb.append(toIsoTime());
        return sb.toString();
    }

    public String toIsoTime() {
        return String.format("%02d:%02d:%02d", get(Calendar.HOUR_OF_DAY), get(Calendar.MINUTE), get(Calendar.SECOND));
    }

    @Override
    public String toString() {
        return toIsoDatetime();
    }

}
