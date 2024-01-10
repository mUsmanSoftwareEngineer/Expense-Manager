package myapps.expensetracker.spendingmanager.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.activities.Add_Category;
import myapps.expensetracker.spendingmanager.adapters.CategoryAdapter;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.data.models.CategoryModel;
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

public class IncomeCategories extends Fragment {

    private Activity mActivity;
    private Context mContext;

    private ArrayList<CategoryModel> arrayList=null;

    private CategoryAdapter adapter;

    private RecyclerView mRecyclerView;

    private CategoriesViewModel mCategoryViewModel;

    ArrayList<Categories> categoriesArrayList=new ArrayList<>();

    private SpendingViewModel mSpendingViewModel;
    int color;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.expense_categories, container, false);

        initView(rootView);
        initFunctionality();
        initListener();

        return rootView;
    }

    private void initFunctionality() {




        Constants.catType=1;

        mCategoryViewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
//        mCategoryViewModel.insert(categories);

        addCategoriesListIntoRoom();


        mCategoryViewModel.getCategoriesByType(1).observe((LifecycleOwner) mActivity, new Observer<List<Categories>>() {
            @Override
            public void onChanged(List<Categories> categories) {
//                adapter.notifyDataSetChanged();
                adapter.setmCategory(categories);
            }
        });
        BuildRecycler();

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
        color=mContext.getResources().getColor(R.color.cat_color_1);

    }

    private void addCategoriesListIntoRoom(){
        boolean firstRun = AppPreference.getInstance(mContext).getBoolean(PrefKey.INCOME_First_RUN,true);

        Drawable d = getResources().getDrawable(R.drawable.money);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        byte[] img= BitmapManager.bitmapToByte(bitmap);

        if (firstRun){
            categoriesArrayList.add(new Categories(getResources().getString(R.string.cat_inc_salary),1,img,color));
            mCategoryViewModel.insertList(categoriesArrayList);
            AppPreference.getInstance(mContext).setBoolean(PrefKey.INCOME_First_RUN,false);
        }

    }

    private void initListener() {

        adapter.setClickListener(new CategoryAdapter.ClickListener() {

            @Override
            public void onItemClicked(int position, int id, String categoryName, int catColor) {
                if(Constants.adapterPosition==0){

                    ActivityUtils.getInstance().startActivity(mActivity, Add_Category.class, Constants.UPDATE_INCOME_CATEGORIES_FRAGMENT,id,categoryName,catColor);

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
            }

            @Override
            public void onItemLongClicked(int categoryId, String category_name) {
                DialogUtils.showDialogPrompt(mActivity, null, getString(R.string.delete_category_item),
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



    private void BuildRecycler() {

        adapter = new CategoryAdapter(mContext);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(adapter);

    }
}
