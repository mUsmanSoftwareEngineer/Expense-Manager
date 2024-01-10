package myapps.expensetracker.spendingmanager.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import myapps.expensetracker.spendingmanager.database.BudgetRepository;
import myapps.expensetracker.spendingmanager.database.CategoryRepository;
import myapps.expensetracker.spendingmanager.entity.Budget;
import myapps.expensetracker.spendingmanager.entity.Categories;
import myapps.expensetracker.spendingmanager.entity.Spending;

public class BudgetViewModel extends AndroidViewModel {

    private BudgetRepository mRepository;

    private LiveData<List<Categories>> mAllCategories;

    private LiveData<List<Categories>> mCategoriesByType;

    public BudgetViewModel(Application application) {
        super(application);
        mRepository = new BudgetRepository(application);

    }

    public LiveData<List<Budget>> getBudgetMonthly(String date, int accountID) {
        return mRepository.getBudgetMonthly(date,accountID); }


    public void insert(Budget budget) { mRepository.insert(budget); }
    public void update(Budget budget) { mRepository.update(budget); }
    public void delete(Budget budget) { mRepository.delete(budget); }

}