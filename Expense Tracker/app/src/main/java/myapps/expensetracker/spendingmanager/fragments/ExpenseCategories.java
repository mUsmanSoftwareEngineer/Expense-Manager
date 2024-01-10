package myapps.expensetracker.spendingmanager.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.activities.Add_Category;
import myapps.expensetracker.spendingmanager.adapters.CategoryAdapter;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.data.preference.AppPreference;
import myapps.expensetracker.spendingmanager.data.preference.PrefKey;
import myapps.expensetracker.spendingmanager.entity.Categories;
import myapps.expensetracker.spendingmanager.entity.Spending;
import myapps.expensetracker.spendingmanager.libs.BitmapManager;
import myapps.expensetracker.spendingmanager.utilities.ActivityUtils;
import myapps.expensetracker.spendingmanager.utilities.DialogUtils;
import myapps.expensetracker.spendingmanager.viewmodel.CategoriesViewModel;
import myapps.expensetracker.spendingmanager.viewmodel.SpendingViewModel;

import static android.app.Activity.RESULT_OK;

public class ExpenseCategories extends Fragment {

    private Activity mActivity;
    private Context mContext;

    private ArrayList<Categories> arrayList=null;

    private CategoryAdapter adapter;

    private RecyclerView mRecyclerView;

    private CategoriesViewModel mCategoryViewModel;

    ArrayList<Categories> categoriesArrayList=new ArrayList<>();

    private SpendingViewModel mSpendingViewModel;

    ArrayList<Integer> drawableArrayList=new ArrayList<>();
    ArrayList<Bitmap> bitmapArrayList=new ArrayList<>();

    Drawable drawable1,drawable2,drawable3,drawable4,drawable5,drawable6,drawable7,drawable8;
    Bitmap bMpa1,bMpa2,bMpa3,bMpa4,bMpa5,bMpa6,bMpa7,bMpa8;
    byte[] byte1,byte2,byte3,byte4,byte5,byte6,byte7,byte8;
    int color1,color2,color3,color4,color5,color6,color7,color8,color9;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVar();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.expense_categories, container, false);

        initView(rootView);
        initFunctionality(rootView);
        initListener();

        return rootView;
    }

    private void initFunctionality(View rootView) {


//        Log.d("accountUser001",Constants.countUser+"");
//        Log.d("IncomeUser001",Constants.getIncome+"");

        checkCategoryTooltip(rootView);
        BuildRecycler();


        Constants.catType=0;



        addCategoriesListIntoRoom();


        mCategoryViewModel.getCategoriesByType(0).observe((LifecycleOwner) mActivity, new Observer<List<Categories>>() {
            @Override
            public void onChanged(List<Categories> categories) {
                adapter.setmCategory(categories);
            }
        });

//        Log.d("checkArrayListValue",arrayList.size()+"");

    }

    private void checkCategoryTooltip(View rootView) {
        boolean firstRun = AppPreference.getInstance(mContext).getBoolean(PrefKey.CategoryFirstRun, true);

        if (firstRun) {
            new SimpleTooltip.Builder(mContext)
                    .anchorView(mRecyclerView.getRootView())
                    .text(R.string.category_center)
                    .gravity(Gravity.CENTER)
                    .animated(true)
                    .dismissOnInsideTouch(true)
                    .build()
                    .show();
            AppPreference.getInstance(mContext).setBoolean(PrefKey.CategoryFirstRun, false);
        }
    }

    private void initVar() {
        mActivity = getActivity();
        mContext = mActivity.getApplicationContext();
        mSpendingViewModel = ViewModelProviders.of(this).get(SpendingViewModel.class);
        mCategoryViewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
        arrayList=new ArrayList<>();
    }

    private void initView(View rootView) {

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.category_recycler);
        color1=mContext.getResources().getColor(R.color.cat_color_2);
        color2=mContext.getResources().getColor(R.color.cat_color_3);
        color3=mContext.getResources().getColor(R.color.cat_color_4);
        color4=mContext.getResources().getColor(R.color.cat_color_5);
        color5=mContext.getResources().getColor(R.color.cat_color_6);
        color6=mContext.getResources().getColor(R.color.cat_color_7);
        color7=mContext.getResources().getColor(R.color.cat_color_8);
        color8=mContext.getResources().getColor(R.color.cat_color_9);
        color9=mContext.getResources().getColor(R.color.cat_color_10);

    }


    private void initListener() {


        adapter.setClickListener(new CategoryAdapter.ClickListener() {


            @Override
            public void onItemClicked(int position, int id, String categoryName, int catColor) {

                if(Constants.adapterPosition==0){

                    ActivityUtils.getInstance().startActivity(mActivity, Add_Category.class, Constants.UPDATE_EXPENSE_CATEGORIES_FRAGMENT,id,categoryName,catColor);
//                    Toast.makeText(mActivity, "Categories Fragment", Toast.LENGTH_SHORT).show();
                }
                else if(Constants.adapterPosition==1){
                    Intent intent=new Intent();
                    intent.putExtra("categoryName", categoryName);
                    intent.putExtra("categoryId", id);
                    mActivity.setResult(RESULT_OK,intent);
                    mActivity.finish();

                }
            }

            @Override
            public void onItemDeleteClicked(int layoutPosition, int cat_id, String category_name) {
                Categories categories = new Categories();
                categories.setCategoryId(cat_id);
                mCategoryViewModel.delete(categories);
                Spending spending=new Spending();
                spending.setSpendingAccountHolderId(cat_id);
                mSpendingViewModel.deleteSingleCategory(cat_id);
            }

            @Override
            public void onItemLongClicked(int categoryId, String category_name) {

                DialogUtils.showDialogPrompt(mActivity, null, getString(R.string.delete_transaction_item),
                        getString(R.string.yes), getString(R.string.no), true, new DialogUtils.DialogActionListener() {
                            @Override
                            public void onPositiveClick() {
                                Categories categories = new Categories();
                                categories.setCategoryId(categoryId);
                                mCategoryViewModel.delete(categories);
                                Spending spending=new Spending();
                                spending.setSpendingAccountHolderId(categoryId);
                                mSpendingViewModel.deleteSingleCategory(categoryId);
                            }
                        });

            }




        });

    }




    private void addImages(){

        drawable1 = getResources().getDrawable(R.drawable.clothes);
        bMpa1 = ((BitmapDrawable)drawable1).getBitmap();
        byte1= BitmapManager.bitmapToByte(bMpa1);

        drawable2 = getResources().getDrawable(R.drawable.fuel);
        bMpa2 = ((BitmapDrawable)drawable2).getBitmap();
        byte2= BitmapManager.bitmapToByte(bMpa2);

        drawable3 = getResources().getDrawable(R.drawable.cd_player);
        bMpa3 = ((BitmapDrawable)drawable3).getBitmap();
        byte3= BitmapManager.bitmapToByte(bMpa3);

        drawable4 = getResources().getDrawable(R.drawable.wardrobe);
        bMpa4 = ((BitmapDrawable)drawable4).getBitmap();
        byte4= BitmapManager.bitmapToByte(bMpa4);

        drawable5 = getResources().getDrawable(R.drawable.train);
        bMpa5 = ((BitmapDrawable)drawable5).getBitmap();
        byte5= BitmapManager.bitmapToByte(bMpa5);

        drawable6 = getResources().getDrawable(R.drawable.spa);
        bMpa6 = ((BitmapDrawable)drawable6).getBitmap();
        byte6= BitmapManager.bitmapToByte(bMpa6);

        drawable7 = getResources().getDrawable(R.drawable.travel);
        bMpa7 = ((BitmapDrawable)drawable7).getBitmap();
        byte7= BitmapManager.bitmapToByte(bMpa7);

        drawable8 = getResources().getDrawable(R.drawable.led_tv);
        bMpa8 = ((BitmapDrawable)drawable8).getBitmap();
        byte8= BitmapManager.bitmapToByte(bMpa8);

    }

    private void addCategoriesListIntoRoom(){

        boolean firstRun = AppPreference.getInstance(mContext).getBoolean(PrefKey.First_RUN,true);

        if (firstRun){

           addImages();



            categoriesArrayList.add(new Categories(getResources().getString(R.string.cat_exp_clothes),0,byte1,color1));
            categoriesArrayList.add(new Categories(getResources().getString(R.string.cat_exp_fuel),0,byte2,color2));
            categoriesArrayList.add(new Categories(getResources().getString(R.string.cat_exp_entertainment),0,byte3,color3));
            categoriesArrayList.add(new Categories(getResources().getString(R.string.cat_exp_general),0,byte4,color4));
            categoriesArrayList.add(new Categories(getResources().getString(R.string.cat_exp_holidays),0,byte5,color5));
            categoriesArrayList.add(new Categories(getResources().getString(R.string.cat_exp_sports),0,byte6,color6));
            categoriesArrayList.add(new Categories(getResources().getString(R.string.cat_exp_travel),0,byte7,color7));
            categoriesArrayList.add(new Categories(getResources().getString(R.string.cat_exp_gifts),0,byte8,color8));
            mCategoryViewModel.insertList(categoriesArrayList);
            AppPreference.getInstance(mContext).setBoolean(PrefKey.First_RUN,false);

        }
    }


    private void BuildRecycler() {

        adapter = new CategoryAdapter(mContext);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setDrawingCacheEnabled(true);
        adapter.notifyDataSetChanged();

    }


}
