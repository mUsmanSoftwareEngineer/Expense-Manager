//package myapps.expensetracker.spendingmanager.database;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//
//
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//
//public class DatabaseHelper extends SQLiteOpenHelper {
//
//
//    public static int f1484a = 5;
//    public static String b = "FitDB";
//    public static String c = "exc_day";
//    public static String d = "day";
//    public static String e = "progress";
//    public static String f = "counter";
//    public static String g = "cycles";
//
//    public static String tbl_name = "workout_category";
//    public static String id = "exercise_id";
//    public static String workoutName = "workout_name";
//    public static String one = "exerciseOne";
//    public static String two  = "exerciseTwo";
//    public static String three = "exerciseThree";
//    public static String four = "exerciseFour";
//    public static String five = "exerciseFive";
//    public static String six = "exerciseSix";
//    public static String seven = "exerciseSeven";
//    public static String eight = "exerciseEight";
//    public static String nine = "exerciseNine";
//    public static String ten = "exerciseTen";
//    public static String eleven = "exerciseEleven";
//    public static String twelve = "exerciseTwelve";
//    public static String thirteen = "exerciseThirteen";
//    public static String fourteen = "exerciseFourteen";
//
//    public static String date="excDate";
//    public static String startTime="excStartTime";
//    public static String totalDuration="excDurationTime";
//    public static String durationInMinutes="excMinutes";
//    public static String durationInSeconds="excSeconds";
//    public static String totalExercise="totalExercise";
//    public static String progress="progress";
//
////    public static String tbl_name_weight = "weight";
//    public static String weightVal = "weight_val";
//    public static String tbl_name_one = "weight";
//    public static String weight_id = "weight_id";
//
//
//
//
//
//    public Context h;
//
//    public String j,z,u;
//
//    public DatabaseHelper(Context context) {
//        super(context, b, null, f1484a);
//        StringBuilder sb = new StringBuilder();
//        sb.append("CREATE TABLE ");
//        sb.append(c);
//        sb.append(" (");
//        sb.append(d);
//        sb.append(" TEXT, ");
//        sb.append(e);
//        sb.append(" REAL, ");
//        sb.append(f);
//        sb.append(" INTEGER, ");
//        sb.append(g);
//        sb.append(" TEXT)");
//        this.j = sb.toString();
//        StringBuilder ss = new StringBuilder();
//        ss.append("CREATE TABLE ");
//        ss.append(tbl_name);
//        ss.append(" (");
//        ss.append(id);
//        ss.append(" INTEGER PRIMARY KEY, ");
//        ss.append(workoutName);
//        ss.append(" TEXT, ");
//        ss.append(progress);
//        ss.append(" TEXT, ");
//        ss.append(one);
//        ss.append(" INTEGER, ");
//        ss.append(two);
//        ss.append(" INTEGER, ");
//        ss.append(three);
//        ss.append(" INTEGER, ");
//        ss.append(four);
//        ss.append(" INTEGER, ");
//        ss.append(five);
//        ss.append(" INTEGER, ");
//        ss.append(six);
//        ss.append(" INTEGER, ");
//        ss.append(seven);
//        ss.append(" INTEGER, ");
//        ss.append(eight);
//        ss.append(" INTEGER, ");
//        ss.append(nine);
//        ss.append(" INTEGER, ");
//        ss.append(ten);
//        ss.append(" INTEGER, ");
//        ss.append(eleven);
//        ss.append(" INTEGER, ");
//        ss.append(twelve);
//        ss.append(" INTEGER, ");
//        ss.append(thirteen);
//        ss.append(" INTEGER, ");
//        ss.append(fourteen);
//        ss.append(" INTEGER, ");
//        ss.append(date);
//        ss.append(" TEXT, ");
//        ss.append(startTime);
//        ss.append(" TEXT, ");
//        ss.append(totalDuration);
//        ss.append(" INTEGER, ");
//        ss.append(durationInMinutes);
//        ss.append(" INTEGER, ");
//        ss.append(durationInSeconds);
//        ss.append(" INTEGER, ");
//        ss.append(totalExercise);
//        ss.append(" INTEGER)");
//        this.z = ss.toString();
//        StringBuilder sm = new StringBuilder();
//        sm.append("CREATE TABLE ");
//        sm.append(tbl_name_one);
//        sm.append(" (");
//        sm.append(weight_id);
//        sm.append(" INTEGER PRIMARY KEY, ");
//        sm.append(date);
//        sm.append(" TEXT, ");
//        sm.append(weightVal);
//        sm.append(" REAL)");
//        this.u = sm.toString();
//        this.h = context;
//    }
//
//    public void onCreate(SQLiteDatabase sQLiteDatabase) {
//        try {
//            sQLiteDatabase.execSQL(this.j);
//            sQLiteDatabase.execSQL(this.z);
//            sQLiteDatabase.execSQL(this.u);
//        } catch (Exception e2) {
//            e2.printStackTrace();
//        }
//    }
//
//    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
//        String str = "TAG";
//        if (i2 == 2 || i2 == 3) {
//            StringBuilder sb = new StringBuilder();
//            sb.append("ALTER TABLE ");
//            sb.append(c);
//            sb.append(" ADD COLUMN ");
//            sb.append(g);
//            sb.append(" TEXT");
//            sQLiteDatabase.execSQL(sb.toString());
//            try {
//                JSONObject jSONObject = new JSONObject();
//                for (int i4 = 1; i4 <= 30; i4++) {
//                    int i5 = 0;
//                    for (int put : this.h.getResources().getIntArray(this.i[i4 - 1])) {
//                        try {
//                            jSONObject.put(String.valueOf(i5), put);
//                            i5++;
//                        } catch (JSONException e2) {
//                            e2.printStackTrace();
//                        }
//                    }
//                    StringBuilder sb2 = new StringBuilder();
//                    sb2.append("json str databs: ");
//                    sb2.append(jSONObject.toString());
//                    Log.e(str, sb2.toString());
//                    ContentValues contentValues = new ContentValues();
//                    contentValues.put(g, jSONObject.toString());
//                    if (sQLiteDatabase != null) {
//                        try {
//                            String str2 = c;
//                            StringBuilder sb3 = new StringBuilder();
//                            sb3.append(d);
//                            sb3.append("='Day ");
//                            sb3.append(i4);
//                            sb3.append("'");
//                            long update = (long) sQLiteDatabase.update(str2, contentValues, sb3.toString(), null);
//                            StringBuilder sb4 = new StringBuilder();
//                            sb4.append("res: ");
//                            sb4.append(update);
//                            Log.e(str, sb4.toString());
//                        } catch (Exception e3) {
//                            e3.printStackTrace();
//                        }
//                    }
//                }
//            } catch (Exception e4) {
//                e4.printStackTrace();
//            }
//            Log.e(str, "Case 3 db");
//        }
//
////        onCreate(sQLiteDatabase);
//    }
//
//
//}
