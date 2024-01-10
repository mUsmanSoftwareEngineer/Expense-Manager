package myapps.expensetracker.spendingmanager.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.activities.AddIncomeExpense;
import myapps.expensetracker.spendingmanager.activities.MainActivity;
import myapps.expensetracker.spendingmanager.adapters.TransactionAdapter;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.entity.Categories;
import myapps.expensetracker.spendingmanager.entity.Spending;
import myapps.expensetracker.spendingmanager.utilities.ActivityUtils;
import myapps.expensetracker.spendingmanager.utilities.DialogUtils;
import myapps.expensetracker.spendingmanager.viewmodel.CategoriesViewModel;
import myapps.expensetracker.spendingmanager.viewmodel.SpendingViewModel;


public class HistoryFragment extends Fragment {

    TransactionAdapter adapter;
    RecyclerView mRecyclerView;
    CheckBox expenseCheck, incomeCheck, allCheck;
    SimpleDateFormat inputDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
    SimpleDateFormat outputDate = new SimpleDateFormat("MMM yyyy");
    TextView monthTxt;
    Date date1;
    ImageView backImg;
    RelativeLayout incomeCheckRelative, expenseCheckRelative, allCheckRelative;
    private Activity mActivity;
    private Context mContext;
    private SpendingViewModel mSpendingViewModel;
    private CategoriesViewModel mCategoryViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d("checkFragment","onCreate");
        initVar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        initView(rootView);
        initFunctionality();
        initListener();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    private void initFunctionality() {


        setDate();
        buildRecycler();
        populateViewModels();


    }

    private void setDate() {
        try {
            date1 = inputDate.parse(Constants.passDateToNext);
        } catch (ParseException e) {
            Toast.makeText(mActivity, e + "", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        monthTxt.setText(outputDate.format(date1));
    }

    private void initVar() {
        mActivity = getActivity();
        mContext = mActivity.getApplicationContext();

        mSpendingViewModel = ViewModelProviders.of(this).get(SpendingViewModel.class);
        mCategoryViewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
    }

    private void initView(View rootView) {

        mRecyclerView = rootView.findViewById(R.id.history_recycler);
        expenseCheck = rootView.findViewById(R.id.expense_checkBox);
        incomeCheck = rootView.findViewById(R.id.income_checkBox);
        allCheck = rootView.findViewById(R.id.allCheckBox);
        monthTxt = rootView.findViewById(R.id.txt_month_date);
        backImg = rootView.findViewById(R.id.back);

        expenseCheckRelative = rootView.findViewById(R.id.expense_check);
        incomeCheckRelative = rootView.findViewById(R.id.income_check);
        allCheckRelative = rootView.findViewById(R.id.all_check);

    }


    private void initListener() {


        expenseCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    incomeCheck.setChecked(false);
                    allCheck.setChecked(false);

                    mSpendingViewModel.getExpenseMonthly(SpendingFragment.passDateInMonth, 0, Constants.accountId).observe(getViewLifecycleOwner(), new Observer<List<Spending>>() {
                        @Override
                        public void onChanged(List<Spending> spendings) {
                            adapter.setTransaction(spendings);
                        }
                    });
                }
            }
        });

        incomeCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    expenseCheck.setChecked(false);
                    allCheck.setChecked(false);
                    mSpendingViewModel.getExpenseMonthly(SpendingFragment.passDateInMonth, 1, Constants.accountId).observe(getViewLifecycleOwner(), new Observer<List<Spending>>() {
                        @Override
                        public void onChanged(List<Spending> spendings) {
                            adapter.setTransaction(spendings);
                        }
                    });
                }
            }
        });

        allCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    incomeCheck.setChecked(false);
                    expenseCheck.setChecked(false);

                    mSpendingViewModel.getSpending(SpendingFragment.passDateInMonth, SpendingFragment.passDateInMonth, Constants.accountId).observe(getViewLifecycleOwner(), new Observer<List<Spending>>() {
                        @Override
                        public void onChanged(List<Spending> spendings) {
                            adapter.setTransaction(spendings);
                        }
                    });
                }
            }
        });

        adapter.setClickListener(new TransactionAdapter.ClickListener() {
            @Override
            public void onItemClicked(int layoutPosition, int transaction_id, int transaction_category_id, int transaction_type_id, int transaction_account_id, double transaction_amount, String transaction_date, String transactionNotes) {

                ActivityUtils.getInstance().invokeActivity(mActivity, AddIncomeExpense.class, Constants.History_FRAGMENT_Position, transaction_id, transaction_category_id, transaction_type_id, transaction_account_id, transaction_amount, transaction_date, transactionNotes);

            }

            @Override
            public void onItemLongClicked(int transactionId) {

                DialogUtils.showDialogPrompt(mActivity, null, getString(R.string.delete_transaction_item),
                        getString(R.string.yes), getString(R.string.no), true, new DialogUtils.DialogActionListener() {
                            @Override
                            public void onPositiveClick() {
                                Spending spending = new Spending();
                                spending.setSpendingId(transactionId);
                                mSpendingViewModel.delete(spending);
                            }
                        });


            }


        });


        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, MainActivity.class));
                mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


    }

    private void buildRecycler() {

        adapter = new TransactionAdapter(mContext);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(adapter);

    }

    private void populateViewModels() {


        getAllCategories();

        mSpendingViewModel.getSpending(SpendingFragment.passDateInMonth, SpendingFragment.passDateInMonth, Constants.accountId).observe(getViewLifecycleOwner(), new Observer<List<Spending>>() {
            @Override
            public void onChanged(List<Spending> spendings) {
                adapter.setTransaction(spendings);
            }
        });
    }

    public void getAllCategories() {

        mCategoryViewModel.getAllCategories().observe(getViewLifecycleOwner(), new Observer<List<Categories>>() {
            @Override
            public void onChanged(List<Categories> categories) {


                adapter.setmCategory(categories);

            }
        });

    }


}
