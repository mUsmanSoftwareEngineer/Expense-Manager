//package myapps.expensetracker.spendingmanager.database;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.util.Log;
//
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DatabaseOperations {
//
//
//    public Context context;
//
//
//    public DatabaseHelper dbHelper;
//    public SQLiteDatabase sqlite;
//
//    public DatabaseOperations(Context context) {
//        this.dbHelper = new DatabaseHelper(context);
//        this.context = context;
//    }
//
//    public int CheckDBEmpty() {
//        this.sqlite = this.dbHelper.getReadableDatabase();
//        StringBuilder sb = new StringBuilder();
//        sb.append("SELECT count(*) FROM ");
//        sb.append(DatabaseHelper.c);
//        Cursor rawQuery = this.sqlite.rawQuery(sb.toString(), null);
//        rawQuery.moveToFirst();
//        return rawQuery.getInt(0) > 0 ? 1 : 0;
//    }
//
//    public int deleteTable() {
//        this.sqlite = this.dbHelper.getWritableDatabase();
//        int delete = this.sqlite.delete(DatabaseHelper.c, null, null);
//        this.sqlite.close();
//        return delete;
//    }
//
////    public List<WorkoutData> getAllDaysProgress() {
////        ArrayList arrayList = new ArrayList();
////        this.sqlite = this.dbHelper.getReadableDatabase();
////        try {
////            if (this.sqlite != null) {
////                SQLiteDatabase sQLiteDatabase = this.sqlite;
////                StringBuilder sb = new StringBuilder();
////                sb.append("select * from ");
////                sb.append(DatabaseHelper.c);
////                Cursor rawQuery = sQLiteDatabase.rawQuery(sb.toString(), null);
////                if (rawQuery.moveToFirst()) {
////                    while (!rawQuery.isAfterLast()) {
////                        WorkoutData workoutData = new WorkoutData();
////                        workoutData.setDay(rawQuery.getString(rawQuery.getColumnIndex(DatabaseHelper.d)));
////                        workoutData.setProgress(rawQuery.getFloat(rawQuery.getColumnIndex(DatabaseHelper.e)));
////                        rawQuery.moveToNext();
////                        arrayList.add(workoutData);
////                    }
////                }
////                rawQuery.close();
////                this.sqlite.close();
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        return arrayList;
////    }
////
////    public List<ExerciseModelList> getWorkOutList(String date) {
////        ArrayList arrayList = new ArrayList();
////        this.sqlite = this.dbHelper.getReadableDatabase();
////        try {
////            if (this.sqlite != null) {
////                SQLiteDatabase sQLiteDatabase = this.sqlite;
////                StringBuilder sb = new StringBuilder();
////                sb.append("select * from ");
////                sb.append(DatabaseHelper.tbl_name);
////                sb.append(" where ");
////                sb.append(DatabaseHelper.date);
////                sb.append(" = '");
////                sb.append(date);
////                sb.append("'");
////
////                Cursor rawQuery = sQLiteDatabase.rawQuery(sb.toString(), null);
////                if (rawQuery.moveToFirst()) {
////                    while (!rawQuery.isAfterLast()) {
////                        ExerciseModelList exerciseData = new ExerciseModelList();
////                        exerciseData.setWorkoutName(rawQuery.getString(rawQuery.getColumnIndex(DatabaseHelper.workoutName)));
////                        exerciseData.setExercise_min(rawQuery.getInt(rawQuery.getColumnIndex(DatabaseHelper.durationInMinutes)));
////                        exerciseData.setExercise_one(rawQuery.getInt(rawQuery.getColumnIndex(DatabaseHelper.one)));
////                        exerciseData.setExercise_two(rawQuery.getInt(rawQuery.getColumnIndex(DatabaseHelper.two)));
////                        exerciseData.setExercise_three(rawQuery.getInt(rawQuery.getColumnIndex(DatabaseHelper.three)));
////                        exerciseData.setExercise_four(rawQuery.getInt(rawQuery.getColumnIndex(DatabaseHelper.four)));
////                        exerciseData.setExercise_five(rawQuery.getInt(rawQuery.getColumnIndex(DatabaseHelper.five)));
////                        exerciseData.setExercise_six(rawQuery.getInt(rawQuery.getColumnIndex(DatabaseHelper.six)));
////                        exerciseData.setExercise_seven(rawQuery.getInt(rawQuery.getColumnIndex(DatabaseHelper.seven)));
////                        exerciseData.setExercise_eight(rawQuery.getInt(rawQuery.getColumnIndex(DatabaseHelper.eight)));
////                        exerciseData.setExercise_nine(rawQuery.getInt(rawQuery.getColumnIndex(DatabaseHelper.nine)));
////                        exerciseData.setExercise_ten(rawQuery.getInt(rawQuery.getColumnIndex(DatabaseHelper.ten)));
////                        exerciseData.setExercise_eleven(rawQuery.getInt(rawQuery.getColumnIndex(DatabaseHelper.eleven)));
////                        exerciseData.setExercise_twelve(rawQuery.getInt(rawQuery.getColumnIndex(DatabaseHelper.twelve)));
////                        exerciseData.setExercise_thirteen(rawQuery.getInt(rawQuery.getColumnIndex(DatabaseHelper.thirteen)));
////                        exerciseData.setExercise_fourteen(rawQuery.getInt(rawQuery.getColumnIndex(DatabaseHelper.fourteen)));
////                        exerciseData.setExercise_start_time(rawQuery.getString(rawQuery.getColumnIndex(DatabaseHelper.startTime)));
////                        exerciseData.setProgress(rawQuery.getString(rawQuery.getColumnIndex(DatabaseHelper.progress)));
////                        rawQuery.moveToNext();
////                        arrayList.add(exerciseData);
////                    }
////                }
////                rawQuery.close();
////                this.sqlite.close();
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        return arrayList;
////    }
////
////    public List<ConstantClass> getWeight() {
////        ArrayList arrayList1 = new ArrayList();
//////        ConstantClass constantClass = new ConstantClass();
//////        List<ConstantClass> constantClass=new ArrayList<>();
////        this.sqlite = this.dbHelper.getReadableDatabase();
////        try {
////            if (this.sqlite != null) {
////                SQLiteDatabase sQLiteDatabase = this.sqlite;
////                StringBuilder sb = new StringBuilder();
////                sb.append("select * from ");
////                sb.append(DatabaseHelper.tbl_name_one);
//////                sb.append(" where ");
//////                sb.append(DatabaseHelper.date);
//////                sb.append(" = '");
//////                sb.append(date);
//////                sb.append("'");
////
////                Cursor rawQuery = sQLiteDatabase.rawQuery(sb.toString(), null);
////
////                if (rawQuery.moveToFirst()) {
////
////                    while (!rawQuery.isAfterLast()) {
//////                        Log.d("ConstantClassData",i +"");
////                      ConstantClass constantClass=new ConstantClass();
//////                        constantClass.add(new ConstantClass(rawQuery.getString(rawQuery.getColumnIndex(DatabaseHelper.date)),rawQuery.getFloat(rawQuery.getColumnIndex(DatabaseHelper.weightVal))));
////                        constantClass.setDate(rawQuery.getString(rawQuery.getColumnIndex(DatabaseHelper.date)));
////                        constantClass.setWeight(rawQuery.getFloat(rawQuery.getColumnIndex(DatabaseHelper.weightVal)));
////
////                        arrayList1.add(constantClass);
////                        rawQuery.moveToNext();
////
////                    }
////
////                }
////                rawQuery.close();
////                this.sqlite.close();
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        return arrayList1;
////    }
////
////    public ArrayList<ConstantClass> getWeightData() {
////        ArrayList<ConstantClass> arrayList = new ArrayList<>();
////
////        // select all query
////        String select_query= "SELECT *FROM " + DatabaseHelper.tbl_name_one;
////
////        this.sqlite = this.dbHelper.getReadableDatabase();
////        Cursor cursor = sqlite.rawQuery(select_query, null);
////
////        // looping through all rows and adding to list
////        if (cursor.moveToFirst()) {
////            do {
////                ConstantClass constantClass = new ConstantClass();
////                constantClass.setDate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.date)));
////                constantClass.setWeight(cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.weightVal)));
////                arrayList.add(constantClass);
////            }while (cursor.moveToNext());
////        }
////        return arrayList;
////    }
//
//
//
//    public List<Integer> getWorkOutDuration(String date) {
//        List<Integer> arrayList = new ArrayList<Integer>();
//        this.sqlite = this.dbHelper.getReadableDatabase();
//        try {
//            if (this.sqlite != null) {
//                SQLiteDatabase sQLiteDatabase = this.sqlite;
//                StringBuilder sb = new StringBuilder();
//                sb.append("select * from ");
//                sb.append(DatabaseHelper.tbl_name);
//                sb.append(" where ");
//                sb.append(DatabaseHelper.date);
//                sb.append(" = '");
//                sb.append(date);
//                sb.append("'");
//
//                Cursor rawQuery = sQLiteDatabase.rawQuery(sb.toString(), null);
//                if (rawQuery.moveToFirst()) {
//                    while (!rawQuery.isAfterLast()) {
////
//                        arrayList.add(rawQuery.getInt(rawQuery.getColumnIndex(DatabaseHelper.totalDuration)));
//                        rawQuery.moveToNext();
//                    }
//                }
//                rawQuery.close();
//                this.sqlite.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return arrayList;
//    }
//
//    public boolean checkIfSingleRecordExist(String date)
//    {
//        try
//        {
//            this.sqlite = this.dbHelper.getReadableDatabase();
//            StringBuilder sb = new StringBuilder();
//            sb.append("select * from ");
//            sb.append(DatabaseHelper.tbl_name_one);
//            sb.append(" where ");
//            sb.append(DatabaseHelper.date);
//            sb.append(" = '");
//            sb.append(date);
//            sb.append("'");
//            Cursor cursor=this.sqlite.rawQuery(sb.toString(), null);
//            if (cursor.moveToFirst())
//            {
//                this.sqlite.close();
//                Log.d("Record  Already Exists", "Record Exist");
//                return true;//record Exists
//
//            }
//            Log.d("Record  Not Exists", "Record Not Exist");
//            this.sqlite.close();
//        }
//        catch(Exception errorException)
//        {
//            Log.d("Exception occured", "Exception occured "+errorException);
//            // db.close();
//        }
//        return false;
//    }
//
//    public float getWeightVal(String date)
//    {
//
//        Float val=0.0f;
//        try
//        {
//            this.sqlite = this.dbHelper.getReadableDatabase();
//            StringBuilder sb = new StringBuilder();
//            sb.append("select * from ");
//            sb.append(DatabaseHelper.tbl_name_one);
//            sb.append(" where ");
//            sb.append(DatabaseHelper.date);
//            sb.append(" = '");
//            sb.append(date);
//            sb.append("'");
//            Cursor cursor=this.sqlite.rawQuery(sb.toString(), null);
//            if (cursor.moveToFirst())
//            {
//                val=cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.weightVal));
//            }
//            Log.d("Record  Not Exists", "Record Not Exist");
//            this.sqlite.close();
//        }
//        catch(Exception errorException)
//        {
//            Log.d("Exception occured", "Exception occured "+errorException);
//            // db.close();
//        }
//        return val;
//    }
//
//
//
//    public boolean checkIfRecordExists(String date) {
//        this.sqlite = this.dbHelper.getReadableDatabase();
//        try {
//            if (this.sqlite != null) {
//                SQLiteDatabase sQLiteDatabase = this.sqlite;
//                StringBuilder sb = new StringBuilder();
//                sb.append("select * from ");
//                sb.append(DatabaseHelper.tbl_name);
//                sb.append(" where ");
//                sb.append(DatabaseHelper.date);
//                sb.append(" = '");
//                sb.append(date);
//                sb.append("'");
//
//                Cursor rawQuery = sQLiteDatabase.rawQuery(sb.toString(), null);
//                if (rawQuery.getCount()<=0) {
//                    rawQuery.close();
//                    this.sqlite.close();
//                    return false;
//                }
//                rawQuery.close();
//                this.sqlite.close();
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
//
//    public Float getMax() {// use the data type of the column
//        this.sqlite = this.dbHelper.getReadableDatabase();
//        Cursor cursor = this.sqlite.query(DatabaseHelper.tbl_name_one, new String[]{"MAX(" +DatabaseHelper.weightVal + ") AS MAX"}, null, null, null, null, null);
//        cursor.moveToFirst(); // to move the cursor to first record
//        int index = cursor.getColumnIndex("MAX");
//        Float data = cursor.getFloat(index);// use the data type of the column or use String itself you can parse it
//        this.sqlite.close();
//        return data;
//    }
//
//    public Float getMin() {// use the data type of the column
//        this.sqlite = this.dbHelper.getReadableDatabase();
//        Cursor cursor = this.sqlite.query(DatabaseHelper.tbl_name_one, new String[]{"MIN(" +DatabaseHelper.weightVal + ") AS MIN"}, null, null, null, null, null);
//        cursor.moveToFirst(); // to move the cursor to first record
//        int index = cursor.getColumnIndex("MIN");
//        Float data = cursor.getFloat(index);// use the data type of the column or use String itself you can parse it
//        this.sqlite.close();
//        return data;
//    }
//
////    public ArrayList<ExerciseModelList> readExercises() {
////        // on below line we are creating a
////        // database for reading our database.
////        this.sqlite = this.dbHelper.getReadableDatabase();
////
////        // on below line we are creating a cursor with query to read data from database.
////        Cursor cursorCourses = sqlite.rawQuery("SELECT * FROM " + DatabaseHelper.tbl_name, null);
////
////        // on below line we are creating a new array list.
////        ArrayList<ExerciseModelList> courseModalArrayList = new ArrayList<>();
////
////        // moving our cursor to first position.
////        if (cursorCourses.moveToFirst()) {
////            do {
////                // on below line we are adding the data from cursor to our array list.
//////                courseModalArrayList.add(new ExerciseModelList(cursorCourses.getString(1),
//////                        cursorCourses.getString(4),
//////                        cursorCourses.getString(2),
//////                        cursorCourses.getString(3)));
////
//////                courseModalArrayList.add(new ExerciseModelList(cursorCourses.getInt(0),
//////                        cursorCourses.getInt(1),
//////                        cursorCourses.getInt(2),
//////                        cursorCourses.getInt(3),
//////                        cursorCourses.getInt(4),
//////                        cursorCourses.getInt(6)));
////
////            } while (cursorCourses.moveToNext());
////            // moving our cursor to next.
////        }
////        // at last closing our cursor
////        // and returning our array list.
////        cursorCourses.close();
////        return courseModalArrayList;
////    }
//
//
//
//    public int getDayExcCounter(String str) {
//        this.sqlite = this.dbHelper.getReadableDatabase();
//        SQLiteDatabase sQLiteDatabase = this.sqlite;
//        int i = 0;
//        if (sQLiteDatabase != null) {
//            StringBuilder sb = new StringBuilder();
//            sb.append("select * from ");
//            sb.append(DatabaseHelper.c);
//            sb.append(" where ");
//            sb.append(DatabaseHelper.d);
//            sb.append(" = '");
//            sb.append(str);
//            sb.append("'");
//            Cursor rawQuery = sQLiteDatabase.rawQuery(sb.toString(), null);
//            if (rawQuery.moveToFirst()) {
//                i = rawQuery.getInt(rawQuery.getColumnIndex(DatabaseHelper.f));
//            }
//            rawQuery.close();
//            this.sqlite.close();
//        }
//        return i;
//    }
//
//    public String getDayExcCycles(String str) {
//        this.sqlite = this.dbHelper.getReadableDatabase();
//        SQLiteDatabase sQLiteDatabase = this.sqlite;
//        String str2 = "";
//        if (sQLiteDatabase != null) {
//            StringBuilder sb = new StringBuilder();
//            sb.append("select * from ");
//            sb.append(DatabaseHelper.c);
//            sb.append(" where ");
//            sb.append(DatabaseHelper.d);
//            sb.append(" = '");
//            sb.append(str);
//            sb.append("'");
//            Cursor rawQuery = sQLiteDatabase.rawQuery(sb.toString(), null);
//            if (rawQuery.moveToFirst()) {
//                str2 = rawQuery.getString(rawQuery.getColumnIndex(DatabaseHelper.g));
//            }
//            this.sqlite.close();
//        }
//        return str2;
//    }
//
//
//
//    public float getExcDayProgress(String str) {
//        this.sqlite = this.dbHelper.getReadableDatabase();
//        SQLiteDatabase sQLiteDatabase = this.sqlite;
//        float f = 0.0f;
//        if (sQLiteDatabase != null) {
//            StringBuilder sb = new StringBuilder();
//            sb.append("select * from ");
//            sb.append(DatabaseHelper.c);
//            sb.append(" where ");
//            sb.append(DatabaseHelper.d);
//            sb.append(" = '");
//            sb.append(str);
//            sb.append("'");
//            Cursor rawQuery = sQLiteDatabase.rawQuery(sb.toString(), null);
//            if (rawQuery.moveToFirst()) {
//                f = rawQuery.getFloat(rawQuery.getColumnIndex(DatabaseHelper.e));
//            }
//            rawQuery.close();
//            this.sqlite.close();
//        }
//        return f;
//    }
//
////    public long insertExcALLDayData() {
////        long j = 0;
////        try {
////            this.sqlite = this.dbHelper.getWritableDatabase();
////            for (int i = 1; i <= 30; i++) {
////                JSONObject jSONObject = new JSONObject();
////                this.b = this.context.getResources().getIntArray(this.c[i - 1]);
////                int i2 = 0;
////                for (int put : this.b) {
////                    try {
////                        jSONObject.put(String.valueOf(i2), put);
////                        i2++;
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////                }
////                StringBuilder sb = new StringBuilder();
////                sb.append("json str databs: ");
////                sb.append(jSONObject.toString());
////                Log.e("TAG", sb.toString());
////                ContentValues contentValues = new ContentValues();
////                String str = DatabaseHelper.d;
////                StringBuilder sb2 = new StringBuilder();
////                sb2.append("Day ");
////                sb2.append(i);
////                contentValues.put(str, sb2.toString());
////                contentValues.put(DatabaseHelper.e, Double.valueOf(0.0d));
////                contentValues.put(DatabaseHelper.f, Integer.valueOf(0));
////                contentValues.put(DatabaseHelper.g, jSONObject.toString());
////                if (this.sqlite != null) {
////                    try {
////                        j = this.sqlite.insert(DatabaseHelper.c, null, contentValues);
////                    } catch (Exception e2) {
////                        e2.printStackTrace();
////                    }
////                }
////            }
////            this.sqlite.close();
////        } catch (Exception e3) {
////            e3.printStackTrace();
////            this.sqlite.close();
////        }
////        return j;
////    }
//
////    public int insertExcCounter(String str, int i) {
////        String str2 = "'";
////        int i2 = 0;
////        try {
////            this.sqlite = this.dbHelper.getWritableDatabase();
////            ContentValues contentValues = new ContentValues();
////            contentValues.put(DatabaseHelper.f, Integer.valueOf(i));
////            if (this.sqlite != null) {
////                try {
////                    StringBuilder sb = new StringBuilder();
////                    sb.append("UPDATE ");
////                    sb.append(DatabaseHelper.c);
////                    sb.append(" SET ");
////                    sb.append(DatabaseHelper.f);
////                    sb.append(" = ");
////                    sb.append(i);
////                    sb.append(" WHERE ");
////                    sb.append(DatabaseHelper.d);
////                    sb.append(" = '");
////                    sb.append(str);
////                    sb.append(str2);
////                    sb.toString();
////                    SQLiteDatabase sQLiteDatabase = this.sqlite;
////                    String str3 = DatabaseHelper.c;
////                    StringBuilder sb2 = new StringBuilder();
////                    sb2.append(DatabaseHelper.d);
////                    sb2.append("='");
////                    sb2.append(str);
////                    sb2.append(str2);
////                    i2 = sQLiteDatabase.update(str3, contentValues, sb2.toString(), null);
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////            }
////            this.sqlite.close();
////        } catch (Exception e2) {
////            e2.printStackTrace();
////            this.sqlite.close();
////        }
////        return i2;
////    }
//
////    public int insertExcCycles(String str, String str2) {
////        String str3 = "'";
////        int i = 0;
////        try {
////            this.sqlite = this.dbHelper.getWritableDatabase();
////            ContentValues contentValues = new ContentValues();
////            contentValues.put(DatabaseHelper.g, str2);
////            if (this.sqlite != null) {
////                try {
////                    StringBuilder sb = new StringBuilder();
////                    sb.append("UPDATE ");
////                    sb.append(DatabaseHelper.c);
////                    sb.append(" SET ");
////                    sb.append(DatabaseHelper.g);
////                    sb.append(" = ");
////                    sb.append(str2);
////                    sb.append(" WHERE ");
////                    sb.append(DatabaseHelper.d);
////                    sb.append(" = '");
////                    sb.append(str);
////                    sb.append(str3);
////                    sb.toString();
////                    SQLiteDatabase sQLiteDatabase = this.sqlite;
////                    String str4 = DatabaseHelper.c;
////                    StringBuilder sb2 = new StringBuilder();
////                    sb2.append(DatabaseHelper.d);
////                    sb2.append("='");
////                    sb2.append(str);
////                    sb2.append(str3);
////                    i = sQLiteDatabase.update(str4, contentValues, sb2.toString(), null);
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////            }
////            this.sqlite.close();
////        } catch (Exception e2) {
////            e2.printStackTrace();
////            this.sqlite.close();
////        }
////        return i;
////    }
//
////    public int insertExcDayData(String str, float f) {
////        int i = 0;
////        try {
////            this.sqlite = this.dbHelper.getWritableDatabase();
////            ContentValues contentValues = new ContentValues();
////            contentValues.put(DatabaseHelper.e, Float.valueOf(f));
////            if (this.sqlite != null) {
////                try {
////                    SQLiteDatabase sQLiteDatabase = this.sqlite;
////                    String str2 = DatabaseHelper.c;
////                    StringBuilder sb = new StringBuilder();
////                    sb.append(DatabaseHelper.d);
////                    sb.append("='");
////                    sb.append(str);
////                    sb.append("'");
////                    i = sQLiteDatabase.update(str2, contentValues, sb.toString(), null);
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////            }
////            this.sqlite.close();
////        } catch (Exception e2) {
////            e2.printStackTrace();
////            this.sqlite.close();
////        }
////        return i;
////    }
//
//
//
////    public boolean tableExists(String str) {
////        SQLiteDatabase wdritableDatabase = this.dbHelper.getWritableDatabase();
////        boolean z = false;
////        if (!(str == null || writableDatabase == null || !writableDatabase.isOpen())) {
////            Cursor rawQuery = writableDatabase.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[]{"table", str});
////            if (!rawQuery.moveToFirst()) {
////                rawQuery.close();
////                return false;
////            }
////            int i = rawQuery.getInt(0);
////            rawQuery.close();
////            if (i > 0) {
////                z = true;
////            }
////        }
////        return z;
////    }
//}
