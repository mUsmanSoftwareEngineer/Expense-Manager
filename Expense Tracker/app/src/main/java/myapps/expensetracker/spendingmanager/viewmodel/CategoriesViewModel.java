package myapps.expensetracker.spendingmanager.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;



import java.util.List;

import myapps.expensetracker.spendingmanager.database.CategoryRepository;
import myapps.expensetracker.spendingmanager.entity.Categories;

public class CategoriesViewModel extends AndroidViewModel {

    private CategoryRepository mRepository;

    private LiveData<List<Categories>> mAllCategories;

    private LiveData<List<Categories>> mCategoriesByType;

    public CategoriesViewModel(Application application) {
        super(application);
        mRepository = new CategoryRepository(application);
        mAllCategories = mRepository.getAllCategories();
//        mCategoriesByType=mRepository.getCategoriesByType();
    }

    public LiveData<List<Categories>> getAllCategories() { return mAllCategories; }

    public LiveData<List<Categories>> getCategoriesByType(int catType) { return mRepository.getCategoriesByType(catType); }



    public LiveData<List<Categories>> getCategory(int catId) { return mRepository.getSingleCategories(catId); }

    public void insert(Categories categories) { mRepository.insert(categories); }
    public void insertList(List<Categories> cat) { mRepository.insertCategoriesList(cat); }
    public void update(Categories categories) { mRepository.update(categories); }
    public void delete(Categories categories) { mRepository.delete(categories); }
}