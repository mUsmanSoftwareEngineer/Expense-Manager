package myapps.expensetracker.spendingmanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.data.preference.PrefKey;
import myapps.expensetracker.spendingmanager.fragments.CategoriesFragment;
import myapps.expensetracker.spendingmanager.fragments.ExpenseCategories;
//import myapps.expensetracker.spendingmanager.fragments.HistoryFragment;
import myapps.expensetracker.spendingmanager.fragments.IncomeCategories;
import myapps.expensetracker.spendingmanager.utilities.ActivityUtils;

public class SelectCategory extends AppCompatActivity {

    ImageView back,add_new_cat;
//    close,
    TextView done,toolbarText;

    private Activity mActivity;
    private Context mContext;

    private int cat_btn = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);

        initVars();
        initViews();
        initFunctionality();
        initListeners();
    }






    private void initVars() {

        mActivity = this;
        mContext = mActivity.getApplicationContext();

    }

    private void initViews() {

        //ImageView
        add_new_cat=(ImageView) findViewById(R.id.add_new_cat) ;
        back= (ImageView) findViewById(R.id.back);

        //TextView
        done=(TextView) findViewById(R.id.done);
        toolbarText=(TextView) findViewById(R.id.iconTxt);


    }

    private void initFunctionality() {

        done.setVisibility(View.GONE);
        toolbarText.setVisibility(View.VISIBLE);
        toolbarText.setText(R.string.Select_category);
        add_new_cat.setVisibility(View.VISIBLE);


        Constants.adapterPosition=1;

        Bundle b = getIntent().getExtras();

        if (b != null) {

            cat_btn = b.getInt(PrefKey.Selected_category);

        }

        if(cat_btn==0){

           loadFragment(new ExpenseCategories());

        }
        else if(cat_btn==1){

            loadFragment(new IncomeCategories());
        }

    }


    private void initListeners() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mActivity, AddIncomeExpense.class);
//                startActivity(intent);
//                mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                Constants.adapterPosition=0;
                finish();
            }
        });

        add_new_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cat_btn==0){
                    ActivityUtils.getInstance().startActivity(mActivity, Add_Category.class, Constants.NEW_EXPENSE_CATEGORIES_FRAGMENT);
                }
                else if(cat_btn==1){
                    ActivityUtils.getInstance().startActivity(mActivity, Add_Category.class, Constants.NEW_INCOME_CATEGORIES_FRAGMENT);
                }
//                Toast.makeText(mActivity, "add new from here", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Constants.adapterPosition=0;
        finish();
    }

    private void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout1, fragment);
        fragmentTransaction.commit(); // save the changes
    }
}