package myapps.expensetracker.spendingmanager.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.adapters.MainPagerAdapter;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.data.preference.AppPreference;
import myapps.expensetracker.spendingmanager.data.preference.PrefKey;
import myapps.expensetracker.spendingmanager.database.AccountRepository;
import myapps.expensetracker.spendingmanager.entity.Accounts;
import myapps.expensetracker.spendingmanager.fragments.AccountFragment;
import myapps.expensetracker.spendingmanager.fragments.CategoriesFragment;
import myapps.expensetracker.spendingmanager.fragments.HistoryFragment;
import myapps.expensetracker.spendingmanager.fragments.SpendingFragment;
import myapps.expensetracker.spendingmanager.fragments.StatsFragment;
import myapps.expensetracker.spendingmanager.libs.BitmapManager;
import myapps.expensetracker.spendingmanager.utilities.ActivityUtils;
import myapps.expensetracker.spendingmanager.utilities.AlarmReceiver;
import myapps.expensetracker.spendingmanager.viewmodel.AccountViewModel;

import static myapps.expensetracker.spendingmanager.fragments.CategoriesFragment.current_cat_btn;


public class MainActivity extends AppCompatActivity {


    private Activity mActivity;
    private Context mContext;

    private ViewPager mViewPager;
    private ArrayList<String> mFragmentItems;

    private BottomNavigationView bottomNavigationView;

    RelativeLayout add_new_item;


    MainPagerAdapter mainPagerAdapter;

    private AccountViewModel mAccountViewModel;

    int checkAccounts;

    private AccountRepository mRepository;
    private List<Accounts> mAllAccounts=null;

    SharedPreferences prefs;
    boolean checkDarkMode;
    private int selected_item = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initVars();
        initViews();
        initFunctionality();
        initListeners();
    }

    private void initVars() {
        mActivity = MainActivity.this;
        mContext = mActivity.getApplicationContext();
        mFragmentItems = new ArrayList<>();
//        AdManager.getInstance(mContext).showBannerAd((AdView)findViewById(R.id.adViewMain));
        prefs = mContext.getSharedPreferences(PrefKey.APP_PREF_NAME, Context.MODE_PRIVATE);
        mAccountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
    }

    private void initViews() {
        setContentView(R.layout.activity_main);

//        mViewPager = (ViewPager) findViewById(R.id.viewpager);
//        mViewPager.setOffscreenPageLimit(5);


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

    }







    private void initFunctionality() {



        Bundle b = getIntent().getExtras();

        if(prefs.contains(PrefKey.enableDarkMode)){
           Constants.checkDarkMode=AppPreference.getInstance(mContext).getBoolean(PrefKey.enableDarkMode,false);
        }

        if(Constants.checkDarkMode){
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
        }
        else{
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
        }

        insertIntoAccounts();


        if(b!=null){
            selected_item = b.getInt(PrefKey.Selected_item);

        }

        if(selected_item==4){
            bottomNavigationView.setSelectedItemId(R.id.nav_account);
            loadFragment(new AccountFragment());
        }
        else {
            loadFragment(new SpendingFragment());
        }




//        Toast.makeText(mActivity, AppPreference.getInstance(.)"", Toast.LENGTH_SHORT).show();

//        setUpViewPager();

        // TODO Sample banner Ad implementation

    }


    private void insertIntoAccounts() {

        boolean firstRun = AppPreference.getInstance(mContext).getBoolean(PrefKey.Account_First_RUN,true);

        if (firstRun){

            Drawable d = getResources().getDrawable(R.drawable.account);
            Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
            byte[] image= BitmapManager.bitmapToByte(bitmap);
            Accounts accounts=new Accounts(getResources().getString(R.string.personal),image);
            mAccountViewModel.insert(accounts);
            AppPreference.getInstance(mContext).setBoolean(PrefKey.Account_First_RUN,false);
        }

    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    private void initListeners() {


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_spending:
                        loadFragment(new SpendingFragment());
//                        mViewPager.setCurrentItem(0, true);
                        break;
                    case R.id.nav_history:
                        loadFragment(new HistoryFragment());
//                        mViewPager.setCurrentItem(1, true);
                        break;
                    case R.id.nav_category:
                        loadFragment(new CategoriesFragment());
//                        mViewPager.setCurrentItem(2, true);
                        break;
                    case R.id.nav_graph:
                        loadFragment(new StatsFragment());
//                        mViewPager.setCurrentItem(3, true);
                        break;
                    case R.id.nav_account:
                        loadFragment(new AccountFragment());
//                        mViewPager.setCurrentItem(4, true);
                        break;
                }
                return true;
            }
        });




//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                if(position == 0) {
//                    bottomNavigationView.setSelectedItemId(R.id.nav_spending);
//                } else if(position == 1) {
//                    bottomNavigationView.setSelectedItemId(R.id.nav_history);
//                }
//                else if(position == 2){
//                    bottomNavigationView.setSelectedItemId(R.id.nav_category);
//                }
//                else if(position == 3) {
//                    bottomNavigationView.setSelectedItemId(R.id.nav_graph);
//                }
//                else if(position == 4) {
//                    bottomNavigationView.setSelectedItemId(R.id.nav_account);
//                }
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

    }



    public void setUpViewPager() {

        mFragmentItems.add(getString(R.string.menu_spending));
        mFragmentItems.add(getString(R.string.menu_category));
        mFragmentItems.add(getString(R.string.menu_history));
        mFragmentItems.add(getString(R.string.graph_stats));
        mFragmentItems.add(getString(R.string.menu_accounts));

        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), mFragmentItems);

        mViewPager.setAdapter(mainPagerAdapter);
        mViewPager.setEnabled(false);
        mainPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {

        Constants.resume=false;
        finishAffinity();

    }


}