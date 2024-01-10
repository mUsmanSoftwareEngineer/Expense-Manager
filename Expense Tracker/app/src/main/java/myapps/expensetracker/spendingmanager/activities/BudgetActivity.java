package myapps.expensetracker.spendingmanager.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maltaisn.calcdialog.CalcDialog;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.data.preference.PrefKey;
import myapps.expensetracker.spendingmanager.entity.Budget;
import myapps.expensetracker.spendingmanager.fragments.SpendingFragment;
import myapps.expensetracker.spendingmanager.viewmodel.BudgetViewModel;

public class BudgetActivity extends AppCompatActivity implements CalcDialog.CalcDialogCallback {



    int budgetPosition,budgetId,budgetAccountId;
    Double budgetAmount;
    String budgetDate;

    final CalcDialog calcDialog = new CalcDialog();

    ImageView back;
    TextView budget_amount,budget_date,done;

    RelativeLayout budget_amount_relative,budget_date_relative;

    SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
    SimpleDateFormat OutputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    String outputDate;

    private int mYear, mMonth, mDay;

    @Nullable
    private BigDecimal value = null;

    Date date=new Date();
    BudgetViewModel budgetViewModel;
    Activity mActivity;
    Context mContext;

    TextView budgetCurrencySymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        initVars();
        initViews();
        try {
            initFunctionality();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        initListeners();

    }

    private void initVars() {

        mActivity=this;
        mContext=this.getApplicationContext();
        budgetViewModel = ViewModelProviders.of(this).get(BudgetViewModel.class);

    }

    private void initViews() {

        back=(ImageView) findViewById(R.id.back);
        budget_amount=(TextView) findViewById(R.id.budget_amount);
        budget_date=(TextView) findViewById(R.id.budget_date);
        budget_amount_relative=(RelativeLayout) findViewById(R.id.add_budget_amount_relative);
        budget_date_relative=(RelativeLayout) findViewById(R.id.date_relative);

        done=(TextView) findViewById(R.id.done);
        budgetCurrencySymbol=(TextView) findViewById(R.id.currency_symbol);


    }

    private void initFunctionality() throws ParseException {

        setCurrencySymbol();

        Bundle b = getIntent().getExtras();

        if (b != null) {

            budgetId = b.getInt(PrefKey.Budget_Id);
            budgetAmount=b.getDouble(PrefKey.Budget_Amount);
            budgetDate=b.getString(PrefKey.Budget_Date);
            budgetPosition=b.getInt(PrefKey.Budget_Position);
            budgetAccountId=b.getInt(PrefKey.Budget_account_id);

        }

        DateDialogSetting();
        setBudgetDate();


        if(budgetPosition== Constants.ADD_NEW_BUDGET){
                Log.d("checkBudgetInfo",budgetId+"\n"+budgetAmount+"\n"+budgetDate+"\n"+budgetPosition+"\n"+budgetAccountId);
        }
        else {
            Log.d("checkBudgetInfo",budgetId+"\n"+budgetAmount+"\n"+budgetDate+"\n"+budgetPosition+"\n"+budgetAccountId);
            budget_amount.setText(budgetAmount+"");
//            budgetDate


        }

    }

    private void setCurrencySymbol(){
        budgetCurrencySymbol.setText(Constants.currency_symbols);
    }

    private void setBudgetDate() throws ParseException {

        date=DateFormat.parse(budgetDate);
        outputDate= OutputDateFormat.format(date);
        budget_date.setText(outputDate);

    }

    private void initListeners() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
            }
        });

       budget_amount_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcDialog.getSettings().setInitialValue(value);
                calcDialog.show(getSupportFragmentManager(), "calc_dialog");
            }
        });

        budget_date_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    transaction_dateFromCalendar();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(budgetPosition== Constants.ADD_NEW_BUDGET){
                        if(budgetAmount==0.0){
                            Toast.makeText(BudgetActivity.this, "Enter Correct Budget", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Constants.resume=true;
                            Budget budget=new Budget(budgetAmount,Constants.accountId,date);
                            budgetViewModel.insert(budget);
                            finish();
                        }
                }
                else {
                    if(budgetAmount==0.0){
                        Toast.makeText(BudgetActivity.this, "Enter Correct Budget", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Constants.resume=true;
                        Budget budget=new Budget(budgetAmount,Constants.accountId,date);
                        budget.setBudgetId(budgetId);
                        budgetViewModel.update(budget);
                        finish();
                    }
                }
            }
        });


    }

    private void DateDialogSetting() {

        calcDialog.getSettings().setExpressionEditable(true)
                .setExpressionShown(true)
                .setZeroShownWhenNoValue(false)
                .setAnswerBtnShown(true);

    }

    private void transaction_dateFromCalendar() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        c.set(year, monthOfYear, dayOfMonth);
                        date=c.getTime();
                        outputDate = OutputDateFormat.format(date);
                        budget_date.setText(outputDate);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    @Override
    public void onValueEntered(int requestCode, @Nullable BigDecimal value) {
        // if (requestCode == CALC_REQUEST_CODE) {} <-- If there were many dialogs, this would be used

        // The calculator dialog returned a value
        this.value = value;

        budgetAmount=this.value.doubleValue();
        budget_amount.setText(budgetAmount+"");

    }




}