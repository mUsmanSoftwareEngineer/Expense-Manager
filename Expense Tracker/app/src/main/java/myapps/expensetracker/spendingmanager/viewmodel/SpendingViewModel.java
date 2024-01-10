package myapps.expensetracker.spendingmanager.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.database.SpendingRepository;
import myapps.expensetracker.spendingmanager.entity.Spending;

public class SpendingViewModel extends AndroidViewModel {

    private SpendingRepository mRepository;

    private LiveData<List<Spending>> mAllSpending;
    public static Float result1 = 0F;


    private MutableLiveData<Integer> result = new MutableLiveData<Integer>();

    Float loadIncome=0.0f;



    public SpendingViewModel(Application application) {
        super(application);
        mRepository = new SpendingRepository(application);
//        mAllSpending = mRepository.getAllSpending(startingMonth, currentMonth, spendingExpenseType, SpendingAccountId);


    }

    public void deleteSingleTransaction(int id){
            mRepository.delById(id);
    }

    public void deleteSingleCategory(int id){
        mRepository.delByIdCategory(id);
    }

    public LiveData<Float> getTotalIncomeMonthly(String date, int spendingType, int accountID) {

        return mRepository.getTotalIncomeMonthly(date,spendingType,accountID); }

    public LiveData<Double> getAllExpenseMonthlyTotal(String date, int spendingType, int accountID) { return mRepository.getTotalExpenseMonthly(date,spendingType,accountID); }

    public LiveData<List<Spending>> getExpenseMonthly(String date, int spendingType, int accountID) {
        return mRepository.getExpensesMonthly(date,spendingType,accountID); }

    public LiveData<Float> getTotalExpense(String strtDate, String endDate, int spendingType, int accountID) {
        return mRepository.getTotalExpense(strtDate,endDate,spendingType,accountID); }

    public LiveData<List<Spending>> getAllExpenseMonthlyHistory(int accountID) {
        return mRepository.getExpensesMonthlyHistory(accountID); }

    public LiveData<Float> getAllSixMonthExp(String spendingDate, int spendingExpenseType, int SpendingAccountId, int spendingCategoryId) {
        return mRepository.getLastSixMonthAmountExp(spendingDate,spendingExpenseType,SpendingAccountId,spendingCategoryId); }

    public LiveData<Float> getLastSixMonthExpCategoryOneByOne(String startingMonth, String currentMonth, int spendingCategoryID, int spendingExpenseType, int SpendingAccountId)  {

        return mRepository.getLastSixMonthExpCategoryOneByOne(startingMonth,currentMonth,spendingCategoryID,spendingExpenseType,SpendingAccountId);

    }

    public LiveData<Float> getDataDayWise(String startingMonth, int spendingExpenseType, int SpendingAccountId)  {

        return mRepository.getDataDayWise(startingMonth,spendingExpenseType,SpendingAccountId);

    }

//    public LiveData<Double> getLastSixMonthCategory(String spendingDate, int spendingExpenseType, int SpendingAccountId, int spendingCategoryId) {
//        return mRepository.getLastSixMonthCategory(spendingDate,spendingExpenseType,SpendingAccountId,spendingCategoryId); }


    public LiveData<List<Spending>> getIconsById(int ID) {
        return mRepository.getExpensesMonthlyHistory(ID); }

    public LiveData<List<Spending>> getSpending(String startingMonth, String currentMonth, int SpendingAccountId) {

//        return mAllSpending;
       return mRepository.getAllSpending(startingMonth,currentMonth,SpendingAccountId);
    }

    public LiveData<List<Spending>> getTransactionByCategory(String Month, int categoryId, int SpendingAccountId) {

        return mRepository.getTransactionByCategoryId(Month,categoryId,SpendingAccountId);
    }


    public void setAmount(Integer value) {



//        result1 = value;
        Constants.incomeVal=value;
        result.setValue(value);
        result.postValue(value);

    }

    public MutableLiveData<Integer> getResult()

    {

        return result;

    }

    public void insert(Spending spending) { mRepository.insert(spending); }
    public void update(Spending spending) { mRepository.update(spending); }
    public void delete(Spending spending) { mRepository.delete(spending); }
    public void deleteById(Spending spending) { mRepository.delete(spending); }

//    public Float getIncome(String date, int spendingType, int accountID){
//        loadIncome=mRepository.getTotal(date,spendingType,accountID);
//        return loadIncome;
//    }
}