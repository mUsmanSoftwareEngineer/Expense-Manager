package myapps.expensetracker.spendingmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.data.preference.AppPreference;
import myapps.expensetracker.spendingmanager.data.preference.PrefKey;

public class SplashActivity extends AppCompatActivity {


    SharedPreferences prefs;
    Activity mActivity;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initVars();
        initFunctionality();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                startActivity(new Intent(SplashActivity.this,MainActivity.class));
//
//            }
//        },2000);

    }

    private void initVars() {
        mActivity = SplashActivity.this;
        mContext = mActivity.getApplicationContext();
    }

    private void initFunctionality() {

        boolean firstRun =  AppPreference.getInstance(mContext).getBoolean(PrefKey.ActivityFirstRun,true);

//        Toast.makeText(mActivity,firstRun+ "", Toast.LENGTH_SHORT).show();
        if(firstRun){
            try {

//                Thread t = new Thread(){
//                    public void run(){
//
//
//                    }
//                };
//                t.start();
//                AppPreference.getInstance(mContext).setBoolean(PrefKey.ActivityFirstRun,false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this, SlideActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.enter, R.anim.exit);
                        finish();
                    }
                },2000);



            } catch (Exception e) {
                Log.d("check7247", e.getMessage());
            }
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
            },2000);
        }



    }
}