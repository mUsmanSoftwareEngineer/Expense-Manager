package myapps.expensetracker.spendingmanager.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nightonke.boommenu.BoomMenuButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.activities.BudgetActivity;
import myapps.expensetracker.spendingmanager.activities.BuilderManager;
import myapps.expensetracker.spendingmanager.activities.MainActivity;
import myapps.expensetracker.spendingmanager.activities.ReminderScreen;
import myapps.expensetracker.spendingmanager.activities.Settings_Screen;
import myapps.expensetracker.spendingmanager.adapters.TransactionAdapter;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.data.models.BarChartEnteries;
import myapps.expensetracker.spendingmanager.data.models.SpendingModel;
import myapps.expensetracker.spendingmanager.data.preference.AppPreference;
import myapps.expensetracker.spendingmanager.data.preference.PrefKey;
import myapps.expensetracker.spendingmanager.entity.Accounts;
import myapps.expensetracker.spendingmanager.entity.Budget;
import myapps.expensetracker.spendingmanager.entity.Categories;
import myapps.expensetracker.spendingmanager.entity.Spending;
import myapps.expensetracker.spendingmanager.libs.BitmapManager;
import myapps.expensetracker.spendingmanager.utilities.ActivityUtils;
import myapps.expensetracker.spendingmanager.utilities.AppUtils;
import myapps.expensetracker.spendingmanager.viewmodel.AccountViewModel;
import myapps.expensetracker.spendingmanager.viewmodel.BudgetViewModel;
import myapps.expensetracker.spendingmanager.viewmodel.CategoriesViewModel;
import myapps.expensetracker.spendingmanager.viewmodel.SpendingViewModel;


public class SpendingFragment extends Fragment implements OnChartValueSelectedListener {

    public static Double expenseValue;
    public static String passDateInMonth;
    public static int current_cat_btn = 0;
    public static int current_graph_cat_btn = 0;
    protected Typeface tfRegular, popsin;
    protected Typeface tfLight;
    ImageView nextMonth, previousMonth, add_transaction;
    TextView monthText, addIncome, addExpense;
    FloatingActionButton add;
    LinearLayout add_income_expense;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    int count = 0;
    boolean isUp;
    boolean isOpen = false;
    TextView allIncome, allExpense, allBalance, budgetText;
    Double getIncVal;
    Double getExpVal;
    TransactionAdapter adapter;
    RecyclerView mRecyclerView;
    //    DialogAdapter dialogAdapter;
    RelativeLayout relAppBar;
    String dateFromPrefs;
    Double balanceValue;
    Float incVal;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
    SimpleDateFormat passDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
    SimpleDateFormat monthDateFormatter = new SimpleDateFormat("yyyy-MM");
    String getDateText;
    String passDateString;
    Date date = null;
    String income;
    Integer number;
    DrawerLayout mDrawerLayout;
    ImageView add_new;
    List<Accounts> accountsList;
    ArrayList<PieEntry> entries = new ArrayList<>();
    List<SpendingModel> mSpendingModel = new ArrayList<>();
    List<SpendingModel> mSpendingModelIncome = new ArrayList<>();
    RelativeLayout expenseGraph, incomeGraph;
    ImageView open_menu;
    FloatingActionButton actionButton;
    BoomMenuButton bmb;
    DrawerLayout drawerLayout;
    //    NavigationView navigationView;
    NavigationView navigationView;
    LinearLayout contentView;
    RelativeLayout addBudget;
    Double budgetAmount = 0.0;
    int budgetId = 0;
    String budgetDate;
    RelativeLayout bmbRelative;
    TextView balanceCurrencySymbol, budgetCurrencySymbol, incomeCurrencySymbol, expenseCurrencySymbol;
    SharedPreferences prefs;
    ToggleButton notifications, reminders;
    List<String> xAxisValues = new ArrayList<>();
    SimpleDateFormat lineChartFormatter = new SimpleDateFormat("dd-MMM");
    SimpleDateFormat lineChartDBFormatter = new SimpleDateFormat("yyyy-MM-dd");
    List<BarChartEnteries> lineChartEnteries = new ArrayList<>();
    boolean checkNotifications;
    TextView accName;
    CircularImageView accImage, accImageMain;
    Bitmap accountBitmap;
    Double savings;
    TextView updateSavings;
    TextView currentTransaction;
    SimpleTooltip.Builder tooltip;
    boolean firstTip = false;
    private Activity mActivity;
    private Context mContext;
    private SpendingViewModel mSpendingViewModel;
    private AccountViewModel mAccountViewModel;
    private BudgetViewModel mBudgetViewModel;
    private PieChart pieChart, pieChart2;
    private List<Categories> mCategory = new ArrayList<>();
    private CategoriesViewModel mCategoryViewModel;
    private LineChart lineChart;
    private LinearLayout income_btn, expense_btn;
    private String accountName;
    private boolean noData = false;
    private TextView noDataTxt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVar();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("isVisible", "is create");

        View rootView = inflater.inflate(R.layout.fragment_spending, container, false);

        initView(rootView);
        try {
            initFunctionality();
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        initListener();

        return rootView;
    }


    private void initView(View rootView) {


        //ImageViews
        nextMonth = rootView.findViewById(R.id.img_next);
        previousMonth = rootView.findViewById(R.id.img_pre);
        add_transaction = rootView.findViewById(R.id.add_transaction);


        //TextViews
        monthText = rootView.findViewById(R.id.lbl_toolbar_title);
        allIncome = rootView.findViewById(R.id.current_income);
        allExpense = rootView.findViewById(R.id.current_expense);
        allBalance = rootView.findViewById(R.id.balance_value);
        budgetText = rootView.findViewById(R.id.budget_txt);

        balanceCurrencySymbol = rootView.findViewById(R.id.balanceSymbolCurrency);
        budgetCurrencySymbol = rootView.findViewById(R.id.symbolCurrencyBudget);
        expenseCurrencySymbol = rootView.findViewById(R.id.symbolCurrencyExpense);
        incomeCurrencySymbol = rootView.findViewById(R.id.symbolCurrencyIncome);

        updateSavings = rootView.findViewById(R.id.savings);

        mRecyclerView = rootView.findViewById(R.id.current_recycler);

//        add_new=(ImageView) rootView.findViewById(R.id.add_new);

//        relAppBar=(RelativeLayout) rootView.findViewById(R.id.app_bar);
//        pieChart = rootView.findViewById(R.id.chartExp);
//        pieChart2=rootView.findViewById(R.id.chartInc);
//
//        expenseGraph=rootView.findViewById(R.id.expense_graph);
//        incomeGraph=rootView.findViewById(R.id.income_graph);

        //drawer
        mDrawerLayout = rootView.findViewById(R.id.drawer_layout);
        navigationView = rootView.findViewById(R.id.navigation_view);
        contentView = rootView.findViewById(R.id.content);

        //chart views
        lineChart = rootView.findViewById(R.id.line_chart1);

        open_menu = rootView.findViewById(R.id.btn_menu);
        bmb = rootView.findViewById(R.id.bmb);

        //relative_layout
        addBudget = rootView.findViewById(R.id.enter_budget);
        bmbRelative = rootView.findViewById(R.id.bmb_relative);

        notifications = (ToggleButton) navigationView.getMenu().findItem(R.id.notification_item).getActionView();

        income_btn = rootView.findViewById(R.id.income_linear);
        expense_btn = rootView.findViewById(R.id.expense_linear);

        View header = navigationView.getHeaderView(0);
        accName = header.findViewById(R.id.acc_name);
        accImage = header.findViewById(R.id.acc_img);
        accImageMain = rootView.findViewById(R.id.acc_img_main);

        currentTransaction = rootView.findViewById(R.id.current_transaction);
        noDataTxt = rootView.findViewById(R.id.no_data_txt);
    }

    public void changePrefs() {

        if (prefs.contains(PrefKey.currencySymbol)) {
            Constants.currency_symbols = AppPreference.getInstance(mContext).getString(PrefKey.currencySymbol);
        }

        if (prefs.contains(PrefKey.accountSelection)) {
            Constants.accountId = AppPreference.getInstance(mContext).getInteger(PrefKey.accountSelection);
        }

        if (prefs.contains(PrefKey.enableNotification)) {
            checkNotifications = AppPreference.getInstance(mContext).getBoolean(PrefKey.enableNotification, false);
        }

    }

    private void notificationSwitch() {
        if (checkNotifications) {
            notifications.setChecked(true);
        }
    }

    public void changeCurrencySymbols() {
        balanceCurrencySymbol.setText(Constants.currency_symbols);
        budgetCurrencySymbol.setText(Constants.currency_symbols);
        expenseCurrencySymbol.setText(Constants.currency_symbols);
        incomeCurrencySymbol.setText(Constants.currency_symbols);
    }


    public void setmCategory(List<Categories> categories) {

        mCategory = categories;

    }

    public void getAllCategories() {

        mCategoryViewModel.getAllCategories().observe(getViewLifecycleOwner(), new Observer<List<Categories>>() {
            @Override
            public void onChanged(List<Categories> categories) {


                adapter.setmCategory(categories);

            }
        });

    }


    private void initFunctionality() throws ParseException {

//        if (mActivity != null) {
//            showProgressDialog(mActivity);
//        }


        initializeVars();
        navigationDrawer();
        checkTutorials();
        changePrefs();
        notificationSwitch();
        changeCurrencySymbols();
        setMonth();
        buildRecycler();
        initializeAddBtn();
        populateViewModels();
        getAllDaysInaMonth(current_cat_btn);


    }

    private void checkTutorials() {
        boolean firstRun = AppPreference.getInstance(mContext).getBoolean(PrefKey.SpendingFirstRun, true);
        if (firstRun) {
            tooltip.anchorView(bmbRelative).
                    text(mActivity.getResources().getString(R.string.addTooltip))
                    .gravity(Gravity.TOP)
                    .animated(true)
                    .transparentOverlay(true)
                    .onDismissListener(new SimpleTooltip.OnDismissListener() {
                        @Override
                        public void onDismiss(SimpleTooltip tooltip) {
                            new SimpleTooltip.Builder(mContext)
                                    .anchorView(addBudget)
                                    .text(mActivity.getResources().getString(R.string.budgetTooltip))
                                    .gravity(Gravity.TOP)
                                    .animated(true)
                                    .transparentOverlay(true)
                                    .build()
                                    .show();
                        }
                    })
                    .build()
                    .show();


        }

        AppPreference.getInstance(mContext).setBoolean(PrefKey.SpendingFirstRun, false);

    }

    private void getAllDaysInaMonth(int type) {

        noData = false;

        lineChartEnteries = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        int myMonth = cal.get(Calendar.MONTH);

        while (myMonth == cal.get(Calendar.MONTH)) {

            String xAxisDate = lineChartFormatter.format(cal.getTime());
            String yearMonthDay = lineChartDBFormatter.format(cal.getTime());
            xAxisValues.add(xAxisDate);

            mSpendingViewModel.getDataDayWise(yearMonthDay, type, 1).observe(getViewLifecycleOwner(), new Observer<Float>() {
                @Override
                public void onChanged(Float aFloat) {

                    if (aFloat != null) {
                        Double aDouble = aFloat.doubleValue();
                        lineChartEnteries.add(new BarChartEnteries(aDouble, yearMonthDay));
                        Log.d("checkData", aFloat + " " + yearMonthDay);
                    } else {
                        lineChartEnteries.add(new BarChartEnteries(null, yearMonthDay));
                        Log.d("checkData", aFloat + " " + yearMonthDay);
                    }

                }
            });

            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Collections.sort(lineChartEnteries);

                for (int i = 0; i < lineChartEnteries.size(); i++) {
                    if (lineChartEnteries.get(i).getValue() != null) {
                        noData = true;
                        break;
                    }

                }

                if (noData) {
                    noDataTxt.setVisibility(View.GONE);
                    lineChart.setVisibility(View.VISIBLE);
                    lineChartInitialization(lineChart);
                } else {
                    noDataTxt.setVisibility(View.VISIBLE);
                    lineChart.setVisibility(View.GONE);
                }

            }
        }, 200);


    }


    private void lineChartInitialization(LineChart lineChart) {


        // background color

        lineChart.setBackgroundColor(mActivity.getResources().getColor(R.color.bg_color));

        // disable description text
        lineChart.getDescription().setEnabled(false);

        // enable touch gestures
        lineChart.setTouchEnabled(true);

        // set listeners
        lineChart.setOnChartValueSelectedListener(this);
        lineChart.setDrawGridBackground(false);

        // create marker to display box when values are selected
        MyMarkerView mv = new MyMarkerView(mActivity, R.layout.custom_marker_view);

        // Set the marker to the chart
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);


        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        // chart.setScaleXEnabled(true);
        // chart.setScaleYEnabled(true);
//        lineChart.canScrollHorizontally(0);
        // force pinch zoom along both axis
        lineChart.setPinchZoom(false);


        XAxis xAxis;
        // // X-Axis Style // //
        xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineColor(Color.BLACK);
        xAxis.setAxisLineWidth(1);
        xAxis.setLabelRotationAngle(45);
        xAxis.setTypeface(popsin);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(mActivity.getResources().getColor(R.color.item_bg));
//        lineChart.getAxisLeft().setSpaceTop(35);
        lineChart.getAxisLeft().setXOffset(20);
//        lineChart.getAxisLeft().setYOffset(10);

//        xAxis.setAx
        // vertical grid lines
//        xAxis.enableGridDashedLine(10f, 10f, 0f);


        YAxis yAxis;
        // // Y-Axis Style // //
        yAxis = lineChart.getAxisLeft();
        yAxis.setDrawAxisLine(false);
        // disable dual axis (only use LEFT axis)
        lineChart.getAxisRight().setEnabled(false);

        // horizontal grid lines
        yAxis.enableGridDashedLine(10f, 10f, 0f);
        yAxis.setGridColor(mActivity.getResources().getColor(R.color.item_bg));
        yAxis.setTypeface(popsin);
        yAxis.setTextSize(10f);
        yAxis.setTextColor(mActivity.getResources().getColor(R.color.item_bg));
        // axis range
//        yAxis.setAxisMaximum(200f);
        yAxis.setAxisMinimum(0f);

        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisValues));

        // // Create Limit Lines // //
//        LimitLine llXAxis = new LimitLine(9f, "Index 10");
//        llXAxis.setLineWidth(4f);
//        llXAxis.enableDashedLine(10f, 10f, 0f);
//        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        llXAxis.setTextSize(10f);
//        llXAxis.setTypeface(tfRegular);
//
//        LimitLine ll1 = new LimitLine(150f, "Upper Limit");
//        ll1.setLineWidth(4f);
//        ll1.enableDashedLine(10f, 10f, 0f);
//        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//        ll1.setTextSize(10f);
//        ll1.setTypeface(tfRegular);
//
//        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
//        ll2.setLineWidth(4f);
//        ll2.enableDashedLine(10f, 10f, 0f);
//        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        ll2.setTextSize(10f);
//        ll2.setTypeface(tfRegular);
//
//        // draw limit lines behind data instead of on top
//        yAxis.setDrawLimitLinesBehindData(false);
//        xAxis.setDrawLimitLinesBehindData(true);
//
//        // add limit lines
//        yAxis.addLimitLine(ll1);
//        yAxis.addLimitLine(ll2);
        //xAxis.addLimitLine(llXAxis);


        // add data

        if (Constants.checkDarkMode) {

        }

        setData();
        // draw points over time

        lineChart.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = lineChart.getLegend();

        // draw legend entries as lines

        l.setForm(Legend.LegendForm.NONE);
        lineChart.setVisibleXRange(0, 6);
    }


    private void setData() {

        ArrayList<Entry> values = new ArrayList<>();


        for (int i = 0; i < lineChartEnteries.size(); i++) {

            Double val = lineChartEnteries.get(i).getValue();

            if (val != null) {


                values.add(new Entry(i, lineChartEnteries.get(i).getValue().floatValue(), mContext.getResources().getDrawable(R.drawable.graph_read_line)));
            } else {

                values.add(new Entry(i, 0));
            }

        }


//        values.add(new Entry(0, 10));
//        values.add(new Entry(1, 20));
        LineDataSet set1;

        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "");

            set1.setDrawIcons(true);

            set1.disableDashedLine();
            // draw dashed line
//            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);

            // line thickness and point size
//            set1.setLineWidth(1f);
//            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            set1.setDrawCircles(false);
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            set1.setLineWidth(4f);
            // customize legend entry
//            set1.setFormLineWidth(0f);
//            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
//            set1.setFormSize(15.f);

            // text size of values
            set1.setDrawValues(false);
//            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

//            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(mActivity.getResources().getColor(R.color.item_bg));
//            set1.setColor(Color.rgb(43, 65, 127));

            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return lineChart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
//            if (Utils.getSDKInt() >= 18) {
//                // drawables only supported on api level 18 and above
////                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.account);
////                set1.setFillDrawable(drawable);
//            } else {
//                set1.setFillColor(Color.BLACK);
//            }


            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets


            // create a data object with the data sets
            LineData data = new LineData(dataSets);
            lineChart.setVisibleXRangeMaximum(10);
            // set data
            lineChart.setData(data);
            lineChartEnteries.clear();
            lineChart.invalidate();
        }
    }

    private void initializeVars() {

        mSpendingModel = new ArrayList<>();
        mSpendingModelIncome = new ArrayList<>();
        entries = new ArrayList<>();
        allIncome.setText("0.00");
        allExpense.setText("0.00");
        allBalance.setText("0.00");
        budgetText.setText("0.0");
        budgetId = 0;
        budgetAmount = 0.0;
        allExpense.setText("0.0");
        allIncome.setText("0.0");
        allBalance.setText("0.0");
        getExpVal = 0.0;
        getIncVal = 0.0;
        balanceValue = 0.0;
        savings = 0.0;
        xAxisValues = new ArrayList<>();
    }

    private void DataFromBudgetViewModel() {

        mBudgetViewModel.getBudgetMonthly(passDateInMonth, Constants.accountId).observe(getViewLifecycleOwner(), new Observer<List<Budget>>() {
            @Override
            public void onChanged(List<Budget> budgets) {
                if (budgets.size() != 0) {

                    budgetId = budgets.get(0).getBudgetId();
                    budgetAmount = budgets.get(0).getBudgetAmount();
                    budgetDate = passDateFormat.format(budgets.get(0).getBudgetDate());
                    Constants.accountId = budgets.get(0).getBudgetAccountId();
                    budgetText.setText(budgetAmount + "");

                }
            }
        });

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                upDateBalance();
//            }
//        },50);
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

                if (alert.isShowing()) {
                    alert.dismiss();
                }

            }
        }, 500);
    }


    private void populateViewModels() {

        getAllCategories();

        mSpendingViewModel.getSpending(passDateInMonth, passDateInMonth, Constants.accountId).observe(getViewLifecycleOwner(), new Observer<List<Spending>>() {
            @Override
            public void onChanged(List<Spending> spendings) {

                if (spendings.size() > 0) {
                    currentTransaction.setVisibility(View.VISIBLE);
                } else {
                    currentTransaction.setVisibility(View.GONE);
                }

                adapter.setTransaction(spendings);

            }
        });

        mSpendingViewModel.getAllExpenseMonthlyTotal(passDateInMonth, 1, Constants.accountId).observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {

                if (aDouble != null) {
                    getIncVal = aDouble;
                } else {
                    getIncVal = 0.0;
                    allExpense.setText("0.0");
                    allIncome.setText("0.0");
                    allBalance.setText("0.0");
                    updateSavings.setText("+0% savings");
                }

            }
        });

        mSpendingViewModel.getAllExpenseMonthlyTotal(passDateInMonth, 0, Constants.accountId).observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {

                if (aDouble != null) {
                    getExpVal = aDouble;
                } else {
                    getExpVal = 0.0;
                    allExpense.setText("0.00");
                    allIncome.setText("0.0");
                    allBalance.setText("0.0");
                    updateSavings.setText("+0% savings");
                }
            }
        });

        mAccountViewModel.getAccount(Constants.accountId).observe(getViewLifecycleOwner(), new Observer<List<Accounts>>() {
            @Override
            public void onChanged(List<Accounts> accounts) {
//                Toast.makeText(mActivity, accounts.size()+"", Toast.LENGTH_SHORT).show();
                if (accounts.size() > 0) {
//                    accounts.get(0).getImage()
                    accountName = accounts.get(0).getAccountName();
                    accountBitmap = BitmapManager.byteToBitmap(accounts.get(0).getImage());
                }
            }
        });


        DataFromBudgetViewModel();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                updateExpenseIncome();
                upDateBalance();
                upDateAccountName();
                updateSavings();

            }
        }, 200);
    }

    private void updateSavings() {

        try {
            if (savings != 0.0) {
                String saveMoney = String.format("%.2f", savings) + "% savings";
                updateSavings.setText(saveMoney);
            }

        } catch (Exception ignored) {

        }


    }

    private void upDateAccountName() {


        try {
            accName.setText(accountName);
            accImage.setImageBitmap(accountBitmap);
            accImageMain.setImageBitmap(accountBitmap);
        } catch (Exception ignored) {

        }

    }

    private void updateExpenseIncome() {

        String currentIncAmount = getIncVal.toString();
        String currentExpAmount = getExpVal.toString();
        allIncome.setText(currentIncAmount);
        allExpense.setText(currentExpAmount);
    }

    private void initializeAddBtn() {

        bmb.setNormalColor(Color.WHITE);
        bmb.clearBuilders();
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++)
            bmb.addBuilder(BuilderManager.getTextOutsideCircleButton(mActivity, Constants.Spending_FRAGMENT_Position, Constants.passDateToNext, Constants.accountId));

    }


    private void setGraphData() {

//        entries=new ArrayList<>();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (Categories item : mCategory) {


                    mSpendingViewModel.getLastSixMonthExpCategoryOneByOne(passDateInMonth, passDateInMonth, item.getCategoryId(), 1, Constants.accountId).observe(getViewLifecycleOwner(), new Observer<Float>() {
                        @Override
                        public void onChanged(Float aFloat) {

                            if (aFloat != null) {
                                mSpendingModel.add(new SpendingModel(aFloat, item.getCategoryName()));

                            }
                        }

                    });

                    mSpendingViewModel.getLastSixMonthExpCategoryOneByOne(passDateInMonth, passDateInMonth, item.getCategoryId(), 0, Constants.accountId).observe(getViewLifecycleOwner(), new Observer<Float>() {
                        @Override
                        public void onChanged(Float aFloat) {
                            if (aFloat != null) {
                                mSpendingModelIncome.add(new SpendingModel(aFloat, item.getCategoryName()));
                            }
                        }

                    });


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

//                            if(mSpendingModel.size()==0){
//                                pieChart.setVisibility(View.GONE);
//                            }
//
//                            if(mSpendingModelIncome.size()==0){
//                                pieChart2.setVisibility(View.GONE);
//                            }


                            if (mSpendingModel.size() > 0) {
                                expenseGraph.setVisibility(View.VISIBLE);
//                                pieChart.setVisibility(View.VISIBLE);
//                                setData(pieChart, 0);

                            } else {
                                expenseGraph.setVisibility(View.GONE);
                            }

                            if (mSpendingModelIncome.size() > 0) {
                                incomeGraph.setVisibility(View.VISIBLE);
//                                pieChart2.setVisibility(View.VISIBLE);
//                                setData(pieChart2, 1);
                            } else {
                                incomeGraph.setVisibility(View.GONE);
//                                pieChart2.setVisibility(View.GONE);
                            }


                        }
                    }, 50);
                }
            }
        }, 50);

    }

    private void getAccountsInNumber() {


        mAccountViewModel.getCount().observe((FragmentActivity) mActivity, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {

                number = integer;
                Constants.numberOfAccounts = number;

            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

//                upDateBalance();

//                if(Constants.numberOfAccounts>1){
//                    relAppBar.setVisibility(View.VISIBLE);
//                }
//                else {
//                    relAppBar.setVisibility(View.GONE);
//                }

            }
        }, 50);
    }

    private void upDateBalance() {


        if (budgetAmount != 0.0) {
            balanceValue = budgetAmount.floatValue() - getExpVal;
            savings = getExpVal / budgetAmount.floatValue() * 100;

        } else {
            balanceValue = getIncVal - getExpVal;
            savings = getExpVal / getIncVal * 100;
        }

        String balanceVal = String.valueOf(balanceValue);
        allBalance.setText(balanceVal);


    }

    private void initVar() {
        mActivity = this.getActivity();
        mContext = mActivity.getApplicationContext();
        mCategoryViewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
        mSpendingViewModel = ViewModelProviders.of(this).get(SpendingViewModel.class);
        mBudgetViewModel = ViewModelProviders.of(this).get(BudgetViewModel.class);
        mAccountViewModel = ViewModelProviders.of((FragmentActivity) mActivity).get(AccountViewModel.class);
        tfRegular = Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Regular.ttf");
        tfLight = Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Light.ttf");
        popsin = Typeface.createFromAsset(mContext.getAssets(), "poppins_semibold.ttf");
        prefs = mContext.getSharedPreferences(PrefKey.APP_PREF_NAME, Context.MODE_PRIVATE);
        tooltip = new SimpleTooltip.Builder(mContext);

    }

    private void buildRecycler() {

        adapter = new TransactionAdapter(mContext);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(adapter);

    }


    private void navigationDrawer() {

        //Navigation Drawer
        navigationView.bringToFront();

//        navigationView.setItemIconTintList(null);

        open_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout.isDrawerVisible(GravityCompat.START))
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                else mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.notification_item:

                        break;

                    case R.id.reminder_item:
                        mDrawerLayout.closeDrawers();
                        startActivity(new Intent(mContext, ReminderScreen.class));
                        break;

                    case R.id.settings_item:
                        mDrawerLayout.closeDrawers();
                        startActivity(new Intent(mContext, Settings_Screen.class));
                        break;
                    case R.id.category_item:
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.privacy_item:
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.rate_us_item:
                        mDrawerLayout.closeDrawers();
                        AppUtils.showRatingDialog(mActivity);
                        break;

                }

                return true;
            }
        });

    }

    public void setMonth() {

        try {
            getDateText = dateFormat.format(Constants.c.getTime());
            passDateString = passDateFormat.format(Constants.c.getTime());
            passDateInMonth = monthDateFormatter.format(Constants.c.getTime());
            date = Constants.c.getTime();
        } catch (Exception ignored) {

        }

        Constants.passDateToNext = passDateString;

        monthText.setText(getDateText);

    }

    private void setPieChart(PieChart pieChart) {

        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setCenterTextTypeface(tfLight);
        pieChart.setCenterText("Expense Tracker");

        pieChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);


        pieChart.setEntryLabelColor(Color.WHITE);


        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        pieChart.setOnChartValueSelectedListener(this);

//        seekBarX.setProgress(4);
//        seekBarY.setProgress(10);

        pieChart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);
        pieChart.spin(1000, pieChart.getRotationAngle(), pieChart.getRotationAngle() + 360, Easing.EaseInOutCubic);
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setTextColor(Color.WHITE);

        // entry label styling
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTypeface(tfRegular);
        pieChart.setEntryLabelTextSize(10f);


    }

    private void getPieEnteries(int i) {


        if (i == 0) {
            entries = new ArrayList<>();
            for (SpendingModel spendingModel : mSpendingModel) {
                entries.add(new PieEntry(spendingModel.getAmount(), spendingModel.getCategoryName()));
            }
        } else if (i == 1) {
            entries = new ArrayList<>();
            for (SpendingModel spendingModel : mSpendingModelIncome) {
                entries.add(new PieEntry(spendingModel.getAmount(), spendingModel.getCategoryName()));
            }
        }


    }

//    private void setData(PieChart pieChart, int i) {
//
//
//        getPieEnteries(i);
//
//        PieDataSet dataSet = new PieDataSet(entries, "");
//
//        dataSet.setDrawIcons(false);
//
//        dataSet.setSliceSpace(3f);
//        dataSet.setIconsOffset(new MPPointF(0, 40));
//        dataSet.setSelectionShift(5f);
//
//        // add a lot of colors
//
//        ArrayList<Integer> colors = new ArrayList<>();
//
//
//        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
//        //dataSet.setSelectionShift(0f);
//
//        dataSet.setValueLinePart1OffsetPercentage(80.f);
//        dataSet.setValueLinePart1Length(0.2f);
//        dataSet.setValueLinePart2Length(0.4f);
//
//        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//
//        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(11f);
//        data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(tfLight);
//        pieChart.setData(data);
//
//        // undo all highlights
//        pieChart.highlightValues(null);
//
//        if (pieChart.getMinAngleForSlices() == 0f)
//            pieChart.setMinAngleForSlices(36f);
//        else
//            pieChart.setMinAngleForSlices(0f);
//        pieChart.notifyDataSetChanged();
//        pieChart.invalidate();
//
//
//    }

    private void initListener() {

        nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                initializeVars();


                Constants.c.add(Calendar.MONTH, +1);

                date = Constants.c.getTime();
                try {
                    getDateText = dateFormat.format(Constants.c.getTime());
                    passDateString = passDateFormat.format(Constants.c.getTime());
                    passDateInMonth = monthDateFormatter.format(Constants.c.getTime());
                } catch (Exception ignored) {

                }
                Constants.passDateToNext = passDateString;
                monthText.setText(getDateText);

                populateViewModels();
                getAllDaysInaMonth(current_cat_btn);
//                DataFromBudgetViewModel();


//                setGraphData();


            }
        });

        previousMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initializeVars();


                Constants.c.add(Calendar.MONTH, -1);
                date = Constants.c.getTime();
                try {
                    getDateText = dateFormat.format(Constants.c.getTime());
                    passDateString = passDateFormat.format(Constants.c.getTime());
                    passDateInMonth = monthDateFormatter.format(Constants.c.getTime());
                } catch (Exception ignored) {

                }
                Constants.passDateToNext = passDateString;
                monthText.setText(getDateText);

                populateViewModels();
                getAllDaysInaMonth(current_cat_btn);
//                DataFromBudgetViewModel();

//                setGraphData();

            }
        });

        addBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (budgetId == 0) {
                    ActivityUtils.getInstance().invokeActivity(mActivity, BudgetActivity.class, budgetId, Constants.ADD_NEW_BUDGET, budgetAmount, Constants.passDateToNext, Constants.accountId);
                } else {
                    ActivityUtils.getInstance().invokeActivity(mActivity, BudgetActivity.class, budgetId, Constants.ADD_UPDATE_BUDGET, budgetAmount, budgetDate, Constants.accountId);
                }

            }
        });


        income_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                income_btn.setBackgroundResource(R.drawable.bg_5);
                expense_btn.setBackgroundResource(0);
                current_cat_btn = 1;
                getAllDaysInaMonth(current_cat_btn);


            }
        });

        expense_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                income_btn.setBackgroundResource(0);
                expense_btn.setBackgroundResource(R.drawable.bg_5);
                current_cat_btn = 0;
                getAllDaysInaMonth(current_cat_btn);
            }
        });

        notifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppPreference.getInstance(mContext).setBoolean(PrefKey.enableNotification, isChecked);
            }
        });

        accImageMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.getInstance().invokeActivity(mActivity, MainActivity.class, 4);
            }
        });


    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }


}
