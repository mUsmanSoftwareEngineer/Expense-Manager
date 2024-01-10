package myapps.expensetracker.spendingmanager.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;

import java.util.Date;

import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.activities.AddIncomeExpense;
import myapps.expensetracker.spendingmanager.data.preference.PrefKey;
import myapps.expensetracker.spendingmanager.entity.Accounts;


/**
 * Created by Bezruk on 16/10/18.
 */
public class ActivityUtils {

    private static ActivityUtils sActivityUtils = null;

    public static ActivityUtils getInstance() {
        if (sActivityUtils == null) {
            sActivityUtils = new ActivityUtils();
        }
        return sActivityUtils;
    }

    public void invokeActivity(Activity activity, Class<?> tClass,   int selectedItem) {
        Intent intent = new Intent(activity, tClass);
        Bundle b = new Bundle();

        b.putInt(PrefKey.Selected_item, selectedItem);
        intent.putExtras(b);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

    public void invokeActivity(Activity activity, Class<?> tClass, int fragmentPosition , int selectedPosition,String value) {
        Intent intent = new Intent(activity, tClass);
        Bundle b = new Bundle();

        b.putInt(PrefKey.Selected_category, selectedPosition);
        b.putString(PrefKey.Selected_date, value);
        b.putInt(PrefKey.Spending_Position,fragmentPosition);
        intent.putExtras(b);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

    public void invokeActivity(Activity activity, Class<?> tClass, int fragmentPosition , int selectedPosition,String value,int accountId,String accountName) {
        Intent intent = new Intent(activity, tClass);

        Bundle b = new Bundle();

        b.putInt(PrefKey.Selected_category, selectedPosition);
        b.putString(PrefKey.Selected_date, value);
        b.putInt(PrefKey.Spending_Position,fragmentPosition);
        b.putInt(PrefKey.Transaction_account_id,accountId);
        b.putString(PrefKey.Transaction_account_name, accountName);
        intent.putExtras(b);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }
    public void invokeActivity(Activity activity, Class<?> tClass, int fragmentPosition , int selectedPosition, Date value, int accountId, String accountName) {
        Intent intent = new Intent(activity, tClass);
        Bundle b = new Bundle();

        b.putInt(PrefKey.Selected_category, selectedPosition);
//        b.putString(PrefKey.Selected_date, value);
//        b.put
        b.putInt(PrefKey.Spending_Position,fragmentPosition);
        b.putInt(PrefKey.Transaction_account_id,accountId);
        b.putString(PrefKey.Transaction_account_name, accountName);
        intent.putExtras(b);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

    public void startActivityWithResult(Activity activity, Class<?> tClass,   int selectedPosition) {
        Intent intent = new Intent(activity, tClass);
        Bundle b = new Bundle();
        b.putInt(PrefKey.Selected_category, selectedPosition);
        intent.putExtras(b);
        activity.startActivityForResult(intent, 1);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }



    public void startActivity(Activity activity, Class<?> tClass,  int resultForFragment,int adapterPosition) {
        Intent intent = new Intent(activity, tClass);
        Bundle b = new Bundle();
        b.putInt(PrefKey.Fragment_position, resultForFragment); //id
        b.putInt(PrefKey.Adapter_position, adapterPosition);
        intent.putExtras(b);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

    public void startActivity(Activity activity, Class<?> tClass,  int resultForFragment,int adapterPosition,String catName,int catColor) {
        Intent intent = new Intent(activity, tClass);
        Bundle b = new Bundle();
        b.putInt(PrefKey.Fragment_position, resultForFragment); //id
        b.putInt(PrefKey.Adapter_position, adapterPosition);
        b.putString(PrefKey.Category_name, catName);
        b.putInt(PrefKey.Category_color,catColor);
        intent.putExtras(b);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

    public void startActivity(Activity activity, Class<?> tClass,  int resultForFragment) {
        Intent intent = new Intent(activity, tClass);
        Bundle b = new Bundle();
        b.putInt(PrefKey.Fragment_position, resultForFragment); //id
        intent.putExtras(b);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

    public void startActivity(Context context, Class<?> tClass, int resultForFragment,int accId ,String accName) {
        Intent intent = new Intent(context, tClass);
        Bundle b = new Bundle();
        b.putInt(PrefKey.Fragment_position, resultForFragment); //id
        b.putInt(PrefKey.Account_id,accId);
        b.putString(PrefKey.Account_name,accName);
        intent.putExtras(b);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
//        context.getApplicationContext().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

    public void startActivity(Context context, Class<?> tClass, int resultForFragment, Accounts accounts) {
        Intent intent = new Intent(context, tClass);
        Bundle b = new Bundle();
        b.putInt(PrefKey.Fragment_position, resultForFragment); //id
        intent.putExtras(b);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
//        context.getApplicationContext().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

    public void invokeActivity(Activity mActivity, Class<?> tClass, int history_fragment_position, int transaction_id, int transaction_category_id, String transaction_category_name, int transaction_type_id, String transaction_type_name, int transaction_account_id, String transaction_account_name, Double transaction_amount_db, String transaction_date_db) {

        Intent intent = new Intent(mActivity, tClass);
        Bundle b = new Bundle();

        b.putInt(PrefKey.Spending_Position, history_fragment_position);
        b.putInt(PrefKey.Transaction_id, transaction_id);
        b.putInt(PrefKey.Transaction_category_id, transaction_category_id);
        b.putString(PrefKey.Transaction_category_name, transaction_category_name);
        b.putInt(PrefKey.Selected_category,transaction_type_id);
        b.putString(PrefKey.Transaction_type_name, transaction_type_name);
        b.putInt(PrefKey.Transaction_account_id,transaction_account_id);
        b.putString(PrefKey.Transaction_account_name, transaction_account_name);
        b.putDouble(PrefKey.Transaction_amount,transaction_amount_db);
        b.putString(PrefKey.Selected_date, transaction_date_db);


        intent.putExtras(b);
        mActivity.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);



    }

    public void invokeActivity(Activity mActivity, Class<?> addIncomeExpenseClass, int fragmentPosition, int i, String passDateString, int accountId) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(mActivity, addIncomeExpenseClass);

                Bundle b = new Bundle();

                b.putInt(PrefKey.Selected_category, i);
                b.putString(PrefKey.Selected_date, passDateString);
                b.putInt(PrefKey.Spending_Position,fragmentPosition);
                b.putInt(PrefKey.Transaction_account_id,accountId);
//        b.putString(PrefKey.Transaction_account_name, accountName);
                intent.putExtras(b);
                mActivity.startActivity(intent);
                mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        },400);

    }


    public void invokeActivity(Activity mActivity, Class<?> addIncomeExpenseClass,int budgetID, int fragmentPosition, Double bAmount, String passDateString, int accountId) {

        Intent intent = new Intent(mActivity, addIncomeExpenseClass);

        Bundle b = new Bundle();

        b.putInt(PrefKey.Budget_Id,budgetID);
        b.putDouble(PrefKey.Budget_Amount, bAmount);
        b.putString(PrefKey.Budget_Date, passDateString);
        b.putInt(PrefKey.Budget_Position,fragmentPosition);
        b.putInt(PrefKey.Budget_account_id,accountId);
//        b.putString(PrefKey.Transaction_account_name, accountName);
        intent.putExtras(b);
        mActivity.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void invokeActivity(Activity mActivity, Class<?> addIncomeExpenseClass, int fragmentPosition, int transaction_id,int transaction_category_id,int transaction_type_id,int transaction_account_id,Double transaction_amount,String transaction_date,String transactionNotes) {

        Intent intent = new Intent(mActivity, addIncomeExpenseClass);

        Bundle b = new Bundle();

        b.putInt(PrefKey.Spending_Position,fragmentPosition);
        b.putInt(PrefKey.Transaction_id, transaction_id);
        b.putInt(PrefKey.Transaction_category_id, transaction_category_id);
        b.putInt(PrefKey.Selected_category, transaction_type_id);
        b.putInt(PrefKey.Transaction_account_id,transaction_account_id);
        b.putDouble(PrefKey.Transaction_amount,transaction_amount);
        b.putString(PrefKey.Selected_date, transaction_date);
        b.putString(PrefKey.notesTransaction,transactionNotes);
        intent.putExtras(b);
        mActivity.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }
}
