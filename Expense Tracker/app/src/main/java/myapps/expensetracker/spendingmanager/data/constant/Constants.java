package myapps.expensetracker.spendingmanager.data.constant;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import androidx.lifecycle.LiveData;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.List;

import myapps.expensetracker.spendingmanager.entity.Accounts;

public class Constants {

    public static final int PERMISSION_REQ = 445;
    public static String SAVE_TO = "Spending_Tracker";
    public static LiveData<Float> incomeValue;
    //resultForFragment
    public static int SPENDING_FRAGMENT = 1;
    public static int UPDATE_EXPENSE_CATEGORIES_FRAGMENT = 2;
    public static int UPDATE_INCOME_CATEGORIES_FRAGMENT = 3;
    public static int NEW_EXPENSE_CATEGORIES_FRAGMENT=4;
    public static int NEW_INCOME_CATEGORIES_FRAGMENT=5;
    public static int NEW_Account_Add_FRAGMENT=6;
    public static int Account_UPDATE_FRAGMENT=7;

    public static int countUser;

    public static int Spending_FRAGMENT_Position=8;
    public static int History_FRAGMENT_Position=9;

    public static int adapterPosition=0;

    public static int catType=0;

    public static Integer incomeVal;



    public static List<Accounts> accounts;

    public static int accountId=1;
    public static String  accountName="Default";

    public static Integer numberOfAccounts=1;

    public static Calendar c=Calendar.getInstance();

    public static String passDateToNext;

    public static int add_new_account_icon;

    public static String currency_symbols="$";


    public static String imgStr;
    public static Bitmap imgBitmap;
    public static Drawable imgDrawable;

    public static Bitmap accountBitmap;
    public static Bitmap categoryBitmap;
    public static Bitmap TransactionBitmap=null;
    public static int TransactionUpdate=20;

    public static boolean resume=false;

    public static boolean checkDarkMode=false;

    public static boolean checkCurrencySymbol=false;

    public static int ADD_NEW_BUDGET=1;
    public static int ADD_UPDATE_BUDGET=2;

    public static String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String result = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return result;
    }
}
