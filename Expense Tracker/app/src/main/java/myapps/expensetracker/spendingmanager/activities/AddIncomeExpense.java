package myapps.expensetracker.spendingmanager.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.maltaisn.calcdialog.CalcDialog;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.data.preference.AppPreference;
import myapps.expensetracker.spendingmanager.data.preference.PrefKey;
import myapps.expensetracker.spendingmanager.entity.Categories;
import myapps.expensetracker.spendingmanager.entity.Spending;
import myapps.expensetracker.spendingmanager.libs.BitmapManager;
import myapps.expensetracker.spendingmanager.utilities.ActivityUtils;
import myapps.expensetracker.spendingmanager.utilities.AppUtils;
import myapps.expensetracker.spendingmanager.utilities.ImageCaptureManager;
import myapps.expensetracker.spendingmanager.viewmodel.AccountViewModel;
import myapps.expensetracker.spendingmanager.viewmodel.CategoriesViewModel;
import myapps.expensetracker.spendingmanager.viewmodel.SpendingViewModel;

public class AddIncomeExpense extends AppCompatActivity implements CalcDialog.CalcDialogCallback {

    public static final int PICK_IMAGE = 3;
    public static final int CAMERA_REQUEST_CODE = 101;
    public static int NumberOfAccounts;
    final CalcDialog calcDialog = new CalcDialog();
    private final int current_adapter_position = 0;
    int resultForFragment = 0;
    TextView textView_1, textView_2, textView_3;
    TextView editText_1;
    //    ImageView close;
    String transaction_date = "";
    String saveDBDate = "";
    String endTransactionDate;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
    SimpleDateFormat dbFormatter = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat Formatter = new SimpleDateFormat("yyyy-MM");
    int account_id = 1;
    String storeDate = "";
    String setDateTextView;
    Date transactionDate;
    Date EndTransactionDate;
    Date setCurrDate;
    RelativeLayout accountRelative;
    String accountName = "Default";
    int categoryId;
    String categoryName;
    int categoryIcon;
    Double spendingAmount;
    Calendar c;
    String[] weight = {"Expense", "Income"};
    int transaction_account_id, transaction_id;
    String transaction_account_name;
    Double transaction_amount_db;
    ImageView back, add_new_item, galleryImg, cameraImg, setImg, transactionCatIcon;
    TextView titleToolbar, done, currencySymbol, transactionAmount, catName, transaction_date_txt, end_auto_date;
    EditText categoryNotes;
    Button del, confirm;
    RadioButton autoAdd, autoReminder;
    RadioGroup radioGroup;
    Spinner simpleSpinner_type;
    RelativeLayout amountRel, dateRel, categoryRel, radio_rel;
    LinearLayout end_date_linear, radio_linear, imageViewLinear;
    boolean checkIntent = false;
    String saveBitmap;
    String notesText;
    byte[] image;
    boolean categoryExist = false;
    private Activity mActivity;
    private Context mContext;
    private TextView income_btn, expense_btn;
    private int transaction_type = 0;
    private int current_fragment_position = 0;
    private int mYear, mMonth, mDay;
    private AccountViewModel mAccountViewModel;
    @Nullable
    private BigDecimal value = null;
    private SpendingViewModel mSpendingViewModel;
    private ImageCaptureManager imageCaptureManager;
    private CategoriesViewModel mCategoryViewModel;
    View v;
    RelativeLayout rootV;

    public static String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String result = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income_expense);


        initVar();
        initViews();
        try {
            initFunctionality();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        initListener();

    }

    private void initVar() {

        mActivity = this;
        mContext = mActivity.getApplicationContext();
        mCategoryViewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
        mSpendingViewModel = ViewModelProviders.of(this).get(SpendingViewModel.class);

    }

    private void initViews() {

        //imageViews
        back = findViewById(R.id.back);
        add_new_item = findViewById(R.id.add_new_cat);
        galleryImg = findViewById(R.id.galleryImg);
        cameraImg = findViewById(R.id.cameraImg);
        setImg = findViewById(R.id.transactionImg);
        transactionCatIcon = findViewById(R.id.transaction_category_icon);


        //textViews
        titleToolbar = findViewById(R.id.iconTxt);
        done = findViewById(R.id.done);
        currencySymbol = findViewById(R.id.currency_symbol);
        transactionAmount = findViewById(R.id.transaction_amount);
        catName = findViewById(R.id.transaction_category_name);
        transaction_date_txt = findViewById(R.id.transaction_date);
        end_auto_date = findViewById(R.id.end_auto_date);

        //EditText
        categoryNotes = findViewById(R.id.transaction_notes);

        //button
        confirm = findViewById(R.id.confirm);

        //radioButtons
        autoAdd = findViewById(R.id.auto_add);
//        autoAdd=(RadioButton) findViewById(R.id.auto_remind);

//        radioGroup = (RadioGroup)findViewById(R.id.auto_add_radio_group);

        //spinner
        simpleSpinner_type = findViewById(R.id.simpleSpinner_type);

        //RelativeLayout
        amountRel = findViewById(R.id.add_amount_relative);
        dateRel = findViewById(R.id.date_relative);
        categoryRel = findViewById(R.id.category_rel);
        radio_rel = findViewById(R.id.radio_relative);
        rootV=findViewById(R.id.rootView);

        //LinearLayout
        end_date_linear = findViewById(R.id.transaction_linear_7);
        radio_linear = findViewById(R.id.radio_linear);
        imageViewLinear = findViewById(R.id.transaction_linear_6);

//        textView_1=(TextView) findViewById(R.id.txt_2);
//        textView_2=(TextView) findViewById(R.id.txt_4);
//        textView_3=(TextView) findViewById(R.id.account_name);
//
//        editText_1=(TextView) findViewById(R.id.edit_1);
//
//        accountRelative=(RelativeLayout) findViewById(R.id.account_relative);
//
//        del = (Button) findViewById(R.id.deleteTransaction);
//

    }

    private void initFunctionality() throws ParseException {


        back.setVisibility(View.VISIBLE);
        titleToolbar.setVisibility(View.VISIBLE);
        done.setVisibility(View.GONE);
        add_new_item.setVisibility(View.GONE);

        setCurrencySymbol();

        Bundle b = getIntent().getExtras();

        if (b != null) {
            transaction_type = b.getInt(PrefKey.Selected_category);
            transaction_date = b.getString(PrefKey.Selected_date);
            current_fragment_position = b.getInt(PrefKey.Spending_Position);
            transaction_id = b.getInt(PrefKey.Transaction_id);
            categoryId = b.getInt(PrefKey.Transaction_category_id);
            categoryName = b.getString(PrefKey.Transaction_category_name);
            spendingAmount = b.getDouble(PrefKey.Transaction_amount);
            account_id = b.getInt(PrefKey.Transaction_account_id);
            accountName = b.getString(PrefKey.Transaction_account_name);
            notesText = b.getString(PrefKey.notesTransaction);
        }


        if (transaction_type == 0) {
            titleToolbar.setText(R.string.expense);
            weight = new String[]{"Expense", "Income"};
        } else if (transaction_type == 1) {
            titleToolbar.setText(R.string.income);
            weight = new String[]{"Income", "Expense"};
        }


        setTransaction_date();

        setCurrentDate();


        DateDialogSetting();


        viewSettings();

        SpinnerData();

        checkTutorials();

    }

    private void checkTutorials() {
        boolean firstRun = AppPreference.getInstance(mContext).getBoolean(PrefKey.IncomeExpenseFirstRun, true);
        if (firstRun) {
            new SimpleTooltip.Builder(mContext)
                    .anchorView(rootV)
                    .text(mActivity.getResources().getString(R.string.incomeExpenseTooltip))
                    .gravity(Gravity.CENTER)
                    .animated(true)
                    .transparentOverlay(true)
                    .build()
                    .show();

        }

        AppPreference.getInstance(mContext).setBoolean(PrefKey.IncomeExpenseFirstRun, false);

    }

    private void setCurrencySymbol() {

        currencySymbol.setText(Constants.currency_symbols);
    }

    private void setCurrentDate() throws ParseException {

        setCurrDate = formatter.parse(transaction_date);
        setDateTextView = dbFormatter.format(setCurrDate.getTime());
        transaction_date_txt.setText(setDateTextView);
    }



    private void setTransaction_date() throws ParseException {

        transactionDate = formatter.parse(transaction_date);

        transactionDate.setSeconds(transactionDate.getSeconds() + 10);

    }

    public void showAddExpenseDialog(final Activity activity, Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

        LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View customLayoutpermission = vi.inflate(R.layout.add_expense_dialogue, null);
        AlertDialog alert = alertDialog.create();
        alert.setView(customLayoutpermission);
        alert.setCancelable(false);

        ImageView close = customLayoutpermission.findViewById(R.id.close_dialogue);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                startActivity(new Intent(mContext, MainActivity.class));
//                activity.finish();
            }
        });

        alert.show();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.80); //<-- int width=400;
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.40);
        alert.getWindow().setLayout(width, height);
    }

    private void viewSettings() {

        if (current_fragment_position == Constants.Spending_FRAGMENT_Position) {

        } else if (current_fragment_position == Constants.History_FRAGMENT_Position) {


            transactionAmount.setText(spendingAmount + "");

            mCategoryViewModel.getCategory(categoryId).observe((LifecycleOwner) mActivity, new Observer<List<Categories>>() {
                @Override
                public void onChanged(List<Categories> categories) {

                    categoryName = categories.get(0).getCategoryName();

                    Bitmap catIcon = BitmapManager.byteToBitmap(categories.get(0).getCategoryImage());
                    transactionCatIcon.setImageBitmap(catIcon);
                    catName.setText(categories.get(0).getCategoryName());

                }
            });


            if (notesText != null) {
                categoryNotes.setText(notesText);
            }

            if (Constants.TransactionBitmap != null) {

                image = BitmapManager.bitmapToByte(Constants.TransactionBitmap);
                imageViewLinear.setVisibility(View.VISIBLE);
                setImg.setImageBitmap(Constants.TransactionBitmap);
            }

        }
    }



    public void showProgressDialog(final Activity activity) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

        LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View customLayoutpermission = vi.inflate(R.layout.progress_dialog, null);
        AlertDialog alert = alertDialog.create();
        alert.setView(customLayoutpermission);
        alert.setCancelable(false);

        alert.show();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.80); //<-- int width=400;
//        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.20);
//        alert.getWindow().setLayout(width, height);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alert.dismiss();
                if (categoryExist) {
                    Constants.resume = true;
                    Spending spending = new Spending(transaction_type, categoryId, spendingAmount, Constants.accountId, transactionDate, notesText, image);
                    spending.setSpendingId(transaction_id);
                    mSpendingViewModel.update(spending);
                    startActivity(new Intent(mContext, MainActivity.class));
//                    finish();
                } else {
                    Constants.resume = true;
                    Spending spending = new Spending(transaction_type, categoryId, spendingAmount, Constants.accountId, transactionDate, notesText, image);
                    mSpendingViewModel.insert(spending);
                    startActivity(new Intent(mContext, MainActivity.class));
//                    finish();
//                                   showAddExpenseDialog(mActivity,mContext);
                }

            }
        }, 500);
    }

    private void DateDialogSetting() {

        calcDialog.getSettings().setExpressionEditable(true)
                .setExpressionShown(true)
                .setZeroShownWhenNoValue(false)
                .setAnswerBtnShown(true);
    }

    @Override
    public void onValueEntered(int requestCode, @Nullable BigDecimal value) {
        // if (requestCode == CALC_REQUEST_CODE) {} <-- If there were many dialogs, this would be used

        // The calculator dialog returned a value
        this.value = value;

        spendingAmount = this.value.doubleValue();
        transactionAmount.setText(spendingAmount + "");
    }


//    private void endTransaction_dateFromCalendar() {
//
//        final Calendar c = Calendar.getInstance();
//        mYear = c.get(Calendar.YEAR);
//        mMonth = c.get(Calendar.MONTH);
//        mDay = c.get(Calendar.DAY_OF_MONTH);
//
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
//                new DatePickerDialog.OnDateSetListener() {
//
//                    @Override
//                    public void onDateSet(DatePicker view, int year,
//                                          int monthOfYear, int dayOfMonth) {
//
//                        c.set(year, monthOfYear, dayOfMonth);
//                        transaction_date=formatter.format((c.getTime()));
//                        saveDBDate=dbFormatter.format(c.getTime());
//                        transactionDate=c.getTime();
//                        endTransactionDate=saveDBDate;
//                        end_auto_date.setText(saveDBDate);
//
//                    }
//                }, mYear, mMonth, mDay);
//        datePickerDialog.show();
//
//    }

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
                        transaction_date = formatter.format((c.getTime()));
                        saveDBDate = dbFormatter.format(c.getTime());

                        transactionDate = c.getTime();
                        setDateTextView = saveDBDate;
                        transaction_date_txt.setText(saveDBDate);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();


    }

    private void SpinnerData() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_text, this.weight);
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        this.simpleSpinner_type.setAdapter(arrayAdapter);
        this.simpleSpinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {

                if (weight[i].equals("Expense")) {
                    transaction_type = 0;
                    titleToolbar.setText(R.string.expense);

                    if (current_fragment_position == Constants.Spending_FRAGMENT_Position) {
                        categoryName = null;
                        catName.setText(R.string.Select_category);


                    } else if (current_fragment_position == Constants.History_FRAGMENT_Position) {
                        transaction_type = 0;
                        titleToolbar.setText(R.string.expense);

                    }

                } else {
                    transaction_type = 1;
                    titleToolbar.setText(R.string.income);
                    if (current_fragment_position == Constants.Spending_FRAGMENT_Position) {
                        categoryName = null;
                        catName.setText(R.string.Select_category);


                    } else if (current_fragment_position == Constants.History_FRAGMENT_Position) {
                        transaction_type = 1;
                        titleToolbar.setText(R.string.income);
                    }
                }

            }
        });
    }

    private void initListener() {

        amountRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcDialog.getSettings().setInitialValue(value);
                calcDialog.show(getSupportFragmentManager(), "calc_dialog");
            }
        });

        dateRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction_dateFromCalendar();
            }
        });

        categoryRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (transaction_type == 0) {
                    ActivityUtils.getInstance().startActivityWithResult(mActivity, SelectCategory.class, 0);
                } else {
                    ActivityUtils.getInstance().startActivityWithResult(mActivity, SelectCategory.class, 1);
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        autoAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Toast.makeText(mActivity, isChecked+"", Toast.LENGTH_SHORT).show();
                if (isChecked) {
                    end_date_linear.setVisibility(View.VISIBLE);

                }
            }
        });

        end_date_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               endTransaction_dateFromCalendar();
            }
        });

        galleryImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIntent = false;
                if (checkWritePermission()) {
                    galleryImage();
                }
            }
        });

        cameraImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIntent = true;
                checkCameraPermission();
//               Log.d("checkPermissions",checkCameraPermission()+"");
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

//               Toast.makeText(mActivity, categoryName, Toast.LENGTH_SHORT).show();

                notesText = categoryNotes.getText().toString();

                if (categoryName == null) {
                    Toast.makeText(mActivity, "Select Category", Toast.LENGTH_SHORT).show();
                } else if (spendingAmount == 0.0) {
                    Toast.makeText(mActivity, "Enter correct amount", Toast.LENGTH_SHORT).show();
                } else {
                    if (current_fragment_position == Constants.Spending_FRAGMENT_Position) {


//                       Toast.makeText(mActivity, Formatter.format(transactionDate)+"", Toast.LENGTH_SHORT).show();

                        String checkDate = Formatter.format(transactionDate);

                        mSpendingViewModel.getTransactionByCategory(checkDate, categoryId, Constants.accountId).observe(AddIncomeExpense.this, new Observer<List<Spending>>() {
                            @Override
                            public void onChanged(List<Spending> spendings) {


                                if (spendings.size() != 0) {
//                                   Toast.makeText(mActivity, "kab kab ata", Toast.LENGTH_SHORT).show();
                                    transaction_id = spendings.get(0).getSpendingId();
                                    spendingAmount = spendingAmount + spendings.get(0).getSpendingAmount();
                                    categoryExist = true;
                                }

                            }
                        });

//                       Toast.makeText(mActivity, categoryExist+"", Toast.LENGTH_SHORT).show();
                        showProgressDialog(mActivity);


                    } else if (current_fragment_position == Constants.History_FRAGMENT_Position) {
                        Constants.resume = true;
                        Spending spending = new Spending(transaction_type, categoryId, spendingAmount, Constants.accountId, transactionDate, notesText, image);
                        spending.setSpendingId(transaction_id);
                        mSpendingViewModel.update(spending);
                        showAddExpenseDialog(mActivity, mContext);
                    }

                }

//               if(categoryName.isEmpty()){
//                   Toast.makeText(mActivity, "Category can't be empty", Toast.LENGTH_SHORT).show();
//               }

//               Log.d("checkTransactionInfo",transaction_type+"\n"+categoryId+"\n"+categoryName+"\n"+spendingAmount+"\n"+account_id+"\n"+transaction_date+"\n"+notesText+"\n"+saveBitmap);


//               Spending spending = new Spending(transaction_type,"Expense",categoryId,categoryName,spendingAmount,account_id,accountName,transactionDate,notesText,saveBitmap);
//               mSpendingViewModel.insert(spending);

//               if(transaction_type==0){
//
//               }
//               else if(transaction_type)


//               if(autoAdd.isChecked()){
//                   calculateDates();
//               }


//               if(!transaction_date.isEmpty() && spendingAmount!=null)

//               Spending spending = new Spending(transaction_type,"Expense",categoryId,categoryName,spendingAmount,account_id,accountName,transactionDate,notesText,saveBitmap);
//               mSpendingViewModel.insert(spending);

            }
        });


//        del.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(transaction_type==0){
//
//                    Spending spending = new Spending(0,"income",categoryId,categoryName,spendingAmount,account_id,accountName,transactionDate);
//                    spending.setSpendingId(transaction_id);
//                    mSpendingViewModel.delete(spending);
//                    finish();
//
//                }else if(transaction_type==1){
//
//                    Spending spending = new Spending(0,"income",categoryId,categoryName,spendingAmount,account_id,accountName,transactionDate);
//                    spending.setSpendingId(transaction_id);
//                    mSpendingViewModel.delete(spending);
//                    finish();
//
//                }
//
//            }
//        });
//
//        done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//                if(textView_2.getText().toString().isEmpty()){
//                    Toast.makeText(mActivity, "Category can't be empty", Toast.LENGTH_SHORT).show();
//                }
//                else if(editText_1.getText().toString().isEmpty()){
//                    Toast.makeText(mActivity, "Amount can't be empty", Toast.LENGTH_SHORT).show();
//                }
//                else{
//
//                    if(current_fragment_position==Constants.Spending_FRAGMENT_Position){
//
//                        if(transaction_type==0){
//
//
//                            Spending spending = new Spending(0,"Income",categoryId,categoryName,spendingAmount,account_id,accountName,transactionDate);
//                            mSpendingViewModel.insert(spending);
//                            Intent intent = new Intent(mActivity, MainActivity.class);
//                            startActivity(intent);
//                            mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                        }else if(transaction_type==1){
//
//
//                            Spending spending = new Spending(1,"Expense",categoryId,categoryName,spendingAmount,account_id,accountName,transactionDate);
//                            mSpendingViewModel.insert(spending);
//                            Intent intent = new Intent(mActivity, MainActivity.class);
//                            startActivity(intent);
//                            mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                        }
//
//                    }
//                    else if(current_fragment_position==Constants.History_FRAGMENT_Position){
//
//                        if(transaction_type==0){
//
//                            Spending spending = new Spending(0,"Income",categoryId,categoryName,spendingAmount,account_id,accountName,transactionDate);
//                            spending.setSpendingId(transaction_id);
//                            mSpendingViewModel.update(spending);
//                            finish();
//
//                        }else if(transaction_type==1){
//
//                            Spending spending = new Spending(1,"income",categoryId,categoryName,spendingAmount,account_id,accountName,transactionDate);
//                            spending.setSpendingId(transaction_id);
//                            mSpendingViewModel.update(spending);
//                            finish();
//
//                        }
//
//
//                    }
//
//
//
//                }
//
//
//            }
//        });
//
//        editText_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calcDialog.getSettings().setInitialValue(value);
//                calcDialog.show(getSupportFragmentManager(), "calc_dialog");
//            }
//        });


    }


    private void galleryImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        //set intent type to image
        intent.setType("image/*");
//        galleryActivityResultLauncher.launch(intent);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    private void checkCameraPermission() {
        if ((ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    Constants.PERMISSION_REQ);
        } else {
            if (checkWritePermission()) {
                startCameraActivity();
            }


//           return true;
        }
//        return false;
    }


    private void startCameraActivity() {
//        this.imageCaptureManager = new ImageCaptureManager(this);
//        try {
//            startActivityForResult(this.imageCaptureManager.dispatchTakePictureIntent(), 2);
//        } catch (IOException | ActivityNotFoundException e) {
//            e.printStackTrace();
//        }
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.PERMISSION_REQ) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.CAMERA)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {

                        checkWritePermission();
                        if (checkWritePermission()) {
                            startCameraActivity();
                        }


                    } else {

                        AppUtils.showToast(mContext, getString(R.string.permission_not_granted));
                    }
                } else if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {

                        if (checkIntent) {
                            startCameraActivity();
                        } else {
                            galleryImage();
                        }


                    } else {
                        AppUtils.showToast(mContext, getString(R.string.permission_not_granted));
                    }
                }
            }
        }
    }

    private boolean checkWritePermission() {
        if ((ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constants.PERMISSION_REQ);
        } else {
            return true;
        }
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                categoryName = data.getStringExtra("categoryName");
                categoryIcon = data.getIntExtra("categoryIcon", 0);
                categoryId = data.getIntExtra("categoryId", 0);

                catName.setText(categoryName);
                if (Constants.categoryBitmap != null) {
                    transactionCatIcon.setImageBitmap(Constants.categoryBitmap);
                } else {
                    transactionCatIcon.setVisibility(View.GONE);
                }

                Constants.adapterPosition = 0;

            }
        }

        if (requestCode == 101) {
//            if(resultCode==RESULT_OK){

//                if (this.imageCaptureManager == null) {
//                    this.imageCaptureManager = new ImageCaptureManager(this);
//                }
//
//                try {
//
////                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(this.imageCaptureManager.getCurrentPhotoPath()));
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(new File(this.imageCaptureManager.getCurrentPhotoPath())));
//
//
//                    imageViewLinear.setVisibility(View.VISIBLE);
//                    setImg.setImageBitmap(bitmap);
//                    saveBitmap=convertBitmapToString(bitmap);
//
//                } catch (IOException e) {
////                e.printStackTrace();
//                    Log.d("valued", "" + e.getMessage());
//
//                }

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            imageViewLinear.setVisibility(View.VISIBLE);
            setImg.setImageBitmap(bitmap);
            image = BitmapManager.bitmapToByte(bitmap);

        } else if (requestCode == 3) {
            if (data != null) {
                Uri selectedImage = null;
                try {
                    selectedImage = data.getData();
                } catch (Exception e) {
                    Toast.makeText(mActivity, "File Corrupted", Toast.LENGTH_SHORT).show();
                }

                InputStream baseStream = null;
                InputStream imageStream = null;
                try {
                    //getting the image
                    baseStream = mActivity.getContentResolver().openInputStream(selectedImage);
                    imageStream = mActivity.getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    Toast.makeText(mContext, getResources().getString(R.string.error_file), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    return;
                }

                // ChecksumException
                try {
                    //decoding bitmap for get width
                    Bitmap base = BitmapFactory.decodeStream(baseStream);
                    //set options for resize image
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = base.getWidth() / 1000; //get compress coef  (set image px size (compressing big image))

                    //decoding bitmap
                    Bitmap bMap = BitmapFactory.decodeStream(imageStream, null, options); //get image with options
//                            saveBitmap=convertBitmapToString(bMap);
                    imageViewLinear.setVisibility(View.VISIBLE);
                    setImg.setImageBitmap(bMap);

                    image = BitmapManager.bitmapToByte(bMap);
//                            Log.d("checkBitmapFromGallery",bMap+"");
                    //Convert Bitmap to ByteArray
//                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                        bMap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                        byte[] byteArray = stream.toByteArray();


                } catch (Exception e) {
//                        Toast.makeText(mContext, getResources().getString(R.string.error_nothing_find), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }

    }


}