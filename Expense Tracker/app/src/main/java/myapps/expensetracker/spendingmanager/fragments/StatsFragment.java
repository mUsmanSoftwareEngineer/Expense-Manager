package myapps.expensetracker.spendingmanager.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.activities.MainActivity;
import myapps.expensetracker.spendingmanager.adapters.InvoiceAdapter;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.data.models.BarChartEnteries;
import myapps.expensetracker.spendingmanager.data.models.SpendingModel;
import myapps.expensetracker.spendingmanager.entity.Categories;
import myapps.expensetracker.spendingmanager.viewmodel.CategoriesViewModel;
import myapps.expensetracker.spendingmanager.viewmodel.SpendingViewModel;

//import myapps.expensetracker.spendingmanager.adapters.HistoryAdapter;


public class StatsFragment extends Fragment implements OnChartValueSelectedListener {

    protected final String[] parties = new String[]{
            "Expenses", "Income"
    };
    final int[] MY_COLORS = {Color.rgb(192, 0, 0), Color.rgb(255, 0, 0), Color.rgb(255, 192, 0), Color.rgb(127, 127, 127), Color.rgb(146, 208, 80), Color.rgb(0, 176, 80), Color.rgb(79, 129, 189)};
    private final boolean pieCheck = false;
    protected Typeface tfRegular;
    protected Typeface tfLight, popsin;
    Activity mActivity;
    Context mContext;
    BarChart chart;
    BarChart barChart;
    ArrayList<IBarDataSet> dataSets = new ArrayList<>();
    float defaultBarWidth = -1;
    Float[] arrIncome = new Float[12];
    Double[] arrExpense = new Double[12];
    Float sumIncomeLastSixMonths = 0f;
    Double sumExpenseLastSixMonths = 0.0;
    List<SpendingModel> mSpendingModel = new ArrayList<>();
    List<SpendingModel> mSpendingModelIncome = new ArrayList<>();
    List<Float> getMonthlyIncome = new ArrayList<>();
    List<Double> getMonthlyExpense = new ArrayList<>();
    List<Double> getDetailedExpensed = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<String>();
    ArrayList<String> Yearlylabels = new ArrayList<String>();
    List<String> xAxisValues = new ArrayList<>(Arrays.asList("Jan", "Feb", "March", "April", "May", "June", "July", "August", "September", "October", "November", "Decemeber"));
    MaterialSpinner spinner;
    String startDate, endDate;
    Spinner simpleSpinner_type, mainSpinner, incomeExpenseSpinner;
    ArrayList<PieEntry> entries = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    SimpleDateFormat sdf1 = new SimpleDateFormat("MMM");
    InvoiceAdapter adapter;
    RecyclerView mRecyclerView, incomeRecyclerView;
    String[] Spinner = {"Last 3 months", "Last 6 Months", "Last 12 Months"};
    String[] PieSpinner = {"Expense", "Income"};
    String[] filterSpinner = {"Monthly", "Yearly"};
    TextView totalExpense, totalIncome, toolbarTitle, totalSavings;
    List<Integer> colorList = new ArrayList<>();
    List<BarEntry> expenseEntries = new ArrayList<>();
    List<BarEntry> incomeEntries = new ArrayList<>();
    List<BarChartEnteries> expenseBarChartEnteries = new ArrayList<>();
    List<BarChartEnteries> incomebarChartEnteries = new ArrayList<>();
    float total_income, total_expense, savings;
    TextView currencySymbolSaving, totalExpCurrencySymbolStats, totalIncCurrencySymbolStats;
    ImageView back;
    TextView noData, noDataPieTxt, categoriesTxt;
    private SpendingViewModel mSpendingViewModel;
    private PieChart pieChart, pieChart2, pieChart3;
    private CategoriesViewModel mCategoryViewModel;
    private List<Categories> mCategory = new ArrayList<>();
    private boolean expenseBarCheck = false;
    private boolean incomeBarCheck = false;
    private LinearLayout income_btn, expense_btn;
    private int current_cat_btn = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);

        initView(rootView);

        initFunctionality();
        initListener();

        return rootView;
    }

    private void initFunctionality() {

        setCurrencySymbols();
        getAllCategories();
        setPieChart(pieChart3);
        SpinnerFilter();


    }


    private void SpinnerFilter() {

        ArrayAdapter arrayAdapter = new ArrayAdapter(mContext, R.layout.spinner_text, this.filterSpinner);
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        this.mainSpinner.setAdapter(arrayAdapter);
        this.mainSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onNothingSelected(AdapterView<?> adapterView) {

            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {

                if (i == 0) {
                    Spinner = new String[]{"Last 3 months", "Last 6 Months", "Last 12 Months"};
                    SpinnerData();
                } else if (i == 1) {
                    Spinner = new String[]{"Current year", "Last Year"};
                    YearSpinnerData();
                }

            }
        });
    }


    public void initVariables() {

        expenseBarChartEnteries = new ArrayList<>();
        incomebarChartEnteries = new ArrayList<>();
        expenseEntries = new ArrayList<>();
        incomeEntries = new ArrayList<>();
        entries = new ArrayList<>();
        mSpendingModel = new ArrayList<>();
        colorList = new ArrayList<>();
        labels = new ArrayList<>();

    }


    private void setData(PieChart pieChart) {


        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        dataSet.setSliceSpace(0f);

        // add a lot of colors

//        ArrayList<Integer> colors = new ArrayList<>();
//
//
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);

//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);

//        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colorList);

//        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        //dataSet.setSelectionShift(0f);
//        dataSet.setColors(colors);


        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);

        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        dataSet.setUsingSliceColorAsValueLineColor(true);


        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12f);

        data.setValueTextColor(mActivity.getResources().getColor(R.color.blackish_txt));


        data.setValueTypeface(popsin);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        if (pieChart.getMinAngleForSlices() == 0f)
            pieChart.setMinAngleForSlices(36f);
        else
            pieChart.setMinAngleForSlices(0f);
        pieChart.notifyDataSetChanged();
        pieChart.invalidate();

//        Log.d("checkPieColors",colors.get(0).intValue()+"");
//        pieChart.invalidate();

    }


    private void initVar() {


        mActivity = getActivity();
        mContext = mActivity.getApplicationContext();

        tfRegular = Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Regular.ttf");
        tfLight = Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Light.ttf");
        popsin = Typeface.createFromAsset(mContext.getAssets(), "poppins_semibold.ttf");
        mSpendingViewModel = ViewModelProviders.of(this).get(SpendingViewModel.class);
        mCategoryViewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);


    }

    public void getAllCategories() {

        mCategoryViewModel.getAllCategories().observe(getViewLifecycleOwner(), new Observer<List<Categories>>() {
            @Override
            public void onChanged(List<Categories> categories) {

                setmCategory(categories);

            }
        });

    }

    public void setmCategory(List<Categories> categories) {

        mCategory = categories;

    }

    private void initView(View rootView) {

        barChart = rootView.findViewById(R.id.chart1);
        pieChart = rootView.findViewById(R.id.chart2);
        pieChart2 = rootView.findViewById(R.id.chart3);
        pieChart3 = rootView.findViewById(R.id.chart4);

        simpleSpinner_type = rootView.findViewById(R.id.simpleSpinner_type);
        mainSpinner = rootView.findViewById(R.id.filter_spinner);


        mRecyclerView = rootView.findViewById(R.id.invoice_recycler);
        incomeRecyclerView = rootView.findViewById(R.id.inc_invoice_recycler);

        totalExpense = rootView.findViewById(R.id.total_expense);
        totalIncome = rootView.findViewById(R.id.total_income);
        totalSavings = rootView.findViewById(R.id.balance_value);

        toolbarTitle = rootView.findViewById(R.id.iconTxt);

        currencySymbolSaving = rootView.findViewById(R.id.symbolCurrency);
        totalExpCurrencySymbolStats = rootView.findViewById(R.id.expesnseStatsSymbol);
        totalIncCurrencySymbolStats = rootView.findViewById(R.id.IncStatsSymbol);

        back = rootView.findViewById(R.id.back);
        noData = rootView.findViewById(R.id.no_data_txt);
        noDataPieTxt = rootView.findViewById(R.id.no_data_txt_pie);
        categoriesTxt = rootView.findViewById(R.id.categories_txt);

        income_btn = rootView.findViewById(R.id.income_linear);
        expense_btn = rootView.findViewById(R.id.expense_linear);
    }

    private void buildRecycler(List<SpendingModel> mSpendingModel, RecyclerView mRecyclerView) {

        adapter = new InvoiceAdapter(mContext, mSpendingModel);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void setCurrencySymbols() {

        currencySymbolSaving.setText(Constants.currency_symbols);
        totalExpCurrencySymbolStats.setText(Constants.currency_symbols);
        totalIncCurrencySymbolStats.setText(Constants.currency_symbols);

    }


    private void SpinnerData() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(mContext, R.layout.spinner_text, this.Spinner);
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        this.simpleSpinner_type.setAdapter(arrayAdapter);
        this.simpleSpinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {


                if (i == 0) {
                    initVariables();
                    getData(3);
                } else if (i == 1) {
                    initVariables();
                    getData(6);
                } else if (i == 2) {

                    initVariables();
                    getData(12);

                }

            }
        });
    }


    private void YearSpinnerData() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(mContext, R.layout.spinner_text, this.Spinner);
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        this.simpleSpinner_type.setAdapter(arrayAdapter);
        this.simpleSpinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {

                if (i == 0) {
                    initVariables();
                    getDataCurrentYear(12);
                } else if (i == 1) {
                    initVariables();
                    getData(12);
                }


            }
        });
    }

    private void initListener() {


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, MainActivity.class));
                mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        income_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                income_btn.setBackgroundResource(R.drawable.bg_5);
                expense_btn.setBackgroundResource(0);
                current_cat_btn = 1;
                mSpendingModel = new ArrayList<>();
                colorList = new ArrayList<>();
                entries = new ArrayList<>();
                Log.d("checkdates",startDate+"\n"+endDate+"\n"+current_cat_btn);
                getPieData(startDate, endDate, current_cat_btn, Constants.accountId);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (SpendingModel spendingModel : mSpendingModel) {
                            colorList.add(spendingModel.getCategoryColor());
                            entries.add(new PieEntry(spendingModel.getAmount(), spendingModel.getCategoryName()));
                        }

                        if (mSpendingModel.size() > 0) {
                            categoriesTxt.setVisibility(View.VISIBLE);
                            noDataPieTxt.setVisibility(View.GONE);
                            pieChart3.setVisibility(View.VISIBLE);
                            setData(pieChart3);
                        } else {
                            categoriesTxt.setVisibility(View.GONE);
                            noDataPieTxt.setVisibility(View.VISIBLE);
                            pieChart3.setVisibility(View.GONE);
                        }

                        buildRecycler(mSpendingModel, mRecyclerView);
                    }
                },100);

            }
        });

        expense_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                income_btn.setBackgroundResource(0);
                expense_btn.setBackgroundResource(R.drawable.bg_5);
                current_cat_btn = 0;
                mSpendingModel = new ArrayList<>();
                colorList = new ArrayList<>();
                entries = new ArrayList<>();
                Log.d("checkdates",startDate+"\n"+endDate+"\n"+current_cat_btn);
                getPieData(startDate, endDate, current_cat_btn, Constants.accountId);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (SpendingModel spendingModel : mSpendingModel) {
                            colorList.add(spendingModel.getCategoryColor());
                            entries.add(new PieEntry(spendingModel.getAmount(), spendingModel.getCategoryName()));
                        }

                        if (mSpendingModel.size() > 0) {
                            categoriesTxt.setVisibility(View.VISIBLE);
                            noDataPieTxt.setVisibility(View.GONE);
                            pieChart3.setVisibility(View.VISIBLE);
                            setData(pieChart3);
                        } else {
                            categoriesTxt.setVisibility(View.GONE);
                            noDataPieTxt.setVisibility(View.VISIBLE);
                            pieChart3.setVisibility(View.GONE);
                        }

                        buildRecycler(mSpendingModel, mRecyclerView);
                    }
                },100);
            }
        });

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }


    public void getDataCurrentYear(int size) {


        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 0);

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.MONTH, 0);

        Date date2 = cal2.getTime();

        Date date = cal.getTime();
        String[] days = new String[size];
        String[] month = new String[size];
        month[0] = sdf1.format(date);
        days[0] = sdf.format(date);

        String[] days2 = new String[size];
        String[] month2 = new String[size];
        month2[0] = sdf1.format(date);
        days2[0] = sdf.format(date);

        endDate = days2[0];
        cal2.add(Calendar.MONTH, (size - 1));
        date2 = cal2.getTime();
        startDate = sdf.format(date2);


        for (int i = 1; i < size; i++) {

            cal.add(Calendar.MONTH, +1);
            date = cal.getTime();
            days[i] = sdf.format(date);
            month[i] = sdf1.format(date);

        }

        Collections.addAll(labels, month);


        for (String x : days) {

            mSpendingViewModel.getAllExpenseMonthlyTotal(x, 0, Constants.accountId).observe(getViewLifecycleOwner(), new Observer<Double>() {
                @Override
                public void onChanged(Double aDouble) {
                    expenseBarChartEnteries.add(new BarChartEnteries(aDouble, x));

                }

            });

            mSpendingViewModel.getAllExpenseMonthlyTotal(x, 1, Constants.accountId).observe(getViewLifecycleOwner(), new Observer<Double>() {
                @Override
                public void onChanged(Double aDouble) {
                    incomebarChartEnteries.add(new BarChartEnteries(aDouble, x));
                }
            });
        }


        mSpendingViewModel.getTotalExpense(endDate, startDate, 0, Constants.accountId).observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                if (aFloat != null) {
                    totalExpense.setText(aFloat + "");
                    total_expense = aFloat;
                }

            }


        });

        mSpendingViewModel.getTotalExpense(endDate, startDate, 1, Constants.accountId).observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                if (aFloat != null) {
                    totalIncome.setText(aFloat + "");
                    total_income = aFloat;
                }
            }
        });

        for (Categories item : mCategory) {

            mSpendingViewModel.getLastSixMonthExpCategoryOneByOne(endDate, startDate, item.getCategoryId(), 0, Constants.accountId).observe(getViewLifecycleOwner(), new Observer<Float>() {
                @Override
                public void onChanged(Float aFloat) {

                    if (aFloat != null) {

                        Calendar calendar = Calendar.getInstance();
                        long time = calendar.getTimeInMillis();

                        mSpendingModel.add(new SpendingModel(item.getCategoryId(), aFloat, item.getCategoryName(), item.getCategoryColour(), item.getCategoryImage(), time));

                    }
                }

            });


        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                Collections.sort(expenseBarChartEnteries);
                Collections.reverse(expenseBarChartEnteries);
                Collections.sort(incomebarChartEnteries);
                Collections.reverse(incomebarChartEnteries);


                for (int i = 0; i < expenseBarChartEnteries.size(); i++) {
                    if (expenseBarChartEnteries.get(i).getValue() != null) {
                        expenseEntries.add(new BarEntry(i + 1, expenseBarChartEnteries.get(i).getValue().floatValue()));
                    } else {
                        expenseEntries.add(new BarEntry(i + 1, null));
                    }
                }

                for (int i = 0; i < incomebarChartEnteries.size(); i++) {
                    if (incomebarChartEnteries.get(i).getValue() != null) {
                        incomeEntries.add(new BarEntry(i + 1, incomebarChartEnteries.get(i).getValue().floatValue()));
                    } else {
                        incomeEntries.add(new BarEntry(i + 1, null));
                    }
                }

                for (SpendingModel spendingModel : mSpendingModel) {
                    colorList.add(spendingModel.getCategoryColor());
                    entries.add(new PieEntry(spendingModel.getAmount(), spendingModel.getCategoryName()));
                }


                upDateSavings(total_expense, total_income);
                setChart(size);
                setData(pieChart3);
                buildRecycler(mSpendingModel, mRecyclerView);
            }
        }, 100);


    }

    public void getData(int size) {

        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        Date date = cal.getTime();
        Date date2 = cal2.getTime();

        String[] days = new String[size];
        String[] month = new String[size];
        month[0] = sdf1.format(date);
        days[0] = sdf.format(date);

        String[] days2 = new String[size];
        String[] month2 = new String[size];
        month2[0] = sdf1.format(date);
        days2[0] = sdf.format(date);

        endDate = days2[0];
        cal2.add(Calendar.MONTH, -(size - 1));
        date2 = cal2.getTime();
        startDate = sdf.format(date2);

        for (int i = 1; i < size; i++) {

            cal.add(Calendar.MONTH, -1);
            date = cal.getTime();
            days[i] = sdf.format(date);
            month[i] = sdf1.format(date);

        }

        Collections.addAll(labels, month);

        for (String x : days) {

            mSpendingViewModel.getAllExpenseMonthlyTotal(x, 0, Constants.accountId).observe(getViewLifecycleOwner(), new Observer<Double>() {
                @Override
                public void onChanged(Double aDouble) {
                    expenseBarChartEnteries.add(new BarChartEnteries(aDouble, x));

                }

            });

            mSpendingViewModel.getAllExpenseMonthlyTotal(x, 1, Constants.accountId).observe(getViewLifecycleOwner(), new Observer<Double>() {
                @Override
                public void onChanged(Double aDouble) {
                    incomebarChartEnteries.add(new BarChartEnteries(aDouble, x));
                }
            });


        }

        mSpendingViewModel.getTotalExpense(startDate, endDate, 0, Constants.accountId).observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                if (aFloat != null) {
                    totalExpense.setText(aFloat + "");
                    total_expense = aFloat;
                }
            }
        });


        mSpendingViewModel.getTotalExpense(startDate, endDate, 1, Constants.accountId).observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                if (aFloat != null) {
                    totalIncome.setText(aFloat + "");
                    total_income = aFloat;
                }
            }
        });


        Log.d("checkCurrentCatBtn", current_cat_btn + "");
        getPieData(startDate, endDate, current_cat_btn, Constants.accountId);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                Collections.sort(expenseBarChartEnteries);
                Collections.reverse(expenseBarChartEnteries);
                Collections.sort(incomebarChartEnteries);
                Collections.reverse(incomebarChartEnteries);


                for (int i = 0; i < expenseBarChartEnteries.size(); i++) {
                    if (expenseBarChartEnteries.get(i).getValue() != null) {
                        expenseEntries.add(new BarEntry(i + 1, expenseBarChartEnteries.get(i).getValue().floatValue()));
                    } else {
                        expenseEntries.add(new BarEntry(i + 1, null));
                    }
                }

                for (int i = 0; i < incomebarChartEnteries.size(); i++) {
                    if (incomebarChartEnteries.get(i).getValue() != null) {
                        incomeEntries.add(new BarEntry(i + 1, incomebarChartEnteries.get(i).getValue().floatValue()));
                    } else {
                        incomeEntries.add(new BarEntry(i + 1, null));
                    }
                }


                for (SpendingModel spendingModel : mSpendingModel) {
                    colorList.add(spendingModel.getCategoryColor());
                    entries.add(new PieEntry(spendingModel.getAmount(), spendingModel.getCategoryName()));
                }


                for (BarEntry barEntry : expenseEntries) {
                    if (barEntry.getY() != 0.0) {
                        expenseBarCheck = true;
                        break;
                    }
                }

                for (BarEntry barEntry : incomeEntries) {
                    if (barEntry.getY() != 0.0) {
                        incomeBarCheck = true;
                        break;
                    }
                }

                if (expenseBarCheck || incomeBarCheck) {
                    noData.setVisibility(View.GONE);
                    barChart.setVisibility(View.VISIBLE);
                    setChart(size);
                }


                if (mSpendingModel.size() > 0) {
                    categoriesTxt.setVisibility(View.VISIBLE);
                    noDataPieTxt.setVisibility(View.GONE);
                    pieChart3.setVisibility(View.VISIBLE);
                    setData(pieChart3);
                } else {
                    categoriesTxt.setVisibility(View.GONE);
                    noDataPieTxt.setVisibility(View.VISIBLE);
                    pieChart3.setVisibility(View.GONE);
                }

                upDateSavings(total_expense, total_income);
                buildRecycler(mSpendingModel, mRecyclerView);
            }
        }, 100);


    }


    public void upDateSavings(float expense, float income) {

        if (expense != 0 && income != 0) {

            savings = income - expense;
            totalSavings.setText(savings + "");
        }

    }


    private void getPieData(String startingDate, String endingDate, int categoryType, int accountID) {
        for (Categories item : mCategory) {

            mSpendingViewModel.getLastSixMonthExpCategoryOneByOne(startingDate, endingDate, item.getCategoryId(), categoryType, accountID).observe(getViewLifecycleOwner(), new Observer<Float>() {
                @Override
                public void onChanged(Float aFloat) {

                    if (aFloat != null) {

                        Calendar calendar = Calendar.getInstance();
                        long time = calendar.getTimeInMillis();

                        mSpendingModel.add(new SpendingModel(item.getCategoryId(), aFloat, item.getCategoryName(), item.getCategoryColour(), item.getCategoryImage(), time));


                    }
                }

            });


        }
    }

    private void setPieChart(PieChart pieChart) {

        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setCenterTextTypeface(tfLight);
        pieChart.setCenterText("");

        pieChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setDrawSliceText(false);
        pieChart.setEntryLabelColor(Color.RED);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(48f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setUsePercentValues(false);


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
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);


        // entry label styling

        l.setTextColor(mActivity.getResources().getColor(R.color.blackish_txt));
        l.setTypeface(popsin);
        l.setTextSize(12f);
        pieChart.setEntryLabelColor(mActivity.getResources().getColor(R.color.blackish_txt));
        pieChart.setEntryLabelTypeface(popsin);
        pieChart.setEntryLabelTextSize(10f);

    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }


    private void setChart(int size) {

//        List<BarEntry> incomeEntries = getIncomeEntries(size);
//         expenseEntries = getExpenseEntries(size);
        dataSets = new ArrayList<>();
        BarDataSet set1, set2;

        set1 = new BarDataSet(incomeEntries, "Income");
        set1.setColor(Color.rgb(47, 73, 147));

        set1.setValueTextColor(Color.rgb(55, 70, 73));
        set1.setValueTextSize(10f);

        set2 = new BarDataSet(expenseEntries, "Expense");
        set2.setColors(Color.rgb(241, 107, 72));
        set2.setValueTextColor(Color.rgb(55, 70, 73));
        set2.setValueTextSize(10f);

        dataSets.add(set1);
        dataSets.add(set2);


        BarData data = new BarData(dataSets);
        barChart.setData(data);
        data.setValueTextColor(mActivity.getResources().getColor(R.color.blackish_txt));
        data.setValueTextSize(10f);
        data.setValueTypeface(popsin);
        barChart.getAxisLeft().setAxisMinimum(0);

        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setAxisMinimum(0);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(10);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        barChart.setVerticalScrollBarEnabled(false);
        barChart.setHorizontalScrollBarEnabled(false);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.animateXY(2000, 2000);

        MyMarkerView mv = new MyMarkerView(mContext, R.layout.custom_marker_view);
        mv.setChartView(chart); // For bounds control
        barChart.setMarker(mv);

        Legend l = barChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setTextSize(14);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setTypeface(popsin);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMaximum(expenseEntries.size());
        xAxis.setTextSize(12f);
        xAxis.setTypeface(popsin);
        barChart.getAxisLeft().setXOffset(20);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.setTypeface(Typeface.DEFAULT);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setTextColor(mActivity.getResources().getColor(R.color.blackish_txt));
        l.setTextColor(mActivity.getResources().getColor(R.color.blackish_txt));
        xAxis.setTextColor(mActivity.getResources().getColor(R.color.blackish_txt));

        leftAxis.setDrawGridLines(true);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setGridColor(mActivity.getResources().getColor(R.color.blackish_txt));
        leftAxis.setTextSize(12f);
        leftAxis.setTypeface(popsin);
        leftAxis.setDrawAxisLine(false);
//        leftAxis.setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
        if (size == 12) {
            barChart.setVisibleXRange(0, 6);
        }
        setBarWidth(data, size);
        barChart.invalidate();

    }

    private void setBarWidth(BarData barData, int size) {
        if (dataSets.size() > 1) {
            float barSpace = 0.02f;
            float groupSpace = 0.3f;
            defaultBarWidth = (1 - groupSpace) / dataSets.size() - barSpace;
            if (defaultBarWidth >= 0) {
                barData.setBarWidth(defaultBarWidth);
            } else {
//                Toast.makeText(mContext, "Default Barwdith " + defaultBarWidth, Toast.LENGTH_SHORT).show();
            }
            int groupCount = size;
            if (groupCount != -1) {
                barChart.getXAxis().setAxisMinimum(0);
                barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
                barChart.getXAxis().setCenterAxisLabels(true);
            } else {
//                Toast.makeText(mContext, "no of bar groups is " + groupCount, Toast.LENGTH_SHORT).show();
            }

            barChart.groupBars(0, groupSpace, barSpace); // perform the "explicit" grouping
            barChart.invalidate();
        }
    }


}
