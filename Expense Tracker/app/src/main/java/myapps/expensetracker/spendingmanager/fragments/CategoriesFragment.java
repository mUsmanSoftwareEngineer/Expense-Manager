package myapps.expensetracker.spendingmanager.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.activities.Add_Category;
import myapps.expensetracker.spendingmanager.activities.MainActivity;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.data.preference.AppPreference;
import myapps.expensetracker.spendingmanager.data.preference.PrefKey;
import myapps.expensetracker.spendingmanager.utilities.ActivityUtils;


public class CategoriesFragment extends Fragment {

    public static int current_cat_btn = 0;
    ImageView add_new, back;
    TextView iconTxt, doneTxt;
    private Activity mActivity;
    private Context mContext;
    private LinearLayout income_btn, expense_btn;

    private RelativeLayout relativeLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVar();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);

        initView(rootView);

        initFunctionality(rootView);
        initListener();

        return rootView;
    }




    private void initFunctionality(View rootView) {


        doneTxt.setVisibility(View.GONE);
        iconTxt.setVisibility(View.VISIBLE);
        add_new.setVisibility(View.VISIBLE);
        iconTxt.setText(R.string.category);


        loadFragment(new ExpenseCategories());


    }


    private void initVar() {

        mActivity = getActivity();
        mContext = mActivity.getApplicationContext();

    }

    private void initView(View rootView) {


        income_btn = rootView.findViewById(R.id.income_linear);
        expense_btn = rootView.findViewById(R.id.expense_linear);

        add_new = rootView.findViewById(R.id.add_new_cat);
        back = rootView.findViewById(R.id.back);

        iconTxt = rootView.findViewById(R.id.iconTxt);
        doneTxt = rootView.findViewById(R.id.done);

        relativeLayout=rootView.findViewById(R.id.mainRelative);

    }


    private void initListener() {


        Constants.adapterPosition = 0;

        income_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                income_btn.setBackgroundResource(R.drawable.bg_5);
                expense_btn.setBackgroundResource(0);
                current_cat_btn = 1;

                loadFragment(new IncomeCategories());

            }
        });

        expense_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                income_btn.setBackgroundResource(0);
                expense_btn.setBackgroundResource(R.drawable.bg_5);
                current_cat_btn = 0;

                loadFragment(new ExpenseCategories());

            }
        });

        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (CategoriesFragment.current_cat_btn == 0) {
                    ActivityUtils.getInstance().startActivity(mActivity, Add_Category.class, Constants.NEW_EXPENSE_CATEGORIES_FRAGMENT);
                } else if (CategoriesFragment.current_cat_btn == 1) {
                    ActivityUtils.getInstance().startActivity(mActivity, Add_Category.class, Constants.NEW_INCOME_CATEGORIES_FRAGMENT);
                }


            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, MainActivity.class));
                mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

//                mActivity.finish();
            }
        });

    }

    private void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }

}
