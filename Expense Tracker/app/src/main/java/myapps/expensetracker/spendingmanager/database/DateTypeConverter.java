package myapps.expensetracker.spendingmanager.database;

import android.text.format.DateFormat;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import myapps.expensetracker.spendingmanager.data.constant.Constants;

public class DateTypeConverter {

//    static SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
//        @TypeConverter
//        public static Date fromString(String value) {
//            if (value != null) {
//                try {
//                    return df.parse(value);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            } else {
//                return null;
//            }
//        }
@TypeConverter
public static Date fromTimestamp(Long value) {
    return value == null ? null : new Date(value);
}

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    public static Date fromString(String tmp) {
    return null;
    }
}
