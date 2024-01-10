package myapps.expensetracker.spendingmanager.app;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static App instance;

    private static Context sContext;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }
}
