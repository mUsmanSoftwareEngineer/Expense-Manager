package myapps.expensetracker.spendingmanager.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.activities.Add_Account;
import myapps.expensetracker.spendingmanager.activities.Add_Category;
//import myapps.expensetracker.spendingmanager.adapters.AccountAdapter;
import myapps.expensetracker.spendingmanager.activities.MainActivity;
import myapps.expensetracker.spendingmanager.adapters.AccountAdapter;
import myapps.expensetracker.spendingmanager.adapters.CategoryAdapter;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.data.models.CategoryModel;
import myapps.expensetracker.spendingmanager.data.preference.AppPreference;
import myapps.expensetracker.spendingmanager.data.preference.PrefKey;
import myapps.expensetracker.spendingmanager.entity.Accounts;
import myapps.expensetracker.spendingmanager.entity.Categories;
import myapps.expensetracker.spendingmanager.entity.Spending;
import myapps.expensetracker.spendingmanager.libs.BitmapManager;
import myapps.expensetracker.spendingmanager.utilities.ActivityUtils;
import myapps.expensetracker.spendingmanager.utilities.DialogUtils;
import myapps.expensetracker.spendingmanager.viewmodel.AccountViewModel;
import myapps.expensetracker.spendingmanager.viewmodel.CategoriesViewModel;
import myapps.expensetracker.spendingmanager.viewmodel.SpendingViewModel;


public class AccountFragment extends Fragment   {

     Activity mActivity;
     Context mContext;

     AccountAdapter adapter;

     RecyclerView mRecyclerView;

     ArrayList<CategoryModel> arrayList=null;

    private AccountViewModel mAccountViewModel;
    private SpendingViewModel mSpendingViewModel;



    ImageView add_new_btn,back_btn,add_new_account,deleteAccount;

    TextView titleToolbar,doneText;

    int CurrentAccountId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVar();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        initView(rootView);
        initFunctionality();
        initListener();

        return rootView;
    }

    private void initFunctionality() {


        ViewsSettings();

        buildRecycler();

        fetchAllAccounts();

    }

    private void fetchAllAccounts() {


        mAccountViewModel.getAllAccounts().observe((LifecycleOwner) mActivity, new Observer<List<Accounts>>() {

            @Override
            public void onChanged(List<Accounts> accounts) {

                adapter.setAccount(accounts);
            }

        });

    }



    private void ViewsSettings() {

        back_btn.setVisibility(View.VISIBLE);
        titleToolbar.setVisibility(View.VISIBLE);
        titleToolbar.setText(R.string.account_string);
        doneText.setVisibility(View.GONE);
        add_new_btn.setVisibility(View.GONE);

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


    public static String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String result = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return result;
    }

    private void buildRecycler() {

        adapter = new AccountAdapter(mContext);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(adapter);

    }

    private void initVar() {
        mActivity = getActivity();
        mContext = mActivity.getApplicationContext();
        mAccountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        mSpendingViewModel = ViewModelProviders.of(this).get(SpendingViewModel.class);
        arrayList=new ArrayList<>();
    }

    private void initView(View rootView) {

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.account_recycler);

        //ImageView
        add_new_btn=(ImageView) rootView.findViewById(R.id.add_new_cat);
        back_btn=(ImageView) rootView.findViewById(R.id.back);
        add_new_account=(ImageView) rootView.findViewById(R.id.add_new_acc);
        deleteAccount=(ImageView) rootView.findViewById(R.id.deleteBtn);


        //TextView
        titleToolbar=(TextView) rootView.findViewById(R.id.iconTxt);
        doneText=(TextView) rootView.findViewById(R.id.done);


    }


    private void initListener() {

        add_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.getInstance().startActivity(mActivity, Add_Account.class, Constants.NEW_Account_Add_FRAGMENT);
            }
        });



        adapter.setClickListener(new AccountAdapter.ClickListener() {
            @Override
            public void onItemClicked(int layoutPosition, int account_id, String account_name, int account_color) {

            }

            @Override
            public void onDeleteClicked(int layoutPosition, int account_id, String account_name, int account_color) {

            }

            @Override
            public void onRadioClickListener(int position, View view) {
                adapter.selectedItem();
            }

            @Override
            public void onItemLongClicked(int accountId) {



                DialogUtils.showDialogPrompt(mActivity, null, getString(R.string.delete_message_item),
                        getString(R.string.yes), getString(R.string.no), true, new DialogUtils.DialogActionListener() {
                            @Override
                            public void onPositiveClick() {
                                if(accountId!=1){
                                    Accounts accounts = new Accounts();
                                    accounts.setAccountId(accountId);
                                    mAccountViewModel.delete(accounts);

                                    Spending spending=new Spending();
                                    spending.setSpendingAccountHolderId(accountId);

                                    mSpendingViewModel.deleteSingleTransaction(accountId);

                                }
                                else {
                                    Toast.makeText(mActivity, "Default account can't be deleted", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });



        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, MainActivity.class));
                mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }


}
