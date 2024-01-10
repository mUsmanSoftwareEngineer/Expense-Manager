package myapps.expensetracker.spendingmanager.data.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import myapps.expensetracker.spendingmanager.data.models.CategoryModel;


/**
 * Created by Bezruk on 16/10/18.
 */
public class AppPreference {

    // declare context
    private static Context mContext;

    // singleton
    private static AppPreference appPreference = null;

    // common
    private SharedPreferences sharedPreferences, settingsPreferences;
    private SharedPreferences.Editor editor;

    public static AppPreference getInstance(Context context) {
        if(appPreference == null) {
            mContext = context;
            appPreference = new AppPreference();
        }
        return appPreference;
    }
    private AppPreference() {
        sharedPreferences = mContext.getSharedPreferences(PrefKey.APP_PREF_NAME, Context.MODE_PRIVATE);
        settingsPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        editor = sharedPreferences.edit();
    }

    public void setString(String key, String value) {
        editor.putString(key , value);
        editor.commit();
    }
    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }
    public Boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void setInteger(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public void setFloat(String key, Float value) {

        editor.putFloat(key,value);
        editor.commit();
    }

    public float getFloat(String key) {
        return sharedPreferences.getFloat(key, 0);
    }

    public int getInteger(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public void saveModelClass(String key, ArrayList<CategoryModel> values) {
        if (values != null && !values.isEmpty()) {
            Gson gson = new Gson();
            String json = gson.toJson(values);

            setString(key, json);
        } else if(values == null) {
            setString(key, null);
        }
    }

    public ArrayList<CategoryModel> getModelClass(String key){
        ArrayList<CategoryModel> categoryModelArrayList=new ArrayList<>();
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, null);
        Type type = new TypeToken<ArrayList<CategoryModel>>() {}.getType();
        categoryModelArrayList = gson.fromJson(json, type);

        if (categoryModelArrayList == null) {
            categoryModelArrayList = new ArrayList<>();
        }
        return categoryModelArrayList;
    }


    public ArrayList<String> getStringArray(String key) {
        ArrayList<String> arrayList = new ArrayList<>();
        String value = getString(key);
        if (value != null) {
            arrayList = new ArrayList<>(Arrays.asList(value.split("‼")));
        }
        return arrayList;
    }


}
